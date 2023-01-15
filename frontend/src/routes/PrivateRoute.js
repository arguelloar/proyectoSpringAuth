import { useContext } from 'react';
import {Outlet, Navigate} from 'react-router-dom'
import { AuthContext } from '../App';



const PrivateRoutes = () => {
  const auth = useContext(AuthContext);
  console.log("FROM PRIVATEROUTE"+auth.auth);
  

  
  return(
    auth.auth ? <Outlet /> : <Navigate to="/login"/>
  )
};

export default PrivateRoutes;