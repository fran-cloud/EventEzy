package com.eventprogrammer.userservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.eventprogrammer.userservice.DTO.EventResponse;
import com.eventprogrammer.userservice.DTO.ReservationRequest;
import com.eventprogrammer.userservice.entity.Event;
import com.eventprogrammer.userservice.entity.Organization;
import com.eventprogrammer.userservice.entity.Reservation;
import com.eventprogrammer.userservice.entity.User;
import com.eventprogrammer.userservice.repository.EventRepository;
import com.eventprogrammer.userservice.repository.OrganizationRepository;
import com.eventprogrammer.userservice.repository.ReservationRepository;
import com.eventprogrammer.userservice.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReservationService {
    
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;

    /*Salvo una prenotazione inserendo il body della request senza l'id */
    public Reservation createReservation(ReservationRequest reservationRequest){

        AccessToken accessToken = getKeycloakAccessToken();
       /* Boolean present = userRepository.existsById(accessToken.getId());
        if(present==false){
            createUser(accessToken);
        } */

        if(reservationRequest.getEvento().getPrenotazioni().size()<reservationRequest.getEvento().getMaxPrenotati()){
            //User user = userRepository.findByUserId(accessToken.getId());
            Reservation reservation = Reservation.builder()
            .evento(reservationRequest.getEvento())
            .utenteEmail(accessToken.getEmail())
            .build();  /* Build mi permette di salvare richiamando un costruttore senza inserire l'Id */

            reservationRepository.save(reservation);
            // user.getPrenotazioni().add(reservation);
            log.info("La prenotazione {} è stata salvata", reservation.getReservationId());
            return reservation;
        }
        else{
            log.info("L'evento è sold out");
            return null;
        }


    }

    /* Con questo prelevo tutti gli eventi disponibili */
    public List<EventResponse> getAllEvents() {
        List<Event> events = eventRepository.findAll(); 
        
        return events.stream().map(this::mapToEventResponse).toList();
    }

     /* Con questo prelevo tutti gli eventi di un determinato organizzatore  */
     public List<EventResponse> getAllOrganizationEvent(String organizationId) {

        Organization organization = organizationRepository.findByOrganizationId(organizationId);
    	
    	List<Event> events = organization.getEventiOrganizzati();

        return events.stream().map(this::mapToEventResponse).toList();

    }

    /* Metodo ausiliario che serve a mappare una EventResponse in una List */
    private EventResponse mapToEventResponse(Event event){
        return EventResponse.builder()
        .eventId(event.getEventId())
        .nome(event.getNome())
        .descrizione(event.getDescrizione())
        .maxPrenotati(event.getMaxPrenotati())
        .dataEoraDate(event.getDataEoraDate())
        .organizationEmail(event.getOrganizationEmail())
        .build();
    }

    /*Con questo prelevo tutte le prenotazioni effettuate da un utente */
    public List<Reservation> getAllReservation(){

        AccessToken accessToken = getKeycloakAccessToken();
        /* Boolean present = userRepository.existsById(accessToken.getId());
        if(present==false){
            createUser(accessToken);
        } */

        // User user = userRepository.findByUserId(accessToken.getEmail());
        List<Reservation> reservations = reservationRepository.findByUtenteEmail(accessToken.getEmail());  //user.getPrenotazioni();

        return  reservations;   //reservations.stream().toList();
    }

    /*Metodo per eliminare una prenotazione */
    public boolean deleteReservation(String reservationId){
        Reservation reservation = reservationRepository.findByReservationId(reservationId);
        reservationRepository.delete(reservation);
        AccessToken accessToken = getKeycloakAccessToken();
        /*User user = userRepository.findByUserId(accessToken.getId());
        user.getPrenotazioni().remove(reservation);*/
        log.info("La prenotazione {} è stata cancellata", reservation.getReservationId());
        return true;
    }

    private AccessToken getKeycloakAccessToken(){
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) keycloakAuthenticationToken.getPrincipal();
        KeycloakSecurityContext keycloakSecurityContext = keycloakPrincipal.getKeycloakSecurityContext();
        AccessToken accessToken = keycloakSecurityContext.getToken();

        return accessToken;
    }

    /*Metodo per creare un utente */
    public void createUser(AccessToken accessToken){

        Map<String, Object> customAttribute = accessToken.getOtherClaims();
        
        User user = User.builder()
        .nome(accessToken.getGivenName())
        .cognome(accessToken.getFamilyName())
        .indirizzo(String.valueOf(customAttribute.get("indirizzo")))
        .email(accessToken.getEmail())
        .dataNascita((Date) customAttribute.get("dataNascita"))
        .prenotazioni(new ArrayList<Reservation>())
        .build();

        userRepository.save(user);
        log.info("L'utente {} è stato registrato", user.getUserId());
    }

    public Event getEventById(String id) {
        Event event = new Event();
        event = eventRepository.findById(id).get();
        return event;
    }

}

