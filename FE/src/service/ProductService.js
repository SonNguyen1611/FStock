import api from "../api/api";

export const getAllProducts = (pageNumber = 1) => {
    return api.get(`/products/page/${pageNumber}`); 
}
export const getProductById = (id) => {
    return api.get(`/products/${id}`); 
}
export const getProductByFilter = (filter) => {
    return api.get(`/products/filter`, {
        params: {
            priceMin : filter.priceMin,
            priceMax : filter.priceMax,
            category : filter.category,
            size : filter.size,
            color : filter.color,
            pageNumber : filter.pageNumber
        }
    }); 
}

export const getStockQuantity = (data) => {
    return api.get(`/products/quantity`, {
        params : {
            productId : data.productId,
            sizeName : data.sizeName,
            colorName : data.colorName
        }

    }); 
}


