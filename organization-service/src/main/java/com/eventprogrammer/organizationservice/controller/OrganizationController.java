package com.eventprogrammer.organizationservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eventprogrammer.organizationservice.DTO.*;
import com.eventprogrammer.organizationservice.eccezioni.GenericErrorException;
import com.eventprogrammer.organizationservice.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eventprogrammer.organizationservice.service.EventService;
import com.eventprogrammer.organizationservice.service.OrganizationService;
import com.eventprogrammer.organizationservice.entity.Organization;
import com.eventprogrammer.organizationservice.entity.Reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;


@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/organizations")
@Slf4j
@RequiredArgsConstructor
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private EventService eventService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse createOrganization(@Valid @RequestBody OrganizationRequest organizationRequest ) throws GenericErrorException {
        return organizationService.createOrganization(organizationRequest);
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token) {
        return organizationService.confirmToken(token);
    }


    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) throws GenericErrorException {
        return organizationService.login(authenticationRequest);
    }


    @PostMapping("/save-organization")
    public Organization saveOrganization(@RequestBody Organization organization)
    {   log.info("All'interno del metodo saveOrganization dell' organization controller.");
        return organizationService.saveOrganization(organization);

    }
    @GetMapping("/find-organization/{id}")
    public Organization findOrganizationById(@PathVariable("id") String id) {
        log.info("All'interno del metodo findOrganizationById del controller di organization.");  
        return organizationService.findOrganizationById(id);

    }

    @GetMapping("/getAllEvents")
    @ResponseStatus(HttpStatus.OK)
    public List<EventResponse> getAllEvents(){
        return eventService.getAllEvents();

    }

   @GetMapping("/get-all-organization-events/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<EventResponse> getAllOrganizationEvent(@PathVariable("id") String id){
        return eventService.getAllOrganizationEvent(id);

    }


    @PostMapping("/create-event/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@Valid @RequestBody EventRequest eventRequest, @PathVariable("id") String id) throws GenericErrorException{
        return eventService.createEvent(eventRequest, id);
        
    }

    @PostMapping("/modify-event/{id}")
    public Event modifyEvent(@PathVariable("id") String id,@Valid @RequestBody EventRequest eventRequest ){
        return eventService.modifyEvent(id, eventRequest);

    }

    @GetMapping("/get-all-reservations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Reservation> getAllReservation(@PathVariable("id") String id){
        return eventService.getAllReservation(id);

    }

    @DeleteMapping("/delete-event/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteEvent(@PathVariable("id") String id) throws GenericErrorException{
        boolean deleted = false;
        deleted = eventService.deleteEvent(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", deleted);
        return ResponseEntity.ok(response);
    }

}
