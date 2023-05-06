import { Dialog, Transition } from "@headlessui/react";
import React from "react";
import { Fragment, useState } from "react";
import EventList from './EventList';
import axios from "axios";
import Button from 'react-bootstrap/Button';

const CreaEvento = () => {
  const USER_API_BASE_URL = "http://localhost:8080/organizations/create-event";
  const [isOpen, setIsOpen] = useState(false);
  const [evento, setEvento] = useState({
    eventId: "",
    nome: "",
    tipologia: "",
    indirizzo: "",
    dataEoraDate: "",
    organizationEmail: "",
    maxPrenotati: "",
    postiDisponibili: "",
  });
  const [responseEvento, setResponseEvento] = useState({
   eventId: "",
    nome: "",
    tipologia: "",
    indirizzo: "",
    dataEoraDate: "",
    organizationEmail: "",
    maxPrenotati: "",
    postiDisponibili: "",
  });

  function closeModal() {
    setIsOpen(false);
  }

  function openModal() {
    setIsOpen(true);
  }

  const handleChange = (event) => {
    const value = event.target.value;
    setEvento({ ...evento, [event.target.name]: value });
  };

  const salvaEvento = async (e) => {
    e.preventDefault();
    const response = await fetch(USER_API_BASE_URL, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": 'Bearer '+localStorage.getItem("token"),
      },
      body: JSON.stringify(evento),
    });
    const _evento = await response.json();
    setResponseEvento(_evento);
    reset(e);
  };

  const reset = (e) => {
    e.preventDefault();
    setEvento({
    eventId: "",
    nome: "",
    organizationEmail: "",
    descrizione: "",
    indirizzo: "",
    dataEoraDate: "",
    maxPrenotati: "",
    });
    setIsOpen(false);
  };

  return (
    <>
    <br/>
      <div className="container mx-auto my-8">
        <div className="h-12">
          <Button
            onClick={openModal}
            className="rounded bg-slate-600 text-white px-6 py-2 font-semibold">
            Crea evento
          </Button>
        </div>
      </div>
      <Transition appear show={isOpen} as={Fragment}>
        <Dialog
          as="div"
          className="fixed inset-0 z-10 overflow-y-auto"
          onClose={closeModal}>
          <div className="min-h-screen px-4 text-center">
            <Transition.Child
              as={Fragment}
              enter="ease-out duration-300"
              enterFrom="opacity-0 scale-95"
              enterTo="opacity-100 scale-100"
              leave="ease-in duration-200"
              leaveFrom="opacity-100 scale-100"
              leaveTo="opacity-0 scale-95">
              <div className="inline-block w-full max-w-md p-6 my-8 overflow-hidden text-left align-middle transition-all transform bg-white shadow-xl rounded-md">
                <Dialog.Title
                  as="h3"
                  className="text-lg font-medium leading-6 text-gray-900">
                  Crea nuovo evento
                </Dialog.Title>
                <div className="flex max-w-md max-auto">
                  <div className="py-2">
                    <div className="h-14 my-4">
                      <label className="block text-gray-600 text-sm font-normal">
                        Nome
                      </label>
                      <input
                        type="text"
                        name="nome"
                        value={evento.nome}
                        onChange={(e) => handleChange(e)}
                        className="h-10 w-96 border mt-2 px-2 py-2"></input>
                    </div>
                    <div className="h-14 my-4">
                      <label className="block text-gray-600 text-sm font-normal">
                        Tipologia
                      </label>
                      <input
                        type="text"
                        name="Tipologia"
                        value={evento.tipologia}
                        onChange={(e) => handleChange(e)}
                        className="h-10 w-96 border mt-2 px-2 py-2"></input>
                    </div>
                    <div className="h-14 my-4">
                      <label className="block text-gray-600 text-sm font-normal">
                        Indirizzo
                      </label>
                      <input
                        type="text"
                        name="indirizzo"
                        value={evento.indirizzo}
                        onChange={(e) => handleChange(e)}
                        className="h-10 w-96 border mt-2 px-2 py-2"></input>
                    </div>
                    <div className="h-14 my-4">
                      <label className="block text-gray-600 text-sm font-normal">
                        Data e ora (yyyy-MM-dd HH:mm:ss)
                      </label>
                      <input
                        type="date"
                        name="dataEoraDate"
                        value={evento.dataEoraDate}
                        onChange={(e) => handleChange(e)}
                        className="h-10 w-96 border mt-2 px-2 py-2"></input>
                    </div>
                    <div className="h-14 my-4">
                      <label className="block text-gray-600 text-sm font-normal">
                        Numero max di prenotazioni
                      </label>
                      <input
                        type="number"
                        name="maxPrenotati"
                        value={evento.maxPrenotati}
                        onChange={(e) => handleChange(e)}
                        className="h-10 w-96 border mt-2 px-2 py-2"></input>
                    </div>
                    <div className="h-14 my-4">
                      <label className="block text-gray-600 text-sm font-normal">
                        Posti disponibili
                      </label>
                      <input
                        type="number"
                        name="maxPrenotati"
                        value={evento.postiDisponibili}
                        onChange={(e) => handleChange(e)}
                        className="h-10 w-96 border mt-2 px-2 py-2"></input>
                    </div>
                    <div className="h-14 my-4 space-x-4 pt-4">
                      <button
                        onClick={salvaEvento}
                        className="rounded text-white font-semibold bg-green-400 hover:bg-green-700 py-2 px-6">
                        Save
                      </button>
                      <button
                        onClick={reset}
                        className="rounded text-white font-semibold bg-red-400 hover:bg-red-700 py-2 px-6">
                        Close
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </Transition.Child>
          </div>
        </Dialog>
      </Transition>
      <EventList evento={responseEvento} />
    </>
  );
};

export default CreaEvento;