import Link from 'next/link';

export default function RestaurantCard({ restaurant }) {
  return (
    <Link
      href={`/restaurants/${restaurant.id}`}
      className="group block bg-white rounded-2xl shadow-sm hover:shadow-xl transition-all duration-300 overflow-hidden border border-gray-100"
    >
      {/* Restoran Görseli */}
      <div className="relative h-48 w-full overflow-hidden">
        <img
          src={restaurant.imageUrl}
          alt={restaurant.name}
          className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500"
        />
        <div className="absolute top-4 right-4 bg-white px-2 py-1 rounded-lg shadow-md flex items-center font-bold text-sm text-gray-800">
          ⭐ {restaurant.rating}
        </div>
      </div>
      {/* Restorant Bilgileri */}
      <div className="p-5">
        <div className="flex justify-between item-start mb-2">
          <h3 className="text-xl font-bold text-gray-900 group-hover:text-red-600 transition-colors">
            {restaurant.name}
          </h3>
        </div>
        <p className="text-gray-500 text-sm mb-4">{restaurant.category}</p>
        <div className="flex items-center justify-between border-t border-gray-200 pt-4">
          <div className="flex items-center text-sm font-medium text-gray-600">
            {restaurant.deliveryTime} dk{' '}
          </div>
          <div className="text-sm font-medium text-green-600 bg-green-100 px-3 py-1 rounded-full">
            {restaurant.minOrderValue} TL min.
          </div>
        </div>
      </div>
    </Link>
  );
}
