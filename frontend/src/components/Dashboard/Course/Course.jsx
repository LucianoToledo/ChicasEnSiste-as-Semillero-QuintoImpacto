import React, {useState, useEffect} from 'react';
import clientAxios from '../../../config/axios';
import {useParams} from 'react-router-dom';
import RightSidebar from '../../RightSidebar';
import Video from '../Video/index';
import Swal from 'sweetalert2';
import withReactContent from 'sweetalert2-react-content';

//import course from './Course.module.css';

const Course = () => {

  const{id} = useParams();

  const [inscription, setInscription] = useState({
    courseName: '',
    courseId: ''
  });

  const [course, setCourse] = useState({
    id: '',
    nameCourse: '',
    description: '',
    modules: [
      {
        id: '',
        name: '',
        description: '',
        videosCourse: [
          {
            id: '',
            name: '',
            url: ''
          }
        ]
    }
    ],
  })

  const MySwal = withReactContent(Swal);

  const [video, setVideo] = useState ({})

  const notSucceed = () => {
    MySwal.fire({
      title: 'Error!',
      text: 'Por favor, intente nuevamente más tarde.',
      icon: 'error',
      confirmButtonText: 'Ok'
    })
  }

  const getDataCourse = async () => {
    try{
      const inscriptionResponse = await clientAxios.get(`/inscription/${id}`)
      setInscription(inscriptionResponse.data)
           
      const courseResponse = await clientAxios.get(`/course/find/${inscriptionResponse.data.courseId}`);
      setCourse(courseResponse.data)
           
    }catch(error){
      console.error(error);
      notSucceed();
    }
  }

  useEffect(() => {
    getDataCourse()
  }, []);
  
  return (
    <div className='grid grid-cols-4'>    
      <div className="col-span-3 bg-[#FAFAFA]">
        <h2 className="font-sans font-bold text-[35px] text-dark-steal"> {inscription.courseName}</h2>
        <Video video = {video} />
      </div>
      <div className=''>
        <RightSidebar course={course} setVideo={setVideo} />
      </div>
    </div>
       
    )
}

export default Course;