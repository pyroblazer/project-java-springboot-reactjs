export interface Order {
    id: number;
    user: User
    moment: string;
    orderStatus: string;
    totalAmount: number;
    orderDetails?: OrderDetail[];
}

export interface Product {
    id: number; 
    name: string;
    description: string;
    price: number; 
    weight: number; 
    img: Uint8Array; 
  }

export interface OrderDetail {
    orderDetailsId: number;
    product: Product; 
    quantity: number;
    amount: number;
}

export interface User {
    id: number;
    name: string;
    email: string;
    password: string; 
    role: string;
}

export interface SignIn {
    email: string;
    password: string;
    jwt?: string; 
}