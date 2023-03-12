import AuthProvider from "./context/authentication/AuthProvider";
import MainRoutes from "./routes/MainRoutes";
import { GoogleOAuthProvider } from "@react-oauth/google";
import { BrowserRouter } from "react-router-dom";
import { ENV } from "./environment/environment";

const App = () => {
  return (
    <BrowserRouter>
      <AuthProvider>
        <GoogleOAuthProvider clientId={ENV.GOOGLE_CLIENT_ID}>
          <MainRoutes />
        </GoogleOAuthProvider>
      </AuthProvider>
    </BrowserRouter>
  );
};

export default App;
