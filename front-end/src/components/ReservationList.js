import { React, useState, useEffect } from "react";
import PrenotazioneUser from './PrenotazioneUser';
import { useNavigate } from "react-router-dom";
import Button from 'react-bootstrap/Button';

const ReservationList = () => {
    const USER_API_BASE_URL = "http://localhost:8080/users/get-all-reservations";
    const USER_API_BASE_URL_2 = "http://localhost:8080/users/delete-reservation";
    const [prenotazioni, setPrenotazioni] = useState(null);
    const [loading, setLoading] = useState(true);
    const [reservationId, setReservationId] = useState(null);
    const [responsePrenotazione, setResponsePrenotazione] = useState(null);
    const [prenotazione, setPrenotazione] = useState({
      reservationId: "",
      eventId: "",
      eventName: "",
      eventAddress: "",
      eventData: "",
      organizationEmail: "",
      utenteEmail: "",
    });

    useEffect(() => {
      const fetchData = async () => {
        setLoading(true);
        try {
          const response = await fetch(USER_API_BASE_URL+'/'+localStorage.getItem('utente'), {
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

    const eliminaPrenotazione = (e, id) => {
      e.preventDefault();
      fetch(USER_API_BASE_URL_2  + "/" + id, {
        method: "DELETE",
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
      }).then((res) => {
        if (prenotazioni) {
          setPrenotazioni((prevElement) => {
            return prevElement.filter((prenotazione) => prenotazione.reservationId !== id);
          });
        }
      });
    };

    const navigate = useNavigate()

    const logout = (e) =>{
        localStorage.removeItem('token');
        localStorage.removeItem('utente');
        navigate("/");
    }


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
            </ul>
            <form className="d-flex">
              <Button variant="danger" onClick={logout}>Logout</Button>
            </form>
          </div>
        </div>
      </nav>
      <br/>
        <div className="container mx-auto my-8">
          <div className="flex shadow border-b">
            <table className="min-w-full">
              <thead className="bg-transparent">
                <tr>
                  <th className="text-left font-medium text-gray-500 uppercase tracking-wide py-3 px-6"> Id</th>
                  <th className="text-left font-medium text-gray-500 uppercase tracking-wide py-3 px-6"> Nome</th>
                  <th className="text-left font-medium text-gray-500 uppercase tracking-wide py-3 px-6"> Indirizzo</th>
                  <th className="text-left font-medium text-gray-500 uppercase tracking-wide py-3 px-6"> Data e ora</th>
                  <th className="text-right font-medium text-gray-500 uppercase tracking-wide py-3 px-2"> Contatti</th>
                  <th className="text-right font-medium text-gray-500 uppercase tracking-wide py-3 px-6"> Azioni</th>
                </tr>
              </thead>
              {!loading && (
                <tbody className="bg-transparent">
                  { prenotazioni?.map((prenotazione) => (       // prenotazioni?.map((prenotazione)
                    <PrenotazioneUser
                      prenotazione={prenotazione}
                      key={prenotazione.reservationId}
                      eliminaPrenotazione={eliminaPrenotazione}
                    />
                  ))}
                </tbody>
              )}
            </table>
          </div>
        </div>

      </>
    );
}

export default ReservationList