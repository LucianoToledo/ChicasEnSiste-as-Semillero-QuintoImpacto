import { useContext } from "react";
import { Navigate } from "react-router-dom";
import AuthContext from "../context/authentication/AuthContext";

const ProtectedRoutes = ({ children, URL }) => {
  const { state } = useContext(AuthContext);

  if (state.isAuth) {
    return children;
  }
  return <Navigate to={URL} />;
};
export default ProtectedRoutes;
