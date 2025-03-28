'use client';

import { Client } from "@stomp/stompjs";
import { useEffect, useState } from "react";

interface NotificationProps {
    message: string;
    time: string;
}

export default function Notification() {
    const [client, setClient] = useState<Client | null>(null);
    const [notifications, setNotifications] = useState<NotificationProps[]>([]);
    const [hiddenNotifications, setHiddenNotifications] = useState<number[]>([]);

    useEffect(() => {
        const stompClient = new Client({
            brokerURL: 'ws://localhost:8080/ws/websocket',
            onConnect: () => {
                console.log('Connected');
                stompClient.subscribe('/topic/public', (payload) => {
                    const newNotification = JSON.parse(payload.body) as NotificationProps;
                    console.log("newNotification : ", newNotification);
                    setNotifications((prevNotifications) => [...prevNotifications, newNotification]);
                });
            },
            onDisconnect: () => {
                console.log('Disconnected');
            },
            onStompError: (frame) => {
                console.error('Error', frame);
            }
        });

        stompClient.activate();
        setClient(stompClient);
        return () => {
            stompClient.deactivate();
        };
    }, []);

    const handleNotificationClick = (index: number) => {
        if(client) {
            client.publish({
                destination: '/app/noti.read',
                body: JSON.stringify({
                    message: notifications[index].message,
                    time: notifications[index].time,
                })
            });
            setHiddenNotifications((prev) => [...prev, index]);
        }
    };
        
    return (
        <div className="flex flex-col gap-2">
        {notifications.map((notification, index) => (
            !hiddenNotifications.includes(index) && (
                <div 
                    key={index}
                    onClick={() => handleNotificationClick(index)}
                    onKeyDown={(e) => e.key === 'Enter' && handleNotificationClick(index)}
                    className="cursor-pointer p-4 bg-white shadow rounded hover:bg-gray-50 transition-colors"
                    role="button"
                    tabIndex={0}
                    aria-label={`Notification: ${notification.message}. Click to dismiss.`}
                >
                    <p>{notification.message}</p>
                    <p>{notification.time}</p>
                </div>
            )
        ))}
    </div>
    )
}

