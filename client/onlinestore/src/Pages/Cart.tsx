import React, { useState } from 'react';
import { Footer } from '../Component/Footer';
import { Header } from '../Component/Header';
import { CartTable } from '../Component/CartComponent/CartTable';
import { CardElement, useElements, useStripe } from '@stripe/react-stripe-js';
import { User } from '../shared.types';
import { useFetchCart } from '../Hooks/useFetchCart';

export const Cart: React.FC = () => {
  const [token] = useState<string | null>(sessionStorage.getItem('token'));
  const { data, loading } = useFetchCart(token);
  const stripe = useStripe();
  const elements = useElements();

  const CARD_OPTIONS = {
    iconStyle: 'solid',
    style: {
      base: {
        iconColor: '#c4f0ff',
        color: '#000',
        fontWeight: 500,
        fontFamily: 'Roboto, Open Sans, Segoe UI, sans-serif',
        fontSize: '16px',
        fontSmoothing: 'antialiased',
        ':-webkit-autofill': { color: '#fce883' },
        '::placeholder': { color: '#87bbfd' },
      },
      invalid: {
        iconColor: '#fa0000',
        color: '#ffc7ee',
      },
    },
  };

  const createPaymentIntent = async () => {
    if (!stripe || !elements) return;

    // @ts-ignore
    const { error, paymentMethod } = await stripe.createPaymentMethod({
      type: 'card',
      card: elements.getElement(CardElement),
    });

    if (error) return;

    const { id } = paymentMethod;
    const res = await fetch('http://localhost:9090/payment/payment-intent', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({
        amount: data ? data.totalAmount * 100 : 0,
        id,
        name: data?.user.name,
        email: data?.user.email,
      }),
    });
    const response = await res.json();
    return response;
  };

  const handlePayment = async () => {
    const paymentIntent = await createPaymentIntent();
    if (paymentIntent) {
      console.log(paymentIntent);
      console.log('Successful payment');
    }
  };

  return (
    <>
      <Header />
      <div className="shopping-cart">
        <div className="px-4 px-lg-0">
          <div className="pb-5">
            <div className="container">
              <div className="row">
                <div className="col-lg-12 p-5 bg-white rounded shadow-sm mb-5">
                  {loading ? (
                    <p>Loading...</p>
                  ) : (
                    <CartTable items={data?.cartDetails || []} setLoading={() => { }} />
                  )}
                </div>
              </div>
              <div className="row py-5 p-4 bg-white rounded shadow-sm">
                <div className="col-lg-6"></div>
                <div className="col-lg-6">
                  <div className="bg-light rounded-pill px-4 py-3 text-uppercase font-weight-bold">
                    Order summary
                  </div>
                  <div className="p-4">
                    <ul className="list-unstyled mb-4">
                      <li className="d-flex justify-content-between py-3 border-bottom">
                        <strong className="text-muted">Total</strong>
                        <h3 className="font-weight-bold">
                          ${data ? data.totalAmount.toFixed(2) : '0.00'}
                        </h3>
                      </li>
                    </ul>
                    <CardElement />
                    <button
                      className="btn btn-dark rounded-pill py-2 btn-block"
                      onClick={handlePayment}
                      disabled={data?.cartDetails.length === 0}
                    >
                      Proceed to checkout
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
};