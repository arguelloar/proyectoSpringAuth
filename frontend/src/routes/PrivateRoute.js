import { useContext } from 'react';
import {Outlet, Navigate} from 'react-router-dom'
import { AuthContext } from '../App';
import {cookieCheck} from '../services/cookieAuth';


const PrivateRoutes = () => {
  const auth = useContext(AuthContext);
  cookieCheck().then(res => auth.setAuth(res))
  
  return(
    auth.auth ? <Outlet /> : <Navigate to="/login"/>
  )
};

export default PrivateRoutes;