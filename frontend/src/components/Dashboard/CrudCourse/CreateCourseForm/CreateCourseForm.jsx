import { ErrorMessage, Field, Form, Formik } from "formik";
import * as Yup from "yup";

const CreateCourseForm = ({ setShowModal, createCourse }) => {
  return (
    <Formik
      initialValues={{
        name: "",
        description: "",
        price: "",
        duration: "",
        photo: "",
      }}
      validationSchema={Yup.object({
        name: Yup.string()
          .max(50, "Debe tener 50 caracteres o menos")
          .required("Requerido"),
        description: Yup.string()
          .max(2000, "Debe tener 2000 caracteres o menos")
          .required("Requerido"),
        price: Yup.number()
          .typeError('Debes ingresar valores numericos')
          .max(10000000, "Debe tener 10000000 caracteres o menos") 
          .positive("No ingresar valores negativos")         
          .required("Requerido"),
        duration: Yup.number()
          .typeError('Debes ingresar valores numericos')
          .max(65000, "Debe tener 65000 caracteres o menos")
          .positive("No ingresar valores negativos")
          .required("Requerido"),
      })}
      onSubmit={(values, { setSubmitting }) => {
        setTimeout(() => {
          // alert(JSON.stringify(values, null, 2));
          createCourse(values);
          setSubmitting(false);
          setShowModal(false);
        }, 400);
      }}
    >
      <Form>
        <div className="mb-6">
          <label
            htmlFor="name"
            className="block mb-2 text-xl font-medium text-gray-900 dark:text-gray-300"
          >
            Nombre
          </label>
          <Field
            id="name"
            name="name"
            type="text"
            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          />
          <ErrorMessage name="name" component="span" className="text-red-600" />
        </div>
        <div className="mb-6">
          <label
            htmlFor="description"
            className="block mb-2 text-xl font-medium text-gray-900 dark:text-gray-300"
          >
            Descripción
          </label>
          <Field
            id="description"
            name="description"
            as="textarea"
            className="h-48 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          />
          <ErrorMessage
            name="description"
            component="span"
            className="text-red-600"
          />
        </div>
        <div className="mb-6">
          <label
            htmlFor="price"
            className="block mb-2 text-xl font-medium text-gray-900 dark:text-gray-300"
          >
            Precio
          </label>
          <Field
            id="price"
            name="price"
            type="text"
            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          />
          <ErrorMessage name="price" component="span" className="text-red-600" />
        </div>
        <div className="mb-6">
          <label
            htmlFor="duration"
            className="block mb-2 text-xl font-medium text-gray-900 dark:text-gray-300"
          >
            Duración
          </label>
          <Field
            id="duration"
            name="duration"
            type="text"
            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          />
          <ErrorMessage name="duration" component="span" className="text-red-600" />
        </div>
        <div className="mb-6">
          <label
            htmlFor="photo"
            className="block mb-2 text-xl font-medium text-gray-900 dark:text-gray-300"
          >
            Imagen
          </label>
          <Field
            id="photo"
            name="photo"
            type="file"
            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            disabled
          />
          <ErrorMessage name="photo" component="span" className="text-red-600" />
        </div>
        <div className="flex items-center justify-end p-6 border-t border-solid border-slate-200 rounded-b">
          <button
            className="text-red-500 background-transparent font-bold uppercase px-6 py-2 text-sm outline-none focus:outline-none mr-1 mb-1 ease-linear transition-all duration-150"
            type="button"
            onClick={() => setShowModal(false)}
          >
            Cerrar
          </button>
          <button
            className="bg-emerald-500 text-white active:bg-emerald-600 font-bold uppercase text-sm px-6 py-3 rounded shadow hover:shadow-lg outline-none focus:outline-none mr-1 mb-1 ease-linear transition-all duration-150"
            type="submit"
          >
            Guardar
          </button>
        </div>
      </Form>
    </Formik>
  );
};

export default CreateCourseForm;
