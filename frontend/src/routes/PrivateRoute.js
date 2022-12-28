import { useEffect, useState, useContext } from 'react';
import {Outlet, Navigate} from 'react-router-dom'
import tokenAuth from '../services/tokenAuth'
import { AuthContext } from '../App';


const PrivateRoutes = () => {
  const auth = useContext(AuthContext);
  
  return(
    auth.auth ? <Outlet /> : <Navigate to="/login"/>
  )
};

export default PrivateRoutes;