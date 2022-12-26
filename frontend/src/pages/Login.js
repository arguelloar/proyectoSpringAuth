import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Login() {
    
    let navigate = useNavigate();

    const [login, setLogin] = useState({
        email:"",
        password:""
    });

    const {email,password} = login;


    const onInputChange = (e) => {
        setLogin({...login,[e.target.name]: e.target.value});
    };

    let statusCode;

    async function userLogin(){
        const response = await fetch("http://localhost:8080/api/auth/login", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                 body: JSON.stringify(login)
            });
            statusCode = response.status;
            if(statusCode == 200){
                navigate("/");
            }
            return response.json();
    }

    const onSubmit = (e) => {
        e.preventDefault();
        userLogin();
    }

  return (
    <div className="container-fluid text-center">
        <form className="mt-5 row justify-content-around" onSubmit={(e) => onSubmit(e)}>
            <div className="col-lg-7 form-outline mb-4">
                <input type="email" name="email" id="form2Example1" className="form-control" value={email} onChange={(e) => onInputChange(e)} />
                <label className="form-label" for="form2Example1">Email address</label>
            </div>

            <div className="form-outline mb-4 col-lg-7">
                <input type="password" name="password" id="form2Example2" className="form-control" value={password} onChange={(e) => onInputChange(e)} />
                <label className="form-label" for="form2Example2">Password</label>
            </div>

            <div className="row mb-4 col-lg-6">
            <div>
            <a href="#!">Forgot password?</a>
            </div>
            </div>
            <div className="col-12"><button type="submit" className="btn btn-primary btn-block mb-4">Sign in</button></div>
            <div className="text-center col-5">
                <p>Not a member? <a href="/register">Register</a></p>
            </div>
        </form>
    </div>
  )
};