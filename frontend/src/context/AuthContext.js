'use client';

import { createContext, useContext, useState, useEffect } from 'react';
import { api } from '@/app/lib/api';
import { useRouter } from 'next/router';

const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(null);
  const router = useRouter();

  useEffect(() => {
    const token = localStorage.getItem('token');
    const storedUser = localStorage.getItem('user');

    if (token && storedUser) {
      setUser(JSON.parse(storedUser));
      api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    }
    setLoading(false);
  }, []);

  const login = async (email, password) => {
    try {
      const response = await api.post('/auth/login', { email, password });
      const { token, user: userData } = response.data;

      localStorage.setItem('token', token);
      localStorage.setItem('user', JSON.stringify(userData));

      api.defaults.headers.common['Authorization'] = `Bearer ${token}`;

      setUser(userData);
      router.push('/');
      return { success: true };
    } catch (error) {
      console.error(
        'Giriş hatası: ',
        error.response?.data?.message || error.message,
      );
      return {
        success: false,
        message:
          error.response?.data?.message || 'Giriş yapılırken bir hata oluştu',
      };
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    delete api.defaults.headers.common['Authorization'];
    setUser(null);
    router.push('/login');
  };

  return (
    <AuthContext.Provider
      value={{ user, login, logout, loading }}
    ></AuthContext.Provider>
  );
}
