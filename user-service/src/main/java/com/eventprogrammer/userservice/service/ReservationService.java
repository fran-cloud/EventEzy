package com.eventprogrammer.userservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.eventprogrammer.userservice.DTO.Email;
import com.eventprogrammer.userservice.eccezioni.GenericErrorException;
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
    @Autowired
    private EmailSend emailSend;

    /*Metodo per effettuare una prenotazione */
    public Reservation createReservation(ReservationRequest reservationRequest, String id) throws GenericErrorException {

        //User user = userRepository.findByUserId(id);
        //Controllo se è già presente una prenotazione per l'evento selezionato
        List<Reservation> check = reservationRepository.findByUtenteEmail(id);
        for (int i=0; i<check.size(); i++) {
            if (check.get(i).getEventId().equals(reservationRequest.getEventId())) {
                throw new GenericErrorException("È già stata effettuata una prenotazione per questo evento", "R01");
            }
        }

        //Controllo se ci sono posti disponibili e in caso di risposta affermativa registro la prenotazione
        Event event = eventRepository.findByEventId(reservationRequest.getEventId());
        if(event.getPostiDisponibili()>0){

            Reservation reservation = Reservation.builder()
            .eventId(event.getEventId())
            .eventName(event.getNome())
            .eventAddress(event.getIndirizzo())
            .eventData(event.getDataEoraDate())
            .organizationEmail(event.getOrganizationEmail())
            .utenteEmail(id)
            .build();  /* Build mi permette di salvare richiamando un costruttore senza inserire l'Id */

            event.setPostiDisponibili(event.getPostiDisponibili()-1);
            eventRepository.save(event);

            reservationRepository.save(reservation);
            Email email = new Email(buildEmail(id, event), "Riepilogo prenotazione");
            emailSend.send(id, email);

            log.info("La prenotazione {} è stata salvata", reservation.getReservationId());
            return reservation;
        }
        else{
            throw new GenericErrorException("L'evento è SOLD OUT", "R02");
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
        .postiDisponibili(event.getPostiDisponibili())
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

        //User user = userRepository.findByUserId(userId);
        //List<Reservation> reservations = reservationRepository.findByUtenteEmail(user.getEmail());
        List<Reservation> reservations = reservationRepository.findByUtenteEmail(userId);

        return  reservations;
    }

    /*Metodo per eliminare una prenotazione */
    public boolean deleteReservation(String reservationId){
        Reservation reservation = reservationRepository.findByReservationId(reservationId);
        Event event = eventRepository.findByEventId(reservation.getEventId());
        event.setPostiDisponibili(event.getPostiDisponibili()+1);
        eventRepository.save(event);
        reservationRepository.delete(reservation);

        log.info("La prenotazione {} è stata cancellata", reservation.getReservationId());
        return true;
    }

    public Event getEventById(String id) {
        Event event = new Event();
        event = eventRepository.findById(id).get();
        return event;
    }


    private String buildEmail(String name, Event event) {
        String msg = new String();

        msg = "Ciao " + name + ",\nti sei prenotato con successo per l'evento \""+ event.getNome() +
                "\" che si terrà il giorno "+ event.getDataEoraDate()+ " presso " + event.getIndirizzo() +
                ".\nPer maggiori dettagli visita la tua area personale su EventEzy.\n\nEventEzy Team.";

        return msg;
    }

}

