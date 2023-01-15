import { useContext } from 'react';
import {Outlet, Navigate} from 'react-router-dom'
import { AuthContext } from '../App';



const PrivateRoutes = () => {
  const auth = useContext(AuthContext);

  function setAuthentication(){
    cookieCheck().then(res => {
      if(res.ok) auth.setAuth(true);
      res.json().then(data => {
        setRole(data[0].name) 
      });
    })
  }
  setAuthentication();
  
  return(
    auth.auth ? <Outlet /> : <Navigate to="/login"/>
  )
};

export default PrivateRoutes;