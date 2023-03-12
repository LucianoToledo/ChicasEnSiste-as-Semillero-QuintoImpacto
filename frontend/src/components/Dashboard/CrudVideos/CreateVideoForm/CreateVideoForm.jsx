import { ErrorMessage, Field, Form, Formik } from "formik";
import * as Yup from "yup";

const CreateVideoForm = ({ setShowModal, createVideo, modules }) => {
  return (
    <Formik
      initialValues={{ url: "", name: "", duration: "", idModulo: "" }}
      validationSchema={Yup.object({
        url: Yup.string().required("Campo obligatorio"),
        name: Yup.string()
          .max(100, "Debe tener 100 caracteres o menos")
          .required("Campo obligatorio"),
        duration: Yup.number().required("Campo obligatorio"),
        idModulo: Yup.string().nullable(),
      })}
      onSubmit={(values, { setSubmitting }) => {
        setTimeout(() => {
          // alert(JSON.stringify(values, null, 2));
          createVideo(values);
          setSubmitting(false);
          setShowModal(false);
        }, 400);
      }}
    >
      <Form>
        <div className="mb-6">
          <label
            htmlFor="url"
            className="block mb-2 text-xl font-medium text-gray-900 dark:text-gray-300"
          >
            URL
          </label>
          <Field
            id="url"
            name="url"
            type="text"
            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          />
          <ErrorMessage name="url" component="span" className="text-red-600" />
        </div>
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
            htmlFor="duration"
            className="block mb-2 text-xl font-medium text-gray-900 dark:text-gray-300"
          >
            Tiempo del Video (minutos)
          </label>
          <Field
            id="duration"
            name="duration"
            type="number"
            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          />
          <ErrorMessage
            name="duration"
            component="span"
            className="text-red-600"
          />
        </div>
        <div className="mb-6">
          <label
            htmlFor="idModulo"
            className="block mb-2 text-xl font-medium text-gray-900 dark:text-gray-300"
          >
            Módulo
          </label>
          <Field
            as="select"
            id="idModulo"
            name="idModulo"
            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          >
            <option value="" disabled>
              Seleccionar un Módulo
            </option>
            {modules.map((module) => {
              return (
                <option value={module.id} key={module.id}>
                  {module.name}
                </option>
              );
            })}
          </Field>
          <ErrorMessage
            name="idModulo"
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

export default CreateVideoForm;
