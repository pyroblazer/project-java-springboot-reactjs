import React, { useEffect, useState } from "react";
import { ProductCard } from "../ShopComponent/ProductCard";
import axiosFetch from "../../Helper/Axios";

export const ListProduct = () => {
  const[token,setToken]=useState(sessionStorage.getItem("token"));
  
  const[data,setData]=useState([]);
  const fatchData = async () => {
  
    const response = await axiosFetch({
      "url":"product/",
      "method":"GET",
    });
    
    // const
    console.log(response.data);
    setData(response.data);
  };


  
  useEffect(() => {
    fatchData();
  }, []);

  
  return (
    <>
      <section id="products" className="section product">
        <div className="container">
          <p className="section-subtitle"> -- Products --</p>
          <h2 className="h2 section-title">All Products</h2>
          <ul className="grid-list">
            {data.map((item) => 
               <ProductCard key={item.productid} id={item.productid} name={item.productName} description={item.description} price={item.price} img={item.img} />
            )}
          </ul>
        </div>
      </section>
    </>
  );
};
