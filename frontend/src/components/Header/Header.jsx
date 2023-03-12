import { useEffect, useState } from "react";
import iconInstagram from "../../assets/images/icons/instagram.png";
import iconLinkedin from "../../assets/images/icons/linkedin.png";
import logoSm from "../../assets/images/logo.png";
import { Link, NavLink } from "react-router-dom";

const Header = () => {
  const [toggleMenu, setToggleMenu] = useState(false);
  const [screenWidth, setScreenWidth] = useState(window.innerWidth);

  useEffect(() => {
    const changeWidth = () => {
      setScreenWidth(window.innerWidth);
    };

    window.addEventListener("resize", changeWidth);
    return () => {
      window.removeEventListener("resize", changeWidth);
    };
  }, []);

  const toggleNav = () => {
    setToggleMenu(!toggleMenu);
  };
  return (
    <header className="flex items-center justify-between flex-wrap bg-withe">
      <div className="w-16 md:w-32 lg:w-48">
        {/* <div className={header.logo}> */}
        <Link to="/">
          <img src={logoSm} alt="logo" height="auto" width="100%" />
        </Link>
      </div>
      <div className="block lg:hidden mr-6">
        <button
          onClick={toggleNav}
          className="flex items-center px-3 py-2 border rounded border-withe hover:text-primary hover:border-primary"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="h-6 w-6"
            fill="none"
            viewBox="0 0 24 24"
            stroke="#494697"
            strokeWidth="2"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="M4 6h16M4 12h16M4 18h16"
            />
          </svg>
        </button>
      </div>
      {(toggleMenu || screenWidth > 1024) && (
        <div className="w-full fixed lg:relative top-16 lg:top-0 bg-white flex-grow lg:flex lg:items-center lg:w-auto z-10 ">
          <nav className="font-sans text-3xl text-center lg:flex-grow">
            <NavLink
              to="/about"
              className="block lg:inline-block text-primary hover:text-indigo-900 lg:mr-10 m-4 transition ease-in-out duration-300 hover:scale-110"
            >
              Nosotras
            </NavLink>
            <NavLink
              to="/mission"
              className="block lg:inline-block text-primary hover:text-indigo-900 lg:mr-10 m-4 transition ease-in-out duration-300 hover:scale-110"
            >
              Misión
            </NavLink>
            <NavLink
              to="/courses"
              className="block lg:inline-block text-primary hover:text-indigo-900 m-4 transition ease-in-out duration-300 hover:scale-110"
            >
              Cursos
            </NavLink>
          </nav>
          <div className="min-w-[70px] text-center">
            {/* <div className={header["social-icons"]}> */}
            <a
              target="_blank"
              rel="noreferrer noopener"
              href="https://www.instagram.com"
              className="inline-block mr-4 m-4 transition ease-in-out duration-300 hover:scale-110"
            >
              <img src={iconInstagram} alt="instagram" height="34" width="34" />
            </a>
            <a
              target="_blank"
              rel="noreferrer noopener"
              href="https://ar.linkedin.com/"
              className="inline-block lg:mr-16 m-4 transition ease-in-out duration-300 hover:scale-110"
            >
              <img src={iconLinkedin} alt="linkedin" height="34" width="34" />
            </a>
          </div>
          <div className="block lg:inline-block text-3xl text-center px-4 py-2 mb-8 lg:mb-0 leading-none border rounded bg-primary text-white border-primary hover:border-transparent hover:bg-indigo-900">
            {/* <div className={header["btn-go-login"]}> */}
            <Link to="/auth">Iniciar sesión</Link>
          </div>
        </div>
      )}
    </header>
  );
};
export default Header;
