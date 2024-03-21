import React, { useState } from 'react';
import './App.css';

import {
  BrowserRouter as Router,
  Routes,
  Route,
  Link
} from "react-router-dom";
import { UserRegistration } from './UserRegistration';
import { UserSignIn } from './UserSignIn';

function Home(){
  return (
    <span>This is home</span>
  )
}

function About({token}:any){
  return (
    <span>This is about: {token}</span>
  )
}

function App() {
  const [token, setToken] = useState(null);
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
            <Link to="/signin">Sign in</Link>
          </ul>
        </nav>

        <Routes>
          <Route path="/about" element={<About token={token} />}/>
          <Route path="/users" element={<UserRegistration />}/>
          <Route path="/" element={ <Home />}/>
          <Route path="/signin" element={<UserSignIn onUserSignin={setToken}/>}/>
        </Routes>
      </div>
    </Router>
  );
}

export default App;
