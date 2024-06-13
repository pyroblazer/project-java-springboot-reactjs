import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import * as Scroll from 'react-scroll';

// Or Access Link,Element,etc as follows
let Link = Scroll.Link;

export const Header = () => {
  

  const [islogin, setislogin] = useState(sessionStorage.getItem("token"));
  
  const navigate = useNavigate();
  const handleRedirect = () => {
    if (islogin) {
      navigate(`/cart`);
   
    } else {
      navigate(`/login`);

    }
  };

  const handleLogout = () => {
    sessionStorage.removeItem("token");
    setislogin(false)
    navigate(`/`);
  };

  return (
    <header className="header" data-header="">
      <div className="nav-wrapper">
        <div className="container">
          <h1 className="h1">
            <a href="/" className="logo">
              Online<span className="span">Store</span>
            </a>
          </h1>
          <button
            className="nav-open-btn"
            aria-label="Open Menu"
            data-nav-open-btn=""
          >
            <ion-icon name="menu-outline" />
          </button>
          <nav className="navbar" data-navbar="">
            <button
              className="nav-close-btn"
              aria-label="Close Menu"
              data-nav-close-btn=""
            >
              <ion-icon name="close-outline" />
            </button>
            <ul className="navbar-list">
              <li>
                <a href="/" className="navbar-link">
                  Home
                </a>
              </li>
              <li>
                <a href="/shop" className="navbar-link">
                  Shop
                </a>
              </li>
            </ul>
          </nav>
          <div className="header-action">
            <div className="search-wrapper" data-search-wrapper="">
              <div className="input-wrapper">
                <input
                  type="search"
                  name="search"
                  placeholder="Search here"
                  className="search-input"
                />
                <button className="search-submit" aria-label="Submit search">
                  <ion-icon name="search-outline" />
                </button>
              </div>
            </div>
            {!islogin ? (
              <button
                className="header-action-btn"
                aria-label="Open shopping cart"
                data-panel-btn="cart"
                onClick={handleRedirect}
              >
                <ion-icon name="person-circle-outline"></ion-icon>
              </button>
            ) : (
              <>
                <button
                  className="header-action-btn"
                  aria-label="Open shopping cart"
                  data-panel-btn="cart"
                  onClick={handleRedirect}
                >
                  <ion-icon name="basket-outline" />
                  <data className="btn-badge" value={'•'}>
                    ⦿             
                  </data>
                </button>
              </>
              
            )}
           {islogin ? <button
                  className="header-action-btn"
                  aria-label="Open shopping cart"
                  data-panel-btn="cart"
                  onClick={() => handleLogout()}
                >
                 <ion-icon name="log-out-outline"></ion-icon>
                </button>:<></>}
          </div>
        </div>
      </div>
    </header>
  );
};
