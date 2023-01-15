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


const AuthContext = createContext({});  
const UserContext = createContext({});
export {AuthContext, UserContext};

function App() {

  const [role,setRole] = useState("ROLE_USER");
  const [auth,setAuth] = useState(false);

  function setAuthentication(){
    cookieCheck().then(res => {
      if(res.ok) setAuth(true);
      res.json().then(data => {
        setRole(data[0].name) 
      });
    })
    console.log(auth);
    console.log(role);
  }
  
  setAuthentication();

  return (
    <AuthContext.Provider value={{auth,setAuth}}>
      <UserContext.Provider value={{role,setRole}}>
      <div className="App">
        <BrowserRouter>
        <Navbar />
        <Routes>       
          <Route element={<PrivateRoutes />}>
            <Route exact path="/products" element={<Products />}/>
            <Route exact path="/" element={<Home />} />
          </Route>
          <Route exact path="/login" element={<Login />} />
          <Route exact path="/register" element={<Register />} />
        </Routes>
        </BrowserRouter>
      </div>
      </UserContext.Provider>
    </AuthContext.Provider>
  );
}
export default App;
