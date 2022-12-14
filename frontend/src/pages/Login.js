import React, { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import userLogin from "../services/userLogin";
import { AuthContext } from "../App";

export default function Login() {
    let navigate = useNavigate();

    const auth = useContext(AuthContext);
    
    const [login, setLogin] = useState({
        email:"",
        password:""
    });

    const {email,password} = login;


    const onInputChange = (e) => {
        setLogin({...login,[e.target.name]: e.target.value});
    };

    const onSubmit = (e) => {
        e.preventDefault();
        userLogin(login).then(res => {
            if(res.ok){
                auth.setAuth(true);
                navigate("/");
            }else{
                auth.setAuth(false);
                alert("Incorrect email/password");
            }
        })
    }

  return (
    <div className="container-fluid text-center">
        <h1>User Login</h1>
        <form className="mt-5 row justify-content-around" onSubmit={(e) => onSubmit(e)}>
            <div className="col-lg-7 form-outline mb-4">
                <input type="text" name="email" id="form2Example1" className="form-control" value={email} onChange={(e) => onInputChange(e)} />
                <label className="form-label" htmlFor="form2Example1">Email address</label>
            </div>

            <div className="form-outline mb-4 col-lg-7">
                <input type="password" name="password" id="form2Example2" className="form-control" value={password} onChange={(e) => onInputChange(e)} />
                <label className="form-label" htmlFor="form2Example2">Password</label>
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