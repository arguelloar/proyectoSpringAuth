import React from 'react'
import { useNavigate } from "react-router-dom";

export default function Home() {
  const navigate = useNavigate();
  
  return (
    <div className="container-fluid">
        <div className="row py-3 justify-content-md-center">
            <div className="col-lg-5 col-sm-12">
                <h1 className="text-center">Spring and React APP</h1>
                <a href="https://github.com/arguelloar/proyectoSpringAuth" className="text-center h3">GitHub Repository</a>
                <br></br>
                <p>One of my first projects using Spring Boot and React.</p>
                <p>It contains a products page so you can visualize inventory as a user, and add/update/delete as an admin</p>
                <p>I use cookie authentication via API Call, the cookie contains JWT Access and Refresh Tokens</p>
                <br></br>
                <ul className="list-group">
                    <li className="list-group-item">Java</li>
                    <li className="list-group-item">Spring Security for protecting resources and Data Manipulation</li>
                    <li className="list-group-item">Spring JPA and MYSQL for persisting and storing data</li>
                    <li className="list-group-item">REACT</li>
                </ul>
            </div>
            <div className="col-lg-8 col-sm-12 mt-5">
            <a onClick={() => navigate("/products")} className="btn btn-outline-dark" role="button" id="btnLogout">View all products</a>
            </div>
        </div>
    </div>
  )
};
