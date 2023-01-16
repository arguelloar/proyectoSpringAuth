import './App.css';
import Navbar from './layout/Navbar';
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import Home from "./pages/Home";
import "../node_modules/bootstrap/dist/js/bootstrap.min.js";
import 'bootstrap/dist/js/bootstrap.bundle';
import Login from "./pages/Login";
import { BrowserRouter, Routes, Route} from 'react-router-dom';
import Register from "./pages/Register";
import PrivateRoutes from "./routes/PrivateRoute";
import Products from './pages/Products';
import { createContext, useState } from 'react';
import { cookieCheck } from './services/cookieAuth';
import '@popperjs/core';

const LoggedIn = createContext({});
const AuthContext = createContext({});  
const UserContext = createContext({});
export {AuthContext, UserContext, LoggedIn};

function App() {

  const [isLoggedIn,setLoggedIn] = useState(false);
  const [role,setRole] = useState("ROLE_USER");
  const [auth,setAuth] = useState(true);

  return (
    <AuthContext.Provider value={{auth,setAuth}}>
      <UserContext.Provider value={{role,setRole}}>
        <LoggedIn.Provider value={{isLoggedIn,setLoggedIn}}>
      <div className="App">
        <BrowserRouter>
        <Navbar />
        <Routes>       
          <Route element={<PrivateRoutes />}>
            <Route exact path="/products" element={<Products />}/>
          </Route>
          <Route exact path="/" element={<Home />} />
          <Route exact path="/login" element={<Login />} />
          <Route exact path="/register" element={<Register />} />
        </Routes>
        </BrowserRouter>
      </div>
      </LoggedIn.Provider>
      </UserContext.Provider>
    </AuthContext.Provider>
  );
}
export default App;
