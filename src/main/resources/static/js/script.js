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
            url: "dht/getAllData",
            contentType: "application/json",
            success: function (data) {
                console.log(data);
            }
        });
    })

});
