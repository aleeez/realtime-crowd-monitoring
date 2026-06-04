import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

export const connectWebSocket = (
    crowdCallback,
    checkinsCallback
) => {

    const client = new Client({

        webSocketFactory: () =>
            new SockJS("http://localhost:8082/ws"),

        reconnectDelay: 5000,

        onConnect: () => {

            console.log("Connected");

            client.subscribe(
                "/topic/crowd",
                (message) => {

                    crowdCallback(
                        JSON.parse(message.body)
                    );
                }
            );

            client.subscribe(
                "/topic/checkins",
                (message) => {

                    checkinsCallback(
                        JSON.parse(message.body)
                    );
                }
            );
        }
    });

    client.activate();

    return client;
};