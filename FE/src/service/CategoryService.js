import api from "../api/api";
export const listCategories = () => {
    return api.get(`/categories`); 
    
}

export const getCategoryById = (id) => {
    return api.get(`/categories/${id}`);
}

export const categoriesInPage = (pageNumber, pageSize , search) => {
    return api.get(`/categories/page`, 
        {
        params: {
            pageNumber: pageNumber,
            pageSize: pageSize,
            search: search
        }

    }); 
    
}
export const createCategory = (category) => {
    return api.post(`/categories/create-category`, category);
}
export const deleteCategory = (categoryId) => {
    return api.delete(`/categories/delete/${categoryId}`);
}
export const updateCategory = (categoryId, data) => {
    return api.put(`/categories/update/${categoryId}`, data);
}
