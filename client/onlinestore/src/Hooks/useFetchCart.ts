import { useState, useEffect } from 'react';
import { User } from '../shared.types';

interface CartDetails {
    // Define the structure of the cart details according to your data
}

interface CartData {
    totalAmount: number;
    cartDetails: CartDetails[];
    user: User;
}

export const useFetchCart = (token: string | null) => {
    const [data, setData] = useState<CartData | null>(null);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        const fetchCart = async () => {
            const res = await fetch('http://localhost:9090/cart', {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`,
                },
            });
            const data = await res.json();
            setData(data);
            setLoading(false);
        };

        if (token) {
            fetchCart();
        }
    }, [token]);

    return { data, loading };
};
