import {Navbar, Container, Nav} from "react-bootstrap"
import { useNavigate } from "react-router-dom";
import Button from 'react-bootstrap/Button';
import SearchResult from './SearchResult';
import React, { useState } from "react";


function NavBarUser() {

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
        <a className="navbar-brand" href="#">EventEzy - Cliente</a>
        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            <li className="nav-item">
              <a className="nav-link active" aria-current="page" href="/homeUser">Home</a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="../prenotazioniUser">Prenotazioni Effettuate</a>
            </li>
            <li className="nav-item">
              <form class="d-flex">
                <a className="nav-link" href="../searchResult">Cerca evento</a>
              </form>
            </li>
          </ul>

          <form className="d-flex">
            <Button variant="danger" onClick={logout}>Logout</Button>
          </form>
        </div>
      </div>
    </nav>

    </>
  );
}

export default NavBarUser;