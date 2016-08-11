window.glo = {};
window.glo = {CADetailTable: null,  OpTable:null}

$(document).ready(function() {
    var GLOBAL_VAL = {
        imgPath: "../assets/img/",
        selectedGroup: {},
        groupid: "1",
        start_date: "",
        end_date: "",
        imageIdLayerMap: {},
        floorMap: null,
        curGroupSvg:null,
        groupMenuItemPrefix: "groupMenuItem",
        //selectedObject.type:grop/indoorUnit
        selectedObject: {
            id: -1,
            type: ""
        },
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
        scrollY:"660px", //621 660px
        svgIdIDUListMap:{},
        svgIdODUListmap:{},
        $remoteControlDialog:null,
        unitHightLightDom:null,
        tempCheckIduIDsForOperationLog:null,
        
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
        },caData: null, selectedCA: [],CADetailTable : null
    };
    
    // highlight menu
    //menu('acsettings');
    //End of previous code
    /*
    $('#ca_mac')
         .append($("<option></option>")
         .attr("value","08007B910566")
         .text("08007B910566")); 
     $('#ca_mac')
         .append($("<option></option>")
         .attr("value","08007B92002C")
         .text("08007B92002C")); 
    */
    
    $(document).on("change", "#ca_mac", function(e) {
    	//var idList = getCheckedGroupIds();
    	
    		//clearFreshTimer();
    		var idList = $(this).find(":selected").val();
    	    var id = $(this).attr('id');
            
            $("#acDetailHeaderCheckbox").removeClass("mixStatus").prop("checked",false);
            
    		//$('#checkboxMenuTree').jstree().save_state();
    		
    		updateIDUDetailTableAndMap(idList);
    		updateODUAndMainDetailTable(idList);
    		
    		
    		//setFreshTimer();
    	
    	return false;
    });
    /*
    function clearFreshTimer(){
    	if (GLOBAL_VAL.refreshTimer != null) {clearInterval(GLOBAL_VAL.refreshTimer);}
    }
    
    function setFreshTimer(){
    	  //refresh in 3mins depends on requirements
    	GLOBAL_VAL.refreshTimer = setInterval(function(){
    		$("#displayButton").click();
	    }, 1000*60*3);
    }
    */
    
    initMonitoringCtrl();
    bindMonitorControlEvent();
    

    
    // Event start
    /*$(window).bind('resize', function() {
    	var width = $('.jqGrid_wrapper').width();
    	$('#table_list_1').setGridWidth(width);
    });*/
    
    /**
     * ******************* Monitor Control part logic function
     * start ***************
     */
    function bindMonitorControlEvent() {
    	bindAcDetailEvent();
        bindAcControlEvent();
        bindFloorMapEvent();
        bindOperationLogEvent();
        bindTabEvent();
        bindAcMainDetaiEvent();
    }

    function bindAcDetailEvent() {
    	//click head checkbox then check all
        $(document).on("click", "#acDetailHeaderCheckbox", function() {
        	var status = $(this).prop("checked");
        	
        	$("#acDetailTbody tr").each(function( index ) {
        		var $tr = $(this);
        		if ($tr.attr("data-rcFlag") == "1") {
        			$tr.find(".acDetailCheckbox").prop("checked",status);
        			status ? $tr.addClass("trSelected"):$tr.removeClass("trSelected");
				}
        	});
        	
        	$(this).removeClass("mixStatus");
        });
        
      //click detail checkbox then affect head checkbox
        $(document).on("click", "#acDetailTbody .acDetailCheckbox", function() {
        	//change children checked status by parent
        	changeChildrenCheckedStatusByParent($(this));

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
        });
        
    	//click head checkbox then check all
        $(document).on("click", "#acOUDDetailHeaderCheckbox", function() {
        	var status = $(this).prop("checked");
        	$("#acODUDetailTbody .acDetailCheckbox").prop("checked",status);
        	status ? $("#acODUDetailTbody tr").addClass("trSelected"):$("#acODUDetailTbody tr").removeClass("trSelected");
        	$(this).removeClass("mixStatus");
        });
        
        //click detail checkbox then affect head checkbox
        $(document).on("click", "#acODUDetailTbody .acDetailCheckbox", function() {
        	//change children checked status by parent
        	changeChildrenCheckedStatusByParent($(this));

        	var allCheckboxCount = $("#acODUDetailTbody .acDetailCheckbox").length;
        	var checkedCount = $("#acODUDetailTbody .acDetailCheckbox:checked").length;
        	
        	if (checkedCount == allCheckboxCount) {
        		$("#acOUDDetailHeaderCheckbox").removeClass("mixStatus").prop("checked",true);
			}else {
				if (checkedCount == 0) {
					$("#acOUDDetailHeaderCheckbox").removeClass("mixStatus").prop("checked",false);
				}else {
					$("#acOUDDetailHeaderCheckbox").addClass("mixStatus").prop("checked",false);
				}
				
			}
        });
    
   	//click head checkbox then check all
        $(document).on("click", "#caDetailHeaderCheckbox", function() {
        	var status = $(this).prop("checked");
        	$("#acCADetailTbody .caDetailCheckbox").prop("checked",status);
        	status ? $("#acCADetailTbody tr").addClass("trSelected"):$("#acCADetailTbody tr").removeClass("trSelected");

            //$(this).prop("checked",status);
            //status ? $(this).parent().addClass("trSelected"):$(this).parent().removeClass("trSelected");
            $(this).removeClass("mixStatus");
        });

        //click detail checkbox then affect head checkbox
        $(document).on("click", "#acCADetailTbody .caDetailCheckbox", function() {
        	//change children checked status by parent
        	//changeChildrenCheckedStatusByParentCA($(this));

            var currentStatus = $(this).prop("checked");
            $(this).prop("checked",currentStatus);
        	currentStatus ? $(this).parent().parent().addClass("trSelected"):$(this).parent().parent().removeClass("trSelected");


        	var allCheckboxCount = $("#acCADetailTbody .caDetailCheckbox").length;
        	var checkedCount = $("#acCADetailTbody .caDetailCheckbox:checked").length;

        	if (checkedCount == allCheckboxCount) {
        		$("#caDetailHeaderCheckbox").removeClass("mixStatus").prop("checked",true);
			}else {
				if (checkedCount == 0) {
					$("#caDetailHeaderCheckbox").removeClass("mixStatus").prop("checked",false);
				}else {
					$("#caDetailHeaderCheckbox").addClass("mixStatus").prop("checked",false);
				}

			}
        });
    
        
        function changeChildrenCheckedStatusByParent($this){
			//change children checked status by parent
        	var currentStatus = $this.prop("checked");
        	currentStatus ? $this.parent().parent().addClass("trSelected"):$this.parent().parent().removeClass("trSelected");
        	
        	var $curTr = $this.parent().parent();
        	var curPCValue = $curTr.find(".detailPCTd").text()
        	$curTr.nextAll().each(function( index ) {
        		if ($(this).find("td.detailPCTd").text().indexOf("Main") != -1){
        			return false;
        		}else {
        			currentStatus ? $(this).addClass("trSelected"):$(this).removeClass("trSelected");
        			$(this).find("input.acDetailCheckbox").prop("checked",currentStatus);
        		}
        	});
        	
        	if (curPCValue.indexOf("Main") == -1) { // child node
            	$curTr.prevAll().each(function( index ) {
            		currentStatus ? $(this).addClass("trSelected"):$(this).removeClass("trSelected");
            		$(this).find("input.acDetailCheckbox").prop("checked",currentStatus);
        			
            		if ($(this).find("td.detailPCTd").text().indexOf("Main") != -1){
            			return false;
            		}
            	});
			}
        }
        
         $(document).on("click", "#caControlButton", function() {
        	//$.bizalert("Download Function coming soon");
            var id = _getCheckedCAIds();
            console.log(id)
        	url = "../acconfig/downloadCADetails2.htm";
        	if (id.length > 0) {
        		
            	var data_send = JSON.stringify({"id":id,"idType":"group", "fileType": "csv"});

                $.ajax({
                    url: url,
                    beforeSend: function (request){
                    	getToken(request);
                    },
                    cache: false,
                    data: {
                    	"json_request": data_send,
                    	"AJAXREQUEST": true
                    },
                    success: function(data) {
        	        	console.log(JSON.stringify(data));

        	            var Indoor_i= "ControlAdaptersUnit Details";
        	            var fileName = Indoor_i+"_"+"("+Indoor_i+").csv";

                        downloadAs(data,fileName);
                        
                    }
                },"post");


        	}else {
        		$.bizalert("Please select at least 1 check box to view accontrol");
        	}

        });
        
        //Operation Log Download
        $(document).on("click","#oplogdownload", function( event, id ){
        	var now = new Date();
    		var nowYear = now.getFullYear(),
    			nowMonth = now.getMonth()+1,
    			nowDate = now.getDate();
    		var nintyDaysAgo = new Date(nowYear,nowMonth-1,nowDate - 90);
    		//avoid stay in this dialog more than 3 mins, page will be refreshed.
    		//var IDUIds = _getCheckedIDUIds();
    		var IDUIds = GLOBAL_VAL.tempCheckIduIDsForOperationLog;
        	url = "../rc/downloadRCOperationsLog.htm";
        	if (IDUIds.length > 0) {
        		
        		$.ajax({        			
                    url:url,
                    beforeSend: function (request){
                    	getToken(request);
                    },
                    cache: false,
                    data: {"unitIDs":IDUIds.toString(),                    	
                        "toDateTime": nowYear+"-"+ nowMonth + "-" + nowDate,
                        "fromDateTime": nintyDaysAgo.getFullYear()+"-"+(nintyDaysAgo.getMonth()+1)+"-"+nintyDaysAgo.getDate(),
                        "reportType": "csv",
                        "AJAXREQUEST": true
                        },
                    success: function(data) {
        	        	console.log(JSON.stringify(data));

        	            var Indoor_i= "Operationlog Details";
        	            var fileName = Indoor_i+"_"+"("+Indoor_i+").csv";

                        downloadAs(data,fileName);
                    }
                });
        	
            

        	}else{
                $.bizalert("Please select at least 1 check box to view operationlog");
                
            }	

        });
        
        
        $(document).on("click", "#acControlButtonInPopup", function() {
        	var id = $(this).attr("data-iduId");
        	var parentId = $(this).attr("data-parentId") ;
        	if (parentId == null) {parentId = id;}
        	
        	
        	_showRemoteControlDialog([[id],[parentId]]);
        });
        
        function _showRemoteControlDialog(IDUArray){
        	GLOBAL_VAL.$remoteControlDialog = $.jsPanel({
        		id:"acControlDialog",
        	    title:    "AC Remote Control",
        	    size:     { width: "auto", height: "auto" },
        	    position: "center",
        	    bootstrap:"primary",
        	    resizable: "disabled",
        	    content:$("#acControlFileDiv").html(),
        	    callback: function () {
        	    	updateAcContorl(IDUArray);
        	    }
        	});
        }
        
        $(document).on( "jspanelnormalized", function( event, id ){
            if( id === "acControlDialog" ) {
                $("#acControlDialog .panel-body").width($("#acControlDialog").width())
            }
        });
        
        //IDU download
        $(document).on("click","#iduDownloadButton", function( event, id ){
        	var IDUIds = _getCheckedIDUIds();
        	url = "../acconfig/downloadACDetails.htm";
        	if (IDUIds.length > 0) {
        		
            	var data_send = JSON.stringify({                   
        			"id": IDUIds,
                    "idType": "indoorUnit",
                    "fileType": "csv"});
                
                $.ajax({
	                url: url,
	                beforeSend: function (request){
	                	getToken(request);
	                },
                    cache: false,
	                data: {
	                	"json_request":data_send,
	                	"AJAXREQUEST": true
	                },
	                success: function(data) {
	    	        	//console.log(JSON.stringify(data));
	    	        	     
	    	            var Indoor_i= "indoorUnit Details";		    	            		    	           		         
	    	            var fileName = Indoor_i+"_"+"("+Indoor_i+").csv";
                        downloadAs(data,fileName);
                        
	                }
	            },"post");
                
                
        	}else {
        		$.bizalert("Please select at least 1 check box to proceed download");
        	}

        });
        
      //ODU download
        $(document).on("click","#downODUButton", function( event, id ){
        	var IDUIds = _getCheckedODUIds();
        	url = "../acconfig/downloadACDetailsODU.htm";
        	if (IDUIds.length > 0) {
        		
            	var data_send = JSON.stringify({"id":IDUIds,"idType":"outdoorUnit",
                    "fileType": "csv"});
                
                $.ajax({
                    url: url,
                    beforeSend: function (request){
                    	getToken(request);
                    },
                    cache: false,
                    data: {
                    	"json_request": data_send,
                    	"AJAXREQUEST": true
                    },
                    success: function(data) {
        	        	console.log(JSON.stringify(data));
        	            var Indoor_i= "OutdoorUnit Details";		    	            		    	           		   	 
        	            var fileName = Indoor_i+"_"+"("+Indoor_i+").csv";
                        
                        downloadAs(data,fileName);
                    }
                },"post");
                
                
        	}else {
        		$.bizalert("Please select at least 1 check box to proceed download");
        	}

        });
        
        var alertTimer = null;
        //AC detail prohibition popup tip
        $(document).on("click", ".prohibitionTitle", function() {
        	$.bizinfo($(this).attr("data-proname"));
        	
        	alertTimer = setTimeout(function() {
                 $("#bizinfo").modal("hide");
             }, 2000);
        });
        
        $('#bizinfo').on('hidden.bs.modal', function (e) {
        	if (alertTimer != null) {
                clearTimeout(alertTimer);
            }
        });
        
        
        
    }
    
    
    
    
    function _getCheckedIDUIds(){
    	var IDUIds = [];
    	$("#acDetailTbody input:checked").each(function( index ) {
    		IDUIds.push($(this).attr("data-unitid"));
    	});
    	return IDUIds;
    }
    
    function _getCheckedIDUIdsForRemoteControl(){
    	var IDUIds = [];
    	var onlyParentIDUIds = [];
    	$("#acDetailTbody input:checked").each(function( index ) {
    		var id = $(this).attr("data-unitid");
    		IDUIds.push(id);
    		if ($(this).parent().parent().find("td.detailPCTd").text().indexOf("Main") != -1) {
    			onlyParentIDUIds.push(id);
			}
    	});
    	return [IDUIds,onlyParentIDUIds];
    }
    
    function _getCheckedODUIds(){
    	var ODUIds = [];
    	$("#acODUDetailTbody input:checked").each(function( index ) {
    		ODUIds.push($(this).attr("data-unitid"));
    	});
    	return ODUIds;
    }

    function _getCheckedCAIds(){
    	var IDUIds = [];
    	$("#acCADetailTbody input:checked").each(function( index ) {
    		IDUIds.push($(this).attr("data-unitid"));
    	});
    	return IDUIds;
    }

    function bindAcControlEvent() {
        
    	bindControlPanelEvent();
    }

    function bindControlPanelEvent(){
        $(document).on("click", "#acControlDialog #remoteControlRightFrame .controlButton", function() {
        	var $imageDiv = $(this).children();
        	if (!$imageDiv.hasClass("disabled")) {
        		
            	$("#acControlDialog #control #remoteControlRightFrame .controlButton div").removeClass("selected");
            	$imageDiv.addClass("selected");
            	
                var panelId = $(this).attr("id")+"Panel";
                $("#acControlDialog #remoteControlRightFrame .speech-bubble").hide();
                $("#acControlDialog #"+panelId).show();
			}
        });
               
        $(document).on("click", "#acControlDialog #remoteControlRightFrame .speech-bubble > div > div", function() {
        	var $selfDiv = $(this);
        	if (!$selfDiv.find(".controlIcon").hasClass("disabled") || $selfDiv.hasClass("disabled")) {
            	var $row = $selfDiv.parent();
            	$row.find(".controlIcon").removeClass("selected");
            	$selfDiv.find(".controlIcon").addClass("selected");
            	
            	var type = $selfDiv.attr("control-type");
            	var $linkedObj = $("#acControlDialog #"+$row.parent().attr("linked-id"));
            	
            	var $linkedDiv = $linkedObj.children();
            	var arrayOfClasses = $linkedDiv.attr("class").split(' ');
            	$linkedDiv.removeClass();
            	if (arrayOfClasses.indexOf("selected") != -1) {$linkedDiv.addClass("selected");}
            	$linkedDiv.addClass(type+"Selected");
            	
        		var controlTypeId = $selfDiv.parent().parent().attr("id");
        		if (controlTypeId == "modeControlPanel") {
//            		var $oldSelectedMode = $("#acControlDialog #modeControlPanel .selected");
//            		var oldSeclectedModeStr = "";
//            		if (typeof ($oldSelectedMode) != "undefined" && $oldSelectedMode.length > 0) {
//            			oldSelectedModeStr = $oldSelectedMode.parent().attr("control-type");
//    				}
            		
                    //make flap disabled based on mode
        			 var data = $("#acControlDialog").data("cacheRCData");
        			if (data.windDirection_support == 1) {
            			resetFlapSelection();
                        var controlType = $selfDiv.attr("control-type");
                        var $controlDialog = $("#acControlDialog");
                        $controlDialog.find("#flapControlPanel .controlIcon").addClass("disabled");
                       
                        var supportFlapList = data["flap_range_"+controlType];
                        if (supportFlapList != null && supportFlapList.length > 0) {
                        	for (var i = 0; i < supportFlapList.length; i++) {
        						var flapStr = supportFlapList[i].toLowerCase();
        						$("#acControlDialog ." + flapStr +"Flap .controlIcon").removeClass("disabled");
        					}
        				}
					}
                    
                    //clear temp
                    resetTempSelection();
//                    if (controlType != oldSeclectedModeStr) {
//                    	resetFlapSelection();resetFlapDisable();
//                        if (controlType == "cool" || controlType == "dry") {
//         					disableFlap("f4");disableFlap("f5");
//         				}
//					}
				}
			}
        });
        
        //reset rc selection function
        $(document).on("click", "#acControlDialog .resetButton", function() {
        	var resetType = $(this).attr("data-resetType");
        	switch (resetType) {
			case "operation":
				resetOperationSelection();
				break;
			case "temp":
				resetTempSelection();
				break;
			case "mode":
				resetModeSelection();
				break;
			case "fanSpeed":
				resetFanSpeedSelection();
				break;
			case "flap":
				resetFlapSelection();
				break;
			case "energySaving":
				resetEnergySavingSelection();
				break;
			case "prohibition":
				resetProhibitionSelection($(this).attr("data-resetId"));
				break;
			default:
				break;
			}
        });
        
        function resetOperationSelection(){
        	$("#acControlDialog #powerControlPanel .powerStatusButton").removeClass("selected");
        }
        
        function resetTempSelection(){
        	$("#acControlDialog #tempLabel").text("_ _");
        }
        
        function resetModeSelection(){
        	var $modeButton = $("#acControlDialog #modeControl > div");
        	var arrayOfClasses = $modeButton.attr("class").split(' ');
        	$modeButton.removeClass();
        	if (arrayOfClasses.indexOf("selected") != -1) {$modeButton.addClass("selected");}
        	if (arrayOfClasses.indexOf("disabled") != -1) {$modeButton.addClass("disabled");}
        	$("#acControlDialog #modeControlPanel .controlIcon").removeClass("selected");
        }
        
        function resetFanSpeedSelection(){
        	var $button = $("#acControlDialog #fanSpeedControl > div");
        	var arrayOfClasses = $button.attr("class").split(' ');
        	$button.removeClass();
        	if (arrayOfClasses.indexOf("selected") != -1) {$button.addClass("selected");}
        	if (arrayOfClasses.indexOf("disabled") != -1) {$button.addClass("disabled");}
        	$("#acControlDialog #fanSpeedControlPanel .controlIcon").removeClass("selected");
        }
        
        function resetFlapSelection(){
        	var $button = $("#acControlDialog #flapControl > div");
        	var arrayOfClasses = $button.attr("class").split(' ');
        	$button.removeClass();
        	if (arrayOfClasses.indexOf("selected") != -1) {$button.addClass("selected");}
        	if (arrayOfClasses.indexOf("disabled") != -1) {$button.addClass("disabled");}
        	$("#acControlDialog #flapControlPanel .controlIcon").removeClass("selected");
        }
        
        function resetFlapDisable(){
        	$("#acControlDialog #flapControlPanel .controlIcon").removeClass("disabled");
        }
        
        function resetProhibitionSelection(resetId){
        	$("#acControlDialog #"+resetId+" input").prop("checked",false);
        }
        
        //set ON/OFF
        $(document).on("click", "#acControlDialog .powerStatusButton", function() {
        	if (!$(this).hasClass("disabled")) {
            	var $row = $(this).parent();
            	$row.find(".powerStatusButton").removeClass("selected");
            	$(this).addClass("selected");
            	
            	var powerStatus = $(this).attr("control-type");
            	$("#acControlDialog #powerControl").removeClass("selected onPowerStatus offPowerStatus")
            		.addClass("greyGradient "+powerStatus + "PowerStatus");
			}
        });
        
        //set temp
        $(document).on("click", "#acControlDialog #increaseTempButton", function() {
        	var data = $("#acControlDialog").data("cacheRCData");
        	var selectedMode = getSelectedMode();
        	if (selectedMode == "") {
        		$.bizalert("please choose mode");
			}else {
        		var tempRangeArray = data["temp_range_"+selectedMode];
        		if (tempRangeArray == null || tempRangeArray.length == 0) {
					$.bizalert("No available temperature range based on mode:  " + selectedMode);
				}else {
					var curTemp = $("#acControlDialog #tempLabel").text();
					var maxTemp = tempRangeArray[1] >= tempRangeArray[0]?tempRangeArray[1] : tempRangeArray[0];
					var minTemp = tempRangeArray[1] < tempRangeArray[0]?tempRangeArray[1] : tempRangeArray[0];
		        	if (curTemp == "_ _") {
		        		curTemp = (minTemp + maxTemp)/2
					}
		        	var newValue = parseInt(curTemp) + 1;
		        	if (newValue > maxTemp){
		        		newValue = maxTemp;
		        	}
		        	$("#acControlDialog #tempLabel").text(newValue);
				}
			}
        });
        
        $(document).on("click", "#acControlDialog #decreaseTempButton", function() {
        	var data = $("#acControlDialog").data("cacheRCData");
        	var selectedMode = getSelectedMode();
        	if (selectedMode == "") {
        		$.bizalert("please choose mode");
			}else {
				var tempRangeArray = data["temp_range_"+selectedMode];
        		if (tempRangeArray == null || tempRangeArray.length == 0) {
					$.bizalert("No available temperature range based on mode:  " + selectedMode);
				}else {
					var curTemp = $("#acControlDialog #tempLabel").text();
					var maxTemp = tempRangeArray[1] >= tempRangeArray[0]?tempRangeArray[1] : tempRangeArray[0];
					var minTemp = tempRangeArray[1] < tempRangeArray[0]?tempRangeArray[1] : tempRangeArray[0];
		        	if (curTemp == "_ _") {
		        		curTemp = (maxTemp + minTemp)/2
					}
		        	var newValue = parseInt(curTemp) - 1;
		        	if (newValue < minTemp){
		        		newValue = minTemp;
		        	}
		        	$("#acControlDialog #tempLabel").text(newValue);
				}
			}
        });
        
        function getSelectedMode(){
        	var modeStr = "";
        	var $selectedMode = $("#acControlDialog #modeControlPanel .selected");
        	if (typeof ($selectedMode) != "undefined" && $selectedMode.length > 0 ) {
        		modeStr = $selectedMode.parent().attr("control-type");
			}
        	
        	return modeStr;
        }
        
        //apply remote setting
        $(document).on("click", "#acControlDialog #remoteControlButton", function() {
        	var remoteSetting = {};
        	$("#acControlDialog #modeControlPanel div.selected").each(function( index ) {
        		remoteSetting["MODE"] = $(this).parent().attr("control-type");
        	});
        	
        	$("#acControlDialog #fanSpeedControlPanel div.selected").each(function( index ) {
        		remoteSetting["FANSPEED"] = $(this).parent().attr("control-type");
        	});
        	
        	$("#acControlDialog #flapControlPanel div.selected").each(function( index ) {
        		remoteSetting["WINDDIRECTION"] = $(this).parent().attr("control-type");
        	});
        	
        	$("#acControlDialog #prohibitionPanel input:checked").each(function( index ) {
        		var valuePair = $(this).attr("control-type").split("-");
        		remoteSetting[valuePair[0]] = valuePair[1];
        	});
        	
        	$("#acControlDialog #powerControlPanel div.selected").each(function( index ) {
        		remoteSetting["POWERSTATUS"] = $(this).attr("control-type").toUpperCase();
        	});
        	
        	$("#acControlDialog #energySavingControlPanel div.selected").each(function( index ) {
        		remoteSetting["ENERGY_SAVING"] = $(this).parent().attr("control-type").toUpperCase();
        	});
        	
        	var curTemp = $("#acControlDialog #tempLabel").text();
        	if (curTemp != '_ _') {
        		remoteSetting["TEMPERATURE"] = curTemp;
			}
        	
        	if (remoteSetting == {}) {
				$.bizalert("Please make some changes");
			}else {
				var iduArray = $("#acControlDialog").data("cacheRCCheckedIds");
                $.axs("../rc/setControlRC.htm", {
                	"json_request":JSON.stringify({
                    "id": iduArray[1],
                    "operation":remoteSetting})
                }, function(data) {
                    GLOBAL_VAL.clickUnitIcon = false;
                    console.log(data);
//                    if (typeof(data.success) != "undefined" && data.success) {
//						$.bizalert("Request sent. Please wait for the server to update");
//					}else {
//						$.bizalert("Operation failed")
//					}
                    _changeIDUDetailTemporaryly(iduArray[0],remoteSetting);
                    
                    GLOBAL_VAL.$remoteControlDialog.close();
                   
                }, "post");
			}
        	console.log(remoteSetting);
        });
    }
    
    function _changeIDUDetailTemporaryly(iduList,remoteSetting){
    	var proValueMap = {"1":"&#176","0":"&#149"};
    	var proClassNameMap = {"1":"lineHeight27","0":""};
    	for ( var param in remoteSetting) {
    		var value = remoteSetting[param];
			if(param == "MODE"){
				var className = "acDetailMode " + getModeFontClass(remoteSetting["MODE"]);
		    	for (var i = 0; i < iduList.length; i++) {
					var $IduTr = $("#IDU"+ iduList[i] +"tr");
					var $td = $IduTr.find("td.acDetailMode");
					$td.removeClass().addClass(className);
					$td.html(value.toUpperCase());
					
				}
			}else if(param == "FANSPEED"){
		    	for (var i = 0; i < iduList.length; i++) {
					var $IduTr = $("#IDU"+ iduList[i] +"tr");
					var $td = $IduTr.find("td.acDetailFanSpeed");
					$td.html(value.toUpperCase());
				}
			}else if(param == "WINDDIRECTION"){
				var flapModeHtml = "<div class='" + value.toLowerCase() + "FlapIcon'></div>";
		    	for (var i = 0; i < iduList.length; i++) {
					var $IduTr = $("#IDU"+ iduList[i] +"tr");
					var $td = $IduTr.find("td.acDetailWind");
					$td.html(flapModeHtml);
				}
			}else if(param == "POWERSTATUS"){
				var className = "acDetailStatus "+value.toLowerCase()+"PowerFont";
		    	for (var i = 0; i < iduList.length; i++) {
					var $IduTr = $("#IDU"+ iduList[i] +"tr");
					var $td = $IduTr.find("td.acDetailStatus");
					$td.removeClass().addClass(className);
					$td.html(value.toUpperCase());
				}
			}else if(param == "ENERGY_SAVING"){
		    	for (var i = 0; i < iduList.length; i++) {
					var $IduTr = $("#IDU"+ iduList[i] +"tr");
					var $td = $IduTr.find("td.acDetailESaving");
					$td.html(value.toUpperCase());
				}
			}else if(param == "TEMPERATURE"){
		    	for (var i = 0; i < iduList.length; i++) {
					var $IduTr = $("#IDU"+ iduList[i] +"tr");
					var $td = $IduTr.find("td.acDetailTemp");
					$td.html(value + "&#176C");
				}
			}else if(param == "PROHIBITION_POWERSTATUS"){
				var className = "acDetailPro1 "+proClassNameMap[value];
		    	for (var i = 0; i < iduList.length; i++) {
					var $IduTr = $("#IDU"+ iduList[i] +"tr");
					var $td = $IduTr.find("td.acDetailPro1");
					$td.removeClass().addClass(className);
					$td.html(proValueMap[value]);
				}
			}else if(param == "PROHIBITION_SET_TEMP"){
				var className = "acDetailPro2 "+proClassNameMap[value];
		    	for (var i = 0; i < iduList.length; i++) {
					var $IduTr = $("#IDU"+ iduList[i] +"tr");
					var $td = $IduTr.find("td.acDetailPro2");
					$td.removeClass().addClass(className);
					$td.html(proValueMap[value]);
				}
			}else if(param == "PROHIBITON_MODE"){
				var className = "acDetailPro3 "+proClassNameMap[value];
		    	for (var i = 0; i < iduList.length; i++) {
					var $IduTr = $("#IDU"+ iduList[i] +"tr");
					var $td = $IduTr.find("td.acDetailPro3");
					$td.removeClass().addClass(className);
					$td.html(proValueMap[value]);
				}
			}else if(param == "PROHIBITION_FANSPEED"){
				var className = "acDetailPro4 "+proClassNameMap[value];
		    	for (var i = 0; i < iduList.length; i++) {
					var $IduTr = $("#IDU"+ iduList[i] +"tr");
					var $td = $IduTr.find("td.acDetailPro4");
					$td.removeClass().addClass(className);
					$td.html(proValueMap[value]);
				}
			}else if(param == "PROHIBITION_WINDRIECTION"){
				var className = "acDetailPro5 "+proClassNameMap[value];
		    	for (var i = 0; i < iduList.length; i++) {
					var $IduTr = $("#IDU"+ iduList[i] +"tr");
					var $td = $IduTr.find("td.acDetailPro5");
					$td.removeClass().addClass(className);
					$td.html(proValueMap[value]);
				}
			}else if(param == "PROHIBITION_ENERGY_SAVING"){
				var className = "acDetailPro6 "+proClassNameMap[value];
		    	for (var i = 0; i < iduList.length; i++) {
					var $IduTr = $("#IDU"+ iduList[i] +"tr");
					var $td = $IduTr.find("td.acDetailPro6");
					$td.removeClass().addClass(className);
					$td.html(proValueMap[value]);
				}
			}
		}
    }
    
    function bindSlideControlEvent() {
        var statusTimer = null;
        var statusMap = {
            "true": "ON",
            "false": "OFF"
        };
   
        $(document).on("click", "#c_status", function() {
            var selectedObject = GLOBAL_VAL.selectedObject;
            if (statusTimer != null) {
                clearTimeout(statusTimer);
            }
            $(this).toggleClass("stateOn");
            var curStatus = statusMap[$(this).hasClass('stateOn')];
            if ($(this).hasClass('stateOn')) {
            	$("#c_status").attr("src","../assets/img/On-Off-icon-Mo.png");
            } else {
            	$("#c_status").attr("src","../assets/img/On-Off-icon.png");
			}
            
            statusTimer = setTimeout(function() {
                _sendAjaxUpdateCtrlStatus(selectedObject, curStatus);
            }, 1000);
        });
        
        var fanTimer = null;
        $(document).on("click", "#fan", function() {
            var nextFanspeedStatus = GLOBAL_VAL.fanspeedMap[GLOBAL_VAL.fanspeedMap.currentStatus].nextStatus;
            $("#fan").attr('src', GLOBAL_VAL.fanspeedMap[nextFanspeedStatus].image);
            GLOBAL_VAL.fanspeedMap["currentStatus"] = nextFanspeedStatus;
            var selectedObject = GLOBAL_VAL.selectedObject;
            if (fanTimer != null) {
                clearTimeout(fanTimer);
            }
            fanTimer = setTimeout(function() {
            	if (!GLOBAL_VAL.isAWS) {
                    $.axs("../rc/setControlRC.htm", {
                        "id": selectedObject.id,
                        "idType": selectedObject.type,
                        "fanSpeed": nextFanspeedStatus
                    }, function(data) {
                        GLOBAL_VAL.clickUnitIcon = false;
                        refreshMapAndACDetail();
                    }, "post");
            	}else {
            		var speedRequestMap = {
            	            MIX: "fanspeed3",
            	            Low: "fanspeed3",
            	            Medium: "fanspeed4",
            	            High: "fanSpeedMax",
            	            Auto: "fanSpeedAuto"
            		};
            		
            		controlRemoteAC(selectedObject,speedRequestMap[nextFanspeedStatus]);
            	}

            }, 1000);
        });
        var windTimer = null;
        $(document).on("click", "#wind", function() {
            var nextWindStatus = GLOBAL_VAL.windMap[GLOBAL_VAL.windMap.currentStatus].nextStatus;
            $("#wind").attr('src', GLOBAL_VAL.windMap[nextWindStatus].image);
            GLOBAL_VAL.windMap["currentStatus"] = nextWindStatus;
            var selectedObject = GLOBAL_VAL.selectedObject;
            if (windTimer != null) {
                clearTimeout(windTimer);
            }
            windTimer = setTimeout(function() {
            	if (!GLOBAL_VAL.isAWS) {
                    $.axs("../rc/setControlRC.htm", {
                        "id": selectedObject.id,
                        "idType": selectedObject.type,
                        "windDirection": nextWindStatus
                    }, function(data) {
                        GLOBAL_VAL.clickUnitIcon = false;
                        refreshMapAndACDetail();
                    }, "post");
            	}else {

            		var windRequestMap = {
            	            MIX: "vanePosHorz1",
            	            Unset: "vanePosHorz1",
            	            F1: "vanePosHorz1",
            	            F2: "vanePosHorz2",
            	            F3: "vanePosHorz3",
            	            F4: "vanePosHorz4",
            	            F5: "vanePosHorz3",
            	            Swing:"vanePosHorz5", 
            		};
            		controlRemoteAC(selectedObject,windRequestMap[nextWindStatus]);
            	}

            }, 1000);
        });
    }

    function _sendAjaxUpdateCtrlStatus(selectedObject, status) {
    	if (!GLOBAL_VAL.isAWS) {
            $.axs("../rc/setControlRC.htm", {
                "id": selectedObject.id,
                "idType": selectedObject.type,
                "powerStatus": status
            }, function(data) {
                GLOBAL_VAL.clickUnitIcon = false;
                refreshMapAndACDetail();
            }, "post");
		}else {
	    	var stateUrl = "turnOff";
        	if (status == "ON" || status == "On") {
        		stateUrl = "turnOn";
    		}
	    	controlRemoteAC(selectedObject,stateUrl);
		}
    }

    function refreshMapAndACDetail() {
        if (GLOBAL_VAL.selectedObject.type == "group") {
            _refreshMapAndACDetailByGroupId(GLOBAL_VAL.selectedGroup.attr("group-id"));
        } else if (GLOBAL_VAL.selectedObject.type == "indoorUnit") {
            $("#IDU" + GLOBAL_VAL.selectedObject.id).click();
        }
    }

    function _toggleSlideIconSelectedClass(mode) {
    	$("#acControlDialog #modeControlPanel .selected").removeClass("selected");
    	if(mode != "reset"){
            var modeMap = {
                    Heat: "heat",
                    Cool: "cool",
                    Dry: "dry",
                    Auto: "auto",
                    Fan: "fan"
                };
                $("#acControlDialog #modeControlPanel").find("div[control-type='"+modeMap[mode]+"'] div").addClass("selected");
    	}
    }

    function _updateACMode(mode) {
        var selectedObject = GLOBAL_VAL.selectedObject;
        var timer = null;
        if (timer != null) {
            clearTimeout(timer);
        }
        timer = setTimeout(function() {
        	if (!GLOBAL_VAL.isAWS) {
                $.axs("../rc/setControlRC.htm", {
                    "id": selectedObject.id,
                    "idType": selectedObject.type,
                    "mode": mode
                }, function(data) {
                    GLOBAL_VAL.clickUnitIcon = false;
                    refreshMapAndACDetail();
                }, "post");
        	}else {
        		var modeRequestMap = {
        				"Heat":"operationModeHeat",
        				"Cool":"operationModeCool",
        				"Fan":"operationModeFan",
        				"Dry":"operationModeDry",
        				"Auto":"operationModeAuto"
        		};
        		controlRemoteAC(selectedObject,modeRequestMap[mode]);
        	}
        }, 1000);
    }

    function bindProhibitionsCtrlEvent() {
        // RC Prohibit
        var ptemp = pfan_speed = pfan_direction = true;
        $(document).on("click", "#prohibit_onoff", function() {
            $(this).toggleClass("btn-danger").toggleClass("btn-primary");
            $("#papply").removeClass("btn-default");
            $("#papply").addClass("btn-primary");
        });
        $(document).on("click", "#prohibit_temp", function() {
            if (ptemp) {
                $(this).attr("src", "../assets/img/prohibit_temp_off.png");
                ptemp = false;
            } else {
                $(this).attr("src", "../assets/img/prohibit_temp_on.png");
                ptemp = true;
            }
            $("#papply").removeClass("btn-default");
            $("#papply").addClass("btn-primary");
        });
        $(document).on("click", "#prohibit_fan_speed", function() {
            if (pfan_speed) {
                $(this).attr("src", "../assets/img/fanspeed-mix.png");
                pfan_speed = false;
            } else {
                $(this).attr("src", "../assets/img/fanspeed-high.png");
                pfan_speed = true;
            }
            $("#papply").removeClass("btn-default");
            $("#papply").addClass("btn-primary");
        });
        $(document).on("click", "#prohibit_fan_direction", function() {
            if (pfan_direction) {
                $(this).attr("src", "../assets/img/wind_mix.png");
                pfan_direction = false;
            } else {
                $(this).attr("src", "../assets/img/f4.png");
                pfan_direction = true;
            }
            $("#papply").removeClass("btn-default");
            $("#papply").addClass("btn-primary");
        });
        $(document).on("click", ".btn-pmode", function() {
            $(".btn-pmode").toggleClass("btn-danger").toggleClass("btn-primary");
            $("#papply").removeClass("btn-default");
            $("#papply").addClass("btn-primary");
        });
    }

    function bindFloorMapEvent() {
        $(document).on('click', '.IDUImage', function() {
            var tempInfo = $("#"+$(this).attr("id")).data("indoorUnitInfo");
//            GLOBAL_VAL.selectedObject = {
//                id: $(this).data("indoorUnitInfo").id,
//                type: "indoorUnit"
//            };
            $.axs("../acconfig/getACDetails.htm", {
                "json_request": JSON.stringify({"id":[tempInfo.iduId],"idType":"indoorUnit"})
            }, function(data) {
            	if (typeof(data) != "undefined") {
            		var indoorUnit = data.iduList[0];
            		var iduId = indoorUnit.iduId;
                    console.log(indoorUnit)
//                  GLOBAL_VAL.selectedObject.centralAddress = indoorUnit.centralAddress;
                    
                    addImageLayer(indoorUnit, "IDU");

	  	            var highlightImg = $("img.svgHighlight");
	  	            //Back to initial size
	  	            highlightImg.removeClass("svgHighlight").css("height", (highlightImg.height() - 6) + "px").css("width", (highlightImg.width() - 6) + "px");
//	  	            $("#acDetailTbody tr").removeClass("svgHighlight");
	  	            var IDUDom = $("#IDU" + iduId);
	  	            //increase size for showing border
	  	            IDUDom.css("height", (IDUDom.height() + 6) + "px").css("width", (IDUDom.width() + 6) + "px").addClass("svgHighlight");
	  	            
//	  	            var checkStatus = $("#IDU" + indoorUnit.id + "tr input.acDetailCheckbox").prop("checked");
//	  	            $("#IDU" + iduId + "tr").replaceWith(getACDetailLineHtml(indoorUnit));
//	  	            $("#IDU" + indoorUnit.id + "tr input.acDetailCheckbox").prop("checked",checkStatus);
//	  	            $("#IDU" + indoorUnit.id + "tr").addClass("svgHighlight");
	  	            _openPopup(indoorUnit);
	  	            //updateAcContorl(indoorUnit.id);
	  	            if (GLOBAL_VAL.clickUnitIcon == true) {
	  	                $("acDetailScrollDiv").mCustomScrollbar("scrollTo", "#IDU" + iduId + "tr");
	  	            } else {
	  	                GLOBAL_VAL.clickUnitIcon = true;
	  	                //move to center
//	  	                GLOBAL_VAL.floorMap.setView([indoorUnit.maxlatitude, indoorUnit.minlongitude]);
	  	            }
				}

	                return false;
	            });
            return false;
        });
        $(document).on('click', '.ODUImage', function() {
        	var tempInfo = $("#"+$(this).attr("id")).data("indoorUnitInfo");
    		
            $.axs("../acconfig/getACDetailsODU.htm", {
                "cache": false,
            	"json_request": JSON.stringify({"id":[tempInfo.oduId],"idType":"outdoorUnit"})
            }, function(data) {
            	var highlightImg = $("img.svgHighlight");
  	            //Back to initial size
  	            highlightImg.removeClass("svgHighlight").css("height", (highlightImg.height() - 6) + "px").css("width", (highlightImg.width() - 6) + "px");
  	            if (data.length > 0) {
  	            	var ODUDom = $("#ODU" + data[0].oduId);
  	  	            //increase size for showing border
  	  	            ODUDom.css("height", (ODUDom.height() + 6) + "px").css("width", (ODUDom.width() + 6) + "px").addClass("svgHighlight");
				}
//  	            $("#acDetailTbody tr").removeClass("svgHighlight");
  	            
                return false;
            });
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
        	$('#acMapTabLink').click();
        	$("#groupSelects").val(parseInt($(this).attr("data-svgId")));
        	updateFloorMap($(this).attr("data-svgId"),$(this).attr("data-unitid"),"IDU");
        });
        
        $(document).on('click', 'a.ODUViewMapLink', function(e) {
        	$('#acMapTabLink').click();
        	updateFloorMap($(this).attr("data-svgId"),$(this).attr("data-unitid"),"ODU");
        });
        
        //map group select 
        $(document).on('change', '#groupSelects', function(e) {
        	updateFloorMap($(this).val());
        });
        
        //map legend button
        $(document).on('click', '#legendButton', function(e) {
        	$(this).find("span.glyphicon").toggleClass("glyphicon-chevron-up").toggleClass("glyphicon-chevron-down");
        	$('div.legendBody').toggle();
        });
    }

    function _openPopup(indoorUnit) {
    	var buttonClass = indoorUnit.rc_flag == 1 ? "btn bizButton orangeGradient" : "btn bizButton orangeGradient disabled ";
    	var content = '<div style="width:187px;"><div class="title text-center">'+indoorUnit.site+'</div>'+
			    		'<div class="settingBody">'+'<p style="padding:0;margin:0px 5px;">Current Settings:</p>'+
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
			    				'<td class="text-center"><span>'+indoorUnit.energy_saving+'</span></td></tr>'+
			    				'<tr><td class="text-center" colspan="5"><img src='+GLOBAL_VAL.imgPath+'popup/Map_current_Status_icon_Econavi.png /></td></tr></table>'+
			    			'</div>'+
		    				'<p style="padding:0;margin:0px 5px;">'+
			    				'<button id = "acControlButtonInPopup" data-iduId='+indoorUnit.iduId + ' data-parentId='+indoorUnit.parentId + 
			    				' type="button" class="'+buttonClass+'">AC Remote Control &nbsp;<span class="fa fa-caret-right"></span></button></p>'+
		    				'</div></div>';
    	
        var middlelatlng = L.latLng(parseFloat(indoorUnit.maxlatitude), (parseFloat(indoorUnit.maxlongitude) + parseFloat(indoorUnit.minlongitude)) / 2);
        var popup = L.popup().setLatLng(middlelatlng).setContent(content).openOn(GLOBAL_VAL.floorMap);
    }

    function controlRemoteAC(selectedObject,operation){
        if (selectedObject.type == "group") {
            $.axs("../map/getMapData.htm", {
                "id": selectedObject.id,
                "idType": "group"
            }, function(data) {
            	var iduList = data.iduList;
            	if (typeof(iduList) != "undefined" && iduList.length > 0) {
            		var paramsList = [];
					for (var i = 0; i < iduList.length; i++) {
	    	        	var unitInfo = iduList[i].address.split("-");
	    	        	var controllId = unitInfo[0];
	    	        	//conver 01 to 1 then conver it to string
	    	        	var unitId = parseInt(unitInfo[2]).toString();
	    	        	if (typeof(unitId) != "undefined") {
	    	        		paramsList.push({ "unitId": unitId});
						}
					}
					
//					paramsList = [{ "unitId": "2"},{ "unitId": "3"},{ "unitId": "4"}] 
					if (paramsList.length > 0) {
						_sendRemoteACControlRequest(operation,{"units":paramsList});
					}
				}
            });
        }else {
        	var unitInfo = selectedObject.centralAddress.split("-");
        	var controllId = unitInfo[0];
        	var unitId = parseInt(unitInfo[2]).toString();
//        	_sendRemoteACControlRequest(operation,{"units":[{"unitId": "3"}]});
        	_sendRemoteACControlRequest(operation,{"units":[{"unitId": unitId}]});
        }
    }
    
    function _sendRemoteACControlRequest(operation,param){
    	$.ajax({
    		  url:"http://52.74.89.89/emulator/restapi/rc/v1/ACSystemControl/"+operation+"/34/34/34",
    		  type:"POST",
    		  data:JSON.stringify(param),
    		  contentType:"application/json; charset=utf-8",
    		  dataType:"json",
    		  statusCode: {
    			    200: function(data) {
    			    	if (typeof (data) != "undefined") {
    			    		GLOBAL_VAL.clickUnitIcon = false;
							if (data.responseText.indexOf("failed") == -1) {
		    			    	setTimeout(function() {refreshMapAndACDetail();}, 10000);
							}else {
								$.bizalert(data.responseText);
								refreshMapAndACDetail();
							}
						}

    			    }
    			  }
    		});
    }
    
    
    function bindOperationLogEvent(){
        $(document).on('click', '#detailLog', function(e) {
        //$('#operationLogModal').on('show.bs.modal', function (e) {
           
        	GLOBAL_VAL.tempCheckIduIDsForOperationLog = _getCheckedIDUIds();
    		if (GLOBAL_VAL.tempCheckIduIDsForOperationLog.length == 0) {
                
    			$.bizalert("Please select at least 1 check box to view operationlog!");
    			
              
                return false;
			}else {
				$('#operationLogModal').modal();
			}
        });
        
        
    	$('#operationLogModal').on('shown.bs.modal', function (e) {

        	GLOBAL_VAL.tempCheckIduIDsForOperationLog = _getCheckedIDUIds();
            if (GLOBAL_VAL.tempCheckIduIDsForOperationLog.length == 0) {
              $('#operationLogModal').modal('hide');
            }
            
            
    		var now = new Date();
    		var nowYear = now.getFullYear(),
    			nowMonth = now.getMonth()+1,
    			nowDate = now.getDate();
    		var nintyDaysAgo = new Date(nowYear,nowMonth-1,nowDate - 90);
    		var IDUIds = GLOBAL_VAL.tempCheckIduIDsForOperationLog;

            $.axs("../rc/getRCOperationsLog.htm", {
            	"unitIDs":IDUIds.toString(),
            	"pageNo":1,
                "toDateTime": nowYear+"-"+ nowMonth + "-" + nowDate,
                "fromDateTime": nintyDaysAgo.getFullYear()+"-"+(nintyDaysAgo.getMonth()+1)+"-"+nintyDaysAgo.getDate()
            }, function(data) {
            	if (data != null && typeof(data) != "undefined" && typeof(data.errorMessage) == "undefined") {
                    console.log(data);
                    //appendOperLogTable(data);

                    setOperLogTable(data.data);
                    //render pagination
                    
        			$("#operationLogModal #opeartionLogPagination").paginate({
        				count 		: data.pageCount,
        				start 		: 1,
        				display     : 20,
        				border					: false,
        				text_color  			: '#888',
        				background_color    	: '#EEE',	
        				text_hover_color  		: 'black',
        				background_hover_color	: '#CFCFCF',
        				images					: false,
        				rotate      : false,
        				mouse					: 'press',
        				onChange     			: function(page){
                            console.log("Page" + page)
        		            $.axs("../rc/getRCOperationsLog.htm", {
        		            	"unitIDs":IDUIds.toString(),
        		            	"pageNo":page,
        		                "toDateTime": nowYear+"-"+ nowMonth + "-" + nowDate,
        		                "fromDateTime": nintyDaysAgo.getFullYear()+"-"+(nintyDaysAgo.getMonth()+1)+"-"+nintyDaysAgo.getDate()
        		            }, function(data) {
        		            	var operationData = [];
        		            	if (data != null && typeof(data) != "undefined" && typeof(data.errorMessage) == "undefined") {
        		            		operationData = data.data;
        		            	}
        		            	
        		            	setOperLogTable(operationData);
        		                
        		            },"post");
        				}
        			});
                    
        			//For fixing IE cannot hide last page issue.
        			var ulPageWidth = $("#operationLogModal ul.jPag-pages ").width();
        			$("#operationLogModal ul.jPag-pages ").width(ulPageWidth + 50);
            	}else {
            		setOperLogTable([]);
            	}
            },"post");
            
            
    	})


    	$('#operationLogModal').on('hide.bs.modal', function (e) {
                if(GLOBAL_VAL.operLogTable != null){
                GLOBAL_VAL.operLogTable.clear();
                }
//                GLOBAL_VAL.operLogTable.destroy();
                $("#opeartionLogPagination").empty();
    	})

    }



    function setOperLogTable(data){
            var rclog_data = {data : []};
            var rowData = [];
             
            for(var i = 0; i < data.length; i++){


                rowData = [data[i].site, data[i].location,data[i].iduName,
                data[i].powerstatus, data[i].temperature + "&#176C", data[i].airconmode, data[i].fanspeed, data[i].flapMode,data[i].energysaving,
                prohibitionDescription(
                {'prohibitionpowerstatus':data[i].prohibitionpowerstatus, 'prohibitionsettemp':data[i].prohibitionsettemp,
                'prohibitonmode':data[i].prohibitonmode, 'prohibitionfanspeed':data[i].prohibitionfanspeed,
                'prohibitionwindriection':data[i].prohibitionwindriection,'prohibitionEnergySaving':data[i].prohibitionEnergySaving}
                )
                , data[i].date  + " " +data[i].time, data[i].username
                ]

                rclog_data.data.push(rowData);

            }
           console.log(rclog_data.data.length)
            /*
            Exponential Increase the Testing Data
            for(var j = 0; j < 3; j++){
               rclog_data.data = rclog_data.data.concat(rclog_data.data);

            }

            console.log(rclog_data.data.length)
            */
            if (GLOBAL_VAL.operLogTable != null) {
            	GLOBAL_VAL.operLogTable.clear();
            	GLOBAL_VAL.operLogTable.destroy();
            }
           
            GLOBAL_VAL.operLogTable = $('#operLogTable').DataTable( {
                "data": rclog_data.data,
                "deferRender": true,
                "ordering": true,
                "info" : false,
                "responsive":   true,
                //Scroller
                "paging":   false,
                "scrollCollapse": false,
                "scroller": true,
                "scrollY":  "360px",
                //End Of Scroller
                "filter": true,
                "language": {
                            "searchPlaceholder": "Search"
                    },
                "search": {
                        "caseInsensitive": false
                 },

                "columnDefs": [
                    { "targets": 3,
                    "createdCell": function (td, cellData, rowData, row, col) {
                        if ( cellData = "ON" ) {
                            $(td).attr("class","onPowerFont");
                        }else{
                            $(td).attr("class","offPowerFont");
                        }
                    }},
                    { "targets": 5,
                    "createdCell": function (td, cellData, rowData, row, col) {
                        if (typeof (cellData) != "undefined" && cellData != null) {
                            $(td).attr("class",getModeFontClass(cellData));
                        }
                    }},
                    { "targets": 7,
                    "createdCell": function (td, cellData, rowData, row, col) {
                        if (typeof (cellData) != "undefined" && cellData != null) {
                            $(td).attr("class","acDetailWind");
                            $(td).html("<div class='" + cellData.toLowerCase() + "FlapIcon'></div>")
                            //$(td).attr("class",cellData.toLowerCase() + "FlapIcon");
                            //$(td).html("");

                        }
                    }}
                ]

             });

           $("#operLogTable_wrapper .dataTables_scrollBody").mCustomScrollbar({
               scrollButtons: {
                   enable: true
               },
               theme: "dark-2"
           });


    }


    function prohibitionDescription(unitVO){
        //console.log(unitVO)
        var prohibitionHtml = "";
        if (unitVO.prohibitionpowerstatus == '1') {prohibitionHtml += "&#149" + "Oper. Prohibited</br>";}

        if (unitVO.prohibitionsettemp == '1') {prohibitionHtml += "&#149" + "Set Temp. Prohibited</br>";}

        if (unitVO.prohibitonmode == '1') {prohibitionHtml += "&#149" + "Mode Prohibited</br>";}

        if (unitVO.prohibitionfanspeed == '1') {prohibitionHtml += "&#149" + "Fan Speed Prohibited</br>";}

        if (unitVO.prohibitionwindriection == '1') {prohibitionHtml += "&#149" + "Flap Prohibited</br>";}

        if (unitVO.prohibitionEnergySaving == '1') {prohibitionHtml += "&#149" + "Energy Saving Prohibited</br>";}
        return prohibitionHtml;
    }

    function appendOperLogTable(data){

                var acOperLogTableHtml = "";
                if (data != null && typeof(data) != "undefined") {
                	for (var i = 0; i < data.length; i++) {
						var log = data[i];
						acOperLogTableHtml += getOperLogLineHtml(log);
					}
                }
                initOperLogTable(acOperLogTableHtml);

                $("#operLogTable_wrapper .dataTables_scrollBody").mCustomScrollbar({
                    scrollButtons: {
                        enable: true
                    },
                    theme: "dark-2"
                });


    }
    
    function getOperLogLineHtml(unitVO) {
        var html = "<tr>"+getTDHtml(unitVO.site, "acOperLogSite") + getTDHtml(unitVO.location, "acOperLogLoc")  + 
		getTDHtml(unitVO.iduName, "acOperLogName") + getTDHtml(unitVO.powerstatus, "acOperLogStatus "+unitVO.powerstatus.toLowerCase()+"PowerFont");
        
        if (unitVO.powerstatus.toLowerCase() != "off") {
        	var flapModeHtml = unitVO.flapMode;
        	if (flapModeHtml != "-") {flapModeHtml = "<div class='" + unitVO.flapMode.toLowerCase() + "FlapIcon'></div>";}
        	
        	html += getTDHtml(unitVO.temperature == "-"?"-":unitVO.temperature + "&#176C", "acOperLogTemp") + getTDHtml(unitVO.airconmode, "acOperLogMode " + getModeFontClass(unitVO.airconmode)) + 
			getTDHtml(unitVO.fanspeed, "acOperLogFanSpeed") +
			getTDHtml(flapModeHtml, "acOperLogWind") + getTDHtml(unitVO.energysaving, "acOperLogESaving");
		}else {
			html += getTDHtml("-", "acOperLogTemp") + 
			getTDHtml("-", "acOperLogMode") + getTDHtml("-", "acOperLogFanSpeed")  + 
			getTDHtml("-", "acOperLogWind") + getTDHtml("-", "acOperLogESaving");
		}
        
        var prohibitionHtml = "";
        if (unitVO.prohibitionpowerstatus == '1') {prohibitionHtml += "&#149" + "Oper. Prohibited</br>";}
        
        if (unitVO.prohibitionsettemp == '1') {prohibitionHtml += "&#149" + "Set Temp. Prohibited</br>";}
        
        if (unitVO.prohibitonmode == '1') {prohibitionHtml += "&#149" + "Mode Prohibited</br>";}
        
        if (unitVO.prohibitionfanspeed == '1') {prohibitionHtml += "&#149" + "Fan Speed Prohibited</br>";}
        
        if (unitVO.prohibitionwindriection == '1') {prohibitionHtml += "&#149" + "Flap Prohibited</br>";}
        
        if (unitVO.prohibitionEnergySaving == '1') {prohibitionHtml += "&#149" + "Energy Saving Prohibited</br>";}
        
        html += getTDHtml(prohibitionHtml, "acOperLogProhibition") + getTDHtml(unitVO.date + " " + unitVO.time, "acOperLogDate")  + 
				getTDHtml(unitVO.username, "acOperLogUserId")  + "</tr>";
        
        return html;
    }
    
    function bindTabEvent(){
        $(document).on("click", "#inner a[data-toggle='tab']", function() {
            var target = $(this).attr("href");
            if (target == "#acMapTab" && GLOBAL_VAL.floorMap != null) {
            	GLOBAL_VAL.floorMap.invalidateSize();
			} else {
                
                if(target == "#acODUDetailTab"){
                    GLOBAL_VAL.ODUDetailTable.columns.adjust().draw();
                }else if(target == "#acDetailTab"){
                    GLOBAL_VAL.IDUDetailTable.columns.adjust().draw();
                }else if(target == "#acCADetailTab"){
                    window.glo.CADetailTable.columns.adjust().draw();
                }
                
				$(target + " .dataTables_scrollBody").mCustomScrollbar("update");
				$(target + " .dataTables_scroll").resize();
			}
        });
    }
    
    function bindAcMainDetaiEvent(){
        $(document).on("click", "#acMainDetailTbody .acDetailCheckbox", function() {
        	var $this = $(this);
        	var currentStatus = $this.prop("checked");
        	if (currentStatus) {
        		$("#acMainDetailTbody tr").removeClass("trSelected");
        		$("#acMainDetailTbody .acDetailCheckbox:checked").not($this).prop("checked",false);
        		
				$this.parent().parent().addClass("trSelected");
				
	        	var $curTr = $this.parent().parent();
	        	var comp1 = $curTr.find(".acODUDetailComp1").text();
	        	var comp2 = $curTr.find(".acODUDetailComp2").text();
	        	var comp3 = $curTr.find(".acODUDetailComp3").text();
	        	
	        	$("#comp1Value").text(comp1);
	        	$("#comp2Value").text(comp2);
	        	$("#comp3Value").text(comp3);
        	}else {
        		$this.parent().parent().removeClass("trSelected")
        	}
        });
        
    }
 
    function initMonitoringCtrl() {
        // clear
        // Move modal to body
        // Fix Bootstrap backdrop issue with animation.css
        $('.modal').appendTo("body");
        // ajax then update acControl
//        updateAcContorl();
        
        if(!hasSelectedGroupInSession()){
        	initIDUDetailTable(null);
        	initODUDetailTable(null);
        	//initMaintenanceDetailTable("");
            initCADetailTable(null);
        	return;
        }
        /*
         * else {
         * 	IDU AND ODU will populated by clicking display button triggered after jstree ready function 
         * }
         */
        
        //initOperationLogDetail();
    }

    function initIDUDetailTable(data){
        if (GLOBAL_VAL.IDUDetailTable != null) {
        	GLOBAL_VAL.IDUDetailTable.destroy();
		}
        
        //$("#acDetailTbody").html(detailHtml);
        
        if (data != null && typeof(data) != "undefined") {
            for(var i = 0; i < data.length; i++){
                
                data[i]["refUnitId"] = _getUnitId(data[i]);
                //&#149 is ●,&#176 is ○
                var proValueMap = {"prohibited":"&#149","notProhibited":"-","-":"-"};
                var disabledHtml = data[i].rc_flag == 1? "" : " disabled ";
                
                
                data[i]["checkbox"] = "<td class='acDetailHeaderCheckbox'><input id=ck"+ data[i].iduId + disabledHtml + " class='acDetailCheckbox bizCheckbox' type='checkbox' data-unitid=" + data[i].iduId +" /><label for=ck"+ data[i].iduId + "></label></td>";
                 
                data[i]["alarmCodeHtml"] = getAlarmCodeHtml(data[i].alarmCode);
                 
                var temp = data[i].temperature;
                if (temp != "-") {
                    data[i].temperature += "&#176C";
                }

                var roomTemp = data[i].roomTemp
                if (roomTemp != "-") {
                    data[i].roomTemp += "&#176C";
                }

                var modeStr = data[i].mode.toLowerCase();
                if (modeStr.indexOf("auto") != -1) {data[i].mode = "Auto";}
                //getModeFontClass(unitVO.mode
                
                data[i].prohibitRCPower = proValueMap[data[i].prohibitRCPower];
                data[i].prohibitRCTemp = proValueMap[data[i].prohibitRCTemp];
                data[i].prohibitRCMode = proValueMap[data[i].prohibitRCMode];
                data[i].prohibitRCFanSpeed = proValueMap[data[i].prohibitRCFanSpeed];
                data[i].prohibitRCFlapMode = proValueMap[data[i].prohibitRCFlapMode];
                data[i].prohibitRCEnergySaving = proValueMap[data[i].prohibitRCEnergySaving];
                
                
                data[i]["flapModeHtml"] = data[i].flapMode;
                if (data[i]["flapModeHtml"] != "-") {data[i]["flapModeHtml"] = "<div class='" + data[i].flapMode.toLowerCase() + "FlapIcon'></div>";}
                
                data[i]["type_parentId"]= data[i].type+"/" + data[i].parentChild
                
                data[i]["viewinmap"] = getTDHtml('<a class="IDUViewMapLink" data-unitid='+ data[i].iduId+' data-svgId='+ data[i].svgId+' href="javascript:void(0);">View</a>',"acDetailViewMap") + "</tr>";
                
            }
        }
        
        GLOBAL_VAL.IDUDetailTable = $('#IDUDetailTable').DataTable({
            //dom: 'frtip',
            
            
            "ordering": true,
            "paging":   false,
            "filter": true,
            "info" : false,
            "responsive":   true,
            "deferRender":    true,
            
            "scroller": true,
            "scrollY":  GLOBAL_VAL.scrollY,
            "scrollCollapse": false,

            //autoWidth: false,
            "createdRow": function ( row, data, index ) {
                $(row).attr( "data-rc",  data.rc_flag);
                $(row).attr( "refDomId",  data.refUnitId);   
                $(row).attr( "id",  data.refUnitId + "tr");   
                  
            },
            "columnDefs": [         
                {
                orderable: false,
                targets:   4,
                "createdCell": function (td, cellData, rowData, row, col) {
                    $(td).addClass(cellData.toLowerCase()+"PowerFont");
                }
                },
                {
                orderable: false,
                targets:   7,
                "createdCell": function (td, cellData, rowData, row, col) {
                    $(td).addClass(getModeFontClass(cellData));
                }
                }
                
                
            ],

            "order": [[ 1, 'asc' ]],
            "data": data,
			"columns": [
                { "data" : "checkbox"
                , "className": "acDetailHeaderCheckbox"
                },
				{ "data": "site"
                , "className": "acDetailSite" 
                },
				{ "data": "location"
                , "className": "acDetailLoc"
                },
				{ "data": "idu_Name"
                , "className": "acDetailName" 
                },
				{ "data": "power"
                , "className": "acDetailStatus" 
                },
				{ "data": "temperature", 
                "className": "acDetailTemp" 
                },
				{ "data": "roomTemp", 
                "className": "acDetailRoomTemp" 
                },
				{ "data": "mode"
                , "className": "acDetailMode" 
                },
				{ "data": "fanSpeed"
                , "className": "acDetailFanSpeed"
                },
				{ "data": "flapModeHtml"
                , "className": "acDetailWind"
                },
				{ "data": "ecoNavi"
                , "className": "acDetailESaving"
                },
				{ "data": "alarmCode"
                , "className": "acDetailAlarmCode"
                },
				{ "data": "prohibitRCPower"
                , "className": "acDetailPro1"
                },
				{ "data": "prohibitRCTemp"
                , "className": "acDetailPro2"
                },
				{ "data": "prohibitRCMode"
                , "className": "acDetailPro3"
                },
				{ "data": "prohibitRCFanSpeed"
                , "className": "acDetailPro4"
                },
				{ "data": "prohibitRCFlapMode"
                , "className": "acDetailPro5"
                },
				{ "data": "prohibitRCEnergySaving"
                , "className": "acDetailPro6"
                },
				{ "data": "type_parentId"
                , "className": "acDetailType detailPCTd"
                },
				{ "data": "caStatus"
                , "className": "acDetailCAStatus"
                }
			],
            //select: true,
            "language": {
                        "searchPlaceholder": "Search"
                },

			 "search": {
	   			    "caseInsensitive": false
			  }
    	});
        
 
    }
    
    function initODUDetailTable(data){

        if (GLOBAL_VAL.ODUDetailTable != null) {
        	GLOBAL_VAL.ODUDetailTable.destroy();
		}
        if (data != null && typeof(data) != "undefined") {
            for(var i = 0; i < data.length; i++){
                //Assigned to another object or enhance the data with html element
                
                /*
                selectedGroup.caData[i]["checkbox"] = '<input id="ckCA' + data[i]["id"] +  '" class="caDetailCheckbox bizCheckbox" type="checkbox" data-unitid="'
                    + data[i]["id"] + '"><label for="ckCA' + data[i]["id"] + '" class="labelck" id="lck"></label>';
                
                <td class="acDetailHeaderCheckbox"><input id="ckOdu631" class="acDetailCheckbox bizCheckbox" type="checkbox" data-unitid="631"><label for="ckOdu631"></label></td>
                */
                var outdoorTemp = data[i].outdoorTemp;
                if (outdoorTemp == 0) {
                    outdoorTemp = "-";
                }else {
                    outdoorTemp = outdoorTemp.toFixed(1);
                    outdoorTemp += "&#176C";
                }
                data[i].outdoorTemp = outdoorTemp;
                
                data[i]["checkbox"] = '<input id="ckOdu' + data[i]["oduId"] +  '" class="acDetailCheckbox bizCheckbox" type="checkbox" data-unitid="' + data[i]["oduId"] + '"><label for="ckOdu' + data[i]["oduId"] + '" class="labelck" id="lck"></label>';
                
                data[i]["type_parentId"]= data[i].type+"/" + data[i].parentChild
                
	            data[i]["viewinmap"] = getTDHtml('<a class="ODUViewMapLink" data-unitid='+ data[i].oduId+' data-svgId='+ data[i].svgId+' href="javascript:void(0);">VIew</a>',"acODUDetailViewMap");
                
                
            }
        }
        

        //$("#acODUDetailTbody").html(detailHtml);
        /*
        GLOBAL_VAL.ODUDetailTable = $('#ODUDetailTable').DataTable({
            "ordering": true,
            "paging":   false,
            "filter": true,
            "info" : false,
            "responsive":   true,
            "deferRender":    true,
            "scrollCollapse": false,
            "scroller": true,
            "scrollY":  GLOBAL_VAL.scrollY,
            "columnDefs": [{ orderable: false, targets: 0 }],
            "order": [[ 1, "asc" ]],
            "language": {
			          "searchPlaceholder": "Search"
			 },
			 "search": {
	   			    "caseInsensitive": false
  			  }
        });
        */
         
         GLOBAL_VAL.ODUDetailTable = $('#ODUDetailTable').DataTable({
            //dom: 'frtip',
            
            
            "ordering": true,
            "paging":   false,
            "filter": true,
            "info" : false,
            "responsive":   true,
            "deferRender":    true,
            
            "scroller": true,
            "scrollY":  GLOBAL_VAL.scrollY,
            "scrollCollapse": false,

            //autoWidth: false,

            "columnDefs": [         
                {
                orderable: false,
               
                targets:  [6,7,8],
                "createdCell": function (td, cellData, rowData, row, col) {
                    
                    //$(td).addClass(cellData);
                    if (cellData == "0" || cellData == "0hrs") {
                        $(td).addClass(cellData);
                        
                        
                    } 
                }
                }
            ],

            "order": [[ 1, 'asc' ]],
            "data": data,
			"columns": [
                { "data" : "checkbox"
                , "className": "acDetailHeaderCheckbox"
                },
				{ "data": "site"
                , "className": "acODUDetailSite" 
                },
				{ "data": "slinkAddress"
                , "className": "acODUDetailSlink"
                },
				{ "data": "odu_Name"
                , "className": "acODUDetailName" 
                },
				{ "data": "alarmCode"
                , "className": "acODUDetailAlarmCode" 
                },
				{ "data": "outdoorTemp", 
                "className": "acODUDetailOutTemp" 
                },
				{ "data": "maintenanceCountDownComp1", 
                "className": "acODUDetailComp1" 
                },
				{ "data": "maintenanceCountDownComp2"
                , "className": "acODUDetailComp2" 
                },
				{ "data": "maintenanceCountDownComp3"
                , "className": "acODUDetailComp3"
                },
				{ "data": "type_parentId"
                , "className": "acODUDetailType detailPCTd"
                }
			],
            //select: true,
            "language": {
                        "searchPlaceholder": "Search"
                },

			 "search": {
	   			    "caseInsensitive": false
			  }
    	});
        
        $("#ODuDetailTable_wrapper .dataTables_scrollBody").mCustomScrollbar({
            scrollButtons: {
                enable: true
            },
            theme: "dark-2"
        });

        
    }
    
    function initMaintenanceDetailTable(detailHtml){
    	if (GLOBAL_VAL.ODUDetailTable != null) {
        	GLOBAL_VAL.ODUDetailTable.destroy();
		}
        $("#acMainDetailTbody").html(detailHtml);
        
        GLOBAL_VAL.ODUDetailTable = $('#MainDetailTable').DataTable({
            "ordering": true,
            "paging":   false,
            "filter": true,
            "info" : false,
            "responsive":   true,
            "deferRender":    true,
            "scrollCollapse": false,
            "scroller": true,
            "scrollY":  "300px",
            "columnDefs": [{ orderable: false, targets: 0 }],
            "order": [[ 1, "asc" ]],
            "language": {
			          "searchPlaceholder": "Search"
			 },
			 "search": {
	   			    "caseInsensitive": false
			  }
        });
        
        //clear compressor value
        $("#comp1Value").text("-");
        $("#comp2Value").text("-");
        $("#comp3Value").text("-");
    }
    
    function initOperLogTable(detailHtml){
        if (GLOBAL_VAL.operLogTable != null) {
        	GLOBAL_VAL.operLogTable.destroy();
		}
        $("#acOperLogTbody").html(detailHtml);
        
        GLOBAL_VAL.operLogTable = $("#operLogTable").DataTable({
            "ordering": true,
            "paging":   false,
            "filter": true,
            "info" : false,
            "responsive":   true,
            "deferRender":    true,
            "scrollCollapse": false,
            "scroller": true,
            "scrollY":  "300px",
            "language": {
			          "searchPlaceholder": "Search"
			 },
			 "search": {
	   			    "caseInsensitive": false
			  }
        });
    }
    
    
     function initCADetailTable(detailHtml){


        if (window.glo.CADetailTable != null) {
        	window.glo.CADetailTable.destroy();
		    }
        //$("#acCADetailTbody").html(detailHtml);
        /*
        GLOBAL_VAL.CADetailTable = $('#CADetailTable').DataTable({
            "ordering": true,
            "paging":   false,
            "filter": false,
            "info" : false,
            "responsive":   true,
            "deferRender":    true,
            "scrollCollapse": false,
            "scroller": true,
            "scrollY":  GLOBAL_VAL.scrollY,
            "columnDefs": [{ orderable: false, targets: 0 }],
            "order": [[ 1, "asc" ]]
        });
        */

        var selectedGroup = {caData: []};
        //console.log(JSON.stringify(GLOBAL_VAL.caData));
        //console.log(JSON.stringify(detailHtml));
        /*
        for(var i = 0; i < GLOBAL_VAL.caData.length; i++){
            for(var j = 0; j < detailHtml.length; j++){

                if(GLOBAL_VAL.caData[i].group_id == detailHtml[j]){
                    selectedGroup.caData.push(GLOBAL_VAL.caData[i]);
                }
            }
        }
*/
        var data = GLOBAL_VAL.caData;
        if(GLOBAL_VAL.caData != null){
        for(var i = 0; i < GLOBAL_VAL.caData.length; i++){
            GLOBAL_VAL.selectedCA.push(GLOBAL_VAL.caData[i].id);
            selectedGroup.caData.push(GLOBAL_VAL.caData[i]);
            selectedGroup.caData[i]["checkbox"] = '<input id="ckCA' + data[i]["id"] +  '" class="caDetailCheckbox bizCheckbox" type="checkbox" data-unitid="'
                + data[i]["id"] + '"><label for="ckCA' + data[i]["id"] + '" class="labelck" id="lck"></label>';

        }
      }
       window.glo.CADetailTable = $('#CADetailTable').DataTable({
            dom: 'frtip',
            "data": selectedGroup.caData,

            "columnDefs": [ {
                orderable: false,
                className: '',
                targets:   0,
                /*
                "createdCell": function (td, cellData, rowData, row, col) {

                            $(td).html('<input id="ck2" class="acDetailCheckbox bizCheckbox" type="checkbox" data-unitid="2"><label for="ck2" id="lck"></label>');

                }
                */

                }
            ],

                    /*

                "template": function (obj, type, value) {
                        return '<input id="ck2" class="acDetailCheckbox bizCheckbox" type="checkbox" data-unitid="2"><label for="ck2"></label>';
                    }
                    */

            /*
            select: {
                style:    'os',
                //selector: 'td:first-child, td, .labelck, .bizCheckbox, #lck'
                selector: 'td:first-child, td, td:first-child > #lck, td:first-child > .caDetailCheckbox, td:first-child > input+label'
                //selector: 'td:first-child, td, tr'
            },
            */

            "order": [[ 1, 'asc' ]],
			"columns": [
                { "data" : "checkbox"},
				{ "data": "company_name" },
				{ "data": "site_name" },
				{ "data": "address" },
				{ "data": "mov1pulse_id" },
				{ "data": "mov1pulse_type" },
				{ "data": "mov1pulse" },
				{ "data": "mov1pulse_factor" },
				{ "data": "mov2pulse_id" },
				{ "data": "mov2pulse_type" },
				{ "data": "mov2pulse" },
				{ "data": "mov2pulse_factor" },
				{ "data": "mov4pulse_id" },
				{ "data": "mov4pulse_type" },
				{ "data": "mov4pulse" },
				{ "data": "mov4pulse_factor" },
				{ "data": "alarm_code" }
			],
            //select: true,
            "language": {
                        "searchPlaceholder": "Search"
                },

			 "search": {
	   			    "caseInsensitive": false
			  }
    	});



    }
    
    $('#caDownloadButton').on('click', function(){
        //$.bizalert("clicked")
        window.glo.CADetailTable.button( '0' ).trigger();
        //GLOBAL_VAL.CADetailTable.button( '0-0' ).trigger();

    });
    
    function updateIDUDetailTableAndMap(checkedControlGroupIds) {
    	
    	_refreshMapAndACDetailByGroupId(checkedControlGroupIds);
        _refreshMapAndCADetailByGroupId(checkedControlGroupIds);
//        var selectedGroup = GLOBAL_VAL.selectedGroup;
//        var selectedGroup = $("#groupMenuItem"+checkedControlGroupIds[0]);
//        var svgname = selectedGroup.attr("group-svg");
//        _refreshMapAndACDetailByGroupId(selectedGroup.attr("group-id"));
    }

    function _refreshMapAndACDetailByGroupId(groupIds) {
        $.axs("../acconfig/getACDetailsCA.htm", {  //Minimum changes
            "json_request": JSON.stringify({"id":[1,2,3],"idType":groupIds}) //Minimum changes
        }, function(data) {console.log("ca data: " + data);
            data.refrigerantList = new Array();
            var acDetailTableHtml1 = "";
            var groupOptionHtml = "";
            //_clearSvgIDURelationship();
            
            if (data != null && typeof(data) != "undefined" && typeof(data.errorMessage) == "undefined") {
            	//demo 
            	var displaySvgName = "panasonic_demo.svg";
            	var iduList = data.iduList;
            	if (typeof(iduList[0]) != "undefined") {
            		if (iduList[0].svg_Location == "") {
            			displaySvgName = "panasonic_demo.svg";
					}else {
						displaySvgName = iduList[0].svg_Location;
					}
				}
            	 
        		//setUnitIconsInMap("svg/"+displaySvgName);
        		
        		var groupNameArray = [];
            	for (var i = 0; i < iduList.length; i++) {
                    var unitInfo = iduList[i];
                    
                    //_saveSvgIdIDUListRelationship(unitInfo.svgId,unitInfo.iduId);
                    /*
                    var IDUSvgName = unitInfo.svg_Location;
                    if (iduList[0].svg_Location == "") {
                    	IDUSvgName = "panasonic_demo.svg";
					}
                    
                    if (displaySvgName == IDUSvgName) {
                    	//addImageLayer(unitInfo, "IDU");
					}
                    
                    if (groupNameArray.indexOf(IDUSvgName) == -1) {
                    	groupNameArray.push(IDUSvgName);
                    	groupOptionHtml += _createOptionForGroupDropDown(unitInfo.svgId,unitInfo.svgDisplayName);
					}
                    */
                    //acDetailTableHtml1 += getACDetailLineHtml(unitInfo);
				}
            	initIDUDetailTable(iduList);
//                } else if (typeof(oduList) != "undefined") {
//                    for (var i = 0; i < oduList.length; i++) {
//                        var unitInfo = oduList[i];
//                        addImageLayer(unitInfo, "ODU");
//                        acDetailTableHtml1 += getODUDetailLineHtml(unitInfo);
//                    }
//                }

            }
            
            //initIDUDetailTable(acDetailTableHtml1);
            
            //$("#groupSelects").html(groupOptionHtml);
            
            $("#IDUDetailTable_wrapper .dataTables_scrollBody").mCustomScrollbar({
                scrollButtons: {
                    enable: true
                },
                theme: "dark-2"
            });
        },"post");
    }
    
    function _refreshMapAndCADetailByGroupId(groupIds) {

    	
    		
        $.axs("../acconfig/getCADetails3.htm", {
            "cache": false,
            "json_request": JSON.stringify({"id":[1,2,3,4],"idType":groupIds }) // minimum changes
        }, function(data) {console.log(data);
            for(var i = 0; i < data.length; i++){
            	
	        	for (var k in data[i]) {
	        		if(data[i][k] == null){
	        			data[i][k] = "-";
	        		}	
	    	    }
            }
            GLOBAL_VAL.caData = data;
            //var checkedIdList = getCheckedGroupIds();
            initCADetailTable(null); //minimum changes

            //CADetailTable
            $("#CADetailTable_wrapper .dataTables_scrollBody").mCustomScrollbar({
                scrollButtons: {
                    enable: true
                },
                theme: "dark-2"
            });

        });

    	
    }
    
    function _clearSvgIDURelationship(){
    	GLOBAL_VAL.svgIdIDUListMap = {};
    }
    
    function _clearSvgODURelationship(){
    	GLOBAL_VAL.svgIdODUListMap = {};
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
    
    function _saveSvgIdOduListRelationship(svgId,oduId){
    	var oduList = (GLOBAL_VAL.svgIdODUListMap)[svgId];
    	if (typeof(oduList) == "undefined") {
    		oduList = [];
		}
    	oduList.push(oduId);
    	(GLOBAL_VAL.svgIdODUListMap)[svgId] = oduList;
    }
    
    function updateFloorMap(svgId,selectedId,unitType){
    	var iduList = (GLOBAL_VAL.svgIdIDUListMap)[svgId];
    	var url = "../acconfig/getACDetails.htm",
    		paramObject = {
    	            "json_request": JSON.stringify({"id":iduList,"idType":"indoorUnit"})
            };
    	if (unitType == "ODU") {
    		url = "../acconfig/getACDetailsODU.htm";
    		var oduList = (GLOBAL_VAL.svgIdODUListMap)[svgId];
    		paramObject = {
    	            "json_request": JSON.stringify({"id":oduList,"idType":"outdoorUnit"})
            };
		}else {
			unitType = "IDU";
		}
        $.axs(url,paramObject, function(data) {console.log(data);
            if (data != null && typeof(data) != "undefined") {
            	//demo
            	if (unitType == "IDU") {
            		_displayIDUInFloorMap(data);
				}else {
					_displayODUInFloorMap(data);
				}
            	
            	if (selectedId != null && typeof(selectedId) != "undefined") {
        		    $("#"+unitType + selectedId).click();
            	}	
            }
        },"post");
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
        var refUnitId = _getUnitId(unitVO);
        //&#149 is ●,&#176 is ○
        var proValueMap = {"prohibited":"&#176","notProhibited":"&#149","-":"-"};
        var proClassNameMap = {"prohibited":"lineHeight27","notProhibited":"","-":"-"};

        var disabledHtml = unitVO.rc_flag == 1? "" : " disabled ";

        var trHtml = "<tr id=" + refUnitId + "tr refDomId=" + refUnitId + " data-rcFlag="+unitVO.rc_flag +"><td class='acDetailHeaderCheckbox'><input id=ck"+ unitVO.iduId + disabledHtml+ " class='acDetailCheckbox bizCheckbox' type='checkbox' data-unitid=" + unitVO.iduId +" /><label for=ck"+ unitVO.iduId + "></label></td>" +
					getTDHtml(unitVO.site, "acDetailSite") + getTDHtml(unitVO.location, "acDetailLoc")  +
					getTDHtml(unitVO.idu_Name, "acDetailName") + getTDHtml(unitVO.power, "acDetailStatus "+unitVO.power.toLowerCase()+"PowerFont");

        var alarmCodeHtml = getAlarmCodeHtml(unitVO.alarmCode);
        var temp = unitVO.temperature;
        if (temp != "-") {
        	temp += "&#176C";
		}

        var roomTemp = unitVO.roomTemp
        if (roomTemp != "-") {
        	roomTemp += "&#176C";
		}

//        if (unitVO.power == "ON") {
        	var modeStr = unitVO.mode.toLowerCase();
        	if (modeStr.indexOf("auto") != -1) {unitVO.mode = "Auto";}

        	var flapModeHtml = unitVO.flapMode;
        	if (flapModeHtml != "-") {flapModeHtml = "<div class='" + unitVO.flapMode.toLowerCase() + "FlapIcon'></div>";}

			trHtml +=
					getTDHtml(temp, "acDetailTemp") + getTDHtml(roomTemp, "acDetailRoomTemp") + getTDHtml(unitVO.mode, "acDetailMode " + getModeFontClass(unitVO.mode)) +
					getTDHtml(unitVO.fanSpeed, "acDetailFanSpeed")+
					getTDHtml(flapModeHtml, "acDetailWind") +
					getTDHtml(unitVO.energy_saving, "acDetailESaving") + getTDHtml(alarmCodeHtml, "acDetailAlarmCode") +
					getTDHtml(proValueMap[unitVO.prohibitRCPower], "acDetailPro1 "+proClassNameMap[unitVO.prohibitRCPower]) +
					getTDHtml(proValueMap[unitVO.prohibitRCTemp], "acDetailPro2 "+proClassNameMap[unitVO.prohibitRCTemp]) +
					getTDHtml(proValueMap[unitVO.prohibitRCMode], "acDetailPro3 "+proClassNameMap[unitVO.prohibitRCMode]) +
					getTDHtml(proValueMap[unitVO.prohibitRCFanSpeed], "acDetailPro4 "+proClassNameMap[unitVO.prohibitRCFanSpeed]) +
					getTDHtml(proValueMap[unitVO.prohibitRCFlapMode], "acDetailPro5 "+proClassNameMap[unitVO.prohibitRCFlapMode]) +
					getTDHtml(proValueMap[unitVO.prohibitRCEnergySaving], "acDetailPro6 "+proClassNameMap[unitVO.prohibitRCEnergySaving]) +
					getTDHtml(unitVO.type + "/" + unitVO.parentChild, "acDetailPC detailPCTd") + getTDHtml(unitVO.caStatus, "acDetailCAStatus");
//		}else {
//			trHtml +=
//					getTDHtml(temp, "acDetailTemp") + getTDHtml("-", "acDetailRoomTemp") + getTDHtml("-", "acDetailMode") +
//					getTDHtml("-", "acDetailFanSpeed") + getTDHtml("-", "acDetailWind") + getTDHtml("-", "acDetailESaving")+
//					getTDHtml(alarmCodeHtml, "acDetailAlarmCode") + getTDHtml("-", "acDetailPro1") + getTDHtml("-", "acDetailPro2") +
//					getTDHtml("-", "acDetailPro3") + getTDHtml("-", "acDetailPro4") + getTDHtml("-", "acDetailPro5") +
//					getTDHtml("-", "acDetailPro6") + getTDHtml(unitVO.type + "/" + unitVO.parentChild, "acDetailPC detailPCTd") +getTDHtml(unitVO.caStatus, "acDetailCAStatus");
//		}
/*
        return trHtml += getTDHtml('<a class="IDUViewMapLink" data-unitid='+ unitVO.iduId+' data-svgId='+ unitVO.svgId+' href="javascript:void(0);">View</a>',"acDetailViewMap") + "</tr>";

*/
        return trHtml;
    }
    
    function getModeFontClass(mode)	{
    	var className = "";
    	if (typeof (mode) != "undefined" && mode != null) {
        	var modeStr = mode.toLowerCase();
        	if (modeStr.indexOf("auto") != -1) {className = "autoModeFont";}
        	else if (modeStr.indexOf("cool") != -1) {className = "coolModeFont";}
    		else if (modeStr.indexOf("dry") != -1) {className = "dryModeFont";}
    		else if (modeStr.indexOf("fan") != -1) {className = "fanModeFont";}
    		else if (modeStr.indexOf("heat") != -1) {className = "heatModeFont";}
		}
    	return className;
    }
    function getAlarmCodeHtml(alarmCode){
        var alarmCodeHtml = alarmCode;
        if (alarmCodeHtml != null && alarmCodeHtml.toLowerCase() == "filter") {
        	alarmCodeHtml = "<div class='filterAlarmIcon'></div>";
		}
        return alarmCodeHtml;
    }
    function _displayIDUInFloorMap(data){
    	var svgName = "panasonic_demo.svg";
    	var iduList = data.iduList;
    	if (typeof(iduList[0]) != "undefined") {
    		if (iduList[0].svg_Location == "") {
    			svgName = "panasonic_demo.svg";
			}else {
				svgName = iduList[0].svg_Location;
			}
		}
    	 
		setUnitIconsInMap("svg/"+svgName);
		
    	for (var i = 0; i < iduList.length; i++) {
            var unitInfo = iduList[i];
            var IDUSvgName = unitInfo.svg_Location;
            if (iduList[0].svg_Location == "") {
            	IDUSvgName = "panasonic_demo.svg";
			}
            
            if (svgName == IDUSvgName && _isInSelectedGroup(unitInfo.svgId,unitInfo.iduId)) {
            	addImageLayer(unitInfo, "IDU");
			}
		}
    }
    
    function _displayODUInFloorMap(oduList){
    	var svgName = "XMU-RF-with-ODU-01.svg";
    	if (typeof(oduList[0]) != "undefined" && oduList[0].svg_Location != "") {
    		svgName = oduList[0].svg_Location;
		}
    	 
		setUnitIconsInMap("svg/"+svgName);
		
    	for (var i = 0; i < oduList.length; i++) {
            var unitInfo = oduList[i];
            var IDUSvgName = unitInfo.svg_Location;
            if (oduList[0].svg_Location == "") {
            	IDUSvgName = "XMU-RF-with-ODU-01.svg";
			}
            
            if (svgName == IDUSvgName) {
            	addImageLayer(unitInfo, "ODU");
			}
		}
    }

    function _isInSelectedGroup(svgId,iduId){
    	var iduList = (GLOBAL_VAL.svgIdIDUListMap)[svgId];
    	return iduList.indexOf(iduId) != -1;
    }
    
    function updateODUAndMainDetailTable(groupIds){

        $.axs("../acconfig/getODUDetails4.htm", { //Minimum Changes
            "json_request": JSON.stringify({"id":[1,2,3],"idType":groupIds}) //Minimum Changes
        }, function(data) {console.log(data);
            var acDetailTableHtml1 = "",
            	mainDetailTableHtml = "";
            //_clearSvgODURelationship();
            if (data != null && typeof(data) != "undefined") {
            	for (var i = 0; i < data.length; i++) {
                    var unitInfo = data[i];
                    //_saveSvgIdOduListRelationship(unitInfo.svgId,unitInfo.oduId);
                    //acDetailTableHtml1 += getODUDetailLineHtml(unitInfo);
                    mainDetailTableHtml += getMainDetailLineHtml(unitInfo);
				}
            	initODUDetailTable(data);
            }
            
            //initODUDetailTable(acDetailTableHtml1);
            //initMaintenanceDetailTable(mainDetailTableHtml);
            
            $("#ODUDetailTable_wrapper .dataTables_scrollBody").mCustomScrollbar({
                scrollButtons: {
                    enable: true
                },
                theme: "dark-2"
            });
        },"post");
    
    }
    
    function getODUDetailLineHtml(unitVO) {
        var refUnitId = "ODU" + unitVO.oduId;
        var html =  "<tr id=" + refUnitId + "tr refDomId=" + refUnitId + "><td class='acDetailHeaderCheckbox'><input id=ckOdu"+ unitVO.oduId +" class='acDetailCheckbox bizCheckbox' type='checkbox' data-unitid=" + unitVO.oduId +" /><label for=ckOdu"+ unitVO.oduId + "></label></td>" + 
        			getTDHtml(unitVO.site, "acODUDetailSite")  + getTDHtml(unitVO.slinkAddress, "acODUDetailSlink") + getTDHtml(unitVO.odu_Name, "acODUDetailName") + 
        			getTDHtml(unitVO.alarmCode, "acODUDetailAlarmCode") + getTDHtml(unitVO.outdoorTemp+"&#176C", "acODUDetailOutTemp") + 
//        			getTDHtml('-', "acODUDetailOilCheck") + getTDHtml('-', "acODUDetailEngineServ") + getTDHtml('-', "acODUDetailPowerGen") +
        			getTDHtml(unitVO.maintenanceCountDownComp1, "acODUDetailComp1" + _getRedFontBasedOnComp(unitVO.maintenanceCountDownComp1)) + 
        			getTDHtml(unitVO.maintenanceCountDownComp2, "acODUDetailComp2" + _getRedFontBasedOnComp(unitVO.maintenanceCountDownComp2)) +
        			getTDHtml(unitVO.maintenanceCountDownComp3, "acODUDetailComp3" + _getRedFontBasedOnComp(unitVO.maintenanceCountDownComp3)) +
        			getTDHtml(unitVO.type+"/" + unitVO.parentChild, "acODUDetailType detailPCTd");/* + 
        			getTDHtml('<a class="ODUViewMapLink" data-unitid='+ unitVO.oduId+' data-svgId='+ unitVO.svgId+' href="javascript:void(0);">VIew</a>',"acODUDetailViewMap") + "</tr>";*/
        return html;
    }

    function getMainDetailLineHtml(unitVO) {
        var refUnitId = "main" + unitVO.oduId;
        var html =  "<tr id=" + refUnitId + "tr refDomId=" + refUnitId + "><td class='acDetailHeaderCheckbox'><input id=ckMain"+ unitVO.oduId +" class='acDetailCheckbox bizCheckbox' type='checkbox' data-unitid=" + unitVO.oduId +" /><label for=ckMain"+ unitVO.oduId + "></label></td>" + 
        			getTDHtml(unitVO.site, "acODUDetailSite")  + getTDHtml(unitVO.slinkAddress, "acODUDetailSlink") + getTDHtml(unitVO.odu_Name, "acODUDetailName") + 
        			getTDHtml(unitVO.alarmCode, "acODUDetailAlarmCode") + getTDHtml(unitVO.outdoorTemp+"&#176C", "acODUDetailOutTemp") + 
//        			getTDHtml('-', "acODUDetailOilCheck") + getTDHtml('-', "acODUDetailEngineServ") + getTDHtml('-', "acODUDetailPowerGen") +
        			getTDHtml(unitVO.maintenanceCountDownComp1, "acODUDetailComp1" + _getRedFontBasedOnComp(unitVO.maintenanceCountDownComp1)) + 
        			getTDHtml(unitVO.maintenanceCountDownComp2, "acODUDetailComp2" + _getRedFontBasedOnComp(unitVO.maintenanceCountDownComp2)) +
        			getTDHtml(unitVO.maintenanceCountDownComp3, "acODUDetailComp3" + _getRedFontBasedOnComp(unitVO.maintenanceCountDownComp3)) +
        			getTDHtml(unitVO.type+"/" + unitVO.parentChild, "acODUDetailType detailPCTd");
        return html;
    }
    
    function _getRedFontBasedOnComp(compValue){
    	var className= "";
    	if (compValue == "0" || compValue == "0hrs") {
			className = " redFont";
		}
    	return className;
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

    function initOperationLogDetail() {
    	$('#operationLogModal #operationLog_datepicker').datepicker({
    		format: 'dd/mm/yyyy',
    		keyboardNavigation : false,
    		forceParse : false,
    		autoclose : true,
    		endDate: "7d",
    		todayHighlight:true
    	});
    }
    
    /**
     * ******************* Monitor Control part logic function
     * end*****************************
     */

});