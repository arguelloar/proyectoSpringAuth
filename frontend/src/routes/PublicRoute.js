import { useContext, useEffect } from 'react';
import {Outlet, Navigate} from 'react-router-dom'
import { AuthContext, UserContext } from '../App';
import {cookieCheck} from '../services/cookieAuth';


const PublicRoutes = () => {
  const auth = useContext(AuthContext);
  const role = useContext(UserContext);

  useEffect(() => {
    cookieCheck().then(res => {
      res.json().then(data => {
        role.setRole(data[0].name) 
      });
    })
  })
  
  return(
    auth.auth ? <Navigate to="/"/> : <Outlet />)
};

export default PublicRoutes;