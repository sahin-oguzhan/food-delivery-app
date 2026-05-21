'use client';

import { useState } from 'react';
import { useAuth } from '@/context/AuthContext';
import Link from 'next/link';

export default function LoginPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [submitting, setSubmitting] = useState(false);

  const { login } = useAuth();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSubmitting(true);

    const result = await login(email, password);

    if (!result.success) {
      setError(result.message);
      setSubmitting(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50 px-4">
      <div className="max-w-md w-full space-y-8 bg-white p-8 rounded-2xl shadow-sm border border-gray-100">
        {/*Logo ve Başlık */}
        <div className="text-center">
          <h1 className="text-4xl font-extrabold text-red-600 tracking-tight mb-2">
            Food Delivery
          </h1>
          <h2 className="text-2xl font-bold text-gray-900">Giriş yap</h2>
          <p className="mt-2 text-sm text-gray-600">
            veya{' '}
            <Link
              href="/register"
              className="font-medium text-red-600 hover:text-red-500 transition-colors"
            >
              hemen yeni hesap oluşturun
            </Link>
          </p>
        </div>

        {/*Form */}
        <form onSubmit={handleSubmit} className="mt-8 space-y-6">
          {error && (
            <div className="bg-red-50 text-red-600 p-3 rounded-xl text-sm font-medium border border-red-100 animate-pulse">
              {error}
            </div>
          )}

          <div className="space-y-4">
            {/*Eposta */}
            <div className="text-sm font-medium text-gray-700 block mb-1">
              <label>E-posta Adresi</label>
              <input
                type="email"
                required
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="apperance-none rounded-xl relative block w-full px-4 py-3 border border-gray-300 placeholder-gray-400 text-gray-900 focus:outline-none focus:ring-2 focus:ring-red-500 focus:border-red-500 sm:text-sm transition-all"
                placeholder="eposta@eposta.com"
              />
            </div>
            {/*Şifre */}
            <div>
              <label className="text-sm font-medium text-gray-700 block mb-1">
                Şifre
              </label>
              <input
                type="password"
                required
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="appearance-none rounded-xl relative block w-full px-4 py-3 border border-gray-300 placeholder-gray-400 text-gray-900 focus:outline-none focus:ring-2 focus:ring-red-500 focus:border-red-500 sm:text-sm transition-all"
                placeholder="••••••••"
              />
            </div>
          </div>

          {/* Beni Hatırla & Şifremi Unuttum (TODO) */}
          <div className="flex items-center justify-between text-sm">
            <div className="flex items-center">
              <input
                id="remember-me"
                type="checkbox"
                className="h-4 w-4 text-red-600 focus:ring-red-500 border-gray-300 rounded"
              />
              <label htmlFor="remember-me" className="ml-2 block text-gray-900">
                Beni hatırla
              </label>
            </div>
            <a href="#" className="font-medium text-red-600 hover:text-red-500">
              Şifremi unuttum
            </a>
          </div>

          {/*Giriş Butonu */}
          <div>
            <button
              type="submit"
              disabled={submitting}
              className="group relative w-full flex justify-center py-3 px-4 border border-transparent text-sm font-semibold rounded-xl text-white bg-red-600 hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 transition-colors disabled:bg-gray-400 cursor-pointer"
            >
              {submitting ? 'Giriş Yapılıyor...' : 'Giriş Yap'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
