import { api } from '@/app/lib/api';
import { useWebSocket } from '@/context/WebSocketContext';
import { useCallback, useEffect, useState } from 'react';

export function useRestaurantOrders(user) {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { stompClient, isConnected } = useWebSocket();

  const fetchRestaurantOrders = useCallback(async () => {
    try {
      console.log('[API] Siparişler veritabanından çekiliyor...');
      const response = await api.get('/owner/orders');
      setOrders(response.data);
    } catch (error) {
      console.error('Restoran siparişleri çekilemedi:', error);
      setError('Siparişler yüklenirken bir hata oluştu');
    } finally {
      setLoading(false);
    }
  }, []);

  const updateOrderStatus = async (orderId, newStatus) => {
    try {
      console.log(
        `[API] Sipariş durumu güncelleniyor: #${orderId} -> ${newStatus}`,
      );

      const response = await api.put(
        `/owner/orders/${orderId}/status?orderStatus=${newStatus}`,
      );

      setOrders((prevOrders) =>
        prevOrders.map((order) => {
          return order.orderId == orderId ? response.data : order;
        }),
      );

      console.log(
        `[API] #${orderId} nolu siparişin durumu state üzerinde '${newStatus}' olarak güncellendi.`,
      );
    } catch (error) {
      console.error('Sipariş durumu güncellenemedi:', error);
      alert('Durum güncellenirken bir hata oluştu');
    }
  };

  useEffect(() => {
    if (user) {
      fetchRestaurantOrders();
    }
  }, [user, fetchRestaurantOrders]);

  useEffect(() => {
    console.log('[WebSocket Status Check]:', {
      isConnected,
      hasClient: !!stompClient,
      userId: user?.id,
    });

    if (isConnected && stompClient && user && user.id) {
      const topicPath = `/topic/restaurant/${user.id}`;
      console.log(
        `📡 [WebSocket] ${topicPath} kanalına başarıyla abone olunuyor...`,
      );

      const subscription = stompClient.subscribe(topicPath, (message) => {
        const notification = JSON.parse(message.body);
        console.log(
          '🎈 [WebSocket] KANALINDAN CANLI VERİ GELDİ:',
          notification,
        );
        fetchRestaurantOrders();
      });

      return () => {
        console.log(
          `🛑 [WebSocket] ${topicPath} aboneliği sonlandırıldı (Unsubscribed).`,
        );
        subscription.unsubscribe();
      };
    }
  }, [isConnected, stompClient, user, fetchRestaurantOrders]);

  return {
    orders,
    loading,
    error,
    updateOrderStatus,
    refetch: fetchRestaurantOrders,
  };
}
