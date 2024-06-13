import React, { Dispatch, useEffect, useState } from 'react'
import { Header } from '../Component/Header'
import { Footer } from '../Component/Footer'
import { useParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import { Product } from '../shared.types';

export const ProductDetails = () => {

  const [product, setProduct]: [Product, Dispatch<Product>] = useState({} as Product);
  const [quantity, setQuantity] = useState(1);
  const [token, setToken] = useState(sessionStorage.getItem("token"));

  const { id } = useParams();

  const handleQuantity = (e) => {
    setQuantity(e.target.value);
  }
  const handleMinus = () => {
    if (quantity > 1) {
      setQuantity(quantity - 1);
    }
  }
  const handlePlus = () => {
    if (quantity > 0) {
      setQuantity(quantity + 1);
    }
  }
  const onToast = () => {
    toast.success('Added to cart!', {
      position: "bottom-center",
      autoClose: 5000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
      theme: "light",
    });
  }
  const handleCart = async (quantity: Number) => {
    console.log(quantity);
    const res = await fetch(
      `http://localhost:9090/cart/addproduct`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": "Bearer " + token
        },
        body: JSON.stringify({
          productId: id,
          quantity: quantity,
        }),
      }

    );
    if (res.status === 200) {
      onToast();
    }


  };
  useEffect(() => {
    window.scrollTo(0, 0)

    const fatchData = async () => {
      const response = await fetch(`http://localhost:9090/product/${id}`, {
        headers: {
          "Authorization": "Bearer " + token
        },
      });

      const res = await response.json();
      setProduct(res);
    }
    fatchData();
  }, []);


  return (
    <>

      <>
        <Header />
        <div className="pd-wrap">
          <div className="container">
            {/* <div className="heading-section">
        <h2>Product Details</h2>
      </div> */}
            <div className="row">
              <div className="col-md-6 d-flex justify-content-center">
                {!product.img ? <></> : <img
                  src={`data:image/png;base64,${product.img}`}
                  alt="Product"
                  className="img-fluid rounded shadow-sm d-inline "
                />}
              </div>
              <div className="col-md-6">
                <div className="product-dtl">
                  <div className="product-info">
                    <div className="product-name">{product.name}</div>
                    <div className="reviews-counter">
                      <div className="rate">
                        <input
                          type="radio"
                          id="star5"
                          name="rate"
                          defaultValue={5}
                          defaultChecked={false}
                        />
                        <label htmlFor="star5" title="text">
                          5 stars
                        </label>
                        <input
                          type="radio"
                          id="star4"
                          name="rate"
                          defaultValue={4}
                          defaultChecked={false}
                        />
                        <label htmlFor="star4" title="text">
                          4 stars
                        </label>
                        <input
                          type="radio"
                          id="star3"
                          name="rate"
                          defaultValue={3}
                          defaultChecked={false}
                        />
                        <label htmlFor="star3" title="text">
                          3 stars
                        </label>
                        <input type="radio" id="star2" name="rate" defaultValue={2} />
                        <label htmlFor="star2" title="text">
                          2 stars
                        </label>
                        <input type="radio" id="star1" name="rate" defaultValue={1} />
                        <label htmlFor="star1" title="text">
                          1 star
                        </label>
                      </div>
                      <span>3 Reviews</span>
                    </div>
                    <div className="product-price-discount">
                      <span>$ {product.price}</span>
                      <span className="line-through">$ {product.price + 100}</span>
                    </div>
                  </div>
                  <p>
                    {product.description}
                  </p>
                  <div className="row">
                    {/* <div className="col-md-6">
                <label htmlFor="size">Size</label>
                <select id="size" name="size" className="form-control">
                  <option>S</option>
                  <option>M</option>
                  <option>L</option>
                  <option>XL</option>
                </select>
              </div> */}
                    {/* <div className="col-md-6">
                <label htmlFor="color">Color</label>
                <select id="color" name="color" className="form-control">
                  <option>Blue</option>
                  <option>Green</option>
                  <option>Red</option>
                </select>
              </div> */}
                  </div>
                  <div className="product-count">
                    <label htmlFor="size">Quantity</label>
                    <form action="#" className="display-flex">
                      <div className="qtyminus" onClick={() => handleMinus()}>-</div>
                      <input
                        type="text"
                        name="quantity"
                        onChange={(e) => handleQuantity(e)}
                        defaultValue={1}
                        value={quantity}
                        className="qty"
                      />
                      <div className="qtyplus" onClick={() => handlePlus()}>+</div>
                    </form>
                    {/* eslint-disable-next-line jsx-a11y/anchor-is-valid */}
                    <a className="round-black-btn" onClick={(event) => {
                      event.preventDefault();
                      handleCart(quantity);
                    }} href="#">
                      Add to Cart
                    </a>
                  </div>
                </div>
              </div>
            </div>

          </div>
        </div>
        <Footer />
      </>



    </>
  )
}
