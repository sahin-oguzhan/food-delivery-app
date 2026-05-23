'use client';

import { useCart } from '@/context/CartContext';

export default function CartSidebar({ isOpen, onClose }) {
  const { cart, removeFromCart, updateQuantity, cartTotal } = useCart();

  if (!isOpen) return null;

  const cartItems = cart?.items || [];
  const totalPrice = cart?.totalPrice || 0;

  return (
    <>
      {/*Overlay */}
      <div className="fixed inset-0 bg-black/50 z-40 transition-opacity"></div>

      {/*Sidebar */}
      <div className="fixed inset-y-0 right-0 w-full max-w-md bg-white shadow-2xl z-50 transform transition-transform duration-300 flex flex-col">
        {/*Başlık ve kapatma */}
        <div className="flex items-center justify-between p-5 border-b border-gray-100">
          <h2 className="text-xl font-bold text-gray-900">Sepetim</h2>
          <button
            onClick={onClose}
            className="p-2 text-gray-400 hover:text-gray-600 hover:bg-gray-100 rounded-full transition-colors cursor-pointer"
          >
            ✕
          </button>
        </div>

        {/*Sepet içeriği */}
        <div className="flex-1 overflow-y-auto p-5 space-y-4">
          {cartItems.length === 0 ? (
            <div className="flex flex-col items-center justify-center h-full text-gray-500 space-y-4">
              <span className="text-4xl">🛒</span>
              <p>Sepetiniz şu an boş.</p>
            </div>
          ) : (
            cartItems.map((item) => (
              <div
                key={item.id}
                className="flex items-center gap-4 bg-white p-3 rounded-xl border border-gray-100 shadow-sm"
              >
                {/* Ürün Bilgileri */}
                <div className="flex-1">
                  <h3 className="font-semibold text-gray-900">
                    {item.productName}
                  </h3>
                  <p className="text-red-600 font-bold mt-1">{item.price} ₺</p>
                </div>

                {/*Adet güncelleme ve silme */}
                <div className="flex items-center gap-3">
                  <div className="flex items-center border border-gray-200 rounded-lg">
                    <button
                      onClick={() => updateQuantity(item.productId, -1)}
                      className="px-3 py-1 text-gray-600 hover:bg-gray-50 transition-colors"
                    >
                      -
                    </button>
                    <span className="px-2 font-medium text-gray-900">
                      {item.quantity}
                    </span>
                    <button
                      onClick={() => updateQuantity(item.productId, 1)}
                      className="px-3 py-1 text-gray-600 hover:bg-gray-50 transition-colors"
                    >
                      +
                    </button>
                  </div>
                  <button
                    onClick={() => removeFromCart(item.id)}
                    className="p-2 text-red-500 hover:bg-red-50 rounded-lg transition-colors"
                  >
                    🗑️
                  </button>
                </div>
              </div>
            ))
          )}
        </div>
        {/*Toplam tutar ve Sipariş */}
        {cart.length > 0 && (
          <div className="border-t border-gray-100 p-5 bg-gray-50">
            <div className="flex justify-between items-center mb-4">
              <span className="text-gray-600 font-medium">Ara Toplam</span>
              <span className="text-2xl font-bold text-gray-900">
                {cartTotal.toFixed(2)} ₺
              </span>
            </div>
            <button className="w-full bg-red-600 hover:bg-red-700 text-white font-bold py-4 rounded-xl transition-colors cursor-pointer">
              Siparişi Tamamla
            </button>
          </div>
        )}
      </div>
    </>
  );
}
