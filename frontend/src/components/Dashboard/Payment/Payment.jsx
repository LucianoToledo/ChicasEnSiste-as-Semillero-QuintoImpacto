import React, {useState, useEffect, useContext} from "react";
import clientAxios from "../../../config/axios";
//import paymentStyle from "./Payment.module.css";
import Dropdown1 from "../Dropdown/Dropdown1";
import {useParams} from "react-router-dom";
import AuthContext from "../../../context/authentication/AuthContext";
import Swal from 'sweetalert2';
import withReactContent from 'sweetalert2-react-content';

const Payment = () => {

  const {id} = useParams();

  const authContext = useContext(AuthContext);
  const { state } = authContext;
  const { user } = state;


  const [course, setCourse] = useState({
    title: "",
    description: "",
    price: "",
    id: id
})

  const MySwal = withReactContent(Swal);

  const notSucceed = () => {
    MySwal.fire({
      title: 'Error!',
      text: 'Por favor, intente nuevamente más tarde.',
      icon: 'error',
      confirmButtonText: 'Ok'
    })
  }

  const success = () => {
    MySwal.fire({
      title: 'Muy buena elección!',
      text: 'Redirigiendo al sitio de pago.',
      icon: 'success', 
      confirmButtonText: 'Ok',
    }
    )
  }

const getCourse = async () => {
  try{
    const response = await clientAxios.get(`/course/find/${course.id}`);
    await setCourse(response.data);    
  } catch (error) {
    console.error(error);
    notSucceed();
  }
};

const openWindow = (response) => {
  let res = response.data.urlPayment;
  let win = window.open(res, '_blank');
  win.focus();
}


 const pago = async () => {
  try{
     const response = await clientAxios.post('/mp/createAndRedirect', {
      idUser: user.userId,
      idCourse: course.id
    })
    success();
    openWindow(response)
  }catch(error){
    console.log(error)
    notSucceed();
  }
}

useEffect( () => {
  getCourse();
}, [])


  return (
    
    <div className="grid grid-cols-1 lg:grid-cols-2 gap-3 bg-white shadow-[0_3px_6px_rgb(73,70,151)] rounded-[5px] p-0">
      <div className="bg-dark-steal border border-solid border-dark-steal pb-4">
        <h3 className="font-sans text-2xl lg:text-3xl text-[#fafafa] font-medium mt-9 ml-5 md:ml-8 lg:ml-10 mr-4">{course.nameCourse}</h3>
        <p className="font-sans text-xl lg:text-2xl text-[#fafafa] font-normal mt-9 mx-5 md:mx-8 lg:mx-10">
          {course.description}
        </p>
      </div>
      <div>
        <h3 className="font-sans font-medium text-2xl lg:text-3xl text-[#707070] mt-4 md:mt-28 ml-5 md:ml-8 lg:ml-10 py-0 lg:py-16">Pagar</h3>
        <div className="flex">
          <h4 className="font-sans text-xl md:text-2xl lg:text-3xl text-[#707070] mt-14 ml-5 md:ml-8 lg:ml-10">{course.nameCourse}</h4>
          <h4 className="font-sans text-xl sm:text-2xl lg:text-3xl text-[#707070] mt-14 ml-5 mr-10 lg:ml-24">$ { course.price} </h4>         
        </div>      
        <Dropdown1 pago={pago}  />
        <div className="mt-36 mb-36">
        </ div>
      </div>
    </div>
  );
};

export default Payment;

