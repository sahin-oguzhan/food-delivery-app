import { Inter } from 'next/font/google';
import './globals.css';
import Navbar from './components/Navbar';

const inter = Inter({ subsets: ['latin'] });

export const metadata = {
  title: 'Food-Delivery | Acıktığında Kapında',
  description: 'En lezzetli yemekler tek tıkla kapında!',
};

export default function RootLayout({ children }) {
  return (
    <html lang="tr">
      <body className={`${inter.className} bg-gray-50 text-gray-90`}>
        <Navbar />
        <main className="min-h-screen pt-4">{children}</main>
      </body>
    </html>
  );
}
