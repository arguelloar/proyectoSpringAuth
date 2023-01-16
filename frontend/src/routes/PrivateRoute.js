import { useContext, useEffect } from 'react';
import {Outlet, Navigate} from 'react-router-dom'
import { AuthContext, UserContext} from '../App';
import {cookieCheck} from '../services/cookieAuth';


const PrivateRoutes = () => {
  const auth = useContext(AuthContext);
  const role = useContext(UserContext);

  useEffect(() => {
    cookieCheck().then(res => {
      if(res.ok)auth.setAuth(true);
      res.json().then(data => {
        role.setRole(data[0].name) 
      });
    })
  })
  
  return(
    auth.auth ? <Outlet /> : <Navigate to="/login"/>
  )
};

export default PrivateRoutes;