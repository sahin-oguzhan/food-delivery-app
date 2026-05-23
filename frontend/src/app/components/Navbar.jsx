'use client';

import Link from 'next/link';
import { useAuth } from '@/context/AuthContext';
import { useCart } from '@/context/CartContext';
import { useState } from 'react';
import CartSidebar from './CartSidebar';

export default function Navbar() {
  const { user, logout } = useAuth();
  const { cart } = useCart();

  const [isCartOpen, setIsCartOpen] = useState(false);

  const cartItemCount = cart.reduce((total, item) => total + item.quantity, 0);
  return (
    <>
      <nav className="bg-white border-b border-gray-100 sticky top-0 z-30">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between h-16 items-center">
            {/* Logo */}
            <div className="shrink-0">
              <Link href="/" className="text-2xl font-extrabold text-red-600">
                food_delivery
              </Link>
            </div>

            {/* Sağ Taraf: Menüler ve Sepet */}
            <div className="flex items-center gap-6">
              {/* Sepet Butonu */}
              <button
                onClick={() => setIsCartOpen(true)}
                className="relative p-2 text-gray-600 hover:text-red-600 transition-colors cursor-pointer"
              >
                <span className="text-2xl">🛒</span>
                {/* Ürün Sayısı */}
                {cartItemCount > 0 && (
                  <span className="absolute top-0 right-0 inline-flex items-center justify-center px-2 py-1 text-xs font-bold leading-none text-white transform translate-x-1/4 -translate-y-1/4 bg-red-600 rounded-full">
                    {cartItemCount}
                  </span>
                )}
              </button>

              {/* Kullanıcı Menüsü */}
              {user ? (
                <div className="flex items-center gap-4">
                  <span className="text-sm font-medium text-gray-700">
                    Merhaba, {user.email.split('@')[0]}
                  </span>
                  <button
                    onClick={logout}
                    className="text-sm font-medium text-gray-500 hover:text-gray-700 transition-colors cursor-pointer"
                  >
                    Çıkış Yap
                  </button>
                </div>
              ) : (
                <Link
                  href="/login"
                  className="bg-red-600 text-white px-4 py-2 rounded-lg text-sm font-medium hover:bg-red-700 transition-colors"
                >
                  Giriş Yap
                </Link>
              )}
            </div>
          </div>
        </div>
      </nav>

      <CartSidebar isOpen={isCartOpen} onClose={() => setIsCartOpen(false)} />
    </>
  );
}
