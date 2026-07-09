import { api } from '@/app/lib/api';
import { useEffect, useState } from 'react';

export default function ProductModal({
  isOpen,
  onClose,
  product,
  restaurantId,
  onSave,
}) {
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    price: '',
    categoryId: '',
  });

  const [categories, setCategories] = useState([]);
  const [imageFile, setImageFile] = useState(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (isOpen) {
      api
        .get('/owner/categories')
        .then((res) => setCategories(res.data || []))
        .catch((err) => console.error('Kategoriler yüklenemedi:', err));
    }
  }, [isOpen]);

  useEffect(() => {
    if (product) {
      const currentStatus =
        product.available !== undefined
          ? product.available
          : product.isAvailable;

      setFormData({
        name: product.name || '',
        description: product.description || '',
        price: product.price || '',
        categoryId: product.categoryId || '',
        isAvailable: currentStatus ?? true,
      });
    } else {
      setFormData({
        name: '',
        description: '',
        price: '',
        categoryId: '',
        isAvailable: true,
      });
      setImageFile(null);
    }
  }, [product, isOpen]);

  if (!isOpen) return null;

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      setLoading(true);

      if (product) {
        const updateRequest = {
          name: formData.name,
          description: formData.description,
          price: parseFloat(formData.price),
          imageUrl: product.imageUrl || '',
          isAvailable: formData.isAvailable,
          categoryId: parseInt(formData.categoryId),
        };

        const response = await api.put(
          `/products/${product.id}`,
          updateRequest,
        );
        onSave(response.data);
      } else {
        const submissionData = new FormData();

        const productDto = {
          name: formData.name,
          description: formData.description,
          price: parseFloat(formData.price),
          categoryId: parseInt(formData.categoryId),
          restaurantId: parseInt(restaurantId),
        };

        submissionData.append(
          'product',
          new Blob([JSON.stringify(productDto)], {
            type: 'application/json',
          }),
        );

        if (imageFile) {
          submissionData.append('image', imageFile);
        }

        const response = await api.post(
          '/api/products/restaurant',
          submissionData,
          {
            headers: { 'Content-Type': 'multipart/form-data' },
          },
        );
        onSave(response.data);
      }
      onClose();
    } catch (error) {
      console.error('Ürün kaydedilemedi:', error);
      alert(error.response?.data?.message || 'İşlem başarısız oldu.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="fixed inset-0 bg-black/50 backdrop-blur-sm flex items-center justify-center z-50 p-4">
      <div className="bg-white rounded-2xl max-w-md w-full p-6 shadow-xl border border-gray-100 relative">
        <button
          onClick={onClose}
          className="absolute top-4 right-4 text-gray-400 hover:text-gray-600 font-bold text-lg"
        >
          ✕
        </button>
        <h3 className="text-xl font-bold text-gray-900 mb-6">
          {product ? '🍔 Ürünü Düzenle' : '➕ Yeni Ürün Ekle'}
        </h3>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-xs font-bold text-gray-500 uppercase mb-1">
              Yemek Adı
            </label>
            <input
              type="text"
              name="name"
              required
              value={formData.name}
              onChange={handleChange}
              className="w-full border border-gray-200 rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:border-red-500"
              placeholder="Örn: Adana Kebap"
            />
          </div>
          <div>
            <label className="block text-xs font-bold text-gray-500 uppercase mb-1">
              Açıklama
            </label>
            <textarea
              name="description"
              value={formData.description}
              onChange={handleChange}
              className="w-full border border-gray-200 rounded-xl px-4 py-2 text-sm focus:outline-none focus:border-red-500 h-20 resize-none"
              placeholder="İçindekiler..."
            />
          </div>
          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-xs font-bold text-gray-500 uppercase mb-1">
                Fiyat (TL)
              </label>
              <input
                type="number"
                name="price"
                step="0.01"
                required
                value={formData.price}
                onChange={handleChange}
                className="w-full border border-gray-200 rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:border-red-500"
                placeholder="0.00"
              />
            </div>
            <div>
              <label className="block text-xs font-bold text-gray-500 uppercase mb-1">
                Kategori
              </label>
              <select
                name="categoryId"
                required
                value={formData.categoryId}
                onChange={handleChange}
                className="w-full border border-gray-200 rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:border-red-500 bg-white"
              >
                <option value="">Seçiniz</option>
                {categories.map((cat) => (
                  <option key={cat.id} value={cat.id}>
                    {cat.name}
                  </option>
                ))}
              </select>
            </div>
          </div>
          {!product && (
            <div>
              <label className="block text-xs font-bold text-gray-500 uppercase mb-1">
                Yemek Görseli
              </label>
              <input
                type="file"
                accept="image/*"
                onChange={(e) => setImageFile(e.target.files[0])}
                className="w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4 file:rounded-full file:border-0 file:text-xs file:font-bold file:bg-red-50 file:text-red-700 hover:file:bg-red-100 cursor-pointer"
              />
            </div>
          )}
          <div className="border-t pt-4 mt-6 flex justify-end gap-3">
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 rounded-xl text-sm font-semibold text-gray-500 hover:bg-gray-100 transition-colors"
            >
              Vazgeç
            </button>
            <button
              type="submit"
              disabled={loading}
              className="bg-red-600 hover:bg-red-700 disabled:bg-gray-400 text-white font-bold px-6 py-2 rounded-xl text-sm transition-colors shadow-sm"
            >
              {loading ? 'Kaydediliyor...' : 'Kaydet'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
