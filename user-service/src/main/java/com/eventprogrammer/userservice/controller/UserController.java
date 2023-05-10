package com.eventprogrammer.userservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eventprogrammer.userservice.DTO.AuthenticationRequest;
import com.eventprogrammer.userservice.DTO.AuthenticationResponse;
import com.eventprogrammer.userservice.eccezioni.GenericErrorException;
import com.eventprogrammer.userservice.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eventprogrammer.userservice.DTO.EventResponse;
import com.eventprogrammer.userservice.DTO.ReservationRequest;
import com.eventprogrammer.userservice.DTO.UserRequest;
import com.eventprogrammer.userservice.entity.Reservation;
import com.eventprogrammer.userservice.entity.User;
import com.eventprogrammer.userservice.service.ReservationService;
import com.eventprogrammer.userservice.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/create-user")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse createUser(@Valid @RequestBody UserRequest userRequest ) throws GenericErrorException {
        return userService.createUser(userRequest);
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token) {
        return userService.confirmToken(token);
    }


    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) throws GenericErrorException {
        return userService.login(authenticationRequest);
    }

    @PostMapping("/save-user")
    public User saveUser(@RequestBody User user){
        log.info("Nel metodo saveUser del controller di User");
        return userService.saveUser(user);
    }

    @GetMapping("/find-user/{id}")
    public User findUserById(@PathVariable("id") String id){
        log.info("All'interno del metodo findUserById del controller di user.");
        return userService.findUserById(id);
    }
    
    @GetMapping("/getAllEvents")
    @ResponseStatus(HttpStatus.OK)
    public List<EventResponse> getAllEvents(){
        return reservationService.getAllEvents();
    }


    @PostMapping("/create-reservation/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation createReservation(@RequestBody ReservationRequest reservationRequest, @PathVariable("id") String userId) throws GenericErrorException {
        return reservationService.createReservation(reservationRequest, userId);
    }

    @GetMapping("/get-all-reservations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Reservation> getAllReservation(@PathVariable("id") String userId){
        return reservationService.getAllReservation(userId);
    }

    @DeleteMapping("/delete-reservation/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteReservation(@PathVariable("id") String reservationId){
        boolean deleted = false;
        deleted = reservationService.deleteReservation(reservationId);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", deleted);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/searchEvent")
    public List<Event> searchEvent(String txt) {
        List<Event> events = reservationService.searchEvent(txt);
        return events;
    }

    @GetMapping("/getEventById/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable("id") String id) {
        Event evento = null;
        evento = reservationService.getEventById(id);
        return ResponseEntity.ok(evento);
    }

}
