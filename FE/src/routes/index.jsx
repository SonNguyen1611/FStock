import { createBrowserRouter } from "react-router-dom";
import ProductDetailPage from "../pages/ProductDetailPage.jsx";
import ShopPage from "../pages/ShopPage.jsx";
import LayoutUser from "../components/user/LayoutUser.jsx";
import HomePage from "../pages/HomePage.jsx";
import LoginPage from "../pages/LoginPage.jsx";
import RegisterPage from "../pages/RegisterPage.jsx";
import CartPage from "../pages/CartPage.jsx";
import { ProfilePage } from "../pages/ProfilePage.jsx";

const routers = createBrowserRouter([
  {
    path: "/",
    element: <LayoutUser Component={HomePage}></LayoutUser>,
  },
  {
    path: "/shop",
    element: <LayoutUser Component={ShopPage}></LayoutUser>,
  },
  {
    path: "/product-detail/:id",
    element: <LayoutUser Component={ProductDetailPage}></LayoutUser>,
  },
  {
    path: "/login",
    element: <LayoutUser Component={LoginPage}></LayoutUser>,
  },
  {
    path: "/register",
    element: <LayoutUser Component={RegisterPage}></LayoutUser>,
  },
  {
    path: "/cart",
    element: <LayoutUser Component={CartPage}></LayoutUser>,
  },
  {
    path: "/profile",
    element: <LayoutUser Component={ProfilePage}></LayoutUser>,
  },
]);
export default routers;
