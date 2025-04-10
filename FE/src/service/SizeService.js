import api from "../api/api";
export const getAllSizes = () => {
    return api.get(`/sizes`); 
    
}