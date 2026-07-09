'use client';

import { createContext, useContext, useState, useEffect } from 'react';
import { api } from '@/app/lib/api';
import { useRouter } from 'next/navigation';
import { jwtDecode } from 'jwt-decode';

const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const router = useRouter();

  const fetchCurrentUser = async (token) => {
    try {
      api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      const response = await api.get(`/auth/me`);

      const backendUser = response.data;

      const userData = {
        id: backendUser.id,
        name: backendUser.name,
        email: backendUser.email,
        role: backendUser.roles?.[0],
        restaurantId: backendUser.restaurantId,
      };

      setUser(userData);
      localStorage.setItem('user', JSON.stringify(userData));
      setLoading(false);
    } catch (error) {
      console.error(
        'Kullanıcı profili senkronize edilemedi, oturum kapatılıyor:',
        error,
      );
      logout();
      setLoading(false);
    }
  };

  useEffect(() => {
    const token = localStorage.getItem('token');

    if (token) {
      try {
        const decoded = jwtDecode(token);
        const currentTime = Date.now() / 1000;

        if (decoded.exp < currentTime) {
          console.warn('Oturum süresi doldu.');
          logout();
          setLoading(false);
        } else {
          fetchCurrentUser(token);
        }
      } catch (error) {
        logout();
        setLoading(false);
      }
    } else {
      setLoading(false);
    }
  }, []);

  const login = async (email, password) => {
    try {
      const response = await api.post('/auth/login', { email, password });
      const { token } = response.data;

      localStorage.setItem('token', token);
      api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      await fetchCurrentUser(token);

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
    <AuthContext.Provider value={{ user, login, logout, loading }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}
