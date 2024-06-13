import React, { useEffect, useState } from "react";
import { Footer } from "../Component/Footer";
import { Header } from "../Component/Header";
import { Items } from "../Component/CartComponent/Items";
import { CardElement, useElements, useStripe } from "@stripe/react-stripe-js";

export const Cart = () => {
  useEffect(() => { window.scrollTo(0, 0) }, []);
  const stripe = useStripe();
  const elements = useElements();
  
      const [data, setdata] = useState();
      const[item,setItem]=useState([]);
      const [loading, setLoading] = useState(9);
      const [totalAmount, setTotalAmount] = useState(0.00);
      const[token,setToken]=useState(sessionStorage.getItem("token"));

      const CARD_OPTIONS = {
        iconStyle: "solid",
        style: {
          base: {
            iconColor: "#c4f0ff",
            color: "#000",
            fontWeight: 500,
            fontFamily: "Roboto, Open Sans, Segoe UI, sans-serif",
            fontSize: "16px",
            fontSmoothing: "antialiased",
            ":-webkit-autofill": { color: "#fce883" },
            "::placeholder": { color: "#87bbfd" },
          },
          invalid: {
            iconColor: "#fa0000",
            color: "#ffc7ee",
          },
        },
      };


      const fatchCart = async () => {
        // get cart item
        console.log(token);
        const res = await fetch("http://localhost:9090/cart/1", {headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer "+token
          },
        });
        const data = await res.json();
        setTotalAmount(data.totalAmount);
        setItem(data.cartDetails);
      };

      useEffect(() => {
        fatchCart();

      }, [loading]);

      const createPaymentIntent = async (e) => {
        const { error, paymentMethod } = await stripe.createPaymentMethod({
          type: "card",
          card: elements.getElement(CardElement),
        });

        if (error) return

        const { id } = paymentMethod;
        const res = await fetch(`http://localhost:9090/payment/payment-intent`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
          },
          body: {
            amount: totalAmount*100,
            id
          }
        })
        const da = await res.json();
        setdata(da);
        return da;
      }

      const handlePayment = async () => {
        const paymentIntent = await createPaymentIntent();
        if (paymentIntent) {
          console.log(paymentIntent);
          console.log("Successful payment");
        }
      };

  return (
    <>
      <Header />
      <div className="shopping-cart">
        <div className="px-4 px-lg-0">
          <div className="pb-5">
            <div className="container">
              <div className="row">
                <div className="col-lg-12 p-5 bg-white rounded shadow-sm mb-5">
                  {/* Shopping cart table */}
                  <div className="table-responsive">
                    <table className="table">
                      <thead>
                        <tr>
                          <th scope="col" className="border-0 bg-light">
                            <div className="p-2 px-3 text-uppercase">
                              Product
                            </div>
                          </th>
                          <th scope="col" className="border-0 bg-light">
                            <div className="py-2 text-uppercase">Price</div>
                          </th>
                          <th scope="col" className="border-0 bg-light">
                            <div className="py-2 text-uppercase">Quantity</div>
                          </th>
                          <th scope="col" className="border-0 bg-light">
                            <div className="py-2 text-uppercase">Remove</div>
                          </th>
                        </tr>
                      </thead>
                      <tbody>


                    {item ?    item.map((elem,index) => {
                          return (
                            <>

                              <Items
                                key={index} prop={elem} setLoading={setLoading} />
                              </>
                          )})
                        :<></>}

                      </tbody>
                    </table>
                  </div>
                  {/* End */}
                </div>
              </div>
              <div className="row py-5 p-4 bg-white rounded shadow-sm">
                <div className="col-lg-6"></div>
                <div className="col-lg-6">
                  <div className="bg-light rounded-pill px-4 py-3 text-uppercase font-weight-bold">
                    Order summary{" "}
                  </div>
                  <div className="p-4">
                    <ul className="list-unstyled mb-4">
                      {/* <li className="d-flex justify-content-between py-3 border-bottom">
                        <strong className="text-muted">Order Subtotal </strong>
                        <strong>${totalAmount.toFixed(2)}</strong>
                      </li>
                      <li className="d-flex justify-content-between py-3 border-bottom">
                        <strong className="text-muted">Tax</strong>
                        <strong>$0.00</strong>
                      </li> */}
                      <li className="d-flex justify-content-between py-3 border-bottom">
                        <strong className="text-muted">Total</strong>
                        <h3 className="font-weight-bold">${totalAmount.toFixed(2)}</h3>
                      </li>
                    </ul>
                    <button
                      href=""
                      className="btn btn-dark rounded-pill py-2 btn-block"
                      onClick={(e) => handlePayment(e)}
                    >
                      Procceed to checkout
                    </button>
                  </div>
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