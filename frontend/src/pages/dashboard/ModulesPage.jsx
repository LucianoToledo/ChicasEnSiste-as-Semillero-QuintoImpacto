import { useEffect, useState } from "react";
import Table from "../../components/Table";
import clientAxios from "../../config/axios";
import Modal from "../../components/Modal";
import CreateModuleForm from "../../components/Dashboard/CrudModules/CreateModuleForm";
import UpdateModuleForm from "../../components/Dashboard/CrudModules/UpdateModuleForm";
import Swal from "sweetalert2";
import withReactContent from "sweetalert2-react-content";
import SkeletonTable from "../../components/SkeletonTable/SkeletonTable";
import Paginate from "../../components/Paginate/Paginate";

const MySwal = withReactContent(Swal);

const column = [
  // { heading: "ID", value: "id" },
  { heading: "Nombre", value: "name" },
  { heading: "Descripción", value: "description" },
  { heading: "Cursos", value: "courseName" },
];

const modulePerPage = 10

const ModulesPage = () => {
  const [data, setData] = useState([]);
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [currentModule, setCurrentModule] = useState({
    id: "",
    name: "",
    description: "",
  });
  const [courses, setCourses] = useState();
  const [pageCount, setPageCount] = useState(0)
  const [pageNumber, setPageNumber] = useState(0)

  const getModules = async () => {
    setIsLoading(true);
    try {
      const response = await clientAxios.get("/module/get");
      const orderData = response.data.responseList.sort(function (a, b) {
        if (a.name > b.name) {
          return 1;
        }
        if (a.name < b.name) {
          return -1;
        }
        // a must be equal to b
        return 0;
      });
      const filterSoftDelete = orderData.filter(
        (item) => item.softDelete !== true
      );
      setPageCount(filterSoftDelete.length > modulePerPage ? Math.ceil(filterSoftDelete.length/modulePerPage) : 0)
      const pagedVisited= pageNumber * modulePerPage
      await setData(filterSoftDelete.slice(pagedVisited , pagedVisited + modulePerPage));
    } catch (error) {
      console.error(error);
      setError("Error en la petición de módulos");
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    getModules();
    getCourses();
  }, [pageNumber]);

  const createModule = async (values) => {
    setIsLoading(true);
    try {
      if (values.idModulo === "") {
        await clientAxios.post("/module/create", {
          name: values.name,
          description: values.description,
          idCourse: "",
        });
      } else {
        const responseCreate = await clientAxios.post("/module/create", {
          name: values.name,
          description: values.description,
          idCourse: "",
        });
        const responseAddvideo = await clientAxios.put("/course/addModule", {
          idCourse: values.idCourse,
          idModule: responseCreate.data.id,
        });
      }
      successAlert();
      getModules();
    } catch (error) {
      console.error(error);
      setError("Error al crear un módulo");
    } finally {
      setIsLoading(false);
    }
  };

  const updateModule = async (values, idCourse) => {
    setIsLoading(true);
    try {
      await clientAxios.put("/module/modify/id", {
        id: values.id,
        name: values.name,
        description: values.description,
      });

      if (values.idCourse === "" || values.idCourse === idCourse) {
        await clientAxios.put("/module/modify/id", {
          id: values.id,
          name: values.name,
          description: values.description,
        });
      } else {
        const responseCreate = await clientAxios.put("/module/modify/id", {
          id: values.id,
          name: values.name,
          description: values.description,
        });
        if (idCourse) {
          const responseRemoveVideo = await clientAxios.put(
            "/course/removeModule",
            {
              idCourse: idCourse,
              idModule: values.id,
            }
          );
        }
        const responseAddVideo = await clientAxios.put("/course/addModule", {
          idCourse: values.idCourse,
          idModule: values.id,
        });
      }
      successAlert();
      getModules();
    } catch (error) {
      console.error(error);
      setError("Error al modificar un módulo");
    } finally {
      setIsLoading(false);
    }
  };

  const deleteModule = async (values) => {
    setIsLoading(true);
    try {
      await clientAxios.put("/module/disable/" + values.id);
      getModules();
    } catch (error) {
      console.error(error);
      setError("Error al eliminar un módulo");
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
        MySwal.fire("Eliminado!", "El módulo ha sido eliminado.", "success");
        deleteModule(values);
      }
    });
  };

  const successAlert = () => {
    MySwal.fire({
      position: "center",
      icon: "success",
      title: "Modulo guardado correctamente.",
      showConfirmButton: false,
      timer: 1500,
    });
  };

  const openModal = (type, selectedModule) => {
    if (type === "create") {
      setShowCreateModal(true);
    }
    if (type === "edit") {
      setCurrentModule(selectedModule);
      setShowUpdateModal(true);
    }
    if (type === "delete") {
      deleteAlert(selectedModule);
    }
  };

  const getCourses = async () => {
    setIsLoading(true);
    try {
      const response = await clientAxios.get("/course/list");
      const orderData = response.data.courses.sort(function (a, b) {
        if (a.name > b.name) {
          return 1;
        }
        if (a.name < b.name) {
          return -1;
        }
        // a must be equal to b
        return 0;
      });
      const filterSoftDelete = orderData.filter(
        (item) => item.softDelete !== true
      );
      await setCourses(filterSoftDelete);
    } catch (error) {
      console.error(error);
      setError("Error en la petición de cursos");
    } finally {
      setIsLoading(false);
    }
  };

  const handlePageClick = ({selected}) => {
    setPageNumber(selected)
  }

  return (
    <div className="container max-w-7xl mx-auto mt-8">
      <div className="mb-4">
        <h1 className="font-sans text-4xl text-primary">Modulos</h1>
        <div className="flex justify-end">
          <button
            onClick={() => openModal("create")}
            className="px-4 py-2 rounded-md bg-secondary text-white text-xl hover:opacity-30"
          >
            Crear Modulo
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
                  setCurrentModule={setCurrentModule}
                />
                <div className="flex justify-center my-4">
                  <Paginate
                    pageCount={pageCount}
                    onPageChange={handlePageClick}
                    pageRangeDisplayed={modulePerPage}
                    forcePage={pageNumber > -1 && pageNumber}
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
        title={"Crear Modulo"}
      >
        <div className="relative p-6 flex-auto">
          <CreateModuleForm
            setShowModal={setShowCreateModal}
            createModule={createModule}
            courses={courses}
          />
        </div>
      </Modal>
      <Modal
        showModal={showUpdateModal}
        setShowModal={setShowUpdateModal}
        title={"Editar Modulo"}
      >
        <div className="relative p-6 flex-auto">
          <UpdateModuleForm
            setShowModal={setShowUpdateModal}
            updateModule={updateModule}
            currentModule={currentModule}
            courses={courses}
          />
        </div>
      </Modal>
    </div>
  );
};

export default ModulesPage;
