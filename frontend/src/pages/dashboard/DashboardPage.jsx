import { useContext, useState } from "react";
import Navbar from "../../components/Dashboard/Navbar";
import Sidebar from "../../components/Dashboard/Sidebar/Sidebar";
import AuthContext from "../../context/authentication/AuthContext";

const DashboardPage = ({ children }) => {
  const authContext = useContext(AuthContext);
  const { state } = authContext;
  const [sidebarOpen, setSidebarOpen] = useState(false);
  // console.log(state.user);
  const user = state.user

  return (
    <div className="flex h-screen overflow-hidden bg-[#fafafa]">
      {/* Sidebar */}
      <Sidebar sidebarOpen={sidebarOpen} setSidebarOpen={setSidebarOpen} userData={user} />

      {/* Content area */}
      <div className="relative flex flex-col flex-1 overflow-y-auto overflow-x-hidden">

        {/*  Site header */}
        <Navbar sidebarOpen={sidebarOpen} setSidebarOpen={setSidebarOpen} />

        <main>
          <div className="px-4 sm:px-6 lg:px-8 w-full max-w-9xl mx-auto">
            {children}
            
          </div>
        </main>
      </div>
    </div>
  );
};
export default DashboardPage;
