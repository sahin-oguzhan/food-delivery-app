'use client';

import { useCart } from '@/context/CartContext';
import { CardElement, useElements, useStripe } from '@stripe/react-stripe-js';
import { useRouter } from 'next/navigation';
import { useState } from 'react';

export default function CheckoutForm({ clientSecret }) {
  const stripe = useStripe();
  const elements = useElements();
  const { clearCart } = useCart();
  const router = useRouter();

  const [isProcessing, setIsProcessing] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!stripe || !elements || !clientSecret) {
      return;
    }

    setIsProcessing(true);
    setErrorMessage('');

    const { error, paymentIntent } = await stripe.confirmCardPayment(
      clientSecret,
      {
        payment_method: {
          card: elements.getElement(CardElement),
        },
      },
    );

    if (error) {
      setErrorMessage(error.message || 'Ödeme sırasında bir hata oluştu.');
      setIsProcessing(false);
    } else if (paymentIntent && paymentIntent.status === 'succeeded') {
      router.push('/orders/success');
    }
  };

  return (
    <form
      onSubmit={handleSubmit}
      className="space-y-6 bg-white p-6 rounded-2xl border border-gray-100 shadow-sm"
    >
      <h3 className="text-lg font-bold text-gray-900 mb-4">Kart Bilgileri</h3>

      <div className="p-4 border border-gray-200 rounded-xl bg-gray-50">
        <CardElement
          options={{
            style: {
              base: {
                fontSize: '16px',
                color: '#111827',
                '::placeholder': { color: '#9ca3af' },
              },
              invalid: { color: '#dc2626' },
            },
          }}
        />
      </div>

      {errorMessage && (
        <p className="text-red-600 text-sm font-semibold">{errorMessage}</p>
      )}

      <button
        disabled={!stripe || isProcessing}
        className="w-full bg-red-600 hover:bg-red-700 text-white font-bold py-3 px-4 rounded-xl transition-colors shadow-sm disabled:bg-gray-400 cursor-pointer text-center"
      >
        {isProcessing ? 'Ödeniyor... ⏳' : 'Ödemeyi Tamamla ve Sipariş Ver'}
      </button>
    </form>
  );
}
