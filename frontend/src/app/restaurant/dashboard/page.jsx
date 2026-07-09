'use client';

import { useOwnerGuard } from '@/hooks/useOwnerGuard';
import { useRestaurantOrders } from '@/hooks/useRestaurantOrders';
import MenuManagement from '@/app/components/restaurant/MenuManagement';
import { useState } from 'react';

export default function RestaurantDashboard() {
  const { user, loading: authLoading } = useOwnerGuard();
  const [activeTab, setActiveTab] = useState('orders');

  const {
    orders = [],
    loading: ordersLoading,
    error,
    updateOrderStatus,
    refetch,
  } = useRestaurantOrders(user);

  const resolvedRestaurantId = user?.restaurantId;

  const getStatusStyle = (status) => {
    switch (status) {
      case 'PENDING':
        return 'bg-yellow-100 text-yellow-800 border-yellow-200';
      case 'PREPARING':
        return 'bg-blue-100 text-blue-800 border-blue-200';
      case 'ON_THE_WAY':
        return 'bg-indigo-100 text-indigo-800 border-indigo-200';
      case 'DELIVERED':
        return 'bg-green-100 text-green-800 border-green-200';
      case 'CANCELED':
        return 'bg-red-100 text-red-800 border-red-200';
      default:
        return 'bg-gray-100 text-gray-800 border-gray-200';
    }
  };

  if (authLoading || (activeTab === 'orders' && ordersLoading))
    return (
      <div className="text-center py-20 font-semibold text-gray-500 animate-pulse">
        Panel yükleniyor... ⏳
      </div>
    );

  if (error)
    return (
      <div className="text-center py-20 font-semibold text-red-500">
        {error}
      </div>
    );

  return (
    <div className="max-w-6xl mx-auto px-4 py-10">
      {/* Üst Başlık Bölümü */}
      <div className="flex justify-between items-center mb-6 border-b pb-4">
        <div>
          <h1 className="text-3xl font-extrabold text-gray-900">
            Restoran Yönetim Paneli
          </h1>
          <p className="text-sm text-gray-500 mt-1">
            Gelen siparişleri yönetin ve menü içeriğinizi düzenleyin.
          </p>
        </div>
        {activeTab === 'orders' && (
          <button
            onClick={refetch}
            className="bg-gray-100 hover:bg-gray-200 text-gray-700 px-4 py-2 rounded-xl text-sm font-semibold transition-colors"
          >
            🔄 Yenile
          </button>
        )}
      </div>

      {/* SEKMELER BARI */}
      <div className="flex gap-4 mb-8 border-b border-gray-200 pb-px">
        <button
          onClick={() => setActiveTab('orders')}
          className={`pb-3 text-base font-bold transition-all ${activeTab === 'orders' ? 'text-red-600 border-b-2 border-red-600' : 'text-gray-400 hover:text-gray-600'}`}
        >
          🛍️ Sipariş Takibi ({orders.length})
        </button>
        <button
          onClick={() => setActiveTab('menu')}
          className={`pb-3 text-base font-bold transition-all ${activeTab === 'menu' ? 'text-red-600 border-b-2 border-red-600' : 'text-gray-400 hover:text-gray-600'}`}
        >
          🍔 Menü Yönetimi
        </button>
      </div>

      {/* 1. SEKME: SİPARİŞLER */}
      {activeTab === 'orders' &&
        (orders.length === 0 ? (
          <div className="text-center py-16 bg-white rounded-2xl border border-dashed border-gray-200">
            <p className="text-gray-500 font-medium">
              Henüz gelen bir sipariş bulunmuyor.
            </p>
          </div>
        ) : (
          <div className="grid gap-6">
            {orders.map((order) => (
              <div
                key={order.orderId}
                className="bg-white p-6 rounded-2xl border border-gray-100 shadow-sm flex flex-col md:flex-row justify-between gap-6"
              >
                <div className="flex-1">
                  <div className="flex items-center gap-3 mb-2">
                    <span className="font-bold text-gray-800 text-lg">
                      Sipariş #{order.orderId}
                    </span>
                    <span
                      className={`px-3 py-0.5 rounded-full text-xs font-bold border ${getStatusStyle(order.status)}`}
                    >
                      {order.status}
                    </span>
                  </div>
                  <p className="text-xs text-gray-400 mb-4">
                    {new Date(order.orderDate).toLocaleString('tr-TR')}
                  </p>
                  <div className="bg-gray-50 p-4 rounded-xl space-y-2">
                    {order.items?.map((item, index) => (
                      <div
                        key={index}
                        className="flex justify-between text-sm text-gray-700"
                      >
                        <span>
                          <strong className="text-red-600">
                            {item.quantity}x
                          </strong>{' '}
                          {item.productName}
                        </span>
                        <span className="font-medium text-gray-900">
                          {item.priceAtOrder * item.quantity} ₺
                        </span>
                      </div>
                    ))}
                    <div className="border-t pt-2 mt-2 flex justify-between font-bold text-gray-900">
                      <span>Toplam Tutar:</span>
                      <span className="text-red-600 text-base">
                        {order.totalAmount} ₺
                      </span>
                    </div>
                  </div>
                </div>
                <div className="flex flex-col justify-center gap-2 md:w-52 border-t md:border-t-0 pt-4 md:pt-0 border-gray-100">
                  <p className="text-xs font-bold text-gray-400 uppercase tracking-wider mb-1">
                    Durumu Güncelle
                  </p>
                  {order.status === 'PENDING' && (
                    <button
                      onClick={() =>
                        updateOrderStatus(order.orderId, 'PREPARING')
                      }
                      className="w-full bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-xl text-sm transition-colors shadow-sm"
                    >
                      🍳 Hazırlamaya Başla
                    </button>
                  )}
                  {order.status === 'PREPARING' && (
                    <button
                      onClick={() =>
                        updateOrderStatus(order.orderId, 'ON_THE_WAY')
                      }
                      className="w-full bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-2 px-4 rounded-xl text-sm transition-colors shadow-sm"
                    >
                      🛵 Kuryeye Teslim Et
                    </button>
                  )}
                  {order.status === 'ON_THE_WAY' && (
                    <button
                      onClick={() =>
                        updateOrderStatus(order.orderId, 'DELIVERED')
                      }
                      className="w-full bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-4 rounded-xl text-sm transition-colors shadow-sm"
                    >
                      ✅ Teslim Edildi
                    </button>
                  )}
                  {['PENDING', 'PREPARING'].includes(order.status) && (
                    <button
                      onClick={() =>
                        updateOrderStatus(order.orderId, 'CANCELED')
                      }
                      className="w-full bg-white hover:bg-red-50 text-red-600 border border-red-200 font-semibold py-2 px-4 rounded-xl text-sm transition-colors"
                    >
                      ❌ Siparişi İptal Et
                    </button>
                  )}
                  {['DELIVERED', 'CANCELED'].includes(order.status) && (
                    <span className="text-center text-xs text-gray-400 font-medium py-2 bg-gray-50 rounded-xl border border-gray-100">
                      İşlem Tamamlandı
                    </span>
                  )}
                </div>
              </div>
            ))}
          </div>
        ))}

      {/* 2. SEKME: MENÜ YÖNETİMİ */}
      {activeTab === 'menu' &&
        (resolvedRestaurantId ? (
          <MenuManagement restaurantId={resolvedRestaurantId} />
        ) : (
          <div className="text-center py-16 bg-white rounded-2xl border border-dashed border-gray-200">
            <p className="text-gray-500 font-medium animate-pulse">
              Restoran bilgisi doğrulanıyor... ⏳
            </p>
          </div>
        ))}
    </div>
  );
}
