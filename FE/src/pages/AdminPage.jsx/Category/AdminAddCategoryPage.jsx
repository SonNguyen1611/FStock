import { useContext, useState } from "react";
import { createCategory } from "../../../service/CategoryService";
import { ToastContext } from "../../../contexts/ToastContext";

const AdminAddCategoryPage = () => {
  const { toast } = useContext(ToastContext);
  const [categoryName, setCategoryName] = useState("");
  console.log(categoryName);

  const handleCreateCategory = async (e) => {
    e.preventDefault();
    const data = {
      categoryName: categoryName,
    };
    try {
      const res = await createCategory(data);
      if (res.status === 200) {
        toast.success(res.data.message);
      }
    } catch (error) {
      toast.error(error.response.data.message);
    }
  };
  return (
    <div id="app-content">
      <div className="app-content-area">
        <div className="container-fluid">
          <div className="row">
            <div className="col-lg-12 col-md-12 col-12">
              <div className="mb-5">
                <h3 className="mb-0 ">Add Category</h3>
              </div>
            </div>
          </div>
          <div>
            <form className="row">
              <div className="col-lg-6 col-12">
                <div className="card mb-4">
                  <div className="card-body">
                    <div>
                      <div className="mb-3">
                        <label className="form-label">Category Name</label>
                        <input
                          type="text"
                          className="form-control"
                          placeholder="Enter Category Name"
                          value= {categoryName}
                          onChange={(e) => setCategoryName(e.target.value)}
                        ></input>
                      </div>
                      <div className="d-grid">
                        <button
                          type="submit"
                          className="btn btn-primary"
                          onClick={handleCreateCategory}
                        >
                          Create Category
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};
export default AdminAddCategoryPage;
