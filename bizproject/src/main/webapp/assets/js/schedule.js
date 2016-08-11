$(document).ready(function() {
	
	var GLOBAL_VAL = {
			scrollY:"660px",
			overviewTable:null
	};
	
	menu('schedule');

	var COMMON = {
			FUNC:{
				getTDHtml:function(string, className){
					if (typeof className != "undefined") {
						return "<td class='" + className + "'>" + string + "</td>";
					}else {
						return "<td>" + string + "</td>";
					}
				}
			},
			EVENT:{
				bindTabEvent:function(){
					$(document).on("click", "#inner a[data-toggle='tab']", function() {
			            var target = $(this).attr("href");
			            if (target == "#scheduleOverviewTabLink") {
			            	GLOBAL_VAL.ODUDetailTable.columns.adjust().draw();
						} 
			            $(target + " .dataTables_scrollBody").mCustomScrollbar("update");
						$(target + " .dataTables_scroll").resize();
			        });
				}
			}
	};
	
    var OVERVIEW = {
    		FUNC:{
    			initOverviewTable:function(detailHtml){
                    if (GLOBAL_VAL.overviewTable != null) {
                    	GLOBAL_VAL.overviewTable.destroy();
            		}
                    $("#scheduleOverviewTbody").html(detailHtml);
                    
                    GLOBAL_VAL.overviewTable = $('#scheduleOverviewTable').DataTable({    
                        "paging":   false,
                        "filter": true,
                        "info" : false,
                        "responsive":   true,
                        "deferRender":    true,
                        "scrollCollapse": false,
                        "scroller": true,
                        "stateSave": true,
                        "scrollY":  GLOBAL_VAL.scrollY,            
                        "language": {
            			          "searchPlaceholder": "Search"
            			 },
               			"search": {"caseInsensitive": false}
                  
                    });
            	},
            	initMonthViewCalendar:function(){
            		$('#monthViewCalendar').fullCalendar({
            			firstDay:1,
            			height:350,
            			contentHeight:'auto',
            			header: {
            				left: '',
            				center: 'prev,title,next',
            				right: ''
            			},
            			defaultDate: new Date(),
            			editable: true,
            			droppable: true, // this allows things to be dropped onto the calendar,
            			eventRender: function(event, element) {
            			},
            			eventReceive: function(event) {
            				
            			},
            			eventDrop: function(event, delta, revertFunc) {
            			},
            			eventOverlap:function(stillEvent, movingEvent){
            				$('#monthViewCalendar').fullCalendar('removeEvents',stillEvent._id);
            				return true;
            			}
            		});
            	},
            	getRowHtml:function(rowJson){
            		return  COMMON.FUNC.getTDHtml(rowJson.siteName)+COMMON.FUNC.getTDHtml(rowJson.topSite)+COMMON.FUNC.getTDHtml(rowJson.monMode,"monMode")+
		            		COMMON.FUNC.getTDHtml(rowJson.tueMode,"tueMode")+COMMON.FUNC.getTDHtml(rowJson.wedMode,"wedMode")+COMMON.FUNC.getTDHtml(rowJson.thuMode,"thuMode")+
		            		COMMON.FUNC.getTDHtml(rowJson.friMode,"friMode")+COMMON.FUNC.getTDHtml(rowJson.satMode,"satMode")+COMMON.FUNC.getTDHtml(rowJson.sunMode,"sunMode")+"</tr>";
            	}
			},
			EVENT:{
				makeModeItemDraggble:function(){
					$('#editPlanModal .fc-event').each(function() {

						// store data so the calendar knows to render an event upon drop
						$(this).data('event', {
							title: $.trim($(this).attr("data-shortname")), // use the element's text as the event title
							stick: true // maintain when user navigates (see docs on the renderEvent method)
						});

						// make the event draggable using jQuery UI
						$(this).draggable({
							zIndex: 999,
							revert: true,      // will cause the event to go back to its
							revertDuration: 0  //  original position after the drag
						});

					});
				},
				afterEditPlanModalShown:function(){
					$('#editPlanModal').on('shown.bs.modal', function (e) {
						OVERVIEW.FUNC.initMonthViewCalendar();
					});
				},
				makeDateRangeCalendarDroppable:function(){
					$('#dateRangeCalendar .fc-day').droppable({
					      drop: function( event, ui ) {
					    	  var index = $("#dateRangeCalendar .fc-day").index(this);
					    	  var newTitle = ui.draggable.attr("data-shortname");
					    	  var relatedEvent = $("#dateRangeCalendar .fc-event-container").eq(index);
					    	  relatedEvent.find(".fc-title").text(newTitle);
					    	  $(this).removeClass("fc-highlight");
					      },
					      over: function(event, ui) {
					    	  console.log(event);
					    	  $(this).addClass("fc-highlight");
					      },
					      out: function(event, ui) {
					    	  console.log(event);
					    	  $(this).removeClass("fc-highlight");
					      }
					});
				},
				makeAutomaticSettingCalendarDroppable:function(){
					$('#automaticSettingCalendar .fc-day').droppable({
					      drop: function( event, ui ) {
					    	  var index = $("#automaticSettingCalendar .fc-day").index(this);
					    	  var newTitle = ui.draggable.attr("data-shortname");
					    	  var relatedEvent = $("#automaticSettingCalendar .fc-event-container").eq(index);
					    	  relatedEvent.find(".fc-title").text(newTitle);
					    	  $(this).removeClass("fc-highlight");
					      },
					      over: function(event, ui) {
					    	  console.log(event);
					    	  $(this).addClass("fc-highlight");
					      },
					      out: function(event, ui) {
					    	  console.log(event);
					    	  $(this).removeClass("fc-highlight");
					      }
					});
				},
				makeDailyScheduleTDCanBeSelectable:function(){
					$(document).on("change", "#scheduleOverviewTable input", function(e) {
						//should get mode list from database;
						var modeListHtml = "<select class='modelist'>" +
								"<option value='Weekend Holiday'>Weekend Holiday</option>" +
								"<option value='Weekday 1'>Weekday 1</option>" +
								"<option value='Weekday 2'>Weekday 2</option>" +
								"<option value='Weekday 3'>Weekday 3</option>" +
								"<option value='Weekday 4'>Weekday 4</option>" +
								"<option value='Weekday 5'>Weekday 5</option>" +
								"<option value='Weekend Winter'>Weekend Winter</option></select>"
						
						var $previousSelectedItem = $("#scheduleOverviewTable input.editMode");
						if ($previousSelectedItem.length > 0) {
							//revert it back to read mode
							$previousSelectedItem.removeClass("editMode");
							var $preTr = $previousSelectedItem.parent().parent();
							for (var i = 3; i < 10; i++) {
								var $td = $preTr.find("td:eq("+i+")");
								var currentMode = $td.find("select").val();
								$td.text(currentMode);
							}
						}
						
						//convert to edit mode
						$(this).addClass("editMode");
						var $tr = $(this).parent().parent();
						for (var i = 3; i < 10; i++) {
							var $td = $tr.find("td:eq("+i+")");
							var currentMode = $td.text();
							$td.html(modeListHtml);
							$td.find("select").val(currentMode);
						}
					});
				},
				bindCopyFunction:function(){
					$("#copyButton").click(function(){
						var $checkedRadio = $("#scheduleOverviewTable input:checked");
						if ($checkedRadio.length > 0) {
							$("#copyButton").data("copiedRow",$checkedRadio.parent().parent());
						}
					});
				},
				bindPasteFunction:function(){
					$("#pasteButton").click(function(){
						var $checkedRadio = $("#scheduleOverviewTable input:checked");
						if ($checkedRadio.length > 0) {
							var $copiedRow = $("#copyButton").data("copiedRow");
							var $pasteRow = $checkedRadio.parent().parent();
							for (var i = 1; i < 10; i++) {
								var $copyTd = $copiedRow.find("td:eq("+i+")");
								var $pasteTd = $pasteRow.find("td:eq("+i+")");
								
								var copyText = $copyTd.text();
								if (i<=2) {
									$pasteTd.text(copyText);
								}else {
									$pasteTd.find("select").val(copyText);
								}
							}
						}
					});
				}
			}
        };
    
    initSchedule();
    bindScheduleEvent();
    
    function initSchedule(){
        // Move modal to body
        // Fix Bootstrap backdrop issue with animation.css
        $('.modal').appendTo("body");

        if(!hasSelectedGroupInSession()){
        	OVERVIEW.FUNC.initOverviewTable(null);
//        	return;
        }
        
    	initModeDirectory();
    	initScheduleOverview();
    	initScheduleSummary();
    	initTimeSettings();
    }
    
    function initModeDirectory(){
    	
    }
    
    

    
    function initScheduleOverview(){
    	var jsonArray = [{
    		siteName:"Level 1",
    		topSite:"Block 1",
    		monMode:"Weekend Holiday",
    		tueMode:"Weekday 1",
    		wedMode:"Weekday 2",
    		thuMode:"Weekday 3",
    		friMode:"Weekday 4",
    		satMode:"Weekday 5",
    		sunMode:"Weekend Winter"
    	},{
    		siteName:"Level 2",
    		topSite:"Block 1",
    		monMode:"Weekend Holiday",
    		tueMode:"Weekday 1",
    		wedMode:"Weekday 2",
    		thuMode:"Weekday 3",
    		friMode:"Weekday 4",
    		satMode:"Weekday 5",
    		sunMode:"Weekend Winter"
    	}];
    	
    	var detailHtml = "";
    	for (var i = 0; i < jsonArray.length; i++) {
			var jsonObject = jsonArray[i];
			detailHtml += "<tr><td><input type='radio' name='scheduleOverview' /></td>" + OVERVIEW.FUNC.getRowHtml(jsonObject);
			
		}
    	OVERVIEW.FUNC.initOverviewTable(detailHtml);
//    	$("#scheduleOverviewTbody").html(detailHtml);
    }
    
    function initScheduleSummary(){
    	
    }
    
    function initTimeSettings(){
    	
    }
    
    function bindScheduleEvent(){   
    	bindModeDirectoryEvent();
    	bindScheduleOverviewEvent();
    	bindScheduleSummaryEvent();
    	bindTimeSettingsEvent();
    	COMMON.EVENT.bindTabEvent();
    }
    
    function bindModeDirectoryEvent(){
    	
    }
    
    function bindScheduleOverviewEvent(){
    	OVERVIEW.EVENT.makeDailyScheduleTDCanBeSelectable();
    	OVERVIEW.EVENT.bindCopyFunction();
    	OVERVIEW.EVENT.bindPasteFunction();
    	OVERVIEW.EVENT.afterEditPlanModalShown();
    	OVERVIEW.EVENT.makeModeItemDraggble();
    	OVERVIEW.EVENT.makeDateRangeCalendarDroppable();
    	OVERVIEW.EVENT.makeAutomaticSettingCalendarDroppable();
    }
    
    function bindScheduleSummaryEvent(){
    	
    }
    
    function bindTimeSettingsEvent(){
    	
    }
});
