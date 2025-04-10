const BtnAddToCart = ({handleAddToCart}) => {
    return (
        <a href="#" className="add-cart-btn mr-10" onClick={(e)=> handleAddToCart(e)}>
            + Add to Cart
        </a>
    );
}

export default BtnAddToCart;