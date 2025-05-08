import {  useEffect } from "react";
import { useLocation } from "react-router-dom";
import PropType from 'prop-types';
import AdminSidebar from "./AdminSidebar";
import AdminNavbar from "./AdminNavbar";


const LayoutAdmin = ({ Component, ...props }) => {
  const { pathname } = useLocation();
  useEffect(() => {
    const link = document.createElement("link");
    link.rel = "stylesheet";
    link.type = "text/css"; 
    link.href = "/css/theme.css";
    document.head.appendChild(link);
  
    return () => {
      document.head.removeChild(link);
    };
  }, []);

  useEffect(() => {
    window.scrollTo(0, 0);
  }, [pathname]);
  return (
    <main id="main-wrapper" className="main-wrapper">
      <AdminNavbar></AdminNavbar>
      <AdminSidebar></AdminSidebar>
      <Component {...props}></Component>
    </main>
  );
};
export default LayoutAdmin;

LayoutAdmin.propTypes = {
    Component : PropType.node.isRequired
};
