import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import {
  Hero,
} from "react-landing-page";
import Alert from 'react-bootstrap/Alert';


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

const [isOpen, setIsOpen] = useState(false);

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

    if(token.access_token!=null){
        navigate("../okreg");
    }
    else{
        navigate("../registrazioneOrg");
        setIsOpen(true);
    }

}



return(
    <>

    <Hero
      color="white"
      backgroundImage="https://www.infocilento.it/wp-content/uploads/2022/10/eventi-scaled.jpg"
      bg="black"
      bgOpacity={0.5}
    >

    <Alert variant="danger" show={isOpen}>
      <Alert.Heading>Qualcosa non va</Alert.Heading>
      <p>
        Controlla che i dati inseriti siano corretti. Ti ricordiamo che: <br/> 1. Occorre necessariamente inserire tutti i campi <br/>
        2. L'email deve essere valida <br/> 3. La partita IVA deve essere valida <br/>
        4. La password deve essere almeno di 8 caratteri e contenere almeno una cifra e un carattere
        speciale <br/> 5. Devi accettare termini e condizioni spuntando la casella in basso
      </p>
    </Alert>


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