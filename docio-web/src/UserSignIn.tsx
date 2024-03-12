import React, { FormEvent, useState } from 'react';

export function UserSignIn() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [submittedEmail, setSubmittedEmail] = useState(false);
    const [hidePasswordField, setHidePasswordField] = useState(true);

    function onSubmit(){
        setSubmittedEmail(true)
        setHidePasswordField(false)
    }

    return <div>
        <input type="email" placeholder="email" onChange={e => setEmail(e.target.value)} value={email}></input>
         <br />       
        {!submittedEmail &&
            <button type="button" onClick={onSubmit}>Submit</button>
        }
        <br />
        {!hidePasswordField &&
        <input type='password' placeholder='password' onChange={e => setPassword(e.target.value)} value={password}/>
        }
        <br />
        {!hidePasswordField &&
        <button type='button'>Sign in</button>
        }
        <br />
    </div>
}