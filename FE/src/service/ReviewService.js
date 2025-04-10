import api from "../api/api";
export const createReviewApi = (data) => {
    return api.post(`/reviews`, {
        email : data.email,
        productId : data.productId,
        content : data.content,
        rating : data.rating

    }); 
    
}