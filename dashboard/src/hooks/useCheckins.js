import { useEffect, useState } from "react";
import { connectWebSocket } from "../services/websocket";

export default function useCheckins() {

    const [checkins, setCheckins] =
        useState([]);

    useEffect(() => {

        const client =
            connectWebSocket(

                () => {},

                (checkinData) => {

                    console.log(
        "Received checkin update:",
        checkinData
    );


                    setCheckins(previous => {

                        const updated =
                            previous.map(item => ({
                                ...item,
                                newRecord: false
                            }));

                        return [
                            ...updated,
                            checkinData
                        ].slice(-20);
                    });
                }
            );

        return () => {

            if (client) {
                client.deactivate();
            }
        };

    }, []);

    return checkins;
}