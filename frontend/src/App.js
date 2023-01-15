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
import PublicRoutes from './routes/PublicRoute';
import Products from './pages/Products';
import { createContext, useState } from 'react';
import { isPresent } from './services/cookieAuth';
import '@popperjs/core';


const AuthContext = createContext({});  
const UserContext = createContext({});
export {AuthContext, UserContext};

function App() {

  function setAuthentication(){
    let authed = false;
    isPresent().then(res => res.ok ? authed = true : authed = false);
    return authed;
  }

  const [auth,setAuth] = useState(setAuthentication());
  const [role,setRole] = useState("ROLE_USER");

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

          <Route element={<PublicRoutes />}>
          <Route exact path="/login" element={<Login />} />
          <Route exact path="/register" element={<Register />} />
          </Route>

        </Routes>
        </BrowserRouter>
      </div>
      </UserContext.Provider>
    </AuthContext.Provider>
  );
}
export default App;
