import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import React, { useState, useEffect } from 'react';
import Form from 'react-bootstrap/Form';
import Prenotazione from './Prenotazione';

const Evento = ({evento, eliminaEvento, modificaEvento}) => {

    const GET_RESERATION_API = "http://localhost:8080/organizations/get-all-reservations";
    const [loading, setLoading] = useState(true);
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
    const [eventRequest, setEventRequest] = useState({
      nome: "",
      tipologia: "",
      indirizzo: "",
      dataEoraDate: "",
      maxPrenotati: "",
    });

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
    }, [prenotazioni, prenotazione]);

    const handleChange = (event) => {
      const value = event.target.value;
      setEventRequest({ ...evento, [event.target.name]: value });
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
        <div className="text-sm text-gray-500"> {evento.dataEoraDate}</div>
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
          <Modal.Title>Visualizza Prenotazioni</Modal.Title>
        </Modal.Header>

        <Modal.Body>
          <div className="container mx-auto my-8">
            <div className="flex shadow border-b">
              <table className="min-w-full">
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
                        key={prenotazione.reservationIdId}
                      />
                    ))}
                  </tbody>
                )}
              </table>
            </div>
          </div>
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
            <input
              required
              type="text"
              className="form-control"
              onChange={(e) => handleChange(e)}
              name="nome"
              value={evento.nome}
              placeholder={evento.nome} />
            <br/>
            <input
              required
              type="text"
              className="form-control"
              onChange={(e) => handleChange(e)}
              name="tipologia"
              value={evento.tipologia}
              placeholder={evento.tipologia} />
            <br/>
            <input
              required
              type="text"
              className="form-control"
              onChange={(e) => handleChange(e)}
              name="indirizzo"
              value={evento.indirizzo}
              placeholder={evento.indirizzo} />
            <br/>
            <input
              required
              type="text"
              className="form-control"
              onChange={(e) => handleChange(e)}
              name="dataEoraDate"
              value={evento.dataEoraDate}
              placeholder={evento.dataEoraDate} />
            <br/>
            <input
              required
              type="number"
              className="form-control"
              onChange={(e) => handleChange(e)}
              name="maxPrenotati"
              value={evento.maxPrenotati}
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
          <p>Sicuro di voler eliminare questo evento?</p>
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