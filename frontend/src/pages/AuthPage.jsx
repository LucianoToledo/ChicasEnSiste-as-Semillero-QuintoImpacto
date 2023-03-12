import { useContext, useEffect, useRef, useState } from "react";
import Auth from "../components/Auth";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import logo from "../assets/images/logo2.png";
import googleIcon from "../assets/images/google-icon.png";
import { Link } from "react-router-dom";
import AuthContext from "../context/authentication/AuthContext";
import { useGoogleLogin } from "@react-oauth/google";
import axios from "axios";
import clientAxios from "../config/axios";
import tokenAuth from "../config/tokenAuth";
import { ENV } from "../environment/environment";
import Swal from "sweetalert2";
import withReactContent from "sweetalert2-react-content";

const MySwal = withReactContent(Swal);

const SignupSchema = Yup.object().shape({
  firstName: Yup.string()
    .min(2, "Demasiado corto!")
    .max(50, "Demasiado largo!")
    .required("Requerido"),
  lastName: Yup.string()
    .min(2, "Demasiado corto!")
    .max(50, "Demasiado largo!")
    .required("Requerido"),
  email: Yup.string().email("Correo inválido").required("Requerido"),
  password: Yup.string()
    .required("Requerido")
    .min(
      8,
      "La contraseña es demasiado corta - debe tener un mínimo de 8 caracteres."
    ),
  confirmPasswd: Yup.string().oneOf(
    [Yup.ref("password"), null],
    "Las contraseñas deben coincidir"
  ),
});

const SigninSchema = Yup.object().shape({
  emailLogin: Yup.string().email("Correo inválido").required("Requerido"),
  passwordLogin: Yup.string()
    .required("Requerido")
    .min(
      8,
      "La contraseña es demasiado corta - debe tener un mínimo de 8 caracteres."
    ),
});

const AuthPage = () => {
  const [message, setMessage] = useState(null);
  const googleButton = useRef(null);
  const authContext = useContext(AuthContext);
  const { login } = authContext;

  useEffect(() => {
    if(message !== null) {
      MySwal.fire({
        icon: 'error',
        title: 'Oops...',
        text: message.msg,
      })
    }
  }, [message])
  

  const googleLogin = useGoogleLogin({
    onSuccess: async (codeResponse) => {
      try {
        const userGoogle = await axios.post(
          ENV.GOOGLE_URL_BACKEND,
          { value: codeResponse.code }
        );
        await tokenAuth(userGoogle.data.token)
        const responseUser = await clientAxios.get("/user/me");
        const data = await {
          user: {
            userId: responseUser.data.userId,
            firstName: responseUser.data.firstName,
            lastName: responseUser.data.lastName,
            email: responseUser.data.email,
            phone: responseUser.data.phone,
            photo: "",
            role: responseUser.data.role
          } || null,
          token: userGoogle.data.token,
          resetToken: null,
        }
        await login(data.user,data.token)
      } catch (error) {
        const alert = {
          msg: error.response.data.messages[0],
        };
        setMessage(alert);
      }
    },
    flow: "auth-code",
    onError: (errorResponse) => console.error(errorResponse),
  });

  const getAuth = async (data) => {
    const parseData = {
      email: data.emailLogin,
      password: data.passwordLogin,
      remember: data.remember,
    };

    try {
      const response = await clientAxios.post("/auth/login", parseData);
      await tokenAuth(response.data.token)
      const responseUser = await clientAxios.get("/user/me");
      const data = await {
        user: {
          userId: responseUser.data.userId,
          firstName: responseUser.data.firstName,
          lastName: responseUser.data.lastName,
          email: responseUser.data.email,
          phone: responseUser.data.phone,
          photo: "",
          role: responseUser.data.role
        } || null,
        token: response.data.token,
        resetToken: null,
      }
      await login(data.user, data.token)
    } catch (error) {
      const alert = {
        msg: error.response.data.messages[0],
      };
      console.log(alert);
      setMessage(alert);
    }
  };

  const registerUser = async (data) => {
    try {
      const response = await clientAxios.post("/auth/register", data);
      await login(response.data, response.data.token)
    } catch (error) {
      const alert = {
        msg: error.response.data.messages[0],
      };
      setMessage(alert);
      window.localStorage.removeItem("Authorization");
    }
  };

  return (
    <div className="md:container md:mx-auto flex flex-col justify-center items-center h-screen sm:h-full bg-primary sm:bg-white">
      <div className="grid lg:grid-cols-2 w-full max-w-7xl h-full max-h-[758px] sm:border sm:border-[#5E17EB] sm:rounded-md sm:my-5">
        <div className="hidden lg:flex lg:justify-center lg:items-center lg:animate-fadeAndScale origin-center">
          <Link to="/">
            <img
              src={logo}
              alt="logo"
              height="auto"
              width="100%"
              className="max-w-[582px] sm:px-36"
            />
          </Link>
        </div>
        <Auth>
          <div label="Registrarme">
            <div className="mb-7 mt-4 sm:mt-9">
              <h1 className="font-bold text-center text-5xl text-white">
                Registrate
              </h1>
            </div>
            <Formik
              initialValues={{
                firstName: "",
                lastName: "",
                email: "",
                password: "",
                confirmPasswd: "",
              }}
              validationSchema={SignupSchema}
              onSubmit={(values, { resetForm }) => {
                registerUser(values);
                resetForm();
              }}
            >
              {({ isSubmitting }) => (
                <Form className="max-w-[90%] sm:max-w-[80%] mx-auto">
                  <div className="flex flex-col mb-10p">
                    <Field
                      type="text"
                      id="firstName"
                      name="firstName"
                      className="form-control"
                      placeholder="Nombre"
                      aria-label="input nombre"
                    />
                    <ErrorMessage
                      name="firstName"
                      component="div"
                      className="font-sans block text-red-400"
                    />
                  </div>

                  <div className="flex flex-col mb-10p">
                    <Field
                      type="text"
                      id="lastName"
                      name="lastName"
                      className="form-control"
                      placeholder="Apellido"
                      aria-label="input apellido"
                    />
                    <ErrorMessage
                      name="lastName"
                      component="div"
                      className="font-sans block text-red-400"
                    />
                  </div>

                  <div className="flex flex-col mb-10p">
                    <Field
                      type="text"
                      id="email"
                      name="email"
                      className="form-control"
                      placeholder="Correo electrónico"
                      aria-label="input correo"
                    />
                    <ErrorMessage
                      name="email"
                      component="div"
                      className="font-sans block text-red-400"
                    />
                  </div>

                  <div className="flex flex-col mb-10p">
                    <Field
                      type="password"
                      id="password"
                      name="password"
                      autoComplete="off"
                      className="form-control"
                      placeholder="Contraseña"
                      aria-label="input contraseña"
                    />
                    <ErrorMessage
                      name="password"
                      component="div"
                      className="font-sans block text-red-400"
                    />
                  </div>

                  <div className="flex flex-col mb-10p">
                    <Field
                      type="password"
                      id="confirmPasswd"
                      name="confirmPasswd"
                      autoComplete="off"
                      className="form-control"
                      placeholder="Repetir Contraseña"
                      aria-label="input repetir contraseña"
                    />
                    <ErrorMessage
                      name="confirmPasswd"
                      component="div"
                      className="font-sans block text-red-400"
                    />
                  </div>

                  <button
                    type="submit"
                    disabled={isSubmitting}
                    className="w-full h-14 text-white text-2xl cursor-pointer bg-secondary border-none rounded-md mt-14 mb-4 font-bold text-center no-underline tracking-[1px] hover:opacity-90"
                  >
                    Registrarme
                  </button>
                </Form>
              )}
            </Formik>
            <button
              id="signIn"
              className="flex justify-center mb-4 sm:mb-12 items-center w-[90%]  sm:w-[80%] h-14 m-auto bg-primary text-white border rounded-md font-bold text-xl no-underline hover:bg-white hover:text-primary hover:cursor-pointer"
              onClick={() => googleLogin()}
            >
              Registrarme con Google
              <span className="ml-7">
                <img
                  src={googleIcon}
                  alt="google"
                  height="36px"
                  width="auto"
                />
              </span>
            </button>
            <div ref={googleButton}></div>
          </div>
          {/* LOGIN */}
          <div label="Iniciar Sesión">
            <h1 className="font-bold text-center text-5xl text-white mb-11 mt-2 lg:!mt-20">
              ¡Bienvenidas!
            </h1>

            <Formik
              initialValues={{
                emailLogin: "",
                passwordLogin: "",
                remember: false,
              }}
              validationSchema={SigninSchema}
              onSubmit={(values, { resetForm }) => {
                getAuth(values);
                resetForm();
                console.log("Ingreso");
              }}
            >
              {({ isSubmitting }) => (
                <Form className="max-w-[90%] sm:max-w-[80%] mx-auto">
                  <Field
                    type="text"
                    id="emailLogin"
                    name="emailLogin"
                    className="form-control"
                    placeholder="Correo electrónico"
                    aria-label="input correo"
                  />
                  <ErrorMessage
                    name="emailLogin"
                    component="div"
                    className="font-sans block text-red-400"
                  />

                  <Field
                    type="password"
                    id="passwordLogin"
                    name="passwordLogin"
                    autoComplete="off"
                    className="form-control mt-3"
                    placeholder="Contraseña"
                    aria-label="input password"
                  />
                  <ErrorMessage
                    name="passwordLogin"
                    component="div"
                    className="font-sans block text-red-400"
                  />

                  <div className="flex justify-between mt-15p">
                    <label
                      htmlFor="remember"
                      className="text-xl text-white cursor-pointer inline-flex"
                    >
                      Recordame
                      <Field
                        type="checkbox"
                        id="remember"
                        name="remember"
                        className="accent-secondary ml-3 w-5"
                      />
                    </label>
                    <Link className="text-xl text-white no-underline" to="/">
                      Olvide mi contraseña
                    </Link>
                  </div>
                  <button
                    id="btn-login"
                    type="submit"
                    disabled={isSubmitting}
                    className="w-full h-14 text-white text-2xl cursor-pointer bg-secondary border-none rounded-md mt-20 mb-4 font-bold text-center no-underline tracking-[1px] hover:opacity-90"
                  >
                    Iniciar sesión
                  </button>
                </Form>
              )}
            </Formik>
            <button
              id="signIn"
              className="flex justify-center sm:mb-12 items-center w-[90%]  sm:w-[80%] h-14 m-auto bg-primary text-white border rounded-md font-bold text-xl no-underline hover:bg-white hover:text-primary hover:cursor-pointer"
              onClick={() => googleLogin()}
            >
              Iniciar sesión con Google
              <span className="ml-7">
                <img
                  src={googleIcon}
                  alt="google"
                  height="36px"
                  width="auto"
                />
              </span>
            </button>
          </div>
        </Auth>
      </div>
    </div>
  );
};

export default AuthPage;
