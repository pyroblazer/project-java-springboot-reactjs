import React from 'react';
import { Items } from './Items';

interface CartTableProps {
    items: any[];
    setLoading: React.Dispatch<React.SetStateAction<number>>;
}

export const CartTable: React.FC<CartTableProps> = ({ items, setLoading }) => (
    <div className="table-responsive">
        <table className="table">
            <thead>
                <tr>
                    <th scope="col" className="border-0 bg-light">
                        <div className="p-2 px-3 text-uppercase">Product</div>
                    </th>
                    <th scope="col" className="border-0 bg-light">
                        <div className="py-2 text-uppercase">Price</div>
                    </th>
                    <th scope="col" className="border-0 bg-light">
                        <div className="py-2 text-uppercase">Quantity</div>
                    </th>
                    <th scope="col" className="border-0 bg-light">
                        <div className="py-2 text-uppercase">Remove</div>
                    </th>
                </tr>
            </thead>
            <tbody>
                {items.map((elem, index) => (
                    <Items key={index} prop={elem} setLoading={setLoading} />
                ))}
            </tbody>
        </table>
    </div>
);