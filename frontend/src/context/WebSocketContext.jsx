'use client';

import { createContext, useContext, useEffect, useState } from 'react';
import { useAuth } from './AuthContext';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

const WebSocketContext = createContext(null);

export const WebSocketProvider = ({ children }) => {
  const { user } = useAuth();
  const [stompClient, setStompClient] = useState(null);
  const [isConnected, setIsConnected] = useState(false);

  useEffect(() => {
    if (!user) {
      if (stompClient) {
        stompClient.deactivate();
      }
      setIsConnected(false);
      return;
    }

    const socket = new SockJS('http://localhost:8080/ws');

    const client = new Client({
      webSocketFactory: () => socket,
      debug: (str) => {
        console.log('[WebSocket Debug]:', str);
      },
      reconnectDelay: 5000,
      onConnect: () => {
        console.log('WebSocket bağlantısı başarıyla kuruldu!');
        setIsConnected(true);
      },
      onStompError: (frame) => {
        console.error('STOMP hatası oluştu', frame.headers['message']);
      },
      onDisconnect: () => {
        console.log('WebSocket bağlantısı kesildi!');
        setIsConnected(false);
      },
    });

    client.activate();
    setStompClient(client);

    return () => {
      client.deactivate();
    };
  }, [user]);

  return (
    <WebSocketContext.Provider value={{ stompClient, isConnected }}>
      {children}
    </WebSocketContext.Provider>
  );
};

export const useWebSocket = () => useContext(WebSocketContext);
