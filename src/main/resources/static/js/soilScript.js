$(document).ready(function () {
    moment.locale('ro');

    let polarChart = null;

    $.fn.dataTable.moment( 'DD MMMM YYYY, HH:mm:ss' );
    $("#soilTable").DataTable({
        ajax: {
            url: "soil/getAllSoilData",
            dataSrc: ''
        },
        columns: [
            {data: "soilHumidity"},
            {data: "microcontrollerid"},
            {
                data: "timestamp",
                render: function (data) {
                    const momentLocal = moment(data).local();
                    return momentLocal.format('DD MMMM YYYY, HH:mm:ss');
                }
            }
        ]
    });

    function generateSoilData() {
        return new Promise( (resolve, reject) => {
            let data24h;
            let data7d;

            const request24h = $.ajax({
                type: "GET",
                url: "soil/getAvgLast24Hours",
                contentType: "application/json",
            });

            const request7d = $.ajax({
                type: "GET",
                url: "soil/getAvgLast7Days",
                contentType: "application/json",
            });

            $.when(request24h, request7d)
                .done( (response24h, response7d) => {
                    const data24h = response24h[0];
                    const data7d = response7d[0];

                    const xData24h = [];
                    const yData24h = [];
                    const xData7d = [];
                    const yData7d = [];

                    data24h.forEach((data) => {
                        const formatedDate = moment(data.time).format("HH:mm");
                        xData24h.push(formatedDate);
                        yData24h.push(data.soilHumidityAvg);
                    });

                    data7d.forEach((data) => {
                        const formatedDate = moment(data.time).format("DD.MM.YYYY");
                        xData7d.push(formatedDate);
                        yData7d.push(data.soilHumidityAvg);
                    });

                    const result = {
                        data24hours: {
                            xData: xData24h,
                            yData: yData24h
                        },
                        data7days: {
                            xData: xData7d,
                            yData: yData7d
                        }
                    };
                    resolve(result);
                }).fail( (error) => {
                reject(error);
            });

        });
    }

    function formatHumidityData(values) {
        let newData = [];
        values.forEach((values) => {
            if (values <= 2000 ) {
                newData.push(3);
            } else if (values > 2000 && values < 3750) {
                newData.push(2);
            } else if (values >= 3750) {
                newData.push(1);
            }
        });
        return newData;
    }

    async function getData(chart2, selectedBtn) {
        const result = await generateSoilData();

        const data24h = result.data24hours;
        const data7d = result.data7days;
        if (selectedBtn === 'btn24h') {
            chart2.data.labels = data24h.xData;
            chart2.data.datasets[0].data = formatHumidityData(data24h.yData);
            chart2.update();
        } else if (selectedBtn === 'btn7d') {
            chart2.data.labels = data7d.xData;
            chart2.data.datasets[0].data = formatHumidityData(data7d.yData);
            chart2.update();
        }
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
                    legend: {
                        position: 'right'
                    },
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                let value = context.raw;
                                let label = '';
                                if (value == 1) {
                                    label = "USCAT";
                                } else if (value == 2) {
                                    label = "PERFECT";
                                } else if (value == 3) {
                                    label = "PREA UD";
                                }
                                return label;
                            },
                        },
                    },
                },
            },
        };

        polarChart = new Chart(ctx, config);
    }

    function updateCharts(selectedBtn) {
        if (selectedBtn === 'btn24h') {
            getData(polarChart, 'btn24h');
        } else if (selectedBtn === 'btn7d') {
            getData(polarChart, 'btn7d');
        }

    }

    $('#data24hours').click(function() {
        updateCharts('btn24h');
    });

    $('#data7days').click(function() {
        updateCharts('btn7d');
    });

    drawPolarChart();
});
