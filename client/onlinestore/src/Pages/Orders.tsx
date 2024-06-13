import React, { Dispatch, useEffect, useState } from "react";
import { Footer } from "../Component/Footer";
import { Header } from "../Component/Header";
import { PrettyPrintedDate } from "../Component/PrettyPrintedDate";
import OrderStatusDisplay from "../Component/OrderComponent/OrderStatus";
import { OrderDetailTable } from "../Component/OrderComponent/OrderDetailTable";
import { Order } from "../shared.types";

export const Orders = () => {
  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  const [data, setData] = useState();
  const [orders, setOrders]: [Order[], Dispatch<Order[]>] = useState([] as Order[]);
  const [loading, setLoading] = useState(9);
  const [totalAmount, setTotalAmount] = useState(0.0);
  const [token, setToken] = useState(sessionStorage.getItem("token"));
  const [user, setUser] = useState({});

  const fetchOrders = async () => {
    console.log(token);
    const res = await fetch(`http://localhost:9090/orders/user`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    });
    const data = await res.json();
    setOrders(data);
    console.log(data);
  };

  useEffect(() => {
    fetchOrders();
  }, [loading]);

  return (
    <>
      <Header />
      <div className="px-4 px-lg-0">
        <div className="pb-5">
          <div className="container">
            <div className="row">
              <div className="col-lg-12 p-5 bg-white rounded shadow-sm mb-5">
                <div className="table-responsive">
                  <table className="table">
                    <thead>
                      <tr>
                        <th scope="col" className="border-0 bg-light">
                          <div className="p-2 px-3 text-uppercase">Id</div>
                        </th>
                        <th scope="col" className="border-0 bg-light">
                          <div className="py-2 text-uppercase">Date</div>
                        </th>
                        <th scope="col" className="border-0 bg-light">
                          <div className="py-2 text-uppercase">
                            Order Status
                          </div>
                        </th>
                        <th scope="col" className="border-0 bg-light">
                          <div className="py-2 text-uppercase">
                            Total Amount
                          </div>
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                      {orders ? (
                        orders.map((elem, index) => {
                          return (
                            <>
                              <tr key={index}>
                                <td className="border-0 bg-light">
                                  <div className="p-2 px-3 text-uppercase">
                                    {elem.id}
                                  </div>
                                </td>
                                <td className="border-0 bg-light">
                                  <div className="py-2 text-uppercase">
                                    <PrettyPrintedDate
                                      dateString={elem.moment}
                                    />
                                  </div>
                                </td>
                                <td className="border-0 bg-light">
                                  <div className="py-2 text-uppercase">
                                    <OrderStatusDisplay
                                      status={elem.orderStatus}
                                    />
                                  </div>
                                </td>
                                <td className="border-0 bg-light">
                                  <div className="py-2 text-uppercase">
                                    {elem.totalAmount}
                                  </div>
                                </td>
                              </tr>
                              {elem.orderDetails &&
                                elem.orderDetails.length > 0 && (
                                  <tr key={`order-details-${index}`}>
                                    <td colSpan={4}>
                                      <OrderDetailTable
                                        orderDetails={elem.orderDetails}
                                      />
                                    </td>
                                  </tr>
                                )}
                            </>
                          );
                        })
                      ) : (
                        <>No orders</>
                      )}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
};
