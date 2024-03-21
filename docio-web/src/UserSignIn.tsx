import React, { FormEvent, useState } from 'react';

export function UserSignIn({onUserSignin}:any) {

    const [message, setMessage] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [submittedEmail, setSubmittedEmail] = useState(false);
    const [hidePasswordField, setHidePasswordField] = useState(true);

    function onSubmit(){
        setSubmittedEmail(true);
        setHidePasswordField(false);
        fetch('/api/auth/init', {
            method: 'POST',
            headers: {
              'Content-type': 'application/json',
            },
            body: JSON.stringify({
              email,
            })})
            .then((res) => {
              console.log(res);
              if (res.status !== 200) {
                setMessage("Something went wrong!"); 
                setSubmittedEmail(false);
                setHidePasswordField(true);
              }
            });
    }

    function onSignIn(){
      fetch('/api/auth/signin', {
          method: 'POST',
          headers: {
            'Content-type': 'application/json',
          },
          body: JSON.stringify({
            email,
            code: password,
          })
      }).then((res) => {
        console.log(res);
        if (res.status !== 200) {
          setMessage("Something went wrong!"); 
        }else{
          return res.text()
        }
      }).then((token)=>{
        if(token){
          onUserSignin(token);
        }
      });
    }

    return <div>
        <input type="email" placeholder="email" onChange={e => setEmail(e.target.value)} value={email}></input>
         <br />       
        {!submittedEmail &&
            <button type="button" onClick={onSubmit}>Submit</button>
        }
        <br />
        <p>{message}</p>
        {!hidePasswordField &&
        <input type='password' placeholder='password' onChange={e => setPassword(e.target.value)} value={password}/>
        }
        <br />
        {!hidePasswordField &&
        <button type='button' onClick={onSignIn}>Sign in</button>
        }
        <br />
    </div>
}