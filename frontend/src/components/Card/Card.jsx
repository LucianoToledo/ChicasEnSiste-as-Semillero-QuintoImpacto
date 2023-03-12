import React from "react";
import { Link } from "react-router-dom";
// import "./Card.css";

const Card = ({iconImg, title, description, subtitle, price}) => {
  return (        
      <div className="w-full h-[622px] max-w-[410px] bg-white rounded-lg opacity-100 tracking-normal shadow-[0px_3px_6px_rgb(200,99,130)]">
        <div className="h-full flex flex-col justify-evenly px-10 py-0">
          <div className="flex self-center">
            <img src={iconImg} alt="Icono" width= "99px" height= "99px" />
          </div>
          <div className="min-h-[86px] font-sans font-bold text-3xl text-dark-steal text-center lg:text-4xl">
            <h3>{title}</h3>
          </div>
          <div className="min-h-[206px] flex flex-col justify-between">
            <p className="text-sans text-dark-steal text-2xl justify-around text-left font-normal lg:text-3xl">{description}</p>
            <p className="text-sans text-dark-steal text-2xl text-left font-medium lg:text-3xl">{subtitle}</p>
            <span className="text-sans text-dark-steal text-2xl text-left font-medium lg:text-3xl">{price}</span>
          </div>
          <div className="w-full flex justify-center items-start">
            <Link className="block w-[254px] font-sans text-2xl font-medium bg-dark-steal text-white text-center rounded-[5px] px-2 py-4 transition duration-150 ease-out hover:ease-in hover:bg-[#393486]" to="/auth">Comprar</Link>
          </div>
        </div>
    </div>
  );
};

export default Card;
