'use client';

import { useCart } from '@/context/CartContext';
import { useState } from 'react';

export default function ProductList({ products, currentRestaurantId }) {
  const { addToCart, cart, clearCart } = useCart();

  const [showModal, setShowModal] = useState(false);
  const [pendingProduct, setPendingProduct] = useState(null);

  if (!products || products.length === 0) {
    return (
      <div className="text-gray-500 mt-6">
        Bu restorana ait menü bulunamadı.
      </div>
    );
  }

  const handleAddClick = (product) => {
    const cartItemsCount = cart?.items?.length || 0;

    const cartRestId = String(cart?.restaurantId || '');
    const pageRestId = String(currentRestaurantId || '');

    if (cartItemsCount > 0 && cartRestId !== '' && cartRestId !== pageRestId) {
      setPendingProduct(product);
      setShowModal(true);
      return;
    }

    addToCart(product);
  };

  const handleConfirmClear = async () => {
    if (pendingProduct) {
      await clearCart();
      await addToCart(pendingProduct);
    }
    setShowModal(false);
    setPendingProduct(null);
  };

  return (
    <div className="relative">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-6">
        {products.map((product) => (
          <div
            key={product.id}
            className="bg-white p-5 rounded-2xl border border-gray-100 shadow-sm flex justify-between items-center"
          >
            {/* Ürün Detayları */}
            <div>
              <h3 className="text-lg font-bold text-gray-900">
                {product.name}
              </h3>
              <p className="text-gray-500 text-sm mt-1">
                {product.description}
              </p>
              <p className="text-red-600 font-extrabold mt-2 text-lg">
                {product.price} ₺
              </p>
            </div>

            {/* Sepete Ekle */}
            <button
              onClick={() => handleAddClick(product)}
              className="bg-red-600 hover:bg-red-700 text-white font-semibold px-4 py-2 rounded-xl transition-colors cursor-pointer shadow-sm shadow-red-100"
            >
              Sepete Ekle
            </button>
          </div>
        ))}
      </div>

      {/*Sepet Dolu Modal'ı */}
      {showModal && (
        <div className="fixed inset-0 bg-black/50 z-50 flex items-center justify-center p-4">
          <div className="bg-white rounded-2xl max-w-md w-full p-6 shadow-2xl transform transition-all animate-in fade-in zoom-in duration-200">
            <div className="text-red-500 text-3xl mb-2">⚠️</div>
            <h3 className="text-xl font-bold text-gray-900">
              Farklı Restoran Seçimi
            </h3>
            <p className="text-gray-500 text-sm mt-2 leading-relaxed">
              Sepetinizde başka bir restorana ait ürünler bulunuyor. Aynı anda
              sadece tek bir restorandan sipariş verebilirsiniz.
              <br />
              <br />
              <strong className="text-gray-700">
                Mevcut sepetiniz temizlensin ve bu yeni ürün eklensin mi?
              </strong>
            </p>

            <div className="flex gap-3 mt-6 justify-end">
              <button
                onClick={() => setShowModal(false)}
                className="px-4 py-2.5 rounded-xl border border-gray-200 text-gray-700 font-semibold hover:bg-gray-50 transition-colors cursor-pointer text-sm"
              >
                Vazgeç
              </button>
              <button
                onClick={handleConfirmClear}
                className="px-4 py-2.5 rounded-xl bg-red-600 text-white font-semibold hover:bg-red-700 transition-colors cursor-pointer text-sm shadow-sm shadow-red-100"
              >
                Sepeti Temizle ve Ekle
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
