import Button from 'react-bootstrap/Button';
import { React, useState, useEffect, Fragment } from "react";

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
  };



    return (
  <>
  <div className="col">
  <div className="card">
    <div className="card-body">
      <h5 className="card-title">{evento.nome}</h5>
      <h6 className="card-subtitle mb-2 text-body-secondary">Tipologia: {evento.tipologia}</h6>
      <p className="card-text">Sede: {evento.indirizzo}</p>
      <p className="card-text">Data e ora: {evento.dataEoraDate}</p>
      <p className="card-text">Posti disponibili: {evento.postiDisponibili}</p>
      <Button variant="outline-success" onClick={effettuaPrenotazione}>Prenota</Button>
    </div>
  </div>
  </div>
  <br/>
  </>
  )
}

export default EventoUser