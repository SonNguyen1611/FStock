import { useContext, useEffect, useState } from "react";
import { getOrderById, updateOrderStatus } from "../service/OrderService";
import { useParams } from "react-router-dom";
import { Steps } from "antd";
import Loading from "../components/user/Loading";
import { ToastContext } from "../contexts/ToastContext";

const OrderDetailPage = () => {
  const [orderData, setOrderData] = useState();
  const { toast } = useContext(ToastContext);
  const { orderId } = useParams();

  const statusDescriptions = {
    PENDING: "Đơn hàng đang chờ được xác nhận",
    PROCESSING: "Người bán đang xử lí đơn hàng của bạn",
    SHIPPED: "Đơn hàng đã được giao cho người nhận",
    PAID: "Đơn hàng đã được thanh toán, chúng tôi đang xử lí đơn hàng của bạn",
    REFUNDING:
      "Đơn hàng của bạn đang được xử lý hoàn tiền, chúng tôi sẽ thông báo cho bạn khi hoàn tiền thành công",
    REFUNDED: "Đã hoàn tiền",
    REJECT: "Đơn hàng của bạn đã bị từ chối",
    COMPLETED: "Hoàn thành",
    SHIPPING: "Đang giao hàng",
    RETURNED: "Đã trả hàng",
    CANCELED: "Đơn hàng đã bị hủy bỏ",
  };
  const orderCurrentStep = {
    PENDING: 0,
    PROCESSING: 1,
    SHIPPED: 4,
    PAID: 1,
    REFUNDING: 1,
    REFUNDED: 1,
    REJECT: 0,
    COMPLETED: 4,
    SHIPPING: 2,
    RETURNED: 3,
    CANCELED: 1,
  };
  const orderStatus = {
    PENDING: "process",
    PROCESSING: "process",
    SHIPPED: "process",
    PAID: "process",
    REFUNDING: "process",
    REFUNDED: "error",
    REJECT: "error",
    COMPLETED: "finish",
    SHIPPING: "process",
    RETURNED: "error",
    CANCELED: "error",
  };

  useEffect(() => {
    try {
      const fetchDataOrder = async () => {
        const res = await getOrderById(orderId);
        setOrderData(res.data.data);
      };
      fetchDataOrder();
    } catch (error) {
      console.log(error);
    }
  }, []);

  const handleCancelOrder = async (orderId, curStatus, status) => {
    if (curStatus === "PENDING") {
      try {
        const res = await updateOrderStatus(orderId, status);
        if (res.status === 200) {
          toast.success("Hủy đơn hàng thành công");
        }
      } catch {
        toast.error("Hủy đơn hàng không thành công");
      }
    }
    else {
      toast.error("Đơn hàng không thể hủy");
    }
  };

  console.log(orderData);
  if (orderData === undefined || orderData === null) {
    return <Loading></Loading>;
  }
  return (
    <div className="your-order mb-30 row">
      <Steps
        current={orderCurrentStep[orderData.orderStatus]}
        percent={60}
        status={orderStatus[orderData.orderStatus]}
        items={[
          {
            title: "Xác nhận đơn hàng",
            description:
              orderCurrentStep[orderData.orderStatus] === 0
                ? statusDescriptions[orderData.orderStatus]
                : null,
          },
          {
            title: "Đang xử lý đơn hàng",
            description:
              orderCurrentStep[orderData.orderStatus] === 1
                ? statusDescriptions[orderData.orderStatus]
                : null,
          },
          {
            title: "Giao hàng",
            description:
              orderCurrentStep[orderData.orderStatus] === 2
                ? statusDescriptions[orderData.orderStatus]
                : null,
          },
          {
            title: "Giao hàng thành công",
            description:
              orderCurrentStep[orderData.orderStatus] === 3
                ? statusDescriptions[orderData.orderStatus]
                : null,
          },
          {
            title: "Hoàn thành",
            description:
              orderCurrentStep[orderData.orderStatus] === 4
                ? statusDescriptions[orderData.orderStatus]
                : null,
          },
        ]}
        style={{
          width: "100%",
          marginBottom: "40px",
          marginTop: "60px",
        }}
      />

      <div className="col-lg-6">
        <div className="your-order-table table-responsive">
          <table>
            <tfoot>
              <tr>
                <th>Order Id</th>
                <td>
                  <span>{orderData.orderId}</span>
                </td>
              </tr>

              <tr>
                <th>Recipient Name</th>
                <td>
                  <span>{orderData.recipientName}</span>
                </td>
              </tr>
              <tr>
                <th>Phone </th>
                <td>
                  <span>{orderData.phoneNumber}</span>
                </td>
              </tr>
              <tr>
                <th>Email</th>
                <td>
                  <span>{orderData.email}</span>
                </td>
              </tr>
              <tr>
                <th>Address</th>
                <td>
                  <span>{orderData.shippingAddress}</span>
                </td>
              </tr>
              <tr>
                <th>Note</th>
                <td>
                  <span>{orderData.note}</span>
                </td>
              </tr>
              <tr className="shipping">
                <th>Payment Method</th>
                <td>
                  <strong>
                    <span>{orderData.paymentMethod}</span>
                  </strong>
                </td>
              </tr>
            </tfoot>
          </table>
        </div>
      </div>

      <div className="col-lg-6">
        <div className="your-order-table table-responsive">
          <table>
            <thead>
              <tr>
                <th className="product-name"></th>
                <th className="product-name">Product</th>
                <th className="product-total">Price</th>
              </tr>
            </thead>
            <tbody>
              {orderData &&
                orderData.orderItems?.map((item) => (
                  <tr className="cart_item" key={item?.productId}>
                    <img
                      className="img-4by3-lg rounded-3 p-1"
                      src={item?.imageUrlDisplay}
                      alt={item?.productName || "Product image"}
                    ></img>
                    <td className="product-name">
                      {item?.productName}{" "}
                      <strong className="product-quantity">
                        {" "}
                        × {item?.quantity}
                      </strong>
                    </td>
                    <td className="product-total">
                      <span>${item?.priceAtOrder}</span>
                    </td>
                  </tr>
                ))}
            </tbody>
          </table>
          <div className=" float-right mt-3">
            <button
              className="os-btn os-btn-black mb-3 r-0"
              name="update_cart"
              type="submit"
              onClick={() => {
                if (orderData.paymentMethod === "VNPAY") {
                  handleCancelOrder(
                    orderData.orderId,
                    orderData.orderStatus,
                    "REFUNDING"
                  );
                }else {
                  handleCancelOrder(
                    orderData.orderId,
                    orderData.orderStatus,
                    "CANCELED"
                  );
                }
                
              }}
            >
              Hủy đơn hàng
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
export default OrderDetailPage;
