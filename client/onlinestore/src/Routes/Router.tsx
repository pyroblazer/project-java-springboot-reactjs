import React, { useState } from "react";
import { Routes, Route } from "react-router-dom";
import { Home } from "../Pages/Home";
import { ProductDetails } from "../Pages/ProductDetails";
import { Shop } from "../Pages/Shop";
import { Cart } from "../Pages/Cart";
import { CheckOut } from "../Pages/CheckOut";
import { Login } from "../Pages/Login";
import { Signup } from "../Pages/Signup";
import { Orders } from "../Pages/Orders";
import { Protected } from "../Component/Protected";

import { StripeElementsOptions, loadStripe } from "@stripe/stripe-js";
import { Elements } from "@stripe/react-stripe-js";

export const Router = () => {
  const [isSignedIn, setIsSignedIn] = useState(
    sessionStorage.getItem("token") || false
  );

  const options = {
    mode: "payment",
    currency: "usd",
    amount: 1099,
  };

  const stripePromise = loadStripe(process.env.REACT_APP_STRIPE_PUBLIC_KEY as string);

  return (
    <>
      <Routes>
        <Route
          path="/"
          element={
            <Protected isSignedIn={isSignedIn}>
              <Home />
            </Protected>
          }
        />
        <Route
          path="/shop"
          element={
            <Protected isSignedIn={isSignedIn}>
              <Shop />
            </Protected>
          }
        />
        <Route
          path="/product/:id"
          element={
            <Protected isSignedIn={isSignedIn}>
              <ProductDetails />
            </Protected>
          }
        />
        <Route
          path="/cart"
          element={
            <Protected isSignedIn={isSignedIn}>
              <Elements stripe={stripePromise} options={options as StripeElementsOptions}>
                <Cart />
              </Elements>
            </Protected>
          }
        />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        {/* <Route
          path="/checkout"
          element={
            <Protected isSignedIn={isSignedIn}>
              <CheckOut />
            </Protected>
          }
        /> */}
        <Route
          path="/orders"
          element={
            <Protected isSignedIn={isSignedIn}>
              <Orders />
            </Protected>
          }
        />
        {/* <Route path="users" element={<AdminUser />} /> */}
      </Routes>
    </>
  );
};
