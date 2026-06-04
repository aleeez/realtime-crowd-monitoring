export default function CrowdBar({ stage }) {

    const percentage =
        Math.min(
            stage.percentage,
            100
        );

    let color = "#22c55e";

    if (percentage > 70)
        color = "#f59e0b";

    if (percentage > 90)
        color = "#ef4444";

    return (

        <div className="stage-card">

            <div className="header">

                <h2>{stage.stage}</h2>

                {
                    stage.overcrowded &&
                    <span>🚨</span>
                }

            </div>

            <div className="progress-bg">

                <div
                    className="progress-fill"
                    style={{
                        width: `${percentage}%`,
                        backgroundColor: color
                    }}
                />

            </div>

            <p>

                {stage.peopleCount}
                /
                {stage.capacity}

                (
                {percentage.toFixed(0)}
                %
                )

            </p>

        </div>
    );
}