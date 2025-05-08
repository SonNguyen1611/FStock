import { useContext, useEffect, useState } from "react";
import {
  deleteOrderById,
  delteOrders,
  getAllOrders,
  getOrdersByCondition,
  updateOrderStatus,
} from "../../../service/OrderService";
import Loading from "../../../components/user/Loading";
import { format } from "date-fns";
import dayjs from "dayjs";
import Pagination from "../../../components/user/Pagination";
import { DatePicker } from "antd";
import { ToastContext } from "../../../contexts/ToastContext";

const OrderManagePage = () => {
  const [orders, setOrders] = useState([]);
  const [ordersUpdate, setOrdersUpdate] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize, setPageSize] = useState(10);
  const [pageInfo, setPageInfo] = useState();
  const { toast } = useContext(ToastContext);
  
  const [conditions, setConditions] = useState({
    pageNumber: currentPage,
    pageSize: pageSize,
    orderStatus: null,
    startDate: null,
    endDate: null,
    orderId: null,
    sort: false,
  });
  const [deleteOrders, setDeleteOrders] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const res = await getAllOrders(currentPage, pageSize);
        setOrders(res.data.data.content);
        setPageInfo(res.data.data);
      } catch (error) {
        console.log(error);
      }
    };
    fetchData();
  }, [currentPage, pageSize, ordersUpdate]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const res = await getOrdersByCondition(conditions);
        setOrders(res.data.data.content);
        setPageInfo(res.data.data);
      } catch (error) {
        console.log(error);
      }
    };
    fetchData();
  }, [conditions]);

  const handleSetCurrentPage = (name, pageNumber) => {
    setCurrentPage(pageNumber);
  };
  const handleSetConditions = (name, value) => {
    setConditions((prev) => ({
      ...prev,
      [name]: value,
    }));
  };
  const handleUpdateOrderStatus = (orderId, status) => {
    const fetchData = async () => {
      try {
        const res = await updateOrderStatus(orderId, status);
        if (res.status === 200) {
          setOrdersUpdate(!ordersUpdate);
          toast.success(res.data.message);
        }
      } catch (error) {
        toast.error(error.response.data.message);
      }
    };
    fetchData();
  };
  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      handleSetConditions("orderId", e.target.value);
    }
  };
  const handleSetListDelete = (e) => {
    const value = e.target.value;
    setDeleteOrders((prev) => {
      if (e.target.checked) {
        return [...prev, value];
      } else {
        // Xoá khỏi danh sách nếu bỏ tick
        return prev.filter((id) => id !== value);
      }
    });
  };
  const handleDeleteOrders = async () => {
    try {
      const res = await delteOrders(deleteOrders);
      if (res.status === 200) {
        setOrdersUpdate(!ordersUpdate);
        setDeleteOrders([]);
        toast.success(res.data.message);
      }
    } catch {
      toast.error("Delete failed");
    }
  }
  const handleDeleteOrder = async (orderId) => {
    try {
      const res = await deleteOrderById(orderId);
      if (res.status === 200) {
        setOrdersUpdate(!ordersUpdate);
        toast.success(res.data.message);
      }
    }catch  {
      toast.error("Delete failed");
    }
  }
  
  console.log(ordersUpdate);
  if (orders === null || orders === undefined) {
    return <Loading></Loading>;
  }

  return (
    <div id="app-content">
      <div className="app-content-area">
        <div className="container-fluid">
          <div className="row">
            <div className="col-lg-12 col-md-12 col-12">
              <div className="mb-5">
                <h3 className="mb-0">Order Management</h3>
              </div>
            </div>
          </div>
          <div>
            <div className="row">
              <div className="col-12">
                <div className="card">
                  <div className="card-header d-md-flex border-bottom-0">
                    <div className="flex-grow-1">
                    <button type="button" className="btn btn-danger" onClick={handleDeleteOrders}>Delete</button>
                    <button type="button" className="btn btn-primary ml-3" onClick={()=> {window.location.reload()}
                    }>Reload</button>


                    </div>
                    <div className="mt-3 mt-md-0">
                      <a href="#!" className="btn btn-outline-white ms-2">
                        Import
                      </a>
                      <a href="#!" className="btn btn-outline-white ms-2">
                        Export
                      </a>
                    </div>
                  </div>
                  <div className="card-header border-bottom-0 border-top d-flex flex-row justify-content-between align-items-center">
                    <div className="d-flex flex-row">
                      <div>
                        <label className="form-label">Search</label>
                        <div className="input-group" style={{ width: "100%" }}>
                          <input
                            className="form-control rounded-3 bg-transparent ps-9"
                            type="search"
                            id="searchInput"
                            placeholder="Enter Order Id"
                            onKeyDown={handleKeyDown}
                          />
                          <button
                            className="btn position-absolute start-0"
                            type="button"
                          >
                            <svg
                              xmlns="http://www.w3.org/2000/svg"
                              width="15"
                              height="15"
                              viewBox="0 0 24 24"
                              fill="none"
                              stroke="currentColor"
                              className="feather feather-search text-dark"
                            >
                              <circle cx="11" cy="11" r="8"></circle>
                              <line
                                x1="21"
                                y1="21"
                                x2="16.65"
                                y2="16.65"
                              ></line>
                            </svg>
                          </button>
                        </div>
                      </div>

                      <div className="ml-3">
                        <label className="form-label ">Start Date</label>
                        <DatePicker
                          className="form-control"
                          onChange={(date) => {
                            handleSetConditions(
                              "startDate",
                              dayjs(date).format("YYYY-MM-DD")
                            );
                          }}
                        ></DatePicker>
                      </div>
                      <div className="ml-3">
                        <label className="form-label ">End Date</label>
                        <DatePicker
                          className="form-control"
                          onChange={(date) => {
                            handleSetConditions(
                              "endDate",
                              dayjs(date).format("YYYY-MM-DD")
                            );
                          }}
                        ></DatePicker>
                      </div>
                    </div>

                    <div className="d-flex flex-row">
                      <div className="mr-3">
                        <label className="form-label ">Status</label>
                        <select
                          className="form-select form-select-sm"
                          onChange={(e) => {
                            handleSetConditions("orderStatus", e.target.value);
                          }}
                        >
                          <option value="PENDING">PENDING</option>
                          <option value="PAID">PAID</option>
                          <option value="REFUNDING">REFUNDING</option>
                          <option value="REJECT">REJECT</option>
                          <option value="REFUNDED">REFUNDED</option>
                          <option value="PROCESSING">PROCESSING</option>
                          <option value="SHIPPING">SHIPPING</option>
                          <option value="SHIPPED">SHIPPED</option>
                          <option value="RETURNED">RETURNED</option>
                          <option value="CANCELED">CANCELED</option>
                          <option value="COMPLETED">COMPLETED</option>
                          <option value="ALL" selected>
                            All
                          </option>
                        </select>
                      </div>
                      <div>
                        <label className="form-label">Show</label>
                        <select
                          className="form-select form-select-sm"
                          onChange={(e) => {
                            setPageSize(e.target.value);
                          }}
                        >
                          <option value="10">10</option>
                          <option value="20">20</option>
                          <option value="5">5</option>
                        </select>
                      </div>
                    </div>
                  </div>

                  <div className="card-body">
                    <div className="table-responsive table-card">
                      <table
                        id="example"
                        className="table text-nowrap table-centered mt-0"
                        style={{ width: 100 + "%" }}
                      >
                        <thead className="table-light">
                          <tr>
                            <th></th>
                            <th>Order Id</th>
                            <th>Created Date</th>
                            <th>Email</th>
                            <th>Address</th>
                            <th>Payment</th>
                            <th>Status</th>
                            <th>Action</th>
                          </tr>
                        </thead>
                        <tbody>
                          {orders.map((order, index) => {
                            return (
                              <tr key={index}>
                                <td className="pe-0">
                                  <div className="form-check">
                                    <input
                                      className="form-check-input"
                                      type="checkbox"
                                      value={order.orderId}
                                      id="contactCheckbox2"
                                      onChange={handleSetListDelete}

                                    />
                                    <label
                                      className="form-check-label"
                                    ></label>
                                  </div>
                                </td>
                                <td className="">
                                  <div className="d-flex align-items-center">
                                    <div className="ms-3">
                                      <h5 className="mb-0">
                                        <a href="#!" className="text-inherit">
                                          {order.orderId}
                                        </a>
                                      </h5>
                                    </div>
                                  </div>
                                </td>
                                <td>
                                  {" "}
                                  {format(
                                    new Date(order.orderDate),
                                    "dd/MM/yyyy HH:mm"
                                  )}
                                </td>
                                <td>{order.email}</td>
                                <td>{order.shippingAddress}</td>
                                <td>{order.paymentMethod}</td>
                                <td>
                                  <select
                                    className="form-select form-select-sm "
                                    style={{ width: 135 + "px" }}
                                    value={order.orderStatus}
                                    onChange={(e) => {
                                      handleUpdateOrderStatus(
                                        order.orderId,
                                        e.target.value
                                      );
                                    }}
                                  >
                                    <option value="PENDING">PENDING</option>
                                    <option value="PAID">PAID</option>
                                    <option value="REFUNDING">REFUNDING</option>
                                    <option value="REJECT">REJECT</option>
                                    <option value="REFUNDED">REFUNDED</option>
                                    <option value="PROCESSING">
                                      PROCESSING
                                    </option>
                                    <option value="SHIPPING">SHIPPING</option>
                                    <option value="SHIPPED">SHIPPED</option>
                                    <option value="RETURNED">RETURNED</option>
                                    <option value="CANCELED">CANCELED</option>
                                    <option value="COMPLETED">COMPLETED</option>
                                  </select>
                                </td>

                                <td>
                                  <button
                                    type="button"
                                    className="btn btn-ghost btn-icon btn-sm rounded-circle texttooltip delete"
                                    data-template="trashOne"
                                    onClick={() => {
                                      handleDeleteOrder(order.orderId);
                                    }}
                                  >
                                    <i className="icon-xs bi bi-trash3-fill"></i>
                                    <div id="trashOne" className="d-none">
                                      <span>Delete</span>
                                    </div>
                                  </button>
                                </td>
                              </tr>
                            );
                          })}
                        </tbody>
                      </table>

                      <div className="row border-top pt-10">
                        <Pagination
                          totalPage={pageInfo?.totalPages}
                          handlePageNumber={handleSetCurrentPage}
                        ></Pagination>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default OrderManagePage;
