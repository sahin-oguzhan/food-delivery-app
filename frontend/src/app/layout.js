import { Inter } from 'next/font/google';
import './globals.css';
import Navbar from './components/Navbar';
import { AuthProvider } from '@/context/AuthContext';
import { CartProvider } from '@/context/CartContext';
import { WebSocketProvider } from '@/context/WebSocketContext';

const inter = Inter({ subsets: ['latin'] });

export const metadata = {
  title: 'Food-Delivery | Acıktığında Kapında',
  description: 'En lezzetli yemekler tek tıkla kapında!',
};

export default function RootLayout({ children }) {
  return (
    <html lang="tr">
      <body className={`${inter.className} bg-gray-50 text-gray-900`}>
        <AuthProvider>
          <WebSocketProvider>
            <CartProvider>
              <Navbar />
              <main className="min-h-screen pt-4">{children}</main>
            </CartProvider>
          </WebSocketProvider>
        </AuthProvider>
      </body>
    </html>
  );
}
