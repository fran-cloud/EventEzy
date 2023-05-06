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
      nome: "",
      indirizzo: "",
      dataEoraDate: "",
      organizationEmail: "",
      utenteEmail: "",
    });
    const [prenotazioni, setPrenotazioni] = useState(null);

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
          const response = await fetch(GET_RESERATION_API, {
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
      setPrenotazione({ ...prenotazione, [prenotazione.target.name]: value });
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
        <Button variant="danger" onClick={handleShowPren}> Visualizza Prenotazioni</Button>
      </td>
      <td className="text-right px-6 py-4 whitespace-nowrap">
        <Button variant="danger" onClick={handleShowMod}> Modifica</Button>
      </td>
      <td className="text-right px-6 py-4 whitespace-nowrap">
        <Button variant="danger" onClick={handleShow}> Elimina</Button>
      </td>
    </tr>

    //Modal Visualizza Prenotazioni
    <div
      className="modal show"
      style={{ display: 'block', position: 'initial' }}
    >
      <Modal.Dialog>
        <Modal.Header closeButton>
          <Modal.Title>Visualizza Prenotazioni</Modal.Title>
        </Modal.Header>

        <Modal.Body>
          <div className="container mx-auto my-8">
            <div className="flex shadow border-b">
              <table className="min-w-full">
                <thead className="bg-transparent">
                  <tr>
                    <th className="text-left font-medium text-gray-500 uppercase tracking-wide py-3 px-6"> #</th>
                    <th className="text-left font-medium text-gray-500 uppercase tracking-wide py-3 px-6"> Email Utente</th>
                  </tr>
                </thead>
                {!loading && (
                  <tbody className="bg-white">
                    {prenotazioni?.map((prenotazione)  => (     // //Object.entries(eventi).map((evento)
                      <Prenotazione
                        prenotazione={prenotazione}
                        key={prenotazione.prenotazioneId}
                      />
                    ))}
                  </tbody>
                )}
              </table>
            </div>
          </div>
        </Modal.Body>

        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>Chiudi</Button>
          <Button variant="danger" onClick={(e, id) => eliminaEvento(e, evento.eventId)}>Conferma</Button>
        </Modal.Footer>
      </Modal.Dialog>
    </div>

    //Modal Modifica
    <div
      className="modal show"
      style={{ display: 'block', position: 'initial' }}
    >
      <Modal.Dialog>
        <Modal.Header closeButton>
          <Modal.Title>Modifica Evento</Modal.Title>
        </Modal.Header>

        <Modal.Body>
          <Form.Group md="4" controlId="validationCustom01">
            <Form.Label>Nome</Form.Label>
              <Form.Control
                required
                type="text"
                onChange={(e) => handleChange(e)}
                placeholder="Nome"
                value={evento.nome}
                default={evento.nome}
              />
            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
          </Form.Group>
          <Form.Group md="4" controlId="validationCustom02">
            <Form.Label>Tipologia</Form.Label>
              <Form.Control
                required
                type="text"
                onChange={(e) => handleChange(e)}
                placeholder="Tipologia"
                required
                value={evento.tipologia}
                default={evento.tipologia}
              />
            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
          </Form.Group>
          <Form.Group md="4" controlId="validationCustom03">
            <Form.Label>Indirizzo</Form.Label>
              <Form.Control
                required
                type="text"
                onChange={(e) => handleChange(e)}
                placeholder="Indirizzo"
                required
                value={evento.indirizzo}
                default={evento.indirizzo}
              />
            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
          </Form.Group>
          <Form.Group md="4" controlId="validationCustom04">
            <Form.Label>Data e ora</Form.Label>
              <Form.Control
                type="date"
                onChange={(e) => handleChange(e)}
                placeholder="Data"
                required
                value={evento.dataEoraDate}
                default={evento.dataEoraDate}
              />
              <Form.Control.Feedback type="invalid">
                Inserisci la data
              </Form.Control.Feedback>
          </Form.Group>
          <Form.Group md="4" controlId="validationCustom05">
            <Form.Label>Data e ora</Form.Label>
              <Form.Control
                type="number"
                onChange={(e) => handleChange(e)}
                placeholder="Posti totali"
                required
                value={evento.maxPrenotati}
                default={evento.maxPrenotati}
              />
              <Form.Control.Feedback type="invalid">
                Inserisci il numero totale di posti
              </Form.Control.Feedback>
          </Form.Group>
        </Modal.Body>

        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseMod}>Chiudi</Button>
          <Button variant="danger" onClick={(e, id) => modificaEvento(e, evento.eventId)}>Conferma</Button>
        </Modal.Footer>
      </Modal.Dialog>
    </div>

    //Modal Elimina
    <div
      className="modal show"
      style={{ display: 'block', position: 'initial' }}
    >
      <Modal.Dialog>
        <Modal.Header closeButton>
          <Modal.Title>Conferma Eliminazione</Modal.Title>
        </Modal.Header>

        <Modal.Body>
          <p>Sicuro di voler eliminare questo evento?</p>
        </Modal.Body>

        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>Chiudi</Button>
          <Button variant="danger" onClick={(e, id) => eliminaEvento(e, evento.eventId)}>Conferma</Button>
        </Modal.Footer>
      </Modal.Dialog>
    </div>

    </>
    )
  }
  
  export default Evento