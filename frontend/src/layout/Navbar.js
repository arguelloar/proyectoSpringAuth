import React, { useContext } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext} from "../App";
import { userLogout } from "../services/userAuth";

export default function Navbar() {

  const navigate = useNavigate();
  const auth = useContext(AuthContext);

  const handleClick = () => {
    userLogout();
    auth.setAuth(false);
    navigate("/login");
  }

  return (
    <div>
        <nav className="navbar navbar-expand-lg bg-light">
            <div className="container-fluid justify-content-around">
                <a className="navbar-brand" role="button" onClick={() => navigate("/home")}>Adrian Arguello - Fullstack Java Developer</a>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarSupportedContent">
                  <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                    <li className="nav-item">
                      <a className="nav-link active" aria-current="page" href="https://www.linkedin.com/in/arguelloar/">
                        <img src="https://www.edigitalagency.com.au/wp-content/uploads/new-linkedin-logo-white-black-png.png" width="25px"></img>
                        </a>
                    </li>
                    <li className="nav-item">
                      <a className="nav-link active" aria-current="page" href="https://github.com/arguelloar">
                        <img src="https://seeklogo.com/images/G/github-logo-7880D80B8D-seeklogo.com.png" width="25px"></img>
                        </a>
                    </li>
                  </ul>
                </div>
                {auth.auth ? (<a role="button" className="btn btn-outline-dark" id="btnLogout" onClick={handleClick}>Logout</a>) : 
                (<a className="btn btn-outline-dark" role="button" id="btnLogout" onClick={() => navigate("/login")}>Login</a>)}
            </div>
        </nav>
    </div>
  )
}
