import React from 'react'

export const Hero = () => {
  return (
    <>
      <section className="hero">
        <div className="container">
          <div className="hero-content">
            <p className="hero-subtitle">We have the best products</p>
            <h2 className="h1 hero-title">
              Quality <span className="span">products</span> &amp; <span className="span">services.</span>
            </h2>
            <p className="hero-text">
              Exceptional quality and value, trusted by customers for years.
            </p>
            <a href="/shop" className="btn btn-primary">
              <span className="span">Shop Now</span>
              {/* @ts-ignore */}
              <ion-icon name="chevron-forward" aria-hidden="true" />
            </a>
          </div>
          <figure className="hero-banner">
            <img
              src="./images/hero-banner.png"
              width={603}
              height={634}
              loading="lazy"
              alt="Products"
              className="w-100"
            />
          </figure>
        </div>
      </section>
    </>
  )
}
