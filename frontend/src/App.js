import './App.css';
import Navbar from './layout/Navbar';
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import Home from "./pages/Home";
import "../node_modules/bootstrap/dist/js/bootstrap.min.js";
import Login from "./pages/Login";
import { BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import Register from "./pages/Register";
import PrivateRoutes from "./routes/PrivateRoute";
import Products from './pages/Products';
import { createContext, useState } from 'react';
import tokenAuth from "./services/tokenAuth";

const AuthContext = createContext({});  
export {AuthContext};

function App() {

  const [auth,setAuth] = useState(tokenAuth); 
  
  return (
    <AuthContext.Provider value={{auth,setAuth}}>
    <div className="App">
      <Router>
      <Navbar />
      <Routes>
        <Route element={<PrivateRoutes />}>
          <Route exact path="/products" element={<Products />}/>
          <Route exact path="/" element={<Home />} />
        </Route>
        <Route exact path="/login" element={<Login />} />
        <Route exact path="/register" element={<Register />} />
      </Routes>
      </Router>
    </div>
    </AuthContext.Provider>
  );
}
export default App;