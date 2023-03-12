import React from 'react';
import {useField} from 'formik';

const CustomSelect = ({label, ...props}) => {
    const [field, meta] = useField(props);
  return (
    
    <div>
        <label>{label}</label>
        <select 
            {...field}
            {...props}
            className={meta.touched && meta.error ? "input-error" : ""}
             />
                {meta.touched && meta.error && <div className="error">{meta.error}</div>}
    </div>
  );
};

export default CustomSelect