package com.eventprogrammer.userservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@CrossOrigin(origins = {"http://localhost:80", "http://localhost:8080" })
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
    public void createUser(@RequestBody UserRequest userRequest ){
        userService.createUser(userRequest);
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

    @GetMapping("/get-all-organization-events/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<EventResponse> getAllOrganizationEvent(@PathVariable("id") String id){
        return reservationService.getAllOrganizationEvent(id);
    }

    @PostMapping("/create-reservation")
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation createReservation(@RequestBody ReservationRequest reservationRequest ){
        return reservationService.createReservation(reservationRequest);
    }

    @GetMapping("/get-all-reservations")
    @ResponseStatus(HttpStatus.OK)
    public List<Reservation> getAllReservation(){
        return reservationService.getAllReservation();
    }

    @DeleteMapping("/delete-reservation/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteReservation(@PathVariable("id") String reservationId){
        boolean deleted = false;
        deleted = reservationService.deleteReservation(reservationId);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", deleted);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getEventById/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable("id") String id) {
        Event evento = null;
        evento = reservationService.getEventById(id);
        return ResponseEntity.ok(evento);
    }

}