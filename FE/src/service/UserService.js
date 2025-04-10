import api from "../api/api";
export const getInfo = () => {
    return api.get(`/users/me`);
};