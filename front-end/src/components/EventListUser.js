import { React, useState, useEffect, Fragment } from "react";
import EventoUser from "./EventoUser";

const EventList = () => {
  const USER_API_BASE_URL = "http://localhost:8080/users/getAllEvents";
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


    useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await fetch(USER_API_BASE_URL, {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
          },
        });
        const eventi = await response.json();
        setEventi(eventi);
      } catch (error) {
        console.log(error);
      }
      setLoading(false);
    };
    fetchData();
  }, [evento, responseEvento]);


  return (
    <>
    <br/>
    <div className="container">
    <div className="row">
    {!loading && (
      <tbody className="bg-white">
      {eventi?.map((evento)  => (
        <div className="col">
        <EventoUser
         evento={evento}
         key={evento.eventId}
        />
        </div>
      ))}
      </tbody>
    )}
    </div>
    </div>
    </>

  );
};

export default EventList;