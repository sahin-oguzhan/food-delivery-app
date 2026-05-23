'use client';

import { useCart } from '@/context/CartContext';

export default function ProductList({ products }) {
  const { addToCart } = useCart();

  if (!products || products.length === 0) {
    return (
      <div className="text-gray-500 mt-6">
        Bu restorana ait menü bulunamadı.
      </div>
    );
  }

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-6">
      {products.map((product) => (
        <div
          key={product.id}
          className="bg-white p-5 rounded-2xl border border-gray-100 shadow-sm flex justify-between items-center"
        >
          {/* Ürün Detayları */}
          <div>
            <h3 className="text-lg font-bold text-gray-900">{product.name}</h3>
            <p className="text-gray-500 text-sm mt-1">{product.description}</p>
            <p className="text-red-600 font-extrabold mt-2 text-lg">
              {product.price} ₺
            </p>
          </div>

          {/* Sepete Ekle */}
          <button
            onClick={() => addToCart(product)}
            className="bg-red-600 hover:bg-red-700 text-white font-semibold px-4 py-2 rounded-xl transition-colors cursor-pointer shadow-sm shadow-red-100"
          >
            Sepete Ekle
          </button>
        </div>
      ))}
    </div>
  );
}
