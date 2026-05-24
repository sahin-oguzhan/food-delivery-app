import Link from 'next/link';

export default function OrderSuccessPage() {
  return (
    <div className="min-h-[70vh] flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-md w-full bg-white p-8 rounded-3xl border border-gray-100 shadow-xl text-center transform transition-all animate-in zoom-in duration-500">
        <div className="mx-auto flex items-center justify-center h-20 w-20 rounded-full bg-green-100 mb-6">
          <svg
            className="h-10 w-10 text-green-600"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              d="M5 13l4 4L19 7"
            />
          </svg>
        </div>

        <h2 className="text-3xl font-extrabold text-gray-900 mb-4">
          Sipariş Başarılı!
        </h2>

        <p className="text-gray-600 mb-8 leading-relaxed text-sm">
          Ödemeniz başarıyla alındı ve siparişiniz restorana iletildi. Lezzetli
          yemekleriniz en kısa sürede hazırlanıp yola çıkacak. Bizi tercih
          ettiğiniz için teşekkürler!
        </p>

        <div className="space-y-4">
          <Link
            href="/orders"
            className="block w-full bg-red-600 hover:bg-red-700 text-white font-bold py-3.5 px-4 rounded-xl transition-colors shadow-sm shadow-red-200"
          >
            Siparişlerimi Görüntüle
          </Link>

          <Link
            href="/"
            className="block w-full bg-gray-50 border border-gray-200 hover:bg-gray-100 text-gray-700 font-bold py-3.5 px-4 rounded-xl transition-colors"
          >
            Ana Sayfaya Dön
          </Link>
        </div>
      </div>
    </div>
  );
}
