package com.eventprogrammer.userservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Reservation createReservation(ReservationRequest reservationRequest, String id){

        Event event = eventRepository.findByEventId(reservationRequest.getEventId());
        if(event.getPrenotazioni().size()<event.getMaxPrenotati()){

            User user = userRepository.findByUserId(id);

            Reservation reservation = Reservation.builder()
            .eventId(event.getEventId())
            .eventName(event.getNome())
            .utenteEmail(user.getEmail())
            .build();  /* Build mi permette di salvare richiamando un costruttore senza inserire l'Id */

            List<Reservation> check = user.getPrenotazioni();
            for (int i=0; i<check.size(); i++){
                if (check.get(i).getEventName()==event.getNome()){
                    log.info("È già presente una prenotazione per questo evento");
                    return null;
                }
            }
            //reservationRepository.save(reservation);

            user.getPrenotazioni().add(reservation);
            userRepository.save(user);

            //Event event = eventRepository.findByEventId(reservationRequest.getEvento().getEventId());
            event.getPrenotazioni().add(reservation);
            eventRepository.save(event);
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
        .indirizzo(event.getIndirizzo())
        .maxPrenotati(event.getMaxPrenotati())
        .dataEoraDate(event.getDataEoraDate())
        .organizationEmail(event.getOrganizationEmail())
        .build();
    }


    /*Ricerca evento per nome, tipologia, indirizzo o nome dell'organizzatore*/
    public List<Event> searchEvent(String txt){

        List<Event> events = new ArrayList<Event>();
        events.addAll(eventRepository.findByNome(txt));
        events.addAll(eventRepository.findByTipologia(txt));

        Organization organization = organizationRepository.findByOrganizationName(txt);
        if (organization != null){
            String email = organization.getEmail();
            events.addAll(eventRepository.findByOrganizationEmail(email));
        }

        events.addAll(eventRepository.findByIndirizzo(txt));

        return events.stream().toList();
    }



    /*Con questo prelevo tutte le prenotazioni effettuate da un utente */
    public List<Reservation> getAllReservation(String userId){

        User user = userRepository.findByUserId(userId);
        //List<Reservation> reservations = reservationRepository.findByUtenteEmail(user.getEmail());
        List<Reservation> reservations = user.getPrenotazioni();

        return  reservations;
    }

    /*Metodo per eliminare una prenotazione */
    public boolean deleteReservation(String reservationId){
        Reservation reservation = reservationRepository.findByReservationId(reservationId);
        String email = reservation.getUtenteEmail();
        Event event = eventRepository.findByEventId(reservation.getEventId());
        //reservationRepository.delete(reservation);
        User user = userRepository.findByEmail(email);
        user.getPrenotazioni().remove(reservation);
        userRepository.save(user);
        event.getPrenotazioni().remove(reservation);
        eventRepository.save(event);

        log.info("La prenotazione {} è stata cancellata", reservation.getReservationId());
        return true;
    }

    public Event getEventById(String id) {
        Event event = new Event();
        event = eventRepository.findById(id).get();
        return event;
    }

}

