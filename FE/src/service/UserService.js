import api from "../api/api";
export const getInfo = () => {
    return api.get(`/users/me`);
};
export const getAllUser = (pageNumber, pageSize) => {
    return api.get(`/users`, {
        params: {
            pageNumber: pageNumber,
            pageSize : pageSize,
        },
    });
};
export const changePassword = (data , email) => {
    return api.post(`/users/change-password`, { 
        oldPassword: data.oldPassword, 
        newPassword: data.newPassword,
        confirmPassword: data.confirmPassword,
        email:email,
     
    });
}
export const changeInfo = (data , email) => {
    return api.post(`/users/change-info`, { 
        userName: data.userName, 
        phone: data.phone,
        address: data.address,
        email:email,
     
    });
};
