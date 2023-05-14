import Button from 'react-bootstrap/Button';
import { React, useState, useEffect, Fragment } from "react";
import Alert from 'react-bootstrap/Alert';

const EventoUser = ({evento}) => {

const EFFETTUA_PRENOTAZIONE = "http://localhost:8080/users/create-reservation";
const [prenotazione, setPrenotazione] = useState({
    reservationId: "",
    eventId: "",
    eventName: "",
    eventAddress: "",
    eventData: "",
    organizationEmail: "",
    utenteEmail: "",
  });
const [prenotazioneRequest, setPrenotazioneRequest]= useState({
    eventId: evento.eventId,
});

const [isOpen, setIsOpen] = useState(false);
const [isOpenPren, setIsOpenPren] = useState(false);

  const effettuaPrenotazione = async (e) => {
    e.preventDefault();
    const response = await fetch(EFFETTUA_PRENOTAZIONE + "/" + localStorage.getItem("utente"), {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
      },
      body: JSON.stringify(prenotazioneRequest),
    });
    const _prenotazione = await response.json();
    setPrenotazione(_prenotazione);
    if (_prenotazione.status==500){
        setIsOpen(true);
    }
    else{
        setIsOpenPren(true);
    }
  };



    return (
  <>

  <Alert variant="danger" onClose={() => setIsOpen(false)} show={isOpen} dismissible>
    <Alert.Heading>Qualcosa non va!</Alert.Heading>
      <p>
        Ti sei già prenotato per questo evento oppure non ci sono più posti disponibili.
      </p>
  </Alert>

  <Alert variant="success" onClose={() => setIsOpenPren(false)} show={isOpenPren} dismissible>
      <Alert.Heading>Ottimo!</Alert.Heading>
        <p>
          La tua prenotazione è andata a buon fine. Ti verrà inviata una email di riepilogo. Puoi visualizzare la tua prenotazione in Lista Prenotazioni
        </p>
    </Alert>

  <div className="card" style={{width:"100%", margin: "10px"}}>
  <div class="row g-0">
      <div class="col-md-4">
        <img src="https://brescia.unicusano.it/wp-content/uploads/2021/10/consigli-per-iniziare-una-carriera-come-organizzatore-di-eventi-min.jpg" class="img-fluid rounded-start"></img>
      </div>
    <div class="col-md-8">
    <div className="card-body">
      <h5 className="card-title">{evento.nome}</h5>
      <h6 className="card-subtitle mb-2 text-body-secondary">Tipologia: {evento.tipologia}</h6>
      <p className="card-text">Sede: {evento.indirizzo}</p>
      <p className="card-text">Data e ora: {evento.dataEoraDate.replace('T', ' - ').replace(':00.000+00:00','')}</p>
      <p className="card-text">Posti disponibili: {evento.postiDisponibili}</p>
      <Button variant="outline-success" onClick={effettuaPrenotazione}>Prenota</Button>
    </div>
  </div>
  </div>
  </div>
  <br/>
  </>
  )
}

export default EventoUser