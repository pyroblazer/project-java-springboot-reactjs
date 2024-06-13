import React from 'react'

export const Footer = () => {
  return (
    <>
      <footer id='contact' className="footer">
        <div className="footer-top">
          <div className="container">
            <div className="footer-brand">
              <a href="/" className="logo">
                Online<span className="span">Store</span>
              </a>
              <p className="footer-text">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                Donec massa lorem, pretium vel massa vel, porta scelerisque eros.
                Pellentesque mi massa, varius et posuere vitae, placerat mollis ligula.
              </p>
              {/* <ul className="social-list">
            <li>
              <a href="#" className="social-link">
                <ion-icon name="logo-facebook" />
              </a>
            </li>
            <li>
              <a href="#" className="social-link">
                <ion-icon name="logo-twitter" />
              </a>
            </li>
            <li>
              <a href="#" className="social-link">
                <ion-icon name="logo-skype" />
              </a>
            </li>
            <li>
              <a href="#" className="social-link">
                <ion-icon name="logo-linkedin" />
              </a>
            </li>
          </ul> */}
            </div>
            <ul className="footer-list">
              <li>
                <p className="footer-list-title">Company</p>
              </li>
              <li>
                <a href="/shop" className="footer-link">
                  Shop
                </a>
              </li>
            </ul>
            <ul className="footer-list">
              <li>
                <p className="footer-list-title">Contact</p>
              </li>
              <li className="footer-item">
                <div className="contact-icon">
                  {/* @ts-ignore */}
                  <ion-icon name="location-outline" />
                </div>
                <address className="contact-link">
                  1 Lorem Ipsum Dolor, ID 99999
                </address>
              </li>
              <li className="footer-item">
                <div className="contact-icon">
                  {/* @ts-ignore */}
                  <ion-icon name="call-outline" />
                </div>
                <a href="tel:+081234567890" className="contact-link">
                  +081234567890
                </a>
              </li>
              <li className="footer-item">
                <div className="contact-icon">
                  {/* @ts-ignore */}
                  <ion-icon name="mail-outline" />
                </div>
                <a href="mailto:onlinestore@gmail.com" className="contact-link">
                  onlinestore@gmail.com
                </a>
              </li>
            </ul>
            <div className="footer-list">
              <p className="footer-list-title">Newsletter</p>
              <p className="newsletter-text">
                You'll be the first to know when new updates are available.
              </p>
              <form action="" className="footer-form">
                <input
                  type="email"
                  name="email"
                  placeholder="Email Address *"
                  required
                  className="footer-input"
                />
                <button
                  type="submit"
                  className="footer-form-btn"
                  aria-label="Submit"
                >
                  {/* @ts-ignore */}
                  <ion-icon name="mail-outline" />
                </button>
              </form>
            </div>
          </div>
        </div>
        <div className="footer-bottom">
          <div className="container">
            <p className="copyright">
              © 2024{" "}
              <a href="#" className="copyright-link">
                Ignatius Timothy Manullang
              </a>
              . All Rights Reserved.
            </p>
            <ul className="footer-bottom-list">
              <li>
                <a href="#" className="footer-bottom-link">
                  Terms and Service
                </a>
              </li>
              <li>
                <a href="#" className="footer-bottom-link">
                  Privacy Policy
                </a>
              </li>
            </ul>
          </div>
        </div>
      </footer>
    </>
  )
}
