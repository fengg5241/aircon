$(document).ready(function() {

    var GLOBAL_VAL = {
        imgPath: "../assets/img/",
        selectedGroup: {},
        groupid: "1",
        start_date: "",
        end_date: "",
        imageIdLayerMap: {},
        floorMap: null,
        groupMenuItemPrefix: "groupMenuItem",
        //selectedObject.type:grop/indoorUnit
        selectedObject: {
            id: -1,
            type: ""
        },
        connectedIDUId: null,
        clickUnitIcon: true,
        mapZoom: 20,
        isCompany: true,
        isAWS: false,
        dateMap: {
            1: "1st",
            2: "2nd",
            3: "3rd",
            11: "11st",
            12: "12nd",
            13: "13rd",
            21: "21st",
            22: "22nd",
            23: "23rd",
            31: "31st"
        }

    };


    // highlight menu
    menu('home');
    $(document).on("click", "#lengan", function(e) {
        $("#groupMenuItem61").addClass("active").click();
        return false;
    });

    $(document).on("click", "#bedok", function(e) {
        $("#groupMenuItem25").addClass("active").click();
        return false;
    });

    welcomeLoginUser();
    initHomeScreen();
    bindHomeScreenEvent();
    getalarmcount();
    // refresh interval of AlarmCount 
    setInterval(function() {
        getalarmcount();
    }, 1000 * 60 * 1);

    setInterval(function() {
        drawGraph("ecotree_graph");
        drawGraph("efficiency_graph");
    }, 60000 * 60 * 1);

    function getalarmcount() {
        $.axs("../notification/getNotificationCount.htm", {}, function(data) {
            for (var key in data) {
                var values = data[Object.keys(data)[0]];
                var value = data[Object.keys(data)[1]];
                var totalcount = values + value;
            }
            $("#criticalcount").text(values);
            $("#noncriticalcount").text(value);
            $("#totalcount").text(totalcount);

        });
    }

    //Eco-friendly Building List.
    drawGraph("ecotree_graph");
    drawGraph("efficiency_graph");

    function drawGraph(e) {
        if (e == "efficiency_graph") {
            $.axs('../stats/getEfficiencyonDashboard.htm', {}, function(data) {
                console.log(JSON.stringify(data));
                var groupName = [];
                var cop = [];
                var images = ['E10_30.png', 'E10_30.png', 'E10_30.png', 'E40.png', 'E50.png', 'E60.png', 'E70.png', 'E80.png', 'E90.png', 'E100.png', 'E110_150.png', 'E110_150.png', 'E110_150.png', 'E110_150.png', 'E110_150.png', 'E160_200.png', 'E160_200.png', 'E160_200.png', 'E160_200.png', 'E160_200.png'];
                var data_efficiency = [];
                if (data.errorMessage != 'Sorry. You have no privilege to see such information. ')
                    if (data.errorMessage != 'no.records.found') {
                        for (var i = 0, k = 0; i < data.compare_by.length; i++) {
                            groupName.push(data.compare_by[i]);
                            cop.push(data.value[i]);

                            for (var j = 1; j <= Math.ceil(cop[i] / 10); j++) {
                                data_efficiency[k] = {};
                                //console.log("cop: "+cop[i]+"<br/> j:"+ j +"<br/> i:"+i);
                                data_efficiency[k].x = i;
                                data_efficiency[k].y = j * 10;
                                data_efficiency[k].marker = {
                                    symbol: "url(" + GLOBAL_VAL.imgPath + images[j - 1] + ")"
                                };
                                k++;

                            }

                        }
                    }
                    //console.log(JSON.stringify(data_efficiency));
                    //console.log(JSON.stringify(groupName));

                if (typeof data.errorMessage === 'undefined') {
                    var chart = new Highcharts.Chart({
                        chart: {
                            renderTo: e,
                            marginRight: 10,
                            type: 'bar',
                            backgroundColor: 'white',
                            height: "385"
                        },
                        title: {
                            text: null
                        },
                        credits: {
                            enabled: false
                        },
                        legend: {
                            enabled: false
                        },
                        exporting: {
                            enabled: false
                        },
                        xAxis: [{
                            tickWidth: 0,
                            lineWidth: 0,
                            categories: groupName,
                            gridLineWidth: 1,
                            gridLineColor: "#B3B3B3",
                            offset: 15,
                            labels: {
                                style: {
                                    color: '#4d4d4d',
                                    "fontSize": "12px",
                                    "font-family": 'panasonic',
                                    "fontWeight": 'bold',
                                    "src": 'url(../fonts/PUDSansserifR.ttf)'
                                }
                            }
                        }],

                        yAxis: [{
                            min: 0,
                            max: 200,
                            tickWidth: 2,
                            lineWidth: 1,
                            gridLineWidth: 0,
                            pointStart: 0,
                            lineColor: '#B3B3B3',
                            tickColor: '#B3B3B3',
                            tickInterval: 10,
                            labels: {
                                format: '{value}%',
                                style: {
                                    color: '#4d4d4d',
                                    "fontWeight": "bold"

                                }

                            },

                            startOnTick: true,
                            title: {
                                text: null
                            }

                        }],

                        tooltip: {
                            shared: true,
                            useHTML: true,
                            formatter: function() {
                                if (false) {
                                    var s = '<span><b>' + this.x + '</b></span><table>';
                                    $.each(this.points, function() {
                                        s += '<tr><td align = "left" style = "color:' + this.series.color + ';">' + this.series.name + ': ' + '</td>' +
                                            '<td><b>' + this.y + '</b></td></tr>';
                                    });
                                    return s + '</table>';
                                } else {
                                    return false;
                                }
                            }
                        },

                        series: [{
                            data: data_efficiency,
                            type: 'scatter',
                            animation: false
                        }]
                    });
                } else {
                    $('#' + e).html(data.errorMessage);
                }
            });
        } else if (e == "ecotree_graph") {

            $.axs('../dashboard/getEfficiencyRating.htm', {},function(data) {
                console.log(JSON.stringify(data));
                var groupName = [];
                var cop = [];
                var images = ['co2_tree.png', 'co2_tree.png', 'co2_tree.png', 'co2_tree.png', 'co2_tree.png', 'co2_tree.png', 'co2_tree.png', 'co2_tree.png', 'co2_tree.png', 'co2_tree.png'];
                var data_efficiency = [];
                var factor = [];

                for (var i = 0, k = 0; i < data.length; i++) {
                    groupName.push(data[i].groupName);
                    cop.push(data[i].treesCount);

                    var length = getlength(cop[i]);

                    for (var f = 0; f < length; f++) {
                        if (f == 0) {
                            factor[i] = 1;
                        }
                        factor[i] = factor[i] * 10;
                    }

                    factor[i] = factor[i] / 8; // for max 8 tress at any point of time 

                    if (cop[i] < factor[i]) {
                        factor[i] = factor[i] / 4;
                    }


                }

                var max_factor = Math.round(Math.max.apply(Math, factor));

                for (var i = 0, k = 0; i < data.length; i++) {
                    for (var j = 1; j <= cop[i] / max_factor; j++) {
                        data_efficiency[k] = {};
                        //console.log("cop: "+cop[i]+"<br/> j:"+ j +"<br/> i:"+i);
                        data_efficiency[k].x = i;
                        data_efficiency[k].y = j * 10;
                        data_efficiency[k].marker = {
                            symbol: "url(" + GLOBAL_VAL.imgPath + images[j - 1] + ")"
                        };
                        k++;

                    }
                }

                if (typeof data.errorMessage === 'undefined') {
                    var chart = new Highcharts.Chart({
                        chart: {
                            renderTo: e,
                            type: 'bar',
                            backgroundColor: 'white',
                            animation: false
                        },

                        title: {
                            text: null
                        },
                        credits: {
                            enabled: false
                        },

                        legend: {
                            enabled: false
                        },
                        exporting: {
                            enabled: false
                        },
                        xAxis: [{
                            tickWidth: 0,
                            lineWidth: 0,
                            allowHTML: true,
                            useHTML: true,
                            categories: groupName,
                            labels: {
                                style: {
                                    color: '#4d4d4d',
                                    "fontSize": "12px",
                                    "font-family": 'panasonic',
                                    "fontWeight": 'bold',
                                    "src": 'url(../fonts/PUDSansserifR.ttf)'
                                }
                            },
                            gridLineWidth: 1,
                            gridLineColor: "#B3B3B3",
                            offset: 15,
                        }],

                        yAxis: [{
                            min: 10,
                            max: 100,
                            lineWidth: 1,
                            gridLineWidth: 0,
                            lineColor: '#B3B3B3',
                            tickColor: '#B3B3B3',
                            allowHTML: true,
                            useHTML: true,
                            tickInterval: 10,
                            enabled: false,
                            labels: {
                                format: '{value}%',
                                enabled: false
                            },
                            title: {
                                allowHTML: true,
                                useHTML: true,
                                text: "<b>" + " 1" + "<img src='../assets/img/trees.png' alt='' />" + "=" + max_factor + " trees needed to nullify amount of &nbsp;" + "<span>CO<sub>2</sub></span>" + "</b",
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
                            startOnTick: false
                        }],

                        tooltip: {
                            shared: true,
                            useHTML: true,
                            formatter: function() {
                                if (false) {
                                    var s = '<span><b>' + this.x + '</b></span><table>';
                                    $.each(this.points, function() {
                                        s += '<tr><td align = "left" style = "color:' + this.series.color + ';">' + this.series.name + ': ' + '</td>' +
                                            '<td><b>' + this.y + '</b></td></tr>';
                                    });
                                    return s + '</table>';
                                } else {
                                    return false;
                                }
                            }
                        },

                        series: [{
                            data: data_efficiency,
                            type: 'scatter',
                            animation: false
                        }]
                    });
                } else {
                    $('#' + e).html(data.errorMessage);
                }
            });
        }
    }



    /**
     * ******************* Home Screen part Event function end
     * ***************
     */
    function bindHomeScreenEvent() {
        bindBuildingMapEvent();
        bindAlarmNotificationEvent();
        bindWeatherEvent();
    }

    function bindBuildingMapEvent() {}

    function bindAlarmNotificationEvent() {
        $(document).on("click", "#table", function() {
            $("#table").hide();
            $("#graph").show();
            $("#graphs").hide();
            $("#tables").show();
        });
        $(document).on("click", "#graph", function() {
            $("#table").show();
            $("#graph").hide();
            $("#graphs").show();
            $("#tables").hide();
        });
    }

    function bindWeatherEvent() {
        $("#weatherlocation").change(function() {
            window.localStorage.setItem("selectedWeatherLocation", $(this).val());
            drawWeatherChart();
        });
    }
    /**
     * ******************* Home Screen part Event function end
     * ***************
     */
    /**
     * ******************* Monitor Control part logic function
     * start ***************
     */
    function _openPopup(indoorUnit) {
        var content = indoorUnit.serialNumber + '</br>' + indoorUnit.centralAddress;
        if (indoorUnit.state == "OFF") {
            content += "</br>OFF";
        } else {
            content += "</br>On:" + indoorUnit.mode + "</br>" + indoorUnit.temprature + "&deg;C"
            if (indoorUnit.alarmCode != null && indoorUnit.alarmCode != "") {
                content += "</br>" + indoorUnit.alarmCode;
            }
        }
        var middlelatlng = L.latLng(parseFloat(indoorUnit.svgMaxLatitude), (parseFloat(indoorUnit.svgMaxLongitude) + parseFloat(indoorUnit.svgMinLongitude)) / 2);
        var popup = L.popup().setLatLng(middlelatlng).setContent(content).openOn(GLOBAL_VAL.floorMap);
    }

    /**
     * ******************* Monitor Control part logic function
     * end ***************
     */
    // Event end
    function initHomeScreen() {
        // clear
        // Move modal to body
        // Fix Bootstrap backdrop issue with animation.css
        $('.modal').appendTo("body");
        //$("#acControlDisplay").addClass("hidden")
        //alert(GLOBAL_VAL.groupid);
        if (typeof GLOBAL_VAL.groupid == "undefined") {
            GLOBAL_VAL.groupid = "1";
        }

        if (GLOBAL_VAL.groupid === "25") {
            $("#weather").attr("src", GLOBAL_VAL.imgPath + "Weather_station.png");
            $("#notification-efficiency").attr("src", GLOBAL_VAL.imgPath + "PSCDemo_NOtification_bedok.png");
        } else if (GLOBAL_VAL.groupid === "61") {
            $("#weather").attr("src", GLOBAL_VAL.imgPath + "Weather_station_langen.png");
            $("#notification-efficiency").attr("src", GLOBAL_VAL.imgPath + "PSCDemo_NOtification_Langen.png");
        } else {
            $("#notification-efficiency").attr("src", GLOBAL_VAL.imgPath + "PSCDemo_NOtification.png");
        }

        /*updateData(GLOBAL_VAL.groupid);*/

        updateLocationWeather();
        // getFirstCompany then generateMap
        updateCompanyMap();
        // ajax then get alarm notification
        updateAlarmNotificationChart();

        // special config
        $(window).resize(function() {
            homeScreenData();
        });
        homeScreenData();
        $("#seer_table").mCustomScrollbar({
            scrollButtons: {
                enable: true
            },
            theme: "dark-2"
        });

    }

    function homeScreenData() {
        $("#total_kw_consumption").peity("line", {
            fill: "#ffffff",
            width: $("#total_kw_consumption_wrapper").width(),
            stroke: "#1ab394"
        });
        $("#total_co2_emission").peity("line", {
            fill: "#ffffff",
            width: $("#total_co2_emission_wrapper").width(),
            stroke: "#ed5565"
        });
    }
    /**
     * ******************* Home Screen part logic function
     * start*****************************
     */
    function updateLocationWeather() {
        displayNowTime();
        // refresh interval of current per minute 
        setInterval(function() {
            displayNowTime();
        }, 60000);
        // refresh interval of weather per hour 
        setInterval(function() {
            drawWeatherChart();
        }, 1000 * 60 * 60);

        $.axs("../home/getLocationList.htm", {}, function(data) {
            //			var locationList = getLocationList(data);
            var locationList = [];
            if (typeof(data) != "undefined" && data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    locationList.push([data[i].name, data[i].id]);
                }

            }
            populateLocationSelect(locationList);
            drawWeatherChart();
        });
    }

    function displayNowTime() {

        var d = new Date();
        var days = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
        var min = d.getMinutes(),
            hour = d.getHours(),
            day = days[d.getDay()],
            dayOfMonth = d.getDate(),
            month = d.getMonth() + 1,
            fullYear = d.getYear() - 100;
        min = min < 10 ? "0" + min : min;
        var ampm = hour < 12 ? 'am' : 'pm';
        hour = (hour % 12) || 12;
        hour = hour < 10 ? "0" + hour : hour;
        month = month < 10 ? "0" + month : month;
        $("#locationTime").text(day + " " + dayOfMonth + "/" + month + "/" +
            fullYear + "," + hour + ":" + min + ampm);

    }

    function getLocationList(data) {
        var locationList = [];

        if (typeof(data) != "undefined" && data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                var company = data[i];
                if (company.children != null) {
                    for (var j = 0; j < company.children.length; j++) {
                        var groupCountry = company.children[j];
                        if (groupCountry.groupTypeLevelName == "Country" || groupCountry.groupTypeLevelName == "State" || groupCountry.groupTypeLevelName == "Town") {
                            var countryName = groupCountry.groupName;

                            if (groupCountry.children != null) {
                                var hasState = false,
                                    hasTown = false;
                                for (var k = 0; k < groupCountry.children.length; k++) {
                                    var groupState = groupCountry.children[k];
                                    if (groupState.groupTypeLevelName == "State" || groupState.groupTypeLevelName == "Town") {

                                        if (groupState.groupTypeLevelName == "State") {
                                            var stateName = groupState.groupName;
                                            hasState = true;
                                            if (groupState.children != null) {
                                                for (var l = 0; l < groupState.children.length; l++) {
                                                    var groupTown = groupState.children[l];
                                                    if (groupTown.groupTypeLevelName == "Town") { //complete grouping
                                                        var townName = groupTown.groupName;
                                                        hasTown = true;
                                                        locationList.push([countryName + "," + townName, groupTown.groupId]);
                                                    }
                                                }
                                                //there is state under country, but no town children even though there are other children.
                                                if (hasState && !hasTown) {
                                                    locationList.push([countryName + "," + stateName, groupState.groupId]);
                                                }

                                            } else { //there is only state under country,no other children
                                                locationList.push([countryName + "," + stateName, groupState.groupId]);
                                            }
                                        } else { // Town     There is Town under country or state 
                                            var groupTown = groupState;
                                            var townName = groupTown.groupName;
                                            hasTown = true;
                                            locationList.push([countryName + "," + townName, groupTown.groupId]);
                                        }
                                    }
                                }

                                if (!hasState && !hasTown) { //no state or town under this country
                                    locationList.push([countryName, groupCountry.groupId]);
                                }
                            } else { //there is only country or state or town
                                locationList.push([countryName, groupCountry.groupId]);
                            }
                        }
                    }
                }
            }
        }

        return locationList;
    }

    function populateLocationSelect(locationList) {
        var optionHtml = "";
        if (locationList.length > 0) {
            locationList.sort(function(a, b) {
                var aName = a[0].toLowerCase();
                var bName = b[0].toLowerCase();
                return ((bName < aName) ? -1 : ((bName > aName) ? 1 : 0));
            });

            for (var i = 0; i < locationList.length; i++) {
                var locationName = locationList[i][0];
                var locationId = locationList[i][1];
                optionHtml += "<option value=" + locationId + ">" + locationName + "</option>";
            }
        }

        $("#weatherlocation").html(optionHtml);

        //restore selected location id
        var selectedLocationId = window.localStorage.getItem("selectedWeatherLocation");
        if (selectedLocationId != null && typeof(selectedLocationId) != "undefined") {
            if ($("#weatherlocation option[value='" + selectedLocationId + "']").length > 0) {
                $("#weatherlocation").val(selectedLocationId);
            };
        }
    }

    var icons = null;

    function drawWeatherChart(data) {
        var groupId = $("#weatherlocation").val();
        if (groupId > 0) {
            var currentTime = new Date();
            var currentTimeStr = getUTCHourTimeStrByDate(currentTime);
            var timezone = currentTime.getTimezoneOffset() / 60;
            $.axs("../home/getForecastInfo.htm", {
                "currentTime": currentTimeStr,
                "locationId": groupId
            }, function(data) {
                var firstDatePoint = null;

                var weatherIcon = null;
                var weatherStr = "";
                var currentTemp = "__";
                //store forecast time : temp
                var timeTempMap = {};
                var futureTempStr = "";
                var lastForecastTime = "";

                if (typeof(data) != "undefined" && data.length > 0) {
                    var length = data.length;
                    for (var i = 0; i < length; i++) {
                        var weatherObject = data[i];
                        var forecastTime = weatherObject.time;
                        if (i == 0) {
                            var firstDateYear = parseInt(forecastTime.substring(0, 4)),
                                firstDateMonth = parseInt(forecastTime.substring(5, 7)),
                                firstDateDate = parseInt(forecastTime.substring(8, 10)),
                                firstDateHour = parseInt(forecastTime.substring(11, 13)) - timezone;

                            firstDatePoint = new Date(firstDateYear, firstDateMonth - 1, firstDateDate, firstDateHour - timezone, 0, 0).getTime();
                        }
                        var temp = parseInt(weatherObject.temp);
                        timeTempMap[forecastTime] = temp;
                        if (i == length - 1) {
                            futureTempStr = weatherObject.future_weather;
                            lastForecastTime = forecastTime;
                            if (lastForecastTime == getUTCHourTimeStrByDate(currentTime)) {
                                weatherIcon = data[i].weatherIcon;
                                weatherStr = data[i].weather;
                                currentTemp = data[i].temp;
                            }
                        }
                    }
                }

                if (weatherIcon != null) {
                    icons = new Skycons({
                        "color": "skyblue"
                    });
                    icons.set("weatherIcon", weatherIcon);
                    icons.play();
                } else {
                    if (icons != null) {
                        icons.remove("weatherIcon");
                        $("#weatherIcon")[0].getContext('2d').clearRect(0, 0, 128, 128);
                    }
                }

                $("#locationWeather").text(weatherStr);
                $("#tempNumber").text(currentTemp);

                var currentYear = currentTime.getFullYear(),
                    currentMonth = currentTime.getMonth(),
                    currentDate = currentTime.getDate(),
                    currentHour = currentTime.getHours();

                var startPoint = new Date(currentYear, currentMonth, currentDate, currentHour - 21 - timezone, 0, 0).getTime();

                var dateChangePoint = new Date(currentYear, currentMonth, currentDate, 0 - timezone, 0, 0).getTime();
                var currentPoint = new Date(currentYear, currentMonth, currentDate, currentHour - timezone, 0, 0).getTime();
                var endPoint = new Date(currentYear, currentMonth, currentDate, currentHour + 3 - timezone, 0, 0).getTime();
                var startPointHour = currentHour - 21 + timezone;
                var forecastTimeArray = [];
                var displayTempArray = [];
                for (var i = 0; i < 22; i++) {
                    var utcDate = new Date(currentYear, currentMonth, currentDate, startPointHour + i, 0, 0);
                    var year = utcDate.getFullYear(),
                        month = utcDate.getMonth() + 1,
                        date = utcDate.getDate(),
                        hour = utcDate.getHours();
                    month = month < 10 ? "0" + month : month;
                    date = date < 10 ? "0" + date : date;
                    hour = hour < 10 ? "0" + hour : hour;
                    var forecastTimeStr = year + "-" + month + "-" + date + " " + hour + ":00:00";
                    forecastTimeArray.push(forecastTimeStr);

                    var temp = timeTempMap[forecastTimeStr];
                    if (typeof(temp) == "undefined") {
                        temp = null;
                    }
                    displayTempArray.push(temp);

                }

                if (lastForecastTime != "") {
                    var futureTempArray = futureTempStr.split(",");
                    var forecastTimeArrayLength = forecastTimeArray.length;
                    if (futureTempArray.length == 3) {
                        var index = forecastTimeArray.indexOf(lastForecastTime);
                        var firstFutureTemp = parseInt(futureTempArray[0]),
                            secondFutureTemp = parseInt(futureTempArray[1]),
                            thirdFutureTemp = parseInt(futureTempArray[2]);

                        if (index == forecastTimeArrayLength - 1) {
                            var currentTemp = displayTempArray[index];
                            displayTempArray[index] = {
                                y: currentTemp,
                                color: "#FF7F00"
                            };
                            displayTempArray.push({
                                y: firstFutureTemp,
                                color: "#808080"
                            });
                            displayTempArray.push({
                                y: secondFutureTemp,
                                color: "#808080"
                            });
                            displayTempArray.push({
                                y: thirdFutureTemp,
                                color: "#808080"
                            });
                        }
                    }
                }

                console.log(displayTempArray);
                $('#tempChart').highcharts({
                    credits: {
                        enabled: true,
                        text:"Weather Information Powered by Forecast",
                        href: 'http://forecast.io/'
                    },
                    exporting: {
                        enabled: false
                    },
                    title: {
                        text: '',
                        x: -20 //center
                    },
                    xAxis: {
                        plotBands: [{ // mark the weekend
                            color: '#93DCFF',
                            from: startPoint,
                            to: dateChangePoint,
                            id: 'plotband-1'
                        }, {
                            color: '#FCFFC5',
                            from: dateChangePoint,
                            to: currentPoint,
                            id: 'plotband-2'
                        }],
                        labels: {
                            overflow: 'justify',
                            formatter: function() {
                                var hour = Highcharts.dateFormat('%l', this.value);
                                var ampm = Highcharts.dateFormat('%P', this.value);
                                var hourStr = hour + ampm;
                                var dateStr = "";
                                if ((hour == 12 || hour == 1 || hour == 2) && ampm == "am") {
                                    var date = Highcharts.dateFormat('%e', this.value);
                                    dateStr = GLOBAL_VAL.dateMap[date];
                                    if (dateStr == null || typeof(dateStr) == "undefined") {
                                        dateStr = date + "th";
                                    }
                                    if (hour == 12) {
                                        hourStr = "";
                                    }
                                }

                                return dateStr + hourStr;
                            }
                        },
                        type: 'datetime',
                        tickInterval: 3 * 36e5,
                        min: startPoint,
                        max: endPoint
                    },
                    yAxis: {
                        title: {
                            text: '',
                            useHTML: true
                        },
                        tickInterval: 10,
                        plotLines: [{
                            value: 0,
                            width: 10,
                            color: '#808080'
                        }]
                    },
                    plotOptions: {
                        series: {
                            pointInterval: 3600000, // one hour
                            pointStart: startPoint,
                            zoneAxis: 'x',
                            zones: [{
                                value: currentPoint
                            }, {
                                value: currentPoint + 1,
                                color: '#FF7F00'
                            }, {
                                value: endPoint + 1,
                                color: '#808080',
                                dashStyle: 'dot'
                            }]
                        }
                    },

                    tooltip: {
                        valueSuffix: '&#176C',
                        useHTML: true
                    },
                    legend: {
                        enabled: false
                    },
                    series: [{
                    	name:$("#weatherlocation option:selected").text(),
                        data: displayTempArray,
                        type: "spline"
                    }]
                });
            }, "post");
        }
    }

    function getUTCHourTimeByDate(date) {
        var currentYear = date.getFullYear(),
            currentMonth = date.getMonth(),
            currentDate = date.getDate(),
            currentHour = date.getHours(),
            timezone = date.getTimezoneOffset() / 60;

        var utcDate = new Date(currentYear, currentMonth, currentDate, currentHour + timezone, 0, 0);
        return utcDate;
    }

    function getUTCHourTimeStrByDate(date) {
        var utcDate = getUTCHourTimeByDate(date);
        var year = utcDate.getFullYear(),
            month = utcDate.getMonth() + 1,
            date = utcDate.getDate(),
            hour = utcDate.getHours();

        month = month < 10 ? "0" + month : month;
        date = date < 10 ? "0" + date : date;
        hour = hour < 10 ? "0" + hour : hour;

        return year + "-" + month + "-" + date + " " + hour + ":00:00";
    }

    //format is like this:"2016-01-01 01:00:00"
    function getUTCHourTimeByUTCStr(str) {
        var currentYear1 = utcDate.getFullYear(),
            currentMonth1 = utcDate.getMonth(),
            currentDate1 = utcDate.getDate(),
            currentHour1 = utcDate.getHours();

        console.log(currentYear1 + "-" + currentMonth1 + "-" + currentDate1 + "-" + currentHour1);
    }

    function formatAMPM(date) {
        var hours = date.getHours();
        var ampm = hours >= 12 ? 'pm' : 'am';
        hours = hours % 12;
        hours = hours ? hours : 12; // the hour '0' should be '12'
        var strTime = hours + ampm;
        return strTime;
    }

    function addZero(i) {
        if (i < 10) {
            i = "0" + i;
        }
        return i;
    }

    function getFormattedDate(date) {
        var currentTimeStr = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();

        var h = addZero(date.getHours());
        var m = addZero(date.getMinutes());
        var s = addZero(date.getSeconds());
        currentTimeStr += " " + h + ":" + m + ":" + s;
        return currentTimeStr;
    }

    function updateCompanyMap() {
        $.axs("../home/getHomeSiteByCompanyId.htm", {}, function(data) {
            console.log(data);
            var map = _getMapBackground(data);
            L.tileLayer('', {
                maxZoom: 24,
                minZoom: 20,
                id: 'srihost.5d5783b8',
                accessToken: 'pk.eyJ1Ijoic3JpaG9zdCIsImEiOiI5YjBmYzdmZjYyNGNlMjljMmQ3NDIzM2ZhNTlkNDcyMyJ9.rJFx58Zxheg2VxZOC-vS6w'
            }).addTo(map);
            GLOBAL_VAL.floorMap = map;
            if (data.svgFileName != null) {

                var svgname = data.svgFileName;
                var imageBounds = [
                    [1.3223993770376097, 103.93094598781317],
                    [1.3213348234354438, 103.9319410873577]
                ];
                L.imageOverlay(GLOBAL_VAL.imgPath + "svg/" + svgname, imageBounds, {
                    id: "companyMap"
                }).addTo(map);
            }
            $("#homeMap .leaflet-control-attribution").empty();

        }, "post");
    }

    function generateStatisticsGraph() {
        var series = [];
        series = [{
            "name": "High",
            "color": "#BF616A",
            "type": "column",
            "yAxis": 0,
            "data": [10, 20, 30, 40, 50, 60, 70, 80, 90],
            "tooltip": {
                "valueSuffix": " Hours"
            }
        }, {
            "name": "Medium",
            "color": "#5B90BF",
            "type": "column",
            "yAxis": 0,
            "data": [10, 20, 30, 40, 50, 60, 70, 80, 90],
            "tooltip": {
                "valueSuffix": " Hours"
            }
        }, {
            "name": "Low",
            "color": "#D08770",
            "type": "column",
            "yAxis": 0,
            "data": [10, 20, 30, 40, 50, 60, 70, 80, 90],
            "tooltip": {
                "valueSuffix": " Hours"
            }
        }];
        var categories = [1, 2, 3, 4, 5, 6, 7, 8, 9];
    }


    // RD
    function updateAlarmNotificationChart() {
        var config = {
            '.chosen-select': {},
            '.chosen-select-deselect': {
                allow_single_deselect: true
            },
            '.chosen-select-no-single': {
                disable_search_threshold: 10
            },
            '.chosen-select-no-results': {
                no_results_text: 'Oops, nothing found!'
            },
            '.chosen-select-width': {
                width: "95%"
            }
        };
        for (var selector in config) {
            $(selector).chosen(config[selector]);
            // TODO ????
            // $("#group_type").chosen(config[selector]);
            // $("#group_types").chosen(config[selector]);
            // $("#parameter").chosen(config[selector]);
            // $("#stats_parameter").chosen(config[selector]);
        }
        $('.week-picker').weekpicker({
            startView: 2,
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            autoclose: true
        });
    }
    /**
     * ******************* Home Screen part logic function
     * end*****************************
     */
    /**
     * ******************* Monitor Control part logic function
     * start*****************************
     */
    // TR
    function updateAcContorl() {
        //$("#acControlDisplay").removeClass("hidden");
        var selecteTabHref = $("#acControlTab li.active a[data-toggle='tab']").attr("href");
        updateAcControlBySelectedTab(selecteTabHref);
    }

    function updateAcControlBySelectedTab(selecteTabHref) {
        if (selecteTabHref == "#visualization") {
            updateVisualizationCtrl()
        } else if (selecteTabHref == "#control") {
            updateSliderCtrl();
        } else if (selecteTabHref == "#threshold") {
            updateThresholdCtrl();
        } else if (selecteTabHref == "#prohibitions") {
            updateProhibitionsCtrl();
        } else {
            updateSliderCtrl();
        }
    }

    function updateVisualizationCtrl() {
        var selectedGroup = GLOBAL_VAL.selectedGroup;
        $.axs("../rc/getVisualizationRC.htm", {
            "id": selectedGroup.attr("group-id"),
            "idType": "group"
        }, function(data) {
            if (data != null && typeof(data) != "undefined") {
                console.log(data);
                var status, statusPercent;
                for (var ds in data.deviceStatus) {
                    status = ds;
                    statusPercent = data.deviceStatus[ds];
                }
                if (status == "ON") {
                    $('#v_status').lc_switch(statusPercent + '%', status).lcs_on();
                } else {
                    $('#v_status').lc_switch(statusPercent + '%', status).lcs_off();
                }
                var minTemp = maxTemp = '0';
                var tempListString = "";
                for (var dt in data.temp) {
                    if (tempListString == "") {
                        tempListString += data.temp[dt];
                    } else {
                        tempListString += "," + data.temp[dt];
                    }
                    if (minTemp == '0' || dt < minTemp) {
                        minTemp = dt;
                    }
                    if (dt > maxTemp) {
                        maxTemp = dt;
                    }
                }
                $("#v_temp_bar").text(tempListString);
                $("#v_temp_range").ionRangeSlider({
                    hide_min_max: true,
                    keyboard: true,
                    min: 16,
                    max: 30,
                    from: minTemp,
                    to: maxTemp,
                    step: 1,
                    type: 'double',
                    postfix: " °C",
                    disable: true
                });
                _showTempBarInVisulTab();
                _showModeStatusInTab(data);
            }
        });
    }

    function updateSliderCtrl() {
        var selectedObject = GLOBAL_VAL.selectedObject;
        $.axs("../rc/getControlRC.htm", {
            "id": selectedObject.id,
            "idType": selectedObject.type
        }, function(data) {
            var slider = $("#c_temp_range").data("ionRangeSlider");
            var curTemp = 30;
            if (data != null && typeof(data) != "undefined" && typeof(data.errorMessage) == "undefined") {
                console.log(data);
                if (data.powerStatus == "On") {
                    $("#c_status").attr("src", "../assets/img/On-Off-icon-Mo.png").addClass("stateOn");
                    curTemp = data.temperature;
                    if (slider == null || typeof(slider) == "undefined") {
                        _initTempSlider(curTemp);
                    } else {
                        slider.update({
                            from: curTemp
                        });
                    }
                    //Show mode
                    if (data.mode != null) {
                        _toggleSlideIconSelectedClass(data.mode);
                    }
                    //show fan speed
                    if (data.fanSpeed != null) {
                        var fanSpeed = data.fanSpeed;
                        var image = GLOBAL_VAL.fanspeedMap[fanSpeed].image;
                        $("#fan").attr('src', image);
                        GLOBAL_VAL.fanspeedMap["currentStatus"] = fanSpeed;
                    }
                    //show wind direction
                    if (data.windDirection != null) {
                        var windDirection = data.windDirection;
                        var image = GLOBAL_VAL.windMap[windDirection].image;
                        $("#wind").attr('src', image);
                        GLOBAL_VAL.windMap["currentStatus"] = windDirection;
                    }
                } else {
                    $("#c_status").attr("src", "../assets/img/On-Off-icon.png").removeClass("stateOn");
                    if (slider == null || typeof(slider) == "undefined") {
                        _initTempSlider(23);
                    } else {
                        slider.update({
                            from: 23
                        });
                    }
                    _toggleSlideIconSelectedClass("reset");
                    $("#fan").attr('src', "../assets/img/fanspeed-mix.png");
                    $("#wind").attr('src', "../assets/img/Flap-off.png");

                }
            } else {
                $("#c_status").attr("src", "../assets/img/On-Off-icon.png").removeClass("stateOn");
                if (slider == null || typeof(slider) == "undefined") {
                    _initTempSlider(23);
                } else {
                    slider.update({
                        from: 23
                    });
                }
                _toggleSlideIconSelectedClass("reset");
                $("#fan").attr('src', "../assets/img/fanspeed-mix.png");
                $("#wind").attr('src', "../assets/img/Flap-off.png");
            }
        });
    }

    function _initTempSlider(temperature) {
        $("#c_temp_range").ionRangeSlider({
            hide_min_max: true,
            keyboard: true,
            min: 16,
            max: 30,
            from: temperature,
            step: 1,
            postfix: " °C",
            onFinish: function(temp) {
                var selectedObject = GLOBAL_VAL.selectedObject;
                var timer = null;
                if (timer != null) {
                    clearTimeout(timer);
                }
                timer = setTimeout(function() {
                    _sendAjaxUpdateCtrlTemp(selectedObject, temp.input.prop("value"));
                }, 1000);
                return false;
            }
        });
    }

    function _sendAjaxUpdateCtrlTemp(selectedObject, temp) {
        if (!GLOBAL_VAL.isAWS) {
            $.axs("../rc/setControlRC.htm", {
                "id": selectedObject.id,
                "idType": selectedObject.type,
                "temp": temp
            }, function(data) {
                GLOBAL_VAL.clickUnitIcon = false;
                refreshMapAndACDetail();
            }, "post");
        } else {
            if (selectedObject.type == "group") {
                $.axs("../map/getMapData.htm", {
                    "id": selectedObject.id,
                    "idType": "group"
                }, function(data) {
                    var iduList = data.iduList;
                    if (typeof(iduList) != "undefined" && iduList.length > 0) {
                        var paramsList = [];
                        for (var i = 0; i < iduList.length; i++) {
                            var unitInfo = iduList[0].address.split("-");
                            var controllId = unitInfo[0];
                            var unitId = parseInt(unitInfo[2]).toString();
                            if (typeof(unitId) != "undefined") {
                                //    	    	        		paramsList.push({ "unitId":	 "2","value":temp});
                                paramsList.push({
                                    "unitId": unitId,
                                    "value": temp
                                });
                            }
                        }
                        if (paramsList.length > 0) {
                            //    						paramsList = [{ "unitId": "2","value":22},{ "unitId": "3","value":22},{ "unitId": "4","value":22}] 
                            _sendRemoteACControlRequest("settingTemperature", {
                                "units": paramsList
                            });
                        }
                    }
                });
            } else {
                var unitInfo = selectedObject.centralAddress.split("-");
                var controllId = unitInfo[0];
                var unitId = parseInt(unitInfo[2]).toString();
                _sendRemoteACControlRequest("settingTemperature", {
                    "units": [{
                        "unitId": unitId,
                        "value": temp
                    }]
                });
            }

        }
    }

    function updateThresholdCtrl() {
        $("#t_temp_range").ionRangeSlider({
            hide_min_max: true,
            keyboard: true,
            min: 16,
            max: 30,
            from: 16,
            to: 30,
            step: 1,
            type: 'double',
            postfix: " °C"
        });
    }

    function updateProhibitionsCtrl() {
        $("#t_g_temp_range").ionRangeSlider({
            hide_min_max: true,
            keyboard: true,
            min: 16,
            max: 30,
            from: 16,
            to: 30,
            step: 1,
            type: 'double',
            postfix: " °C"
        });
    }

    function _showTempBarInVisulTab() {
        var resized_width = $("#v_temp_wrapper").width();
        if (resized_width > 0) {
            $("#v_temp_bar").peity("bar", {
                fill: ["#d7d7d7"],
                width: resized_width
            });
        }
    }

    function _showModeStatusInTab(data) {
        $("#visualizationModeSet div.col-xs-5ths").attr("title", "0 Units");
        $("#visualizationModeSet a.mode").addClass("btn-default").removeClass("btn-primary");
        $("#visualizationModeSet a.vmode").addClass("btn-white").removeClass("btn-primary");
        var modeNamePercentDomIdMap = {
            HEAT: ["v_mode_heat_percentage", "v_mode_heat"],
            COOL: ["v_mode_cool_percentage", "v_mode_cool"],
            FAN: ["v_mode_fan_percentage", "v_mode_fan"],
            DRY: ["v_mode_dry_percentage", "v_mode_dry"],
            AUTO: ["v_mode_auto_percentage", "v_mode_auto"]
        }
        for (var dm in data.mode) {
            var percentDom = $("#" + modeNamePercentDomIdMap[dm][0]);
            var iconDom = $("#" + modeNamePercentDomIdMap[dm][1]);
            percentDom.text(data.mode[dm].percentage + "%").addClass("btn-primary").removeClass("btn-white").parent().attr("title", data.mode[dm].count + " Units");
            iconDom.addClass("btn-primary").removeClass("btn-default");
        }
    }
    /**
     * ******************* Monitor Control part logic function
     * end*****************************
     */
    /**
     * ********************* Common logic function start
     * ******************************
     */

    function showGraph(id, date, interval, day, angle, direction, text1, text2, yaxisformatlable2, stacking, opposite, series, categories) {
        $('#' + id).highcharts({
            credits: {
                enabled: false
            },
            chart: {
                zoomType: 'x',
                height: 210,
                plotBorderWidth: 1
            },
            title: {
                text: null
            },
            xAxis: {
                categories: categories,
                type: date,
                tickInterval: interval,
                dateTimeLabelFormats: {
                    day: day
                },
                labels: {
                    style: {
                        color: '#7F7F7F',
                        fontWeight: 'bold'
                    },
                    rotation: angle,
                    align: direction
                }
            },
            yAxis: [{
                labels: {
                    format: '{value}',
                    style: {
                        color: '#7F7F7F',
                        fontWeight: 'bold'
                    }
                },
                title: {
                    text: text1,
                    style: {
                        color: '#7F7F7F',
                        fontWeight: 'bold'
                    }
                }
            }, {
                title: {
                    text: text2,
                    style: {
                        color: '#7F7F7F',
                        fontWeight: 'bold'
                    }
                },
                labels: {
                    format: yaxisformatlable2,
                    style: {
                        color: '#7F7F7F',
                        fontWeight: 'bold'
                    }
                },
                opposite: true
            }],
            tooltip: {
                shared: true
            },
            plotOptions: {
                column: {
                    stacking: stacking
                }
            },
            series: series,
            legend: {
                align: 'center',
                verticalAlign: 'top'
            }
        });
        setTimeout(function() {
            $('#' + id).removeClass("animated fadeInRight");
        }, 1000);
    }

    function welcomeLoginUser() {
        setTimeout(function() {
            toastr.options = {
                closeButton: true,
                progressBar: true,
                showMethod: 'slideDown',
                timeOut: 2000
            };
            toastr.success($('#useridname').val(), 'Welcome to Panasonic Smart Cloud!');
        }, 1000);
    }

    function _getMapBackground(data) {
        var map = GLOBAL_VAL.floorMap;
        if (GLOBAL_VAL.floorMap == null) {
            map = L.map('homeMap', {
                zoomControl: false
            }).setView([1.32186844, 103.93149555], GLOBAL_VAL.mapZoom);
            map.setMaxBounds([
                [1.3215003655464723, 103.93099598781317],
                [1.3223521814812313, 103.9319410873577]
            ]);

            // new L.Control.Zoom({ position: 'bottomright' }).addTo(map);
            var siteArray = []
            for (var i = 0; i < data.sites.length; i++) {
                var site = data.sites[i];
                if (site.svgMaxLatitude != 0 && site.svgMaxLongitude != 0 && site.svgMinLatitude != 0 && site.svgMinLongitude != 0) {
                    siteArray.push(site);
                }
            }
                map.on('click', function(e) {
                    console.log("e.latlng.lat:" + e.latlng.lat + "~~~~~~~~~~~~~~" + "e.latlng.lng:" + e.latlng.lng);
                    if (siteArray.length > 0) {
                    var today = new Date();
                    var dd = today.getDate();
                    var mm = today.getMonth() + 1;
                    var yyyy = today.getFullYear()
                    today = dd + '/' + mm + '/' + yyyy;

                    for (var i = 0; i < siteArray.length; i++) {
                        var validSite = siteArray[i];
                        if (e.latlng.lat >= validSite.svgMinLatitude && e.latlng.lat < validSite.svgMaxLatitude &&
                            e.latlng.lng >= validSite.svgMinLongitude && e.latlng.lng < validSite.svgMaxLongitude) {
                            $.axs("../notification/getNotificationDetails.htm", {
                                "json_request": JSON.stringify({
                                    "id": [validSite.groupId],
                                    "idType": "group",
                                    "severity": ['critical', 'non-critical'],
                                    "alarmType": 'all',
                                    "status": ['new'],
                                    "alarmOccurredStartDate": '01/01/2012',
                                    "alarmOccurredEndDate": today,
                                    "addCustName": "no"
                                })
                            }, function(notiData) {

                                var alarmCountArray = _getAlarmCount(notiData);

                                var location = {
                                    maxlatitude: validSite.svgMaxLatitude,
                                    maxlongitude: validSite.svgMaxLongitude,
                                    minlongitude: validSite.svgMinLongitude
                                }
                                _openPopup(validSite.name == null ? "" : validSite.name, alarmCountArray[0], alarmCountArray[1], location);
                            });
                        }
                    }
                    }
                });
            


        } else {
            for (var domId in GLOBAL_VAL["imageIdLayerMap"]) {
                var imageLayer = GLOBAL_VAL["imageIdLayerMap"][domId];
                if (map.hasLayer(imageLayer)) {
                    map.removeLayer(imageLayer);
                }
            }
            GLOBAL_VAL["imageIdLayerMap"] = {};
            map.closePopup();
        }
        return map;
    }

    function _getAlarmCount(data) {
        var critialUnitIdList = [],
            nonCritialUnitIdList = [];
        var criticalCount = 0,
            nonCriticalCount = 0;
        if (data != null && typeof(data) != "undefined" && typeof(data.errorMessage) == "undefined") {
            var notificationList = data.notificationList;
            for (var i = 0; i < notificationList.length; i++) {
				var alarm = notificationList[i];
				var iduId = alarm.iduId,oduId = alarm.oduId,caId = alarm.caId;
				var unitId = "";
				if (typeof iduId != "undefined") {
					unitId += "idu" + iduId;
				}else if(typeof oduId != "undefined"){
					unitId += "odu" + oduId;
				}else if(typeof caId != "undefined"){
					unitId += "ca" + caId;
				}
				if (alarm.severity == "Critical" && critialUnitIdList.indexOf(unitId) == -1) {
					critialUnitIdList.push(unitId);
					criticalCount += 1;
				}else if(alarm.severity == "Non-critical" && nonCritialUnitIdList.indexOf(unitId) == -1){
					nonCritialUnitIdList.push(unitId);
					nonCriticalCount += 1;
				}
			}
    	}
    	return [criticalCount,nonCriticalCount];
    }

    function _openPopup(siteName, ciriticalCount, nonCriticalCount, location) {

        var content = '<div style="width:150px;"><div class="title text-center ">' + siteName + '</div>' +
            '<div class="settingBody">' + '<p style="padding:0;margin:0px 5px;">Alarm Status:</p>' +
            '<div class="settingContent">' +
            '<div class="row " style="margin-bottom:10px;">' +
            '<div class="col-xs-12 ">' +
            '<div class="greyBackGroundColorPopup" style="float:left;width:100%">' +
            '<div class="col-xs-2 nopadding">' +
            '<img alt="image"' +
            'src="../assets/img/HomeScreen_Alarm_icon_critical.png">' +
            '</div>' +
            '<div class="col-xs-10" style="padding-right: 0px;padding-left:30px;">' +
            '<text style="font-size:15px">Critical</text>' +
            '<br> <label id="noncriticalcount">' + ciriticalCount + '</label> &nbsp; Units' +
            '</div></div>' +
            '</div>' +
            '</div>' +
            '<div class="row">' +
            '<div class="col-xs-12" style="float:left;width:100%">' +
            '<div class="greyBackGroundColorPopup" style="float:left;width:100%">' +
            '<div class="col-xs-2 nopadding ">' +
            '<img alt="image"' +
            'src="../assets/img/HomeScreen_Alarm_icon_noncritical.png">' +
            '</div>' +
            '<div class="col-xs-10 " style="padding-right: 0px;padding-left:30px;">' +
            '<text style="font-size:15px">Non-Critical</text>' +
            '<br> <label id="noncriticalcount">' + nonCriticalCount + '</label> &nbsp; <b>Units</b>' +
            '</div>' +
            '</div></div>' +
            '</div>' +
            '</div>' +
            '</div></div>';

        var middlelatlng = L.latLng(parseFloat(location.maxlatitude), (parseFloat(location.maxlongitude) + parseFloat(location.minlongitude)) / 2);
        var popup = L.popup().setLatLng(middlelatlng).setContent(content).openOn(GLOBAL_VAL.floorMap);
    }

    // Added By Ravi
    function getlength(number) {
        return number.toString().length;
    }

    /**
     * ********************* Common logic function end
     * ******************************
     */
});