package com.eventprogrammer.organizationservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.eventprogrammer.organizationservice.DTO.EventRequest;
import com.eventprogrammer.organizationservice.DTO.EventResponse;
import com.eventprogrammer.organizationservice.entity.Event;
import com.eventprogrammer.organizationservice.entity.Organization;
import com.eventprogrammer.organizationservice.entity.Reservation;
import com.eventprogrammer.organizationservice.repository.EventRepository;
import com.eventprogrammer.organizationservice.repository.OrganizationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private OrganizationRepository organizationRepository;

    /* Quì salvo l'evento inserendo come input il body della request senza l'Id */
    public Event createEvent(EventRequest eventRequest){

        AccessToken accessToken = getKeycloakAccessToken();
        /*Boolean present = organizationRepository.existsByEmail(accessToken.getEmail());
        if(present==false){
            createOrganization(accessToken);
        }*/

        Event event = Event.builder()
         .nome(eventRequest.getNome())
         .descrizione(eventRequest.getDescrizione())
         .indirizzo(eventRequest.getIndirizzo())
         .dataEoraDate(eventRequest.getDataEoraDate())
         .organizationEmail(accessToken.getEmail())
         .maxPrenotati(eventRequest.getMaxPrenotati())

         .build();  /* Build mi permette di salvare richiamando un costruttore senza inserire l'Id */

        eventRepository.save(event);
        /* Organization organization = organizationRepository.findByOrganizationId(accessToken.getId());
        organization.getEventiOrganizzati().add(event); */
        log.info("L'evento {} è stato salvato", event.getEventId());
        return event;
    }

    /* Con questo prelevo tutti gli eventi disponibili */
    public List<EventResponse> getAllEvents() {
        List<Event> events = eventRepository.findAll(); 
        
        return events.stream().map(this::mapToEventResponse).toList();


    }
    /* Con questo prelevo tutti gli eventi di un determinato organizzatore  */
    /*public List<EventResponse> getAllOrganizationEvent(String organizationId) {

        Organization organization = organizationRepository.findByOrganizationId(organizationId);
    	
    	List<Event> events = organization.getEventiOrganizzati();

        return events.stream().map(this::mapToEventResponse).toList();


    }*/

    /* Metodo ausiliario che serve a mappare una EventResponse in una List */
    private EventResponse mapToEventResponse(Event event){
        return EventResponse.builder()
        .eventId(event.getEventId())
        .nome(event.getNome())
        .descrizione(event.getDescrizione())
        .indirizzo(event.getIndirizzo())
        .maxPrenotati(event.getMaxPrenotati())
        .dataEoraDate(event.getDataEoraDate())
        .organizationEmail(event.getOrganizationEmail())
        .build();

    }
    /* Con questo prelevo tutte le prenotazioni di un determinato evento  */
    public List<Reservation> getAllReservation(String eventId) {

        Event event = eventRepository.findByEventId(eventId);
    	
    	List<Reservation> reservations = event.getPrenotazioni();

        return reservations.stream().toList();


    }

    /* Metodo per eliminare un evento */ 
    public boolean deleteEvent(String eventId) {

        Event event = eventRepository.findByEventId(eventId);
        eventRepository.delete(event);
        /*AccessToken accessToken = getKeycloakAccessToken();
        Organization organization = organizationRepository.findByOrganizationId(accessToken.getId());
        organization.getEventiOrganizzati().remove(event);*/
        log.info("L'evento {} è stato cancellato", event.getEventId());
        return true;


    }
  
    private AccessToken getKeycloakAccessToken(){
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) keycloakAuthenticationToken.getPrincipal();
        KeycloakSecurityContext keycloakSecurityContext = keycloakPrincipal.getKeycloakSecurityContext();
        AccessToken accessToken = keycloakSecurityContext.getToken();

        return accessToken;
    }

    /*Metodo per creare una organizzazione */
   /* public void createOrganization(AccessToken accessToken){

        Map<String, Object> customAttribute = accessToken.getOtherClaims();

        Organization organization = Organization.builder()
        .organizationName(accessToken.getGivenName())
        .organizationAddress(String.valueOf(customAttribute.get("organizationAddress")))
        .email(accessToken.getEmail())
        .partitaIva(String.valueOf(customAttribute.get("partitaIva")))
        .eventiOrganizzati(new ArrayList<Event>())
        .build();

        organizationRepository.save(organization);
        log.info("L'organizzazione {} è stata registrata", organization.getOrganizationId());
    }*/
    
}