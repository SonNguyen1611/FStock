import api from "../api/api";
export const addToCart = (data) => {
    return api.post(`/carts`, data); 
    
}
export const getCart = (email) => {
    return api.get(`/carts`, { params : { email : email } });   
}
export const updateCart = (datas) => {
    return api.put(`/carts/update`, datas);   
}

