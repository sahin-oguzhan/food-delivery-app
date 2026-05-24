'use client';

import { createContext, useContext, useState, useEffect } from 'react';
import { useAuth } from './AuthContext';
import { api } from '@/app/lib/api';

const CartContext = createContext();

export function CartProvider({ children }) {
  const [cart, setCart] = useState({ items: [], totalPrice: 0 });
  const [loading, setLoading] = useState(true);
  const { user } = useAuth();

  useEffect(() => {
    const fetchCart = async () => {
      if (!user) {
        setCart([]);
        setLoading(false);
        return;
      }

      try {
        const response = await api.get('/cart');
        setCart(response.data.items || []);
      } catch (error) {
        console.error(
          'Sepet bilgisine ulaşmaya çalışırken hata oluştu: ',
          error.message,
        );
      } finally {
        setLoading(false);
      }
    };

    fetchCart();
  }, []);

  const addToCart = async (product) => {
    const currentItems = cart?.items || [];
    const existingItem = currentItems.find(
      (item) => item.productId === product.id,
    );

    if (existingItem) {
      await updateQuantity(product.id, 1);
    } else {
      try {
        const response = await api.post('/cart/add', {
          productId: product.id,
          quantity: 1,
        });
        setCart(response.data);
      } catch (error) {
        console.error('Ürün sepete eklenirken hata oluştu: ', error.message);
      }
    }
  };

  const updateQuantity = async (productId, amount) => {
    const currentItems = cart?.items || [];
    const item = currentItems.find((item) => item.productId === productId);
    if (!item) return;

    if (item.quantity === 1 && amount === -1) {
      await removeFromCart(item.id);
      return;
    }

    try {
      const response = await api.put('/cart/update', {
        productId: productId,
        quantity: item.quantity + amount,
      });
      setCart(response.data);
    } catch (error) {
      console.error('Miktar güncellenirken hata oluştu:', error.message);
    }
  };

  const removeFromCart = async (productId) => {
    try {
      const response = await api.delete(`/cart/item/${productId}`);
      setCart(response.data);
    } catch (error) {
      console.error('Ürün silinrken hata oluştu:', error.message);
    }
  };

  const clearCart = async () => {
    try {
      const response = await api.delete('/cart/clear');
      setCart(response.data);
    } catch (error) {
      console.error('Sepet temizlenirken bir hata oluştu: ' + error.message);
    }
  };

  const cartTotal = cart?.totalPrice || 0;

  return (
    <CartContext.Provider
      value={{
        cart,
        loading,
        addToCart,
        removeFromCart,
        updateQuantity,
        clearCart,
        cartTotal,
      }}
    >
      {children}
    </CartContext.Provider>
  );
}

export function useCart() {
  return useContext(CartContext);
}
