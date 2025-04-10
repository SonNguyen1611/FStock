import { useForm } from "react-hook-form";
import { registerApi } from "../service/AuthService";
import { useContext, useRef } from "react";
import { useNavigate } from "react-router-dom";
import Banner from "../components/user/Banner";
import { ToastContext } from "../contexts/ToastContext";


const RegisterPage = () => {
  const {toast } = useContext(ToastContext);
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();
  const ref = useRef(null);
  const navigator = useNavigate();
  const registerOptions = {
    firstName: {
      required: "First Name must not be blank",
    },
    lastName: {
      required: "Last Name must not be blank",
    },
    phone: {
      required: "Phone must not be blank",
      pattern: {
        value: /^(0[0-9]{9,11})$/,
        message: "Invalid phone number format",
      },
    },
    address: {
      required: "Address must not be blank",
    },
    userName: {
      required: "User Name must not be blank",
      minLength: {
        value: 8,
        message: "User Name must be at least 8 characters",
      },
      maxLength: {
        value: 30,
        message: "User Name must be at most 30 characters",
      },
    },
    email: {
      required: "Email must not be blank",
      pattern: {
        value: /^[A-Za-z0-9+_.-]+@(.+)$/,
        message: "Invalid email format",
      },
    },
    password: {
      required: "Password must not be blank",
      pattern: {
        value: /^(?=.*[A-Z])(?=.*[@#$%^&+=!.()+ =]).{8,}$/,
        message:
          "Password must have at least 8 characters, one uppercase letter, and one special character",
      },
    },
  };
  const onSubmit = async (data) => {
    try {
      const response = await registerApi(data);
      console.log(response.status);
      if (response.status === 200) {
       toast.success("Register successful");
      }
      ref.current.focus({ behavior: "smooth" });
    } catch (error) {
      toast.error(error.response.data.message);
    }
  };
  const handleNavigateLogin = () => {
    navigator("/login");
  };

  return (
    <>
      <Banner pageName={"Register"}></Banner>
      <section className="login-area pt-100 pb-100">
        <div className="container">
          <div className="row">
            <div className="col-lg-8 offset-lg-2">
              <div className="basic-login">
                <h3 className="text-center mb-60" ref={ref}>
                  Register From Here
                </h3>
                
                  
                <form onSubmit={handleSubmit(onSubmit)}>
                  <div className="row">
                    <div className="col-md-6">
                      <label>
                        First Name <span>**</span>
                      </label>
                      <input
                        id="first-name"
                        type="text"
                        placeholder="Enter First Name"
                        {...register("firstName", registerOptions.firstName)}
                      />
                      {errors.firstName && (
                        <div style={{ color: "red" }}>
                          {errors.firstName.message}
                        </div>
                      )}
                    </div>
                    <div className="col-md-6">
                      <label>
                        Last Name <span>**</span>
                      </label>
                      <input
                        id="last-name"
                        type="text"
                        placeholder="Enter Last Name"
                        {...register("lastName", registerOptions.lastName)}
                      />
                      {errors.lastName && (
                        <div style={{ color: "red" }}>
                          {errors.lastName.message}
                        </div>
                      )}
                    </div>
                  </div>
                  <label>
                    Phone <span>**</span>
                  </label>
                  <input
                    id="phone"
                    type="text"
                    placeholder="Enter Phone Number"
                    {...register("phone", registerOptions.phone)}
                  />
                  {errors.phone && (
                    <div style={{ color: "red" }}>{errors.phone.message}</div>
                  )}

                  <label>
                    Address <span>**</span>
                  </label>
                  <input
                    id="address"
                    type="text"
                    placeholder="Enter Address"
                    {...register("address", registerOptions.address)}
                  />
                  {errors.address && (
                    <div style={{ color: "red" }}>{errors.address.message}</div>
                  )}

                  <label>
                    Username <span>**</span>
                  </label>
                  <input
                    id="name"
                    type="text"
                    placeholder="Enter Username"
                    {...register("userName", registerOptions.userName)}
                  />
                  {errors.userName && (
                    <div style={{ color: "red" }}>
                      {errors.userName.message}
                    </div>
                  )}

                  <label>
                    Email Address <span>**</span>
                  </label>
                  <input
                    id="email-id"
                    type="text"
                    placeholder="Email address..."
                    {...register("email", registerOptions.email)}
                  />
                  {errors.email && (
                    <div style={{ color: "red" }}>{errors.email.message}</div>
                  )}

                  <label>
                    Password <span>**</span>
                  </label>
                  <input
                    id="pass"
                    type="password"
                    placeholder="Enter password..."
                    {...register("password", registerOptions.password)}
                  />
                  {errors.password && (
                    <div style={{ color: "red" }}>
                      {errors.password.message}
                    </div>
                  )}

                  <div className="mt-10"></div>
                  <button className="os-btn w-100" type="submit">
                    Register Now
                  </button>

                  <div className="or-divide">
                    <span>or</span>
                  </div>
                  <a
                    className="os-btn os-btn-black w-100"
                    onClick={handleNavigateLogin}
                  >
                    Login Now
                  </a>
                </form>
              </div>
            </div>
          </div>
        </div>
      </section>
    </>
  );
};
export default RegisterPage;
