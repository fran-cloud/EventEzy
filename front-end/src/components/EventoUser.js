const EventoUser = ({evento, effettuaPrenotazione}) => {
    return (
  <>
  <div className="card" style="width: 18rem;">
    <div className="card-body">
      <h5 className="card-title">{evento.nome}</h5>
      <h6 className="card-subtitle mb-2 text-body-secondary">{evento.tipologia}</h6>
      <p className="card-text">{evento.indirizzo}</p>
      <p className="card-text">{evento.dataEoraDate}</p>
      <p className="card-text">{evento.postiDisponibili}</p>
      <a onClick={(e, id) => effettuaPrenotazione(e, evento.eventId)} className="card-link">Card link</a>
    </div>
  </div>
  </>
  )
}

export default EventoUser