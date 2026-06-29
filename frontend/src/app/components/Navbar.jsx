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

  const cartItems = cart?.items || [];
  const cartItemCount = cartItems.reduce(
    (total, item) => total + (item?.quantity || 0),
    0,
  );
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
            <div className="flex items-center gap-4">
              {/* SEPET BUTONU*/}
              {(!user || user.role !== 'OWNER') && (
                <button
                  onClick={() => setIsCartOpen(true)}
                  className="relative p-2 text-gray-600 hover:text-red-600 transition-colors flex items-center gap-1 bg-gray-50 rounded-xl px-3 py-2"
                >
                  <span>🛒</span>
                  <span className="text-sm font-semibold hidden md:inline">
                    Sepetim
                  </span>
                  {cartItemCount > 0 && (
                    <span className="absolute -top-1 -right-1 bg-red-600 text-white text-xs font-bold rounded-full h-5 w-5 flex items-center justify-center animate-pulse">
                      {cartItemCount}
                    </span>
                  )}
                </button>
              )}

              {user ? (
                <>
                  {user.role === 'ROLE_OWNER' && (
                    <Link
                      href="/restaurant/dashboard"
                      className="bg-indigo-600 hover:bg-indigo-700 text-white font-semibold py-2 px-4 rounded-xl text-sm transition-colors shadow-sm flex items-center gap-2"
                    >
                      📊 Restoran Paneli
                    </Link>
                  )}
                  {user.role === 'ROLE_CUSTOMER' && (
                    <Link
                      href="/orders"
                      className="bg-gray-100 hover:bg-gray-200 text-gray-800 font-semibold py-2 px-4 rounded-xl text-sm transition-colors flex items-center gap-2"
                    >
                      🛍️ Siparişlerim
                    </Link>
                  )}

                  {/* Profil Bilgisi ve Çıkış Butonu */}
                  <div className="h-4 w-px bg-gray-300" />
                  <span className="text-sm font-medium text-gray-600 hidden md:inline">
                    Merhaba👋 {user.name}
                  </span>
                  <button
                    onClick={logout}
                    className="text-sm font-semibold text-red-600 hover:text-red-700 transition-colors"
                  >
                    Çıkış Yap
                  </button>
                </>
              ) : (
                <>
                  <Link
                    href="/login"
                    className="text-sm font-semibold text-gray-700 hover:text-gray-900 transition-colors"
                  >
                    Giriş Yap
                  </Link>
                  <Link
                    href="/register"
                    className="bg-red-600 hover:bg-red-700 text-white font-semibold py-2 px-4 rounded-xl text-sm transition-colors shadow-sm"
                  >
                    Kaydol
                  </Link>
                </>
              )}
            </div>
          </div>
        </div>
      </nav>

      <CartSidebar isOpen={isCartOpen} onClose={() => setIsCartOpen(false)} />
    </>
  );
}
