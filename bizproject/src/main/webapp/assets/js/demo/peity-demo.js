$(function() {
    $("span.pie").peity("pie", {
        fill: ['#1ab394', '#d7d7d7', '#ffffff']
    })

    $(".line").peity("line",{
        fill: '#1ab394',
        stroke:'#169c81',
    })

    $(".bar").peity("bar", {
        fill: ["#1ab394", "#d7d7d7"]
    })

    $(".bar_dashboard").peity("bar", {
        fill: ["#1ab394", "#d7d7d7"],
        width:100
    })

	/*$(".v_temp_bar").peity("bar", {
        fill: ["#d7d7d7"],
		width: $("#v_temp_wrapper").width()
    });
	
	$(document).on('shown.bs.tab', 'a[href="#control"]', function(e) {
		$(".c_temp_bar").peity("bar", {
	        fill: ["#d7d7d7"],
			width: $("#c_temp_wrapper").width()
	    });
	});
	
	$(document).on('shown.bs.tab', 'a[href="#threshold"]', function(e) {
		$(".t_temp_bar").peity("bar", {
	        fill: ["#d7d7d7"],
			width: $("#t_temp_wrapper").width()
	    });
		
		$(".t_g_temp_bar").peity("bar", {
	        fill: ["#d7d7d7"],
			width: $("#t_g_temp_wrapper").width()
	    });
	});
	
	// updating width on resize
	$( window ).resize(function() {
		resize();
	});*/

    var updatingChart = $(".updating-chart").peity("line", { fill: '#1ab394',stroke:'#169c81', width: 64 })

    setInterval(function() {
        var random = Math.round(Math.random() * 10)
        var values = updatingChart.text().split(",")
        values.shift()
        values.push(random)

        updatingChart
            .text(values.join(","))
            .change()
    }, 1000);

});
