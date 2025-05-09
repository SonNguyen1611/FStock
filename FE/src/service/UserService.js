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
export const changeInfo = (data) => {
    return api.put(`/users/change-info`, data , { 
       headers: {
            "Content-Type": "multipart/form-data",
        },
    });
};
export const changeActiveStatus = (email, status) => {
    return api.put(`/users/change-active-status`,null, {
        params: {
            email: email,
            status: status,
        },
    });
}
export const changeRoles = (email, roleNames) => {
    return api.put(`/users/change-roles`,{
        email: email,
        roleNames: roleNames,
    });
}
export const deleteUser = (email) => {
    return api.delete(`/users/delete/${email}`);
};
