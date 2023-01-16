import React, { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { userRegister } from "../services/userAuth";
import {emailValidator, pwValidator} from "../services/inputValidators";
import Alert from "../layout/Alert";
import { AuthContext } from "../App";


export default function Register() {
    let navigate = useNavigate();
    const [alertShow,setAlertShow] = useState(false);
    const [message, setMessage] = useState("");
    const auth = useContext(AuthContext);

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
        if(emailValidator(register.email) && pwValidator(register.password)){
            userRegister(register).then(res => {
                if(res.httpStatus === "FORBIDDEN"){
                    setMessage(res.message);
                    setAlertShow(true);
                }else{
                    auth.setAuth(true);
                    navigate("/");
                }
            })
            
        }else{
            setAlertShow(true);
            setMessage("Incorrect email or password format")
        }
    }

  return (
    <div className="container-fluid text-center" onSubmit={(e) => onSubmit(e)}>
        <h1>Register Here</h1>
        <form className="mt-5 row justify-content-around">
            {alertShow && <Alert message={message}/>}
            <p>Password must have 8 characters and contain atleast 1 number</p>
            <div className="form-outline mb-4 col-lg-7">
                <input type="text" name="firstName" id="form2Example2" className="form-control" value={firstName} onChange={(e) => onInputChange(e)}/>
                <label className="form-label" htmlFor="form2Example2">First Name</label>
            </div>

            <div className="form-outline mb-4 col-lg-7">
                <input type="text" name="lastName" id="form2Example2" className="form-control" value={lastName} onChange={(e) => onInputChange(e)} />
                <label className="form-label" htmlFor="form2Example2">Last Name</label>
            </div>

            <div className="col-lg-7 form-outline mb-4">
                <input type="email" name="email" id="form2Example1" className="form-control" value={email} onChange={(e) => onInputChange(e)} />
                <label className="form-label" htmlFor="form2Example1">Email address</label>
            </div>

            <div className="form-outline mb-4 col-lg-7">
                <input type="password" name="password" id="form2Example2" className="form-control" value={password} onChange={(e) => onInputChange(e)} />
                <label className="form-label" htmlFor="form2Example2">Password</label>
            </div>

            <div className="row mb-4 col-lg-6">
            </div>
            <div className="col-12"><button type="submit" className="btn btn-primary btn-block mb-4">Register</button></div>
        </form>
    </div>
  )
}
