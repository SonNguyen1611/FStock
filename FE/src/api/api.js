import axios from "axios";
const api = axios.create({
    baseURL: 'http://localhost:8080/api', // URL gốc của backend
    headers: {
      'Content-Type': 'application/json',
    },
  });

api.interceptors.request.use(
    (config) => {
      const accessToken = localStorage.getItem('authToken');

      if (accessToken) {
        config.headers['Authorization'] = `Bearer ${accessToken}`;
      }
      return config;
    },
    (error) => Promise.reject(error)
  );

  

export default api;