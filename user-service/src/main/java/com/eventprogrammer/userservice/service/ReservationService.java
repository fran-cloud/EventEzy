package com.eventprogrammer.userservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    /*Metodo per effettuare una prenotazione */
    public Reservation createReservation(ReservationRequest reservationRequest){

        if(reservationRequest.getEvento().getPrenotazioni().size()<reservationRequest.getEvento().getMaxPrenotati()){

            Reservation reservation = Reservation.builder()
            .evento(reservationRequest.getEvento())
            .utenteEmail(reservationRequest.getUtenteEmail())
            .build();  /* Build mi permette di salvare richiamando un costruttore senza inserire l'Id */

            reservationRepository.save(reservation);
            User user = userRepository.findByEmail(reservationRequest.getUtenteEmail());
            user.getPrenotazioni().add(reservation);
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



    /* Metodo ausiliario che serve a mappare una EventResponse in una List */
    private EventResponse mapToEventResponse(Event event){
        return EventResponse.builder()
        .eventId(event.getEventId())
        .nome(event.getNome())
        .tipologia(event.getTipologia())
        .maxPrenotati(event.getMaxPrenotati())
        .dataEoraDate(event.getDataEoraDate())
        .organizationEmail(event.getOrganizationEmail())
        .build();
    }


    /*Ricerca evento per nome, tipologia, indirizzo o nome dell'organizzatore*/
    public List<Event> searchEvent(String txt){

        List<Event> events = new ArrayList<Event>();
        events.addAll(eventRepository.findByNome(txt));
        events.addAll(eventRepository.fingByTipologia(txt));

        Organization organization = organizationRepository.findByOrganizationName(txt);
        if (organization != null){
            String email = organization.getEmail();
            events.addAll(eventRepository.findByOrganizationEmail(email));
        }

        events.addAll(eventRepository.findByIndirizzo(txt));

        return events;
    }



    /*Con questo prelevo tutte le prenotazioni effettuate da un utente */
    public List<Reservation> getAllReservation(String userId){

        User user = userRepository.findByUserId(userId);
        List<Reservation> reservations = reservationRepository.findByUtenteEmail(user.getEmail());

        return  reservations;
    }

    /*Metodo per eliminare una prenotazione */
    public boolean deleteReservation(String reservationId){
        Reservation reservation = reservationRepository.findByReservationId(reservationId);
        String email = reservation.getUtenteEmail();
        reservationRepository.delete(reservation);
        User user = userRepository.findByEmail(email);
        user.getPrenotazioni().remove(reservation);
        log.info("La prenotazione {} è stata cancellata", reservation.getReservationId());
        return true;
    }

    public Event getEventById(String id) {
        Event event = new Event();
        event = eventRepository.findById(id).get();
        return event;
    }

}

