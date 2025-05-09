import { useContext, useEffect, useRef, useState } from "react";
import {
  changeActiveStatus,
  changeRoles,
  deleteUser,
  getAllUser,
} from "../../../service/UserService";
import Pagination from "../../../components/user/Pagination";
import { ToastContext } from "../../../contexts/ToastContext";

const AccountMangementPage = () => {
  const [roles, setRoles] = useState([]);
  const [users, setUsers] = useState([]);
  const [pageInfo, setPageInfo] = useState();
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize, setPageSize] = useState(10);
  const [updatedUsers, setUpdatedUsers] = useState(false);
  const { toast } = useContext(ToastContext);
  const ref = useRef();

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const res = await getAllUser(currentPage, pageSize);
        setUsers(res.data.data.content);
        setPageInfo(res.data.data);
      } catch (error) {
        console.error("Error fetching users:", error);
      }
    };
    fetchUsers();
  }, [currentPage, pageSize, updatedUsers]);

  const handleSetCurrentPage = (name, pageNumber) => {
    setCurrentPage(pageNumber);
  };
  const handleSetPageSize = (e) => {
    setPageSize(e.target.value);
  };
  const handleChangeActiveStatus = async (email, status) => {
    try {
      const res = await changeActiveStatus(email, status);
      if (res.status === 200) {
        setUpdatedUsers(!updatedUsers);
        toast.success("Change active status successfully");
      }
    } catch {
      toast.error("Change active status failed");
    }
  };
  const handleChangeRole = async (email, roles) => {
    try {
      const res = await changeRoles(email, roles);
      if (res.status === 200) {
        setUpdatedUsers(!updatedUsers);
        toast.success("Change role successfully");
      }
    } catch {
      toast.error("Change role failed");
    }
  };
  const handleCheckboxChange = (e) => {
    const { value, checked } = e.target;
    setRoles((prev) =>
      checked ? [...prev, value] : prev.filter((role) => role !== value)
    );
  };

  const handleDeleteUser = async (email) => {
    try {
      const res = await deleteUser(email);
      if (res.status === 200) {
        setUpdatedUsers(!updatedUsers);
        toast.success("Delete user successfully");
      }
    } catch {
      toast.error("Delete user failed");
    }
  };

  return (
    <div id="app-content">
      <div className="app-content-area">
        <div className="container-fluid">
          <div className="row">
            <div className="col-lg-12 col-md-12 col-12">
              <div className="mb-5">
                <h3 className="mb-0">Products</h3>
              </div>
            </div>
          </div>
          <div>
            <div className="row">
              <div className="col-12">
                <div className="card">
                  <div className="card-header d-md-flex border-bottom-0">
                    <div className="flex-grow-1">
                      <a
                        href="#!"
                        className="btn btn-primary"
                        onClick={(e) => {
                          e.preventDefault();
                          navigator("/admin/product/create-product");
                        }}
                      >
                        + Add Product
                      </a>
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
                    <div className="d-flex flex-column">
                      <label className="form-label">Search</label>
                      <div className="input-group" style={{ width: "100%" }}>
                        <input
                          className="form-control rounded-3 bg-transparent ps-9"
                          type="search"
                          id="searchInput"
                          placeholder="Search"
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
                        onChange={handleSetPageSize}
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
                            <th>User Name</th>
                            <th>User Id</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Role</th>
                            <th>Active</th>
                            <th>Action</th>
                          </tr>
                        </thead>
                        <tbody>
                          {users.map((user, index) => {
                            return (
                              <tr key={index}>
                                <td className="">
                                  <div className="d-flex align-items-center">
                                    <img
                                      src={user.imgUrlDisplay}
                                      alt=""
                                      className="img-4by3-sm rounded-3"
                                    />
                                    <div className="ms-3">
                                      <h5 className="mb-0">
                                        <a href="#!" className="text-inherit">
                                          {user.userName}
                                        </a>
                                      </h5>
                                    </div>
                                  </div>
                                </td>
                                <td>{user.userId}</td>
                                <td>{user.email}</td>
                                <td>{user.phone}</td>
                                <td>
                                  {user.roles.map((role) => (
                                    <span
                                      className="badge bg-primary me-1"
                                      key={role.roleName}
                                    >
                                      {role.roleName}
                                    </span>
                                  ))}
                                </td>
                                <td>
                                  <select
                                    className="form-select form-select-sm "
                                    style={{ width: 100 + "px" }}
                                    value={user.active}
                                    onChange={(e) => {
                                      handleChangeActiveStatus(
                                        user.email,
                                        e.target.value
                                      );
                                    }}
                                  >
                                    <option value="false">Blocked</option>
                                    <option value="true">Active</option>
                                  </select>
                                </td>

                                <td>
                                  <div className="role-container">
                                    <a
                                      href="#!"
                                      className="btn btn-ghost btn-icon btn-sm rounded-circle texttooltip"
                                      data-template="editOne"
                                    >
                                      <i className="bi bi-person-add"></i>
                                      <div id="editOne" className="d-none">
                                        <span>Edit</span>
                                      </div>
                                    </a>
                                    <div className="tooltip-box">
                                      <form ref={ref}>
                                        <div className="form-check">
                                          <input
                                            className="form-check-input"
                                            type="checkbox"
                                            value="ADMIN"
                                            onChange={handleCheckboxChange}
                                          />
                                          <label className="form-check-label">
                                            ADMIN
                                          </label>
                                        </div>
                                        <div className="form-check">
                                          <input
                                            className="form-check-input"
                                            type="checkbox"
                                            value="USER"
                                            onChange={handleCheckboxChange}
                                          />
                                          <label className="form-check-label">
                                            USER
                                          </label>
                                        </div>
                                        <button
                                          className="btn btn-primary"
                                          onClick={(e) => {
                                            handleChangeRole(user.email, roles);
                                            e.preventDefault();
                                            setRoles([]);
                                          }}
                                        >
                                          Upgrade
                                        </button>
                                      </form>
                                    </div>
                                  </div>

                                  <button
                                    type="button"
                                    className="btn btn-ghost btn-icon btn-sm rounded-circle texttooltip delete"
                                    data-template="trashOne"
                                    onClick={(e) => {
                                      handleDeleteUser(user.email);
                                      e.preventDefault();
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
export default AccountMangementPage;
