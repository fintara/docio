import React from 'react';
import logo from './logo.svg';
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
  return (
    <div>
      <span>This is users</span>
      <form>
        <input type="email" placeholder="email"></input>
        <button>Sign up</button>
      </form>
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
