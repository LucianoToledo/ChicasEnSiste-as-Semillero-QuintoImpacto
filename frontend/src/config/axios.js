import axios from "axios";
import { ENV } from "../environment/environment";

const clientAxios = axios.create({
  baseURL: ENV.BACKEND_URL,
});

export default clientAxios;
