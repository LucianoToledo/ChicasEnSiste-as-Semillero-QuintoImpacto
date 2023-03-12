// import hero from "./hero.module.css";
import rocket from "../../assets/images/rocket.png";
import { Link } from "react-router-dom";

const Hero = ({ title, secondText, thirdText }) => {
  return (
    <div className="w-full bg-primary ">
      <div className="text-white font-bold text-center text-5xl lg:text-8xl pt-8 pb-6">
        <p>{title}</p>
      </div>
      <div className="text-white font-bold text-center lg:text-left text-2xl lg:text-6xl px-2 lg:px-16 py-8">
        <p>{secondText}</p>
      </div>
      <div className="text-white font-medium text-center lg:text-left text-xl lg:text-4xl px-2 lg:pl-16 lg:pr-24 py-8 lg:pb-44">
        <p>{thirdText}</p>
      </div>

      <div className="">
        <div className="flex justify-center lg:ml-28 lg:justify-start translate-y-[30px]">
          <Link
            to="contact"
            className="bg-secondary hover:bg-pink-700 text-white text-center text-2xl font-bold p-4 rounded w-64"
          >
            Contactar
          </Link>
        </div>
      </div>
      <div className="hidden lg:block w-96 lg:absolute lg:right-24 -translate-y-56">
        <img src={rocket} alt="rocket" height="auto" width="100%" />
      </div>
    </div>
  );
};
export default Hero;
