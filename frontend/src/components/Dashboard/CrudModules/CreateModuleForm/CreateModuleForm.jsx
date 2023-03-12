import { ErrorMessage, Field, Form, Formik } from "formik";
import * as Yup from "yup";

const CreateModuleForm = ({ setShowModal, createModule, courses }) => {
  console.log(courses);
  return (
    <Formik
      initialValues={{ name: "", description: "", idCourse: "" }}
      validationSchema={Yup.object({
        name: Yup.string()
          .max(25, "Debe tener 25 caracteres o menos")
          .required("Requerido"),
        description: Yup.string()
          .max(1000, "Debe tener 1000 caracteres o menos")
          .required("Requerido"),
        idCourse: Yup.string().nullable(),
      })}
      onSubmit={(values, { setSubmitting }) => {
          setTimeout(() => {
              createModule(values)
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
            htmlFor="idCourse"
            className="block mb-2 text-xl font-medium text-gray-900 dark:text-gray-300"
          >
            Cursos
          </label>
          <Field
            as="select"
            id="idCourse"
            name="idCourse"
            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          >
            <option value="" disabled>
              Seleccionar un Curso
            </option>
            {courses.map((course) => {
              return (
                <option value={course.id} key={course.id}>
                  {course.nameCourse}
                </option>
              );
            })}
          </Field>
          <ErrorMessage
            name="idCourse"
            component="span"
            className="text-red-600"
          />
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

export default CreateModuleForm;
