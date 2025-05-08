import api from "../api/api";

export const createOrder = (data, orderId) => {
  return api.post(`/orders/create-order`, {
    ...data,
    orderId: orderId,
  });
};
export const createPaymentVnpay = (amount, orderId) => {
  return api.get(`/orders/create-payment`, {
    params: {
      amount: (amount * 25000).toFixed(0),
      orderId: orderId,
      bankCode: "NCB",
    },
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
  });
};

export const getListNewOrder = (keySearch, pageNumber , pageSize ) => {
  return api.get(`/orders/new-orders`, {
    params: {
      keySearch: keySearch,
      pageNumber: pageNumber,
      pageSize: pageSize,
    },
  });
};
export const getAllOrders = ( pageNumber , pageSize ) => {
  return api.get(`/orders/all-orders`, {
    params: {
      pageNumber: pageNumber,
      pageSize: pageSize,
    },
  });
};
export const getOrdersByCondition = ( data ) => {
  return api.get(`/orders/orders-condition`, {
    params: {
      pageNumber: data.pageNumber,
      pageSize: data.pageSize,
      status: data.orderStatus,
      orderId: data.orderId,
      startDate: data.startDate,
      endDate: data.endDate,
      sortByDateInc: data.sortByDateInc,
    },
  });
};
export const updateOrderStatus = (orderId, status) => {
  return api.put(`/orders/update-order-status`,null, {
    params: {
      orderId: orderId,
      status: status,
    },
  });
};
export const getOrdersByEmail = (email) => {
  return api.get(`/orders`, {
    params: {
      email: email,
    },
  });
};
export const getOrderById = (orderId ) => {
  return api.get(`/orders/${orderId}` );
};
export const delteOrders = (data ) => {
  return api.delete(`/orders/delete-orders`, {
    data: data
  });
};

export const deleteOrderById = (orderId ) => {
  return api.delete(`/orders/delete-order/${orderId}` );
};
