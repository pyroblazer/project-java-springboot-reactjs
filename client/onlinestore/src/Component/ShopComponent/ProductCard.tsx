import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";

export const ProductCard = (props) => {
  const navigate = useNavigate();

  const [token, setToken] = useState(sessionStorage.getItem("token"));

  const onToast = () => {
    toast.success("Added to cart!", {
      position: "bottom-center",
      autoClose: 5000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
      theme: "light",
    });
  };
  const handleClick = (id) => {
    navigate(`/product/${id}`);
  };

  const handleCart = async () => {
    if (sessionStorage.getItem("token") === null) {
      navigate("/login");
    }
    const res = await fetch("http://localhost:9090/cart/addproduct", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
      body: JSON.stringify({
        productId: props.id,
      }),
    });
    if (res.status === 200) {
      onToast();
      const data = await res.json();
    } else {
      navigate("/login");
    }
  };
  return (
    <>
      <li>
        <div className="product-card">
          <figure className="card-banner">
            <img
              src={`data:image/png;base64,${props.img}`}
              width={189}
              height={189}
              loading="lazy"
              alt={`${props.name}`}
            />
            <div className="btn-wrapper">
              <button
                className="product-btn"
                onClick={() => handleClick(props.id)}
                aria-label="Quick View"
              >
                {/* @ts-ignore */}
                <ion-icon name="eye-outline" />
                <div className="tooltip">Quick View</div>
              </button>
            </div>
          </figure>
          <div className="rating-wrapper">
            {/* @ts-ignore */}
            <ion-icon name="star" />
            {/* @ts-ignore */}
            <ion-icon name="star" />
            {/* @ts-ignore */}
            <ion-icon name="star" />
            {/* @ts-ignore */}
            <ion-icon name="star" />
            {/* @ts-ignore */}
            <ion-icon name="star" />
          </div>
          <h3 className="h4 card-title">
            <a href={`/product/${props.id}`}>{props.name}</a>
          </h3>
          <div className="price-wrapper">
            <del className="del">$ {props.price + 100}</del>
            <data className="price" value={85.0}>
              $ {props.price}
            </data>
          </div>
          <button className="btn btn-primary" onClick={() => handleCart()}>
            Add to Cart
          </button>
        </div>
      </li>
    </>
  );
};
