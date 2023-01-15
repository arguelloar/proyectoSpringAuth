import { useContext } from 'react';
import {Outlet, Navigate} from 'react-router-dom'
import { AuthContext } from '../App';
import { cookieCheck } from '../services/cookieAuth';



const PrivateRoutes = () => {
  const auth = useContext(AuthContext);
  console.log("FROM PRIVATEROUTE"+auth.auth);

  cookieCheck().then(res => {
    if(res.ok) auth.setAuth(true);
  })

  console.log("FROM PRIVATEROUTE"+auth.auth);
  
  return(
    auth.auth ? <Outlet /> : <Navigate to="/login"/>
  )
};

export default PrivateRoutes;