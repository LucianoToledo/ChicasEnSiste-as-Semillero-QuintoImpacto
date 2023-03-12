// const initialState = {
//   user: {
//     userId: "",
//     firstName: "",
//     lastName: "",
//     email: "",
//     phone: null,
//     photo: "",
//     role: "",
//   } | null,
//   token: "" | null,
//   resetToken: "" | null,
//   isAuth: false
// }

import tokenAuth from "../config/tokenAuth"

export const initAuthReducer = () => {
  const userFull = JSON.parse(localStorage.getItem('Authorization'))
  // console.log(userFull);
  tokenAuth(userFull?.token)
  return  userFull || initialState
}

export const initialState = {
  user: null,
  token: null,
  resetToken: null,
  isAuth: false
}

export const TYPES = {
  LOGIN: "[auth]LOGIN",
  LOGOUT: "[auth]LOGOUT",
  UPDATEUSER: "UPDATEUSER"
}

export const authReducer = (state, action) => {
  switch (action.type) {
    case TYPES.LOGIN:
      return {
        ...state,
        user: action.payload.user,
        token: action.payload.token,
        resetToken: action.payload.resetToken,
        isAuth: true
      };
    case TYPES.LOGOUT:
      return {
        user: null,
        token: null,
        resetToken: null,
        isAuth: false
      };
    case TYPES.UPDATEUSER:
      return {
        ...state,
        user: action.payload.user,
      };

    default:
      return state;
  }
};
