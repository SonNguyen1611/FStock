import { useEffect, useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import {
  categoriesInPage,
  deleteCategory,
} from "../../../service/CategoryService";
import { ToastContext } from "../../../contexts/ToastContext";
import Pagination from "../../../components/user/Pagination";
import { confirmAlert } from "react-confirm-alert";
import "react-confirm-alert/src/react-confirm-alert.css";

const AdminCategoryManagePage = () => {
  const [categories, setCategories] = useState([]);
  const [pageInfo, setPageInfo] = useState();
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize, setPageSize] = useState(10);
  const [searchValue, setSearchValue] = useState("");
  const [categoryUpdate, setCategoryUpdate] = useState(false);
  const { toast } = useContext(ToastContext);
  const navigator = useNavigate();
  useEffect(() => {
    try {
      const fetchCategories = async () => {
        const response = await categoriesInPage(
          currentPage,
          pageSize,
          searchValue
        );
        if (response && response.status === 200) {
          const data = await response.data.data.content;
          setPageInfo(response.data.data);
          setCategories(data);
        }
      };
      fetchCategories();
    } catch (errors) {
      toast.error(errors.response.data.message);
    }
  }, [currentPage, pageSize, searchValue, categoryUpdate]);
  console.log(categories);

  const handleSetCurrentPage = (name, value) => {
    setCurrentPage(value);
  };
  const handleSetPageSize = (e) => {
    setPageSize(e.target.value);
  };
  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      setSearchValue(e.target.value);
    }
  };
  const handleDeleteCategory = (id, e) => {
    e.preventDefault();
    confirmAlert({
      title: "Confirm to delete",
      message: "Are you sure to do this.",
      buttons: [
        {
          label: "Yes",
          onClick: async () => {
            try {
              const result = await deleteCategory(id);
              if (result && result.status === 200) {
                toast.success("Delete Category successfully");
              }
              setCategoryUpdate(!categoryUpdate);
            } catch (error) {
              toast.error(error.response?.data?.message || "Delete failed");
            }
          },
        },
        {
          label: "No",
          onClick: () => {},
        },
      ],
    });
  };
  return (
    <div id="app-content">
      <div className="app-content-area">
        <div className="container-fluid">
          <div className="row">
            <div className="col-lg-12 col-md-12 col-12">
              <div className="mb-5">
                <h3 className="mb-0">Category Management</h3>
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
                          navigator("/admin/category/create-category");
                        }}
                      >
                        + Add Category
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
                            <th>Category Id</th>
                            <th>Category Name</th>
                            <th>Action</th>
                          </tr>
                        </thead>
                        <tbody>
                          {categories?.map((category, index) => {
                            return (
                              <tr key={index}>
                                <td>{category.categoryId}</td>
                                <td>{category.categoryName}</td>

                                <td>
                                  <a
                                    href="#!"
                                    className="btn btn-ghost btn-icon btn-sm rounded-circle texttooltip"
                                    data-template="eyeOne"
                                  >
                                    <i className="icon-xs bi bi-eye-fill"></i>
                                    <div id="eyeOne" className="d-none">
                                      <span>View</span>
                                    </div>
                                  </a>
                                  <a
                                    href="#!"
                                    className="btn btn-ghost btn-icon btn-sm rounded-circle texttooltip"
                                    data-template="editOne"
                                    onClick={(e) => {
                                      e.preventDefault();
                                      navigator(
                                        `/admin/category/update-category/${category.categoryId}`
                                      );
                                    }}
                                  >
                                    <i className="icon-xs bi bi-gear-fill"></i>
                                    <div id="editOne" className="d-none">
                                      <span>Edit</span>
                                    </div>
                                  </a>
                                  <button
                                    type="button"
                                    className="btn btn-ghost btn-icon btn-sm rounded-circle texttooltip delete"
                                    data-template="trashOne"
                                    onClick={(e) => {
                                      handleDeleteCategory(
                                        category.categoryId,
                                        e
                                      );
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
export default AdminCategoryManagePage;
