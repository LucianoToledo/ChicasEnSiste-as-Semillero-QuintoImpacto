import React, {useEffect, useState} from 'react';
import Cards from './Cards';
import iconBrain from '../../../assets/images/cursos.png';
import clientAxios from '../../../config/axios';
import Swal from 'sweetalert2';
import withReactContent from 'sweetalert2-react-content';

const Courses = () => {

  const MySwal = withReactContent(Swal);

  const[cards, setCards] = useState([{
  icon: iconBrain,
  title: "",
  description: "",
  price: "",
  id:""
  }]);
  
  const notSucceed = () => {
    MySwal.fire({
      title: 'Error!',
      text: 'Por favor, intente nuevamente más tarde.',
      icon: 'error',
      confirmButtonText: 'Ok'
    })
  }

  const getData = async () => {
    try{
      const response = await clientAxios.get("/course/list");
      const courses = response.data.courses;
      
      {courses.map( (course)=>{
        course.icon = iconBrain;
      })
      setCards(courses)
      }
  }catch(error){
    console.error(error);
    notSucceed();
  }
  }

  useEffect (()=>{
    getData();
  }, []);

  return (
    <div>
      <div>
      <h3 className="font-sans font-medium text-dark-steal mb-8 text-2xl md:text-3xl lg:text-4xl">Cursos</h3>
      <Cards cards={cards} />
    </div>
    </div>
  )
}

export default Courses;