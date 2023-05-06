import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import {
  Hero,
} from "react-landing-page";


const RegistrazioneOrg = () =>{

const navigate = useNavigate()

const [utente, setUtente] = useState({
    organizationName: "",
    organizationAddress: "",
    email: "",
    partitaIva: "",
    password: "",
});


const handleChange = (e) => {
    const value = e.target.value;
    setUtente({...utente, [e.target.name]: value});
};



const salvaOrganizzazione = async (e) => {
    e.preventDefault();
    const response = await fetch("http://localhost:8080/organizations/registration", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(utente),
    });
    const token = await response.json();
    localStorage.setItem('token', token.access_token);
    localStorage.setItem('utente', utente.email);
    console.log("Ho salvato il token ");
    navigate("../homeOrg");
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
          <h1 className="mb-8 text-3xl text-center">Registrazione Organizzatore</h1>
          <br/>
          <br/>
            <input
              required
              type="text"
              className="form-control"
              onChange={(e) => handleChange(e)}
              name="organizationName"
              value={utente.organizationName}
              placeholder="Nome" />
            <br/>
            <input
              required
              type="text"
              className="form-control"
              onChange={(e) => handleChange(e)}
              name="organizationAddress"
              value={utente.organizationAddress}
              placeholder="Località (sede principale)" />
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
              type="text"
              className="form-control"
              onChange={(e) => handleChange(e)}
              name="partitaIva"
              value={utente.partitaIva}
              placeholder="Partita IVA" />
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
          <Button onClick={salvaOrganizzazione}>Registrati</Button>
       </Hero>
    </>
);
};
export default RegistrazioneOrg;