import React from "react";
import {
  Hero,
  Flex,
} from "react-landing-page";
import Button from 'react-bootstrap/Button';

const OKREG = () => {

  return (
    <>

    <Hero
      color="white"
      backgroundImage="https://www.infocilento.it/wp-content/uploads/2022/10/eventi-scaled.jpg"

      bg="black"
      bgOpacity={0.5}
    >

      <br/>
      <h3 className="mb-8 text-2xl text-center font-light">Ben fatto!</h3>
      <p>Controlla la tua email e clicca sul link per confermare l'account. Dopodiché potrai effettuare il login e usufruire delle funzionalità di EventEzy.</p>
      <br />
      <br />

    <Flex mt={3}>
      </Flex>
    </Hero>

    </>
  )
}
export default OKREG;