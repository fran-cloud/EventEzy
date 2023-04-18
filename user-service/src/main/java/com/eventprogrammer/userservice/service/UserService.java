package com.eventprogrammer.userservice.service;

import com.eventprogrammer.userservice.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventprogrammer.userservice.DTO.UserRequest;
import com.eventprogrammer.userservice.entity.User;
import com.eventprogrammer.userservice.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /*Metodo per creare un utente */
    public void createUser(UserRequest userRequest){
        User user = User.builder()
        .nome(userRequest.getNome())
        .cognome(userRequest.getCognome())
        .indirizzo(userRequest.getIndirizzo())
        .email(userRequest.getEmail())
        .dataNascita(userRequest.getDataNascita())
        .build();

        userRepository.save(user);
        log.info("L'utente {} è stato registrato", user.getUserId());
    }

    public User saveUser(User user) {
        log.info("Nel metodo saveUser del service di user");
        return userRepository.save(user);
    }

    public User findUserById(String userId){
        log.info("All'interno del metodo findUserById del service di user.");
        return userRepository.findByUserId(userId);
    }
    
}
