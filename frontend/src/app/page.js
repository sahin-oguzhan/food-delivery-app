import Hero from './components/Hero';
import RestaurantCard from './components/RestaurantCard';

const mockRestaurants = [
  {
    id: 1,
    name: 'Anne Eli Lezzetleri',
    category: 'Geleneksel Türk Mutfağı • Mercimek Köftesi • Kısır',
    rating: '4.9',
    deliveryTime: '25-35',
    minOrderValue: '150',
    imageUrl:
      'https://images.unsplash.com/photo-1541518763669-27fef04b14ea?q=80&w=2000',
  },
  {
    id: 2,
    name: 'Burger Station',
    category: 'Fast Food • Burger • Patates',
    rating: '4.5',
    deliveryTime: '15-25',
    minOrderValue: '120',
    imageUrl:
      'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?q=80&w=2000',
  },
  {
    id: 3,
    name: 'Napoli Pizza',
    category: 'İtalyan • Pizza • Makarna',
    rating: '4.7',
    deliveryTime: '30-40',
    minOrderValue: '200',
    imageUrl:
      'https://images.unsplash.com/photo-1513104890138-7c749659a591?q=80&w=2000',
  },
];

export default function Home() {
  return (
    <div className="min-h-screen">
      <Hero />
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <h2 className="text-3xl font-bold text-gray-900 mb-8">
          Popüler Restoranlar
        </h2>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
          {mockRestaurants.map((restaurant) => (
            <RestaurantCard key={restaurant.id} restaurant={restaurant} />
          ))}
        </div>
      </main>
    </div>
  );
}
