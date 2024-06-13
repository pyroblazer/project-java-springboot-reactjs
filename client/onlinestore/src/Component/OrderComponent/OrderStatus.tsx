import React from "react";

const OrderStatus = {
  PROCESSING: 1,
  SHIPPED: 2,
  DELIVERED: 3,
  CANCELED: 4,
};

const orderStatusLabelMap = {
  [OrderStatus.PROCESSING]: "Processing",
  [OrderStatus.SHIPPED]: "Shipped",
  [OrderStatus.DELIVERED]: "Delivered",
  [OrderStatus.CANCELED]: "Canceled",
};

export const OrderStatusDisplay = ({ status }) => {
  return <span>{orderStatusLabelMap[status] || "Unknown"}</span>;
};

export default OrderStatusDisplay;
