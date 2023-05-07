const Prenotazione = ({prenotazione}) => {
    return (
  
      <tr key={prenotazione.reservationId}>
        <td className="text-left px-6 py-4 whitespace-nowrap">
          <div className="text-sm text-gray-500"> {prenotazione.reservationId}</div>
        </td>
        <td className="text-left px-6 py-4 whitespace-nowrap">
          <div className="text-sm text-gray-500"> {prenotazione.utenteEmail}</div>
        </td>
      </tr>
      
    )
  }
  
  export default Prenotazione