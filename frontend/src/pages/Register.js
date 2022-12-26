import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Register() {


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

  return (
    <div className="container-fluid text-center">
        <form className="mt-5 row justify-content-around">
            <div className="form-outline mb-4 col-lg-7">
                <input type="text" name="firstName" id="form2Example2" className="form-control" value={firstName} />
                <label className="form-label" for="form2Example2">First Name</label>
            </div>

            <div className="form-outline mb-4 col-lg-7">
                <input type="text" name="lastName" id="form2Example2" className="form-control" value={lastName} />
                <label className="form-label" for="form2Example2">Last Name</label>
            </div>

            <div className="col-lg-7 form-outline mb-4">
                <input type="email" name="email" id="form2Example1" className="form-control" value={email} />
                <label className="form-label" for="form2Example1">Email address</label>
            </div>

            <div className="form-outline mb-4 col-lg-7">
                <input type="password" name="password" id="form2Example2" className="form-control" value={password} />
                <label className="form-label" for="form2Example2">Password</label>
            </div>

            <div className="row mb-4 col-lg-6">
            </div>
            <div className="col-12"><button type="submit" className="btn btn-primary btn-block mb-4">Register</button></div>
        </form>
    </div>
  )
}
