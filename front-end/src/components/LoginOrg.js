import React, { useState } from 'react'
import { useNavigate } from "react-router-dom";
import {
  Hero,
} from "react-landing-page";
import Button from 'react-bootstrap/Button';

const LoginOrg = () => {

    const navigate = useNavigate()
    
    const [utente, setUtente] = useState({
        email: "",
        password: "",
    });

    const [errore, setErrore] = useState({
        messaggio: "",
    });
    
    const handleChange = (e) => {
        const value = e.target.value;
        setUtente({...utente, [e.target.name]: value});
    };

    const loginUtente = async (e) => {
        e.preventDefault();
        const response = await fetch("http://localhost:8080/organizations/login", {
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
            navigate("../homeOrg");
        }
    }
    
    return (


    <>
        <Hero
          color="white"
          backgroundImage="https://www.infocilento.it/wp-content/uploads/2022/10/eventi-scaled.jpg"
          bg="black"
          bgOpacity={0.5}
        >
            <div className="min-h-screen flex flex-col">
                <div className="container max-w-sm mx-auto flex-1 flex flex-col items-center justify-center px-2">

                        <h1 className="mb-form-control8 text-3xl text-center">Login Organizzatore</h1>
                        <br />
                        <input
                            type="email"
                            className="form-control"
                            onChange={(e) => handleChange(e)}
                            name="email"
                            value={utente.email}
                            placeholder="Email" />
                        <br />
                        <input
                            type="password"
                            className="form-control"
                            onChange={(e) => handleChange(e)}
                            name="password"
                            value={utente.password}
                            placeholder="Password" />
                        <br />
                        <Button onClick={loginUtente}>Accedi</Button>

                        <div
                            type="text"
                            className="text-red-500"
                        >{errore.messaggio} </div>
                    </div>
                    <br />
                    <div className="text-grey-dark mt-6">
                      Non hai un account?{' '}
                      <a className="no-underline border-b border-blue-600 text-blue-600" href="../registrazioneOrg/">
                        Registrati
                      </a>.

                </div>
            </div>
        </Hero>
    </>
  )
}

export default LoginOrg;