import React from 'react'
import {Navbar, Container, Nav} from "react-bootstrap"
import { useNavigate } from "react-router-dom";
import CreaEvento from './CreaEvento';
import Button from 'react-bootstrap/Button';


function NavBarOrg() {

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
        <a className="navbar-brand" href="#">EventEzy - Organizzatore</a>
        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            <li className="nav-item">
              <a className="nav-link active" aria-current="page" href="/homeOrg">Home</a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="#">Crea Evento</a>
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

export default NavBarOrg;