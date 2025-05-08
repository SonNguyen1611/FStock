import api from "../api/api";

export const getAllProducts = (pageNumber, pageSize , search) => {
    return api.get(`/products/page`, {
        params: {
            pageNumber: pageNumber,
            pageSize: pageSize,
            search: search
        }
    }); 
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
export const createProduct = (data) => {
    return api.post(`/products/create-product`, data , {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    }); 
}

export const updateProduct = (data , id) => {
    return api.put(`/products/update-product/${id}`, data , {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    }); 
}
export const deleteProduct = (id) => {
    return api.delete(`/products/delete/${id}`)
}


export const getColorNames = () => {
    return api.get(`/products/colors`); 
}
export const getSizeNames = () => {
    return api.get(`/products/sizes`); 
}
