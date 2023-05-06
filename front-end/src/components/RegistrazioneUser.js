import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import {
  Hero,
} from "react-landing-page";


const RegistrazioneUser = () =>{

const navigate = useNavigate()

const [utente, setUtente] = useState({
    nome: "",
    cognome: "",
    indirizzo: "",
    email: "",
    dataNascita: "",
    password: "",
});


const handleChange = (e) => {
    const value = e.target.value;
    setUtente({...utente, [e.target.name]: value});
};



const salvaUtente = async (e) => {
    e.preventDefault();
    const response = await fetch("http://localhost:8080/users/registration", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(utente),
    });
    const token = await response.json();
    localStorage.setItem('token', token.accessToken);
    localStorage.setItem('utente', utente.email);
    console.log("Ho salvato il token ");
    navigate("../homeUser");
}


return(
    <>

    <Hero
      color="white"
      backgroundImage="https://www.infocilento.it/wp-content/uploads/2022/10/eventi-scaled.jpg"
      bg="black"
      bgOpacity={0.5}
    >

    <div className="min-h-screen flex flex-col">
      <div className="container max-w-sm mx-auto flex-1 flex flex-col items-center justify-center px-2">
          <h1 className="mb-8 text-3xl text-center">Registrazione Utente</h1>
          <br/>
          <br/>
            <input
              required
              type="text"
              className="form-control"
              onChange={(e) => handleChange(e)}
              name="nome"
              value={utente.nome}
              placeholder="Nome" />
            <br/>
            <input
              required
              type="text"
              className="form-control"
              onChange={(e) => handleChange(e)}
              name="cognome"
              value={utente.cognome}
              placeholder="Cognome" />
            <br/>
            <input
              required
              type="text"
              className="form-control"
              onChange={(e) => handleChange(e)}
              name="indirizzo"
              value={utente.indirizzo}
              placeholder="Indirizzo" />
            <br/>
            <input
              required
              type="email"
              className="form-control"
              onChange={(e) => handleChange(e)}
              name="email"
              value={utente.email}
              placeholder="Email" />
            <br/>
            <input
              required
              type="date"
              className="form-control"
              onChange={(e) => handleChange(e)}
              name="dataNascita"
              value={utente.dataNascita}
              placeholder="Data di nascita" />
            <br/>
            <input
              required
              type="password"
              className="form-control"
              onChange={(e) => handleChange(e)}
              name="password"
              value={utente.password}
              placeholder="Password" />
            <br/>

      </div>
    </div>


          <Form.Group className="mb-3">
            <Form.Check
              required
              label="Accetta termini e condizioni di servizio"
              feedback="Devi accettare per continuare"
              feedbackType="invalid"
            />
          </Form.Group>
          <Button onClick={salvaUtente}>Registrati</Button>
       </Hero>
    </>
);
};
export default RegistrazioneUser;