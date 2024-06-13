import React from "react";

export const OrderDetailTable = ({ orderDetails }) => {
  return (
    <table className="table table-sm table-bordered">
      <thead>
        <tr>
          <th scope="col">Product</th>
          <th scope="col">Description</th>
          <th scope="col">Quantity</th>
          <th scope="col">Unit Price</th>
          <th scope="col">Total Price</th>
        </tr>
      </thead>
      <tbody>
        {orderDetails.map((detail, index) => (
          <tr key={index}>
            <td>
              <img
                src={`data:image/png;base64,${detail.product.img}`}
                alt=""
                width={70}
              // className="img-fluid rounded shadow-sm d-inline"
              />
              {detail.product.name}
            </td>
            <td>{detail.product.description}</td>
            <td>{detail.quantity}</td>
            <td>${detail.product.price}</td>
            <td>${detail.product.price * detail.quantity}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};
