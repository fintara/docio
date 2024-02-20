import React, { FormEvent, useState } from 'react';
import './App.css';

import {
  BrowserRouter as Router,
  Routes,
  Route,
  Link
} from "react-router-dom";

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
    })
    .then((res)=>{
      console.log(res);
      if(res.status === 200){ 
      //=== has value and type; value1 === value2 eg: 
      //   v1 === v2 => type(v1) == type(v2) && v1 == v2 
      //   "200" == 200 // true
      //   "200" === 200 // false
      return res.json()}
      else{
        return res.text()
      }
    })
    .then((res)=>{setMessage(JSON.stringify(res))})
    .catch((error)=>{setMessage(error.toString())});
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
