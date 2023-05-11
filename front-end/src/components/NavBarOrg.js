import { React, Fragment, useState } from "react";
import {Navbar, Container, Nav} from "react-bootstrap"
import { useNavigate } from "react-router-dom";
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import EventList from './EventList';
import Alert from 'react-bootstrap/Alert';


function NavBarOrg() {

const navigate = useNavigate()

  const USER_API_BASE_URL = "http://localhost:8080/organizations/create-event";
  const [isOpen, setIsOpen] = useState(false);
  const [isOpenValid, setIsOpenValid] = useState(false);
  const [evento, setEvento] = useState({
    eventId: "",
    nome: "",
    tipologia: "",
    indirizzo: "",
    dataEoraDate: "",
    organizationEmail: "",
    maxPrenotati: "",
    postiDisponibili: "",
  });
  const [responseEvento, setResponseEvento] = useState({
   eventId: "",
    nome: "",
    tipologia: "",
    indirizzo: "",
    dataEoraDate: "",
    organizationEmail: "",
    maxPrenotati: "",
    postiDisponibili: "",
  });

  function closeModal() {
    setIsOpen(false);
  }

  function openModal() {
    setIsOpen(true);
  }

const logout = (e) =>{
    localStorage.removeItem('token');
    localStorage.removeItem('utente');
    navigate("/");
}


  const handleChange = (event) => {
    const value = event.target.value;
    setEvento({ ...evento, [event.target.name]: value });
  };

  const salvaEvento = async (e) => {
    e.preventDefault();
    const response = await fetch(USER_API_BASE_URL+'/'+localStorage.getItem('utente'), {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": 'Bearer '+localStorage.getItem("token"),
      },
      body: JSON.stringify(evento),
    });
    const _evento = await response.json();
    setResponseEvento(_evento);
    reset(e);
    if(_evento.status==400){
        setIsOpenValid(true);
    }
  };

  const reset = (e) => {
    e.preventDefault();
    setEvento({
    eventId: "",
    nome: "",
    organizationEmail: "",
    descrizione: "",
    indirizzo: "",
    dataEoraDate: "",
    maxPrenotati: "",
    });
    setIsOpen(false);
  };

return (
    <>

    <nav className="navbar navbar-expand-lg bg-body-tertiary">
      <div className="container-fluid">
        <a className="navbar-brand" href="#">EventEzy - Organizzatore</a>
        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            <li className="nav-item">
              <a className="nav-link active" aria-current="page" href="/homeOrg">Home</a>
            </li>
            <li className="nav-item">
              <Button variant="outline-success" onClick={openModal}> Crea Evento</Button>
            </li>
          </ul>
          <form className="d-flex">
            <Button variant="danger" onClick={logout}>Logout</Button>
          </form>
        </div>
      </div>
    </nav>

    <Alert variant="danger" onClose={() => setIsOpenValid(false)} show={isOpenValid} dismissible>
      <Alert.Heading>Qualcosa non va!</Alert.Heading>
        <p>
          I dati inseriti non sono validi. Ti ricordiamo che: <br/>
          1. Non si possono lasciare campi vuoti <br/>
          2. La data deve corrispondere ad un giorno futuro <br/>
          3. Il numero di posti disponibili deve essere almeno pari a 1 <br/>
          Se il problema persiste contatta il team di EventEzy all'indirizzo eventezy@libero.it
        </p>
    </Alert>

    <div
      className="modal show"
      style={{ display: 'block', position: 'initial' }}
    >
      <Modal show={isOpen}>
        <Modal.Header>
          <Modal.Title>Crea Evento</Modal.Title>
        </Modal.Header>

        <Modal.Body>
            <input
              required
              type="text"
              className="form-control"
              onChange={(e) => handleChange(e)}
              name="nome"
              value={evento.nome}
              placeholder="Nome" />
            <br/>
            <input
              required
              type="text"
              className="form-control"
              onChange={(e) => handleChange(e)}
              name="tipologia"
              value={evento.tipologia}
              placeholder="Tipologia (Concerto/Seminario/Convegno...)" />
            <br/>
            <input
              required
              type="text"
              className="form-control"
              onChange={(e) => handleChange(e)}
              name="indirizzo"
              value={evento.indirizzo}
              placeholder="Indirizzo" />
            <br/>
            <input
              required
              type="text"
              className="form-control"
              onChange={(e) => handleChange(e)}
              name="dataEoraDate"
              value={evento.dataEoraDate}
              placeholder="Data e ora (yyyy-mm-ddT00:00)" />
            <br/>
            <input
              required
              type="number"
              className="form-control"
              onChange={(e) => handleChange(e)}
              name="maxPrenotati"
              value={evento.maxPrenotati}
              placeholder="Posti disponibili" />

        </Modal.Body>

        <Modal.Footer>
          <Button variant="secondary" onClick={closeModal}>Chiudi</Button>
          <Button variant="outline-success" onClick={salvaEvento}>Salva</Button>
        </Modal.Footer>
      </Modal>
    </div>

    <EventList evento={responseEvento} />
    </>
  );
}

export default NavBarOrg;