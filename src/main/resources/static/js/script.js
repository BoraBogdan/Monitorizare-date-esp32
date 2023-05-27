$(document).ready(function () {
    console.log("Loaded jQuery");

    moment.locale('ro');
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

    $("#load-data").click(function () {
        $.ajax({
            type: "GET",
            url: "soil/getAvgLast7Days",
            contentType: "application/json",
            success: function (data) {
                let xData = [];
                let yData = [];

                data.forEach((data) => {
                    xData.push(data.time);
                    yData.push(data.soilHumidityAvg);
                });
                drawLineChart(xData, yData);
                console.log(xData);
                console.log(yData);
            }
        });
    });

    function drawLineChart(labels, values) {
        const ctx = document.getElementById('myChart').getContext('2d');
        let gradient = ctx.createLinearGradient(0, 0, 0, 400);
        gradient.addColorStop(0, 'rgba(58, 123, 213, 1)');
        gradient.addColorStop(1, 'rgba(0, 210, 255, 0.3)');
        const data = {
            labels,
            datasets: [{
                data: values,
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

        const myChart = new Chart(ctx, config);
    }

});
