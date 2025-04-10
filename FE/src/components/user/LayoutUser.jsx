import {  useEffect } from "react";
import Footer from "./Footer";
import Header from "./Header";
import { useLocation } from "react-router-dom";
import PropType from 'prop-types';


const LayoutUser = ({ Component, ...props }) => {
  const { pathname } = useLocation();

  useEffect(() => {
    window.scrollTo(0, 0);
  }, [pathname]);
  return (
    <>
      <Header></Header>
      <Component {...props}></Component>
      <Footer></Footer>
    </>
  );
};
export default LayoutUser;

LayoutUser.propTypes = {
    Component : PropType.node.isRequired
};
