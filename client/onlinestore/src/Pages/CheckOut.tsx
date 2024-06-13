import React, { useEffect, useState } from "react";

interface PriceFormatOptions {
  amount: number;
  currency: string;
  quantity: number;
}

const formatPrice = ({ amount, currency, quantity }: PriceFormatOptions): string => {
  const numberFormat = new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency,
    currencyDisplay: 'symbol',
  });

  const parts = numberFormat.formatToParts(amount);
  const hasDecimals = parts.some((part) => part.type === 'decimal');

  const formattedAmount = hasDecimals ? amount : amount / 100;
  const total = (quantity * formattedAmount).toFixed(2);

  return numberFormat.format(Number(total));
};

export const CheckOut = ({ location }) => {
  const [quantity, setQuantity] = useState(1);
  const [amount, setAmount] = useState(0);
  const [currency, setCurrency] = useState('USD');

  useEffect(() => {
    async function fetchConfig() {
      const {
        unitAmount,
        currency
      } = await fetch('http://localhost:9090/payment/config').then(r => r.json());
      setAmount(unitAmount);
      setCurrency(currency);
    }
    fetchConfig();
  }, []);

  return (
    <div className="sr-root">
      <div className="sr-main">
        <section className="container">
          <div>
            <h1>Single photo</h1>
            <h4>Purchase a Pasha original photo</h4>
            <div className="pasha-image">
              <img
                alt="Random asset from Picsum"
                src="https://picsum.photos/280/320?random=4"
                width="140"
                height="160"
              />
            </div>
          </div>
          <form action="/create-checkout-session" method="POST">
            <div className="quantity-setter">
              <button
                className="increment-btn"
                disabled={quantity === 1}
                onClick={() => setQuantity(quantity - 1)}
                type="button"
              >
                -
              </button>
              <input
                type="number"
                id="quantity-input"
                min="1"
                max="10"
                value={quantity}
                name="quantity"
                readOnly
              />
              <button
                className="increment-btn"
                disabled={quantity === 10}
                onClick={() => setQuantity(quantity + 1)}
                type="button"
              >
                +
              </button>
            </div>
            <p className="sr-legal-text">Number of copies (max 10)</p>

            <button role="link" id="submit" type="submit">
              Buy {formatPrice({ amount, currency, quantity })}
            </button>
          </form>
        </section>
      </div>
    </div>
  )
}
