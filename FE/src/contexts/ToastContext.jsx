import { createContext } from "react";
import { toast, ToastContainer } from "react-toastify";
import PropTypes from "prop-types";



export const ToastContext = createContext();

export const ToastProvider = ({ children }) => {
    const value = {
       toast
    }

    return (
        <ToastContext.Provider value={value}>
            {children}
            <ToastContainer />
        </ToastContext.Provider>
    )

}
ToastProvider.propTypes = {
    children: PropTypes.node.isRequired,
};