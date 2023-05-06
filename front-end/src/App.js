import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import EventEzy from './components/EventEzy';
import EventEzy2 from './components/EventEzy2';
import LoginOrg from './components/LoginOrg';
import LoginUser from './components/LoginUser';
import RegistrazioneOrg from './components/RegistrazioneOrg';
import RegistrazioneUser from './components/RegistrazioneUser';
import HomeOrg from './components/HomeOrg';
import HomeUser from './components/HomeUser';
import ReservationList from './components/ReservationList';


function App() {
  return (
    <>
    <BrowserRouter>
        <Routes>
          <Route index element={<EventEzy/>} />
          <Route path="/" element={<EventEzy/>} />
          <Route path="/registrazioneUser" element={<RegistrazioneUser/>}></Route>
          <Route path="/registrazioneOrg" element={<RegistrazioneOrg/>}></Route>
          <Route path="/loginUser" element={<LoginUser/>}></Route>
          <Route path="/loginOrg" element={<LoginOrg/>}></Route>
          <Route path="/homeOrg" element={<HomeOrg/>}></Route>
          <Route path="/homeUser" element={<HomeUser/>}></Route>
          <Route path="/prenotazioniUser" element={<ReservationList/>}></Route>
        </Routes>
    </BrowserRouter>
    </>
  );
}

export default App;
