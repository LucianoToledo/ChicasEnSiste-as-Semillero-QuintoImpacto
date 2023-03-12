import React, {useState, useEffect, useContext} from 'react';
import Cards from './Cards';
import iconBrain from '../../../assets/images/cursos.png';
import clientAxios from '../../../config/axios';
import AuthContext from '../../../context/authentication/AuthContext';
import Swal from 'sweetalert2';
import withReactContent from 'sweetalert2-react-content';



const MyCourses = () => {

  const authContext = useContext(AuthContext);
  const { state } = authContext;
  const { user } = state;

  const userId = user.userId;

  const MySwal = withReactContent(Swal);

  const notCoursesFound = () => {
    MySwal.fire({
      title: 'Usted no se encuentra inscripto en ningún curso!',
      text: 'Aún no ha adquirido ningún curso. Visite "Cursos" y escoja un curso!',
      icon: 'error',
      confirmButtonText: 'Ok'
    })
  }

  const notSucceed = () => {
    MySwal.fire({
      title: 'Error!',
      text: 'Por favor, intente nuevamente más tarde.',
      icon: 'error',
      confirmButtonText: 'Ok'
    })
  }

  const[cards, setCards] = useState([{
    icon: iconBrain,
    title: "",
    id: "",
    courseId:""
  }])

  const getCourseData = async () => {
    try{
      const response = await clientAxios.get(`/inscription/user/${userId}`); 
      const courses = response.data.inscriptionResponseList;

      {courses.map( (course) => {
        course.icon = iconBrain;
      })}
      await setCards(courses);
    }catch(error){
      console.error(error)
      notSucceed();
    }
  }
 
  useEffect(()=>{
    getCourseData()
  }, []);
 
  return (
    <div className="container bg-[#FAFAFA]">
        <h3 className="mx-auto font-sans text-[35px] text-dark-steal font-medium text-left tracking-normal opacity-100 ml-0 lg:ml-10 mb-4">Mis cursos</h3>

        { cards.length !== 0 ? <Cards cards={cards} /> : notCoursesFound() 
        }         
        
    </div>
  )
}

export default MyCourses