import { React, useState, useEffect, Fragment } from "react";
import EventoUser from "./EventoUser";

const EventList = ({ evento }) => {
  const USER_API_BASE_URL = "http://localhost:8080/users/getAllEvents";
  const EFFETTUA_PRENOTAZIONE = "http://localhost:8080/users/create-reservation"
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

  const fetchData = () => {
        fetch(USER_API_BASE_URL+'/'+encodeURIComponent(localStorage.getItem("utente")), {method: "GET", headers: {"Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem('token')}`,}})
          .then(response => {
            return response.json()
          })
          .then(data => {
            setEventi(data)
          })
      }

      useEffect(() => {
        fetchData()
      }, [])

    /*useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await fetch(USER_API_BASE_URL+'/'+encodeURIComponent(localStorage.getItem("utente")), {
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
  }, [evento, responseEvento]);*/

  const effettuaPrenotazione = async (e) => {
    e.preventDefault();
    const response = await fetch(EFFETTUA_PRENOTAZIONE + "/" + encodeURIComponent(localStorage.getItem("utente")), {
      method: "POST",
      headers: {
      "Content-Type": "application/json",
      'Authorization': `Bearer ${localStorage.getItem('token')}`,
      },
      body: JSON.stringify(evento.eventId),
    });
    const _prenotazione = await response.json();
    setPrenotazione(_prenotazione);
  };

  return (
    <>
    <br/>

    {!loading && (
      <tbody className="bg-white">
      {eventi?.map((evento)  => (     // //Object.entries(eventi).map((evento)
        <EventoUser
         evento={evento}
         key={evento.eventId}
         effettuaPrenotazione={effettuaPrenotazione}
        />
      ))}
      </tbody>
    )}

    </>

  );
};

export default EventList;