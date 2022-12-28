import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import userRegister from "../services/userRegister";


export default function Register() {
    let navigate = useNavigate();

    const [register, setRegister] = useState({
        firstName:"",
        lastName:"",
        email:"",
        password:""
    });

    const {firstName,lastName,email,password} = register;


    const onInputChange = (e) => {
        setRegister({...register,[e.target.name]: e.target.value});
    };

    const onSubmit = (e) => {
        e.preventDefault();
        userRegister(register).then(res => {
            if(res.ok){
                navigate("/");
            }
        });
    }

  return (
    <div className="container-fluid text-center" onSubmit={(e) => onSubmit(e)}>
        <form className="mt-5 row justify-content-around">
            <div className="form-outline mb-4 col-lg-7">
                <input type="text" name="firstName" id="form2Example2" className="form-control" value={firstName} onChange={(e) => onInputChange(e)}/>
                <label className="form-label" for="form2Example2">First Name</label>
            </div>

            <div className="form-outline mb-4 col-lg-7">
                <input type="text" name="lastName" id="form2Example2" className="form-control" value={lastName} onChange={(e) => onInputChange(e)} />
                <label className="form-label" for="form2Example2">Last Name</label>
            </div>

            <div className="col-lg-7 form-outline mb-4">
                <input type="email" name="email" id="form2Example1" className="form-control" value={email} onChange={(e) => onInputChange(e)} />
                <label className="form-label" for="form2Example1">Email address</label>
            </div>

            <div className="form-outline mb-4 col-lg-7">
                <input type="password" name="password" id="form2Example2" className="form-control" value={password} onChange={(e) => onInputChange(e)} />
                <label className="form-label" for="form2Example2">Password</label>
            </div>

            <div className="row mb-4 col-lg-6">
            </div>
            <div className="col-12"><button type="submit" className="btn btn-primary btn-block mb-4">Register</button></div>
        </form>
    </div>
  )
}
