import React, { useEffect } from 'react'
import { Header } from '../Component/Header'
import { Hero } from '../Component/HomeComponent/Hero'
import { ListProduct } from '../Component/HomeComponent/ListProduct'
import { Footer } from '../Component/Footer'
import { ToastContainer} from 'react-toastify';

export const Home = () => {
  useEffect(() => { window.scrollTo(0, 0) }, []);
  
  return (
    <>
  <Header />
  <main>
    <article>
     <Hero/>
      <ListProduct/>  
    </article>
  </main>
<Footer/>
  <a
    href="#top"
    className="back-to-top"
    aria-label="Back to Top"
    data-back-top-btn=""
  >
  <i className="fa-solid fa-chevrons-up"/>
  </a>
      <ToastContainer />
</>
  )
}
