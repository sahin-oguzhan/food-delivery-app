'use client';

import { useAuth } from '@/context/AuthContext';
import { useCustomerOrders } from '@/hooks/useCustomerOrders';
import Link from 'next/link';

export default function OrdersPage() {
  const { user } = useAuth();

  const { orders, loading, error } = useCustomerOrders(user);

  const getStatusBadge = (status) => {
    switch (status) {
      case 'PENDING':
        return (
          <span className="bg-yellow-100 text-yellow-800 px-3 py-1 rounded-full text-xs font-bold">
            Onay Bekliyor
          </span>
        );
      case 'PREPARING':
        return (
          <span className="bg-blue-100 text-blue-800 px-3 py-1 rounded-full text-xs font-bold">
            Hazırlanıyor 🍳
          </span>
        );
      case 'ON_THE_WAY':
        return (
          <span className="bg-indigo-100 text-indigo-800 px-3 py-1 rounded-full text-xs font-bold">
            Yolda 🛵
          </span>
        );
      case 'DELIVERED':
        return (
          <span className="bg-green-100 text-green-800 px-3 py-1 rounded-full text-xs font-bold">
            Teslim Edildi ✅
          </span>
        );
      case 'CANCELED':
        return (
          <span className="bg-red-100 text-red-800 px-3 py-1 rounded-full text-xs font-bold">
            İptal Edildi ❌
          </span>
        );
      default:
        return (
          <span className="bg-gray-100 text-gray-800 px-3 py-1 rounded-full text-xs font-bold">
            {status}
          </span>
        );
    }
  };

  if (loading) {
    return (
      <div className="text-center py-20 font-semibold text-gray-500 animet-pulse">
        Siparişler yükleniyor...
      </div>
    );
  }

  if (error) {
    return (
      <div className="text-center py-20 font-semibold text-red-500">
        {error}
      </div>
    );
  }

  if (orders.length === 0) {
    return (
      <div className="max-w-4xl mx-auto px-4 py-16 text-center">
        <div className="text-6xl mb-4">🛒</div>
        <h2 className="text-2xl font-bold text-gray-900 mb-2">
          Henüz hiç siparişiniz yok.
        </h2>
        <p className="text-gray-500 mb-8">
          Lezzetli yemeklerimizi keşfetmek için menümüze göz atın.
        </p>
        <Link
          href="/"
          className="bg-red-600 hover:bg-red-700 text-white font-bold py-3 px-6 rounded-xl transition-colors shadow-sm"
        >
          Menüye Dön
        </Link>
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto px-4 py-12">
      <h2 className="text-3xl font-extrabold text-gray-900 mb-8">
        Sipariş Geçmişim
      </h2>

      <div className="space-y-6">
        {orders.map((order) => (
          <div
            key={order.orderId}
            className="bg-white p-6 rounded-2xl border border-gray-100 shadow-sm flex flex-col md:flex-row justify-between items-start md:items-center gap-4 hover:shadow-md transition-shadow"
          >
            {/*Sipariş Detayları */}
            <div>
              <div className="flex flex-wrap items-center gap-3 mb-2">
                <span className="text-sm text-gray-500 font-semibold">
                  Sipariş #{order.orderId}
                </span>
                <span className="text-sm font-bold text-gray-800">
                  | {order.restaurantName}
                </span>
                {getStatusBadge(order.status)}
              </div>

              <p className="text-xs text-gray-400 mb-3">
                {new Date(order.orderDate).toLocaleString('tr-TR', {
                  day: 'numeric',
                  month: 'long',
                  year: 'numeric',
                  hour: '2-digit',
                  minute: '2-digit',
                })}
              </p>

              {/* Ürün Listesi */}
              <div className="text-sm text-gray-700 font-medium">
                {order.items?.map((item, index) => (
                  <span key={index}>
                    {item.quantity}x {item.productName}
                    {index !== order.items.length - 1 ? ', ' : ''}
                  </span>
                ))}
              </div>
            </div>

            {/*Toplam Tutar */}
            <div className="text-left md:text-right w-full md:w-auto mt-2 md:mt-0 border-t md:border-t-0 pt-4 md:pt-0 border-gray-100">
              <p className="text-xs text-gray-500 mb-1">Toplam Tutar</p>
              <p className="text-xl font-extrabold text-red-600">
                {order.totalAmount} ₺
              </p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
