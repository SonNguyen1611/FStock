import { useContext, useState, useEffect } from "react";
import {
  getCategoryById,
  updateCategory,
} from "../../../service/CategoryService";
import { ToastContext } from "../../../contexts/ToastContext";
import { useParams } from "react-router-dom";

const AdminUpdateCategoryPage = () => {
  const { toast } = useContext(ToastContext);
  const [categoryName, setCategoryName] = useState("");
  const [category, setCategory] = useState({});
  const { categoryId } = useParams();

  useEffect(() => {
    const fetchCategory = async () => {
      try {
        const res = await getCategoryById(categoryId);
        setCategory(res.data.data);
      } catch (error) {
        toast.error(error.response.data.message);
      }
    };
    fetchCategory();
  }, [categoryId]);

  const handleUpdateCategory = async (e) => {
    e.preventDefault();
    const data = {
      categoryName: categoryName,
    };
    try {
      const res = await updateCategory(categoryId, data);
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
                <h3 className="mb-0 ">Update Category</h3>
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
                          placeholder="Category Name"
                          defaultValue={category.categoryName}
                          onChange={(e) => setCategoryName(e.target.value)}
                        ></input>
                      </div>
                      <div className="d-grid">
                        <button
                          type="submit"
                          className="btn btn-primary"
                          onClick={handleUpdateCategory}
                        >
                          Update Category
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
export default AdminUpdateCategoryPage;
