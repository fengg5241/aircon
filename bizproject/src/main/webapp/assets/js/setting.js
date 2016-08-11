$(document).ready(function() {
	var GLOBAL_VAL = {
		start_date : "",
		end_date : "",
		areaDistributionGroupTable:null,
		cutOffRequestTable:null,
		scrollY:"513px",
		oldIduAreaMap:{},
		newIduAreaMap:{},
		checkedSiteIdListForCutOff:[]
	};
	menu('settings');
	
	bindSystemSettingEvent();
	initSystemSetting();
	var sitelist = [];
	function bindSystemSettingEvent(){
		bindTabEvent();
		
		$(document).on("click", "#displayButton", function(e) {
	    	var idList = getCheckedGroupIds();
	    	if(idList.length == 0){
	    		$.bizalert("Please make a selection in AC configuration panel and try submitting again.");
	    	}else {
	    		$('#checkboxMenuTree').jstree().save_state();
	    		
	        	populateGroupSelects(); 
	        	//reset distribution group select
	        	$("#distributionGrouplist").empty();
	    	}
	    	return false;
	    });
		
		bindAreaAllocationEvent();
		bindCutOffEvent();
	}
	
    function bindTabEvent(){
        $(document).on("click", ".rightViewFrame a[data-toggle='tab']", function() {
            var target = $(this).attr("href");
            if (target == "#acMapTab" && GLOBAL_VAL.floorMap != null) {
            	GLOBAL_VAL.floorMap.invalidateSize();
			} else {
				$(target + " .dataTables_scrollBody").mCustomScrollbar("update");
				$(target + " .dataTables_scroll").resize();
			}
        });
    }
    
    function bindAreaAllocationEvent(){
		//for cascading to refresh distribution group
		$("#areaSiteSelect").change(function(){
			if ($(this).val() != 0) {
		        $.axs("../cr/getDistributionGroupsBySiteId.htm", {
		            "siteID": $(this).val(),
		        }, function(data) {
		        	console.log(data);
		        	populateDistributionGroupSelect(data);
		        });
			}
		});
		
		//searach button
		$("#applySearchButton").click(function(){
			if ($("#areaSiteSelect").val() == 0 || $("#distributionGrouplist").val() == null) {
				$.bizalert("Please select site or distribution group and try submitting again.");
				return false;
			}
			
			getAllAreaAllocation();
		});
		
		function getAllAreaAllocation(){
			GLOBAL_VAL.oldIduAreaMap = {};
	        $.axs("../cr/aa/getAllocatedAreas.htm", {
	            "siteId": $("#areaSiteSelect").val(),
	            "distributionId":$("#distributionGrouplist").val()
	        }, function(data) {
	        	console.log(data);
				//call api to get areaList;
	        	var areaSelectHtml = '<select><option value=0>Please Select</option>';
	        	if (typeof(data) != "undefined") {
					for (var i = 0; i < data.availableAreas.length; i++) {
						var area =data.availableAreas[i];
						areaSelectHtml += "<option value="+area.areaId +">" + area.areaName +"</option>";
					}
				}
				
	        	//refresh areaSelect for remove area
	        	$("#areaListSelect").html(areaSelectHtml);
	        	
				var distributionGroupDetails = data.distributionGroupDetails;
				var areaAllocationHtml = "";
	            if (distributionGroupDetails != null && typeof(distributionGroupDetails) != "undefined") {
	            	for (var i = 0; i < distributionGroupDetails.length; i++) {
						var areaAllocation = distributionGroupDetails[i];
						areaAllocationHtml += getAreaAllocationLineHtml(areaAllocation,areaSelectHtml);
					}
	            }
	            
	            initAreaAllocationTable(areaAllocationHtml);
	            
	            if (distributionGroupDetails != null && typeof(distributionGroupDetails) != "undefined") {
	            	for (var i = 0; i < distributionGroupDetails.length; i++) {
	            		var areaAllocation = distributionGroupDetails[i];
						if (areaAllocation.areaID != null) {
							GLOBAL_VAL.oldIduAreaMap[areaAllocation.iduId] = areaAllocation.areaID
							var $tr = $("#area" + areaAllocation.iduId + "tr");
							$tr.find("select").val(areaAllocation.areaID);
						}
					}
	            }
	            
	            $("#areaDistributionGroupTable_wrapper .dataTables_scrollBody").mCustomScrollbar({
	                scrollButtons: {
	                    enable: true
	                },
	                theme: "dark-2"
	            });
	        });
		}
		//area list select in table
		$(document).on("change", "#areaDistributionGroupTableTbody select", function() {
			var iduId = $(this).parent().attr("data-iduid");
			GLOBAL_VAL.newIduAreaMap[iduId] = $(this).val();
		});
		
		//area operation select
		$("#areaOperationSelect").change(function(){
			if ($(this).val() == "") {
				$(".createAreaOperationForm").hide();
				$(".removeAreaOperationForm").hide();
			}else if($(this).val() == "Create Area"){
				$(".createAreaOperationForm").show();
				$(".removeAreaOperationForm").hide();
			}else if($(this).val() == "Remove Area"){
				$(".createAreaOperationForm").hide();
				$(".removeAreaOperationForm").show();
			}
		});
		
		//apply update
		$("#areaApplyButton").click(function(){
			if ($("#areaSiteSelect").val() == 0 || $("#distributionGrouplist").val() == null) {
				$.bizalert("Please select site or distribution group and try submitting again.");
				return false;
			}
			
//			var operation = $("#areaOperationSelect").val();
//			if (operation == "Create Area" && $("#newAreaNameInput").val() == "") {
//				$.bizalert("Please input new area name!");
//				return false;
//			}else if (operation == "Remove Area" && ($("#areaListSelect").val() == null || $("#areaListSelect").val() == 0)) {
//				$.bizalert("Please selecte area to be deleted!");
//				return false;
//			}
			
			var paramMap = {};
			var paramCount = 0;
			for ( var iduId in GLOBAL_VAL.newIduAreaMap) {
				var newArea = GLOBAL_VAL.newIduAreaMap[iduId];
				var oldArea = GLOBAL_VAL.oldIduAreaMap[iduId];
				if (newArea != oldArea) {
					paramMap[iduId] = newArea == "0" ? null :newArea;
					paramCount += 1;
				}
			}
			
			if (paramCount > 0) {
				//1 update area allocation
				updateAreaAllocation(paramMap);
			}
			
			//send request
			//if succefully clear map
			GLOBAL_VAL.newIduAreaMap = {};
		});
		
		$("#areaAddButton").click(function(){
			if ($("#areaSiteSelect").val() == 0 || $("#distributionGrouplist").val() == null) {
				$.bizalert("Please select site or distribution group and try submitting again.");
				return false;
			}
			
			var operation = $("#areaOperationSelect").val();
			if ($("#newAreaNameInput").val() == "") {
				$.bizalert("Please input new area name!");
				return false;
			}
			
			createArea();
		});
		
		$("#areaRemoveButton").click(function(){
			if ($("#areaSiteSelect").val() == 0 || $("#distributionGrouplist").val() == null) {
				$.bizalert("Please select site or distribution group and try submitting again.");
				return false;
			}
			
			var operation = $("#areaOperationSelect").val();
			if (operation == "Remove Area" && ($("#areaListSelect").val() == null || $("#areaListSelect").val() == 0)) {
				$.bizalert("Please select area to be deleted!");
				return false;
			}
			
			$.axs("../cr/aa/isAreaAssigned.htm", {
	            "areaId": $("#areaListSelect").val()
	        }, function(data) {
	        	if (data.success) {
	        		$.bizalert({
	        			"title":"This area is already assinged to IDU",
	        			"message":"Would you like to Continue to remove this area?",
	        			"showConfirmButton":true,
	        			"confirmCallBack":removeArea
	        		});
				}else {
					removeArea();
				}
	        	
	        });
			
			
		});
		
		function updateAreaAllocation(paramMap){
			var mapList = [];
			for ( var iduId in paramMap) {
				var tempMap = {};
				tempMap.iduId = iduId;
				tempMap.areaId = paramMap[iduId];
				mapList.push(tempMap);
			}
			console.log(mapList);
			
			$.axs("../cr/aa/updateIDUAreaMapping.htm", {
			    "areaMappingList": JSON.stringify(mapList),
			}, function(data) {
				getAllAreaAllocation();
			},"post");
		}
		
		function createArea(){
			$.axs("../cr/aa/createArea.htm", {
	            "siteId": $("#areaSiteSelect").val(),
	            "distributionId":$("#distributionGrouplist").val(),
	            "areaName":$("#newAreaNameInput").val()
	        }, function(data) {
	        	if (typeof(data.errorMessage) != "undefinded" && data.errorMessage == "area.exists.in.dg") {
					$.bizalert("AreaName should be unique under selected distribution group");
				}
	        	getAllAreaAllocation();
	        },"post");
		}
		
		function removeArea(){
			
			$.axs("../cr/aa/removeArea.htm", {
	            "siteId": $("#areaSiteSelect").val(),
	            "distributionId":$("#distributionGrouplist").val(),
	            "areaName":$("#areaListSelect option:selected").text()
	        }, function(data) {
	        	if (typeof(data.errorMessage) != "undefinded" && data.errorMessage == "area.associated.with.idus.cannot.delete") {
					$.bizalert("This area is already assinged to IDU, cannot remove!");
				}
	        	getAllAreaAllocation();
	        },"post");
		}
		
		function populateDistributionGroupSelect(data){
			var option = "";
			if (data != null && data.length > 0) {
				for (var i = 0; i < data.length; i++) {
					var disGroup = data[i];
					option += "<option value="+disGroup.id + ">" + disGroup.groupName + "</option>";
				}
			}
			$("#distributionGrouplist").html(option);
		}
		
		function getAreaAllocationLineHtml(VO,selectHtml){
	        var html =  "<tr id=area" + VO.iduId + "tr>"+ 
	        			getTDHtml(VO.iduAddress, "areaIduAddress")  + getTDHtml(VO.category, "areaCategory") + getTDHtml(VO.deviceName, "areaDeviceName") + 
	        			getTDHtml(VO.distribution, "areaDistributionGroup") +
	        			"<td class='areaTdSelect' data-iduid="+ VO.iduId +">" + selectHtml + "</td>";
	        return html;
		}
    }
    
    function bindCutOffEvent(){
        setInterval(function(){    	    	
        	getAllCutOffRequest();
        }, 1000*60*3); 
        
		$("#cutOffRequestButton").click(function(){
			var siteIdList = GLOBAL_VAL.checkedSiteIdListForCutOff;
			if (siteIdList.length == 0) {
				$.bizalert("Please select group then click apply setting button!");
			}else {
				registerCutoffRequest(siteIdList);
			}
		});
		
		function registerCutoffRequest(siteIdList){
			var startDate = $("#cutOffStartDate").val();
			var endDate = $("#cutOffEndDate").val();

			$.axs("../cr/registerCutoffRequest.htm", {
	            "fromdate": startDate,
	            "todate":endDate,
	            "siteidList":siteIdList.toString()
	        }, function(data) {
	        	getAllCutOffRequest();
	        	
	        },"post");
		}
    }
	
	function initSystemSetting(){
		initSystem();
		initSystemConfig();
		initAreaAllocation();
		initCutOffRequest();
		
	}
	
	function initSystemConfig(){
//		$.fn.datepicker.defaults.format = "dd-mm-yyyy";
		$('#custRegistrationTab .input-group.date').datepicker({
	        todayBtn: "linked",
	        keyboardNavigation: false,
	        forceParse: false,
	        calendarWeeks: true,
	        autoclose: true,
	        endDate: "0d"
	    });
	}
	
	function initSystem(){
		/*$.fn.datepicker.defaults.format = "dd/mm/yyyy";
		var today = new Date();
	    var dd = today.getDate();
	    var mm = today.getMonth()+1; 
	    var yyyy = today.getFullYear()
	    today = dd+'/'+mm+'/'+yyyy;
	    console.log("....."+today);
		$('.input-daterange').datepicker({
			todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            autoclose: true,
            endDate: "0d",
            startDate: "-3y"
        })
        $("#starts").val($.datepicker.formatDate("dd/mm/yyyy", today));*/
//		$.fn.datepicker.defaults.format = "yyyy-mm-dd";
		
		// Modified by ravi
		var end_date = new Date();
		var month = end_date.getMonth()+1;
		var day = end_date.getDate();
		if(month < 10){
			month = "0"+month;
		}
		if(day < 10){
			day = "0"+day
		}
	    var end_date_string = day+"/"+month+"/"+end_date.getFullYear();
	    $('#systemconfigTab #startdate').val(end_date_string);       
        
        $('#systemconfigTab .input-daterange').datepicker({
            keyboardNavigation: false,
            forceParse: false,
            autoclose: true,
            endDate: "+2y",
            startDate: "0d",
            format: 'dd/mm/yyyy',
            
        }).on('click',function(e){
        	$("#showdisplay").hide();
        });
		
      //loading plugin onload (added by ravi)
       $('#island').show();
       $('#island').SumoSelect({ triggerChangeCombined: false, placeholder: "Please Select", selectAll: true});
	}
	
	
	function initAreaAllocation(){
		//clear last selection
		$("#areaOperationSelect").val("");
		$("#newAreaNameInput").val("");
		
		initAreaAllocationTable("");
	}
	
	function initCutOffRequest(){
		getAllCutOffRequest();
		
		var today = new Date();
        var end_date = new Date(today.getFullYear(), today.getMonth(), today.getDate() - 1);
        
        var start_date = new Date(end_date.getFullYear(), end_date.getMonth(), end_date.getDate() - 7);
        
        var month = end_date.getMonth()+1;
		var day = end_date.getDate();
		if(month < 10){
			month = "0"+month;
		}
		if(day < 10){
			day = "0"+day
		}
		
		var month1 = start_date.getMonth()+1;
		var day1 = start_date.getDate();
		if(month1 < 10){
			month1 = "0"+month1;
		}
		if(day1 < 10){
			day1 = "0"+day1;
		}
        
	    var end_date_string = day+"/"+month+"/"+end_date.getFullYear();
	    
	    var start_date_string = day1+"/"+month1+"/"+start_date.getFullYear();
	    
	    $('#cutOffStartDate').val(start_date_string);
        $('#cutOffEndDate').val(end_date_string);
        
        $('#cutOffRequestTab .input-daterange').datepicker({
            keyboardNavigation: false,
            forceParse: false,
            autoclose: true,
            endDate: "-1d",
            startDate: "-3y",
            format: 'dd/mm/yyyy'
        });
        
//		$('#datepicker').datepicker({
//	        todayBtn: "linked",
//	        keyboardNavigation: false,
//	        forceParse: false,
//	        calendarWeeks: true,
//	        autoclose: true,
//	        endDate: "0d"
//	    });
		
	}
	
	function getAllCutOffRequest(){
		$.axs("../cr/getAllCutoffRequests.htm", {
            "siteidList": "8"
        }, function(data) {
			var cutOffHtml = "";
            if (data != null && typeof(data) != "undefined" && typeof(data.resultList) != "undefined") {
            	for (var i = 0; i < data.resultList.length; i++) {
					var cutOff = data.resultList[i];
					cutOffHtml += getCutOffLineHtml(cutOff,cutOffHtml);
				}
            }
            
			initCutOffTable(cutOffHtml);
			
			initCutOffDownload();
			
			$("#cutOffTable_wrapper .dataTables_scrollBody").mCustomScrollbar({
                scrollButtons: {
                    enable: true
                },
                theme: "dark-2"
            });
			
        },"post");
	}
	
	function getCutOffLineHtml(VO,selectHtml){
		var siteHtml = "";
		for (var i = 0; i < VO.sites.length; i++) {
			siteHtml +=  VO.sites[i] + "<br />";
		}
		
		var powerRatioReportHtml = 'N.A.',
		powerDetailReportHtml = 'N.A.';

		if (VO.result == "Incomplete" || VO.result == "Complete") {
			powerRatioReportHtml = '<a class="powerRatioReportLink" data-tid='+ VO.transactionId +' data-startdate='+ VO.startingDate +' href="javascript:void(0);">Download&nbsp;<span class="fa fa-caret-right"></span></a>';
			powerDetailReportHtml = '<a class="powerDetailReportLink" data-tid='+ VO.transactionId +' data-startdate='+ VO.startingDate +' href="javascript:void(0);">Download&nbsp;<span class="fa fa-caret-right"></span></a>';
		} else if (VO.result == "In Progress"){
			powerRatioReportHtml = '';
			powerDetailReportHtml = '';
		}
		
        var html =  "<tr id=cutOff" + VO.transactionId + "tr>"+ getTDHtml(VO.transactionId, "cutOffTransaction") + 
        			getTDHtml(siteHtml, "cutOffSite") + getTDHtml(VO.result, "cutOffResult") + getTDHtml(VO.transactionTime, "cutOffTransactionTime") + 
        			getTDHtml(VO.startingDate, "cutOffStartingDate") + getTDHtml(VO.completionDate, "cutOffCompletionDate") + 
        			getTDHtml(powerRatioReportHtml,"cutOffRatioDownLoad") + getTDHtml(powerDetailReportHtml,"cutOffDetailDownLoad");
        return html;
	}
	
	function initAreaAllocationTable(detailHtml){
        if (GLOBAL_VAL.areaDistributionGroupTable != null) {
        	GLOBAL_VAL.areaDistributionGroupTable.destroy();
		}
        $("#areaDistributionGroupTableTbody").html(detailHtml);
        
        GLOBAL_VAL.areaDistributionGroupTable = $('#areaDistributionGroupTable').DataTable({
            "ordering": true,
            "paging":   false,
            "filter": true,
            "info" : false,
            "responsive":   true,
            "deferRender":    true,
            "scrollCollapse": false,
            "scroller": true,
            "scrollY":  "398px",
//            "columnDefs": [{ orderable: false, targets: 0 }],
//            "order": [[ 1, "asc" ]],
            "language": {
			          "searchPlaceholder": "Search"
			 },
			 "search": {
	   			    "caseInsensitive": false
  			  }
        });
    }
	
	function initCutOffTable(detailHtml){
        if (GLOBAL_VAL.cutOffRequestTable != null) {
        	GLOBAL_VAL.cutOffRequestTable.destroy();
		}
        $("#cutOffTableTbody").html(detailHtml);
        
        GLOBAL_VAL.cutOffRequestTable = $('#cutOffTable').DataTable({
            "ordering": true,
            "paging":   false,
            "filter": true,
            "info" : false,
            "responsive":   true,
            "deferRender":    true,
            "scrollCollapse": false,
            "scroller": true,
            "scrollY":  GLOBAL_VAL.scrollY,
            "order": [[ 3, "desc" ]],
            "language": {
			          "searchPlaceholder": "Search"
			 },
			 "search": {
	   			    "caseInsensitive": false
  			  }
        });
    }
	
    function getTDHtml(string, className) {
        return "<td class='" + className + "'>" + string + "</td>";
    }
	/*$('#systemselectsite').multiselect({
		enableClickableOptions: true,
		includeSelectAllOption: true,
		checkboxName: 'multiselect[]',
		buttonContainer: '<div class="btn-group" />'
	});*/

	
	
	/*$(document).on("click", "#buttondisplay", function(e) {
    	
		$("#showdisplay").show();
		
    });*/
	
	$('#island').on('click', function() {
		console.log(sitelist.length);
				
		if(sitelist.length == 0){
			$.bizinfo("You do not have the access to this see this Information. Please contact your customer administrator for access");
		}			
	});
	
	function initCutOffDownload(){
	
		$('.powerRatioReportLink').on('click', function() {
			var url = '../cr/downloadPowerRatioReport.htm';
			var tid = this.getAttribute('data-tid');
			var start_date = this.getAttribute('data-startdate');
			
			$.ajax({
	            url: url,
	            beforeSend: function (request){
	            	getToken(request);
	            },
	            data: {
	                "transactionId": tid,
	                "AJAXREQUEST": true
	            },
	            success: function(data) {
		        	console.log(data);
			
		        	if(typeof data.errorMessage == 'undefined'){
			            var date = new Date();
			            
			            var sec = date.getSeconds();
			            
			            if(parseInt(sec) < 10){
			            	sec = "0"+sec;
			            }
			            
			            var month = date.getMonth() + 1;
			            
			            if(parseInt(month) < 10){
			            	month = "0"+month;
			            }
			            
			            var day = date.getDate()
			            
			            if(parseInt(day) < 10){
			            	day = "0"+day;
			            }
			            
			            var hours = date.getHours();
			            
			            if(parseInt(hours) < 10){
			            	hours = "0"+hours;
			            }
			            
			            var minutes =  date.getMinutes();
			            
			            if(parseInt(minutes) < 10){
			            	minutes = "0"+minutes;
			            }
			            
			            var timestamp = date.getFullYear()+month+day+hours+minutes+sec;
			            var fileName = 'Distr.Ratio_'+start_date;
			            fileName = fileName + "(" + timestamp + ").csv";
			            
                        // Download Logic
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
                        } 
		        	} else{
		        		$.bizalert("No Records Found!!!");
		        	}
	            }
			});
		});
		
		$('.powerDetailReportLink').on('click', function() {
			var url = '../cr/downloadPowerDetailReport.htm';
			var tid = this.getAttribute('data-tid');
			var start_date = this.getAttribute('data-startdate');
						
			$.ajax({
	            url: url,
	            beforeSend: function (request){
	            	getToken(request);
	            },
	            data: {
	                "transactionId": tid,
	                "AJAXREQUEST": true
	            },
	            success: function(data) {
	            	console.log(data);
		        	
	            	if(typeof data.errorMessage == 'undefined'){

			            var date = new Date();
			            
			            var sec = date.getSeconds();
			            
			            if(parseInt(sec) < 10){
			            	sec = "0"+sec;
			            }
			            
			            var month = date.getMonth() + 1;
			            
			            if(parseInt(month) < 10){
			            	month = "0"+month;
			            }
			            
			            var day = date.getDate()
			            
			            if(parseInt(day) < 10){
			            	day = "0"+day;
			            }
			            
			            var hours = date.getHours();
			            
			            if(parseInt(hours) < 10){
			            	hours = "0"+hours;
			            }
			            
			            var minutes =  date.getMinutes();
			            
			            if(parseInt(minutes) < 10){
			            	minutes = "0"+minutes;
			            }
			            
			            var fileName = 'Distr.Detail_'+start_date;
			            var timestamp = date.getFullYear()+month+day+hours+minutes+sec;
			            
			            fileName = fileName + "(" + timestamp + ").csv";
			            
                        // Download Logic
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
                        } 
		        	} else {
		        		$.bizalert("No Records Found!!!");
		        	}
	            }
			});
		});
		
	}
	
	function populateGroupSelects(){
    	var tree = $.jstree.reference('#checkboxMenuTree');
		var checkedList = tree.get_checked(true);
		var option = "";
		var opt= '';	
		var unique;
		option += '<div class="col-sm-12" style="padding: 9px;"><div class="col-sm-2"><input type="checkbox" class="allsitedrop"  name="all_site" value="1" style="padding: 41px;"></div><div class="col-sm-10" style="text-align:left;">All</div></div>';
		
		var siteIdList = [];
		var areaSiteOption = '<option value=0>Please select</option>';
		for (var i = 0; i < checkedList.length; i++) {
			var levelName = checkedList[i].original.groupTypeLevelName;
			if (levelName == "Building" || levelName == "Building") {
				/*optionsHtml += "<option value="+checkedList[i].original.groupId+">"+checkedList[i].original.groupName+"</option>";*/
				option += '<div class="col-sm-12" style="padding: 9px;"><div class="col-sm-2"><input type="checkbox" class="siteiddrop" id="site_id" name="site_id" value="'+checkedList[i].original.groupId+'" style="padding: 41px;"></div><div class="col-sm-10" style="text-align:left;">'+checkedList[i].original.groupName+'</div></div>'
				//populate for area site select
				var siteId = checkedList[i].original.groupId;
				areaSiteOption += "<option value="+siteId+">"+checkedList[i].original.groupName+"</option>";
				siteIdList.push(siteId);
				sitelist.push(checkedList[i].original.groupId);
				
				opt += "<option value="+checkedList[i].original.groupId+">"+checkedList[i].original.groupName+"</option>";	
				
			}
		}
		$(".put_all_sites").html('<div class="form-control text-center all_siteids" style="height: 250px;overflow-y: auto;"></div>');
		$('.all_siteids').html(option);
		$("#areaSiteSelect").html(areaSiteOption);		
		$("#island").html(opt);			
		$('#island').SumoSelect({ triggerChangeCombined: false, placeholder: "Please Select",selectAll: true  });
		$('#island')[0].sumo.reload();
		GLOBAL_VAL.checkedSiteIdListForCutOff = siteIdList;
		
		/*$("#systemselectsite").html(optionsHtml);*/
		/*$("#systemselectsite").multiselect('dataprovider', optionsHtml);*/
		
    }
	
	var last_valid_selection = null;
	var selected_siteids = [];
	$('#island').change(function (event) {
		for(var i=0;i<$(this).val().length;i++){
	    if ($(this).val().length > 100000) {
	        alert($(this).val());
	        var $this = $(this);
	        $this[0].sumo.unSelectAll();
	        $.each(last_valid_selection, function (i, e) {
	            $this[0].sumo.selectItem($this.find('option[value="' + e + '"]').index());
	        });
	    } else {
	        last_valid_selection = $(this).val()[i];
	        selected_siteids.push($(this).val()[i]);
	        console.log($(this).val()[i]);
	    }
	}
	});
	
	
	$(document).on('change', '.allsitedrop', function (){   

 	   if ($(this).is(':checked')) {
 		   $('.siteiddrop').each(function(){ this.checked = true; });
 	   }	
 	   else{
 		   $('.siteiddrop').each(function(){ this.checked = false; });
 	   }
    		
    	})
    $("#co2factor").keypress(function (e) {  
    	$(this).val($(this).val().replace(/[^0-9\.]/g,''));
        if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 48 || event.which > 57)) {
        	$.bizinfo("Enter valid CO2 factor value"); 
        	return false;
        }else if(e.which == 47){
        	$.bizinfo("Enter valid CO2 factor value");
     	   return false;
        }
      });	
    
	/*$('#co2factor').blur(function(){ 
		$(this).val($(this).val().replace(/[^0-9\.]/g,''));
        if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 46 || event.which > 57)) {
        	$.bizinfo("Enter valid CO2 factor value");alert("3")
        	return false;
        }else if(event.which == 47){
        	$.bizinfo("Enter valid CO2 factor value");alert("4")
     	   return false;
        }
		
	})*/
	
	$(document).on("click", "#co2factor", function(e) { 
		$("#showdisplay").hide();
	});
	
	$("#co2factor").on('input', function (event) { 
		$(this).val($(this).val().replace(/[^0-9\.]/g,''));
        if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 48 || event.which > 57)) {
        	$.bizinfo("Enter valid CO2 factor value");
        	return false;
        }else if(event.which == 47){
        	$.bizinfo("Enter valid CO2 factor value");
     	   return false;
        }
    });
	/*$("#co2factor").on("keypress keyup blur",function (event) {
        //this.value = this.value.replace(/[^0-9\.]/g,'');
		$(this).val($(this).val().replace(/[^0-9\.]/g,''));
        if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 46 || event.which > 57)) {
        	$.bizalert("Enter valid CO2 factor value");
        	return false;
        }else if(e.which == 47){
        	$.bizalert("Enter valid CO2 factor value");
     	   return false;
        }
    });*/
	
	$("#co2factor").val(0);
	$(document).on("click", "#applyco2value", function(e) {
		$("#showdisplay").hide();
		var date=$('#startdate').val();  
		//var selected_siteids = [];
		var json_request = {};
		var co2FactorValue = [];
		var co2value = $('#co2factor').val();		
		var unique =[];
		unique = selected_siteids.filter(function(itm,i,types) {
		    return i==types.indexOf(itm);
		});
		
		
  		 console.log(unique);
  		 for(var i=0;i<unique.length;i++){
  			var Co2factor = {};
  			Co2factor.siteIds = unique[i];
  			Co2factor.co2FactorValue= parseFloat(co2value);
  			Co2factor.startDate = date;
  			co2FactorValue.push(Co2factor); 
  			//$('#island')[0].sumo.unSelectItem(i);  		
  			console.log(co2FactorValue);
  		 }
  		 
  		
  		
  		//console.log(co2FactorValue);
  		json_request= co2FactorValue;
  		//json_request.saveCO2Factor = co2value;
  		//json_request.siteIds=co2FactorValue;
  		console.log({"json_request":JSON.stringify(json_request)});
  		
  		 if(selected_siteids.length <=0){
    		$.bizalert("Please select site");
    	}else if(co2value == "" || co2value == null){
    		$.bizalert("Enter valid CO2 factor value");  			
    	}else if (co2value < 0) {
    		$.bizalert("Enter valid CO2 factor value");
    		
    	}
  		else{
  		$.ax('../co2Factor/saveCO2Factor.htm', {"saveCO2Factor":JSON.stringify(json_request)}, true, "post", "json", function(data) {
			console.log("data:"+ JSON.stringify(data));
			
			if(data.errorMessage !='some.error.occurred'){//selected_siteids =[];
			$.bizinfo("Successfully added CO2 value");
			}else{
				$.bizinfo("Error in setting Co2 Factor.");
			}
			
		}, function(error){
			console.log("Error: "+JSON.stringify(error));
			$.bizalert("Enter valid CO2 factor value");					
		});}
		/*var id = $('#systemselectsite').val();
		var date = $("#data_1").find("input").val();*/
		/*var co2value = $('#co2factor').val();
		var json_request = "{'siteIds':["+ selected_siteids +"],'co2FactorValue':"+ parseFloat(co2value) +"}";		
		
		$.axs("../co2Factor/saveCO2Factor.htm", { 
			"saveCO2Factor":json_request
		},  function(data) {
			console.log(JSON.stringify(data));
		}, "post");*/
	});

});