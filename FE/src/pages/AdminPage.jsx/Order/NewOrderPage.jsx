import { useContext, useEffect, useState } from "react";
import {
  getListNewOrder,
  updateOrderStatus,
} from "../../../service/OrderService";
import { ToastContext } from "../../../contexts/ToastContext";
import { format } from "date-fns";
import Pagination from "../../../components/user/Pagination";

const NewOrderPage = () => {
  const { toast } = useContext(ToastContext);
  const [pageSize, setPageSize] = useState(10);
  const [orderUpdated, setOrderUpdated] = useState(false);
  const [newOrders, setNewOrders] = useState([]);
  const [pageInfo, setPageInfo] = useState({});
  const [currentPage, setCurrentPage] = useState(1);
  const [keySearch, setKeySearch] = useState(0);

  useEffect(() => {
    try {
      const fetchData = async () => {
        const res = await getListNewOrder(keySearch, currentPage, pageSize);
        setNewOrders(res.data.data.content);
        setPageInfo(res.data.data);
      };
      fetchData();
    } catch (error) {
      console.error("Error fetching new orders:", error);
    }
  }, [currentPage, pageSize, orderUpdated, keySearch]);

  const handleUpdateOrderStatus = async (orderId, status) => {
    try {
      const res = await updateOrderStatus(orderId, status);
      if (res.status === 200) {
        setOrderUpdated(!orderUpdated);
        toast.success(res.data.message);
      }
    } catch (error) {
      toast.error(error.response.data.message);
    }
  };
  const handleSetCurrentPage = (name, value) => {
    setCurrentPage(value);
  };
  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      setKeySearch(e.target.value);
    }
  };


  return (
    <div id="app-content">
      <div className="app-content-area">
        <div className="container-fluid">
          <div className="row">
            <div className="col-lg-12 col-md-12 col-12">
              <div className="mb-5">
                <h3 className="mb-0">New Orders</h3>
              </div>
            </div>
          </div>
          <div>
            <div className="row">
              <div className="col-12">
                <div className="card">
                  <div className="card-header d-md-flex border-bottom-0">
                    <div className="flex-grow-1"></div>
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
                    <div className="d-flex flex-column">
                      <label className="form-label">Search</label>
                      <div className="input-group" style={{ width: "100%" }}>
                        <input
                          className="form-control rounded-3 bg-transparent ps-9"
                          type="search"
                          id="searchInput"
                          placeholder="Search"
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
                            <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                          </svg>
                        </button>
                      </div>
                    </div>

                    <div>
                      <label className="form-label">Show</label>
                      <select
                        className="form-select form-select-sm"
                        onClick={(e) => {
                          setPageSize(e.target.value);
                        }}
                      >
                        <option value="10">10</option>
                        <option value="20">20</option>
                        <option value="5">5</option>
                      </select>
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
                            <th>Order Id</th>
                            <th>Date</th>
                            <th>Phone</th>
                            <th>Payment</th>
                            <th>Email</th>
                            <th>Status</th>
                            <th>Action</th>
                          </tr>
                        </thead>
                        <tbody>
                          {newOrders.map((order, index) => {
                            return (
                              <tr key={index}>
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
                                  {format(
                                    new Date(order.orderDate),
                                    "dd/MM/yyyy HH:mm"
                                  )}
                                </td>
                                <td>{order.phoneNumber}</td>
                                <td>{order.paymentMethod}</td>
                                <td>{order.email}</td>
                                <td>{order.orderStatus}</td>

                                <td>
                                  <a
                                    href="#!"
                                    className="btn btn-ghost btn-icon btn-sm rounded-circle texttooltip"
                                    data-template="eyeOne"
                                    data-toggle="modal"
                                    data-target="#exampleModal"
                                   
                                  >
                                    <i className="icon-xs bi bi-eye-fill"></i>
                                    <div id="eyeOne" className="d-none">
                                      <span>View</span>
                                    </div>
                                  </a>
                                  <button
                                    type="button"
                                    className="btn btn-success mr-3"
                                    onClick={() => {
                                      if (
                                        order.paymentMethod !== "DIRECT_PAYMENT"
                                      ) {
                                        handleUpdateOrderStatus(
                                          order.orderId,
                                          "PAID"
                                        );
                                      } else {
                                        handleUpdateOrderStatus(
                                          order.orderId,
                                          "PROCESSING"
                                        );
                                      }
                                    }}
                                  >
                                    Accept
                                  </button>
                                  <button
                                    type="button"
                                    className="btn btn-danger"
                                    onClick={() => {
                                      if (
                                        order.paymentMethod !== "DIRECT_PAYMENT"
                                      ) {
                                        handleUpdateOrderStatus(
                                          order.orderId,
                                          "REFUNDING"
                                        );
                                      } else {
                                        handleUpdateOrderStatus(
                                          order.orderId,
                                          "REJECT"
                                        );
                                      }
                                    }}
                                  >
                                    Reject
                                  </button>
                                </td>
                              </tr>
                            );
                          })}
                        </tbody>
                      </table>
                      <div className="row border-top pt-10">
                        <Pagination
                          totalPage={pageInfo.totalPages}
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
export default NewOrderPage;
