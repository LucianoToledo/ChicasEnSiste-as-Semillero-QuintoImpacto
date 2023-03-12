import { useContext, useEffect, useState } from "react";
import CreateCourseForm from "../../components/Dashboard/CrudCourse/CreateCourseForm";
import UpdateCourseForm from "../../components/Dashboard/CrudCourse/UpdateCourseForm/UpdateCourseForm";
import Modal from "../../components/Modal";
import SkeletonTable from "../../components/SkeletonTable/SkeletonTable";
import Table from "../../components/Table";
import clientAxios from "../../config/axios";
import Swal from "sweetalert2";
import withReactContent from "sweetalert2-react-content";
import AuthContext from "../../context/authentication/AuthContext";
import Paginate from "../../components/Paginate/Paginate";

const MySwal = withReactContent(Swal);

const column = [
  // { heading: "ID", value: "id" },
  { heading: "Nombre", value: "nameCourse" },
  { heading: "Descripción", value: "description" },
  { heading: "Precio", value: "price" },
  { heading: "Duración", value: "duration" },
  { heading: "Imagen", value: "photo" },
];

const coursePerPage = 10

const CreateCoursePage = () => {
  const authContext = useContext(AuthContext);
  const { state } = authContext;

  const [data, setData] = useState([]);
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [currentCourse, setCurrentCourse] = useState({
    id: "",
    nameCourse: "",
    description: "",
    price: "",
    duration: "",
    photo: "",
  });
  const [pageCount, setPageCount] = useState(0)
  const [pageNumber, setPageNumber] = useState(0)

  const {user} = state

  const getCourses = async () => {
    setIsLoading(true);
    try {
      const response = await clientAxios.get("/course/list");
      const orderData = response.data.courses.sort(function (a, b) {
        if (a.nameCourse > b.nameCourse) {
          return 1;
        }
        if (a.nameCourse < b.nameCourse) {
          return -1;
        }
        // a must be equal to b
        return 0;
      });
      const filterSoftDelete = orderData.filter(
        (item) => item.softDelete !== true
      );
      setPageCount(filterSoftDelete.length > coursePerPage ? Math.ceil(filterSoftDelete.length/coursePerPage) : 0)
      const pagedVisited= pageNumber * coursePerPage
      await setData(filterSoftDelete.slice(pagedVisited , pagedVisited + coursePerPage));
    } catch (error) {
      console.error(error);
      setError("Error en la petición de cursos");
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    getCourses();
  }, [pageNumber]);

  const createCourse = async (values) => {
    setIsLoading(true);
    try {
      await clientAxios.post("/course/create", {
        nameCourse: values.name,
        description: values.description,
        price: values.price,
        duration: values.duration,
        userAdminId: user.userId,
      });
      successAlert();
      getCourses();
    } catch (error) {
      console.error(error);
      setError("Error al crear el curso");
    } finally {
      setIsLoading(false);
    }
  };

  const updateCourse = async (values) => {
    setIsLoading(true);
    try {
      await clientAxios.put("/course/modify", {
        id: values.id,
        name: values.name,
        description: values.description,
        price: values.price,
        duration: values.duration,
      });
      successAlert();
      getCourses();
    } catch (error) {
      console.error(error);
      setError("Error al modificar el curso");
    } finally {
      setIsLoading(false);
    }
  };

  const deleteCourse = async (values) => {
    setIsLoading(true);
    try {
      await clientAxios.put("/course/disable/" + values.id);
      getCourses();
    } catch (error) {
      console.error(error);
      setError("Error al eliminar el curso");
    } finally {
      setIsLoading(false);
    }
  };

  const deleteAlert = (values) => {
    MySwal.fire({
      title: "Estas seguro?",
      text: "No podrás revertir esto!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Sí",
      cancelButtonText: "Cancelar",
    }).then((result) => {
      if (result.isConfirmed) {
        MySwal.fire("Eliminado!", "El curso ha sido eliminado.", "success");
        deleteCourse(values);
      }
    });
  };

  const successAlert = () => {
    MySwal.fire({
      position: "center",
      icon: "success",
      title: "Curso guardado correctamente.",
      showConfirmButton: false,
      timer: 1500,
    });
  };

  const openModal = (type, selectedCourse) => {
    if (type === "create") {
      setShowCreateModal(true);
    }
    if (type === "edit") {
      setCurrentCourse(selectedCourse);
      setShowUpdateModal(true);
    }
    if (type === "delete") {
      deleteAlert(selectedCourse);
    }
  };

  const handlePageClick = ({selected}) => {
    setPageNumber(selected)
  }

  return (
    <div className="container max-w-7xl mx-auto mt-8">
      <div className="mb-4">
        <h1 className="font-sans text-4xl text-primary">Cursos</h1>
        <div className="flex justify-end">
          <button
            onClick={() => openModal("create")}
            className="px-4 py-2 rounded-md bg-secondary text-white text-xl hover:opacity-30"
          >
            Crear Curso
          </button>
        </div>
      </div>
      <div className="flex flex-col">
        <div className="overflow-x-auto sm:-mx-6 sm:px-6 lg:-mx-8 lg:px-8">
          <div className="inline-block min-w-full overflow-hidden align-middle border-b border-gray-200 shadow sm:rounded-lg">
            {error && error}
            {isLoading ? (
              <SkeletonTable />
            ) : (
              <>
                <Table
                  data={data}
                  column={column}
                  openModal={openModal}
                  setCurrentCourse={setCurrentCourse}
                />
                <div className="flex justify-center my-4">
                  <Paginate
                    pageCount={pageCount}
                    onPageChange={handlePageClick}
                    pageRangeDisplayed={coursePerPage}
                    initialPage={pageNumber > -1 && pageNumber}
                />
                </div>
              </>
            )}
          </div>
        </div>
      </div>
      <Modal
        showModal={showCreateModal}
        setShowModal={setShowCreateModal}
        title={"Crear Curso"}
      >
        <div className="relative p-6 flex-auto">
          <CreateCourseForm
            setShowModal={setShowCreateModal}
            createCourse={createCourse}
          />
        </div>
      </Modal>
      <Modal
        showModal={showUpdateModal}
        setShowModal={setShowUpdateModal}
        title={"Editar Curso"}
      >
        <div className="relative p-6 flex-auto">
          <UpdateCourseForm
            setShowModal={setShowUpdateModal}
            updateCourse={updateCourse}
            currentCourse={currentCourse}
          />
        </div>
      </Modal>
    </div>
  );
};
export default CreateCoursePage;
