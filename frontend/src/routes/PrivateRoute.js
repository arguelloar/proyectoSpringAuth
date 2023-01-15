import { useContext, useEffect } from 'react';
import {Outlet, Navigate} from 'react-router-dom'
import { AuthContext } from '../App';
import { cookieCheck } from '../services/cookieAuth';



const PrivateRoutes = () => {
  const auth = useContext(AuthContext);

  useEffect(() => {
    cookieCheck().then(res => {if(res.ok) auth.setAuth(true)});
  })
   
  
  return(
    auth.auth ? <Outlet /> : <Navigate to="/login"/>
  )
};

export default PrivateRoutes;