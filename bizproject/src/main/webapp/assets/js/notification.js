$(document).ready(function() {
    var GLOBAL_VAL = {
    		imgPath: "../assets/img/",
            selectedGroup: {},
            groupid: "1",
            start_date: "",
            end_date: "",
            enddate:"",
            imageIdLayerMap: {},
            floorMap: null,
            curGroupSvg:null,
            groupMenuItemPrefix: "groupMenuItem",
            //selectedObject.type:grop/indoorUnit
            selectedObject: {
                id: -1,
                type: ""
            },
            map_data_object: null,
            connectedIDUId: null,
            clickUnitIcon: true,
            MODE_HEAT: "Heat",
            MODE_COOL: "Cool",
            MODE_FAN: "Fan",
            MODE_DRY: "Dry",
            MODE_AUTO: "Auto",
            mapZoom: 21,
            isCompany:true,
            isAWS:false,
            IDUDetailTable:null,
            ODUDetailTable:null,
            OperLogTable:null,
            refreshTimer:null,
            scrollY:"695px",
            svgIdIDUListMap:{},
            svgIdODUListmap:{},
            autoFreshFlag:false,
            currentTable:{
            	headerCheckboxStatus:false,//false,"mix",true
            	selectedTrIdList:[],
            	scrollTop:0
            },
            fanspeedMap: {
                currentStatus: "Low",
                MIX: {
                    image: "../assets/img/fanspeed-mix.png",
                    nextStatus: "Low"
                },
                Low: {
                    image: "../assets/img/fanspeed-low.png",
                    nextStatus: "Medium"
                },
                Medium: {
                    image: "../assets/img/fanspeed-medium.png",
                    nextStatus: "High"
                },
                High: {
                    image: "../assets/img/fanspeed-high.png",
                    nextStatus: "Auto"
                },
                Auto: {
                    image: "../assets/img/fanspeed-auto.png",
                    nextStatus: "Low"
                }
            },
            windMap: {
                currentStatus: "F1",
                MIX: {
                    image: "../assets/img/wind_mix.png",
                    nextStatus: "F1"
                },
                Unset: {
                    image: "../assets/img/Flap-off.png",
                    nextStatus: "F1"
                },
                F1: {
                    image: "../assets/img/f1.png",
                    nextStatus: "F2"
                },
                F2: {
                    image: "../assets/img/f2.png",
                    nextStatus: "F3"
                },
                F3: {
                    image: "../assets/img/f3.png",
                    nextStatus: "F4"
                },
                F4: {
                    image: "../assets/img/f4.png",
                    nextStatus: "Swing"
                },
                F5: {
                    image: "../assets/img/f5.png",
                    nextStatus: "Swing"
                },
                Swing: {
                    image: "../assets/img/swing.png",
                    nextStatus: "F1"
                }
            }                     
    };
    
    // highlight menu
    menu('notification');
    function getCurrentTabHref(){
    	return $(".nav-tabs li.active a").attr("href");//"#acDetailTab"
    }
    
    function clearSearchResult(){
		var tableArray = [GLOBAL_VAL.IDUDetailTable];
		for (var i = 0; i < tableArray.length; i++) {
			var $table = tableArray[i];
			if ($table != null) {
				$table.search('').columns().search('').draw();
				//As other scrollbars will be refreshed when switch tab, only refresh scrollbar in current tab
			}
		}
    } 
    

    $(document).on("click", "#displayButton", function(e) {
    	$("#selectlabel").hide();
		$("#groupSelect").hide();
		$("#idtypes").hide();
		$("#idmaintypes").hide();
    	$("#v1").hide();
    	$("#v2").hide();
    	$("#v3").hide();
    	//var optHtmls = '<option value="" disabled selected></option>';
    	//$("#groupSelect").html(optHtmls);
    	var idList = getCheckedGroupIds();
    	if(idList.length == 0){
    		$.bizinfo("Please choose group");
    	}else {
    		clearFreshTimer();
    		$('#checkboxMenuTree').jstree().save_state();    		
    		if (GLOBAL_VAL.autoFreshFlag) {
				saveCurrentTableState();
			}else{
	    		$("#acDetailHeaderCheckbox").removeClass("mixStatus").prop("checked",false);	    		
			}  		
        	populateGroupSelects();
        	updateIDUDetailTableAndMap(idList);
        	if (GLOBAL_VAL.autoFreshFlag) {
				restoreCurrentTableState();
			}else {
				clearSearchResult();
				refreshScrollbarAlignment(getCurrentTabHref());
			}
    		
    		GLOBAL_VAL.autoFreshFlag = false;

    		setFreshTimer();
    	}
    	return false;
    });
    
       var items = [];
	   items =  $("#hiddenPermissionNameList").val();
	   console.log(items);
	   if(items.indexOf("component-User Account/Update User/customerIdTree") != -1 || items.indexOf("navi-CA Installation") != -1){
		   $('#company').show();
	   }else{
		   $('#company').hide();
	   }
    
    var dataobject =null;
    var Filetype = "csv";
/*    $(".dropdown-menu li a").click(function(){
    	  $(this).parents(".dropup").find('.btn').html($(this).text() + ' <span class="fa fa-caret-right"></span>');
    	  $(this).parents(".dropup").find('.btn').val($(this).data('value'));
    	   Filetype =$(this).text();
    	});*/
    
    //Download Notification Detail.
    $(document).on("click", "#notificationdetailsdownload", function(event, id) {
    	var groupIds = _getCheckedIDUIds();
    	var idList = getCheckedGroupIds();    	
    	console.log(groupIds+"........."+Filetype);
    	url = "../notification/downloadNotificationDetails.htm";
    	if (groupIds.length > 0) {

        	var data_send = JSON.stringify({"notificationIds":groupIds,"fileType": Filetype});
        	
        	$.ajax({
                url: url,
                beforeSend: function (request){
                	getToken(request);
                },
                data: {
                    "json_request": data_send,
                    "AJAXREQUEST": true
                },
                type:"POST",
                success: function(data) {
    	        	console.log("heeeee"+JSON.stringify(data));
    	        	var param= "Notification";
    	        	var details= "Details";
    	            var fileName = param+"_"+"("+details+").csv";    	            
    	            /*// Download Logic
    	            var blob=new Blob([data]);
    	            var link=document.createElement('a');
    	            link.href=window.URL.createObjectURL(blob);
    	            link.download = fileName;
    	            link.setAttribute("download", fileName);

    	            var isIE = false || !!document.documentMode;
    	            if(isIE == true){
    	            window.navigator.msSaveBlob(blob, fileName);
    	            } else {
    	            document.body.appendChild(link);
    	            link.click();
    	            document.body.removeChild(link);
    	            }*/
    	            
    	            // Download new Logic via plugin
		        	try {
		        	    var isFileSaverSupported = !!new Blob;
		        	} catch (e) {
		        		$.bizinfo("File Download not supported on your browser!!!");
		        	}
		        	
		        	if(isFileSaverSupported){
			        	var blob = new Blob([data], {type: "text/csv;charset=utf-8"});
			        	saveAs(blob, fileName, false);
		        	}
    	            
    	            
                }
            });        		       	
    	}else if (idList.length == 0){   		
        		$.bizinfo("Please choose group");        	
    	}else {
    		$.bizinfo("Please select at least 1 check box to proceed download");
    	}
         	
    });
    
  
    
    
    /*function _getCheckedNotificationIds(){
    	var NotificationIds = [];
    	$("#acDetailTbody input:checked").each(function( index ) {
    		NotificationIds.push($(this).attr("data-notificationID "));
    	});
    	return NotificationIds;
    }
    */
    function clearFreshTimer(){
    	if (GLOBAL_VAL.refreshTimer != null) {clearInterval(GLOBAL_VAL.refreshTimer);}
    }
    function setFreshTimer(){
  	  //refresh in 3mins depends on requirements 
  	GLOBAL_VAL.refreshTimer = setInterval(function(){
    	var idList = getCheckedGroupIds();
    	
    	if(idList.length == 0){
    		
    	}else {
    		GLOBAL_VAL.autoFreshFlag = true;
    		$("#displayButton").click();
    	}
    }, 1000*60*1);
      	
  	
  }
    
    
    function saveCurrentTableState(){
    	var currentTabHref = getCurrentTabHref();
    	if (currentTabHref != null && currentTabHref != "") {
    		GLOBAL_VAL.currentTable.selectedTrIdList = [];
    		
    		$(currentTabHref+ " tr.trSelected").each(function( index ) {
    			GLOBAL_VAL.currentTable.selectedTrIdList.push($(this).attr("ID"));
        	});
    		
    		var $currentPosition = $(currentTabHref + " .dataTables_scrollBody").find(".mCSB_container").position();
    		var scrollTop = 0;
    		if (typeof $currentPosition != "undefined" && $currentPosition.top != 0) {
    			scrollTop = $currentPosition.top * -1;
			}
    		GLOBAL_VAL.currentTable.scrollTop = scrollTop;
		}
    }
    
    function restoreCurrentTableState(){
    	var currentTabHref = getCurrentTabHref();
    	
    	var idList = GLOBAL_VAL.currentTable.selectedTrIdList;
    	for (var i = 0; i < idList.length; i++) {
			$(currentTabHref + " #" + idList[i]).addClass("trSelected");
			$(currentTabHref + " #" + idList[i] + " input").prop("checked",true);
		}
    	
    	var tabTableMap = {
				"#acDetailTab":GLOBAL_VAL.IDUDetailTable
		}
    	
    	$(".nav-tabs li").each(function( index ) {
			if(!$(this).hasClass("active")){
				var tabHref = $(this).find("a").attr("href");
				//clear filter result
				var $searchBoxInput = $(tabHref + ".dataTables_filter input");
				if (typeof $searchBoxInput != "undefined") {
					var inputVal = $searchBoxInput.val();
					if (inputVal != "") {
						var $table = tabTableMap[tabHref];
						if ($table != null) {
							$table.search('').columns().search('').draw();
						}
					}
				}
				
				//reset hearCheckBox status
				var $hearderCheckbox = $(tabHref+ " thead input:eq(0)");
				if (typeof $hearderCheckbox != "undefined") {
					$hearderCheckbox.removeClass("mixStatus").prop("checked",false);
				}
			}
    	});
    	
    	$(currentTabHref + " .dataTables_scrollBody").mCustomScrollbar("scrollTo", GLOBAL_VAL.currentTable.scrollTop);
    }
    
    function refreshScrollbarAlignment(parentDomStr){
    	if (parentDomStr.indexOf("#") == -1) {
    		parentDomStr = "#" + parentDomStr;
		}
    	$(parentDomStr + " .dataTables_scrollBody").mCustomScrollbar("update");
		$(parentDomStr + " .dataTables_scroll").resize();
    }
    
    $("#vrfCompressor1,#prevrfCompressor1,#vrfCompressor2,#prevrfCompressor2,#vrfCompressor3,#prevrfCompressor3,#ghpEngOper,#preghpEngOper,#preghptimeOil,#cacommunication").keypress(function (e) {       
        if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {                     
                  return false;
       }
      });
    
    //get allAlarms.
    function getalarmtypes()
    {
    	var  optionsHtml="";
    	$.axs("../notification/getAlarmType.htm", {
    		
        }, function(data) {
        	var  optionsHtml="";
            if (typeof data != "undefined") {
                for (var i = 0; i < data.length; i++) {
                    optionsHtml += "<option value="+data[i][1]+">"+data[i][1]+"</option>";
                }
            } 
            $("#notify_names").html(optionsHtml);
        });
    }
    
    var id1="";
    var id2="";
    var id3="";
       
    //get Maintenance setting Data.
    $(document).on("change", "#groupSelect", function(e) {  
    	$("#v1").show();
    	$("#v2").show();
    	$("#v3").show();
    	$("#vrfCompressor1,#vrfCompressor2,#vrfCompressor3").val(20000);
    			var selectedSiteId = $("#groupSelect").val();   		
            	$.axs("../maintenance/getMaintenanceSetting.htm", {
            		"json_request": JSON.stringify({"siteID":selectedSiteId})
                }, function(data) {console.log(JSON.stringify(data));
                                             
                if(data.errorMessage !== 'no.records.found'){ 
                 var vrf1=data[0].value;
                 var vrf2=data[1].value;
                 var vrf3=data[2].value;   
                 
                 id1 =data[0].id;
                 id2 =data[1].id;
                 id3 =data[2].id;
            	$("#vrfCompressor1").val(vrf1);
            	$("#vrfCompressor2").val(vrf2);
            	$("#vrfCompressor3").val(vrf3);
                }else{
                	
                	id1 =0;
                    id2 =0;
                    id3 =0;
                    $("#vrfCompressor1").val(20000);
                	$("#vrfCompressor2").val(20000);
                	$("#vrfCompressor3").val(20000);
                }
                });            
    	return false;
    });
    
    //cancel changes.(cancel)
    $(document).on("click", "#cancelmaintenancesetting", function(e) {    	
    	var selectedSiteId = $("#groupSelect").val();   		
    	$.axs("../maintenance/getMaintenanceSetting.htm", {
    		"json_request": JSON.stringify({"siteID":selectedSiteId})
        }, function(data) {         
        $("#vrfCompressor1").val(data[0].value);
     	$("#vrfCompressor2").val(data[1].value);
     	$("#vrfCompressor3").val(data[2].value);
        });            
    	return false;    	
    });
    
    $("#vrfCompressor1,#vrfCompressor2,#vrfCompressor3").val(20000);
    
    //set Maintenance setting Data.(Apply)
    $(document).on("click", "#savemaintenancesetting", function(e) {
    	var idList = getCheckedControlGroupIds();
    	 var vrfvalue1 = $("#vrfCompressor1").val();
	     var vrfvalue2 = $("#vrfCompressor2").val();
	     var vrfvalue3 = $("#vrfCompressor3").val();
	     var selectedSiteId = $("#groupSelect").val();
	     
	     if(/^\d+$/.test(vrfvalue1) || /^\d+$/.test(vrfvalue1)|| /^\d+$/.test(vrfvalue1)) {
	    	 if(idList.length == 0){  
	     		$.bizinfo("Please choose group");    		
	     	}else if(selectedSiteId == "" ||selectedSiteId == null){
	     		$.bizinfo("Please select Site ID");    		
	     	}
	     	else if(vrfvalue1 <10000 ||vrfvalue1>30000){
	     		$.bizinfo("Please enter value between 10,000 to 30,000 hours.");
	     	}else if(vrfvalue2 <10000 ||vrfvalue2>30000){
	     		$.bizinfo("Please enter value between 10,000 to 30,000 hours.");
	     	}else if(vrfvalue3 <10000 ||vrfvalue3>30000){
	     		$.bizinfo("Please enter value between 10,000 to 30,000 hours.");
	     	}
	     	else {
	     			var json_request = {};
	     			 var selectedSiteId = $("#groupSelect").val();
	     		     var vrfvalue1 = $("#vrfCompressor1").val();
	     		     var vrfvalue2 = $("#vrfCompressor2").val();
	     		     var vrfvalue3 = $("#vrfCompressor3").val();
	     		     var maintenanceTypeList = [];
	     		     
	     		     var maintenanceTypeList1 = {};
	     		     maintenanceTypeList1.id = id1;
	     		     maintenanceTypeList1.name= "vrf_comp_1_operation_hours";
	     		     maintenanceTypeList1.value = parseInt(vrfvalue1);
	     		     
	     		     var maintenanceTypeList2 = {};
	     		     maintenanceTypeList2.id = id2;
	     		     maintenanceTypeList2.name= "vrf_comp_2_operation_hours";
	     		     maintenanceTypeList2.value = parseInt(vrfvalue2);
	     		     
	     		     var maintenanceTypeList3 = {};
	     		     maintenanceTypeList3.id = id3;
	     		     maintenanceTypeList3.name= "vrf_comp_3_operation_hours";
	     		     maintenanceTypeList3.value = parseInt(vrfvalue3);

	     		     maintenanceTypeList.push(maintenanceTypeList1);
	     		     maintenanceTypeList.push(maintenanceTypeList2);
	     		     maintenanceTypeList.push(maintenanceTypeList3);
	     		     
	     		     json_request.siteID = parseInt(selectedSiteId);
	     		     json_request.oduID = null;
	     		     json_request.maintenanceTypeList = maintenanceTypeList;
	     		     json_request.maintenanceTypeID = null;
	     		     json_request.addUserList = null;
	     		     json_request.deleteUserList = null;
	     		     json_request.companyID = null;
	     		     json_request.maintenanceID = null;
	     		     json_request.maintenanceType = null;
	     		     
	     		     console.log({"json_request":JSON.stringify(json_request)});
	 	    		$.ax('../maintenance/setMaintenanceSetting.htm', {"json_request":JSON.stringify(json_request)}, true, "post", "json", function(data) {
	 					console.log("data:"+ JSON.stringify(data));
	 	    		}, function(error){
	 	    			console.log("Error: "+JSON.stringify(error));
	 				});
	                 $.bizinfo("Sucessfully");	    		
	     	}
	    	}
	    	else {
	    		$.bizinfo("Please enter valid numbers only");
	    	 return false;
	    	}
    	return false;
    });
    
    $(document).on("click", "#graphapply", function(e) {
    	
    	var idList = getCheckedControlGroupIds();
    	if(idList.length == 0){
    		$.bizinfo("Please choose group");
    	}else {
    		$(this).data('clicked', true);
        	var checkedIdList = getCheckedGroupIds();
        	populateGraph('notify_overview_graph', checkedIdList);
    	}
    	return false;
    });
    
    //var optHtml = '<option value="" disabled selected>Select Site</option>';
    
    function populateGroupSelects(){
    	var tree = $.jstree.reference('#checkboxMenuTree');
		var checkedList = tree.get_checked(true);
		var optionsHtml = '<option value="" disabled selected>Please Select</option>';	
		var opHtml = "";	
		var optHtml = '<option value="" disabled selected>Select Site</option>';
		//var optHtml = "";		
		var temp = [];
		var unique;
		for (var i = 0; i < checkedList.length; i++) {
			var categoryName = checkedList[i].original.groupCategory;
			var levelName = checkedList[i].original.groupTypeLevelName;
			if (typeof categoryName !== "undefined") {
				temp.push(checkedList[i].original.groupTypeLevelName+"-"+checkedList[i].original.groupTypeLevelID);
			}
			if (levelName == "floor" || levelName == "Floor") {
				opHtml += "<option id="+checkedList[i].original.groupId+">"+checkedList[i].original.groupName+"</option>";	
			}
			if (levelName == "Building" || levelName == "Building") {
				if (checkedList.length>0){
					$("#selectlabel").show();
					$("#groupSelect").show();
					$("#idtypes").show();
					$("#idmaintypes").show();
				optHtml += "<option value="+checkedList[i].original.groupId+">"+checkedList[i].original.groupName+"</option>";	
				}
				else {
				//optHtml += "<option value="+ +">"+ +"</option>";	
				}
			}
		}
		
		unique = temp.filter(function(itm,i,types) {
		    return i==types.indexOf(itm);
		});
		
		for (var i = unique.length - 1; i >= 0; i--) {/*console.log(i);*/
			var temp_array = unique[i].split("-");
			optionsHtml += "<option value="+temp_array[1]+">"+temp_array[0]+"</option>";
		}
		
		$("#categorylevel").html(optionsHtml);		
		$("#groupSelects").html(opHtml);
		$("#groupSelect").html(optHtml);   	
    }
    
			    var today = new Date();
			    var dd = today.getDate();
			    var mm = today.getMonth()+1; 
			    var yyyy = today.getFullYear()
			    today = dd+'/'+mm+'/'+yyyy;
			    console.log("....."+today);
    
            // Grpah Related Logic.
    		$.fn.datepicker.defaults.format = "dd/mm/yyyy";
			GLOBAL_VAL.end_date = GLOBAL_VAL.start_date =  new Date();
		    GLOBAL_VAL.enddate = new Date();		   
			GLOBAL_VAL.enddate.setDate(GLOBAL_VAL.end_date.getDate());
			$("#end").val($.datepicker.formatDate("dd/mm/yy", GLOBAL_VAL.enddate));
			GLOBAL_VAL.start_date.setDate(GLOBAL_VAL.end_date.getDate() - 6);	        
	        $("#start").val($.datepicker.formatDate("dd/mm/yy", GLOBAL_VAL.start_date));
			
				$('.input-daterange').datepicker({
			              keyboardNavigation: false,
			              forceParse: false,
			              autoclose: true,
			              endDate: "0d",
			              startDate: "-3y"			              			              			              
			    });
				
				
    function populateGraph(e, id){
    	
    	var categorylevel = $("#categorylevel").val();   	
/*    	var notify_names =  $("#notify_names option:selected").text();  */   	
    	var period = $('input[name="period"]:checked').val();     	
    	if(period == 'date_range'){
    		GLOBAL_VAL.start_date = $('#notification_overview_datepicker > input[name="start"]').val();    		
	    	var splitted = GLOBAL_VAL.start_date.split('/');	    	
	        var startDate = splitted[2] + "-" + splitted[1] + "-" + splitted[0];		         
	    	GLOBAL_VAL.end_date = $('#notification_overview_datepicker > input[name="end"]').val();
	    	var splitted = GLOBAL_VAL.end_date.split('/');
	        var endDate = splitted[2] + "-" + splitted[1] + "-" + splitted[0];;    		
    		period = '';
    	}
    	if(e == "notify_overview_graph" && categorylevel !=null){    	   		
     	   data_send = "{ 'groupIds': ["+id+"],'startDate': '"+startDate+"','endDate': '"+endDate+"','period': '"+period+"','grouplevel': "+categorylevel+"}";
    	        console.log("overview"+data_send);
    	    	   $.axs("../notification/getNotificationOverView.htm",{"json_request": data_send}, function(data) {				
	    	   console.log(JSON.stringify(data));	    	   
	    	   var series = [];
	    	   if(data.errorMessage !== 'no.records.found'){
	    		   
	    		   for(var i=0;i<data.yAxis.length;i++){
	    			   if(typeof data.yAxis[i].Name !== undefined){
				    	   if(data.yAxis[i].Name == "New"){
				    		   series[i] = {
			                        borderWidth: 0,
			                        name: 'New',
			                        data: data.yAxis[i].Data,
			                        color: '#CC1519'
			                    };
				    		   
				    	   }
				    	   else if(data.yAxis[i].Name == "on-hold"){
				    		   series[i] = {
			                        borderWidth: 0,
			                        name: 'on-hold',
			                        /*data: data.yAxis[i].Data,*/
			                        color: '#FCCC27',
			                        showInLegend: false
			                        /*visible:false*/
			                    };   		   
								    		  
				    	   }
				    	   else if(data.yAxis[i].Name == "Fixed" ){
				    		   series[i] = {
			                        borderWidth: 0,
			                        name: 'Fixed',
			                        data: data.yAxis[i].Data,
			                        color: '#049386'
			                    };
					    		  
					    	}
	    			   }
	    		   }
	    	   }else {
	                series = [];
	            }
	    	       	  
	            var scroll = false;
	            
	            if(data.errorMessage !== 'no.records.found'){   	
        		if(data.xAxis.length > 7){	            			
        			scroll = true;
        		}else{       			
        			scroll = false;
        		}
	            	
	            }
	            
	    	   if(data.length == 0 || data.errorMessage == 'no.records.found'){	    		  	    			   
	    		   $('#' + e).highcharts({
	    			   credits: {
		                    enabled: false
		                },
		                legend: {
		                    enabled: false,		                    		            		
		                },		               
		                exporting: { enabled: false },		                	                
		                chart: {
		                	backgroundColor: '#ffffff'		                    
		                },		                		                
		                title: {
		                    text: null
		                }		                		                		                	                
		            });			                    		                	    		   
	    	   }
	    	   else { $('#' + e).highcharts({		            	
		            	credits: {
		                    enabled: false
		                },
		                legend: {
		                    enabled: true,
		                    title: {
		                        text: 'Legend:',		                        
		                        style: {		                            
		                            "fontSize":"15px",
		                            "fontWeight":"bold"
		                        },
		                        labels: {
			                        style: {		                            
			                            "fontSize":"15px",
			                            "fontWeight":"bold"
			                        }
			                    }
		                    },		                    
		                    align: 'right',
		                    layout: 'vertical',
		                    verticalAlign: 'top',
		                    y: 250,
		                    itemStyle: {
		                        fontSize: '15px'
		                    },
		            		itemHoverStyle: {
		            			color: '#4d4d4d'
		            		},
		            		
		                },
		                rangeSelector: {
		                    enabled: false
		                },
		                exporting: { enabled: false },
		                navigator: {
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
		                chart: {
		                	backgroundColor: '#ffffff',
		                    type: 'column',
		                    zoomType: 'xy',		                    
		                    plotBorderWidth: 0,
		                    plotBorderColor: '#ffffff',
		                    animation: true,		                    
		                    style: {
		                        fontFamily: 'panasonic'
		                    },
		                   
		                },
		                plotOptions: {
		                    column: {
		                        stacking: 'normal',
		                        plotBorderColor: '#808080'		                        
		                    }
		                },		                
		                title: {
		                    text: null
		                },
		                xAxis: {
		                    categories: data.xAxis,
		                    labels: {
		                        style: {		                            
		                            "fontSize":"15px",
		                            "fontWeight":"bold"
		                        }
		                    }
		                },
		                yAxis: {
		                    title: {
		                        text: null
		                    },
		                    labels: {
		                        style: {		                            
		                            "fontSize":"15px",
		                            "fontWeight":"bold"
		                        }
		                    }
		                },
		                tooltip: {
		                    pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b><br/>',
		                    shared: true,
		                    valueSuffix: ' Alarms ',
		                    style: {		                            
	                            "fontSize":"15px",
	                            "fontWeight":"bold"
	                        }
		                },
		                series: series,		                
		            });	  }          
	        });
	    }else{
	    	$.bizinfo("Please select category level");
	    }
    	
    }
    
    $(document).on('change', '#categorylevel,#period,#notification_overview_datepicker', function(e) {
    		$('#graphapply').data('clicked', false);
	  });
    
    
  //Download Notification Overview.
    $(document).on("click", "#notificationoverviewdownload", function(event, id) {
    	var checkedIdList = getCheckedGroupIds();
    	var FileType="csv";
    	var categorylevel = $("#categorylevel").val();   	
/*    	var notify_names =  $("#notify_names option:selected").text();  */   	
    	var period = $('input[name="period"]:checked').val(); 
    	url = "../notification/downloadNotificationOverView.htm";
    	if (checkedIdList.length == 0 ){
    		$.bizinfo("Please choose group ");
    	}else if(categorylevel == null){
    	$.bizinfo("Please select category level and apply selection");
    	}else if(!($('#graphapply').data('clicked'))){
    	$.bizinfo("Please click on apply selection button if changes are made to any of the above entries before downloading data.");
    	}
    	else {
    	if(period == 'date_range'){
    		GLOBAL_VAL.start_date = $('#notification_overview_datepicker > input[name="start"]').val();    		
	    	var splitted = GLOBAL_VAL.start_date.split('/');	    	
	        var start_date = splitted[2] + "-" + splitted[1] + "-" + splitted[0];		         
	    	GLOBAL_VAL.end_date = $('#notification_overview_datepicker > input[name="end"]').val();
	    	var splitted = GLOBAL_VAL.end_date.split('/');
	        var end_date = splitted[2] + "-" + splitted[1] + "-" + splitted[0];;    		
    		period = '';
    	    	}
    		data_send = "{ 'groupIds': ["+checkedIdList+"],'startDate': '"+start_date+"','endDate': '"+end_date+"','period': '"+period+"','grouplevel': "+categorylevel+",'fileType': '"+FileType+"'}";
    		console.log("overview download"+data_send);
	    	
        	$.ajax({
                url: url,
                beforeSend: function (request){
                	getToken(request);
                },
                data: {
                    "json_request": data_send,
                    "AJAXREQUEST": true
                },
                success: function(data) {
    	        	console.log(JSON.stringify(data));
    	        	     
    	            var date_range = null;
    	            if(period == null){
    	            	start_date = start_date.split("-");
    	                end_date = end_date.split("-");
    	                
    	                if(parseInt(start_date[1]) < 10){
    	                	start_date[1] = "0"+start_date[1];
    	                }
    	                
    	                if(parseInt(end_date[1]) < 10){
    	                	end_date[1] = "0"+end_date[1];
    	                }
    	                
    	                var start_final = start_date[0]+start_date[1]+start_date[2];
    	                var end_final = end_date[0]+end_date[1]+end_date[2];
    	                
    	                date_range = start_final.substring(1, start_final.length - 1)+"to"+end_final.substring(1, end_final.length - 1);
    	            } else {
    	            	date_range = period;
    	            }
    	            
    	            var date = new Date();
    	            var parameter = "Notification Overview";
    	            var timestamp = date.getFullYear()+""+(date.getMonth()+1)+""+date.getDate()+""+date.getHours()+""+date.getMinutes()+""+date.getSeconds();
    	            
    	            var fileName = parameter+"_"+date_range+"("+timestamp+").csv";
    	            
    	            /*// Download Logic
    	            var blob=new Blob([data]);
    	            var link=document.createElement('a');
    	            link.href=window.URL.createObjectURL(blob);
    	            link.download = fileName;
    	            link.setAttribute("download", fileName);

    	            var isIE = false || !!document.documentMode;
    	            if(isIE == true){
    	            window.navigator.msSaveBlob(blob, fileName);
    	            } else {
    	            document.body.appendChild(link);
    	            link.click();
    	            document.body.removeChild(link);
    	            }*/
    	            
    	            
    	            // Download new Logic via plugin
		        	try {
		        	    var isFileSaverSupported = !!new Blob;
		        	} catch (e) {
		        		$.bizinfo("File Download not supported on your browser!!!");
		        	}
		        	
		        	if(isFileSaverSupported){
			        	var blob = new Blob([data], {type: "text/csv;charset=utf-8"});
			        	saveAs(blob, fileName, false);
		        	}
    	            

                }
            });
        	
        	
    	}
    	$("#graphapply").data('clicked', false);
    });
    
    initMonitoringCtrl();
    bindMonitorControlEvent();
    

    
    /**
     * ******************* Monitor Control part logic function
     * start ***************
     */
    function bindMonitorControlEvent() {
    	bindAcDetailEvent();
        bindFloorMapEvent();       
        bindTabEvent();
    }

    function bindAcDetailEvent() {
    	//click head checkbox then check all
        $(document).on("click", "#acDetailHeaderCheckbox", function() {
        	var status = $(this).prop("checked");
        	$("#acDetailTbody .acDetailCheckbox").prop("checked",status);
        	status ? $("#acDetailTbody tr").addClass("trSelected"):$("#acDetailTbody tr").removeClass("trSelected");
        	$(this).removeClass("mixStatus");

        });
        
      //click detail checkbox then affect head checkbox
        $(document).on("click", ".acDetailCheckbox", function() {
    		$("#acDetailHeaderCheckbox").addClass("mixStatus").prop("checked",false);

        	$("#acDetailHeaderCheckbox").prop("checked",status);
        	
        	//change children checked status by parent
        	var currentStatus = $(this).prop("checked");
        	currentStatus ? $(this).parent().parent().addClass("trSelected"):$(this).parent().parent().removeClass("trSelected");
        	var allCheckboxCount = $("#acDetailTbody .acDetailCheckbox").length;
        	var checkedCount = $("#acDetailTbody .acDetailCheckbox:checked").length;
        	
        	if (checkedCount == allCheckboxCount) {
        		$("#acDetailHeaderCheckbox").removeClass("mixStatus").prop("checked",true);
			}else {
				if (checkedCount == 0) {
					$("#acDetailHeaderCheckbox").removeClass("mixStatus").prop("checked",false);
				}else {
					$("#acDetailHeaderCheckbox").addClass("mixStatus").prop("checked",false);
				}
				
			}
        	/*var $curTr = $(this).parent().parent();
        	$curTr.nextAll().each(function( index ) {
        		if ($(this).find("td.acDetailPC").text() == "Parent"){
        			return false;
        		}else {
        			currentStatus ? $(this).addClass("trSelected"):$(this).removeClass("trSelected");
        			$(this).find("input.acDetailCheckbox").prop("checked",currentStatus);
        		}

        	});*/
        });
        
        $('#alarmDetailDialog').on('show.bs.modal', function (e) {
        	var $aLink = $(e.relatedTarget)
        	var severity = $aLink.text().toLowerCase();
        	var dialogTitle = "Fault Diagnosis";
        	
        	if (severity.indexOf("non-critical") != -1){
        		dialogTitle = "Fault Prediction";
        		$("#criticleDiagnosisResult").hide();
        		$("#nonCriticleDiagnosisResult").show();
        	}else {
        		$("#nonCriticleDiagnosisResult").hide();
        		$("#criticleDiagnosisResult").show();
        	}
        	
        	$("#alarmDetailDialog .modal-title").text(dialogTitle);
        	var $tr = $aLink.parent().parent();
        	$("#locationValue").text($tr.find(".acDetailsPC").text());
        	$("#caNameValue").text("CA"+$tr.find(".acDetailsLoc").text());
        	$("#timeZoneValue").text("GMT +8:00");
        	
        	var modelName = "S-56MD1E5";
        	var deviceName = $tr.find(".acDetailsStatus").text();
        	$("#deviceNameValue").text(deviceName);
        	if (deviceName.indexOf("ODU") != "-1") {
        		modelName = "U-20ME1H8";
			}
        	
        	$("#modelNameValue").text(modelName);
        	$("#occurredTimeValue").text($tr.find(".acDetailsSite").text());
        	$("#alarmDetailDialog .alarmCodeValue").text($tr.find(".acDetailsRoomTemp").text());
        	
    	});
        
        //popup for model number
        $(document).on("click", ".modelNumberLink", function() {
        	var modelNumber = $(this).attr("data-deviceModel");
        	var message = getModelNumberRowHtml('IDU Model Number:'+ modelNumber)+getModelNumberRowHtml('ODU1 Name:');
        	$.bizinfo({
    			"title":"Device Information",
    			"message":message
    		});
        });
        
        function getModelNumberRowHtml(text){
        	return '<div class="row"><div class="col-xs-3"></div><div class="col-xs-9 text-left" >' + text + '</div></div>'
        }
    
    }
    
    function _getCheckedIDUIds(){
    	var IDUIds = [];
    	$("#acDetailTbody input:checked").each(function( index ) {
    		IDUIds.push($(this).attr("data-notifid"));
    	});
    	return IDUIds;
    }
    
   

   
    
   
    


    function refreshMapAndACDetail() {
        if (GLOBAL_VAL.selectedObject.type == "group") {
            _refreshMapAndACDetailByGroupId(GLOBAL_VAL.selectedGroup.attr("group-id"));
        } else if (GLOBAL_VAL.selectedObject.type == "indoorUnit") {
            $("#IDU" + GLOBAL_VAL.selectedObject.id).click();
        }
    }

    

    

    

    function bindFloorMapEvent() {
        $(document).on('click', '.IDUImage', function() {
			var tempInfo = $("#"+$(this).attr("id")).data("indoorUnitInfo");
            console.log(tempInfo.iduId);

//          GLOBAL_VAL.selectedObject = {
//          id: $(this).data("indoorUnitInfo").id,
//          type: "indoorUnit"
//      };
      $.axs("../notification/getNotificationDetails.htm", {
          "json_request": JSON.stringify({"id":[tempInfo.iduId],"idType":"indoorUnit","severity":['critical','non-critical'],"alarmType":'all',"status":['fixed','new'],"alarmOccurredStartDate":'01/01/2012',"alarmOccurredEndDate":today,"addCustName":"yes"})
      }, function(data) { 
    	  console.log(data);
      	if (typeof(data) != "undefined" && data.errorMessage != "no.records.found") {     		
            var indoorUnit = data.notificationList[0];
      		var iduId = indoorUnit.iduId;
              console.log(indoorUnit)
//            GLOBAL_VAL.selectedObject.centralAddress = indoorUnit.centralAddress;
              
              addImageLayer(indoorUnit, "IDU");

	            var highlightImg = $("img.svgHighlight");
	            //Back to initial size
	            highlightImg.removeClass("svgHighlight").css("height", (highlightImg.height() - 6) + "px").css("width", (highlightImg.width() - 6) + "px");
//	            $("#acDetailTbody tr").removeClass("svgHighlight");
	            var IDUDom = $("#IDU" + iduId);
	            //increase size for showing border
	            IDUDom.css("height", (IDUDom.height() + 6) + "px").css("width", (IDUDom.width() + 6) + "px").addClass("svgHighlight");
	            
//	            var checkStatus = $("#IDU" + indoorUnit.id + "tr input.acDetailCheckbox").prop("checked");
	            $("#IDU" + iduId + "tr").replaceWith(getACDetailLineHtml(indoorUnit));
//	            $("#IDU" + indoorUnit.id + "tr input.acDetailCheckbox").prop("checked",checkStatus);
//	            $("#IDU" + indoorUnit.id + "tr").addClass("svgHighlight");
	            
	            	
	                	var severityStr = indoorUnit.severity.toLowerCase();
	                	if (severityStr.indexOf("non-critical") != -1 && indoorUnit.alarmStatus =='New') 
	                	{
	                		_opennoncriticalPopup(indoorUnit);	                			                		
	                	}
	                	else if (severityStr.indexOf("critical") != -1 && indoorUnit.alarmStatus =='New' ){              		
	                		_opencriticalPopup(indoorUnit);
	                		}
	                	else if ((severityStr.indexOf("critical") != -1 ||severityStr.indexOf("non-critical") != -1) && indoorUnit.alarmStatus =='Fixed' ){              		
	                		_openfixedPopup(indoorUnit);
	                		}
	        			            		            	
	           	            
	            //updateAcContorl(indoorUnit.id);
	            if (GLOBAL_VAL.clickUnitIcon == true) {
	                $("acDetailScrollDiv").mCustomScrollbar("scrollTo", "#IDU" + iduId + "tr");
	            } else {
	                GLOBAL_VAL.clickUnitIcon = true;
	               // GLOBAL_VAL.floorMap.setView([indoorUnit.maxlatitude, indoorUnit.minlongitude]);
	            }
			}

              return false;
          }, "post");
      return false;
  });
        
        $(document).on('click', '.ODUImage', function() {
        	var tempInfo = $("#"+$(this).attr("id")).data("indoorUnitInfo");
            console.log(tempInfo.oduId);

      $.axs("../notification/getNotificationDetails.htm", {
          "json_request": JSON.stringify({"id":[tempInfo.oduId],"idType":"outdoorUnit","severity":['critical','non-critical'],"alarmType":'all',"status":['fixed','new'],"alarmOccurredStartDate":'01/01/2012',"alarmOccurredEndDate":today,"addCustName":"yes"})
      }, function(data) { 
    	  console.log(data);
      	if (typeof(data) != "undefined" && data.errorMessage != "no.records.found") {     		
            var indoorUnit = data.notificationList[0];
      		var oduId = indoorUnit.oduId;
              console.log(indoorUnit)
              
              addImageLayer(indoorUnit, "ODU");

	            var highlightImg = $("img.svgHighlight");
	          
	            highlightImg.removeClass("svgHighlight").css("height", (highlightImg.height() - 6) + "px").css("width", (highlightImg.width() - 6) + "px");

	            var IDUDom = $("#ODU" + oduId);
	           
	            IDUDom.css("height", (IDUDom.height() + 6) + "px").css("width", (IDUDom.width() + 6) + "px").addClass("svgHighlight");
	            

	            $("#ODU" + oduId + "tr").replaceWith(getACDetailLineHtml(indoorUnit));

	            
	            	
	                	var severityStr = indoorUnit.severity.toLowerCase();
	                	if (severityStr.indexOf("non-critical") != -1 && indoorUnit.alarmStatus =='New') 
	                	{
	                		_openodunoncriticalPopup(indoorUnit);	                			                		
	                	}
	                	else if (severityStr.indexOf("critical") != -1 && indoorUnit.alarmStatus =='New' ){              		
	                		_openoducriticalPopup(indoorUnit);
	                		}
	                	else if ((severityStr.indexOf("critical") != -1 ||severityStr.indexOf("non-critical") != -1) && indoorUnit.alarmStatus =='Fixed' ){              		
	                		_openodufixedPopup(indoorUnit);
	                		}
	        			            		            	
	           	            
	            //updateAcContorl(indoorUnit.id);
	            if (GLOBAL_VAL.clickUnitIcon == true) {
	                $("acDetailScrollDiv").mCustomScrollbar("scrollTo", "#ODU" + oduId + "tr");
	            } else {
	                GLOBAL_VAL.clickUnitIcon = true;
	               // GLOBAL_VAL.floorMap.setView([indoorUnit.maxlatitude, indoorUnit.minlongitude]);
	            }
			}

              return false;
          }, "post");
      return false;
        });
        $(document).on('click', 'a.ODUConnectedLink', function() {
            var IDUId = $(this).attr("data-IDUId");
            var groupId = $(this).attr("data-groupId");
            if (IDUId != -1 && groupId != -1) {
                GLOBAL_VAL.connectedIDUId = IDUId;
                storeMapZoom();
                $("#groupMenuItem" + groupId).click();
            }
            return false;
        });

//        $(document).on('click', '#map .levelPanelButton', function() {
//            var groupId = $(this).attr("group-id");
//            storeMapZoom();
//            $("#" + GLOBAL_VAL.groupMenuItemPrefix + groupId).addClass("active").click();
//            return false;
//        });
        
        $(document).on('click', 'a.IDUViewMapLink', function(e) {
            /*GLOBAL_VAL.clickUnitIcon = false;
            $('#acMapTabLink').click();
            $("#IDU" + $(this).attr("data-unitid")).click();*/
            
            $('#acMapTabLink').click();
        	$("#groupSelects").val(parseInt($(this).attr("data-svgId")));
        	
        	updateODUFloorMap($(this).attr("data-svgId"),$(this).attr("data-unitid"),"IDU");
        	
        });
        
        $(document).on('click', 'a.ODUViewMapLink', function(e) {
        	$('#acMapTabLink').click();
        	$("#groupSelects").val(parseInt($(this).attr("data-svgId")));    	
        	updateODUFloorMap($(this).attr("data-svgId"),$(this).attr("data-unitid"),"ODU");
        });
        
        //map group select 
        $(document).on('change', '#groupSelects', function(e) {
        	/*var selectedGroupId = $(this).find(":selected").attr("id");
        	updateFloorMap([selectedGroupId],isOnlyRefreshMap);*/
        	
        	updateonFloorMap($(this).val());
        });
        
      //map legend button
        $(document).on('click', '#legendButton', function(e) {
        	$(this).find("span.glyphicon").toggleClass("glyphicon-chevron-up").toggleClass("glyphicon-chevron-down");
        	$('div.legendBody').toggle();
        });
        
    }

    function _opencriticalPopup(indoorUnit) {
    	
    	
    	
    	
		var content = '<div style="width:195px;"><div style="background-color:#e6e6e6;font-size:15px;color:#4d4d4d;" class="title text-center mapthickbold">'+indoorUnit.area+'</div>'+'<div style="background-color:#e6e6e6;font-size:15px;color:#4d4d4d;" class="title text-center mapbold">'+indoorUnit.deviceName+'</div>'+
						'<div class="settingBody">'+'<br>'+'<p style="padding:0;margin:0px 5px;">Current Settings:</p>'+
						'<div class="settingContent" style="background-color:#e6e6e6">'+
						'<table><tr><td><img src='+GLOBAL_VAL.imgPath+'popup/Map_current_Status_icon_Outdoor_Temp.png /></td>'+
							'<td><img src='+GLOBAL_VAL.imgPath+'popup/Map_current_Status_icon_Indoor_Temp.png /></td>'+
							'<td><img src='+GLOBAL_VAL.imgPath+'popup/Map_current_Status_icon_Fan_Speed.png /></td>'+
							'<td><img src='+GLOBAL_VAL.imgPath+'popup/Map_current_Status_icon_Flap.png /></td>'+
							'<td><img src='+GLOBAL_VAL.imgPath+'popup/Map_current_Status_icon_Energy_Savings.png /></td></tr>'+
							'<tr><td class="text-center"><span>'+indoorUnit.temperature+'</span></td>'+
							'<td class="text-center"><span>'+indoorUnit.roomTemp+'</span></td>'+
							'<td class="text-center"><span>'+indoorUnit.fanSpeed+'</span></td>'+
							'<td class="text-center"><span>'+indoorUnit.flapMode+'</span></td>'+
							'<td class="text-center"><span>'+indoorUnit.energy_saving+'</span></td></tr></table>'+
						'</div>'+'<br>'+'<p style="padding:0;margin:0px 5px;">Alarm Status:</p>'+
						'<button type="button" class="b b-criticaldefaul"><img src='+GLOBAL_VAL.imgPath+'critical.png />&nbsp&nbsp <b style="size:15px;">'+indoorUnit.severity+'</b></button>'+
						'<p style="padding:0;margin:0px 5px;">Error Code: '+indoorUnit.code+'</p>'+
						'<p style="padding:0;margin:0px 5px;">Occurred Date/Time:</p>'+
						'<p style="padding:0;margin:0px 5px;">'+indoorUnit.alarmOccurred+'</p>'+
						'<div style="padding:0 5px;"><p>IDU Model Number:'+indoorUnit.deviceModel + '</p><p>ODU1 Name:</p>'+
						'</div></div>';
    	
    	var middlelatlng = L.latLng(parseFloat(indoorUnit.maxlatitude), (parseFloat(indoorUnit.maxlongitude) + parseFloat(indoorUnit.minlongitude)) / 2);
        var popup = L.popup().setLatLng(middlelatlng).setContent(content).openOn(GLOBAL_VAL.floorMap);
    }

    function _opennoncriticalPopup(indoorUnit) {
    	
    	
		var content = '<div style="width:195px;"><div style="background-color:#e6e6e6;font-size:15px;color:#4d4d4d;" class="title text-center mapthickbold">'+indoorUnit.area+'</div>'+'<div style="background-color:#e6e6e6;font-size:15px;color:#4d4d4d;" class="title text-center mapbold">'+indoorUnit.deviceName+'</div>'+
						'<div class="settingBody">'+'<br>'+'<p style="padding:0;margin:0px 5px;">Current Settings:</p>'+
						'<div class="settingContent" style="background-color:#e6e6e6">'+
						'<table><tr><td><img src='+GLOBAL_VAL.imgPath+'popup/Map_current_Status_icon_Outdoor_Temp.png /></td>'+
							'<td><img src='+GLOBAL_VAL.imgPath+'popup/Map_current_Status_icon_Indoor_Temp.png /></td>'+
							'<td><img src='+GLOBAL_VAL.imgPath+'popup/Map_current_Status_icon_Fan_Speed.png /></td>'+
							'<td><img src='+GLOBAL_VAL.imgPath+'popup/Map_current_Status_icon_Flap.png /></td>'+
							'<td><img src='+GLOBAL_VAL.imgPath+'popup/Map_current_Status_icon_Energy_Savings.png /></td></tr>'+
							'<tr><td class="text-center"><span>'+indoorUnit.temperature+'</span></td>'+
							'<td class="text-center"><span>'+indoorUnit.roomTemp+'</span></td>'+
							'<td class="text-center"><span>'+indoorUnit.fanSpeed+'</span></td>'+
							'<td class="text-center"><span>'+indoorUnit.flapMode+'</span></td>'+
							'<td class="text-center"><span>'+indoorUnit.energy_saving+'</span></td></tr></table>'+
						'</div>'+'<br>'+'<p style="padding:0;margin:0px 5px;">Alarm Status:</p>'+
						'<button type="button" class="b b-noncriticaldefaul"><img src='+GLOBAL_VAL.imgPath+'noncritical.png />&nbsp&nbsp <b style="size:15px;">'+indoorUnit.severity+'</b></button>'+
						'<p style="padding:0;margin:0px 5px;">Error Code: '+indoorUnit.code+'</p>'+
						'<p style="padding:0;margin:0px 5px;">Occurred Date/Time:</p>'+
						'<p style="padding:0;margin:0px 5px;">'+indoorUnit.alarmOccurred+'</p>'+
						'<div style="padding:0 5px;"><p>IDU Model Number:'+indoorUnit.deviceModel + '</p><p>ODU1 Name:</p>'+
						'</div></div>';    	
    	var middlelatlng = L.latLng(parseFloat(indoorUnit.maxlatitude), (parseFloat(indoorUnit.maxlongitude) + parseFloat(indoorUnit.minlongitude)) / 2);
        var popup = L.popup().setLatLng(middlelatlng).setContent(content).openOn(GLOBAL_VAL.floorMap);
    }
    
function _openfixedPopup(indoorUnit) {
    	
    	var fixed= 'Fixed';
		var content = '<div style="width:195px;"><div class="title text-center mapthickbold">'+indoorUnit.area+'</div>'+'<div class="title text-center mapbold">'+indoorUnit.deviceName+'</div>'+
						'<div class="settingBody">'+'<br>'+'<p style="padding:0;margin:0px 5px;">Current Settings:</p>'+
						'<div class="settingContent greyBackground">'+
						'<table><tr><td><img src='+GLOBAL_VAL.imgPath+'popup/Map_current_Status_icon_Outdoor_Temp.png /></td>'+
							'<td><img src='+GLOBAL_VAL.imgPath+'popup/Map_current_Status_icon_Indoor_Temp.png /></td>'+
							'<td><img src='+GLOBAL_VAL.imgPath+'popup/Map_current_Status_icon_Fan_Speed.png /></td>'+
							'<td><img src='+GLOBAL_VAL.imgPath+'popup/Map_current_Status_icon_Flap.png /></td>'+
							'<td><img src='+GLOBAL_VAL.imgPath+'popup/Map_current_Status_icon_Energy_Savings.png /></td></tr>'+
							'<tr><td class="text-center"><span>'+indoorUnit.temperature+'</span></td>'+
							'<td class="text-center"><span>'+indoorUnit.roomTemp+'</span></td>'+
							'<td class="text-center"><span>'+indoorUnit.fanSpeed+'</span></td>'+
							'<td class="text-center"><span>'+indoorUnit.flapMode+'</span></td>'+
							'<td class="text-center"><span>'+indoorUnit.energy_saving+'</span></td></tr></table>'+
						'</div>'+'<br>'+'<p style="padding:0;margin:0px 5px;">Alarm Status:</p>'+
						'<button type="button" class="b b-fixeddefaul"><b class="fixed" style="size:15px;">'+fixed+'</b></button>'+
						'<p style="padding:0;margin:0px 5px;">Latest Error Code: '+indoorUnit.code+'</p>'+
						'<p style="padding:0;margin:0px 5px;">Latest Fixed Date/Time:</p>'+
						'<p style="padding:0;margin:0px 5px;">'+indoorUnit.alarmFixed+'</p>'+
						'<div style="padding:0 5px;"><p>IDU Model Number:'+indoorUnit.deviceModel + '</p><p>ODU1 Name:</p>'+
						'</div></div>';    	
    	var middlelatlng = L.latLng(parseFloat(indoorUnit.maxlatitude), (parseFloat(indoorUnit.maxlongitude) + parseFloat(indoorUnit.minlongitude)) / 2);
        var popup = L.popup().setLatLng(middlelatlng).setContent(content).openOn(GLOBAL_VAL.floorMap);
    }
    
    
		function _openoducriticalPopup(indoorUnit) {
			
			
			
			
			var content = '<div style="width:195px;"><div class="title text-center mapthickbold">'+indoorUnit.site+'</div>'+'<div class="title text-center mapbold">'+indoorUnit.deviceName+'</div>'+
							'<div class="settingBody">'+
							'<div class="">'+							
							'</div>'+'<br>'+'<p style="padding:0;margin:0px 5px;">Alarm Status:</p>'+
							'<button type="button" class="b b-criticaldefaul"><img src='+GLOBAL_VAL.imgPath+'critical.png />&nbsp&nbsp <b style="color:white;size:15px;">'+indoorUnit.severity+'</b></button>'+
							'<p style="padding:0;margin:0px 5px;">Error Code: '+indoorUnit.code+'</p>'+
							'<p style="padding:0;margin:0px 5px;">Occurred Date/Time:</p>'+
							'<p style="padding:0;margin:0px 5px;">'+indoorUnit.alarmOccurred+'</p>'+
							
							'</div></div>';
			
			var middlelatlng = L.latLng(parseFloat(indoorUnit.maxlatitude), (parseFloat(indoorUnit.maxlongitude) + parseFloat(indoorUnit.minlongitude)) / 2);
		    var popup = L.popup().setLatLng(middlelatlng).setContent(content).openOn(GLOBAL_VAL.floorMap);
		}
		
		function _openodunoncriticalPopup(indoorUnit) {
			
			
			var content = '<div style="width:195px;"><div class="title text-center mapthickbold">'+indoorUnit.site+'</div>'+'<div class="title text-center mapbold">'+indoorUnit.deviceName+'</div>'+
							'<div class="settingBody">'+
							'<div class="">'+							
							'</div>'+'<br>'+'<p style="padding:0;margin:0px 5px;">Alarm Status:</p>'+
							'<button type="button" class="b b-noncriticaldefaul"><img src='+GLOBAL_VAL.imgPath+'noncritical.png />&nbsp&nbsp <b style="color:white;size:15px;">'+indoorUnit.severity+'</b></button>'+
							'<p style="padding:0;margin:0px 5px;">Error Code: '+indoorUnit.code+'</p>'+
							'<p style="padding:0;margin:0px 5px;">Occurred Date/Time:</p>'+
							'<p style="padding:0;margin:0px 5px;">'+indoorUnit.alarmOccurred+'</p>'+
							
							'</div></div>';    	
			var middlelatlng = L.latLng(parseFloat(indoorUnit.maxlatitude), (parseFloat(indoorUnit.maxlongitude) + parseFloat(indoorUnit.minlongitude)) / 2);
		    var popup = L.popup().setLatLng(middlelatlng).setContent(content).openOn(GLOBAL_VAL.floorMap);
		}
		
		function _openodufixedPopup(indoorUnit) {
			
			var fixed= 'Fixed';
			var content = '<div style="width:195px;"><div class="title text-center mapthickbold">'+indoorUnit.site+'</div>'+'<div class="title text-center mapbold">'+indoorUnit.deviceName+'</div>'+
							'<div class="settingBody">'+
							'<div class="">'+							
							'</div>'+'<br>'+'<p style="padding:0;margin:0px 5px;">Alarm Status:</p>'+
							'<button type="button" class="b b-fixeddefaul"><b class="fixed" style="size:15px;">'+fixed+'</b></button>'+
							'<p style="padding:0;margin:0px 5px;">Latest Error Code: '+indoorUnit.code+'</p>'+
							'<p style="padding:0;margin:0px 5px;">Latest Fixed Date/Time:</p>'+
							'<p style="padding:0;margin:0px 5px;">'+indoorUnit.alarmFixed+'</p>'+						
							'</div></div>';    	
			var middlelatlng = L.latLng(parseFloat(indoorUnit.maxlatitude), (parseFloat(indoorUnit.maxlongitude) + parseFloat(indoorUnit.minlongitude)) / 2);
		    var popup = L.popup().setLatLng(middlelatlng).setContent(content).openOn(GLOBAL_VAL.floorMap);
		}

   
    	
    function _openPopupForODU(data) {
        var content = "address:" + data.address + "</br>ratio:" + data.utilizationRatio + "</br>time1:" + data.workingTime1;
        var linkedIdus = data.linkedIduUnitList;
        if (linkedIdus != null && typeof(linkedIdus) != "undefined") {
            content += "</br>linkedIDU:";
            for (var i = 0; i < linkedIdus.length; i++) {
                if (i == 0) {
                    content += "<a class='ODUConnectedLink' data-IDUId=" + linkedIdus[i].id + " data-groupId=" + linkedIdus[i].groupId + ">IDU" + linkedIdus[i].id + "</a>";
                } else {
                    content += ","
                    if (i % 3 == 1) {
                        content += "</br>";
                    }
                    content += "<a class='ODUConnectedLink' data-IDUId=" + linkedIdus[i].id + " data-groupId=" + linkedIdus[i].groupId + ">IDU" + linkedIdus[i].id + "</a>";
                }
            }
        }
        var middlelatlng = L.latLng(parseFloat(data.svgMaxLatitude), (parseFloat(data.svgMaxLongitude) + parseFloat(data.svgMinLongitude)) / 2);
        var popup = L.popup().setLatLng(middlelatlng).setContent(content).openOn(GLOBAL_VAL.floorMap);
    }
    
 
    
    
    function bindTabEvent(){
        $(document).on("click", "#inner a[data-toggle='tab']", function() {
            var target = $(this).attr("href");
            if (target == "#acMapTab" && GLOBAL_VAL.floorMap != null) {
            	GLOBAL_VAL.floorMap.invalidateSize();
			} else {
				$(target + " .dataTables_scrollBody").mCustomScrollbar("update");
				$(target + " .dataTables_scroll").resize();
			}
        });
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
       
        
        updateData(GLOBAL_VAL.groupid);
        
        // getFirstCompany then generateMap
        updateCompanyMap();
        // ajax then get alarm notification
        updateAlarmNotificationChart();

        // special config
        $(window).resize(function() {
            homeScreenData();
        });
        homeScreenData();
        
       
    }


    function initMonitoringCtrl() {
        // clear
        // Move modal to body
        // Fix Bootstrap backdrop issue with animation.css
        $('.modal').appendTo("body");
        // ajax then update acControl
//        updateAcContorl();
        if(!hasSelectedGroupInSession()){
        	initIDUDetailTable("");       	
        	return;
        }
        /*var idList = getCheckedControlGroupIds();
        if(idList.length == 0){
        	initIDUDetailTable("");        	
        	return;
        }
        updateFloorMap(idList);*/        
    }

    function initIDUDetailTable(detailHtml){
        if (GLOBAL_VAL.IDUDetailTable != null) {
        	GLOBAL_VAL.IDUDetailTable.destroy();
		}
        $("#acDetailTbody").html(detailHtml);
        
        //Sorting logic for alphanumeric types
        var reA = /[^a-zA-Z]/g;
        var reN = /[^0-9]/g;
        function sortAlphaNum(a,b) {
            var aA = a.replace(reA, "");
            var bA = b.replace(reA, "");
            if(aA === bA) {
                var aN = parseInt(a.replace(reN, ""), 10);
                var bN = parseInt(b.replace(reN, ""), 10);
                return aN === bN ? 0 : aN > bN ? 1 : -1;
            } else {
                return aA > bA ? 1 : -1;
            }
        }
        
        $.fn.dataTable.moment( 'D/M/YYYY HH:mm' );    
        jQuery.fn.dataTableExt.oSort['natural-asc']  = function(a,b) {
            return sortAlphaNum(a,b);
        };

        jQuery.fn.dataTableExt.oSort['natural-desc'] = function(a,b) {
            return sortAlphaNum(a,b) * -1;
        };     
        GLOBAL_VAL.IDUDetailTable = $('#IDUDetailTable').DataTable({    
     
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
   			"search": {"caseInsensitive": false},
            "aoColumnDefs": [                             
                             { 'bSortable': true, 'aTargets': [ 5 ] ,"orderSequence": [ "desc", "asc"]  },
                             { 'bSortable': true, 'aTargets': [ 6 ] ,"orderSequence": [ "desc", "asc"]  },
                           //  { 'bSortable': true, 'aTargets': [ 7 ] ,"orderSequence": [ "desc", "asc"],'type':'natural'},
                             { 'bSortable': true, 'aTargets': [ 7 ] ,"orderSequence": [ "desc", "asc"]  },
                             { 'bSortable': true, 'aTargets': [ 4 ] ,"orderSequence": [ "desc", "asc"],'type':'natural'}                                                        
                          ]
      
        });
    }
    
    
    
    function updateIDUDetailTableAndMap(checkedControlGroupIds) {
    	
    	_refreshMapAndACDetailByGroupId(checkedControlGroupIds);
        
//        var selectedGroup = GLOBAL_VAL.selectedGroup;
//        var selectedGroup = $("#groupMenuItem"+checkedControlGroupIds[0]);
//        var svgname = selectedGroup.attr("group-svg");
//        _refreshMapAndACDetailByGroupId(selectedGroup.attr("group-id"));
    }

    function _refreshMapAndACDetailByGroupId(groupIds) {
    	$.axs("../notification/getNotificationDetails.htm", {
            "json_request": JSON.stringify({"id":groupIds,"idType":"group","severity":['critical','non-critical'],"alarmType":'all',"status":['fixed','new'],"alarmOccurredStartDate":'01/01/2012',"alarmOccurredEndDate":today,"addCustName":"yes"})
        }, function(data) {console.log(data);
        
        	GLOBAL_VAL.map_data_object = data;
        	
        	//console.log(JSON.stringify(GLOBAL_VAL.map_data_object));
            var acDetailTableHtml1 = "";
            var groupOptionHtml = "";
            /*var acTableHtml1 = "";*/
            _clearSvgIDURelationship();
            
            if (data != null && typeof(data) != "undefined" && typeof(data.errorMessage) == "undefined") {
            	//demo 
            	var displaySvgName = "panasonic_demo.svg";
            	var iduList = data.notificationList;            	
            	if (typeof(iduList[0]) != "undefined") {
            		if (iduList[0].svg_Location == "") {
            			displaySvgName = "panasonic_demo.svg";
					}else {
						displaySvgName = iduList[0].svg_Location;
					}
				}
            	 
            	setUnitIconsInMap("svg/"+displaySvgName);
        		
        		var groupNameArray = [];
            	for (var i = 0; i < iduList.length; i++) {
                    var unitInfo = iduList[i];
                    
                    _saveSvgIdIDUListRelationship(unitInfo.svgId,unitInfo.iduId);
                    
                    var IDUSvgName = unitInfo.svg_Location;
                    if (iduList[0].svg_Location == "") {
                    	IDUSvgName = "panasonic_demo.svg";
					}
                    
                    if (displaySvgName == IDUSvgName && unitInfo.deviceType == "IDU") {
                    	addImageLayer(unitInfo, "IDU");
					}else if (displaySvgName == IDUSvgName){
						addImageLayer(unitInfo, "ODU");
					}
                    
                    if (groupNameArray.indexOf(IDUSvgName) == -1  && IDUSvgName != '-') {
                    	groupNameArray.push(IDUSvgName);
                    	groupOptionHtml += _createOptionForGroupDropDown(unitInfo.svgId,unitInfo.svgDisplayName);
					}
                    
                    acDetailTableHtml1 += getACDetailLineHtml(unitInfo);
				}
            		  		  
            }
                                                            
            initIDUDetailTable(acDetailTableHtml1);
            
            $("#groupSelects").html(groupOptionHtml);
            
            $("#IDUDetailTable_wrapper .dataTables_scrollBody").mCustomScrollbar({
                scrollButtons: {
                    enable: true
                },
                theme: "dark-2"
            });
        },"post");
    }

    function _refreshMapAndNotificationDetailByGroupId(groupIds) {
    	$.axs("../notification/getNotificationDetails.htm", {
            "json_request": JSON.stringify({"id":groupIds,"idType":"group","severity":['critical','non-critical'],"alarmType":'all',"status":['fixed','new'],"alarmOccurredStartDate":'01/01/2012',"alarmOccurredEndDate":today,"addCustName":"yes"})
        }, function(data) {console.log(data);
        
        	GLOBAL_VAL.map_data_object = data;
        	          
            if (data != null && typeof(data) != "undefined" && typeof(data.errorMessage) == "undefined") {
            	//demo 
            	var displaySvgName = "panasonic_demo.svg";
            	var iduList = data.notificationList;            	
            	if (typeof(iduList[0]) != "undefined") {
            		if (iduList[0].svg_Location == "") {
            			displaySvgName = "panasonic_demo.svg";
					}else {
						displaySvgName = iduList[0].svg_Location;
					}
				}
            	 
            	setUnitIconsInMap("svg/"+displaySvgName);
        		
        		var groupNameArray = [];
            	for (var i = 0; i < iduList.length; i++) {
                    var unitInfo = iduList[i];
                    
                    
                    
                    var IDUSvgName = unitInfo.svg_Location;
                    if (iduList[0].svg_Location == "") {
                    	IDUSvgName = "panasonic_demo.svg";
					}
                    
                    if (displaySvgName == IDUSvgName && unitInfo.deviceType == "IDU") {
                    	addImageLayer(unitInfo, "IDU");
					}else if (displaySvgName == IDUSvgName){
						addImageLayer(unitInfo, "ODU");
					}
                    
                    
                  
				}
            		  		  
            }
                                                            
            
        },"post");
    }
    function _clearSvgIDURelationship(){
    	GLOBAL_VAL.svgIdIDUListMap = {};
    }
    
    
    function _saveSvgIdIDUListRelationship(svgId,iduId){
    	var iduList = (GLOBAL_VAL.svgIdIDUListMap)[svgId];
    	if (typeof(iduList) == "undefined") {
    		iduList = [];
		}
    	iduList.push(iduId);
    	(GLOBAL_VAL.svgIdIDUListMap)[svgId] = iduList;
    }
    
    
    
    function _createOptionForGroupDropDown(svgId,displayName){
    	return "<option value="+svgId+">"+displayName+"</option>";;
    }
    
    function updateFloorMap(svgId,selectedId,unitType){
    	
    	     dataobject = GLOBAL_VAL.map_data_object;
        if (GLOBAL_VAL.map_data_object != null && typeof(GLOBAL_VAL.map_data_object) != "undefined") {
        	
        	if (selectedId != null && typeof(selectedId) != "undefined") { console.log(unitType);
    		    $("#"+unitType + selectedId).click();
        	}else if(unitType == "IDU"){
        		_displayIDUInFloorMap(dataobject); 
        	}else {
        		_displayODUInFloorMap(dataobject); 
        	}	
        }
   
    }
    
    function updateonFloorMap(svgId,selectedId,unitType){ 
    	
	     dataobject = GLOBAL_VAL.map_data_object;
	     console.log("dataobe");
	     console.log(dataobject);
	     var dataobj = {notificationList: []};
	     for(var i = 0; i < dataobject.notificationList.length; i++){
	    	 if(dataobject.notificationList[i].svgId == svgId){
	    		 dataobj.notificationList.push(dataobject.notificationList[i])
	    		 
	    	 }
	    	 
	     }
	     console.log(dataobj)
	        if (GLOBAL_VAL.map_data_object != null && typeof(GLOBAL_VAL.map_data_object) != "undefined") {
	        	
	             	//demo 
	             	var displaySvgName = "panasonic_demo.svg";
	             	var iduList = dataobj.notificationList;            	
	             	if (typeof(iduList[0]) != "undefined") {
	             		if (iduList[0].svg_Location == "") {
	             			displaySvgName = "panasonic_demo.svg";
	 					}else {
	 						displaySvgName = iduList[0].svg_Location;
	 					}
	 				}
	             	 
	             	setUnitIconsInMap("svg/"+displaySvgName);
	         		
	         		var groupNameArray = [];
	             	for (var i = 0; i < iduList.length; i++) {
	                     var unitInfo = iduList[i];
	                     	                    	                     
	                     var IDUSvgName = unitInfo.svg_Location;
	                     if (iduList[0].svg_Location == "") {
	                     	IDUSvgName = "panasonic_demo.svg";
	 					}
	                     
	                     if (displaySvgName == IDUSvgName && unitInfo.deviceType == "IDU") {
	                     	addImageLayer(unitInfo, "IDU");
	 					}else if (displaySvgName == IDUSvgName){
	 						addImageLayer(unitInfo, "ODU");
	 					}
	                     	                    	                     
	 				}
	             		  		  
	             

	        }
    }
    
    
    
    
    function updateODUFloorMap(svgId,selectedId,unitType){ 
    	
	     dataobject = GLOBAL_VAL.map_data_object;
	     console.log("dataobe");
	     console.log(dataobject);
	     var dataobj = {notificationList: []};
	     for(var i = 0; i < dataobject.notificationList.length; i++){
	    	 if(dataobject.notificationList[i].svgId == svgId){
	    		 dataobj.notificationList.push(dataobject.notificationList[i])
	    		 
	    	 }
	    	 
	     }
	     console.log(dataobj)
	        if (GLOBAL_VAL.map_data_object != null && typeof(GLOBAL_VAL.map_data_object) != "undefined") {
	        	
	             	//demo 
	             	var displaySvgName = "panasonic_demo.svg";
	             	var iduList = dataobj.notificationList;            	
	             	if (typeof(iduList[0]) != "undefined") {
	             		if (iduList[0].svg_Location == "") {
	             			displaySvgName = "panasonic_demo.svg";
	 					}else {
	 						displaySvgName = iduList[0].svg_Location;
	 					}
	 				}
	             	 
	             	setUnitIconsInMap("svg/"+displaySvgName);
	         		
	         		var groupNameArray = [];
	             	for (var i = 0; i < iduList.length; i++) {
	                     var unitInfo = iduList[i];
	                     	                    	                     
	                     var IDUSvgName = unitInfo.svg_Location;
	                     if (iduList[0].svg_Location == "") {
	                     	IDUSvgName = "panasonic_demo.svg";
	 					}
	                     
	                     if (displaySvgName == IDUSvgName && unitInfo.deviceType == "IDU") {
	                     	addImageLayer(unitInfo, "IDU");
	 					}else if (displaySvgName == IDUSvgName){
	 						addImageLayer(unitInfo, "ODU");
	 					
	 					}
	                     	                    	                     
	 				}
	             		  		  	             		 						
	 						if (selectedId != null && typeof(selectedId) != "undefined") { console.log(unitType);
	 		    		    $("#"+unitType + selectedId).click();
	 						}
	 		        	
	        }
   }
    
    
    
    
    
    function _displayIDUInFloorMap(dataobj){ 
    	var svgName = "panasonic_demo.svg";
    	var iduList = dataobj.notificationList;
    	//console.log(JSON.stringify(iduList));
    	if (typeof(iduList[0]) != "undefined") {
    		if (iduList[0].svgDisplayName == "") {
    			svgName = "panasonic_demo.svg";
			}else {
				svgName = iduList[0].svg_Location;
				console.log(svgName);
			}
		}
    	 
		setUnitIconsInMap("svg/"+svgName);
		
    	for (var i = 0; i < iduList.length; i++) {
            var unitInfo = iduList[i];
            var IDUSvgName = unitInfo.svg_Location;
            if (iduList[0].svgDisplayName == "") {
            	IDUSvgName = "panasonic_demo.svg";
			}
            
            if (svgName == IDUSvgName && _isInSelectedGroup(unitInfo.svgId,unitInfo.iduId)) {
            	//addImageLayer(unitInfo, "IDU");
			}
		}
    }
    
    function _displayODUInFloorMap(dataobject){
    	var svgName = "XMU-RF-with-ODU-01.svg";
    	if (typeof(dataobject[0]) != "undefined" && dataobject[0].svg_Location != "") {
    		svgName = dataobject[0].svg_Location;
		}
    	 
		setUnitIconsInMap("svg/"+svgName);
		
    	for (var i = 0; i < dataobject.length; i++) {
            var unitInfo = dataobject[i];
            var IDUSvgName = unitInfo.svg_Location;
            if (dataobject[0].svg_Location == "") {
            	IDUSvgName = "XMU-RF-with-ODU-01.svg";
			}
            
            if (svgName == IDUSvgName) {
            	addImageLayer(unitInfo, "ODU");
			}
		}
    }
    
    function setUnitIconsInMap(svgname){
        if (svgname != GLOBAL_VAL.curGroupSvg) {
        	GLOBAL_VAL.curGroupSvg = svgname;
        	if (GLOBAL_VAL.floorMap != null) {
        		GLOBAL_VAL.floorMap.remove();
			}
        	GLOBAL_VAL.floorMap = null;
		}
        var map = _getMapBackground();
        //console.log(map._layers);
        L.tileLayer('', {
            maxZoom: 23,
            minZoom: 20,
            id: 'srihost.5d5783b8',
            accessToken: 'pk.eyJ1Ijoic3JpaG9zdCIsImEiOiI5YjBmYzdmZjYyNGNlMjljMmQ3NDIzM2ZhNTlkNDcyMyJ9.rJFx58Zxheg2VxZOC-vS6w'
        }).addTo(map);
        GLOBAL_VAL.floorMap = map;
        
        var imageBounds = [
                           [1.3223993770376097, 103.93094598781317],
                           [1.3213348234354438, 103.9319410873577]
                       ];
        L.imageOverlay(GLOBAL_VAL.imgPath + svgname, imageBounds, {
            id: "building_first_floor"
        }).addTo(map);
        
		$("#map .leaflet-control-attribution").empty();
    }

    function getACDetailLineHtml(unitVO) {
        var refUnitId = _getUnitId(unitVO)
       /* return "<tr id=" + refUnitId + "tr refDomId=" + refUnitId + "><td  class='acDetailsHeaderCheckbox'><input id=cb"+ unitVO.notificationID +"  class='acDetailCheckbox bizCheckbox'     type='checkbox' data-unitid=" + unitVO.iduId +" /><label for=cb"+ unitVO.notificationID + "></label></td>" +
		getTDHtml(unitVO.alarmOccurred, "acDetailsSite",sorting=true) + getTDHtml(unitVO.notificationID, "acDetailsLoc") + getTDHtml(unitVO.site, "acDetailsPC") + 
		getTDHtml(unitVO.area, "acDetailsName") + getTDHtml(unitVO.deviceName, "acDetailsStatus") + 
		getTDHtml(unitVO.severity, "acDetailsTemp"+getseverityFontClass(unitVO.severity,unitVO.alarmStatus)+unitVO.severity.toLowerCase()+"SeverityFont") + getTDHtml(unitVO.code, "acDetailsRoomTemp") + getTDHtml(unitVO.notificationName, "acDetailsMode") + 
		getTDHtml(unitVO.counterMeasure, "acDetailsFanSpeed")  getTDHtml(unitVO.alarmFixed, "acDetailsWind") + getTDHtml(unitVO.alarmStatus, "acDetailsESaving")+					
		getTDHtml('<a class="IDUViewMapLink" data-unitid='+ unitVO.iduId+' href="javascript:void(0);"><span class="fa fa-map-marker"></span>VIew</a>',"acDetailsViewMap") + "</tr>";*/
        
        
        //return "<tr id=" + refUnitId + "tr refDomId=" + refUnitId + "><td  class='acDetailsHeaderCheckbox'><input id=cb"+ unitVO.notificationID +"  class='acDetailCheckbox bizCheckbox'     type='checkbox' data-unitid=" + unitVO.iduId +" /><label for=cb"+ unitVO.notificationID + "></label></td>" +
	//	getTDHtml(unitVO.site, "acDetailsPC") + getTDHtml(unitVO.area, "acDetailsName") + 
//		getTDHtml(unitVO.severity, "acDetailsTemp"+getseverityFontClass(unitVO.severity,unitVO.alarmStatus)/*+unitVO.severity.toLowerCase()+"SeverityFont"*/) +
	//	getTDHtml('<a class="severityLink" data-toggle="modal" data-target="#alarmDetailDialog" data-unitid='+ unitVO.iduId+' href="javascript:void(0);">'+unitVO.severity+'</a>',"acDetailsTemp"+getseverityFontClass(unitVO.severity,unitVO.alarmStatus)) +
        
	//	getTDHtml(unitVO.code, "acDetailsRoomTemp") + getTDHtml(unitVO.alarmOccurred, "acDetailsSite",sorting=true) + 
	//	getTDHtml(unitVO.notificationID, "acDetailsLoc") + getTDHtml(unitVO.deviceName, "acDetailsStatus") + /*getTDHtml(unitVO.notificationName, "acDetailsMode") + 
	//	getTDHtml(unitVO.counterMeasure, "acDetailsFanSpeed")*/  getTDHtml(unitVO.alarmFixed, "acDetailsWind") + getTDHtml(unitVO.alarmStatus, "acDetailsESaving")+					
	//	getTDHtml('<a class="IDUViewMapLink" data-unitid='+ unitVO.iduId+' data-svgId='+ unitVO.svgId+' href="javascript:void(0);"><span class="fa fa-map-marker"></span>VIew</a>',"acDetailsViewMap") + "</tr>";
        //add by shanf, if alarm code is "filter" then display filter icon
        var alarmCodeHtml = unitVO.code;
        if (alarmCodeHtml != null && alarmCodeHtml.toLowerCase() == "filter") {
        	alarmCodeHtml = "<div class='filterAlarmIcon'></div>";
		}
        if(items.indexOf("component-User Account/Update User/customerIdTree") != -1 || items.indexOf("navi-CA Installation") != -1){
        	//Changed TO GUI chnages.
            return "<tr ID=" + unitVO.notificationID +" id=" + refUnitId + "tr refDomId=" + refUnitId + "><td  class='acDetailsHeaderCheckbox'><input id=cb"+ unitVO.notificationID +"  class='acDetailCheckbox bizCheckbox'     type='checkbox' data-notifid=" + unitVO.notificationID +" data-unitid=" + unitVO.iduId +" /><label for=cb"+ unitVO.notificationID + "></label></td>" +
            getTDHtml(unitVO.customerName, "acDetailsCC") +  getTDHtml(unitVO.site, "acDetailsPC") +
            getTDHtml(unitVO.area, "acDetailsName")  + getTDHtml('<a class="modelNumberLink" data-unitid='+ unitVO.notificationID+ ' data-deviceModel='+ unitVO.deviceModel+' href="javascript:void(0);">'+ unitVO.deviceName +'</a>', "acDetailsStatus") +
            
            //change by shanf, data-toggle="modal" data-target="#alarmDetailDialog" for demo, production no dialog.
//    		getTDHtml('<a class="severityLink" data-toggle="modal" data-target="#alarmDetailDialog" data-unitid='+ unitVO.iduId+' href="javascript:void(0);">'+unitVO.severity+'</a>',"acDetailsTemp"+getseverityFontClass(unitVO.severity,unitVO.alarmStatus)) +
    		getTDHtml('<a class="severityLink" data-unitid='+ unitVO.iduId+' href="javascript:void(0);">'+unitVO.severity+'</a>',"acDetailsTemp"+getseverityFontClass(unitVO.severity,unitVO.alarmStatus)) +
            
    		getTDHtml(alarmCodeHtml, "acDetailsRoomTemp")+ getTDHtml(unitVO.notificationID, "acDetailsLoc")+ getTDHtml(unitVO.alarmOccurred, "acDetailsSite",sorting=true)  + getTDHtml(unitVO.alarmFixed, "acDetailsWind") + getTDHtml(unitVO.alarmStatus, "acDetailsESaving")+					
            (unitVO.deviceType == 'CA' ? getTDHtml("-") :(unitVO.deviceType == 'IDU' ?getTDHtml('<a class="IDUViewMapLink" data-unitid='+ unitVO.iduId+' data-svgId='+ unitVO.svgId+' href="javascript:void(0);"><span class="fa fa-map-marker"></span>View</a>',"acDetailsViewMap") :
    		getTDHtml('<a class="ODUViewMapLink" data-unitid='+ unitVO.oduId+' data-svgId='+ unitVO.svgId+' href="javascript:void(0);"><span class="fa fa-map-marker"></span>View</a>',"acDetailsViewMap") 
            )
            )
            + "</tr>";
        }else {
        //Changed TO GUI chnages.
        return "<tr ID=" + unitVO.notificationID +" id=" + refUnitId + "tr refDomId=" + refUnitId + "><td  class='acDetailsHeaderCheckbox'><input id=cb"+ unitVO.notificationID +"  class='acDetailCheckbox bizCheckbox'     type='checkbox' data-notifid=" + unitVO.notificationID +" data-unitid=" + unitVO.iduId +" /><label for=cb"+ unitVO.notificationID + "></label></td>" +
        getTDHtml(unitVO.customerName, "hidden")+  getTDHtml(unitVO.site, "acDetailsPC") +
        getTDHtml(unitVO.area, "acDetailsName")  + getTDHtml('<a class="modelNumberLink" data-unitid='+ unitVO.notificationID+ ' data-deviceModel='+ unitVO.deviceModel+' href="javascript:void(0);">'+ unitVO.deviceName +'</a>', "acDetailsStatus") +
        
        //change by shanf, data-toggle="modal" data-target="#alarmDetailDialog" for demo, production no dialog.
//		getTDHtml('<a class="severityLink" data-toggle="modal" data-target="#alarmDetailDialog" data-unitid='+ unitVO.iduId+' href="javascript:void(0);">'+unitVO.severity+'</a>',"acDetailsTemp"+getseverityFontClass(unitVO.severity,unitVO.alarmStatus)) +
		getTDHtml('<a class="severityLink" data-unitid='+ unitVO.iduId+' href="javascript:void(0);">'+unitVO.severity+'</a>',"acDetailsTemp"+getseverityFontClass(unitVO.severity,unitVO.alarmStatus)) +
        
		getTDHtml(alarmCodeHtml, "acDetailsRoomTemp")+ getTDHtml(unitVO.notificationID, "acDetailsLoc")+ getTDHtml(unitVO.alarmOccurred, "acDetailsSite",sorting=true)  + getTDHtml(unitVO.alarmFixed, "acDetailsWind") + getTDHtml(unitVO.alarmStatus, "acDetailsESaving")+					
		(unitVO.deviceType == 'CA' ? getTDHtml("-") :(unitVO.deviceType == 'IDU' ?getTDHtml('<a class="IDUViewMapLink" data-unitid='+ unitVO.iduId+' data-svgId='+ unitVO.svgId+' href="javascript:void(0);"><span class="fa fa-map-marker"></span>View</a>',"acDetailsViewMap") :
    		getTDHtml('<a class="ODUViewMapLink" data-unitid='+ unitVO.oduId+' data-svgId='+ unitVO.svgId+' href="javascript:void(0);"><span class="fa fa-map-marker"></span>View</a>',"acDetailsViewMap") 
        )
        )
        + "</tr>";
        }
    }
    
    function getseverityFontClass(severity,alarmStatus)	{
    	var className = "";
    	if (typeof (severity) != "undefined" && severity != null && typeof (alarmStatus) != "undefined" && alarmStatus != null) {
        	var severityStr = severity.toLowerCase();
        	var alarmStatusStr = alarmStatus.toLowerCase();     
        	if (alarmStatusStr.indexOf("fixed") != -1) {className = " fixedModeFont";}
        	else if (alarmStatusStr.indexOf("new") != -1 && severityStr == "critical") {className = " criticalModeFont";}
        	else if (alarmStatusStr.indexOf("new") != -1 && severityStr == "non-critical" ) {className = " noncriticalModeFont";}
		}
    	return className;
    	
    }

    function _isInSelectedGroup(svgId,iduId){
    	var iduList = (GLOBAL_VAL.svgIdIDUListMap)[svgId];
    	return iduList.indexOf(iduId) != -1;
    }

    function getTDHtml(string, className) {
        return "<td class='" + className + "'>" + string + "</td>";
    }

    function _getUnitId(unitVO) {
        var unitNameMap = {
            "true": "IDU",
            "false": "ODU"
        };
        return "IDU" + unitVO.iduId;
    }

    function _showLevelPanel(selectedGroup) {
        var levelItems = selectedGroup.parent().children("li.floorMenuItem");
        if (levelItems != null && typeof(levelItems) != "undefined") {
            var levelPanelHtml = '<div class="leaflet-bottom1 leaflet-right div_floors">' + '<div class="btn-group-vertical zoomBtnContainer leaflet-bar leaflet-control" role="group" id="control_buttons"  aria-label="Vertical button group" style="width: 40px;">';
            levelItems.each(function(index) {
                if ($(this).children() != null && $(this).children().length > 0) {
                    var groupName = $(this).children().eq(0).text().trim();
                    var tempName = groupName.split(" ");
                    var levelShortName = "";
                    for (var i = 0; i < tempName.length; i++) {
                    	levelShortName += tempName[i].charAt(0);
					}
                    levelPanelHtml += '<button type="button" class="btn floors_class levelPanelButton" group-Id=' + $(this).attr("group-id") + '>' + levelShortName + '</button>';
                }
            });
            $("#map .leaflet-top.leaflet-right").html(levelPanelHtml);
        }
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
    	}else {
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
    	    	        		paramsList.push({ "unitId":	 unitId,"value":temp});
    						}
    					}
    					if (paramsList.length > 0) {
//    						paramsList = [{ "unitId": "2","value":22},{ "unitId": "3","value":22},{ "unitId": "4","value":22}] 
    						_sendRemoteACControlRequest("settingTemperature",{"units":paramsList});
    					}
    				}
                });
            }else {
            	var unitInfo = selectedObject.centralAddress.split("-");
            	var controllId = unitInfo[0];
            	var unitId = parseInt(unitInfo[2]).toString();
            	_sendRemoteACControlRequest("settingTemperature",{"units":[{"unitId": unitId,"value":temp}]});
            }
            
    	}
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
    /*function welcomeLoginUser() {
        setTimeout(function() {
            toastr.options = {
                closeButton: true,
                progressBar: true,
                showMethod: 'slideDown',
                timeOut: 2000
            };
            toastr.success('David Williams', 'Welcome to Panasonic Smart Cloud!');
        }, 1000);
    }*/

    function _getMapBackground(mapZoom) {
        var map = GLOBAL_VAL.floorMap;
        if (typeof(mapZoom) == "undefined") {
        	mapZoom = GLOBAL_VAL.mapZoom;
		}
        if (GLOBAL_VAL.floorMap == null) {
            map = L.map('map',{ zoomControl: false }).setView([1.32186844, 103.93149555], mapZoom);
            map.setMaxBounds([
            	[1.3215003655464723, 103.93099598781317],
                [1.3223521814812313, 103.9319410873577]
            ]);
            //legend
            _addLegend(map);
            new L.Control.Zoom({ position: 'bottomright' }).addTo(map);
            
        	map.on('click', function(e) {
        		console.log("latitude: "+ e.latlng.lat+" -- longitude: "+e.latlng.lng);
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
    
    function _addLegend(map){
    	var legend = L.control({position: 'bottomright'});

    	legend.onAdd = function (map) {

    	    var div = L.DomUtil.create('div', 'bizLegend');
    	    div.innerHTML += '<button id = "legendButton" type="button" class="btn bizButton legendButton">Legends<span class="glyphicon glyphicon-chevron-up"></span></button>';
    	    var legendTable = '<div class="legendBody" style="display:none"><table><thead><tr><th>IDU OFF:</th><th class="emptyColumn"></th><th colspan="5">IDU ON:</th><th class="emptyColumn"></th><th colspan="3">ODU ON:</th></tr></thread><tbody>'+
    	    '<tr style="padding-top:10px">'+
    	    	_getLegendImgTdHtml("IDUOff.png") + _getEmptyColumn() + _getLegendImgTdHtml("IDUAuto.png") + _getLegendImgTdHtml("IDUHeat.png") + 
    	    	_getLegendImgTdHtml("IDUDry.png") + _getLegendImgTdHtml("IDUCool.png") + _getLegendImgTdHtml("IDUFan.png") + _getEmptyColumn() +
    	    	_getLegendImgTdHtml("ODUVRF.png") + _getLegendImgTdHtml("ODUPAC.png") + _getLegendImgTdHtml("ODUGHP.png") +
	    	'</tr>'+
    	    '<tr>'+
    	    	_getLegendTextTdHtml("Off") + _getEmptyColumn() + _getLegendTextTdHtml("Auto Mode with </br>No Alarm") + _getLegendTextTdHtml("Heat Mode with </br>No Alarm") + 
    	    	_getLegendTextTdHtml("Dry Mode with </br>No Alarm") + _getLegendTextTdHtml("Cool Mode with </br>No Alarm") + _getLegendTextTdHtml("Fan Mode with </br>No Alarm") + _getEmptyColumn() +
    	    	_getLegendTextTdHtml("VRF with </br>No Alarm") + _getLegendTextTdHtml("PAC with </br>No Alarm") + _getLegendTextTdHtml("GHP with </br>No Alarm") +
	    	'</tr>'+
    	    '<tr>'+
	    		_getLegendImgTdHtml("IDUOffCritical.png") + _getEmptyColumn() + _getLegendImgTdHtml("IDUAutoCritical.png") + _getLegendImgTdHtml("IDUHeatCritical.png") + 
	    		_getLegendImgTdHtml("IDUDryCritical.png") + _getLegendImgTdHtml("IDUCoolCritical.png") + _getLegendImgTdHtml("IDUFanCritical.png") + _getEmptyColumn() +
	    		_getLegendImgTdHtml("ODUVRFCritical.png") + _getLegendImgTdHtml("ODUPACCritical.png") + _getLegendImgTdHtml("ODUGHPCritical.png") +
	    	'</tr>'+
		    '<tr>'+
	    		_getLegendTextTdHtml("Off with </br>Critical Alarm") + _getEmptyColumn() + _getLegendTextTdHtml("Auto Mode with </br>Critical Alarm") + _getLegendTextTdHtml("Heat Mode with </br>Critical Alarm") + 
	    		_getLegendTextTdHtml("Dry Mode with </br>Critical Alarm") + _getLegendTextTdHtml("Cool Mode </br>with Critical Alarm") + _getLegendTextTdHtml("Fan Mode with </br>Critical Alarm") + _getEmptyColumn() +
	    		_getLegendTextTdHtml("VRF with </br>Critical Alarm") + _getLegendTextTdHtml("PAC Mode with </br>Critical Alarm") + _getLegendTextTdHtml("GHP with </br>Critical Alarm") +
	    	'</tr>'+
	    	'<tr>'+
	    		_getLegendImgTdHtml("IDUOffAlarm.png") + _getEmptyColumn() + _getLegendImgTdHtml("IDUAutoAlarm.png") + _getLegendImgTdHtml("IDUHeatAlarm.png") + 
	    		_getLegendImgTdHtml("IDUDryAlarm.png") + _getLegendImgTdHtml("IDUCoolAlarm.png") + _getLegendImgTdHtml("IDUFanAlarm.png") + _getEmptyColumn() +
	    		_getLegendImgTdHtml("ODUVRFAlarm.png") + _getLegendImgTdHtml("ODUPACAlarm.png") + _getLegendImgTdHtml("ODUGHPAlarm.png") +
	    	'</tr>'+
	    	'<tr>'+
	    		_getLegendTextTdHtml("Off with </br>Non-Critical Alarm") + _getEmptyColumn() + _getLegendTextTdHtml("Auto Mode with </br>Non-Critical Alarm") + _getLegendTextTdHtml("Heat Mode with </br>Non-Critical Alarm") + 
	    		_getLegendTextTdHtml("Dry Mode with </br>Non-Critical Alarm") + _getLegendTextTdHtml("Cool Mode with </br>Non-Critical Alarm") + _getLegendTextTdHtml("Fan Mode with </br>Non-Critical Alarm") + _getEmptyColumn() +
	    		_getLegendTextTdHtml("VRF Mode with </br>Non-Critical Alarm") + _getLegendTextTdHtml("PAC with </br>Non-Critical Alarm") + _getLegendTextTdHtml("GHP with </br>Non-Critical Alarm") +
	    	'</tr>'+
    	    '</tbody>'+
    	    '</table></div>';
    	    div.innerHTML += legendTable;

    	    return div;
    	};

    	legend.addTo(map);
    }
    
    function _getLegendImgTdHtml(imgName){
    	return '<td><img src= ' + GLOBAL_VAL.imgPath + imgName + '></td>';
    }
    
    function _getLegendTextTdHtml(text){
    	return '<td class="legendText">'+text +'</td>';
    }
    
    function _getEmptyColumn(){
    	return '<td class="emptyColumn"></td>';
    }

    function addImageLayer(data, unitType,id) {
        var domId = unitType + data.iduId;
        if (unitType == "ODU") {
        	domId = unitType + data.oduId;
		}
        
        var imageLayer = GLOBAL_VAL["imageIdLayerMap"][domId];
        //						console.log(GLOBAL_VAL.floorMap._layers);
        if (imageLayer != null && typeof(imageLayer) != "undefined") {
            if (GLOBAL_VAL.floorMap.hasLayer(imageLayer)) {
                GLOBAL_VAL.floorMap.removeLayer(imageLayer);
            }
            delete GLOBAL_VAL["imageIdLayerMap"][domId]
        }
        var imageBounds = [
            [data.maxlatitude.toString(), data.minlongitude.toString()],
            [data.minlatitude.toString(), data.maxlongitude.toString()]
        ];
        var image = "";
        if (unitType == "IDU") { 
            if (data.mode != null && typeof(data.mode) != "undefined") {
                image = getImageBasedOnMode(data.mode);
                if (data.power == "OFF" && data.severity == "Critical" && data.alarmStatus =="Fixed") {
                    image = "IDUOff"
                }
                else if (data.power == "OFF" && data.severity == "Non-critical" && data.alarmStatus =="Fixed") {
                    image = "IDUOff"
                }
                else if (data.power == "OFF" && data.severity == "Critical" && data.alarmStatus =="New") {
                    image = "IDUOffCritical"
                }
                else if (data.power == "OFF" && data.severity == "Non-critical" && data.alarmStatus =="New") {
                    image = "IDUOffAlarm"
                }                
                else if(data.power == "ON" && data.severity == "Critical" && data.mode =="AUTO" && data.alarmStatus =="Fixed"){
                	image = "IDUAuto"
                }
                else if(data.power == "ON" && data.severity == "Critical" && data.mode =="HEAT" && data.alarmStatus =="Fixed"){
                	image = "IDUHeat"
                }
                else if(data.power == "ON" && data.severity == "Critical" && data.mode =="DRY" && data.alarmStatus =="Fixed"){
                	image = "IDUDry"
                }
                else if(data.power == "ON" && data.severity == "Critical" && data.mode =="COOL" && data.alarmStatus =="Fixed"){
                	image = "IDUCool"
                }
                else if(data.power == "ON" && data.severity == "Critical" && data.mode =="FAN" && data.alarmStatus =="Fixed"){
                	image = "IDUFan"
                }                
                else if(data.power == "ON" && data.severity == "Non-critical" && data.mode =="AUTO" && data.alarmStatus =="Fixed"){
                	image = "IDUAuto"
                }
                else if(data.power == "ON" && data.severity == "Non-critical" && data.mode =="HEAT" && data.alarmStatus =="Fixed"){
                	image = "IDUHeat"
                }
                else if(data.power == "ON" && data.severity == "Non-critical" && data.mode =="DRY" && data.alarmStatus =="Fixed"){
                	image = "IDUDry"
                }
                else if(data.power == "ON" && data.severity == "Non-critical" && data.mode =="COOL" && data.alarmStatus =="Fixed"){
                	image = "IDUCool"
                }
                else if(data.power == "ON" && data.severity == "Non-critical" && data.mode =="FAN" && data.alarmStatus =="Fixed"){
                	image = "IDUFan"
                }                                                                                                                                                               
                else if(data.power == "ON" && data.severity == "Critical" && data.mode =="AUTO" && data.alarmStatus =="New"){
                	image = "IDUAutoCritical"
                }
                else if(data.power == "ON" && data.severity == "Critical" && data.mode =="HEAT" && data.alarmStatus =="New"){
                	image = "IDUHeatCritical"
                }
                else if(data.power == "ON" && data.severity == "Critical" && data.mode =="DRY" && data.alarmStatus =="New"){
                	image = "IDUDryCritical"
                }
                else if(data.power == "ON" && data.severity == "Critical" && data.mode =="COOL" && data.alarmStatus =="New"){
                	image = "IDUCoolCritical"
                }
                else if(data.power == "ON" && data.severity == "Critical" && data.mode =="FAN" && data.alarmStatus =="New"){
                	image = "IDUFanCritical"
                }                
                else if(data.power == "ON" && data.severity == "Non-critical" && data.mode =="AUTO" && data.alarmStatus =="New"){
                	image = "IDUAutoAlarm"
                }
                else if(data.power == "ON" && data.severity == "Non-critical" && data.mode =="HEAT" && data.alarmStatus =="New"){
                	image = "IDUHeatAlarm"
                }
                else if(data.power == "ON" && data.severity == "Non-critical" && data.mode =="DRY" && data.alarmStatus =="New"){
                	image = "IDUDryAlarm"
                }
                else if(data.power == "ON" && data.severity == "Non-critical" && data.mode =="COOL" && data.alarmStatus =="New"){
                	image = "IDUCoolAlarm"
                }
                else if(data.power == "ON" && data.severity == "Non-critical" && data.mode =="FAN" && data.alarmStatus =="New"){
                	image = "IDUFanAlarm"
                }
            }
        }else {
        	if(data.severity == "Critical"  && data.alarmStatus =="New"){
            	image = "ODUVRFCritical"
            }
            else if(data.severity == "Non-critical"  && data.alarmStatus =="New"){
            	image = "ODUVRFAlarm"
            }
                   	
            else if( data.severity == "Non-critical"  && data.alarmStatus =="Fixed"){
            	image = "ODUVRF"
            }
            
            else {
            	image = "ODUVRF"
            }
            
        }
        
        
        
        image += ".png";
        var ImLayer_load = L.imageOverlay(GLOBAL_VAL.imgPath + image, imageBounds).addTo(GLOBAL_VAL.floorMap);
        L.DomUtil.addClass(ImLayer_load._image, unitType + "Image");
        L.extend(ImLayer_load._image, {
            id: domId
        });
        GLOBAL_VAL["imageIdLayerMap"][domId] = ImLayer_load;
        $("#" + domId).data("indoorUnitInfo", data);
    }
    
    function getImageBasedOnMode(mode)	{
    	var image = "";
    	var modeStr = mode.toLowerCase();
    	if (modeStr.indexOf("auto") != -1) {image = "IDUAuto";}
    	else if (modeStr.indexOf("cool") != -1) {image = "IDUCool";}
		else if (modeStr.indexOf("dry") != -1) {image = "IDUDry";}
		else if (modeStr.indexOf("fan") != -1) {image = "IDUFan";}
		else if (modeStr.indexOf("heat") != -1) {image = "IDUHeat";}
    	return image;
    }

    
	
    function storeMapZoom() {
        if (GLOBAL_VAL.floorMap != null) {
            GLOBAL_VAL.mapZoom = GLOBAL_VAL.floorMap.getZoom();
        }
    }
    
    
    
    /**
     * ********************* Common logic function end
     * ******************************
     */
});