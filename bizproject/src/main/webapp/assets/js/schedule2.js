$(document)
		.ready(
				function() {

					/* initialize the external events
					-----------------------------------------------------------------*/

					$('#external-events div.external-event').each(function() {

						// store data so the calendar knows to render an event upon drop
						$(this).data('event', {
							title: $.trim($(this).text()), // use the element's text as the event title
							//id: $.trim($(this).id()),
							stick: true // maintain when user navigates (see docs on the renderEvent method)
						});

						// make the event draggable using jQuery UI
						$(this).draggable({
							zIndex: 999,
							revert: true,      // will cause the event to go back to its
							revertDuration: 0  //  original position after the drag
						});

					});

					
					$("#schedule-trash").draggable({disabled:true})
					/*
					 * initialize the calendar
					 * -----------------------------------------------------------------
					 */
					var date = new Date();
					var d = date.getDate();
					var m = date.getMonth();
					var y = date.getFullYear();

					$('#calendar')
							.fullCalendar(
									{
										header : {
											left : 'prev,next today',
											center : 'title',
											right : 'month,agendaWeek,agendaDay'
										},
										contentHeight : $(window).height() - 310,
										editable : true,
										droppable : true,
										dropAccept: '#external-events div.external-event',
										eventLimit : true,
										// events
										drop : function(date, jsEvent, ui) {
											//console.log(jsEvent+" event");
											//console.log(ui+" ui");
										},
										eventRender: function(event, element) {
											if(typeof event.id == "undefined"){
												event.id = Math.round(Math.random() * 10000);
												event._id = ""+event.id;
												console.log(event.id);
											}console.log(event);
									    },
										/*events : [
												{
													id : 1,
													title : 'AC Monitor',
													start : new Date(y, m, 1)
												},
												{
													id : 2,
													title : 'Thershold point',
													start : new Date(y, m,
															d - 5),
													end : new Date(y, m, d - 2),
												},
												{
													id : 999,
													title : 'Prohibiton ON',
													start : new Date(y, m,
															d - 3, 16, 0),
													allDay : false,
												},
												{
													id : 999,
													title : 'Fan Speed',
													start : new Date(y, m,
															d + 4, 16, 0),
													allDay : false
												},
												{
													id : 3,
													title : 'Swing',
													start : new Date(y, m, d,
															10, 30),
													allDay : false
												},
												{
													id : 4,
													title : 'Prohibiton ON',
													start : new Date(y, m, d,
															12, 0),
													end : new Date(y, m, d, 14,
															0),
													allDay : false
												},
												{
													id : 5,
													title : 'Prohibiton OFF',
													start : new Date(y, m,
															d + 1, 19, 0),
													end : new Date(y, m, d + 1,
															22, 30),
													allDay : false
												}, {
													id : 6,
													title : 'Ac Flipflop',
													start : new Date(y, m, 28),
													end : new Date(y, m, 29),
												} ],*/
										eventDragStop : function(event, jsEvent) {
											console.log(event.id+" trash");
											var trashEl = $('#calendarTrash');
											var ofs = trashEl.offset();

											var x1 = ofs.left;
											var x2 = ofs.left
													+ trashEl.outerWidth(true);
											var y1 = ofs.top;
											var y2 = ofs.top
													+ trashEl.outerHeight(true);
											// console.log("x1: "+x1+", x2:
											// "+x2+", y1: "+y1+", y2: "+y2+"
											// jsEvent.pageX: "+jsEvent.pageX+",
											// jsEvent.pageY: "+ jsEvent.pageY);
											if (jsEvent.pageX >= x1
													&& jsEvent.pageX <= x2
													&& jsEvent.pageY >= y1
													&& jsEvent.pageY <= y2) {
												if (confirm("Are you sure to delete this event?") == true) {
													$('#calendar')
															.fullCalendar(
																	'removeEvents',
																	event.id);
													trashEl
															.addClass('animated pulse');
												}
											} else {
												$.bizalert("Not met delete conditions. Make sure you cursor pointed to trash icon!!!");
											}

											setTimeout(
													function() {
														trashEl
																.removeClass('animated pulse');
													}, 1000);
										}
									});

				});
menu('schedule');

$(document).bind(
		"resize",
		function() {
			// if(!$("body").hasClass('body-small')) {
			$('#calendar').fullCalendar('option', 'contentHeight',
					$(window).height() - 310);
			//}
		});