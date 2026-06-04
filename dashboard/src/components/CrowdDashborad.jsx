import { useEffect, useState } from "react";

import CrowdBar from "./CrowdBar";

import {
    connectWebSocket
}
from "../services/websocket";

export default function CrowdDashboard() {

    const [stages, setStages] =
        useState({});

    useEffect(() => {

        connectWebSocket(
            (data) => {

                setStages(
                    previous => ({
                        ...previous,
                        [data.stage]: data
                    })
                );
            }
        );

    }, []);

    return (

        <div>

            {
                Object.values(stages)
                    .map(stage => (

                        <CrowdBar
                            key={stage.stage}
                            stage={stage}
                        />

                    ))
            }

        </div>
    );
}