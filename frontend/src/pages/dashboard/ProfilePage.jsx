import { useContext } from "react";
import Profile from "../../components/Dashboard/Profile/Profile";
import clientAxios from "../../config/axios";
import Swal from "sweetalert2";
import withReactContent from "sweetalert2-react-content";
import AuthContext from "../../context/authentication/AuthContext";

const MySwal = withReactContent(Swal);

const ProfilePage = () => {
  const authContext = useContext(AuthContext);
  const { state, updateUserData } = authContext;
  const userData = state.user

  const successAlert = () => {
    MySwal.fire({
      position: "center",
      icon: "success",
      title: "Datos guardados correctamente.",
      showConfirmButton: false,
      timer: 1500,
    });
  };

  const updateUser = async (values) => {
    try {
      await clientAxios.put("/user/modify", {
        email: values.email,
        firstName: values.firstName,
        lastName: values.lastName,
        phone: values.phone,
        photo: values.photo,
        password: values.password,
        userId: values.userId,
      });
      successAlert();
      updateUserData()
    } catch (error) {
      console.error(error);
    }
  };

  return <Profile userData={userData} updateUser={updateUser} />;
};
export default ProfilePage;
