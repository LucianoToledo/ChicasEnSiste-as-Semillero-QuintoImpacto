import { ErrorMessage, Field, Form, Formik } from "formik";
import { useContext } from "react";
import { FormContext } from "../../../../pages/dashboard/CreateCoursePage_OLD";
import * as yup from "yup";
import CustomTextField from "../../../CustomTextField";


const StepOne = () => {
  const { activeStepIndex, setActiveStepIndex, formData, setFormData } =
    useContext(FormContext);

  const renderError = (message) => (
    <p className="italic text-red-600">{message}</p>
  );

  const ValidationSchema = yup.object().shape({
    titleCourse: yup.string().required("el título es un campo obligatorio"),
    description: yup.string().required("descripción es un campo obligatorio"),
    price: yup.string().required("precio es un campo obligatorio"),
    amountHours: yup
      .string()
      .required("cantidad de horas es un campo obligatorio"),
    // photo: yup.string().required(),
  });

  return (
    <Formik
      initialValues={{
        titleCourse: "",
        description: "",
        price: "",
        amountHours: "",
        photo: "",
      }}
      validationSchema={ValidationSchema}
      onSubmit={(values) => {
        const data = { ...formData, ...values };
        setFormData(data);
        setActiveStepIndex(activeStepIndex + 1);
      }}
    >
      <Form className="flex flex-col w-full border border-primary rounded-md px-8 py-8">
        <div className="mb-2">
          <CustomTextField
            name="titleCourse"
            id="titleCourse"
            type="text"
            label="Título del curso"
          />
        </div>
        <div className="mb-2">
          <label htmlFor="description" className="text-primary text-2xl">
            Descripción del Curso
          </label>
          <Field
            as="textarea"
            id="description"
            name="description"
            className="shadow appearance-none border border-primary rounded w-full py-2 px-3 mt-3 h-64 text-gray-700 text-xl leading-tight focus:outline-none focus:shadow-outline"
          />
          <ErrorMessage name="description" render={renderError} />
        </div>
        <div className="mb-2">
          <CustomTextField name="price" id="price" type="text" label="Precio" />
        </div>
        <div className="mb-2">
          <CustomTextField
            name="amountHours"
            id="amountHours"
            type="text"
            label="Cantidad de horas"
          />
        </div>
        <div className="mb-2">
          <label htmlFor="photo" className="text-primary text-2xl">
            Adjuntar foto
          </label>
          <Field
            type="file"
            id="photo"
            name="photo"
            className="shadow appearance-none border border-primary rounded w-full py-2 px-3 mt-3 text-gray-700 text-xl leading-tight focus:outline-none focus:shadow-outline"
          />
          <ErrorMessage rorMessage name="photo" render={renderError} />
        </div>
        <div className="flex justify-center lg:justify-end">
          <button
            className="rounded-md w-64 h-16 bg-secondary font-medium text-white text-2xl my-2 p-2"
            type="submit"
          >
            Siguiente
          </button>
        </div>
      </Form>
    </Formik>
  );
};
export default StepOne;
