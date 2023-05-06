import { React, useState, useEffect, Fragment } from "react";
import Evento from "./Evento";

const EventList = ({ evento }) => {
  const USER_API_BASE_URL = "http://localhost:8080/organizations/get-all-organization-events";
  const USER_API_BASE_URL_2 = "http://localhost:8080/organizations/delete-event";
  const MODIFYEVENT_API_URL = "http://localhost:8080/organizations/modify-event";
  const [eventi, setEventi] = useState([]);
  const [loading, setLoading] = useState(true);
  const [eventId, setEventId] = useState(null);
  const [responseEvento, setResponseEvento] = useState(null);

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
        const response = await fetch(USER_API_BASE_URL+'/'+localStorage.getItem("utente"), {
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

  const eliminaEvento = (e, id) => {
    e.preventDefault();
    fetch(USER_API_BASE_URL_2  + "/" + id, {
      method: "DELETE",
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
      },
    }).then((res) => {
      if (eventi) {
        setEventi((prevElement) => {
          return prevElement.filter((evento) => evento.eventId !== id);
        });
      }
    });
  };


  const modificaEvento = async (e, id) => {
    e.preventDefault();
    const response = await fetch(MODIFYEVENT_API_URL, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
      },
      body: JSON.stringify(evento),
    });
    const _evento = await response.json();
    setResponseEvento(_evento);
  };

  return (
    <>
    <br/>
      <div className="container mx-auto my-8">
        <div className="flex shadow border-b">
          <table className="min-w-full">
            <thead className="bg-transparent">
              <tr>
                <th className="text-left font-medium text-gray-500 uppercase tracking-wide py-3 px-6"> Nome</th>
                <th className="text-left font-medium text-gray-500 uppercase tracking-wide py-3 px-6"> Tipologia</th>
                <th className="text-left font-medium text-gray-500 uppercase tracking-wide py-3 px-6"> Indirizzo</th>
                <th className="text-left font-medium text-gray-500 uppercase tracking-wide py-3 px-6"> Data e ora</th>
                <th className="text-right font-medium text-gray-500 uppercase tracking-wide py-3 px-2"> Posti ancora disponibili</th>
                <th className="text-right font-medium text-gray-500 uppercase tracking-wide py-3 px-2"> Posti totali</th>
                <th className="text-right font-medium text-gray-500 uppercase tracking-wide py-3 px-6"> Azioni</th>
              </tr>
            </thead>
            {!loading && (
              <tbody className="bg-white">
                {eventi.map((evento)  => (     // //Object.entries(eventi).map((evento)
                  <Evento
                    evento={evento}
                    key={evento.eventId}
                    eliminaEvento={eliminaEvento}
                    modificaEvento={modificaEvento}
                  />
                ))}
              </tbody>
            )}
          </table>
        </div>
      </div>
      
    </>

  );
};

export default EventList;