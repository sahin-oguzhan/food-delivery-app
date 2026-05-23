'use client';

import { createContext, useContext, useState, useEffect } from 'react';
import { useAuth } from './AuthContext';
import { api } from '@/app/lib/api';

const CartContext = createContext();

export function CartProvider({ children }) {
  const [cart, setCart] = useState([]);
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
          'Sepet bilgisine ulaşmaya çalışırken hata oluştu: ' + error.message,
        );
      } finally {
        setLoading(false);
      }
    };

    fetchCart();
  }, []);

  const addToCart = async (product) => {
    const existingItem = cart.find((item) => item.product.id === product.id);

    if (existingItem) {
      setCart(
        cart.map((item) => item.product.id === product.id)
          ? { ...item, quantity: item.quantity + 1 }
          : item,
      );
    } else {
      setCart([...cart, { product, quantity: 1 }]);
    }

    try {
      await api.post('/cart/add', {
        productId: product.id,
        quantity: 1,
      });
    } catch (error) {
      console.error('Ürün sepete eklenirken hata oluştu: ' + error.message);
    }
  };

  const removeFromCart = async (productId) => {
    setCart(cart.filter((item) => item.product.id !== productId));

    try {
      await api.delete('/cart/item/${productId}');
    } catch (error) {
      console.error(
        'Ürün sepetten silinirken bir hata oluştu: ' + error.message,
      );
    }
  };

  const updateQuantity = async (productId, amount) => {
    let targetQuantity = 1;

    setCart(
      cart.map((item) => {
        if (item.product.id === productId) {
          targetQuantity = Math.max(1, item.quantity + amount);
          return { ...item, quantity: targetQuantity };
        }
        return item;
      }),
    );

    try {
      await api.put('/cart/update', {
        productId,
        quantity: targetQuantity,
      });
    } catch (error) {
      console.error('Ürün sepete eklenirken bir hata oluştu: ', error.message);
    }
  };

  const clearCart = async () => {
    setCart([]);
    try {
      await api.delete('/cart/clear');
    } catch (error) {
      console.error('Sepet temizlenirken bir hata oluştu: ' + error.message);
    }
  };

  const cartTotal = cart.reduce(
    (total, item) => total + (item.product?.price || 0) * item.quantity,
    0,
  );

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
