import api from "../api/api";
export const listCategories = () => {
    return api.get(`/categories`); 
    
}