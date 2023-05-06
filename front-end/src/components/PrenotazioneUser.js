const PrenotazioneUser = ({prenotazione, eliminaPrenotazione}) => {
    return (

      <tr key={prenotazione.reservationId}>
        <td className="text-left px-6 py-4 whitespace-nowrap">
        <div className="text-sm text-gray-500"> {prenotazione.reservationId}</div>
      </td>
      <td className="text-left px-6 py-4 whitespace-nowrap">
        <div className="text-sm text-gray-500"> {prenotazione.eventName}</div>
      </td>
      <td className="text-left px-6 py-4 whitespace-nowrap">
        <div className="text-sm text-gray-500"> {prenotazione.eventAddress}</div>
      </td>
      <td className="text-left px-6 py-4 whitespace-nowrap">
        <div className="text-sm text-gray-500"> {prenotazione.eventData}</div>
      </td>
      <td className="text-left px-12 py-4 whitespace-nowrap">
        <div className="text-sm text-gray-500"> {prenotazione.organizationEmail}</div>
      </td>
      <td className="text-right px-6 py-4 whitespace-nowrap">
          <a onClick={(e, id) => eliminaPrenotazione(e, prenotazione.reservationId)} className="text-indigo-600 hover:text-indigo-800 hover:cursor-pointer">
            Elimina
          </a>
          </td>
  </tr>

    )
  }

  export default PrenotazioneUser