import React from 'react';
import { Link } from "react-router-dom";
import button from "./Button.module.css";

const  Button = ({btn}) => {
  return (
    <div className={button["btn-card"]}>
        <Link to="cursos" className={button["btn-text"]} >
            <p>{btn.text}</p>
        </Link>
    </div>
  )
}

export default  Button