import React, { FormEvent, useState } from 'react';
import logo from './logo.svg';
import './App.css';

import {
  BrowserRouter as Router,
  Routes,
  Route,
  Link
} from "react-router-dom";
import { error } from 'console';

function Home(){
  return (
    <span>This is home</span>
  )
}

function About(){
  return (
    <span>This is about</span>
  )
}

function Users(){

  const [message, setMessage] = useState("")
  const [email, setEmail] = useState("")

  function OnSubmit(e:FormEvent<HTMLFormElement>){
    e.preventDefault()
    console.log("Hello", email)
    fetch('/api/auth/signup',{
      method: 'POST',
      headers:{
        'Content-type': 'application/json',
      }, 
      body:JSON.stringify({
        email,
      }),
    }).then((res)=>res.json()).then((res)=>{setMessage(JSON.stringify(res))}).catch((error)=>{setMessage(error.toString())});
  }
  return (
    <div>
      <span>This is users</span>
      <form onSubmit={OnSubmit}>
        <input type="email" placeholder="email" onChange={e=>setEmail(e.target.value)} value={email}></input>
        <button type="submit">Sign up</button>
      </form>
      <p>{message}</p>
    </div>
  )
}

function App() {
  return (
    <Router>
      <div>
        <nav>
          <ul>
            <li>
              <Link to="/">Home</Link>
            </li>
            <li>
              <Link to="/about">About</Link>
            </li>
            <li>
              <Link to="/users">Sign up</Link>
            </li>
          </ul>
        </nav>

        <Routes>
          <Route path="/about" element={<About />}/>
          <Route path="/users" element={<Users />}/>
          <Route path="/" element={ <Home />}/>
        </Routes>
      </div>
    </Router>
  );
}

export default App;
