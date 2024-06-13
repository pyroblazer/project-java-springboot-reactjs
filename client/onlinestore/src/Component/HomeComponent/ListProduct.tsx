import React, { Dispatch, useEffect, useState } from "react";
import { ProductCard } from "../ShopComponent/ProductCard";
import axiosFetch from "../../Helper/Axios";
import { Product } from "../../shared.types";

export const ListProduct = () => {
  const [token, setToken] = useState(sessionStorage.getItem("token"));

  const [data, setData]: [Product[], Dispatch<Product[]>] = useState([] as Product[]);
  const fatchData = async () => {

    const response = await axiosFetch({
      "url": "product/",
      "method": "GET",
    });
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
            {data.map((item) => {
              console.log(item);
              return <ProductCard key={item.id} id={item.id} name={item.name} description={item.description} price={item.price} img={item.img} />
            }
            )}
          </ul>
        </div>
      </section>
    </>
  );
};
