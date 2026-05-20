import Hero from './components/Hero';
import RestaurantCard from './components/RestaurantCard';
import { api } from './lib/api';

export default async function Home() {
  let restaurants = [];

  try {
    const response = await api.get('/restaurants');
    restaurants = response.data.restaurants;
  } catch (error) {
    console.error("Backend'e bağlanırken hata oluştu!", error.message);
  }

  return (
    <div className="min-h-screen">
      <Hero />
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <h2 className="text-3xl font-bold text-gray-900 mb-8">
          Popüler Restoranlar
        </h2>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
          {restaurants.map((restaurant) => (
            <RestaurantCard key={restaurant.id} restaurant={restaurant} />
          ))}
        </div>
      </main>
    </div>
  );
}
