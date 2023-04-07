package com.eventprogrammer.organizationservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eventprogrammer.organizationservice.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eventprogrammer.organizationservice.service.EventService;
import com.eventprogrammer.organizationservice.service.OrganizationService;
import com.eventprogrammer.organizationservice.DTO.EventRequest;
import com.eventprogrammer.organizationservice.DTO.EventResponse;
import com.eventprogrammer.organizationservice.DTO.OrganizationRequest;
import com.eventprogrammer.organizationservice.entity.Organization;
import com.eventprogrammer.organizationservice.entity.Reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@CrossOrigin(origins = {"http://localhost:80", "http://localhost:8080" })
@RequestMapping("/organizations")
@Slf4j
@RequiredArgsConstructor
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private EventService eventService;

    @PostMapping("/create-organization")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrganization(@RequestBody OrganizationRequest organizationRequest ){
        organizationService.createOrganization(organizationRequest);
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

   /* @GetMapping("/get-all-organization-events/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<EventResponse> getAllOrganizationEvent(@PathVariable("id") String id){
        return eventService.getAllOrganizationEvent(id);

    } */


    @PostMapping("/create-event")
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@RequestBody EventRequest eventRequest ){
        return eventService.createEvent(eventRequest);
        
    }

    @GetMapping("/get-all-reservations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Reservation> getAllReservation(@PathVariable("id") String id){
        return eventService.getAllReservation(id);

    }

    @DeleteMapping("/delete-event/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteEvent(@PathVariable("id") String id){
        boolean deleted = false;
        deleted = eventService.deleteEvent(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", deleted);
        return ResponseEntity.ok(response);
    }

}
