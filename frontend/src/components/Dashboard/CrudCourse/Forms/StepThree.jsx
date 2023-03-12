import { useContext } from "react";
import { FormContext } from "../../../../pages/dashboard/CreateCoursePage_OLD";
import cursosDefault from "../../../../assets/images/cursos.png";

const StepThree = () => {
  const { activeStepIndex, setActiveStepIndex, formData, setFormData } =
    useContext(FormContext);
  console.log(formData);
  console.log(cursosDefault);

  return (
    <div className="w-full border border-primary rounded-md px-8 py-8">
      {/* Preview */}
      <div className="w-full lg:max-w-full lg:flex mb-44">
        <div className="flex justify-center min-w-[200px] p-5 shadow-lg rounded-lg group-hover:opacity-75 border border-primary">
          <img
            className="object-cover "
            src={cursosDefault}
            alt="Featured"
          />
        </div>
        <div className="bg-white rounded-b lg:rounded-b-none lg:rounded-r px-4 flex flex-col justify-between leading-normal">
          <div className="text-primary text-2xl max-w-max">
            <p className="md:mt-0 mb-2 mt-3">{formData.titleCourse}</p>
            <p className="text-neutral-500 text-xl text-ellipsis overflow-hidden ">
              {formData.description}
            </p>
          </div>
          <div className="text-primary text-2xl mt-4 md:mt-0">
            <p className="mb-2">Precio: ${formData.price}</p>
            <p> Horas: {formData.amountHours}hs</p>
          </div>
        </div>
      </div>
      <div className="flex justify-end">
        <button
          className="rounded-md w-64 h-16 bg-secondary font-medium text-white text-2xl my-2 p-2"
          type="submit"
        >
          Publicar
        </button>
      </div>
    </div>
  );
};
export default StepThree;
