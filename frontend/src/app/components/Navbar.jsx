import Link from 'next/link';

export default function Navbar() {
  return (
    <nav className="bg-white shadow-md w-full sticky top-0 z-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          <Link
            href="/"
            className="text-2xl text-red-600 font-bold tracking-tight"
          >
            Food-Delivery
          </Link>
          <div className="flex items-center space-x-6">
            <Link
              href="/restaurants"
              className="text-gray-600 hover:text-red-600 font-medium transition duration-200"
            >
              Restoranlar
            </Link>
            <Link
              href="/login"
              className="text-gray-600 hover:text-red-600 font-medium transition duration-200"
            >
              Giriş Yap
            </Link>
            <button className="bg-red-600 text-white px-5 py-2 rounded-full hover:bg-red-700 transition duration-300">
              Sepetim
            </button>
          </div>
        </div>
      </div>
    </nav>
  );
}
