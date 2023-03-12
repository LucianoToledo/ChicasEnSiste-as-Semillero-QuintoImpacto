import { Routes, Route } from "react-router-dom";
import HomePage from "../pages/HomePage";
import NotFound from "../pages/NotFound";
import AuthRoutes from "./AuthRoutes";
import DashboardRoutes from "./DashboardRoutes";
import PublicRoutes from "./PublicRoutes";
import ContactPage from "../pages/ContactPage";
import AboutPage from "../pages/AboutPage";
import MissionPage from "../pages/MissionPage";
import CoursesPage from "../pages/CoursesPage";
import ProtectedRoutes from "./ProtectedRoutes";

const MainRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/contact" element={<ContactPage />} />
      <Route path="/courses" element={<CoursesPage />} />
      <Route path="/about" element={<AboutPage />} />
      <Route path="/mission" element={<MissionPage />} />

      <Route
        path="*"
        element={
          <ProtectedRoutes URL="/auth">
              <DashboardRoutes />
          </ProtectedRoutes>
        }
      />
      <Route
        path="/auth/*"
        element={
          <PublicRoutes>
            <AuthRoutes />
          </PublicRoutes>
        }
      />
      <Route path="*" element={<NotFound />} />
    </Routes>
  );
};
export default MainRoutes;
