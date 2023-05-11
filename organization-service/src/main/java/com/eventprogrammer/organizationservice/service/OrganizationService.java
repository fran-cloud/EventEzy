package com.eventprogrammer.organizationservice.service;

import com.eventprogrammer.organizationservice.DTO.AuthenticationRequest;
import com.eventprogrammer.organizationservice.DTO.AuthenticationResponse;
import com.eventprogrammer.organizationservice.DTO.Email;
import com.eventprogrammer.organizationservice.Util.JwtService;
import com.eventprogrammer.organizationservice.eccezioni.GenericErrorException;
import com.eventprogrammer.organizationservice.entity.ConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.eventprogrammer.organizationservice.DTO.OrganizationRequest;
import com.eventprogrammer.organizationservice.entity.Organization;
import com.eventprogrammer.organizationservice.repository.OrganizationRepository;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Service
@Slf4j
public class OrganizationService implements UserDetailsService {

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    @Autowired
    private EmailSend emailSend;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    /*Metodo per creare una organizzazione */
    public AuthenticationResponse createOrganization(OrganizationRequest organizationRequest) throws GenericErrorException {

        if (organizationRepository.findByEmail(organizationRequest.getEmail())!=null){
            throw new GenericErrorException("Esiste già un utente registrato con questa email", "O01");
        }

        Organization organization = Organization.builder()
        .organizationName(organizationRequest.getOrganizationName())
        .organizationAddress(organizationRequest.getOrganizationAddress())
        .email(organizationRequest.getEmail())
        .partitaIva(organizationRequest.getPartitaIva())
        .role("Organization")
        .password(bCryptPasswordEncoder.encode(organizationRequest.getPassword()))
        .locked(false)
        .enabled(false)
        .build();

        organizationRepository.save(organization);
        log.info("L'organizzazione {} è stata registrata", organization.getOrganizationId());



        //VIENE GENERATO UN TOKEN UTILIZZATO NELLA MAIL DI CONFERMA PER ABILITARE L'ACCOUNT
        //VIENE CREATA E INVIATA LA MAIL
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                organization
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        String link = "http://localhost:8080/organizations/confirm?token=" + token;
        Email email = new Email(buildEmail(organization.getOrganizationName(),link), "Conferma email");
        emailSend.send(organization.getEmail(), email);


        //VIENE GENERATO IL TOKEN PER L'AUTENTICAZIONE
        var jwtToken = jwtService.generateToken(organization);
        return AuthenticationResponse.builder().accessToken(jwtToken).build();
    }



    /*Questa funzione viene richiamata dall'organizzatore appena rigistrato per abilitare l'account
      Il link per richiamare tale metodo è inviato per email.
     */
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token);

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        Organization organization = confirmationToken.getOrganization();
        organization.setEnabled(true);
        organizationRepository.save(organization);
        return "confirmed";
    }


    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) throws GenericErrorException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
        );

        var organization = organizationRepository.findByEmail(authenticationRequest.getEmail());
        if (organization == null) {
            throw new GenericErrorException("Organizzatore non registrato", "O03");
        }
        var jwtToken = jwtService.generateToken(organization);
        return AuthenticationResponse.builder().accessToken(jwtToken).build();
    }


    public Organization saveOrganization(Organization organization) {
        log.info("All'interno del metodo saveOrganization del service di organization.");
        return organizationRepository.save(organization);
 
    }

    
    public Organization findOrganizationById(String organizationId) {
        log.info("All'interno del metodo findOrganizationById del service di organization.");
        return organizationRepository.findByOrganizationId(organizationId);

    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Organization organization = organizationRepository.findByEmail(email);

        if (organization == null) {
            throw new UsernameNotFoundException("Non esiste un utente con l'email specificata");
        }

        return new User(organization.getEmail(), organization.getPassword(), new ArrayList<>());

    }

    private String buildEmail(String name, String link) {
        return "Ciao " + name + ". Grazie per la registrazione!\nPer attivare il tuo account clicca qui: " +
                link + "\nAttivalo ora. Il link scadrà in 15 minuti. \nCi vediamo presto! \n\nEventEzy Team.";
    }
}
