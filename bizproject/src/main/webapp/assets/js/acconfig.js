window.glo = {};
window.glo = {CADetailTable: null, OpTable: null}

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
        MainDetailTable:null,
        OperLogTable:null,
        refreshTimer:null,
        scrollY:"660px",
        svgIdIDUListMap:{},
        svgIdODUListmap:{},
        $remoteControlDialog:null,
        unitHightLightDom:null,
        tempCheckIduIDsForOperationLog:null,
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
        },caData: null, selectedCA: [],CADetailTable : null
    };

    // highlight menu
    menu('acsettings');
    
    function getCurrentTabHref(){
    	return $(".nav-tabs li.active a").attr("href");//"#acDetailTab"
    }
    
    function clearSearchResult(){
		var tableArray = [GLOBAL_VAL.IDUDetailTable,GLOBAL_VAL.ODUDetailTable,
		                  window.glo.CADetailTable,GLOBAL_VAL.MainDetailTable];
		for (var i = 0; i < tableArray.length; i++) {
			var $table = tableArray[i];
			if ($table != null) {
				$table.search('').columns().search('').draw();
				//As other scrollbars will be refreshed when switch tab, only refresh scrollbar in current tab
			}
		}
    }
    
    $(document).on("click", "#displayButton", function(e) {
    	var idList = getCheckedGroupIds();
    	if(idList.length == 0){
    		$.bizalert("Please choose group");
    	}else {
    		clearFreshTimer();
    		
    		$('#checkboxMenuTree').jstree().save_state();

    		if (GLOBAL_VAL.autoFreshFlag) {
				saveCurrentTableState();
			}else{
	    		$("#acDetailHeaderCheckbox").removeClass("mixStatus").prop("checked",false);
	    		$("#acOUDDetailHeaderCheckbox").removeClass("mixStatus").prop("checked",false);
	    		$("#caDetailHeaderCheckbox").removeClass("mixStatus").prop("checked",false);
			}
    		
    		updateIDUDetailTableAndMap(idList);
    		updateODUAndMainDetailTable(idList);
    		
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

    function clearFreshTimer(){
    	if (GLOBAL_VAL.refreshTimer != null) {clearInterval(GLOBAL_VAL.refreshTimer);}
    }

    function setFreshTimer(){
    	  //refresh in 3mins depends on requirements
    	GLOBAL_VAL.refreshTimer = setInterval(function(){
    		GLOBAL_VAL.autoFreshFlag = true;
    		$("#displayButton").click();
	    }, 1000*60*3);
    }

    function saveCurrentTableState(){
    	var currentTabHref = getCurrentTabHref();
    	if (currentTabHref != null && currentTabHref != "") {
    		GLOBAL_VAL.currentTable.selectedTrIdList = [];
    		
    		$(currentTabHref+ " tr.trSelected").each(function( index ) {
    			GLOBAL_VAL.currentTable.selectedTrIdList.push($(this).attr("id"));
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
				"#acDetailTab":GLOBAL_VAL.IDUDetailTable,
				"#acODUDetailTab":GLOBAL_VAL.ODUDetailTable,
				"#acCADetailTab": window.glo.CADetailTable,
				"#acMaintenanceTab":GLOBAL_VAL.MainDetailTable,
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
        		console.log($tr.attr("data-rcFlag") == "1");
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

        $(document).on("click", "#acControlButton", function() {
        	//[iduArray,onlyParentArray]
        	var IDUArray = _getCheckedIDUIdsForRemoteControl();

        	if (IDUArray[0].length > 0) {
        		_showRemoteControlDialog(IDUArray);
        	}else {
        		$.bizalert("Please make a selection and try submitting again.");
        	}
        });

        $(document).on("click", "#caControlButton", function() {
        	//$.bizalert("Download Function coming soon");
            var id = _getCheckedCAIds();
            console.log(id)
        	url = "../acconfig/downloadCADetails2.htm";
        	if (id.length > 0) {
            	
            	var data_send = JSON.stringify({"id":id,"idType":"group",
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
        	        	//console.log(JSON.stringify(data));

        	            var Indoor_i= "ControlAdaptersUnit Details";
        	            var fileName = Indoor_i+"_"+"("+Indoor_i+").csv";

                        downloadAs(data, fileName);

                    }
                },"post");


        	}else {
        		$.bizalert("Please make a selection and try submitting again.");
        	}

        });

  
               
        
        //ODU download
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
        	}
        });
        
        $(document).on("click", "#acControlButtonInPopup", function() {
        	var id = $(this).attr("data-iduId");
        	var parentId = $(this).attr("data-parentId") ;
        	if (parentId == null || parentId == "null") {parentId = id;}

        	_showRemoteControlDialog([[id],[parentId]]);
        });

        function _showRemoteControlDialog(IDUArray){

        	updateAcContorl(IDUArray);
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
	    	        	console.log(JSON.stringify(data));

	    	            var Indoor_i= "indoorUnit Details";
	    	            var fileName = Indoor_i+"_"+"("+Indoor_i+").csv";

                        downloadAs(data, fileName);
	                }
	            },"post");
        	}else {
        		$.bizalert("Please select at least 1 check box to proceed download");
        	}
        });

      //ODU download
        $(document).on("click","#downODUButton", function( event, id ){
        	var ODUIds = _getCheckedODUIds();
        	url = "../acconfig/downloadACDetailsODU.htm";
        	if (ODUIds.length > 0) {
        		
            	var data_send = JSON.stringify({"id":ODUIds,"idType":"outdoorUnit",
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

                        downloadAs(data, fileName);
                    }
                },"post");
        	}else {
        		$.bizalert("please choose ODU");
        	}
        });
        
        var alertTimer = null;
        //AC detail prohibition popup tip
        $(document).on("click", ".prohibitionTitle", function() {
        	$("#prohibitionPopup").modal();
        	
        	alertTimer = setTimeout(function() {
        		$("#prohibitionPopup").modal("hide");
             }, 2000);
        });
        
        $('#prohibitionPopup').on('hidden.bs.modal', function (e) {
        	if (alertTimer != null) {
                clearTimeout(alertTimer);
            }
        });
        
       //popup for model number
        $(document).on("click", ".IDUModelNumberLink", function() {
        	var modelNumber = $(this).attr("data-deviceModel");
        	var message = getModelNumberRowHtml('IDU Model Number:'+ modelNumber)+getModelNumberRowHtml('ODU1 Name:');
        	$.bizinfo({
    			"title":"Device Information",
    			"message":message
    		});
        });
        
        $(document).on("click", ".ODUModelNumberLink", function() {
        	var modelNumber = $(this).attr("data-deviceModel");
        	var message = getModelNumberRowHtml('ODU Model Number:'+ modelNumber)+getModelNumberRowHtml('ODU1 Name:');
        	$.bizinfo({
    			"title":"Device Information",
    			"message":message
    		});
        });
        
        $(document).on("click", ".ACModelNumberLink", function() {
        	var modelNumber = $(this).attr("data-deviceModel");
        	var message = getModelNumberRowHtml('CA Model Number:'+ modelNumber);
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
        		$.bizalert("Please choose desired mode before proceeding.");
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
        		$.bizalert("Please choose desired mode before proceeding.");
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
    	var proValueMap = {"1":"&#149","0":"-"};
//    	var proClassNameMap = {"1":"lineHeight27","0":""};
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
					if (value == "1") {
						value = "ON";
					}else if(value == "0"){
						value = "OFF";
					}
					
					$td.html(value.toUpperCase());
				}
			}else if(param == "TEMPERATURE"){
		    	for (var i = 0; i < iduList.length; i++) {
					var $IduTr = $("#IDU"+ iduList[i] +"tr");
					var $td = $IduTr.find("td.acDetailTemp");
					$td.html(value + "&#176C");
				}
			}else if(param == "PROHIBITION_POWERSTATUS"){
				var className = "acDetailPro1 ";
		    	for (var i = 0; i < iduList.length; i++) {
					var $IduTr = $("#IDU"+ iduList[i] +"tr");
					var $td = $IduTr.find("td.acDetailPro1");
					$td.removeClass().addClass(className);
					$td.html(proValueMap[value]);
				}
			}else if(param == "PROHIBITION_SET_TEMP"){
				var className = "acDetailPro2 ";
		    	for (var i = 0; i < iduList.length; i++) {
					var $IduTr = $("#IDU"+ iduList[i] +"tr");
					var $td = $IduTr.find("td.acDetailPro2");
					$td.removeClass().addClass(className);
					$td.html(proValueMap[value]);
				}
			}else if(param == "PROHIBITON_MODE"){
				var className = "acDetailPro3 ";
		    	for (var i = 0; i < iduList.length; i++) {
					var $IduTr = $("#IDU"+ iduList[i] +"tr");
					var $td = $IduTr.find("td.acDetailPro3");
					$td.removeClass().addClass(className);
					$td.html(proValueMap[value]);
				}
			}else if(param == "PROHIBITION_FANSPEED"){
				var className = "acDetailPro4 ";
		    	for (var i = 0; i < iduList.length; i++) {
					var $IduTr = $("#IDU"+ iduList[i] +"tr");
					var $td = $IduTr.find("td.acDetailPro4");
					$td.removeClass().addClass(className);
					$td.html(proValueMap[value]);
				}
			}else if(param == "PROHIBITION_WINDRIECTION"){
				var className = "acDetailPro5 ";
		    	for (var i = 0; i < iduList.length; i++) {
					var $IduTr = $("#IDU"+ iduList[i] +"tr");
					var $td = $IduTr.find("td.acDetailPro5");
					$td.removeClass().addClass(className);
					$td.html(proValueMap[value]);
				}
			}else if(param == "PROHIBITION_ENERGY_SAVING"){
				var className = "acDetailPro6 ";
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
    	var outDoorTempPng = "MAP_Current_Status_Icon_Temp_Off.png",
    	inDoorTempPng = "MAP_Current_Status_Icon_RoomTemp_Off.png",
    	fanSpeedPng = "MAP_Current_Status_Icon_Fan_Off.png",
    	flapPng = "MAP_Current_Status_Icon_Flap_Off.png",
    	energySavingPng = "MAP_Current_Status_Icon_Energysavings_Off.png";
    	
    	if (indoorUnit.power.toLowerCase() != "off") {
    		outDoorTempPng = "MAP_Current_Status_Icon_outdoorTemp.png";
    		inDoorTempPng = "MAP_Current_Status_Icon_roomTemp.png";
        	fanSpeedPng = "MAP_Current_Status_Icon_Fan.png";
        	flapPng = "MAP_Current_Status_Icon_flapDefault.png";
        	energySavingPng = "MAP_Current_Status_Icon_energySaving.png";
    	}
    	
    	var buttonClass = indoorUnit.rc_flag == 1 ? "btn bizButton orangeGradient" : "btn bizButton orangeGradient disabled ";
    	var content = '<div style="width:287px;"><div style="background-color:#e6e6e6;font-size:15px;color:#4d4d4d;" class="title text-center"><p style="font-weight:bold">'+indoorUnit.location+"</p>"+indoorUnit.idu_Name+'</div>'+
			    	'<div class="settingBody">'+'<p style="padding:0 0 0 5px;margin:0px 0px;font-size:14px;color:#4d4d4d;height:23px;">Current Settings:</p>'+
					'<div class="settingContent" style="background-color:#e6e6e6">'+
						'<table><tr><td><img src='+GLOBAL_VAL.imgPath+'popup/'+outDoorTempPng +' /></td>'+
							'<td><img src='+GLOBAL_VAL.imgPath+'popup/'+inDoorTempPng +' /></td>'+
							'<td><img src='+GLOBAL_VAL.imgPath+'popup/'+fanSpeedPng +' /></td>'+
							'<td><img src='+GLOBAL_VAL.imgPath+'popup/'+flapPng +' /></td>'+
							'<td><img src='+GLOBAL_VAL.imgPath+'popup/'+energySavingPng +' /></td></tr>'+
							'<tr><td class="text-center"><span>'+indoorUnit.temperature+'</span></td>'+
							'<td class="text-center"><span>'+indoorUnit.roomTemp+'</span></td>'+
							'<td class="text-center"><span>'+indoorUnit.fanSpeed+'</span></td>'+
							'<td class="text-center"><span>'+indoorUnit.flapMode+'</span></td>'+
							'<td class="text-center"><span>'+indoorUnit.energy_saving+'</span></td></tr>'+
			//				'<tr><td class="text-center" colspan="5"><img src='+GLOBAL_VAL.imgPath+'popup/Map_current_Status_icon_Econavi.png /></td></tr>'
							'</table>'+
						'</div>'+
						'<p style="text-align:center;">'+
							'<button id = "acControlButtonInPopup" style="width:242px;margin-top:8px;margin-bottom:10px;margin-right:0" data-iduId='+indoorUnit.iduId + ' data-parentId='+indoorUnit.parentId + 
							' type="button" class="'+buttonClass+'">AC Remote Control &nbsp;<span class="fa fa-caret-right"></span></button></p>'+
						'<div style="padding:0 5px;"><p>IDU Model Number:'+indoorUnit.deviceModel + '</p><p>ODU1 Name:</p>'+
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
        	GLOBAL_VAL.tempCheckIduIDsForOperationLog = _getCheckedIDUIds();
    		if (GLOBAL_VAL.tempCheckIduIDsForOperationLog.length == 0) {
    			$.bizalert("Please make a selection and try submitting again.");
    			return false;
			}else {
				$('#operationLogModal').modal();
			}
        });
    	
    	
//    	$('#operationLogModal').on('show.bs.modal', function (e) {
//    		GLOBAL_VAL.tempCheckIduIDsForOperationLog = _getCheckedIDUIds();
//    		if (GLOBAL_VAL.tempCheckIduIDsForOperationLog.length == 0) {
//    			$.bizalert("Please select at least 1 check box to view operationlog!");
//    			return false;
//			}
//    	});

    	$('#operationLogModal').on('shown.bs.modal', function (e) {
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
        				display     : 10,
        				border					: false,
        				text_color  			: '#888',
        				background_color    	: '#EEE',	
        				text_hover_color  		: 'black',
        				background_hover_color	: '#CFCFCF',
        				images					: false,
        				rotate      : false,
        				mouse					: 'press',
        				onChange     			: function(page){
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
                GLOBAL_VAL.operLogTable.clear();
//                GLOBAL_VAL.operLogTable.destroy();
                $("#opeartionLogPagination").empty();
    	})

    }


    function setOperLogTable(data){
            var rclog_data = {data : []};
            var rowData = [];
            for(var i = 0; i < data.length; i++){
            	if (data[i].energysaving == "1") {
            		data[i].energysaving = "ON";
				}else if (data[i].energysaving == "0") {
            		data[i].energysaving = "OFF";
				}

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
                            if (cellData == "-") {
                            	$(td).html(cellData);
							}else {
								 $(td).html("<div class='" + cellData.toLowerCase() + "FlapIcon'></div>");
							}
                           
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

//        if (unitVO.powerstatus.toLowerCase() != "off") {
        	var flapModeHtml = unitVO.flapMode;
        	if (flapModeHtml != "-") {flapModeHtml = "<div class='" + unitVO.flapMode.toLowerCase() + "FlapIcon'></div>";}

        	html += getTDHtml(unitVO.temperature == "-"?"-":unitVO.temperature + "&#176C", "acOperLogTemp") + getTDHtml(unitVO.airconmode, "acOperLogMode " + getModeFontClass(unitVO.airconmode)) +
			getTDHtml(unitVO.fanspeed, "acOperLogFanSpeed") +
			getTDHtml(flapModeHtml, "acOperLogWind") + getTDHtml(unitVO.energysaving, "acOperLogESaving");
        /*
		}else {
			html += getTDHtml("-", "acOperLogTemp") +
			getTDHtml("-", "acOperLogMode") + getTDHtml("-", "acOperLogFanSpeed")  +
			getTDHtml("-", "acOperLogWind") + getTDHtml("-", "acOperLogESaving");
		}
        */
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

        $(document).on("click", "#resetConfirmButton", function() {
        	//get selected ODU in ac main table
        	var ids = [];
        	$("#acMainDetailTbody input:checked").each(function( index ) {
        		ids.push($(this).attr("data-unitid"));
        	});
        	if (ids.length != 1) {
				$.bizalert("please choose one ODU!");
			}else {
				var checkedCompressors = $("#acMainCompressorTbody input:checked");
				if (checkedCompressors.length > 0 ) {
					checkedCompressors.each(function( index ) {
						$.axs("../maintenance/resetThreshodAlert.htm", {
				            "json_request": JSON.stringify({"oduID":ids[0],"maintenanceID":parseInt($(this).attr("data-mainId"))})
				        }, function(data) {
				            if (data != null && typeof(data) != "undefined") {
				            	var idList = getCheckedGroupIds();
				            	if(idList.length == 0){
				            		$.bizalert("Please choose group");
				            	}else {
				            		//dummy quick fixing, performance is not good
				            		updateODUAndMainDetailTable(idList);
				            	}
				            }
				        },"post");
		        	});
				}else {
					$.bizalert("please choose compressor value");
				}
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

        if(GLOBAL_VAL.groupid === "25" ){
        	$("#weather").attr("src", GLOBAL_VAL.imgPath+"Weather_station.png");
        	$("#notification-efficiency").attr("src", GLOBAL_VAL.imgPath+"PSCDemo_NOtification_bedok.png");
        }else if(GLOBAL_VAL.groupid === "61"){
        	$("#weather").attr("src", GLOBAL_VAL.imgPath+"Weather_station_langen.png");
        	$("#notification-efficiency").attr("src", GLOBAL_VAL.imgPath+"PSCDemo_NOtification_Langen.png");
        }else{
        	$("#notification-efficiency").attr("src", GLOBAL_VAL.imgPath+"PSCDemo_NOtification.png");
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

        var d = new Date();
    	var sd = new Date(d.getTime() - (6 * 24 * 60 * 60 * 1000));

        if(GLOBAL_VAL.start_date == "" && GLOBAL_VAL.end_date == ""){
        	$('#energy_home_datepicker_start').val(sd.getDate()+"/0"+(sd.getMonth()+1)+"/"+sd.getFullYear());
        	$('#energy_home_datepicker_end').val(d.getDate()+"/0"+(d.getMonth()+1)+"/"+d.getFullYear());
        }else{
        	$('#energy_home_datepicker_start').val(GLOBAL_VAL.start_date);
        	$('#energy_home_datepicker_end').val(GLOBAL_VAL.end_date);

        }

        $('#energy_home_datepicker').datepicker({
        	format: 'dd/mm/yyyy',
        	keyboardNavigation : false,
        	forceParse : false,
        	autoclose : true,
        	endDate: "0d",
        }).on('changeDate',function(e){
        	GLOBAL_VAL.start_date = $('#energy_home_datepicker_start').val();
        	GLOBAL_VAL.end_date = $('#energy_home_datepicker_end').val();
        	updateData(GLOBAL_VAL.groupid);
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
        	initMaintenanceDetailTable("");
            initCADetailTable(null);
        	return;
        }
        /*
         * else {
         * 	IDU AND ODU will populated by clicking display button triggered after jstree ready function
         * }
         */

        initOperationLogDetail();
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
                
                data[i]["idu_Name"] = '<a class="IDUModelNumberLink" data-unitid='+ data[i].iduId+ ' data-deviceModel='+ data[i].deviceModel+' href="javascript:void(0);">'+ data[i].idu_Name +'</a>';
                	
                data[i]["viewinmap"] = getTDHtml('<a class="IDUViewMapLink" data-unitid='+ data[i].iduId+' data-svgId='+ data[i].svgId+' href="javascript:void(0);">View</a>',"acDetailViewMap") + "</tr>";
                
            }
        }
        
        GLOBAL_VAL.IDUDetailTable = $('#IDUDetailTable').DataTable({
            //dom: 'frtip',
            
            
            "ordering": false,
            "paging":   false,
            "filter": true,
            "info" : false,
            "responsive":   true,
            "deferRender":    true,
            
            "scroller": true,
            "scrollY":  GLOBAL_VAL.scrollY,
            "scrollCollapse": false,
            "stateSave": true,
            //autoWidth: false,
            "createdRow": function ( row, data, index ) {
                $(row).attr( "data-rcFlag",  data.rc_flag);
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
				{ "data": "alarmCodeHtml"
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
                },
                
				{ "data": "viewinmap"
                ,"className": "acODUDetailViewMap"
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
                
                data[i]["alarmCodeHtml"] = getAlarmCodeHtml(data[i].alarmCode);
                
                data[i]["odu_Name"] = '<a class="ODUModelNumberLink" data-unitid='+ data[i].oduId+ ' data-deviceModel='+ data[i].deviceModel+' href="javascript:void(0);">'+ data[i].odu_Name +'</a>';
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
            "stateSave": true,
            //autoWidth: false,
            "createdRow": function ( row, data, index ) {
                $(row).attr( "id",  "oduTr" + data.oduId);   
            },
            "columnDefs": [         
                {
                orderable: false,
               
                targets:  [0,6,7,8],
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
				{ "data": "alarmCodeHtml"
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
                },
				{ "data": "viewinmap"
                ,"className": "acODUDetailViewMap"
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
    	if (GLOBAL_VAL.MainDetailTable != null) {
        	GLOBAL_VAL.MainDetailTable.destroy();
		}
        $("#acMainDetailTbody").html(detailHtml);

        GLOBAL_VAL.MainDetailTable = $('#MainDetailTable').DataTable({
            "ordering": true,
            "paging":   false,
            "filter": true,
            "info" : false,
            "responsive":   true,
            "deferRender":    true,
            "scrollCollapse": false,
            "scroller": true,
            "stateSave": true,
            "scrollY":  "521px",
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
        /*
        if (GLOBAL_VAL.operLogTable != null) {
        	GLOBAL_VAL.operLogTable.destroy();
		}
        */
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
        console.log(JSON.stringify(GLOBAL_VAL.caData));
        if( GLOBAL_VAL.caData != null){
        //console.log(JSON.stringify(detailHtml));

        }

        window.glo.CADetailTable = $('#CADetailTable').DataTable({
            dom: 'frtip',
            "data": detailHtml,
            "stateSave": true,
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
            "createdRow": function ( row, data, index ) {
                $(row).attr( "id",  "caTr" + data.id);   
            },
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
        $.axs("../acconfig/getACDetails.htm", {
            "json_request": JSON.stringify({"id":groupIds,"idType":"group"})
        }, function(data) {console.log(data);
            var acDetailTableHtml1 = "";
            var groupOptionHtml = "";
            _clearSvgIDURelationship();

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

        		setUnitIconsInMap("svg/"+displaySvgName);

        		var groupNameArray = [];
            	for (var i = 0; i < iduList.length; i++) {
                    var unitInfo = iduList[i];

                    _saveSvgIdIDUListRelationship(unitInfo.svgId,unitInfo.iduId);

                    var IDUSvgName = unitInfo.svg_Location;
                    if (iduList[0].svg_Location == "") {
                    	IDUSvgName = "panasonic_demo.svg";
					}

                    if (displaySvgName == IDUSvgName) {
                    	addImageLayer(unitInfo, "IDU");
					}

                    if (groupNameArray.indexOf(IDUSvgName) == -1) {
                    	groupNameArray.push(IDUSvgName);
                    	groupOptionHtml += _createOptionForGroupDropDown(unitInfo.svgId,unitInfo.svgDisplayName);
					}

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

            $("#groupSelects").html(groupOptionHtml);

            $("#IDUDetailTable_wrapper .dataTables_scrollBody").mCustomScrollbar({
                scrollButtons: {
                    enable: true
                },
                theme: "dark-2"
            });
        },"post");
    }

    function _refreshMapAndCADetailByGroupId(groupIds) {



        $.axs("../acconfig/getCADetails2.htm", {
            "cache": false,
            "json_request": JSON.stringify({"id":groupIds,"idType":"group"})
        }, function(data) {console.log(data);
            for(var i = 0; i < data.length; i++){

	        	for (var k in data[i]) {

	        		if(data[i][k] == null){
	        			data[i][k] = "-";
	        		}
	    	    }
                data[i]["checkbox"] = '<input id="ckCA' + data[i]["id"] +  '" class="caDetailCheckbox bizCheckbox" type="checkbox" data-unitid="'
                + data[i]["id"] + '"><label for="ckCA' + data[i]["id"] + '" class="labelck" id="lck"></label>';

                //data[i]["checkbox"] = '<input id="ck2" class="caDetailCheckbox bizCheckbox" type="checkbox" data-unitid="2"><label for="ck2" id= "lck"></label>';
                data[i]["address"] = '<a class="ACModelNumberLink" data-unitid='+ data[i].id+ ' data-deviceModel='+ data[i].deviceModel+' href="javascript:void(0);">'+ data[i].address +'</a>';

            }
            GLOBAL_VAL.caData = data;
            //var checkedIdList = getCheckedGroupIds();
            var checkedIdList = [];
            
            initCADetailTable(data);

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
        var proValueMap = {"prohibited":"&#149","notProhibited":"-","-":"-"};
//        var proClassNameMap = {"prohibited":"lineHeight27","notProhibited":"","-":"-"};

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
					getTDHtml(proValueMap[unitVO.prohibitRCPower], "acDetailPro1 ") +
					getTDHtml(proValueMap[unitVO.prohibitRCTemp], "acDetailPro2 ") +
					getTDHtml(proValueMap[unitVO.prohibitRCMode], "acDetailPro3 ") +
					getTDHtml(proValueMap[unitVO.prohibitRCFanSpeed], "acDetailPro4 ") +
					getTDHtml(proValueMap[unitVO.prohibitRCFlapMode], "acDetailPro5 ") +
					getTDHtml(proValueMap[unitVO.prohibitRCEnergySaving], "acDetailPro6 ") +
					getTDHtml(unitVO.type + "/" + unitVO.parentChild, "acDetailPC detailPCTd") + getTDHtml(unitVO.caStatus, "acDetailCAStatus");
//		}else {
//			trHtml +=
//					getTDHtml(temp, "acDetailTemp") + getTDHtml("-", "acDetailRoomTemp") + getTDHtml("-", "acDetailMode") +
//					getTDHtml("-", "acDetailFanSpeed") + getTDHtml("-", "acDetailWind") + getTDHtml("-", "acDetailESaving")+
//					getTDHtml(alarmCodeHtml, "acDetailAlarmCode") + getTDHtml("-", "acDetailPro1") + getTDHtml("-", "acDetailPro2") +
//					getTDHtml("-", "acDetailPro3") + getTDHtml("-", "acDetailPro4") + getTDHtml("-", "acDetailPro5") +
//					getTDHtml("-", "acDetailPro6") + getTDHtml(unitVO.type + "/" + unitVO.parentChild, "acDetailPC detailPCTd") +getTDHtml(unitVO.caStatus, "acDetailCAStatus");
//		}

        return trHtml += getTDHtml('<a class="IDUViewMapLink" data-unitid='+ unitVO.iduId+' data-svgId='+ unitVO.svgId+' href="javascript:void(0);">View</a>',"acDetailViewMap") + "</tr>";
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

        $.axs("../acconfig/getACDetailsODU.htm", {
            "json_request": JSON.stringify({"id":groupIds,"idType":"group"})
        }, function(data) {console.log(data);
            var acDetailTableHtml1 = "",
            	mainDetailTableHtml = "";
            _clearSvgODURelationship();
            if (data != null && typeof(data) != "undefined") {
            	for (var i = 0; i < data.length; i++) {
                    var unitInfo = data[i];
                    _saveSvgIdOduListRelationship(unitInfo.svgId,unitInfo.oduId);
                    //acDetailTableHtml1 += getODUDetailLineHtml(unitInfo);
                    mainDetailTableHtml += getMainDetailLineHtml(unitInfo);
				}

            initODUDetailTable(data);
            }

            
            //initODUDetailTable(acDetailTableHtml1);
            initMaintenanceDetailTable(mainDetailTableHtml);

            $("#ODUDetailTable_wrapper .dataTables_scrollBody").mCustomScrollbar({
                scrollButtons: {
                    enable: true
                },
                theme: "dark-2"
            });
            
            $("#MainDetailTable_wrapper .dataTables_scrollBody").mCustomScrollbar({
                scrollButtons: {
                    enable: true
                },
                theme: "dark-2"
            });
        },"post");

    }

    function getODUDetailLineHtml(unitVO) {
        var refUnitId = "ODU" + unitVO.oduId;
        var alarmCodeHtml = getAlarmCodeHtml(unitVO.alarmCode);
        var outdoorTemp = unitVO.outdoorTemp;
        if (outdoorTemp == 0) {
        	outdoorTemp = "-";
		}else {
			outdoorTemp = outdoorTemp.toFixed(1);
			outdoorTemp += "&#176C";
		}
        var html =  "<tr id=" + refUnitId + "tr refDomId=" + refUnitId + "><td class='acDetailHeaderCheckbox'><input id=ckOdu"+ unitVO.oduId +" class='acDetailCheckbox bizCheckbox' type='checkbox' data-unitid=" + unitVO.oduId +" /><label for=ckOdu"+ unitVO.oduId + "></label></td>" +
        			getTDHtml(unitVO.site, "acODUDetailSite")  + getTDHtml(unitVO.slinkAddress, "acODUDetailSlink") + getTDHtml(unitVO.odu_Name, "acODUDetailName") +
        			getTDHtml(alarmCodeHtml, "acODUDetailAlarmCode") + getTDHtml(outdoorTemp, "acODUDetailOutTemp") +
//        			getTDHtml('-', "acODUDetailOilCheck") + getTDHtml('-', "acODUDetailEngineServ") + getTDHtml('-', "acODUDetailPowerGen") +
        			getTDHtml(unitVO.maintenanceCountDownComp1, "acODUDetailComp1" + _getRedFontBasedOnComp(unitVO.maintenanceCountDownComp1)) +
        			getTDHtml(unitVO.maintenanceCountDownComp2, "acODUDetailComp2" + _getRedFontBasedOnComp(unitVO.maintenanceCountDownComp2)) +
        			getTDHtml(unitVO.maintenanceCountDownComp3, "acODUDetailComp3" + _getRedFontBasedOnComp(unitVO.maintenanceCountDownComp3)) +
        			getTDHtml(unitVO.type+"/" + unitVO.parentChild, "acODUDetailType detailPCTd") +
        			getTDHtml('<a class="ODUViewMapLink" data-unitid='+ unitVO.oduId+' data-svgId='+ unitVO.svgId+' href="javascript:void(0);">View</a>',"acODUDetailViewMap") + "</tr>";
        return html;
    }

    function getMainDetailLineHtml(unitVO) {
        var refUnitId = "main" + unitVO.oduId;
        var alarmCodeHtml = getAlarmCodeHtml(unitVO.alarmCode);
        var html =  "<tr id=" + refUnitId + "tr refDomId=" + refUnitId + "><td class='acDetailHeaderCheckbox'><input id=ckMain"+ unitVO.oduId +" class='acDetailCheckbox bizCheckbox' type='checkbox' data-unitid=" + unitVO.oduId +" /><label for=ckMain"+ unitVO.oduId + "></label></td>" +
        			getTDHtml(unitVO.site, "acODUDetailSite")  + getTDHtml(unitVO.slinkAddress, "acODUDetailSlink") + getTDHtml(unitVO.odu_Name, "acODUDetailName") +
        			getTDHtml(alarmCodeHtml, "acODUDetailAlarmCode") + getTDHtml(unitVO.outdoorTemp+"&#176C", "acODUDetailOutTemp") +
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
     * start*****************************
     */
    // TR
    function updateAcContorl(IDUArray) {
        $.axs("../rc/RCValidate.htm", {
//            "id": JSON.stringify({"groupIds":IDUIds}),
//            "idType": "indoorUnit"
        	"id":IDUArray[0].toString()
//        	"id":"301"
        }, function(data) {
        	if (typeof data != "undefined" && data.validStatus == true) {
            	if ($("#acControlDialog").length > 0) {
            		updateUIByRCValidation(data,IDUArray);
    			}else {
    				GLOBAL_VAL.$remoteControlDialog = $.jsPanel({
                		id:"acControlDialog",
                		paneltype: {
            		        type: 'modal'
            		    },
                	    title:    "AC Remote Control",
                	    selector: '#wrapper',
                	    size:     { width: "auto", height: "auto" },
                	    position: "center",
                	    bootstrap:"primary",
                	    resizable: "disabled",
                	    content:$("#acControlFileDiv").html(),
                	    callback: function () {
                	    	updateUIByRCValidation(data,IDUArray)
                	    },
                	    draggable: {
    	                    containment: "parent"
    	                }
                	});
    			}
			}else {
				if ($("#acControlDialog").length > 0) {
					GLOBAL_VAL.$remoteControlDialog.close();
    			}
				$.bizalert("The adapters are not ready. Please try again after awhile.");
			}
        	
        });
    }
    
    function updateUIByRCValidation(data,IDUArray){
        $("#acControlDialog").data("cacheRCData",data);
        $("#acControlDialog").data("cacheRCCheckedIds",IDUArray);

        var SUPPORT = 1;
        if (data != null && typeof(data) != "undefined" && typeof(data.errorMessage) == "undefined") {
            console.log(data);
            var $controlDialog = $("#acControlDialog");
            if (data.powerStatus_support != SUPPORT) {
            	$controlDialog.find("#powerControlPanel >div > div").addClass("disabled");
			}
            if (data.temperature_support != SUPPORT) {
            	$controlDialog.find(".tempButton").addClass("disabled");
			}

            if (data.mode_support != SUPPORT) {
            	$controlDialog.find("#modeControl > div").addClass("disabled");
			}

            if (data.fanSpeed_support != SUPPORT) {
            	$controlDialog.find("#fanSpeedControl > div").addClass("disabled");
			}

            if (data.windDirection_support != SUPPORT) {
            	$controlDialog.find("#flapControl > div").addClass("disabled");
			}

            if (data.energySaving_support != SUPPORT) {
            	$controlDialog.find("#energySavingControl > div").addClass("disabled");
			}

            //mode support list
            $controlDialog.find("#modeControlPanel .controlIcon").addClass("disabled");
            if (data.mode_support == SUPPORT && data.mode_support_list != null && data.mode_support_list.length > 0) {
            	var list = data.mode_support_list;
            	for (var i = 0; i < list.length; i++) {
					var modeStr = list[i].toLowerCase();
					$controlDialog.find("." + modeStr + "Mode .controlIcon").removeClass("disabled");
				}
			}

            //fan speed support list
            $controlDialog.find("#fanSpeedControlPanel .controlIcon").addClass("disabled");
            if (data.fan_speed_list != null && data.fan_speed_list.length > 0) {
            	var list = data.fan_speed_list;
            	for (var i = 0; i < list.length; i++) {
					var fanSpeedStr = list[i].toLowerCase();
					$controlDialog.find("." + fanSpeedStr +"FanSpeed .controlIcon").removeClass("disabled");
				}
			}

            //flap support list
            $controlDialog.find("#flapControlPanel .controlIcon").addClass("disabled");
            if (data.windDirection_list != null && data.windDirection_list.length > 0) {
            	//get current IDU mode, if any one of them is cool, dry or auto cool, f4 and f5 should be disabled.
            	var f4f5Support = true;
            	$("#acDetailTbody input:checked").each(function( index ) {
            		var currentMode = $(this).parent().parent().find("td.acDetailMode").text().toLowerCase();
            		if (currentMode == "cool" || currentMode == "dry" || currentMode == "autocool" ) {
            			f4f5Support = false;
            			return false;
        			}
            	});
            	
            	var list = data.windDirection_list;
            	for (var i = 0; i < list.length; i++) {
					var flapStr = list[i].toLowerCase();
					if (flapStr == "f4" || flapStr == "f5") {
						if (f4f5Support) {
							$controlDialog.find("." + flapStr +"Flap .controlIcon").removeClass("disabled");
						}
					}else {
						$controlDialog.find("." + flapStr +"Flap .controlIcon").removeClass("disabled");
					}
				}
			}

            if (data.prohibition_MASTER != SUPPORT) {
            	$controlDialog.find("#prohibitionPanel input").prop("disabled","disabled");
			}
        }
    }

    function disableMode(mode){
    	$("#acControlDialog ."+ mode +"Mode .controlIcon").addClass("disabled");
    }

    function disableFanSpeed(speed){
    	$("#acControlDialog ."+ speed +"FanSpeed .controlIcon").addClass("disabled");
    }

    function disableFlap(flap){
    	$("#acControlDialog ."+ flap +"Flap .controlIcon").addClass("disabled");
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

    /**
     * ******************* Monitor Control part logic function
     * end*****************************
     */
    
    /**
     * ********************* Common logic function start
     * ******************************
     */
    function welcomeLoginUser() {
        setTimeout(function() {
            toastr.options = {
                closeButton: true,
                progressBar: true,
                showMethod: 'slideDown',
                timeOut: 2000
            };
            toastr.success('David Williams', 'Welcome to Panasonic Smart Cloud!');
        }, 1000);
    }

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

        	map.on('zoomstart', function() {
        		var highLightDom = $("#map .svgHighlight");
        		if (highLightDom.length == 0) {
        			highLightDom = null;
				}
        		GLOBAL_unitHightLightDom = highLightDom;
        	    $("#map .svgHighlight").removeClass("svgHighlight");
        	});

        	map.on('zoomend', function() {
        		if (GLOBAL_unitHightLightDom != null) {
        			console.log(GLOBAL_unitHightLightDom);
        			var IDUDom = $(GLOBAL_unitHightLightDom[0]);
        			var offset = GLOBAL_VAL.floorMap.getZoom() - 18;
        			IDUDom.css("height", (IDUDom.height() + offset) + "px").css("width", (IDUDom.width() + offset) + "px").addClass("svgHighlight");
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
                if (data.power == "OFF") {
                    image = "IDUOff"
                }
            }
        }else {
        	image = "ODUVRF";
        }

        if (data.alarmCode != null && data.alarmCode != "") {
        	if (data.alarmType == "non-critical") {
        		image += "Alarm";
			}else if (data.alarmType == "non-critical"){
				image += "Critical";
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
