import React from 'react'
import {Link} from 'react-router-dom'

const Card = ({icon, title, description, price, id}) => {
  
 
  return (
    <div className="flex flex-col lg:flex-row bg-white shadow-[0_3px_6px_rgb(73,70,151)] rounded-[5px] mb-6">
        <div className="w-5/5 p-6 mx-auto mb-0 text-center lg:w-1/5">
            <img className="border-primary border-[1px] text-center" src={icon} alt="Icono" />
        </div>
        <div className="w-5/5 lg:w-4/5 p-4">
            <h4 className="font-sans text-[#707070] font-bold text-xl md:text-2xl lg:text-3xl">{title}</h4>
            <p className="font-sans text-[#707070] text-basic md:text-lg lg:text-xl mt-0.5">{description}</p>
            <p className="font-sans text-[#707070] font-medium text-xl md:text-2xl lg:text-3xl mt-3 mb-3 md:mb-0">Precio: ${price}</p>
            <div className="bg-secondary w-[238px] h-[50px] text-center rounded-[5px] float-none mx-auto mt-4 md:mt-0 md:float-right translate-y-0 md:-translate-y-10 lg:-translate-y-10 mb-0 ">
            <Link className="font-sans text-white text-2xl leading-10 inline-block align-middle" to={`/dashboard/payment/${id}`} >Empezar</Link>
            </div>
        </div>
        </div>
    
  )
}

export default Card