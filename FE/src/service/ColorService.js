import api from "../api/api";
export const getAllColors = () => {
    return api.get(`/colors`); 
    
}