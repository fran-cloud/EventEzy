package com.eventprogrammer.organizationservice.service;

import com.eventprogrammer.organizationservice.DTO.Email;
import com.eventprogrammer.organizationservice.eccezioni.GenericErrorException;
import com.eventprogrammer.organizationservice.entity.ConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
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
import java.util.Collection;
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

    /*Metodo per creare una organizzazione */
    public void createOrganization(OrganizationRequest organizationRequest) throws GenericErrorException {

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

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                organization
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        String link = "http://localhost:9001/organizations/confirm?token=" + token;
        Email email = new Email(buildEmail(organization.getOrganizationName(),link), "Confirm your email");
        emailSend.send(organization.getEmail(), email);

    }

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

        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return organization.getPassword();
            }

            @Override
            public String getUsername() {
                return organization.getEmail();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }

    private String buildEmail(String name, String link) {
        return "Hi " + name + ". Thank you for registering!\nPlease click on the below link to activate your account: " +
                link + "\nActivate Now. Link will expire in 15 minutes. \nSee you soon! \n\n EventEzy Team.";
    }
}
