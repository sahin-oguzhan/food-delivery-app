import Link from 'next/link';

export default function RestaurantCard({ restaurant }) {
  const defaultImage =
    'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?q=80&w=2000';
  const mockRating = '4.8';
  const mockTime = '25-35';
  const mockMinOrder = '150';
  return (
    <Link
      href={`/restaurants/${restaurant.id}`}
      className="group block bg-white rounded-2xl shadow-sm hover:shadow-xl transition-all duration-300 overflow-hidden border border-gray-100"
    >
      {/* Restoran Görseli */}
      <div className="relative h-48 w-full overflow-hidden">
        <img
          src={defaultImage}
          alt={restaurant.name}
          className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500"
        />
        <div className="absolute top-4 right-4 bg-white px-2 py-1 rounded-lg shadow-md flex items-center font-bold text-sm text-gray-800">
          ⭐ {mockRating}
        </div>
      </div>
      {/* Restorant Bilgileri */}
      <div className="p-5">
        <div className="flex justify-between item-start mb-2">
          <h3 className="text-xl font-bold text-gray-900 group-hover:text-red-600 transition-colors">
            {restaurant.name}
          </h3>
        </div>
        <p className="text-gray-500 text-sm mb-4">{restaurant.description}</p>
        <div className="flex items-center justify-between border-t border-gray-200 pt-4">
          <div className="flex items-center text-sm font-medium text-gray-600">
            {mockTime} dk{' '}
          </div>
          <div className="text-sm font-medium text-green-600 bg-green-100 px-3 py-1 rounded-full">
            {mockMinOrder} TL min.
          </div>
        </div>
      </div>
    </Link>
  );
}
