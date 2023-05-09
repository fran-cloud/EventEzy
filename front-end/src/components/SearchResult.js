import { React, useState, useEffect, Fragment } from "react";
import EventoUser from "./EventoUser";
import Button from 'react-bootstrap/Button';
import { useNavigate } from "react-router-dom";

const SearchResult = () => {

const navigate = useNavigate()

  const USER_API_BASE_URL = "http://localhost:8080/users/searchEvent";
  const EFFETTUA_PRENOTAZIONE = "http://localhost:8080/users/create-reservation";
  const [eventi, setEventi] = useState(null);
  const [loading, setLoading] = useState(true);
  const [eventId, setEventId] = useState(null);
  const [responseEvento, setResponseEvento] = useState(null);
  const [prenotazione, setPrenotazione] = useState({
    reservationId: "",
    eventId: "",
    eventName: "",
    eventAddress: "",
    eventData: "",
    organizationEmail: "",
    utenteEmail: "",
  });
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

var txt;

const handleChange = (e) => {
    const value = e.target.value;
    txt = value
    localStorage.setItem('txt', txt);
};

const logout = (e) =>{
    localStorage.removeItem('token');
    localStorage.removeItem('utente');
    navigate("/");
}


useEffect(() => {
        const fetchData = async () => {
          setLoading(true);
          try {
            const response = await fetch(USER_API_BASE_URL+'/?txt='+localStorage.getItem('txt'), {
              method: "GET",
              headers: {
                "Content-Type": "application/json",
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
              },
            });
            const eventi = await response.json();
            setEventi(eventi);
            localStorage.removeItem('txt');
          } catch (error) {
            console.log(error);
          }
          setLoading(false);
        };
        fetchData();
}, [evento, responseEvento]);



  return (
    <>
    <nav className="navbar navbar-expand-lg bg-body-tertiary">
          <div className="container-fluid">
            <a className="navbar-brand" href="#">EventEzy - Utente</a>
            <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
              <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse" id="navbarSupportedContent">
              <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                <li className="nav-item">
                  <a className="nav-link active" aria-current="page" href="../homeUser">Home</a>
                </li>
                <li className="nav-item">
                  <a className="nav-link" href="../prenotazioniUser">Prenotazioni Effettuate</a>
                </li>
                <li className="nav-item">
                  <form class="d-flex">
                    <input class="form-control me-2" type="search" placeholder="Cerca Evento" aria-label="Search" name="txt" onChange={(e) => handleChange(e)}></input>
                    <button class="btn btn-outline-success" href="/searchResult">Cerca</button>
                  </form>
                </li>
              </ul>

              <form className="d-flex">
                <Button variant="danger" onClick={logout}>Logout</Button>
              </form>
            </div>
          </div>
        </nav>
    <br/>
    <div className="container">
    {!loading && (
      <tbody className="bg-white">
      {eventi?.map((evento)  => (
        <EventoUser
         evento={evento}
         key={evento.eventId}
        />
      ))}
      </tbody>
    )}
    </div>
    </>

  );
};

export default SearchResult;