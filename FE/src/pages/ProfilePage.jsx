import { useContext, useEffect, useState } from "react"
import Banner from "../components/user/Banner"
import { CartContext } from "../contexts/CartContext";
import { useNavigate } from "react-router-dom";
import { getInfo } from "../service/UserService";
import AuthContext from "../contexts/AuthContext";
import { ToastContext } from "../contexts/ToastContext";

export const ProfilePage = () => {
    const {cartData} = useContext(CartContext);
    const {user} = useContext(AuthContext)
    const {toast} = useContext(ToastContext);
    const [quantity, setQuantity] = useState(0);
    const [userData, setUserData] = useState();

    const navigator = useNavigate();
    useEffect( () => {
        if(cartData) {
            let quantityAll = 0;
            for (let i = 0; i < cartData.length; i++) {
                const item = cartData[i];
                setQuantity( quantityAll += item.quantity);            
            }
        }
    }, [cartData])

    useEffect(() => {
        try {
            if(user){
                const fetchDataUser = async () => {
                    const userData = await getInfo();
                    setUserData(userData.data.data);
                }
                fetchDataUser();

            }
        } catch (error) {
            toast.error(error.response.data.message);
        }
        
    }, [user])
    console.log(userData);

    return (
       <>
       <Banner pageName={"Profile"} ></Banner>
        <section className="profile__area pt-120 pb-50 grey-bg">
        <div className="container">
           <div className="profile__basic-inner pb-20 bg-white">
              <div className="row align-items-center">
                 <div className="col-xxl-6 col-md-6">
                    <div className="profile__basic d-md-flex align-items-center">
                       <div className="profile__basic-thumb mr-30">
                          <img src="assets/img/testimonial/person-1.jpg" alt=""></img>
                       </div>
                       <div className="profile__basic-content">
                          <h3 className="profile__basic-title">
                             Welcome Back <span>{}</span>
                          </h3>
                       
                       </div>
                    </div>
                 </div>
                 <div className="col-xxl-6 col-md-6">
                    <div className="profile__basic-cart d-flex align-items-center justify-content-md-end">
                       <div className="cart-info mr-10">
                          <a href="cart.html" onClick={(e)=>  (
                            e.preventDefault(),
                            navigator("/cart")
                        )}>View cart</a>
                       </div>
                       <div className="cart-item">
                          <a href="cart.html">
                          <i className="fa-solid fa-cart-shopping"></i>
                          <span className="cart-quantity">{quantity}</span>
                          </a>
                       </div>
                    </div>
                 </div>
              </div>
           </div>
        </div>
     </section>
    
     <section className="profile__menu pb-70 grey-bg">
        <div className="container">
           <div className="row">
              <div className="col-xxl-4 col-md-4">
                 <div className="profile__menu-left bg-white mb-50">
                    <h3 className="profile__menu-title"><i className="fa fa-list-alt"></i> Your Menu</h3>
                    <div className="profile__menu-tab">
                       <div className="nav nav-tabs flex-column justify-content-start text-start" id="nav-tab" role="tablist">
                          <button className="nav-link active" id="nav-account-tab" data-bs-toggle="tab" data-bs-target="#nav-account" type="button" role="tab" aria-controls="nav-account" aria-selected="true"> <i className="fa fa-user"></i> My Account</button>
                          <button className="nav-link" id="nav-order-tab" data-bs-toggle="tab" data-bs-target="#nav-order" type="button" role="tab" aria-controls="nav-order" aria-selected="false"><i className="fa fa-file"></i>Orders</button>
                          <button className="nav-link" id="nav-password-tab" data-bs-toggle="tab" data-bs-target="#nav-password" type="button" role="tab" aria-controls="nav-password" aria-selected="false"><i className="fa fa-lock"></i>Change Password</button>
                          <button className="nav-link"><i className="fa fa-sign-out"></i> Logout</button>
                       </div>
                    </div>
                 </div>
              </div>
              <div className="col-xxl-8 col-md-8">
                 <div className="profile__menu-right">
                    <div className="tab-content" id="nav-tabContent">
                       <div className="tab-pane fade show active" id="nav-account" role="tabpanel" aria-labelledby="nav-account-tab">
                          <div className="profile__info">
                             <div className="profile__info-top d-flex justify-content-between align-items-center">
                                <h3 className="profile__info-title">Profile Information</h3>
                                <button className="profile__info-btn" type="button" data-bs-toggle="modal" data-bs-target="#profile_edit_modal"><i className="fa-regular fa-pen-to-square"></i> Edit Profile</button>
                             </div>

                             <div className="profile__info-wrapper white-bg">
                                <div className="profile__info-item">
                                   <p>Name</p>
                                   <h4>{userData?.userName}</h4>
                                </div>
                                <div className="profile__info-item">
                                   <p>Email</p>
                                   <h4><a href="mailt:outstock@hotmail.com" className="__cf_email__" data-cfemail="94fdfaf2fbd4f1f0e1fff1e6baf7fbf9">outstock@hotmail.com</a></h4>
                                </div>
                                <div className="profile__info-item">
                                   <p>Phone</p>
                                   <h4>(325) 463-5693</h4>
                                </div>
                                <div className="profile__info-item">
                                   <p>Address</p>
                                   <h4>Abingdon, Maryland(MD), 21009</h4>
                                </div>
                             </div>
                          </div>
                       </div>
                       <div className="tab-pane fade" id="nav-order" role="tabpanel" aria-labelledby="nav-order-tab">
                          <div className="order__info">
                             <div className="order__info-top d-flex justify-content-between align-items-center">
                                <h3 className="order__info-title">My Orders</h3>
                                <button type="button" className="order__info-btn"><i className="fa-regular fa-trash-can"></i> Clear</button>
                             </div>

                             <div className="order__list white-bg table-responsive">
                                <table className="table">
                                   <thead>
                                     <tr>
                                       <th scope="col">Order ID</th>
                                       <th scope="col">Name</th>
                                       <th scope="col">Price</th>
                                       <th scope="col">Details</th>
                                     </tr>
                                   </thead>
                                   <tbody>
                                     <tr>
                                       <td className="order__id">#3520</td>
                                       <td><a href="product-details.html" className="order__title">University seminar series global.</a></td>
                                       <td>$144.00</td>
                                       <td><a href="product-details.html" className="order__view-btn">View</a></td>
                                     </tr>
                                     <tr>
                                       <td className="order__id">#2441</td>
                                       <td><a href="product-details.html" className="order__title">Web coding and apache basics</a></td>
                                       <td>$59.54</td>
                                       <td><a href="product-details.html" className="order__view-btn">View</a></td>
                                     </tr>
                                     <tr>
                                       <td className="order__id">#2357</td>
                                       <td><a href="product-details.html" className="order__title">Economics historical development</a></td>
                                       <td>$89.90</td>
                                       <td><a href="product-details.html" className="order__view-btn">View</a></td>
                                     </tr>
                                   </tbody>
                                 </table>
                             </div>
                          </div>
                       </div>
                       <div className="tab-pane fade" id="nav-password" role="tabpanel" aria-labelledby="nav-password-tab">
                          <div className="password__change">
                             <div className="password__change-top">
                                <h3 className="password__change-title">Change Password</h3>
                             </div>
                             <div className="password__form white-bg">
                                <form action="#">
                                   <div className="password__input">
                                      <p>Old Password</p>
                                      <input type="password" placeholder="Enter Old Password"></input>
                                   </div>
                                   <div className="password__input">
                                      <p>New Password</p>
                                      <input type="password" placeholder="Enter New Password"></input>
                                   </div>
                                   <div className="password__input">
                                      <p>Confirm Password</p>
                                      <input type="password" placeholder="Confirm Password"></input>
                                   </div>
                                   <div className="password__input">
                                      <button type="submit" className="os-btn os-btn-black">Update password</button>
                                   </div>
                                </form>
                             </div>
                          </div>
                       </div>
                     </div>
                 </div>
              </div>
           </div>
        </div>
     </section>

     <div className="profile__edit-modal">
        <div className="modal fade" id="profile_edit_modal"  aria-labelledby="profile_edit_modal" aria-hidden="true">
           <div className="modal-dialog modal-dialog-centered">
              <div className="modal-content">
                 <div className="profile__edit-wrapper">
                    <div className="profile__edit-close">
                       <button type="button" className="profile__edit-close-btn" data-dismiss="modal"><i className="fa fa-times"></i></button>
                    </div>
                    <form action="#">
                       <div className="profile__edit-input">
                          <p>Name</p>
                          <input type="text" placeholder="Your Name"></input>
                       </div>
                       <div className="profile__edit-input">
                          <p>Email</p>
                          <input type="text" placeholder="Your Email"></input>
                       </div>
                       <div className="profile__edit-input">
                          <p>Phone</p>
                          <input type="text" placeholder="Your Phone"></input>
                       </div>
                       <div className="profile__edit-input">
                          <p>Address</p>
                          <input type="text" placeholder="Your Address"></input>
                       </div>
                       <div className="profile__edit-input">
                          <button type="submit" className="os-btn os-btn-black w-100">Update</button>
                       </div>
                    </form>
                 </div>
              </div>
           </div>
        </div>
     </div>
       </>
    )
}