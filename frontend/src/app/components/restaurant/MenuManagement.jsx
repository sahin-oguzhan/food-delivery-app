'use client';

import { useState, useEffect, useRef } from 'react';
import { api } from '@/app/lib/api';
import ProductModal from './ProductModal';

export default function MenuManagement({ restaurantId }) {
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState('ALL');
  const [loading, setLoading] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedProduct, setSelectedProduct] = useState(null);

  const isFetching = useRef(false);

  const fetchProducts = async () => {
    if (!restaurantId || isFetching.current) return;
    try {
      isFetching.current = true;
      setLoading(true);
      const response = await api.get(`/products/${restaurantId}`);
      if (response.data && response.data.products) {
        setProducts(response.data.products);
      } else {
        setProducts([]);
      }
    } catch (err) {
      console.error('Menü yüklenemedi:', err);
    } finally {
      setLoading(false);
      isFetching.current = false;
    }
  };

  const fetchCategories = async () => {
    try {
      const response = await api.get('/owner/categories');
      setCategories(response.data || []);
    } catch (err) {
      console.error('Kategoriler yüklenemedi:', err);
    }
  };

  const handleAddCategory = async () => {
    const categoryName = prompt(
      'Lütfen eklemek istediğiniz yeni kategori adını yazın:',
    );
    if (!categoryName || categoryName.trim() === '') return;

    try {
      await api.post('/owner/categories', { name: categoryName.trim() });
      alert('Kategori başarıyla eklendi! 🎉');
      fetchCategories();
    } catch (err) {
      console.error('Kategori eklenirken hata:', err);
      alert('Kategori eklenirken bir hata oluştu.');
    }
  };

  useEffect(() => {
    fetchProducts();
    fetchCategories();
  }, [restaurantId]);

  const toggleAvailability = async (product) => {
    const nextStatus = !product.isAvailable;
    try {
      const updateRequest = {
        name: product.name,
        description: product.description,
        price: parseFloat(product.price),
        imageUrl: product.imageUrl || '',
        isAvailable: nextStatus,
        categoryId: parseInt(product.categoryId),
      };

      await api.put(`/products/${product.id}`, updateRequest);
      setProducts((prev) =>
        prev.map((p) =>
          p.id === product.id ? { ...p, isAvailable: nextStatus } : p,
        ),
      );
    } catch (err) {
      alert('Stok durumu güncellenemedi.');
    }
  };

  const handleDelete = async (productId) => {
    if (!confirm('Bu ürünü silmek istediğinize emin misiniz?')) return;
    try {
      await api.delete(`/products/${productId}`);
      setProducts((prev) => prev.filter((p) => p.id !== productId));
    } catch (err) {
      alert('Ürün silinemedi.');
    }
  };

  const filteredProducts =
    selectedCategory === 'ALL'
      ? products
      : products.filter(
          (p) =>
            p.categoryId === parseInt(selectedCategory) ||
            p.categoryName === selectedCategory,
        );

  return (
    <div className="bg-white rounded-2xl border border-gray-100 p-6 shadow-sm">
      {/* Üst Butonlar ve Başlık */}
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4 mb-6">
        <div>
          <h2 className="text-xl font-bold text-gray-800">
            Yemek Listesi ve Stok Yönetimi
          </h2>
          <p className="text-xs text-gray-400 mt-0.5">
            Ürünlerinizi kategorilere göre filtreleyebilir veya yeni kategori
            açabilirsiniz.
          </p>
        </div>
        <div className="flex gap-2 w-full sm:w-auto">
          <button
            onClick={handleAddCategory}
            className="flex-1 sm:flex-none bg-gray-100 hover:bg-gray-200 text-gray-700 font-bold py-2 px-4 rounded-xl text-sm transition-colors"
          >
            📂 Yeni Kategori Aç
          </button>
          <button
            onClick={() => {
              setSelectedProduct(null);
              setIsModalOpen(true);
            }}
            className="flex-1 sm:flex-none bg-red-600 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-xl text-sm transition-colors shadow-sm"
          >
            ➕ Yeni Ürün Ekle
          </button>
        </div>
      </div>

      <div className="flex gap-2 overflow-x-auto pb-3 mb-6 scrollbar-hide border-b border-gray-50">
        <button
          onClick={() => setSelectedCategory('ALL')}
          className={`px-4 py-1.5 rounded-full text-xs font-bold border transition-all shrink-0 ${
            selectedCategory === 'ALL'
              ? 'bg-gray-900 border-gray-900 text-white'
              : 'bg-gray-50 border-gray-200 text-gray-600 hover:bg-gray-100'
          }`}
        >
          Hepsi ({products.length})
        </button>
        {categories.map((cat) => {
          const count = products.filter(
            (p) => p.categoryId === cat.id || p.categoryName === cat.name,
          ).length;
          return (
            <button
              key={cat.id}
              onClick={() => setSelectedCategory(cat.id)}
              className={`px-4 py-1.5 rounded-full text-xs font-bold border transition-all shrink-0 ${
                selectedCategory === cat.id
                  ? 'bg-red-600 border-red-600 text-white'
                  : 'bg-gray-50 border-gray-200 text-gray-600 hover:bg-gray-100'
              }`}
            >
              {cat.name} ({count})
            </button>
          );
        })}
      </div>

      {loading ? (
        <div className="text-center py-10 text-gray-400">
          Ürünler listeleniyor... ⏳
        </div>
      ) : filteredProducts.length === 0 ? (
        <div className="text-center py-12 text-gray-400 border border-dashed rounded-xl">
          {selectedCategory === 'ALL'
            ? 'Menünüzde henüz ürün bulunmuyor.'
            : 'Bu kategoride henüz bir yemek bulunmuyor.'}
        </div>
      ) : (
        <div className="overflow-x-auto">
          <table className="w-full text-left border-collapse">
            <thead>
              <tr className="border-b border-gray-100 text-sm font-bold text-gray-400 uppercase bg-gray-50/50">
                <th className="py-3 px-4">Görsel</th>
                <th className="py-3 px-4">Yemek Adı</th>
                <th className="py-3 px-4">Kategori</th>
                <th className="py-3 px-4">Fiyat</th>
                <th className="py-3 px-4">Stok</th>
                <th className="py-3 px-4 text-right">Aksiyon</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-50 text-sm">
              {filteredProducts.map((product) => (
                <tr
                  key={product.id}
                  className="hover:bg-gray-50/50 transition-colors"
                >
                  <td className="py-4 px-4">
                    <div className="w-12 h-12 bg-gray-100 rounded-xl overflow-hidden flex items-center justify-center">
                      {product.imageUrl ? (
                        <img
                          src={product.imageUrl}
                          className="w-full h-full object-cover"
                        />
                      ) : (
                        '🍔'
                      )}
                    </div>
                  </td>
                  <td className="py-4 px-4 font-semibold text-gray-900">
                    {product.name}
                    <p className="text-xs font-normal text-gray-400 max-w-xs truncate">
                      {product.description}
                    </p>
                  </td>
                  <td className="py-4 px-4">
                    <span className="bg-gray-100 text-gray-600 text-xs px-2.5 py-1 rounded-md font-medium">
                      {product.categoryName || 'Genel'}
                    </span>
                  </td>
                  <td className="py-4 px-4 font-bold text-gray-900">
                    {product.price} ₺
                  </td>
                  <td className="py-4 px-4">
                    <button
                      onClick={() => toggleAvailability(product)}
                      className={`px-3 py-1 rounded-full text-xs font-bold border transition-colors ${
                        product.isAvailable
                          ? 'bg-green-50 border-green-200 text-green-700 hover:bg-green-100'
                          : 'bg-red-50 border-red-200 text-red-700 hover:bg-red-100'
                      }`}
                    >
                      {product.isAvailable ? '● Satışta' : '○ Tükendi'}
                    </button>
                  </td>
                  <td className="py-4 px-4 text-right space-x-3">
                    <button
                      onClick={() => {
                        setSelectedProduct(product);
                        setIsModalOpen(true);
                      }}
                      className="text-indigo-600 font-semibold"
                    >
                      Düzenle
                    </button>
                    <button
                      onClick={() => handleDelete(product.id)}
                      className="text-red-600 font-semibold"
                    >
                      Sil
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
      <ProductModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        product={selectedProduct}
        restaurantId={restaurantId}
        onSave={fetchProducts}
      />
    </div>
  );
}
