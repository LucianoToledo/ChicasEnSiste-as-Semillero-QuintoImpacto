import { Route, Routes } from "react-router-dom";
import DashboardPage from "../pages/dashboard/DashboardPage";
import Dashboard from "../components/Dashboard";
import Course from "../components/Dashboard/Course";
import Courses from "../components/Dashboard/Courses";
import MyCourses from "../components/Dashboard/MyCourses";
import CreateCoursePage from "../pages/dashboard/CreateCoursePage";
import ModulesPage from "../pages/dashboard/ModulesPage";
import ProfilePage from "../pages/dashboard/ProfilePage";
import Payment from "../components/Dashboard/Payment";
import VideosPage from "../pages/dashboard/VideosPage";
import NotFound from "../pages/NotFound";

const DashboardRoutes2 = () => {
  return (
    <Routes>
      <Route
        path="/dashboard/*"
        element={
          <DashboardPage>
            <Routes>
              <Route index={true} element={<Dashboard />} />
              <Route path="profile" element={<ProfilePage />} />
              <Route path="course/:id" element={<Course />} />
              <Route path="myCourses" element={<MyCourses />} />
              <Route path="courses" element={<Courses />} />
              <Route path="/payment/:id" element={<Payment />} />
              <Route path="new-course" element={<CreateCoursePage />} />
              <Route path="modules" element={<ModulesPage />} />
              <Route path="videos" element={<VideosPage />} />
              <Route path="/*" element={<NotFound />} />
            </Routes>
          </DashboardPage>
        }
      />
      <Route path="*" element={<NotFound />} />
      <Route path="/validationemail" element={<h1>Validar Email</h1>} />
    </Routes>
  );
};
export default DashboardRoutes2;
