'use client';

import { useCart } from '@/context/CartContext';
import { useEffect, useState } from 'react';
import { loadStripe } from '@stripe/stripe-js';
import { api } from '../lib/api';
import { Elements } from '@stripe/react-stripe-js';
import CheckoutForm from '../components/CheckoutForm';

const stripePromise = loadStripe(
  process.env.NEXT_PUBLIC_STRIPE_PUBLISHABLE_KEY,
  console.log(
    'Yüklenen Stripe Key:',
    process.env.NEXT_PUBLIC_STRIPE_PUBLISHABLE_KEY,
  ),
);

export default function CheckoutPage() {
  const { cart } = useCart();
  const [clientSecret, setClientSecret] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!cart || !cart.totalPrice || cart.totalPrice <= 0) {
      setLoading(false);
      return;
    }

    const fetchPaymentIntent = async () => {
      try {
        const response = await api.post('payments', {
          amount: cart.totalPrice,
          cartId: cart.id,
        });
        console.log('Backend Ham Cevap:', response.data);
        console.log('Gelen Secret Anahtarı:', response.data.clientSecret);
        setClientSecret(response.data.clientSecret);
      } catch (error) {
        console.error('Payment intent başlatılamadı: ', error);
      } finally {
        setLoading(false);
      }
    };
    fetchPaymentIntent();
  }, [cart]);

  if (loading) {
    return (
      <div className="text-center py-20 font-semibold text-gray-500">
        Lütfen bekleyiniz...
      </div>
    );
  }

  if (!cart || cart.items?.length === 0) {
    return (
      <div className="text-center py-20 font-semibold text-red-500">
        Sepetiniz boş olduğu için ödeme yapılamaz.
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto px-4 py-12">
      <h2 className="text-3xl font-extrabold text-gray-900 mb-8">
        Ödeme Adımı
      </h2>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        {/* Sepet Özeti */}
        <div className="bg-gray-50 p-6 rounded-2xl border border-gray-100">
          <h3 className="text-lg font-bold text-gray-900 mb-4">
            Sipariş Özeti
          </h3>
          <div className="space-y-4 max-h-60 overflow-y-auto mb-4 pr-2">
            {cart.items?.map((item) => (
              <div
                key={item.id}
                className="flex justify-between text-sm text-gray-600"
              >
                <span>
                  {item.productName} x {item.quantity}
                </span>
                <span className="font-semibold text-gray-900">
                  {item.subTotal} ₺
                </span>
              </div>
            ))}
          </div>
          <div className="border-t border-gray-200 pt-4 flex justify-between font-bold text-lg text-gray-900">
            <span>Toplam Tutar:</span>
            <span className="text-red-600">{cart.totalPrice} ₺</span>
          </div>
        </div>

        {/*Stripe Kart Formu */}
        <div>
          {clientSecret ? (
            <Elements stripe={stripePromise} options={{ clientSecret }}>
              <CheckoutForm clientSecret={clientSecret} />
            </Elements>
          ) : (
            <div className="text-red-500 font-semibold">
              Ödeme oturumu açılırken bir sorun oluştu. Lütfen daha sonra tekrar
              deneyin.
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
