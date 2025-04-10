import { useContext } from "react";
import { loginAuth } from "../service/AuthService";
import AuthContext from "../contexts/AuthContext";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import Banner from "../components/user/Banner";
import { ToastContext } from "../contexts/ToastContext";


const LoginPage = () => {
  const { login } = useContext(AuthContext);
  const { toast } = useContext(ToastContext);
  const navigator = useNavigate();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const loginOptions = {
    email: {
      required: "email must not be blank",
    },
    password: {
      required: "password must not be blank",
    },
  };

  const handleNavigateRegister = () => {
    navigator("/register");
  };

  const onSubmit = async (data) => {
    try {
      const response = await loginAuth(data);
      await login(response.data.data.token);
      if (localStorage.getItem("authToken")) {
        navigator(-1)
        toast.success("Login successful");
      }
    } catch (error) {
        toast.error(error.response.data.message);
    }
  };

  return (
    <>
      <Banner pageName={"Login"}></Banner>
      <section className="login-area pt-100 pb-100">
        <div className="container">
          <div className="row">
            <div className="col-lg-8 offset-lg-2">
              <div className="basic-login">
                <h3 className="text-center mb-60">Login From Here</h3>
                <form onSubmit={handleSubmit(onSubmit)}>
                  <label>
                    Email Address <span>**</span>
                  </label>
                  <input
                    type="text"
                    placeholder="Email address..."
                    {...register("email", loginOptions.email)}
                  />
                  {errors.email && (
                    <div style={{ color: "red" }}>{errors.email.message}</div>
                  )}
                  <label>
                    Password <span>**</span>
                  </label>
                  <input
                    type="password"
                    placeholder="Enter password..."
                    {...register("password", loginOptions.password)}
                  />
                  {errors.password && (
                    <div style={{ color: "red" }}>
                      {errors.password.message}
                    </div>
                  )}
                  <div className="login-action mb-20 fix">
                    <span className="log-rem f-left">
                      <input id="remember" type="checkbox" />
                      <label>Remember me!</label>
                    </span>
                    <span className="forgot-login f-right">
                      <a href="#">Lost your password?</a>
                    </span>
                  </div>
                  <button className="os-btn w-100" type="submit">
                    Login Now
                  </button>
                  <div className="or-divide">
                    <span>or</span>
                  </div>
                  <a
                    className="os-btn os-btn-black w-100"
                    onClick={handleNavigateRegister}
                  >
                    Register Now
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
export default LoginPage;
