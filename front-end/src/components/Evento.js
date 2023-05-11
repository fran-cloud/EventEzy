import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import React, { useState, useEffect } from 'react';
import Form from 'react-bootstrap/Form';
import Prenotazione from './Prenotazione';
import { useNavigate } from "react-router-dom";
import Alert from 'react-bootstrap/Alert';

const Evento = ({evento, eliminaEvento}) => {

    const navigate = useNavigate();

    const MODIFYEVENT_API_URL = "http://localhost:8080/organizations/modify-event";
    const GET_RESERATION_API = "http://localhost:8080/organizations/get-all-reservations";
    const [loading, setLoading] = useState(true);
    const [responseEvento, setResponseEvento] = useState(null);
    const [prenotazioneId, setPrenotazioneId] = useState(null);
    const [prenotazione, setPrenotazione] = useState({
      reservationId: "",
      eventId: "",
      eventName: "",
      eventAddress: "",
      eventData: "",
      organizationEmail: "",
      utenteEmail: "",
    });
    const [prenotazioni, setPrenotazioni] = useState(null);
    const [responsePrenotazione, setResponsePrenotazione] = useState(null);
    const [eventRequest, setEventRequest] = useState(evento);
    //Variabili per gestire la modal del tasto Elimina
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    //Variabili per gestire la modal del tasto Modifica
    const [showMod, setShowMod] = useState(false);
    const handleCloseMod = () => setShowMod(false);
    const handleShowMod = () => setShowMod(true);

    //Variabili per gestire la modal del tasto Visualizza Prenotazioni
    const [showPren, setShowPren] = useState(false);
    const handleClosePren = () => setShowPren(false);
    const handleShowPren = () => setShowPren(true);

    const [isOpen, setIsOpen] = useState(false);

    useEffect(() => {
      const fetchData = async () => {
        setLoading(true);
        try {
          const response = await fetch(GET_RESERATION_API+'/'+evento.eventId, {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              'Authorization': `Bearer ${localStorage.getItem('token')}`,
            },
          });
          const prenotazioni = await response.json();
          setPrenotazioni(prenotazioni);
        } catch (error) {
          console.log(error);
        }
        setLoading(false);
      };
      fetchData();
    }, [prenotazione, responsePrenotazione]);

    const handleChange = (event) => {
      const value = event.target.value;
      setEventRequest({...eventRequest, [event.target.name]: value });
    };

  const modificaEvento = async (e, id) => {
    e.preventDefault();
    const response = await fetch(MODIFYEVENT_API_URL+'/'+id, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
      },
      body: JSON.stringify(eventRequest),
    });
    const _evento = await response.json();
    setResponseEvento(_evento);
    if(_evento.status==400){
        setIsOpen(true);
    }
  };


    return (

    <>

    <tr key={evento.eventId}>
      <td className="text-left px-6 py-4 whitespace-nowrap">
        <div className="text-sm text-gray-500"> {evento.nome}</div>
      </td>
      <td className="text-left px-6 py-4 whitespace-nowrap">
        <div className="text-sm text-gray-500"> {evento.tipologia}</div>
      </td>
      <td className="text-left px-6 py-4 whitespace-nowrap">
        <div className="text-sm text-gray-500"> {evento.indirizzo}</div>
      </td>
      <td className="text-left px-12 py-4 whitespace-nowrap">
        <div className="text-sm text-gray-500"> {evento.dataEoraDate.replace('T', ' - ').replace(':00.000+00:00','')}</div>
      </td>
      <td className="text-left px-6 py-4 whitespace-nowrap">
        <div className="text-sm text-gray-500"> {evento.postiDisponibili}</div>
      </td>
      <td className="text-left px-6 py-4 whitespace-nowrap">
        <div className="text-sm text-gray-500"> {evento.maxPrenotati}</div>
      </td>
      <td className="text-right px-6 py-4 whitespace-nowrap">
        <Button variant="outline-success" onClick={handleShowPren}> Visualizza Prenotazioni</Button>
      </td>
      <td className="text-right px-6 py-4 whitespace-nowrap">
        <Button variant="outline-secondary" onClick={handleShowMod}> Modifica</Button>
      </td>
      <td className="text-right px-6 py-4 whitespace-nowrap">
        <Button variant="outline-danger" onClick={handleShow}> Elimina</Button>
      </td>
    </tr>


    <div
      className="modal show"
      style={{ display: 'block', position: 'initial' }}
    >
      <Modal show={showPren}>
        <Modal.Header>
          <Modal.Title>Prenotazioni per {evento.nome}</Modal.Title>
        </Modal.Header>

        <Modal.Body>
              <table className="table">
                <thead className="bg-transparent">
                  <tr>
                    <th className="text-left font-medium text-gray-500 uppercase tracking-wide py-3 px-6"> Id</th>
                    <th className="text-left font-medium text-gray-500 uppercase tracking-wide py-3 px-6"> Utente</th>
                  </tr>
                </thead>
                {!loading && (
                  <tbody className="bg-white">
                    {prenotazioni?.map((prenotazione)  => (     // //Object.entries(eventi).map((evento)
                      <Prenotazione
                        prenotazione={prenotazione}
                        key={prenotazione.reservationId}
                      />
                    ))}
                  </tbody>
                )}
              </table>
        </Modal.Body>

        <Modal.Footer>
          <Button variant="secondary" onClick={handleClosePren}>Chiudi</Button>
        </Modal.Footer>
      </Modal>
    </div>


    <div
      className="modal show"
      style={{ display: 'block', position: 'initial' }}
    >
      <Modal show={showMod}>
        <Modal.Header>
          <Modal.Title>Modifica Evento</Modal.Title>
        </Modal.Header>

        <Modal.Body>
            <Alert variant="danger" onClose={() => setIsOpen(false)} show={isOpen} dismissible>
              <Alert.Heading>Qualcosa non va!</Alert.Heading>
                <p>
                  I dati inseriti non sono validi. Ti ricordiamo che: <br/>
                  1. Non si possono lasciare campi vuoti <br/>
                  2. La data deve corrispondere ad un giorno futuro <br/>
                  3. Il numero di posti disponibili deve essere almeno pari a 1 <br/>
                  Se il problema persiste contatta il team di EventEzy all'indirizzo eventezy@libero.it
                </p>
            </Alert>
            <input
              required
              type="text"
              className="form-control"
              onChange={(e) => handleChange(e)}
              name="nome"
              value={eventRequest.nome}
              placeholder="Nome" />
            <br/>
            <input
              required
              type="text"
              className="form-control"
              onChange={(e) => handleChange(e)}
              name="tipologia"
              value={eventRequest.tipologia}
              placeholder={evento.tipologia} />
            <br/>
            <input
              required
              type="text"
              className="form-control"
              onChange={(e) => handleChange(e)}
              name="indirizzo"
              value={eventRequest.indirizzo}
              placeholder={evento.indirizzo} />
            <br/>
            <input
              required
              type="text"
              className="form-control"
              onChange={(e) => handleChange(e)}
              name="dataEoraDate"
              value={eventRequest.dataEoraDate}
              placeholder={evento.dataEoraDate} />
            <br/>
            <input
              required
              type="number"
              className="form-control"
              onChange={(e) => handleChange(e)}
              name="maxPrenotati"
              value={eventRequest.maxPrenotati}
              placeholder={evento.maxPrenotati} />
        </Modal.Body>

        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseMod}>Chiudi</Button>
          <Button variant="outline-success" onClick={(e, id) => modificaEvento(e, evento.eventId)}>Salva</Button>
        </Modal.Footer>
      </Modal>
    </div>


    <div
      className="modal show"
      style={{ display: 'block', position: 'initial' }}
    >
      <Modal show={show}>
        <Modal.Header>
          <Modal.Title>Conferma Eliminazione</Modal.Title>
        </Modal.Header>

        <Modal.Body>
          <p>Sicuro di voler eliminare l'evento "{evento.nome}"?</p>
        </Modal.Body>

        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>Chiudi</Button>
          <Button variant="danger" onClick={(e, id) => eliminaEvento(e, evento.eventId)}>Conferma</Button>
        </Modal.Footer>
      </Modal>
    </div>

    </>
    )
  }
  
  export default Evento