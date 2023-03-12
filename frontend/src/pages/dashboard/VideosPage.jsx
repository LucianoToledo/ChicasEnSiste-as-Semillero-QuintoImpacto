import { useEffect, useState } from "react";
import Table from "../../components/Table";
import clientAxios from "../../config/axios";
import Modal from "../../components/Modal";
import CreateVideoForm from "../../components/Dashboard/CrudVideos/CreateVideoForm";
import UpdateVideoForm from "../../components/Dashboard/CrudVideos/UpdateVideoForm";
import Swal from "sweetalert2";
import withReactContent from "sweetalert2-react-content";
import SkeletonTable from "../../components/SkeletonTable/SkeletonTable";
import Paginate from "../../components/Paginate/Paginate";

const MySwal = withReactContent(Swal);

const column = [
  // { heading: "ID", value: "id" },
  { heading: "URL", value: "url" },
  { heading: "Nombre", value: "name" },
  { heading: "Duración", value: "duration" },
  { heading: "Modulo", value: "moduleName" },
];

const videoPerPage = 10

const VideosPage = () => {
  const [data, setData] = useState([]);
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [currentVideo, setCurrentVideo] = useState({
    id: "",
    name: "",
    description: "",
  });
  const [modules, setModules] = useState();
  const [pageCount, setPageCount] = useState(0)
  const [pageNumber, setPageNumber] = useState(0)

  const getVideos = async () => {
    setIsLoading(true);
    try {
      const response = await clientAxios.get("/video/list");
      const orderData = response.data.videoResponses.sort(function (a, b) {
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
      setPageCount(filterSoftDelete.length > videoPerPage ? Math.ceil(filterSoftDelete.length/videoPerPage) : 0)
      const pagedVisited= pageNumber * videoPerPage
      await setData(filterSoftDelete.slice(pagedVisited , pagedVisited + videoPerPage));
    } catch (error) {
      console.error(error);
      setError("Error en la petición de videos");
    } finally {
      setIsLoading(false);
    }
  };

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
      await setModules(filterSoftDelete);
    } catch (error) {
      console.error(error);
      setError("Error en la petición del módulo");
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    getVideos();
    getModules();
  }, [pageNumber]);

  const createVideo = async (values) => {
    setIsLoading(true);
    try {
      if (values.idModulo === "") {
        await clientAxios.post("/video/create", {
          url: values.url,
          name: values.name,
          duration: values.duration,
          idModulo: "",
        });
      } else {
        const responseCreate = await clientAxios.post("/video/create", {
          url: values.url,
          name: values.name,
          duration: values.duration,
          idModulo: "",
        });
        const responseAddvideo = await clientAxios.put("/module/addVideo", {
          idVideo: responseCreate.data.id,
          idModulo: values.idModulo,
        });
      }

      successAlert();
      getVideos();
    } catch (error) {
      console.error(error);
      setError("Error en la creación del video");
    } finally {
      setIsLoading(false);
    }
  };

  const updateVideo = async (values, idModulo) => {
    setIsLoading(true);
    try {
      if (values.idModulo === "" || values.idModulo === idModulo) {
        await clientAxios.put("/video/modify/id", {
          id: values.id,
          url: values.url,
          name: values.name,
          duration: values.duration,
        });
      } else {
        const responseCreate = await clientAxios.put("/video/modify/id", {
          id: values.id,
          url: values.url,
          name: values.name,
          duration: values.duration,
        });
        if (idModulo) {
          const responseRemoveVideo = await clientAxios.put(
            "/module/removeVideo",
            {
              idModulo: idModulo,
              idVideo: values.id,
            }
          );
        }
        const responseAddVideo = await clientAxios.put("/module/addVideo", {
          idVideo: values.id,
          idModulo: values.idModulo,
        });
      }
      successAlert();
      getVideos();
    } catch (error) {
      console.error(error);
      setError("Error al modificar el video");
    } finally {
      setIsLoading(false);
    }
  };

  const deleteVideo = async (values) => {
    setIsLoading(true);
    try {
      await clientAxios.put("/video/disable/" + values.id);
      getVideos();
    } catch (error) {
      console.error(error);
      setError("Error al borrar el videos");
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
        MySwal.fire("Eliminado!", "El video ha sido eliminado.", "success");
        deleteVideo(values);
      }
    });
  };

  const successAlert = () => {
    MySwal.fire({
      position: "center",
      icon: "success",
      title: "Video guardado correctamente.",
      showConfirmButton: false,
      timer: 1500,
    });
  };

  const openModal = (type, selectedVideo) => {
    if (type === "create") {
      setShowCreateModal(true);
    }
    if (type === "edit") {
      setCurrentVideo(selectedVideo);
      setShowUpdateModal(true);
    }
    if (type === "delete") {
      deleteAlert(selectedVideo);
    }
  };

  const handlePageClick = ({selected}) => {
    setPageNumber(selected)
  }

  return (
    <div className="container max-w-7xl mx-auto mt-8">
      <div className="mb-4">
        <h1 className="font-sans text-4xl text-primary">Videos</h1>
        <div className="flex justify-end">
          <button
            onClick={() => openModal("create")}
            className="px-4 py-2 rounded-md bg-secondary text-white text-xl hover:opacity-30"
          >
            Cargar Video
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
                  setCurrentVideo={setCurrentVideo}
                />
                <div className="flex justify-center my-4">
                  <Paginate
                    pageCount={pageCount}
                    onPageChange={handlePageClick}
                    pageRangeDisplayed={videoPerPage}
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
        title={"Cargar Video"}
      >
        <div className="relative p-6 flex-auto">
          <CreateVideoForm
            setShowModal={setShowCreateModal}
            createVideo={createVideo}
            modules={modules}
          />
        </div>
      </Modal>
      <Modal
        showModal={showUpdateModal}
        setShowModal={setShowUpdateModal}
        title={"Editar Video"}
      >
        <div className="relative p-6 flex-auto">
          <UpdateVideoForm
            setShowModal={setShowUpdateModal}
            updateVideo={updateVideo}
            currentVideo={currentVideo}
            modules={modules}
          />
        </div>
      </Modal>
    </div>
  );
};

export default VideosPage;
