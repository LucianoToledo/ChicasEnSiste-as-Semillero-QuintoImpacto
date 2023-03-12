import { useContext } from "react";
import { FormContext } from "../../../../pages/dashboard/CreateCoursePage_OLD";
import StepOne from "../Forms/StepOne";
import StepTwo from "../Forms/StepTwo";
import StepThree from "../Forms/StepThree";

const Step = ({modules}) => {
    const { activeStepIndex } = useContext(FormContext);
    let stepContent;
    switch (activeStepIndex) {
      case 0:
        stepContent = <StepOne />;
        break;
      case 1:
        stepContent = <StepTwo modules={modules} />;
        break;
      case 2:
        stepContent = <StepThree />;
        break;
      default:
        break;
    }
  
    return stepContent;
}
export default Step