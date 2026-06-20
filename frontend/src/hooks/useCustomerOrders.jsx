import { api } from '@/app/lib/api';
import { useWebSocket } from '@/context/WebSocketContext';
import { useCallback, useEffect, useState } from 'react';

export function useCustomerOrders(user) {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const { stompClient, isConnected } = useWebSocket();

  const fetchCustomerOrders = useCallback(async () => {
    try {
      const response = await api.get('customer/orders');
      setOrders(response.data);
    } catch (error) {
      console.error('Siparişler çekilemedi:', error);
      setError('Sipariş geçmişiniz yüklenirken bir hata oluştu');
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    if (user) {
      fetchCustomerOrders();
    }
  }, [user, fetchCustomerOrders]);

  useEffect(() => {
    if (!isConnected || !stompClient || !user) {
      console.log('[WebSocket] Kullanıcı bilgileri bekleniyor!');
      console.log(user);
      return;
    }

    const topicPath = `/topic/customer/${user.id}`;
    console.log('[WebSocket] Müşteri canlı takip başarılı!');

    const subscription = stompClient.subscribe(topicPath, (message) => {
      const notification = JSON.parse(message.body);
      console.log(
        '[WebSocket] Restorandan canlı durum güncellemesi geldi!',
        notification,
      );
      fetchCustomerOrders();
    });

    return () => {
      console.log(`[WebSocket] ${topicPath} aboneliği sonlandırıldı!`);
      subscription.unsubscribe();
    };
  }, [isConnected, stompClient, user, fetchCustomerOrders]);

  return { orders, loading, error, refecth: fetchCustomerOrders };
}
