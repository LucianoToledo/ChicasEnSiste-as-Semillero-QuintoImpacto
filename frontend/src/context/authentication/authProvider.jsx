import { useReducer, useState } from "react";
import AuthContext from "./AuthContext";
import clientAxios from "../../config/axios";
import { authReducer, initAuthReducer, TYPES } from "../../reducers/authReducer";

const AuthProvider = ({ children }) => {
  const [state, dispatch] = useReducer(authReducer, {}, initAuthReducer);

  const login = async (user, token) => {
    try {
      const state = {
        user,
        token,
        resetToken: null,
        isAuth: true
      }
  
      localStorage.setItem(
        "Authorization",
        JSON.stringify(state)
      )
  
      dispatch({type: TYPES.LOGIN, payload: {
        user,
        token
      }})
    } catch (error) {
        window.localStorage.removeItem("Authorization");
    }
  };

  const logout = () => {
    if (window.confirm("¿Esta seguro que desea cerrar sesión?")) {
      window.localStorage.removeItem("Authorization")
      dispatch({type: TYPES.LOGOUT})
    }
  }

  const updateUserData = async() => {
    try {
      const response = await clientAxios.get("/user/me");
      const existing = await JSON.parse(localStorage.getItem('Authorization'))
      existing['user']=await response.data

      localStorage.setItem(
        "Authorization",
        JSON.stringify(existing)
      )
  
      dispatch({type: TYPES.UPDATEUSER, payload: {
        user: response.data,
      }})
    } catch (error) {
      console.error(error);
    }
  }

  return (
    <AuthContext.Provider
      value={{
        state,
        login,
        logout,
        updateUserData
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};
export default AuthProvider;
