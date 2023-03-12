import { useRef } from "react";
import { ErrorMessage, Form, Formik } from "formik";
import * as yup from "yup";
import CustomTextField from "../../CustomTextField";
import clipIcon from "../../../assets/images/clip-icon.png";
import PreviewImage from "../../PreviewImage/PreviewImage";

const Profile = ({ userData, updateUser }) => {
  const renderError = (message) => (
    <p className="italic text-red-600">{message}</p>
  );

  // console.log(userData);

  const fileRef = useRef(null);
  const phoneRegExp =
    /^((\\+[1-9]{1,4}[ \\-]*)|(\\([0-9]{2,3}\\)[ \\-]*)|([0-9]{2,4})[ \\-]*)*?[0-9]{3,4}?[ \\-]*[0-9]{3,4}?$/;

  const ValidationSchema = yup.object().shape({
    firstName: yup.string().required("Nombre es un campo obligatorio"),
    lastName: yup.string().required("Apellido es un campo obligatorio"),
    phone: yup.string().matches(phoneRegExp, "Número de teléfono no válido"),
    email: yup.string().required().email(),
    password: yup.string()
    .min(
      8,
      "La contraseña es demasiado corta - debe tener un mínimo de 8 caracteres."
    ),
    confirmPasswd: yup.string().oneOf(
      [yup.ref("password"), null],
      "Las contraseñas deben coincidir"
    ),
    // photo: yup
    //   .mixed()
    //   .test("fileSize", "Imagen demasiado grande.", (value) => {
    //       return value && value.size <= 5242880; //5242880= 5mb
    //   })
    //   .test(
    //     "type",
    //     "Solo se aceptan los siguientes formatos: .jpeg, .jpg, .png",
    //     (value) => {
    //       return (
    //         value && (value.type === "image/jpeg" || value.type === "image/png")
    //       );
    //     }
    //   ),
  });

  return (
    <>
      <h1 className="text-2xl text-primary mb-9">Mí Perfil</h1>
      {userData.firstName ? (
        <Formik
          initialValues={{
            firstName: userData.firstName == null ? "" : userData.firstName,
            lastName: userData.lastName == null ? "" : userData.lastName,
            phone: userData.phone == null ? "" : userData.phone,
            email: userData.email == null ? "" : userData.email,
            password: "",
            confirmPasswd: "",
            photo: "", //userData.photo === null ? "" : userData.photo,
            userId: userData.userId == null ? "" : userData.userId,
          }}
          validationSchema={ValidationSchema}
          onSubmit={(values) => {
            updateUser(values);
          }}
        >
          {({ values, setFieldValue }) => (
            <Form className="flex flex-col w-full bg-white border border-primary rounded-md px-8 py-8 mb-16">
              <div className="mb-2">
                <CustomTextField
                  name="firstName"
                  id="firstName"
                  type="text"
                  label="Nombre"
                />
              </div>
              <div className="mb-2">
                <CustomTextField
                  name="lastName"
                  id="lastName"
                  type="text"
                  label="Apellido"
                />
              </div>
              <div className="mb-2">
                <CustomTextField
                  name="phone"
                  id="phone"
                  type="text"
                  label="Teléfono"
                />
              </div>
              <div className="mb-2">
                <CustomTextField
                  name="email"
                  id="email"
                  type="text"
                  label="Correo electrónico"
                  autoComplete="email"
                />
              </div>
              <div className="mb-2">
                <CustomTextField
                  name="password"
                  id="password"
                  type="password"
                  label="Contraseña"
                  autoComplete="new-password"
                />
              </div>
              <div className="mb-2">
                <CustomTextField
                  name="confirmPasswd"
                  id="confirmPasswd"
                  type="password"
                  label="Confirmar Contraseña"
                  autoComplete="new-password"
                />
              </div>
              <div className="flex align-middle m-2">
                <label
                  htmlFor="photo"
                  className="text-primary text-2xl cursor-pointer"
                >
                  Adjuntar foto
                </label>
                <input
                  ref={fileRef}
                  hidden
                  type="file"
                  name="photo"
                  id="photo"
                  onChange={(event) => {
                    setFieldValue("photo", event.target.files[0]);
                  }}
                />
                <button
                  type="button"
                  className="mx-2 hover:opacity-60"
                  onClick={() => fileRef.current.click()}
                >
                  <img src={clipIcon} alt="clipIcon" />
                </button>
                <ErrorMessage name="photo" render={renderError} />
              </div>
              <div>{values.photo && <PreviewImage photo={values.photo} />}</div>
              <div className="flex justify-center lg:justify-end">
                <button
                  className="rounded-md w-64 h-16 bg-secondary font-medium text-white text-2xl mt-4 mb-2 p-2 hover:opacity-60"
                  type="submit"
                >
                  Guardar
                </button>
              </div>
            </Form>
          )}
        </Formik>
      ) : (
        "Loading..."
      )}
    </>
  );
};
export default Profile;
