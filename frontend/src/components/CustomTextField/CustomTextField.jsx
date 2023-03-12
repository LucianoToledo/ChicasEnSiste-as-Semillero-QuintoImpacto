import { useField } from "formik";

const CustomTextField = ({ label, ...props }) => {
  const [field, meta] = useField(props);

  return (
    <>
      <label htmlFor={field.id} className="text-primary text-2xl">
        {label}
          <input
            {...field}
            {...props}
            className="shadow appearance-none border border-primary rounded  w-full py-2 px-3 mt-3 text-gray-700 text-xl leading-tight focus:outline-none focus:shadow-outline"
          />
      </label>
      {meta.touched && meta.error ? (
        <div className="italic text-red-600">{meta.error}</div>
      ) : null}
    </>
  );
};
export default CustomTextField;
