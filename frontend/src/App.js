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
import { isPresent } from './services/cookieAuth';
import '@popperjs/core';


const AuthContext = createContext({});  
const UserContext = createContext({});
export {AuthContext, UserContext};

function App() {

  const [auth,setAuth] = useState(isPresent());
  const [role,setRole] = useState("ROLE_USER");

  return (
    <AuthContext.Provider value={{auth,setAuth}}>
      <UserContext.Provider value={{role,setRole}}>
      <div className="App">
        <BrowserRouter>
        <Navbar />
        <Routes>
          <Route element={<PrivateRoutes />}>
            <Route exact path="/users" element={<Products />}/>
            <Route exact path="/home" element={<Home />} />
            <Route exact path="/product/add" element={<Home />} />
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
