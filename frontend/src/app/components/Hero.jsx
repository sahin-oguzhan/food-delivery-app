export default function Hero() {
  return (
    <div className="w-full relative bg-red-600 h-112.5 flex items-center justify-center">
      <div className="bg-[url('/images/hero.jpeg')] absolute inset-0 bg-cover bg-center opacity-30"></div>
      <div className="relative z-10 w-full max-w-4xl text-center">
        <h1 className="text-5xl md:text-6xl font-extrabold text-white mb-6 drop-shadow-lg tracking-tight">
          Acıktın mı?
        </h1>
        <p className="text-xl md:text-2xl text-white mb-10 font-medium drop-shadow-md">
          En sevdiğin lezzetler dakikalar içinde kapında.
        </p>
        <div className="flex max-w-2xl mx-auto bg-white rounded-full p-2 shadow-2xl transition-transform hover:scale-[1.02] duration-300">
          <input
            type="text"
            placeholder="Burger, pizza veya lahmacun ara..."
            className="flex-1 bg-transparent px-6 py-3 outline-none text-gray-700 text-lg"
          />
          <button className="bg-red-600 text-white px-8 py-3 rounded-full font-bold hover:bg-red-700 transition duration-300 shadow-md">
            Yemek bul
          </button>
        </div>
      </div>
      <div className="absolute bottom-0 left-0 right-0 h-16 bg-linear-to-t from-gray-50 to-transparent"></div>
    </div>
  );
}
