import { api } from '@/app/lib/api';

export default async function RestaurantDetail({ params }) {
  const { id } = await params;
  let restaurant = null;
  let products = [];

  try {
    const restaurantResponse = await api.get(`/restaurants/${id}`);
    restaurant = restaurantResponse.data;

    if (restaurant) {
      const productsResponse = await api.get(`/products/${id}`);
      products = productsResponse.data.products;
    }
  } catch (error) {
    console.error(
      'Restoran detayları çekilirken bir hata oluştu: ',
      error.message,
    );
  }

  if (!restaurant) {
    return (
      <div>
        <div>
          <h2>Restoran Bulunamadı</h2>
          <p>Aradığınız restoran sistemde mevcut değil!</p>
        </div>
      </div>
    );
  }

  const defaultImage =
    'https://images.unsplash.com/photo-1555396273-367ea4eb4db5?q=80&w=2000';

  return (
    <div className="min-h-screen bg-gray-50 pb-20">
      {/*Restoran Hero */}
      <div className="relative h-72 w-full">
        <img
          src={defaultImage}
          alt={restaurant.name}
          className="w-full h-full object-cover"
        />
        <div className="absolute inset-0 bg-black/50 flex flex-col justify-end p-8">
          <div className="max-w-7xl mx-auto w-full">
            <h1 className="text-4xl font-extrabold text-white mb-2">
              {restaurant.name}
            </h1>
            <p className="text-gray-200 text-lg mb-2">
              {restaurant.description}
            </p>
            <p className="text-gray-300 text-sm flex items-center gap-2">
              📍 {restaurant.address} | 📞 {restaurant.phoneNumber}
            </p>
          </div>
        </div>
      </div>

      {/*Alt Kısım*/}
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 mt-12">
        <h2 className="text-3xl font-bold text-gray-900 mb-8 border-b pb-4">
          Menü
        </h2>
        {!products || products.length === 0 ? (
          <div className="text-center text-gray-500 py-10 bg-white rounded-2xl shadow-sm border border-gray-100">
            Bu restoran henüz menüsüne ürün eklememiş.
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {products.map((product) => (
              <div
                key={product.id}
                className="bg-white rounded-2xl p-5 shadow-sm border border-gray-100 hover:shadow-md transition-shadow flex flex-col justify-between"
              >
                <div>
                  <div className="flex justify-between items-start mb-2">
                    <h3 className="text-xl font-bold text-gray-900">
                      {product.name}
                    </h3>
                    <span className="text-lg font-bold text-green-600 bg-green-50 px-3 py-1 rounded-lg">
                      {product.price} TL
                    </span>
                  </div>
                  <p className="text-gray-500 text-sm mb-4">
                    {product.description}
                  </p>
                </div>

                <button className="w-full bg-red-600 hover:bg-red-700 text-white font-semibold py-2 px-4 rounded-xl transition-colors">
                  Sepete Ekle
                </button>
              </div>
            ))}
          </div>
        )}
      </main>
    </div>
  );
}
