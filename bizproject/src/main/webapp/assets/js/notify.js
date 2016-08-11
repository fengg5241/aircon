$(document).ready(
		function() {
			var config = {
				'.chosen-select' : {},
				'.chosen-select-deselect' : {
					allow_single_deselect : true
				},
				'.chosen-select-no-single' : {
					disable_search_threshold : 10
				},
				'.chosen-select-no-results' : {
					no_results_text : 'Oops, nothing found!'
				},
				'.chosen-select-width' : {
					width : "5%"
				}
			}
			$('.chosen-select').chosen();
	        $('.chosen-select-deselect').chosen({ allow_single_deselect: true });
			for ( var selector in config) {
				$("#select").chosen(config[selector]);
				$("#time_zone").chosen(config[selector]);
				$("#users").chosen(config[selector]);
				$("#time").chosen(config[selector]);				


			}
			
			//Idle Timer function.
			 /*$(document).ready(function () {

			        // Set idle time
			        $( document ).idleTimer( 5000 );

			    });

			    $( document ).on( "idle.idleTimer", function(event, elem, obj){
			    	swal({
		                title: "User Session Expired due to inactive state",		                
		                text: "Your Session will be terminated"
			    	},
			    	function(){
			    		window.location.href = '../login/loginPage.htm';
			    		window.location.href = '../acconfig/viewAcConfig.htm';			    		
			    	});
		            
			        

			    });*/

			    /*$( document ).on( "active.idleTimer", function(event, elem, obj, triggerevent){
			        // function you want to fire when the user becomes active again
			        toastr.clear();
			        $('.custom-alert').fadeOut();
			        toastr.success('Great that you decided to move a mouse.','You are back. ');



			    });*/
			    
			 $('#data_5 .input-daterange').datepicker({
	                keyboardNavigation: false,
	                forceParse: false,
	                autoclose: true
	            });
			/*var rakete = $('#preinitialized,#pre').searchableOptionList({
                maxHeight: '350px',
                showSelectAll: false,
                texts: {
                    selectAll: 'Select All'
                }
            });	*/		
			$("#generate").click(function(){
				$.bizalert("Downloading new User ID & Password. Please do not delete XXX file.");
	        });
			 /*$("[data-toggle=popover]").popover();

			    // Enabling Popover Example 2 - JS (hidden content and title capturing)
			    $("#popoverExampleTwo").popover({
			        html : true, 
			        content: function() {
			          return $('#popoverExampleTwoHiddenContent').html();
			        },
			        title: function() {
			          return $('#popoverExampleTwoHiddenTitle').html();
			        }
			    });
			
			
			
			    $('#button1').popover();
				$('#button2').popover();
				$('#button3').popover();
				$('#button4').popover();

			    $('#button1').on('click', function () {		        
			        $('#buton2').popover('hide');
			        $('#buton3').popover('hide');
			        $('#buton4').popover('hide');
			    });
			    
			    $('#button2').on('click', function () {		        
			        $('#buton3').popover('hide');
			        $('#buton4').popover('hide');
			        $('#buton1').popover('hide');
			    });
			    $('#button3').on('click', function () {		        
			        $('#buton4').popover('hide');
			        $('#buton1').popover('hide');
			        $('#buton2').popover('hide');
			    });
			    $('#button4').on('click', function () {		        
			        $('#buton1').popover('hide');
			        $('#buton2').popover('hide');
			        $('#buton3').popover('hide');
			    });
			    $("#demo").on("hide.bs.collapse", function(){
			    $(".btn").html('<span class="glyphicon glyphicon-collapse-down"></span> Open');
			  });
			  $("#demo").on("show.bs.collapse", function(){
			    $(".btn").html('<span class="glyphicon glyphicon-collapse-up"></span> Close');
			  });

*/
			 $(".toggle-false").click(function(){
			        $("#myCollapsible").collapse({
			            toggle: false
			        });
			    });
			    
			$("#add_row").on(
					"click",
					function() {
						var newid = 0;
						$.each($("#tab_logic tr"), function() {
							if (parseInt($(this).data("id")) > newid) {
								newid = parseInt($(this).data("id"));
							}
						});
						newid++;
						var tr = $("<tr></tr>", {
							id : "addr" + newid,
							"data-id" : newid
						});
						$.each($("#tab_logic tbody tr:nth(0) td"), function() {
							var cur_td = $(this);
							var children = cur_td.children();
							if ($(this).data("name") != undefined) {
								var td = $("<td></td>", {
									"data-name" : $(cur_td).data("name")
								});
								var c = $(cur_td).find(
										$(children[0]).prop('tagName')).clone()
										.val("");
								c.attr("name", $(cur_td).data("name") + newid);
								c.appendTo($(td));
								td.appendTo($(tr));
							} else {
								var td = $("<td></td>", {
									'text' : $('#tab_logic tr').length
								}).appendTo($(tr));
							}
						});

						
						/*$("<td></td>").append(
						    $("<button class='btn btn-danger glyphicon glyphicon-remove row-remove'></button>")
						        .click(function() {
						            $(this).closest("tr").remove();
						        })
						).appendTo($(tr));*/
						 
						
						// add the new row
						$(tr).appendTo($('#tab_logic'));

						$(tr).find("td i.row-remove").on("click", function() {
							$(this).closest("tr").remove();
						});
						$(tr).find("td i.edit_row").on("click", function() {
							$(this).closest("tr").contents().find("input").prop("disabled",false);
							$(tr).find("td i.edit_row").hide();
							$(tr).find("td i.save_row").show();
						});
						$(tr).find("td i.save_row").on("click", function() {
							$(this).closest("tr").contents().find("input").prop("disabled",true);
							$(tr).find("td i.save_row").hide();
							$(tr).find("td i.edit_row").show();
						});
					});

			 /*$('#edit_row').click(function () {
				 alert('hi');
	              var currentTD = $(this).parents('tr').find('td');
	              if ($(this).html() == 'Edit') {
	                  currentTD = $(this).parents('tr').find('td');
	                  $.each(currentTD, function () {
	                      $(this).prop('contenteditable', true)
	                  });
	              } else {
	                 $.each(currentTD, function () {
	                      $(this).prop('contenteditable', false)
	                  });
	              }
	    
	              $(this).html($(this).html() == 'Edit' ? 'Save' : 'Edit')
	    
	          });*/
			 
			// Sortable Code
			var fixHelperModified = function(e, tr) {
				var $originals = tr.children();
				var $helper = tr.clone();

				$helper.children().each(function(index) {
					$(this).width($originals.eq(index).width())
				});

				return $helper;
			};

			$(".table-sortable tbody").sortable({
				helper : fixHelperModified
			}).disableSelection();

			$(".table-sortable thead").disableSelection();

			$("#add_row").trigger("click");

			
			 // Examle data for jqGrid
            /*var mydata = [
                {id: "1", invdate: "2010-05-24", name: "test", note: "note", tax: "10.00", total: "2111.00"} ,
                {id: "2", invdate: "2010-05-25", name: "test2", note: "note2", tax: "20.00", total: "320.00"},
                {id: "3", invdate: "2007-09-01", name: "test3", note: "note3", tax: "30.00", total: "430.00"},
                {id: "4", invdate: "2007-10-04", name: "test", note: "note", tax: "10.00", total: "210.00"},
                {id: "5", invdate: "2007-10-05", name: "test2", note: "note2", tax: "20.00", total: "320.00"},
                {id: "6", invdate: "2007-09-06", name: "test3", note: "note3", tax: "30.00", total: "430.00"},
                {id: "7", invdate: "2007-10-04", name: "test", note: "note", tax: "10.00", total: "210.00"},
                {id: "8", invdate: "2007-10-03", name: "test2", note: "note2", amount: "300.00", tax: "21.00", total: "320.00"},
                {id: "9", invdate: "2007-09-01", name: "test3", note: "note3", amount: "400.00", tax: "30.00", total: "430.00"},
                {id: "11", invdate: "2007-10-01", name: "test", note: "note", amount: "200.00", tax: "10.00", total: "210.00"},
                {id: "12", invdate: "2007-10-02", name: "test2", note: "note2", amount: "300.00", tax: "20.00", total: "320.00"},
                {id: "13", invdate: "2007-09-01", name: "test3", note: "note3", amount: "400.00", tax: "30.00", total: "430.00"},
                {id: "14", invdate: "2007-10-04", name: "test", note: "note", amount: "200.00", tax: "10.00", total: "210.00"},
                {id: "15", invdate: "2007-10-05", name: "test2", note: "note2", amount: "300.00", tax: "20.00", total: "320.00"},
                {id: "16", invdate: "2007-09-06", name: "test3", note: "note3", amount: "400.00", tax: "30.00", total: "430.00"},
                {id: "17", invdate: "2007-10-04", name: "test", note: "note", amount: "200.00", tax: "10.00", total: "210.00"},
                {id: "18", invdate: "2007-10-03", name: "test2", note: "note2", amount: "300.00", tax: "20.00", total: "320.00"},
                {id: "19", invdate: "2007-09-01", name: "test3", note: "note3", amount: "400.00", tax: "30.00", total: "430.00"},
                {id: "21", invdate: "2007-10-01", name: "test", note: "note", amount: "200.00", tax: "10.00", total: "210.00"},
                {id: "22", invdate: "2007-10-02", name: "test2", note: "note2", amount: "300.00", tax: "20.00", total: "320.00"},
                {id: "23", invdate: "2007-09-01", name: "test3", note: "note3", amount: "400.00", tax: "30.00", total: "430.00"},
                {id: "24", invdate: "2007-10-04", name: "test", note: "note", amount: "200.00", tax: "10.00", total: "210.00"},
                {id: "25", invdate: "2007-10-05", name: "test2", note: "note2", amount: "300.00", tax: "20.00", total: "320.00"},
                {id: "26", invdate: "2007-09-06", name: "test3", note: "note3", amount: "400.00", tax: "30.00", total: "430.00"},
                {id: "27", invdate: "2007-10-04", name: "test", note: "note", amount: "200.00", tax: "10.00", total: "210.00"},
                {id: "28", invdate: "2007-10-03", name: "test2", note: "note2", amount: "300.00", tax: "20.00", total: "320.00"},
                {id: "29", invdate: "2007-09-01", name: "test3", note: "note3", amount: "400.00", tax: "30.00", total: "430.00"}
            ];

            // Configuration for jqGrid Example 1
            $("#table_list_1").jqGrid({
                data: mydata,
                datatype: "local",
                height: 600,
                width:1500,
                shrinkToFit: true,
                rowNum: 14,
                multiselect:true,
                colNames: ['Inv No', 'Date', 'Client', 'Amount', 'Tax', 'Total', 'Notes'],
                colModel: [
                    {name: 'id', index: 'id', width: 60, sorttype: "int"},
                    {name: 'invdate', index: 'invdate', width: 90, sorttype: "date", formatter: "date"},
                    {name: 'name', index: 'name', width: 100},
                    {name: 'amount', index: 'amount', width: 80, align: "right", sorttype: "float", formatter: "number"},
                    {name: 'tax', index: 'tax', width: 80, align: "right", sorttype: "float"},
                    {name: 'total', index: 'total', width: 80, align: "right", sorttype: "float"},
                    {name: 'note', index: 'note', width: 150, sortable: false}
                ],
                viewrecords: true,
                hidegrid: false
            });*/

           /* // Examle data for jqGrid
            var mydata = [
                {invdate: "2010-05-24", id: "2010-05-24", name: "<span class='fa fa-caret-right'/>", note: "note", tax: "10.00", total: "2111.00"} ,
                {invdate: "2010-05-25", id: "2010-05-25", name: "test2", note: "note2", tax: "20.00", total: "320.00"},
                {invdate: "2007-09-01", id: "2007-09-01", name: "test3", note: "note3", tax: "30.00", total: "430.00"},
                {invdate: "2007-10-04", id: "2007-10-04", name: "test", note: "note", tax: "10.00", total: "210.00"},
                {invdate: "2007-10-05", id: "2007-10-05", name: "test2", note: "note2", tax: "20.00", total: "320.00"},
                {invdate: "2007-10-05", id: "2007-09-06", name: "test3", note: "note3", tax: "30.00", total: "430.00"},
                {invdate: "2007-10-05", id: "2007-10-04", name: "test", note: "note", tax: "10.00", total: "210.00"},
                {invdate: "2007-10-05", id: "2007-10-03", name: "test2", note: "note2", amount: "300.00", tax: "21.00", total: "320.00", note: "note3", tax: "21.00", total: "320.00", note: "note3"},
                {invdate: "2007-10-05", id: "2007-09-01", name: "test3", note: "note3", amount: "400.00", tax: "30.00", total: "430.00", note: "note3", tax: "21.00", total: "320.00", note: "note3"},
                {invdate: "2007-10-05", id: "2007-10-01", name: "test", note: "note", amount: "200.00", tax: "10.00", total: "210.00", note: "note3", tax: "21.00", total: "320.00", note: "note3"},
                {invdate: "2007-10-05", id: "2007-10-02", name: "test2", note: "note2", amount: "300.00", tax: "20.00", total: "320.00", note: "note3", tax: "21.00", total: "320.00", note: "note3"},
                {invdate: "2007-10-05", id: "2007-09-01", name: "test3", note: "note3", amount: "400.00", tax: "30.00", total: "430.00", note: "note3", tax: "21.00", total: "320.00", note: "note3"},
                {invdate: "2007-10-05", id: "2007-10-04", name: "test", note: "note", amount: "200.00", tax: "10.00", total: "210.00", note: "note3", tax: "21.00", total: "320.00", note: "note3"},
                {invdate: "2007-10-05", id: "2007-10-05", name: "test2", note: "note2", amount: "300.00", tax: "20.00", total: "320.00", note: "note3", tax: "21.00", total: "320.00", note: "note3"},
                {invdate: "2007-10-05", id: "2007-09-06", name: "test3", note: "note3", amount: "400.00", tax: "30.00", total: "430.00", note: "note3", tax: "21.00", total: "320.00", note: "note3"},
                {invdate: "2007-10-05", id: "2007-10-04", name: "test", note: "note", amount: "200.00", tax: "10.00", total: "210.00", note: "note3", tax: "21.00", total: "320.00", note: "note3"},
                {invdate: "2007-10-05", id: "2007-10-03", name: "test2", note: "note2", amount: "300.00", tax: "20.00", total: "320.00", note: "note3", tax: "21.00", total: "320.00", note: "note3"},
                {invdate: "2007-10-05", id: "2007-09-01", name: "test3", note: "note3", amount: "400.00", tax: "30.00", total: "430.00", note: "note3", tax: "21.00", total: "320.00", note: "note3"},
                {invdate: "2007-10-05", id: "2007-10-01", name: "test", note: "note", amount: "200.00", tax: "10.00", total: "210.00", note: "note3", tax: "21.00", total: "320.00", note: "note3"},
                {invdate: "2007-10-05", id: "2007-10-02", name: "test2", note: "note2", amount: "300.00", tax: "20.00", total: "320.00", note: "note3", tax: "21.00", total: "320.00", note: "note3"},
                {invdate: "2007-10-05", id: "2007-09-01", name: "test3", note: "note3", amount: "400.00", tax: "30.00", total: "430.00", note: "note3", tax: "21.00", total: "320.00", note: "note3"},
                {invdate: "2007-10-05", id: "2007-10-04", name: "test", note: "note", amount: "200.00", tax: "10.00", total: "210.00", note: "note3", tax: "21.00", total: "320.00", note: "note3"},
                {invdate: "2007-10-05", id: "2007-10-05", name: "test2", note: "note2", amount: "300.00", tax: "20.00", total: "320.00", note: "note3", tax: "21.00", total: "320.00", note: "note3"},
                {invdate: "2007-10-05", id: "2007-09-06", name: "test3", note: "note3", amount: "400.00", tax: "30.00", total: "430.00", note: "note3", tax: "21.00", total: "320.00", note: "note3"},
                {invdate: "2007-10-05", id: "2007-10-04", name: "test", note: "note", amount: "200.00", tax: "10.00", total: "210.00", note: "note3", tax: "21.00", total: "320.00", note: "note3"},
                {invdate: "2007-10-05", id: "2007-10-03", name: "test2", note: "note2", amount: "300.00", tax: "20.00", total: "320.00", note: "note3", tax: "21.00", total: "320.00", note: "note3"},
                {invdate: "2007-10-05", id: "2007-09-01", name: "test3", note: "note3", amount: "400.00", tax: "30.00", total: "430.00", note: "note3", tax: "21.00", total: "320.00", note: "note3"}
            ];

            // Configuration for jqGrid Example 1
            $("#table_list_1").jqGrid({
                data: mydata,
                datatype: "local",
                height: 600,
                width:1500,
                rowNum: 14,
                shrinkToFit: true,
                multiselect: true,
                colNames: ['Occurred  Date/Time', 'Notification ID', 'Site', 'Area', 'Device Name', 'Category', 'Code','Notification Name','Counter Measure', 'Fixed Date/Time','Status'],
                colModel: [                           
                    {name: 'invdate', index: 'invdate', width: 125, sorttype: "date", formatter: "date" },
                    {name: 'id', index: 'id', width: 90, sorttype: "date", formatter: "date"},
                    {name: 'name', index: 'name', width: 40},
                    {name: 'amount', index: 'amount', width: 40, align: "right", sorttype: "float", formatter: "number"},
                    {name: 'tax', index: 'tax', width: 90, align: "right", sorttype: "float"},
                    {name: 'total', index: 'total', width: 60, align: "right", sorttype: "float"},
                    {name: 'note', index: 'note', width: 50, sortable: false},
                    {name: 'note', index: 'note', width: 120, sortable: false},
                    {name: 'tax', index: 'tax', width: 120, align: "right", sorttype: "float"},
                    {name: 'total', index: 'total', width: 120, align: "right", sorttype: "float"},
                    {name: 'note', index: 'note', width: 50, sortable: false}

                ],
                viewrecords: true,
                hidegrid: false
            });*/
            
            
			// Add Row Dynamically.
			/*var i = 1;
			$("#add_row")
					.click(
							function() {
								$('#addr' + i)
										.html(
												"<td><input name='name"
														+ i
														+ "' type='text' placeholder='Enter UserName' class='form-control input-md'  /> </td><td><input name='name"
														+ i
														+ "' type='text' placeholder='Enter DepartmentName' class='form-control input-md'  /> </td><td><input  name='mail"
														+ i
														+ "' type='text' placeholder='Enter Mail'  class='form-control input-md'></td><td><i title='Edit Row' class='fa fa-edit fa-2x'/><i title='Delete Row' id='delete' class='fa fa-minus-circle fa-2x delete_row'/></td>");

								$('#tab_logic').append(
										'<tr id="addr' + (i + 1)
												+ '"></tr>');
								i++;
							});
			$("#delete_row").click(function() {
				var par = $(this).parent().parent(); 
				par.remove();

				
			});
			$("#delete").click(function() {
				   $(this).closest("tr").remove(); 

				
			});
			$("#tab_logic").find("td i.row-remove").on("click", function() {
			     $(this).closest("tr").remove();
			});*/
			/*$("#tab_logic").on("click", "i", function() {
				   $(this).closest("tr").remove(); 
				});*/
			/*
			 * $("#btnAdd").click(function(){ Save(); });
			 * $("#btnSave").click(function(){ Save(); });
			 * $("#btnDelete").click(function(){ Delete(); });
			 * $("#btnEdit").click(function(){ Edit(); }); function
			 * Save(){ alert('hi'); var par = $(this).parent().parent();
			 * var tdName = par.children("td:nth-child(1)"); var tdPhone =
			 * par.children("td:nth-child(2)"); var tdEmail =
			 * par.children("td:nth-child(3)"); var tdButtons =
			 * par.children("td:nth-child(4)");
			 * tdName.html(tdName.children("input[type=text]").val());
			 * tdPhone.html(tdPhone.children("input[type=text]").val());
			 * tdEmail.html(tdEmail.children("input[type=text]").val());
			 * tdButtons.html("<i class=fa fa-minus-circle'
			 * class='btnDelete'/><i class=fa fa-edit'
			 * class='btnEdit'/>"); $(".btnEdit").bind("click", Edit);
			 * $(".btnDelete").bind("click", Delete); };
			 * 
			 * 
			 * function Edit(){ var par = $(this).parent().parent(); var
			 * tdName = par.children("td:nth-child(1)"); var tdPhone =
			 * par.children("td:nth-child(2)"); var tdEmail =
			 * par.children("td:nth-child(3)"); var tdButtons =
			 * par.children("td:nth-child(4)"); tdName.html("<input
			 * type='text' id='txtName' value='"+tdName.html()+"'/>");
			 * tdPhone.html("<input type='text' id='txtPhone'
			 * value='"+tdPhone.html()+"'/>"); tdEmail.html("<input
			 * type='text' id='txtEmail' value='"+tdEmail.html()+"'/>");
			 * tdButtons.html("<i class=fa fa-save'
			 * class='btnSave'/>"); $(".btnSave").bind("click", Save);
			 * $(".btnEdit").bind("click", Edit);
			 * $(".btnDelete").bind("click", Delete); };
			 * 
			 * 
			 * function Delete(){ var par = $(this).parent().parent();
			 * par.remove(); };
			 */

			/*
			 * $(document).on("click", "#inner a[data-toggle='tab']",
			 * function() { var target = $(this).attr("href");
			 * refresh(); function refresh(){ $('#ms').show();
			 * $('#ns').show(); $('#as').show(); $('#cs').show();
			 * $('#master').show(); $('#ds').show(); $('#gs').show(); }
			 * var ms = document.getElementById('ms'); var ns =
			 * document.getElementById('ns'); var as =
			 * document.getElementById('as'); var cs =
			 * document.getElementById('cs'); var master =
			 * document.getElementById('master'); var ds =
			 * document.getElementById('ds'); var gs =
			 * document.getElementById('gs');
			 * 
			 * if (target == "#mapTab" ) { ms.style.display = 'none';
			 * ns.style.display = 'none'; ds.style.display = 'none';
			 * as.style.display = 'none'; cs.style.display = 'none';
			 * gs.style.display = 'none'; master.style.display = 'none'; }
			 * else if (target == "#overviewTab") { ms.style.display =
			 * 'none'; ns.style.display = 'none'; ds.style.display =
			 * 'none'; as.style.display = 'none'; cs.style.display =
			 * 'none'; gs.style.display = 'show'; master.style.display =
			 * 'none'; } else if (target == "#acDetailTab") {
			 * ms.style.display = 'show'; ns.style.display = 'show';
			 * ds.style.display = 'show'; as.style.display = 'none';
			 * cs.style.display = 'none'; gs.style.display = 'none';
			 * master.style.display = 'none'; } else if (target ==
			 * "#maintenanceTab") { ms.style.display = 'none';
			 * ns.style.display = 'none'; ds.style.display = 'none';
			 * as.style.display = 'show'; cs.style.display = 'show';
			 * gs.style.display = 'none'; master.style.display = 'none';
			 * }else (target == "#notificationTab") { ms.style.display =
			 * 'none'; ns.style.display = 'none'; ds.style.display =
			 * 'none'; as.style.display = 'show'; cs.style.display =
			 * 'show'; gs.style.display = 'none'; master.style.display =
			 * 'show'; }
			 * 
			 * });
			 */
			menu('notification');
		});