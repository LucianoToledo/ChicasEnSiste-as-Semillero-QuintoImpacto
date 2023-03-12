import React from 'react';
import {Formik, Form} from 'formik';
import * as yup from 'yup';
import CustomSelect from './CustomSelect';

const schema = yup.object().shape({
    medioType: yup
    .string()
    .oneOf(['MP', 'PP'], 'Medio de pago inválido')
    .required('Debe escoger el medio de pago')
});


const Dropdown1 = ({pago}) => {
    
    return(
        <Formik
            initialValues={{medioType:""}}
            validationSchema={schema}
            onSubmit={(values)=>{
                console.log(values)
                if(values.medioType === "MP"){
                    pago()
                }
                if(values.medioType === "PP"){
                    console.log("Pago por paypal")
                }
            }}
        >
            {
                ({isSubmitting}) => (
                    <Form className="mt-20 ml-10 mr-10 p-2 border border-solid border-[#707070]">
                        <CustomSelect 
                            name="medioType"
                            placeholder="Medio de Pago"
                            className="w-full border-none font-sans placeholder:text-[#707070] placeholder:italic placeholder:text-3xl"
                        >
                            <option value="" >Medio de Pago</option>
                            <option value = "MP">Mercado Pago</option>
                            <option value = "PP">PayPal</option> 
                        </CustomSelect>
                        <div className="mt-36 bg-secondary rounded-[5px] w-[238px] h-[58px] cursor-pointer text-center float-right mb-[40px] mr-5 md:mr-8 lg:mr-10 ">
                            <button disable={isSubmitting} type="submit" className=" ">
                            <p className="font-sans text-2xl text-white text-center font-medium leading-10 inline-block align-middle mt-2">Empezar</p></button>
                        </div>
                    </Form>
                    )
                }
        </Formik>
    );
};
export default Dropdown1;
    
