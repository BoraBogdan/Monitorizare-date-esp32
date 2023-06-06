$(document).ready(function () {
    console.log("Loaded jQuery");
    moment.locale('ro');

    let lineChart = null;
    let polarChart = null;

    $.fn.dataTable.moment( 'DD MMMM YYYY, HH:mm:ss' );
    $("#example").DataTable({
        ajax: {
            url: "dht/getAllData",
            dataSrc: ''
        },
        columns: [
            {data: "airHumidity"},
            {data: "temperature"},
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

    $("#soil").DataTable({
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

    $(".button").click(function () {
        let elementId = $(this).attr("id-elem");

        $.ajax({
            type: "POST",
            url: "dht/addData",
            contentType: "application/json",
            data:
                JSON.stringify(
                    {
                        "airHumidity": 15,
                        "temperature": 15,
                        "microcontrollerID": 15
                    }
                ),
            success: function (data) {
                $("#textAppear").show();
                $("#text-button").text(data.message);
            }
        });
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

    async function getData(chart1, chart2, selectedBtn) {
        const result = await generateSoilData();

        const data24h = result.data24hours;
        const data7d = result.data7days;
        if (selectedBtn === 'btn24h') {
            chart1.data.labels = data24h.xData;
            chart1.data.datasets[0].data = data24h.yData;
            chart1.update();

            chart2.data.labels = data24h.xData;
            chart2.data.datasets[0].data = formatHumidityData(data24h.yData);
            chart2.update();
        } else if (selectedBtn === 'btn7d') {
            chart1.data.labels = data7d.xData;
            chart1.data.datasets[0].data = data7d.yData;
            chart1.update();

            chart2.data.labels = data7d.xData;
            chart2.data.datasets[0].data = formatHumidityData(data7d.yData);
            chart2.update();
        }
    }

    $("#load-data").click(function () {
        $.ajax({
            type: "GET",
            url: "soil/getAvgLast24Hours",
            contentType: "application/json",
            success: function (data) {
                let xData = [];
                let yData = [];

                data.forEach((data) => {
                    // const formatedDate = moment(data.time).format("DD.MM.YYYY");
                    const formatedDate = moment(data.time).format("HH:mm")
                    xData.push(formatedDate);
                    yData.push(data.soilHumidityAvg);
                });
                // drawLineChart(xData, yData);
                // drawPolarChart(xData, yData);
                console.log(xData);
                console.log(yData);
            }
        });
    });

    function drawLineChart() {
        const ctx = document.getElementById('myChart').getContext('2d');
        let gradient = ctx.createLinearGradient(0, 0, 0, 400);
        gradient.addColorStop(0, 'rgba(58, 123, 213, 1)');
        gradient.addColorStop(1, 'rgba(0, 210, 255, 0.3)');
        const data = {

            datasets: [{
                data: [],
                label: "Soil Humidity - last 7 days",
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
        const ctx = document.getElementById('myPolarArea').getContext('2d');

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
