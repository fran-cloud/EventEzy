import React from "react";
import {
  Hero,
  Flex,
} from "react-landing-page";
import Button from 'react-bootstrap/Button';

const EventEzy = () => {

  return (
    <>

    <Hero
      color="white"
      backgroundImage="https://www.infocilento.it/wp-content/uploads/2022/10/eventi-scaled.jpg"

      bg="black"
      bgOpacity={0.5}
    >
      <h1 className="mb-8 text-9xl text-center font-light">EventEzy </h1>
      <br/>
      <h5 className="mb-8 text-2xl text-center font-light">Benvenuto in EventEzy, l'app tutta italiana per la gestione degli eventi. Gestisci i tuoi eventi!</h5>
      <p>Puoi accedere come Organizzatore per creare e pubblicizzare eventi o come Cliente per prendere parte a quelli che reputi più interessanti</p>
      <br />
      <br />
      <div>
        <Button variant="outline-secondary" href="../loginUser">Accedi come Cliente</Button>{' '}
        <Button variant="outline-secondary" href="../loginOrg">Accedi come Organizzatore</Button>
      </div>

    <Flex mt={3}>
      </Flex>
    </Hero>

    </>
  )
}
export default EventEzy;