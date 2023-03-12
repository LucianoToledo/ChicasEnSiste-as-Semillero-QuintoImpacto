import { useContext } from "react";
import { Navigate } from "react-router-dom";
import AuthContext from "../context/authentication/AuthContext";

const PublicRoutes = ({ children }) => {
  const { state } = useContext(AuthContext);
  return state.isAuth ? <Navigate to="/dashboard" /> : children;
};
export default PublicRoutes;
