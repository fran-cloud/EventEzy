package com.eventprogrammer.userservice.service;

import com.eventprogrammer.userservice.DTO.AuthenticationRequest;
import com.eventprogrammer.userservice.DTO.AuthenticationResponse;
import com.eventprogrammer.userservice.DTO.Email;
import com.eventprogrammer.userservice.Util.JwtService;
import com.eventprogrammer.userservice.eccezioni.GenericErrorException;
import com.eventprogrammer.userservice.entity.ConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.eventprogrammer.userservice.DTO.UserRequest;
import com.eventprogrammer.userservice.entity.User;
import com.eventprogrammer.userservice.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
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

    /*Metodo per creare un utente */
    public AuthenticationResponse createUser(UserRequest userRequest) throws GenericErrorException {

        if (userRepository.findByEmail(userRequest.getEmail())!=null){
            throw new GenericErrorException("Esiste già un utente registrato con questa email", "O01");
        }

        User user = User.builder()
        .nome(userRequest.getNome())
        .cognome(userRequest.getCognome())
        .indirizzo(userRequest.getIndirizzo())
        .email(userRequest.getEmail())
        .dataNascita(userRequest.getDataNascita())
        .role("User")
        .password(bCryptPasswordEncoder.encode(userRequest.getPassword()))
        .locked(false)
        .enabled(false)
        .build();

        userRepository.save(user);
        log.info("L'utente {} è stato registrato", user.getUserId());


        //VIENE GENERATO UN TOKEN UTILIZZATO NELLA MAIL DI CONFERMA PER ABILITARE L'ACCOUNT
        //VIENE CREATA E INVIATA LA MAIL
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        String link = "http://localhost:9002/users/confirm?token=" + token;
        Email email = new Email(buildEmail(user.getNome(),link), "Confirm your email");
        emailSend.send(user.getEmail(), email);


        //VIENE GENERATO IL TOKEN PER L'AUTENTICAZIONE
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().accessToken(jwtToken).build();
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

        User user = confirmationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        return "confirmed";
    }


    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) throws GenericErrorException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
        );

        var user = userRepository.findByEmail(authenticationRequest.getEmail());
        if (user == null) {
            throw new GenericErrorException("Organizzatore non registrato", "O03");
        }

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().accessToken(jwtToken).build();
    }


    public User saveUser(User user) {
        log.info("Nel metodo saveUser del service di user");
        return userRepository.save(user);
    }

    public User findUserById(String userId){
        log.info("All'interno del metodo findUserById del service di user.");
        return userRepository.findByUserId(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Non esiste un utente con l'email specificata");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());

    }

    private String buildEmail(String name, String link) {
        return "Ciao " + name + ". Grazie per la registrazione!\nPer attivare il tuo account clicca qui: " +
                link + "\nAttivalo ora. Il link scadrà in 15 minuti. \nCi vediamo presto! \n\nEventEzy Team.";
    }
    
}
