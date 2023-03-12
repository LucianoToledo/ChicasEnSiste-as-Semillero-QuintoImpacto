import React from 'react';
import { Link } from "react-router-dom";
// import card from './Card.module.css';

    const Card = ({icon, title, id}) => {
      console.log('Los id de inscripcion son: ' + id);
        return (        
            <div className="bg-white shadow-[0_3px_6px_rgb(73,70,151)] sm:ml-10 2xl:max-h-[515px] 2xl:max-w-[331px]">
              <div className="text-center mx-1 border-solid border-[1px] border-white">
                <div className="mt-[48px] mx-[50px] w-25 lg:w-50">
                  <img className="mx-auto" src={icon} alt="Icono" />
                </div>
                <div className="text-[30px] text-[#707070] mt-6">
                  <h3>{title}</h3>                  
                </div>
                <div className="flex justify-center items-center cursor-pointer bg-secondary hover:bg-dark-steal rounded-[5px] mt-12 mb-12 h-12 w-48 mx-auto">
                  <Link to={`/dashboard/course/${id}`} className="font-sans text-white text-2xl py-5">Ver curso</Link>
                </div>
              </div>
          </div>
        );
      };

export default Card