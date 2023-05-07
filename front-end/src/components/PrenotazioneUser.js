import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import React, { useState, useEffect } from 'react';

const PrenotazioneUser = ({prenotazione, eliminaPrenotazione}) => {

    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);


    return (
    <>
      <tr key={prenotazione.reservationId}>
        <td className="text-left px-6 py-4 whitespace-nowrap">
        <div className="text-sm text-gray-500"> {prenotazione.reservationId}</div>
      </td>
      <td className="text-left px-6 py-4 whitespace-nowrap">
        <div className="text-sm text-gray-500"> {prenotazione.eventName}</div>
      </td>
      <td className="text-left px-6 py-4 whitespace-nowrap">
        <div className="text-sm text-gray-500"> {prenotazione.eventAddress}</div>
      </td>
      <td className="text-left px-6 py-4 whitespace-nowrap">
        <div className="text-sm text-gray-500"> {prenotazione.eventData}</div>
      </td>
      <td className="text-left px-12 py-4 whitespace-nowrap">
        <div className="text-sm text-gray-500"> {prenotazione.organizationEmail}</div>
      </td>
      <td className="text-right px-6 py-4 whitespace-nowrap">
          <Button variant="outline-danger" onClick={handleShow}> Elimina</Button>
          </td>
  </tr>


  <div
        className="modal show"
        style={{ display: 'block', position: 'initial' }}
      >
        <Modal show={show}>
          <Modal.Header>
            <Modal.Title>Conferma Eliminazione</Modal.Title>
          </Modal.Header>

          <Modal.Body>
            <p>Sicuro di voler eliminare questa prenotazione?</p>
          </Modal.Body>

          <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>Chiudi</Button>
            <Button variant="danger" onClick={(e, id) => eliminaPrenotazione(e, prenotazione.reservationId)}>Conferma</Button>
          </Modal.Footer>
        </Modal>
      </div>
    </>
    )
  }

  export default PrenotazioneUser