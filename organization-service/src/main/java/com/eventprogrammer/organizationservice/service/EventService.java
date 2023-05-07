package com.eventprogrammer.organizationservice.service;

import java.util.List;

import com.eventprogrammer.organizationservice.DTO.Email;
import com.eventprogrammer.organizationservice.eccezioni.GenericErrorException;
import com.eventprogrammer.organizationservice.repository.ReservationRepository;
import com.eventprogrammer.organizationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private EmailSend emailSend;

    /* Quì salvo l'evento inserendo come input il body della request senza l'Id */
    public Event createEvent(EventRequest eventRequest, String id) throws GenericErrorException {

        Event event = Event.builder()
         .nome(eventRequest.getNome())
         .tipologia(eventRequest.getTipologia())
         .indirizzo(eventRequest.getIndirizzo())
         .dataEoraDate(eventRequest.getDataEoraDate())
         .organizationEmail(id)
         .maxPrenotati(eventRequest.getMaxPrenotati())
         .postiDisponibili(eventRequest.getMaxPrenotati())

         .build();  /* Build mi permette di salvare richiamando un costruttore senza inserire l'Id */

        //Organization organization = organizationRepository.findByOrganizationId(id);
        //String email = organization.getEmail();
        List<Event> events = eventRepository.findByOrganizationEmail(id);
        for (int i=0; i<events.size(); i++) {
            if (events.get(i).getNome().equals(eventRequest.getNome())) {
                throw new GenericErrorException("È già stato creato un evento con questo nome", "E01");
            }
        }
        eventRepository.save(event);

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

        //Organization organization = organizationRepository.findByOrganizationId(organizationId);
        //String email= organization.getEmail();
    	List<Event> events = eventRepository.findByOrganizationEmail(organizationId);

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



    /* Con questo prelevo tutte le prenotazioni di un determinato evento  */
    public List<Reservation> getAllReservation(String eventId) {
    	
    	List<Reservation> reservations = reservationRepository.findByEventId(eventId);

        return reservations.stream().toList();
    }

    /*Modifica evento*/
    public Event modifyEvent(String eventId, EventRequest eventRequest){

        Event event = eventRepository.findByEventId(eventId);

        List<Reservation> reservations = reservationRepository.findByEventId(eventId);

        event.setNome(eventRequest.getNome());
        event.setTipologia(eventRequest.getTipologia());
        event.setIndirizzo(eventRequest.getIndirizzo());
        event.setDataEoraDate(eventRequest.getDataEoraDate());
        event.setMaxPrenotati(eventRequest.getMaxPrenotati());
        event.setPostiDisponibili(eventRequest.getMaxPrenotati()-reservations.size());

        eventRepository.save(event);

        for (int i=0; i<reservations.size(); i++){
            Reservation reservation = reservations.get(i);
            reservation.setEventName(eventRequest.getNome());
            reservation.setEventAddress(eventRequest.getIndirizzo());
            reservation.setEventData(eventRequest.getDataEoraDate());
            reservationRepository.save(reservation);
            //INVIO MAIL AGLI USER INTERESSATI
            Email email = new Email(buildEmail(reservation.getUtenteEmail(), event,1),"Evento Modificato");
            emailSend.send(reservation.getUtenteEmail(),email);
        }

        return event;
    }




    /* Metodo per eliminare un evento */ 
    public boolean deleteEvent(String eventId) throws GenericErrorException{

        Event event = eventRepository.findByEventId(eventId);

        if (event == null){
            throw new GenericErrorException("L'evento selezionato non esiste", "E02");
        }

        List<Reservation> pren_effettuate = reservationRepository.findByEventId(eventId);
        if (pren_effettuate.size()>0) {
            for (int i = 0; i < pren_effettuate.size(); i++) {
                Reservation reservation = pren_effettuate.get(i);
                reservationRepository.delete(pren_effettuate.get(i));
                //INVIO MAIL AGLI USER INTERESSATI
                Email email = new Email(buildEmail(reservation.getUtenteEmail(), event,0),"Evento Cancellato");
                emailSend.send(reservation.getUtenteEmail(),email);
            }
        }
        eventRepository.delete(event);
        log.info("L'evento {} è stato cancellato", event.getEventId());
        return true;
    }


    private String buildEmail(String name, Event event, int cod) {
        String msg = new String();

        if (cod==0){
            msg = "Ciao " + name + ",\nl'evento " + event.getNome() +" previsto per la data "
            + event.getDataEoraDate() + " è stato cancellato. "
                    + "Contatta l'organizzatore all'indirizzo " + event.getOrganizationEmail()
                    +" per saperne di più. \n\nEventEzy Team.";
        }
        if (cod==1){
            msg = "Ciao " + name + ",\nl'evento " + event.getNome() +" previsto per la data "
                    + event.getDataEoraDate() + " ha subito delle modifiche che possono riguardare la data, l'ora, " +
                    "il luogo o altri dettagli. Controlla la tua area personale per prendere visione delle modifiche. " +
                    "Ricorda che in caso di problemi è sempre possibile eliminare la propria prenotazione. \n\nEventEzy Team.";
        }
        return msg;
    }
    
}