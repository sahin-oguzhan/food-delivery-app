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
    <div>
      <div>
        {/*Logo ve Başlık */}
        <div>
          <h1>Food Delivery</h1>
          <h2>Giriş yap</h2>
          <p>
            veya <Link href="/register">hemen yeni hesap oluşturun</Link>
          </p>
        </div>

        {/*Form */}
        <form onSubmit={handleSubmit}>
          {error && <div>{error}</div>}

          <div>
            {/*Eposta */}
            <div>
              <label>E-posta Adresi</label>
              <input
                type="email"
                required
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </div>
            {/*Şifre */}
            <div>
              <label>Şifre</label>
              <input
                type="password"
                required
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="••••••••"
              />
            </div>
          </div>

          {/* Beni Hatırla & Şifremi Unuttum (TODO) */}
          <div>
            <div>
              <input id="remember-me" type="checkbox" />
              <label htmlFor="remember-me">Beni hatırla</label>
            </div>
            <a href="#">Şifremi unuttum</a>
          </div>

          {/*Giriş Butonu */}
          <div>
            <button type="submit" disabled={submitting}>
              {submitting ? 'Giriş Yapılıyor...' : 'Giriş Yap'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
