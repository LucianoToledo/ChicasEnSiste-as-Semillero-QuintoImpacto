import { useState, useRef } from "react";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
// import "./Contact.css";
import emailjs from '@emailjs/browser';
import Swal from 'sweetalert2';
import withReactContent from 'sweetalert2-react-content'

const MySwal = withReactContent(Swal)

const success = () => {
  MySwal.fire({
    title: 'Excelente!',
    text: 'Tu email ha sido enviado con éxito! Pronto nos comunicaremos con vos!',
    icon: 'success', 
    confirmButtonText: 'Ok',
  }
  )
}

const error = () => {
  MySwal.fire({
    title: 'Error!',
    text: 'Tu email no ha podido ser enviado. Por favor, intenta nuevamente más tarde.',
    icon: 'error', 
    confirmButtonText: 'Ok',
  })
}

const ContactSchema = Yup.object().shape({
  name: Yup.string()
    .min(3, "El nombre debe contener al menos 3 caracteres!")
    .max(50, "El nombre puede contener hasta 50 caracteres!")
    .matches(
      /^[a-zA-ZÀ-ÿ\s]{1,40}$/,
      "El nombre solo puede contener letras y espacios."
    )
    .required("Por favor ingresa un nombre."),
  email: Yup.string()
    .email(
      "Correo electrónico inválido! Debe contener @ y al menos un punto."
    )
    .required("Por favor ingresa un correo electrónico"),
  message: Yup.string()
    .min(5, "El mensaje debe contener al menos 5 caracteres!")
    .max(250, "El mensaje puede contener hasta 250 caracteres!")
    .matches(/^[a-zA-ZÀ-ÿ\s]{1,40}$/, "El mensaje debe contener letras... ")
    .required("Por favor, ingresa un mensaje."),
});

const Contact = () => {
  const [formSended, changeFormSended] = useState(false);

  const form = useRef();

  return (
    <section className="h-[1038px] bg-orange shadow-[0px_3px_6px_rgb(0,0,41)] border-[1px] rounded-[5px] w-full max-w-[1623px] flex items-center shadow-[0px 3px 6px #00000029]">
      <div className="w-full h-full flex flex-col items-center">
        <h2 className="text-sans text-4xl lg:text-5xl font-bold text-primary opacity-100 tracking-normal text-center mt-[49px] mb-[39px]">Contactanos</h2>
        <div className="w-11/12 lg:w-4/5 max-w-[1153px] h-[672px] bg-white shadow-[0px_3px_6px_rgb(0,0,41)] rounded-[5px] opacity-100 pt-[15px]">
          <Formik
            initialValues={{
              name: "",
              email: "",
              message: "",
            }}
            validationSchema={ContactSchema}
            onSubmit={({ resetForm }) => {
              try{
                console.log("Formulario enviado!");
                changeFormSended(true);
                setTimeout(() => changeFormSended(false), 5000);
                emailjs.sendForm('service_0gblnbl','template_eoh6fa5', form.current, '0sJnsfBA91YFO1__9')
                success();
              }catch(error){
                console.error(error)
                error();
              }
              resetForm();
            }}
          >
            {({ isSubmitting }) => (
              <Form className="w-full h-full max-h-[672px] flex flex-col justify-center flex-wrap content-center" ref={form}>               
                <div className="w-full text-center">
                  <Field
                    type="text"
                    name="name"
                    placeholder="Nombre"
                    className="w-11/12 lg:placeholder:font-sans  placeholder:text-xl lg:placeholder:text-2xl placeholder:text-dark-steal placeholder:opacity-60 text-[30px] max-w-[948px] p-4 border-dark-steal border-[3px] rouded-[5px] opacity-100" 
                  />
                  <ErrorMessage name="name" component="div" className="font-sans max-w-[1050px] text-red-500 px-20 absolute " />
                </div>
                <div className="w-full text-center">
                  <Field
                    type="email"
                    name="email"
                    placeholder="E-mail"
                    className="w-11/12 lg:placeholder:font-sans placeholder:text-xl lg:placeholder:text-2xl placeholder:text-dark-steal placeholder:opacity-60 text-[30px] max-w-[948px] p-4 border-dark-steal border-[3px] rouded-[5px] opacity-100 mt-[30px]"
                  />
                  <ErrorMessage
                    name="email"
                    component="div"
                    className="font-sans max-w-[1050px] text-red-500 px-20 absolute"
                  />
                </div>
                <div className="w-full text-center">
                  <Field
                    name="message"
                    as="textarea"
                    placeholder="Mensaje"
                    className="w-11/12 lg:placeholder:font-sans placeholder:text-xl lg:placeholder:text-2xl placeholder:text-dark-steal placeholder:opacity-60 text-[30px] max-w-[948px] p-4 border-dark-steal border-[3px] rouded-[5px] opacity-100 mt-[30px]"
                    cols="22"
                    rows="7"
                  ></Field>
                  <ErrorMessage
                    name="message"
                    component="div"
                    className="font-sans max-w-[1050px] text-red-500 px-20 absolute"
                  />
                </div>
                
                <div>
                  <button
                    className="block w-[230px] lg:w-[254px] font-sans text-2xl text-white font-medium bg-secondary rounded py-3 lg:py-4 opacity-1 shadow-[0px_3px_6px_rgb(0,0,41)] transition duration-150 ease-out hover:ease-in hover:bg-[#ce416c] translate-x-[50px] translate-y-[35px] lg:translate-x-[740px] lg:translate-y-[35px]"
                    type="submit"
                    disabled={isSubmitting}
                  >
                    Enviar
                  </button>
                </div>
                {/* {formSended && (
                  <p className="success">Formulario enviado con éxito!</p>
                )} */}

              </Form>
            )}
          </Formik>
        </div>
      </div>
    </section>
  );
};

export default Contact;
