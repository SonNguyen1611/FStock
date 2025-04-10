import { createContext, useContext, useEffect, useState } from "react";
import PropTypes from "prop-types";
import AuthContext from "./AuthContext";
import { getCart } from "../service/CartService";



export const CartContext = createContext();

export const CartProvider = ({ children }) => {
    const [cartData, setCartData] = useState();
    const { user } = useContext(AuthContext);

    useEffect(() => {
        const fetchData = async () => {
            if (user) {
                try {
                    const result = await getCart(user.sub);
                    setCartData(result.data.data);
                } catch (error) {
                    console.error("Fetch cart failed", error);
                }
            }
        };
        fetchData();
    }, [user]);
    
    
    return (
        <CartContext.Provider value={{cartData , setCartData}}>
            {children}
        </CartContext.Provider>
    )

}
CartProvider.propTypes = {
    children: PropTypes.node.isRequired,
};