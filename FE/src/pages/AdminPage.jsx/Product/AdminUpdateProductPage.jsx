import ColorName from "color-name";
import { useContext, useEffect, useState } from "react";
import { useForm, useFieldArray } from "react-hook-form";
import { listCategories } from "../../../service/CategoryService";
import { getProductById, updateProduct } from "../../../service/ProductService";
import { ToastContext } from "../../../contexts/ToastContext";
import { useParams } from "react-router-dom";
import Loading from "../../../components/user/Loading";
const AdminUpdateProductPage = () => {
  const [categories, setCategories] = useState();
  const [product, setProduct] = useState();
  const [selectCategory, setSelectCategory] = useState();
  const { toast } = useContext(ToastContext);
  const getRgbFromColorName = (name) => {
    const rgb = ColorName[name.toLowerCase()];
    return rgb ? `rgb(${rgb[0]}, ${rgb[1]}, ${rgb[2]})` : null;
  };
  const { productId } = useParams();
  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const response = await getProductById(productId);
        setProduct(response.data.data);
      } catch (error) {
        toast.error(
          error.response.data.message || "Có lỗi xảy ra khi lấy sản phẩm"
        );
      }
    };
    fetchProduct();
  }, [productId]);

  useEffect(() => {
    if (product) {
      setSelectCategory(product.category.categoryId);
    }
  }, [product]);
  const {
    register,
    control,
    handleSubmit,
    reset,
    getValues,
    formState: { errors },
  } = useForm({
    defaultValues: {
      productName: "",
      description: "",
      productSKU: "",
      categoryId: 0,
      imageUrlDisplay: null,
      images: [],

      variants: [
        {
          sizeName: "",
          length: 0,
          width: 0,
          height: 0,
          weight: 0,
          colorName: "",
          colorCode: getRgbFromColorName("red"),
          stcokQuantity: 0,
        },
      ],
      priceDefault: 0,
    },
  });
  const { fields, append, remove } = useFieldArray({
    control,
    name: "variants",
  });
  useEffect(() => {
    if (product) {
      reset({
        productName: product.productName || "",
        description: product.description || "",
        productSKU: product.productSKU || "",
        categoryId: product.category.categoryId || "",
        imageUrlDisplay: null,
        images: [],
        priceDefault: product.priceDefault || 0,
        variants:
          product.productVariants?.length > 0
            ? product.productVariants
            : [
                {
                  sizeName: "",
                  length: 0,
                  width: 0,
                  height: 0,
                  weight: 0,
                  colorName: "",
                  colorCode: getRgbFromColorName("red"),
                  stockQuantity: 0,
                },
              ],
      });
    }
  }, [product]);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await listCategories();
        setCategories(response.data.data);
      } catch (error) {
        console.error("Error fetching categories:", error);
      }
    };
    fetchCategories();
  }, []);

  const updateProductOptions = {
    productName: {
      required: "Product Name must not be blank",
      minLength: {
        value: 6,
        message: "Product Name must be at least 8 characters",
      },
      maxLength: {
        value: 30,
        message: "Product Name must be at most 30 characters",
      },
    },
    description: {
      required: "Description must not be blank",
      minLength: {
        value: 8,
        message: "Description must be at least 8 characters",
      },
      maxLength: {
        value: 200,
        message: "Description must be at most 200 characters",
      },
    },
    productSKU: {
      minLength: {
        value: 2,
        message: "SKU must be at least 2 characters",
      },
      maxLength: {
        value: 30,
        message: "SKU must be at most 30 characters",
      },
    },
    priceDefault: {
      required: "Price must not be blank",
      pattern: {
        value: /^[0-9]+(\.[0-9]+)?$/,
        message: "Value must be a valid number",
      },
    },

    images: {
      validate: (value) => {
        if (value.length === 0 || !value) {
          return true;
        }
        if (value.length != 3 && value.length > 0) {
          return "You should  select  3 images";
        }
        return true;
      },
    },
    length: {
      required: "length must not be blank",
      pattern: {
        value: /^[0-9]+(\.[0-9]+)?$/,
        message: "Value must be a valid number",
      },
      validate: (value) => {
        if (value <= 0) {
          return "length must be greater than 0";
        }
        return true;
      },
    },
    width: {
      required: "width must not be blank",
      pattern: {
        value: /^[0-9]+(\.[0-9]+)?$/,
        message: "Value must be a valid number",
      },
      validate: (value) => {
        if (value <= 0) {
          return "length must be greater than 0";
        }
        return true;
      },
    },
    height: {
      required: "height must not be blank",
      pattern: {
        value: /^[0-9]+(\.[0-9]+)?$/,
        message: "Value must be a valid number",
      },
      validate: (value) => {
        if (value <= 0) {
          return "length must be greater than 0";
        }
        return true;
      },
    },
    weight: {
      required: "weight must not be blank",
      pattern: {
        value: /^[0-9]+(\.[0-9]+)?$/,
        message: "Value must be a valid number",
      },
      validate: (value) => {
        if (value <= 0) {
          return "length must be greater than 0";
        }
        return true;
      },
    },
    colorName: {
      required: "Color must not be blank",
      pattern: {
        value: /^[a-z]+$/,
        message: "only lowercase letters are allowed",
      },
      validate: (value) => {
        if (value <= 0) {
          return "length must be greater than 0";
        }
        return true;
      },
    },
    stockQuantity: {
      required: "quantiy must not be blank",
      pattern: {
        value: /^[0-9]+$/,
        message: "Value must be a valid number",
      },
      validate: (value) => {
        if (value <= 0) {
          return "length must be greater than 0";
        }
        return true;
      },
    },
  };

  const handleSelectCategory = (e) => {
    setSelectCategory(e.target.value);
  };

  const handleUpdateProduct = async (formData) => {
    console.log("formData", getValues());
    try {
      if (!selectCategory) {
        toast.error("Vui lòng thêm danh mục");
        throw new Error("Vui lòng chọn danh mục");
      }

      const dataSend = new FormData();
      dataSend.append("productName", formData.productName || "");
      dataSend.append("description", formData.description || "");
      dataSend.append("productSKU", formData.productSKU || "");
      dataSend.append("categoryId", selectCategory);
      if (formData.imageUrlDisplay) {
        dataSend.append("imageUrlDisplay", formData.imageUrlDisplay[0]);
      }
      dataSend.append("priceDefault", formData.priceDefault || "");

      // Xử lý variants
      if (formData.variants && formData.variants.length > 0) {
        formData.variants.forEach((variant, index) => {
          dataSend.append(
            `variants[${index}].sizeName`,
            variant.sizeName || ""
          );
          dataSend.append(`variants[${index}].length`, variant.length || "");
          dataSend.append(`variants[${index}].width`, variant.width || "");
          dataSend.append(`variants[${index}].height`, variant.height || "");
          dataSend.append(`variants[${index}].weight`, variant.weight || "");
          dataSend.append(
            `variants[${index}].stockQuantity`,
            variant.stockQuantity || ""
          );
          dataSend.append(
            `variants[${index}].colorName`,
            variant.colorName || ""
          );
          if (getRgbFromColorName(variant.colorName) === null) {
            toast.error("Màu không hợp lệ");
            throw new Error("Màu không hợp lệ");
          }
          dataSend.append(
            `variants[${index}].colorCode`,
            getRgbFromColorName(variant.colorName) || ""
          );
        });
      }

      // Thêm images
      Array.from(formData.images).forEach((image) => {
        dataSend.append("images", image);
      });

      const res = await updateProduct(dataSend, productId);
      if (res.status === 200) {
        toast.success(res.data.message || "Update sản phẩm thành công");
      }
    } catch (error) {
      toast.error(
        error?.response?.data?.message ||
          error.message ||
          "Có lỗi xảy ra khi update sản phẩm"
      );
    }
  };
  if (product === null || product === undefined) {
    return <Loading></Loading>;
  }
  return (
    <div id="app-content">
      <div className="app-content-area">
        <div className="container-fluid">
          <div className="row">
            <div className="col-lg-12 col-md-12 col-12">
              <div className="mb-5">
                <h3 className="mb-0 ">Add Product</h3>
              </div>
            </div>
          </div>
          <div>
            <form onSubmit={handleSubmit(handleUpdateProduct)} className="row">
              <div className="col-lg-6 col-12">
                <div className="card mb-4">
                  <div className="card-body">
                    <div>
                      <div className="mb-3">
                        <label className="form-label">Product Name</label>
                        <input
                          type="text"
                          className="form-control"
                          placeholder="Enter Product Name"
                          required
                          {...register(
                            "productName",
                            updateProductOptions.productName
                          )}
                        ></input>
                        {errors.productName && (
                          <div style={{ color: "red" }}>
                            {errors.productName.message}
                          </div>
                        )}
                      </div>
                      <div className="mb-3">
                        <label className="form-label">Product SKU</label>
                        <input
                          type="text"
                          className="form-control"
                          placeholder="Enter Product Title"
                          {...register(
                            "productSKU",
                            updateProductOptions.productSKU
                          )}
                        ></input>
                        {errors.productSKU && (
                          <div style={{ color: "red" }}>
                            {errors.productSKU.message}
                          </div>
                        )}
                      </div>

                      <div>
                        <label className="form-label">
                          Product Description
                        </label>
                        <input
                          type="text"
                          className="form-control"
                          placeholder="Enter Product Title"
                          required
                          {...register(
                            "description",
                            updateProductOptions.description
                          )}
                        ></input>
                        {errors.description && (
                          <div style={{ color: "red" }}>
                            {errors.description.message}
                          </div>
                        )}
                      </div>
                      <div className="mb-3 mt-3">
                        <div className="d-flex justify-content-between">
                          <label className="form-label">Category</label>
                          <a href="#!" className="btn-link fw-semi-bold">
                            Add New
                          </a>
                        </div>
                        <select
                          className="form-select"
                          aria-label="Default select example"
                          onChange={(e) => handleSelectCategory(e)}
                        >
                          {categories &&
                            categories.map((category) => (
                              <option
                                key={category.categoryId}
                                value={category.categoryId}
                              >
                                {category.categoryName}
                              </option>
                            ))}
                        </select>
                      </div>
                    </div>
                  </div>
                </div>
                <div className="card mb-4">
                  <div className="card-body">
                    <div>
                      <div className="mb-4">
                        <h4 className="mb-4">Product Gallery</h4>
                        <h5 className="mb-1">Product Image</h5>
                        <p>Add Product main Image.</p>
                        <input
                          type="file"
                          className="form-control"
                          {...register(
                            "imageUrlDisplay",
                            updateProductOptions.imageUrlDisplay
                          )}
                        ></input>
                        {errors.imageUrlDisplay && (
                          <div style={{ color: "red" }}>
                            {errors.imageUrlDisplay.message}
                          </div>
                        )}
                      </div>
                      <div>
                        <h5 className="mb-1">Product Gallery</h5>
                        <p>Only select 3 file: top, left and right sides</p>
                        <div
                          action="#"
                          className="d-block dropzone border-dashed rounded-2"
                        >
                          <div className="fallback">
                            <input
                              name="file"
                              type="file"
                              multiple
                              {...register(
                                "images",
                                updateProductOptions.images
                              )}
                            />
                          </div>
                          {errors.images && (
                            <div style={{ color: "red" }}>
                              {errors.images.message}
                            </div>
                          )}
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div className="col-lg-6 col-12">
                <div className="d-grid mb-4">
                  <button
                    type="button"
                    onClick={() =>
                      append({
                        sizeName: "",
                        colorName: "",
                        weight: 0,
                        height: 0,
                        length: 0,
                        width: 0,
                        stockQuantity: 0,
                      })
                    }
                    className="btn btn-primary"
                  >
                    Add Variant
                  </button>
                </div>

                {fields.map((item, index) => (
                  <div className="card mb-4" key={index}>
                    <div className="card-body d-flex flex-row">
                      <div className="mb-3 mr-1">
                        <label className="form-label">Size </label>
                        <input
                          type="text"
                          className="form-control"
                          placeholder="Size Name: S, M, L, XL , One Size"
                          {...register(
                            `variants.${index}.sizeName`,
                            updateProductOptions.sizeName
                          )}
                        ></input>
                      </div>
                      <div className="mb-3 mr-1">
                        <label className="form-label">Length</label>
                        <input
                          type="text"
                          className="form-control"
                          placeholder="cm"
                          {...register(
                            `variants.${index}.length`,
                            updateProductOptions.length
                          )}
                        ></input>
                      </div>
                      <div className="mb-3 mr-1">
                        <label className="form-label">Width</label>
                        <input
                          type="text"
                          className="form-control"
                          placeholder="cm"
                          {...register(
                            `variants.${index}.width`,
                            updateProductOptions.width
                          )}
                        ></input>
                      </div>
                      <div className="mb-3 mr-1">
                        <label className="form-label">Height</label>
                        <input
                          type="text"
                          className="form-control"
                          placeholder="Enter Height"
                          {...register(
                            `variants.${index}.height`,
                            updateProductOptions.height
                          )}
                        ></input>
                      </div>
                      <div className="mb-3 mr-1">
                        <label className="form-label">Weight</label>
                        <input
                          type="text"
                          className="form-control"
                          placeholder="kg"
                          {...register(
                            `variants.${index}.weight`,
                            updateProductOptions.weight
                          )}
                        ></input>
                      </div>
                      <div className="mb-3 mr-1">
                        <label className="form-label">Color</label>
                        <input
                          type="text"
                          className="form-control"
                          placeholder="red"
                          {...register(
                            `variants.${index}.colorName`,
                            updateProductOptions.colorName
                          )}
                        ></input>
                      </div>
                      <div className="mb-3">
                        <label className="form-label">Quantity</label>
                        <input
                          type="text"
                          className="form-control"
                          placeholder="Enter Quantity"
                          {...register(
                            `variants.${index}.stockQuantity`,
                            updateProductOptions.stcokQuantity
                          )}
                        ></input>
                      </div>
                      <div className="d-grid"></div>
                    </div>
                    <div className="m-3">
                      <button
                        type="submit"
                        href="#!"
                        className="btn btn-primary"
                        onClick={() => {
                          if (index === 0) {
                            toast.error("You cannot remove the first variant");
                          } else {
                            remove(index);
                          }
                        }}
                      >
                        Remove Variant
                      </button>
                    </div>
                  </div>
                ))}

                <div className="card mb-4">
                  <div className="card-body">
                    <div className="mb-3">
                      <label className="form-label">Regular Price</label>
                      <input
                        type="text"
                        className="form-control"
                        placeholder="$ 49.00"
                        {...register(
                          "priceDefault",
                          updateProductOptions.priceDefault
                        )}
                      ></input>
                      {errors.priceDefault && (
                        <div style={{ color: "red" }}>
                          {errors.priceDefault.message}
                        </div>
                      )}
                    </div>
                  </div>
                </div>
                <div className="d-grid">
                  <button type="submit" href="#!" className="btn btn-primary">
                    Update Product
                  </button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};
export default AdminUpdateProductPage;
