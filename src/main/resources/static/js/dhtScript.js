$(document).ready(function () {
    moment.locale('ro');

    let lineChart = null;
    let polarChart = null;

    $.fn.dataTable.moment( 'DD MMMM YYYY, HH:mm:ss' );
    $("#dhtTable").DataTable({
        ajax: {
            url: "dht/getAllData",
            dataSrc: ''
        },
        columns: [
            {
                data: "airHumidity",
                render: function(data) {
                    return data.toFixed(2);
                }
            },
            {
                data: "temperature",
                render: function(data) {
                    return data.toFixed(2);
                }
            },
            {data: "microcontrollerid"},
            {
                data: "timestamp",
                render: function (data) {
                    // const momentUtc = moment.utc(data);
                    const momentLocal = moment(data).local();
                    return momentLocal.format('DD MMMM YYYY, HH:mm:ss');
                }
            }
        ]
    });

    function generateDhtData() {
        return new Promise( (resolve, reject) => {
            let data24h;
            let data7d;

            const request24h = $.ajax({
                type: "GET",
                url: "dht/getAvgLast24Hours",
                contentType: "application/json",
            });

            const request7d = $.ajax({
                type: "GET",
                url: "dht/getAvgLast7Days",
                contentType: "application/json",
            });

            $.when(request24h, request7d)
                .done( (response24h, response7d) => {
                    const data24h = response24h[0];
                    const data7d = response7d[0];

                    const xData24h = [];
                    const yData24h = [];
                    const zData24h = [];
                    const xData7d = [];
                    const yData7d = [];
                    const zData7d = [];

                    data24h.forEach((data) => {
                        const formatedDate = moment(data.time).format("HH:mm");
                        xData24h.push(formatedDate);
                        yData24h.push(data.temperatureAvg.toFixed(2));
                        zData24h.push(data.airHumidityAvg.toFixed(2));
                    });

                    data7d.forEach((data) => {
                        const formatedDate = moment(data.time).format("DD.MM.YYYY");
                        xData7d.push(formatedDate);
                        yData7d.push(data.temperatureAvg.toFixed(2));
                        zData7d.push(data.airHumidityAvg.toFixed(2));
                    });

                    const result = {
                        data24hours: {
                            xData: xData24h,
                            yData: yData24h,
                            zData: zData24h
                        },
                        data7days: {
                            xData: xData7d,
                            yData: yData7d,
                            zData: zData7d
                        }
                    };
                    resolve(result);
                }).fail( (error) => {
                reject(error);
            });

        });
    }

    async function getData(chart1, chart2, selectedBtn) {
        const result = await generateDhtData();

        const data24h = result.data24hours;
        const data7d = result.data7days;
        if (selectedBtn === 'btn24h') {
            chart1.data.labels = data24h.xData;
            chart1.data.datasets[0].data = data24h.yData;
            chart1.update();

            chart2.data.labels = data24h.xData;
            chart2.data.datasets[0].data = data24h.zData;
            chart2.update();
        } else if (selectedBtn === 'btn7d') {
            chart1.data.labels = data7d.xData;
            chart1.data.datasets[0].data = data7d.yData;
            chart1.update();

            chart2.data.labels = data7d.xData;
            chart2.data.datasets[0].data = data7d.zData;
            chart2.update();
        }
    };

    function drawLineChart() {
        const ctx = document.getElementById('lineChart').getContext('2d');
        let gradient = ctx.createLinearGradient(0, 0, 0, 400);
        gradient.addColorStop(0, 'rgba(58, 123, 213, 1)');
        gradient.addColorStop(1, 'rgba(0, 210, 255, 0.3)');
        const data = {

            datasets: [{
                data: [],
                label: "Temperatura (°C)",
                fill: true,
                backgroundColor: gradient,
                borderColor: "#fff",
                pointBackgroundColor: "#A9A9A9",
                tension: 0.2,
            },
            ],
        };

        let delayed;

        const config = {
            type: 'line',
            data: data,
            options: {
                radius: 5,
                hitRadius: 30,
                hoverRadius: 10,
                responsive: true,
                animation: {
                    onComplete: () => {
                        delayed = true;
                    },
                    delay: (context) => {
                        let delay = 0;
                        if (context.type === 'data' && context.mode === 'default' && !delayed) {
                            delay = context.dataIndex * 300 + context.datasetIndex * 100;
                        }
                        return delay;
                    },
                },
                scales: {
                    y: {
                        ticks: {
                            callback: function (value) {
                                return value + "°C";
                            },
                        },
                    },
                },
            },
        };

        lineChart = new Chart(ctx, config);
    }


    function drawPolarChart() {
        const ctx = document.getElementById('polarChart').getContext('2d');

        bgColors = [];
        for (let i = 0; i < 24; i++) {
            const randomColor = Math.floor(Math.random()*16777215).toString(16);
            bgColors.push("#"+randomColor);
        }

        const data = {

            datasets: [{
                data: [],
                backgroundColor: bgColors,
            },
            ],
        };

        const config = {
            type: 'polarArea',
            data: data,
            options: {
                plugins: {
                    title: {
                      display: true,
                      text: 'Umiditatea din aer',
                        padding: {
                            top: 40,
                        },

                    },
                    legend: {
                        position: 'right'
                    },
                },
            },
        };

        polarChart = new Chart(ctx, config);
    }

    function updateCharts(selectedBtn) {
        if (selectedBtn === 'btn24h') {
            getData(lineChart, polarChart, 'btn24h');
        } else if (selectedBtn === 'btn7d') {
            getData(lineChart, polarChart, 'btn7d');
        }

    }

    $('#data24hours').click(function() {
        updateCharts('btn24h');
    });

    $('#data7days').click(function() {
        updateCharts('btn7d');
    });

    drawLineChart();
    drawPolarChart();
});
