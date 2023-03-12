import { Route, Routes } from "react-router-dom";
import ForgotPassword from "../pages/ForgotPassword";
import AuthPage from "../pages/AuthPage";

const AuthRoutes = () => {
  return (
    <Routes>
      <Route index={true} element={<AuthPage />} />
      <Route path="/reset" element={<ForgotPassword />} />
    </Routes>
  );
};
export default AuthRoutes;
