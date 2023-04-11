package com.eventprogrammer.organizationservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.eventprogrammer.organizationservice.entity.User;
import com.eventprogrammer.organizationservice.repository.UserRepository;
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
    @Autowired
    private UserRepository userRepository;

    /* Quì salvo l'evento inserendo come input il body della request senza l'Id */
    public Event createEvent(EventRequest eventRequest, String id){

        Event event = Event.builder()
         .nome(eventRequest.getNome())
         .tipologia(eventRequest.getTipologia())
         .indirizzo(eventRequest.getIndirizzo())
         .dataEoraDate(eventRequest.getDataEoraDate())
         .organizationEmail(organizationRepository.findByOrganizationId(id).getEmail())
         .maxPrenotati(eventRequest.getMaxPrenotati())
         .prenotazioni(eventRequest.getPrenotazioni())

         .build();  /* Build mi permette di salvare richiamando un costruttore senza inserire l'Id */

        Organization organization = organizationRepository.findByOrganizationId(id);
        List<String> check = organization.getEventiOrganizzati();
        for (int i=0; i<check.size(); i++) {
            Event event_check = eventRepository.findByEventId(check.get(i));
            if (event_check.getNome()==eventRequest.getNome()) {
                log.info("È già stato creato un evento con questo nome");
                return null;
            }
        }
        eventRepository.save(event);
        organization.getEventiOrganizzati().add(event.getEventId());
        organizationRepository.save(organization);

        log.info("L'evento {} è stato salvato", event.getEventId());
        return event;
    }



    /* Con questo prelevo tutti gli eventi disponibili */
    public List<EventResponse> getAllEvents() {
        List<Event> events = eventRepository.findAll(); 
        
        return events.stream().map(this::mapToEventResponse).toList();
    }


    /* Con questo prelevo tutti gli eventi di un determinato organizzatore  */
    /*Lo usiamo nel front-end: quando un organizzatore effettua il login vede tutti gli eventi che ha creato*/
    public List<EventResponse> getAllOrganizationEvent(String organizationId) {

        Organization organization = organizationRepository.findByOrganizationId(organizationId);
        String email= organization.getEmail();
    	List<Event> events = eventRepository.findByOrganizationEmail(email);

        return events.stream().map(this::mapToEventResponse).toList();
    }



    /* Metodo ausiliario che serve a mappare una EventResponse in una List */
    private EventResponse mapToEventResponse(Event event){
        return EventResponse.builder()
        .eventId(event.getEventId())
        .nome(event.getNome())
        .tipologia(event.getTipologia())
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

    /*Modifica evento*/
    public Event modifyEvent(String eventId, EventRequest eventRequest){

        Event event = eventRepository.findByEventId(eventId);

        Organization organization = organizationRepository.findByEmail(event.getOrganizationEmail());
        organization.getEventiOrganizzati().remove(event.getEventId());

        event.setNome(eventRequest.getNome());
        event.setTipologia(eventRequest.getTipologia());
        event.setIndirizzo(eventRequest.getIndirizzo());
        event.setDataEoraDate(eventRequest.getDataEoraDate());
        event.setMaxPrenotati(eventRequest.getMaxPrenotati());
        event.setPrenotazioni(event.getPrenotazioni());

        organization.getEventiOrganizzati().add(event.getEventId());
        organizationRepository.save(organization);
        eventRepository.save(event);

        //AGGIUNGERE INVIO MAIL A TUTTI I PRENOTATI PER AVVISARE DELLA MODIFICA

        return event;
    }




    /* Metodo per eliminare un evento */ 
    public boolean deleteEvent(String eventId) {

        Event event = eventRepository.findByEventId(eventId);
        Organization organization = organizationRepository.findByEmail(event.getOrganizationEmail());
        organization.getEventiOrganizzati().remove(eventId);
        organizationRepository.save(organization);

        List<Reservation> pren_effettuate = event.getPrenotazioni();
        if (pren_effettuate.size()>0) {
            for (int i = 0; i < pren_effettuate.size(); i++) {
                String email = pren_effettuate.get(i).getUtenteEmail();
                User user = userRepository.findByEmail(email);
                user.getPrenotazioni().remove(pren_effettuate.get(i));
                userRepository.save(user);
            }
        }
        eventRepository.delete(event);
        log.info("L'evento {} è stato cancellato", event.getEventId());
        return true;
    }
    
}