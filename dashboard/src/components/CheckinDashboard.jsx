import useCheckins from "../hooks/useCheckins";


export default function CheckinsDashboard() {

    const checkins =
        useCheckins();

    const maxBurst =
        Math.max(
            ...checkins.map(
                item => item.currentBurst
            ),
            1
        );

    return (

        <div className="checkins-container">

            <h2>
                🎟 Entry Traffic
            </h2>

            {
                checkins.map(update => (

                    <div
                        key={update.timeframe}
                        className="checkin-row"
                    >

                        <div className="timeframe">

                            {update.timeframe}

                        </div>

                        <div className="bar-wrapper">

                            <div
                                className="bar"
                                style={{
                                    width:
                                        `${(update.currentBurst / maxBurst) * 100}%`
                                }}
                            />

                        </div>

                        <div className="count">

                            {update.currentBurst}

                            {
                                update.newRecord &&
                                <span className="medal">
                                    🏅
                                </span>
                            }

                        </div>

                    </div>

                ))
            }

        </div>
    );
}