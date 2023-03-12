import { useContext, useRef, useEffect, useState } from "react";
import { NavLink } from "react-router-dom";
import { SidebarHeader } from "./SidebarHeader/SidebarHeader";
import AuthContext from "../../../context/authentication/AuthContext";

const Sidebar = ({ sidebarOpen, setSidebarOpen, userData}) => {
  const authContext = useContext(AuthContext);
  const { logout } = authContext;

  const trigger = useRef(null);
  const sidebar = useRef(null);

  // close on click outside
  useEffect(() => {
    const clickHandler = ({ target }) => {
      if (!sidebar.current || !trigger.current) return;
      if (
        !sidebarOpen ||
        sidebar.current.contains(target) ||
        trigger.current.contains(target)
      ) {
        return;
      }
      if (!(Object.values(target)[0].type !== "div")) {
        setSidebarOpen(false);
      }
    };
    document.addEventListener("click", clickHandler);
    return () => document.removeEventListener("click", clickHandler);
  });

  // close if the esc key is pressed
  useEffect(() => {
    const keyHandler = ({ keyCode }) => {
      if (!sidebarOpen || keyCode !== 27) return;
      setSidebarOpen(false);
    };
    document.addEventListener("keydown", keyHandler);
    return () => document.removeEventListener("keydown", keyHandler);
  });

  //User o Admin
  const userRole = userData.role

  if(userRole === "ROLE_USER"){
    // console.log("user")
    return (
      <div>
        {/* Sidebar backdrop (mobile only) */}
        <div
          className={`fixed inset-0 bg-slate-900 bg-opacity-30 z-40  lg:z-auto transition-opacity duration-200 ${
            sidebarOpen ? "opacity-100" : "opacity-0 pointer-events-none"
          }`}
          aria-hidden="true"
        >
        </div>
          {/* Sidebar */}
          <div
            id="sidebar"
            ref={sidebar}
            className={`flex flex-col absolute z-40 left-0 top-0 lg:static lg:left-auto lg:top-auto lg:translate-x-0 transform h-screen overflow-y-scroll lg:overflow-y-auto no-scrollbar w-64 lg:w-64 2xl:!w-64 shrink-0 bg-primary p-4 transition-all duration-200 ease-in-out ${
              sidebarOpen ? "translate-x-0" : "-translate-x-64"
            }`}
          >
            {/* Sidebar header */}
            <div className="flex justify-between mb-5 pr-3 sm:px-2">
              {/* Close button */}
              <button
                ref={trigger}
                className="lg:hidden text-slate-500 hover:text-slate-400"
                onClick={() => setSidebarOpen(!sidebarOpen)}
                aria-controls="sidebar"
                aria-expanded={sidebarOpen}
              >
                <span className="sr-only">Close sidebar</span>
                <svg
                  className="w-6 h-6 fill-current"
                  viewBox="0 0 24 24"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path d="M10.7 18.7l1.4-1.4L7.8 13H20v-2H7.8l4.3-4.3-1.4-1.4L4 12z" />
                </svg>
              </button>
              {/* Avatar */}
              <SidebarHeader userData={userData} />
            </div>
  
            {/* Links */}
            <nav className="flex flex-col">
              <hr className="my-4 min-w-full" />
              <ul className="flex-col min-w-full flex list-none">                        
                <li>
                  <NavLink
                    to=""
                    end
                    className={({ isActive }) =>
                      isActive
                        ? "block bg-white/30 text-white text-2xl h-12 align-bottom pt-2 pl-3 shadow-md"
                        : "flex items-center gap-4 text-2xl text-white px-4 py-3 hover:text-white/60"
                    }
                  >
                    Dashboard
                  </NavLink>
                </li>
                <li>
                  <NavLink
                    to="courses"
                    className={({ isActive }) =>
                      isActive
                        ? "block bg-white/30 text-white text-2xl h-12 align-bottom pt-2 pl-3 shadow-md"
                        : "flex items-center gap-4 text-2xl text-white px-4 py-3 hover:text-white/60"
                    }
                  >
                    Cursos
                  </NavLink>
                </li>
                <li>
                  <NavLink
                    to="myCourses"
                    className={({ isActive }) =>
                      isActive
                        ? "block bg-white/30 text-white text-2xl h-12 align-bottom pt-2 pl-3 shadow-md"
                        : "flex items-center gap-4 text-2xl text-white px-4 py-3 hover:text-white/60"
                    }
                  >
                    Mis cursos
                  </NavLink>
                </li>
                <li>
                  <NavLink
                    to="profile"
                    className={({ isActive }) =>
                      isActive
                        ? "block bg-white/30 text-white text-2xl h-12 align-bottom pt-2 pl-3 shadow-md"
                        : "flex items-center gap-4 text-2xl text-white px-4 py-3 hover:text-white/60"
                    }
                  >
                    Perfil
                  </NavLink>
                </li>
                <li>
                  <button
                    type="button"
                    onClick={logout}
                    className="flex items-center gap-4 text-2xl text-white px-4 py-3 hover:text-white/60"
                  >
                    Cerrar Sesión
                  </button>
                </li>
              </ul>
            </nav>
          </div>
      </div>
    );
  } else if(userRole === "ROLE_ADMIN"){
    // console.log("admin")
    return (
      <div>
        {/* Sidebar backdrop (mobile only) */}
        <div
          className={`fixed inset-0 bg-slate-900 bg-opacity-30 z-40  lg:z-auto transition-opacity duration-200 ${
            sidebarOpen ? "opacity-100" : "opacity-0 pointer-events-none"
          }`}
          aria-hidden="true"
        >
        </div>
          {/* Sidebar */}
          <div
            id="sidebar"
            ref={sidebar}
            className={`flex flex-col absolute z-40 left-0 top-0 lg:static lg:left-auto lg:top-auto lg:translate-x-0 transform h-screen overflow-y-scroll lg:overflow-y-auto no-scrollbar w-64 lg:w-64 2xl:!w-64 shrink-0 bg-primary p-4 transition-all duration-200 ease-in-out ${
              sidebarOpen ? "translate-x-0" : "-translate-x-64"
            }`}
          >
            {/* Sidebar header */}
            <div className="flex justify-between mb-5 pr-3 sm:px-2">
              {/* Close button */}
              <button
                ref={trigger}
                className="lg:hidden text-slate-500 hover:text-slate-400"
                onClick={() => setSidebarOpen(!sidebarOpen)}
                aria-controls="sidebar"
                aria-expanded={sidebarOpen}
              >
                <span className="sr-only">Close sidebar</span>
                <svg
                  className="w-6 h-6 fill-current"
                  viewBox="0 0 24 24"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path d="M10.7 18.7l1.4-1.4L7.8 13H20v-2H7.8l4.3-4.3-1.4-1.4L4 12z" />
                </svg>
              </button>
              {/* Avatar */}
              <SidebarHeader userData={userData} />
            </div>
  
            {/* Links */}
            <nav className="flex flex-col">
              <hr className="my-4 min-w-full" />
              <ul className="flex-col min-w-full flex list-none">                        
                <li>
                  <NavLink
                    to=""
                    end
                    className={({ isActive }) =>
                      isActive
                        ? "block bg-white/30 text-white text-2xl h-12 align-bottom pt-2 pl-3 shadow-md"
                        : "flex items-center gap-4 text-2xl text-white px-4 py-3 hover:text-white/60"
                    }
                  >
                    Dashboard
                  </NavLink>
                </li>
                <li>
                  <NavLink
                    to="courses"
                    className={({ isActive }) =>
                      isActive
                        ? "block bg-white/30 text-white text-2xl h-12 align-bottom pt-2 pl-3 shadow-md"
                        : "flex items-center gap-4 text-2xl text-white px-4 py-3 hover:text-white/60"
                    }
                  >
                    Cursos
                  </NavLink>
                </li>
                <li>
                  <NavLink
                    to="modules"
                    className={({ isActive }) =>
                      isActive
                        ? "block bg-white/30 text-white text-2xl h-12 align-bottom pt-2 pl-3 shadow-md"
                        : "flex items-center gap-4 text-2xl text-white px-4 py-3 hover:text-white/60"
                    }
                  >
                    Módulos
                  </NavLink>
                </li>
                <li>
                  <NavLink
                    to="videos"
                    className={({ isActive }) =>
                      isActive
                        ? "block bg-white/30 text-white text-2xl h-12 align-bottom pt-2 pl-3 shadow-md"
                        : "flex items-center gap-4 text-2xl text-white px-4 py-3 hover:text-white/60"
                    }
                  >
                    Videos
                  </NavLink>
                </li>
                <li>
                  <NavLink
                    to="new-course"
                    className={({ isActive }) =>
                      isActive
                        ? "block bg-white/30 text-white text-2xl h-12 align-bottom pt-2 pl-3 shadow-md"
                        : "flex items-center gap-4 text-2xl text-white px-4 py-3 hover:text-white/60"
                    }
                  >
                    Crear Curso
                  </NavLink>
                </li>
                <li>
                  <NavLink
                    to="profile"
                    className={({ isActive }) =>
                      isActive
                        ? "block bg-white/30 text-white text-2xl h-12 align-bottom pt-2 pl-3 shadow-md"
                        : "flex items-center gap-4 text-2xl text-white px-4 py-3 hover:text-white/60"
                    }
                  >
                    Perfil
                  </NavLink>
                </li> 
                <li>
                  <button
                    type="button"
                    onClick={logout}
                    className="flex items-center gap-4 text-2xl text-white px-4 py-3 hover:text-white/60"
                  >
                    Cerrar Sesión
                  </button>
                </li>
              </ul>
            </nav>
          </div>
      </div>
    );
  }
};
export default Sidebar;
