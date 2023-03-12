import { Link } from "react-router-dom";
import logoSm from "../../../assets/images/logo-dashboard2.png";

const Navbar = ({ sidebarOpen, setSidebarOpen }) => {
  return (
    <header className="sticky top-0 bg-[#FAFAFA] z-30">
      <div className="px-4 sm:px-6 lg:pl-8 lg:pr-16">
        <div className="flex items-center justify-between h-32 lg:h-44 -mb-px">
          {/* Header: Left side */}
          <div className="flex">
            {/* Hamburger button */}
            <button
              className="text-slate-500 hover:text-slate-600 lg:hidden"
              aria-controls="sidebar"
              aria-expanded={sidebarOpen}
              onClick={() => setSidebarOpen(!sidebarOpen)}
            >
              <span className="sr-only">Open sidebar</span>
              <svg
                className="w-6 h-6 fill-current"
                viewBox="0 0 24 24"
                xmlns="http://www.w3.org/2000/svg"
              >
                <rect x="4" y="5" width="16" height="2" />
                <rect x="4" y="11" width="16" height="2" />
                <rect x="4" y="17" width="16" height="2" />
              </svg>
            </button>
          </div>

          {/* Header: Right side */}
          <div className="flex items-center">
            <Link to="/dashboard">
              <img src={logoSm} alt="logo" className="w-24 lg:w-32" />
            </Link>
          </div>
        </div>
      </div>
    </header>
  );
};
export default Navbar;
