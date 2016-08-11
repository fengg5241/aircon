$(document).ready(
    function() {
        var GLOBAL_VAL = {
            start_date: "",
            end_date: "",
            enddate: ""
        };

        menu('home');

        $.fn.datepicker.defaults.format = "dd/mm/yyyy";
        GLOBAL_VAL.end_date = GLOBAL_VAL.start_date = new Date();
        GLOBAL_VAL.enddate = new Date();
        GLOBAL_VAL.enddate.setDate(GLOBAL_VAL.end_date.getDate() - 1);
        $("#end").val($.datepicker.formatDate("dd/mm/yy", GLOBAL_VAL.enddate));
        GLOBAL_VAL.start_date.setDate(GLOBAL_VAL.end_date.getDate() - 7);
        $("#start").val($.datepicker.formatDate("dd/mm/yy", GLOBAL_VAL.start_date));
        $('.input-daterange').datepicker({
            keyboardNavigation: false,
            forceParse: false,
            autoclose: true,
            endDate: GLOBAL_VAL.enddate,
            startDate: "-3y"
        })

        //Onloadpage graph view.
        updateEnergygraph();

        //Refreshing graph every hour.
        setInterval(function() {
            updateEnergygraph();
        }, 36000000);


        $(document).on("click", "#energy", function(e) {
            updateEnergygraph();
        });

        function updateEnergygraph() {
            GLOBAL_VAL.start_date = $('#energy_home_datepicker > input[name="start"]').val();
            var splitted = GLOBAL_VAL.start_date.split('/');
            var startDate = splitted[2] + "-" + splitted[1] + "-" + splitted[0];
            GLOBAL_VAL.end_date = $('#energy_home_datepicker > input[name="end"]').val();
            var splitted = GLOBAL_VAL.end_date.split('/');
            var endDate = splitted[2] + "-" + splitted[1] + "-" + splitted[0];
            /*data_send = "{'startDate': '2015-10-01','endDate':'2015-10-26' }";*/
            data_send = "{'startDate': '" + startDate + "','endDate': '" + endDate + "'}";
            /*console.log(data_send);*/
            $.axs("../stats/energyConsumption.htm", {
                "json_request": data_send
            }, function(data) {
                console.log("Starting  " + JSON.stringify(data));
                var colors = [];
                var color_data = ['#067F71', '#1281E8']; //console.log(data.total_consumption.length+" data length "+data.dates.length);
                // Commited as average consumption is already caliculated at back-end
                //data.average_consumption = 0;
                if (data.errorMessage !== 'user.group.not.available') {
                    if (data.errorMessage !== 'no.records.found') {
                        if (data.errorMessage !== 'request.resource.not.available') {
                            for (i = 0; i < data.total_consumption.length; i++) {
                                if (typeof data.total_consumption.length !== undefined) {
                                    if (i != data.total_consumption.length - 1) {
                                        colors.push(color_data[0]);
                                        //data.average_consumption += data.total_consumption[i]
                                    } else {
                                        //console.log("hi"+i);
                                        //data.total_consumption[i] = 1000;
                                        colors.push(color_data[1]);
                                    }
                                }
                            }
                        }
                    }
                }
                
                /*if (data.errorMessage !== 'user.group.not.available') {
                    if (data.errorMessage !== 'no.records.found') {
                        if (data.errorMessage !== 'request.resource.not.available') {
                            if (typeof data.total_consumption.length !== undefined) {
                                data.average_consumption = data.average_consumption / (data.total_consumption.length - 1);
                            }
                        }
                    }
                }*/

                var scroll = false;
                if (data.errorMessage !== 'user.group.not.available') {
                    if (data.errorMessage !== 'no.records.found') {
                        if (data.errorMessage !== 'request.resource.not.available') {
                            if (typeof data.total_consumption.length !== undefined) {
                                if (data.dates.length > 12) {
                                    scroll = true;
                                } else {
                                    scroll = false;
                                }
                            }
                        }
                    }
                }


                console.log("ending  " + JSON.stringify(data));
                $('#energy_graph').highcharts({
                    credits: {
                        enabled: false
                    },
                    legend: {
                        enabled: false
                    },
                    chart: {
                        backgroundColor: 'rgba(255, 255, 255, 0.1)',
                        type: 'column',
                        zoomType: 'xy',
                        height: 310,
                        plotBorderWidth: 0,
                        animation: {
                            duration: 1000,
                            easing: 'easeOutBounce'
                        },
                        style: {
                            fontFamily: 'panasonic'
                        }
                    },
                    exporting: {
                        enabled: false
                    },
                    scrollbar: {
                        enabled: scroll,
                        barBackgroundColor: '#666666',
                        barBorderRadius: 0,
                        barBorderWidth: 0,
                        buttonBackgroundColor: '#B3B3B3',
                        buttonBorderWidth: 0,
                        buttonArrowColor: '#B3B3B3',
                        buttonBorderRadius: 0,
                        rifleColor: '#B3B3B3',
                        trackBackgroundColor: '#B3B3B3',
                        trackBorderWidth: 0,
                        trackBorderColor: '#B3B3B3',
                        trackBorderRadius: 0,
                        height: 10
                    },
                    title: {
                        text: null
                    },
                    xAxis: {
                        categories: data.dates,
                        labels: {
                            style: {
                                color: '#4d4d4d',
                                "fontSize": "12px",
                                "font-family": 'panasonic',
                                "fontWeight": 'bold',
                                "src": 'url(../fonts/PUDSansserifR.ttf)'
                            }
                        }
                    },
                    yAxis: {
                        title: {
                            text: "Power Consumption (kWh)",
                            style: {
                                color: '#4d4d4d',
                                "fontSize": "12px",
                                "font-family": 'panasonic',
                                "fontWeight": 'bold',
                                "src": 'url(../fonts/PUDSansserifR.ttf)'
                            }
                        },
                        labels: {
                            style: {
                                format: '{value}',
                                color: '#4d4d4d',
                                "fontSize": "12px",
                                "font-family": 'panasonic',
                                "fontWeight": 'bold',
                                "src": 'url(../fonts/PUDSansserifR.ttf)'
                            }
                        },
                        plotLines: [{
                            value: data.average_consumption,
                            color: 'black',
                            dashStyle: 'shortdash',
                            width: 2,
                            label: {
                                text: 'Average',
                                align: "left"
                            },
                            zIndex: 1000,
                            series: {
                                states: {
                                    hover: {
                                        enabled: false
                                    }
                                }
                            }
                        }]
                    },
                    tooltip: {
                        pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b><br/>',
                        shared: true,
                        style: {
                            color: '#4d4d4d',
                            "fontSize": "12px",
                            "font-family": 'panasonic',
                            "fontWeight": 'bold',
                            "src": 'url(../fonts/PUDSansserifR.ttf)'
                        },
                    },
                    plotOptions: {
                        column: {
                            colorByPoint: true
                        }
                    },
                    series: [{
                        name: 'Energy',
                        type: 'column',
                        data: data.total_consumption,
                        tooltip: {
                            valueSuffix: ' kWh',

                        },
                        colors: colors,
                        states: {
                            hover: {
                                enabled: false
                            }
                        }

                    }]


                });
            });
        }
    });