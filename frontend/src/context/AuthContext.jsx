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

  useEffect(() => {
    const isTokenValid = checkTokenExpiration();

    if (isTokenValid) {
      const token = localStorage.getItem('token');
      const storedUser = localStorage.getItem('user');
      if (token && storedUser) {
        setUser(JSON.parse(storedUser));
        api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      }
    }

    setLoading(false);
  }, []);

  const login = async (email, password) => {
    try {
      const response = await api.post('/auth/login', { email, password });
      const { token } = response.data;

      const decoded = jwtDecode(token);

      const userData = {
        email: decoded.sub,
        role: decoded.role?.[0],
      };

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

  const checkTokenExpiration = () => {
    const token = localStorage.getItem('token');
    if (!token) return false;

    try {
      const decoded = jwtDecode(token);
      const currentTime = Date.now() / 1000;

      if (decoded.exp < currentTime) {
        console.warn('Oturum süresi dolduğu için otomatik çıkış yapıldı.');
        logout();
        return false;
      }
      return true;
    } catch (error) {
      logout();
      return false;
    }
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
