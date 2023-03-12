import { ErrorMessage, Field, Form, Formik } from "formik";
import { useContext } from "react";
import { FormContext } from "../../../../pages/dashboard/CreateCoursePage_OLD";
import * as yup from "yup";
import CustomTextField from "../../../CustomTextField";

const StepTwo = ({modules}) => {
  // console.log(modules);
  const { activeStepIndex, setActiveStepIndex, formData, setFormData } =
    useContext(FormContext);

  const renderError = (message) => (
    <p className="italic text-red-600">{message}</p>
  );

  const ValidationSchema = yup.object().shape({
    titleVideo: yup.string().required("el título es un campo obligatorio"),
    url: yup.string().required("URL es un campo obligatorio"),
    module: yup.string().required("módulo es un campo obligatorio"),
  });

  return (
    <Formik
      initialValues={{
        titleVideo: "",
        url: "",
        module: "",
      }}
      validationSchema={ValidationSchema}
      onSubmit={(values) => {
        const data = { ...formData, ...values };
        setFormData(data);
        setActiveStepIndex(activeStepIndex + 1);
      }}
    >
      <Form className="flex flex-col w-full border border-primary rounded-md px-8 py-8 mb-4">
        <div className="mb-2">
          <CustomTextField
            name="titleVideo"
            id="titleVideo"
            type="text"
            label="Título del video"
          />
          {/* <label htmlFor="titleVideo" className="text-primary text-2xl">
            Título del video
          </label>
          <Field
            type="text"
            id="titleVideo"
            name="titleVideo"
            className="shadow appearance-none border border-primary rounded  w-full py-2 px-3 mt-3 text-gray-700 text-xl leading-tight focus:outline-none focus:shadow-outline"
          />
          <ErrorMessage name="titleVideo" render={renderError} /> */}
        </div>
        <div className="mb-2">
          <CustomTextField name="url" id="url" type="text" label="URL" />
          {/* <label htmlFor="url" className="text-primary text-2xl">
            URL
          </label>
          <Field
            type="text"
            id="url"
            name="url"
            className="shadow appearance-none border border-primary rounded w-full py-2 px-3 mt-3 text-gray-700 text-xl leading-tight focus:outline-none focus:shadow-outline"
          />
          <ErrorMessage name="url" render={renderError} /> */}
        </div>
        <div className="mb-2">
          <label htmlFor="module" className="text-primary text-2xl">
            Módulo
          </label>
          <Field
            as="select"
            id="module"
            name="module"
            className="shadow appearance-none border border-primary rounded w-full py-2 px-3 mt-3 text-gray-700 text-xl leading-tight focus:outline-none focus:shadow-outline"
          >
            <option value="" disabled selected >Seleccionar un Módulo</option>
            {modules.map((module) => {
             return <option value={module.id} key={module.id}>{module.name}</option>
            })}
          </Field>
          <ErrorMessage name="module" render={renderError} />
        </div>
        <div className="flex justify-end">
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
export default StepTwo;
