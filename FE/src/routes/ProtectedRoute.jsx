import { useContext } from "react"
import AuthContext from "../contexts/AuthContext"
import { Navigate } from "react-router-dom";
import PropTypes from "prop-types";

export const ProtectedRoute = ({children})=> {
    const {user} = useContext(AuthContext);
    if(!user){
        return <Navigate to="/login" replace />;
    }
    return children;
}

export const AdminRoute = ({children}) => {
    const {user} = useContext(AuthContext);
    if(user){
        let roles = user.scope.split(" ");
        if (!roles.includes("ROLE_ADMIN")){
            return <Navigate to="/login" replace />;
        }
        return children;
    }
    return <Navigate to="/login" replace />;
}

ProtectedRoute.propTypes = {
  children: PropTypes.node.isRequired,
};
AdminRoute.propTypes = {
    children: PropTypes.node.isRequired,
  };