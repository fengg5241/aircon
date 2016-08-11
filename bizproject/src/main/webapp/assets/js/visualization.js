$(document).ready(function() {
    var GLOBAL_VAL = {
        imgPath: "../assets/img/visualization/",
        stats_idu_table: null,
        stats_ref_table: null,
        apply_bool: false,
        colors: [
                 // Aircon
                 "#077667", //Power Consumption (Aircon)  - 0
                 "#077667", //Working Hours High on (Aircon) -  1 
                 "#178F7F", //Working Hours Medium on (Aircon) - 2
                 "#1FC2AC", //Working Hours Low on (Aircon) - 3
                 "#F7931E", //Working Hours High off (Aircon) - 4
                 "#178F7F", //Working Hours Medium off (Aircon) - 5
                 "#FFFF00", //Working Hours Low off (Aircon) - 6
                 "#077667", //Setting Temp (Aircon) - 7
                 "#F7931E", //Room Temp (Aircon) - 8
                 // Group
                 "#53A61B", //Power Consumption (Group) - 9
                 "#CAF200", //Rated Capacity (Group) - 10
                 "#53A61B", //Current Capacity (Group) - 11
                 "#A01BA6", //Outdoor Temp (Group) - 12
                 "#A01BA6", //Efficiency (Group) - 13
                  ]
    };

    bindStatsEvent();
    initIDUTable("");
    initREFTable("");
    function initIDUTable(html) {
        if (GLOBAL_VAL.stats_idu_table != null) {
            GLOBAL_VAL.stats_idu_table.destroy();
        }

        $("#stats_idu_table_body").html(html);

        GLOBAL_VAL.stats_idu_table = $('#stats_idu_table')
            .DataTable({
                "paging": false,
                "filter": true,
                "info": false,
                "responsive": true,
                "deferRender": true,
                "scrollCollapse": false,
                "scroller": true,
                "scrollY": 150,
                "language": {
                    "searchPlaceholder": "Search"
                },
       			"search": {"caseInsensitive": false},
                "columnDefs": [
                    { "orderable": false, "targets": 0},
                    { "orderable": true, "targets": 1 ,"orderSequence": [ "desc", "asc"],'type':'natural'},
                    { "orderable": true, "targets": 2 ,"orderSequence": [ "desc", "asc"],'type':'natural'}
                 ],
                 "order": [[ 1, "asc" ]]
            });
    }

    function initREFTable(html) {
        if (GLOBAL_VAL.stats_ref_table != null) {
            GLOBAL_VAL.stats_ref_table.destroy();
        }

        $("#stats_ref_table_body").html(html);

        GLOBAL_VAL.stats_ref_table = $('#stats_ref_table').DataTable({
            "paging": false,
            "filter": true,
            "info": false,
            "responsive": true,
            "deferRender": true,
            "scrollCollapse": false,
            "scroller": true,
            "scrollY": 150,
            "language": {
                "searchPlaceholder": "Search"
            },
   			"search": {"caseInsensitive": false},
            "columnDefs": [
                           { "orderable": false, "targets": 0},
                           { "orderable": true, "targets": 1 ,"orderSequence": [ "desc", "asc"],'type':'natural'}
                        ],
            "order": [[ 1, "asc" ]]
        });
    }
    
    function initIDUScroll(){
        $("#stats_idu_table_wrapper .dataTables_scrollBody")
            .mCustomScrollbar({
                scrollButtons: {
                    enable: true
                },
                theme: "dark-2"
            });
    }

    function initREFScroll(){
        $("#stats_ref_table_wrapper .dataTables_scrollBody")
            .mCustomScrollbar({
                scrollButtons: {
                    enable: true
                },
                theme: "dark-2"
            });
    }

    function populateIDU(idList){
        $.axs("../group/getIDUs.htm", {
            "id": idList.toString()
        },
        function(data) {
        	var iduListHtml = "";
        	//console.log(JSON.stringify(data));
        	for (var i = 0; i < data.length; i++) {
        		iduListHtml += getIDULineHtml(data[i]);
        	}
        	initIDUTable(iduListHtml);
        	initIDUScroll();
        }, "post");
    }
    
    function populateREF(idList){
    	var data_send = "{ 'id' : '"+idList.toString()+"', 'idType': 'group'}";
    		
    	$.axs("../stats/getRefrigerantCircuitsByGroupIds.htm", {
            "json_request": data_send
        },
        function(data) {
        	var refListHtml = "";
        	for (var i = 0; i < data.refrigerantCircuits.length; i++) {
        		var unitVO = { id : data.refrigerantCircuits[i].refId, name: data.refrigerantCircuits[i].refrigerantCircuits};
        		refListHtml += getREFLineHtml(unitVO);
        	}
        	initREFTable(refListHtml);
        	initREFScroll();
        }, "post");
    }
    
    function getREFLineHtml(unitVO) {
    	var refUnitId = "REF" + unitVO.id;
		return "<tr id=" + refUnitId + "tr refDomId=" + refUnitId + "><td class='refHeaderCheckbox'><input id=ref_ck"+ unitVO.id +" class='refCheckbox bizCheckbox' type='checkbox' data-unitid=" + unitVO.id +" /><label for=ref_ck"+ unitVO.id + "></label></td>" +
				getTDHtml(unitVO.name)  + "</tr>"; 
    }
    
    function getIDULineHtml(unitVO) {
    	var refUnitId = "IDU" + unitVO.id;
		return "<tr id=" + refUnitId + "tr refDomId=" + refUnitId + "><td class='iduHeaderCheckbox'><input id=idu_ck"+ unitVO.id +" class='iduCheckbox bizCheckbox' type='checkbox' data-unitid=" + unitVO.id +" /><label for=idu_ck"+ unitVO.id + "></label></td>" +
				getTDHtml(unitVO.name) + getTDHtml(unitVO.pathName)  + "</tr>"; 
    }
    
    function getTDHtml(string) {
        return "<td class='text-center'>" + string + "</td>";
    }
    
    function bindStatsEvent(){
    	bindStatsGroupEvent();
    	bindStatsIDUEvent();
    	bindStatsRefEvent();
    	bindStatsEffEvent();
    	bindStatsCommonEvent();
    }
    
    function bindStatsGroupEvent(){
        $(document).on("click", "#groupButton", function(e) {
            var idList = getCheckedControlGroupIds();
            var opts = $("#group_grouplevel")[0].options;
            if(!($('#displayButton').data('clicked'))){
            	$.bizinfo("Group selection changed. Please click apply selection button in AC configuration panel.");
            } else if (idList.length == 0) {
                $.bizinfo("An error occurred. Please make a selection in AC configuration panel and try submitting again.");
            } else if (opts.length == 0) {
                $.bizinfo("Please select site group or site and click apply selection in AC Configuration panel.");
            } else {
            	$(this).data('clicked', true);
                populateGraph('stats_group_graph', "group_");
            }
            return false;
        });
        
        $(document).on("change", "#group_parameter", function(e) {
            if ($(this).val() == "capacity" || $(this).val() == "efficiency") {
                var optionsHtml = "";
                var opts = $("#group_grouplevel")[0].options;
                var array = $.map(opts, function(elem) {
                            	return (elem.value + "-" + elem.text);
                        	});
                //console.log(array);
                for (var i = 0; i < array.length; i++) {
                    if (array[i] == "1-Country" || array[i] == "2-State" || array[i] == "3-Town" || array[i] == "4-Area" || array[i] == "5-Street" || array[i] == "6-Building") {
                        var temp_array = array[i].split("-");
                        optionsHtml += "<option value=" + temp_array[0] + ">" + temp_array[1] + "</option>";
                    }
                }

                $("#group_grouplevel").html(optionsHtml);
            } else {
                populateGroupSelects("group_");
            }
            $("#groupButton").data('clicked', false);
        });
        
        // For download for groups
        $(document).on("click", "#groupDownloadButton", function(e) {
            var idList = getCheckedControlGroupIds();
            var opts = $("#group_grouplevel")[0].options;
            if (idList.length == 0) {
                $.bizinfo("An error occurred. Please make a selection in AC configuration panel and try submitting again.");
            } else if (opts.length == 0) {
                $.bizinfo("Please select site group or site and click apply selection in AC Configuration panel.");
            } else if (!($('#groupButton').data('clicked'))){
            	$.bizinfo("Please click on Apply Selection button if changes are made to any of the above entries before downloading data.");
        	} else {
                downloadData("group_");
            }
            return false;
        });
        
        $(document).on("change", "#group_type", function(e) {
            $("#groupButton").data('clicked', false);
        });
        
        $(document).on("change", "#group_grouplevel", function(e) {
            $("#groupButton").data('clicked', false);
        });
        
        $('input[name="group_period"]').on('ifChecked', function(event){
        	$("#groupButton").data('clicked', false);
    	});
    }
    
    function bindStatsIDUEvent() {
    	//click head checkbox then check all
        $(document).on("click", "#iduHeaderCheckbox", function() {
        	var status = $(this).prop("checked");
        	$("#stats_idu_table_body .iduCheckbox").prop("checked",status);
        	status ? $("#stats_idu_table_body tr").addClass("trSelected"):$("#stats_idu_table_body tr").removeClass("trSelected");
        	$(this).removeClass("mixStatus");
        	$("#iduButton").data('clicked', false);
        });
        
        //click detail checkbox then affect head checkbox
        $(document).on("click", "#stats_idu_table_body .iduCheckbox", function() {
        	var $this = $(this);
        	var currentStatus = $this.prop("checked");
        	
        	if (currentStatus) {
				$this.parent().parent().addClass("trSelected");
        	}else{
        		$this.parent().parent().removeClass("trSelected");
        	}
        	
        	var allCheckboxCount = $("#stats_idu_table_body .iduCheckbox").length;
        	var checkedCount = $("#stats_idu_table_body .iduCheckbox:checked").length;
        	
        	if (checkedCount == allCheckboxCount) {
        		$("#iduHeaderCheckbox").removeClass("mixStatus").prop("checked",true);
			}else {
				if (checkedCount == 0) {
					$("#iduHeaderCheckbox").removeClass("mixStatus").prop("checked",false);
				}else {
					$("#iduHeaderCheckbox").addClass("mixStatus").prop("checked",false);
				}
			}
        	$("#iduButton").data('clicked', false);
        });
        
        $(document).on("click", "#iduButton", function(e) {
            var idList = getCheckedControlGroupIds();
            var opts = $("#stats_idu_table_body > tr > td").text();
            if(!($('#displayButton').data('clicked'))){
            	$.bizinfo("Group selection changed. Please click apply selection button in AC configuration panel.");
            } else if (idList.length == 0) {
                $.bizinfo("An error occurred. Please make a selection in AC configuration panel and try submitting again.");
            } else if (opts == "No data available in table") {
                $.bizinfo("Please select site group or site and click apply selection in AC Configuration panel.");
            } else {
            	$(this).data('clicked', true);
                populateGraph('stats_idu_graph', "idu_");
            }
            return false;
        });
        
        // For download for IDU
        $(document).on("click", "#iduDownloadButton", function(e) {
            var idList = getCheckedControlGroupIds();
            if (idList.length == 0) {
                $.bizinfo("An error occurred. Please make a selection in AC configuration panel and try submitting again.");
            } else if (!($('#iduButton').data('clicked'))){
            	$.bizinfo("Please click on Apply Selection button if changes are made to any of the above entries before downloading data.");
        	} else {
                downloadData("idu_");
            }
            return false;
        });
        
        $(document).on("change", "#idu_parameter", function(e) {
            $("#iduButton").data('clicked', false);
        });
        
        $(document).on("change", "#idu_type", function(e) {
            $("#iduButton").data('clicked', false);
        });
        
        $('input[name="idu_period"]').on('ifChecked', function(event){
        	$("#iduButton").data('clicked', false);
    	});
    }
    
    function _getCheckedIds(type){
    	var Ids = [];
    	$("#stats_"+type+"table_body input:checked").each(function( index ) {
    		Ids.push($(this).attr("data-unitid"));
    	});
    	return Ids;
    }
    
    function bindStatsRefEvent(){
    	//click head checkbox then check all
        $(document).on("click", "#refHeaderCheckbox", function() {
        	var status = $(this).prop("checked");
        	$("#stats_ref_table_body .refCheckbox").prop("checked",status);
        	status ? $("#stats_ref_table_body tr").addClass("trSelected"):$("#stats_ref_table_body tr").removeClass("trSelected");
        	$(this).removeClass("mixStatus");
        	$("#refButton").data('clicked', false);
        });
        
        //click detail checkbox then affect head checkbox
        $(document).on("click", "#stats_ref_table_body .refCheckbox", function() {
        	var $this = $(this);
        	var currentStatus = $this.prop("checked");
        	
        	if (currentStatus) {
				$this.parent().parent().addClass("trSelected");
        	}else{
        		$this.parent().parent().removeClass("trSelected");
        	}
        	
        	var allCheckboxCount = $("#stats_ref_table_body .refCheckbox").length;
        	var checkedCount = $("#stats_ref_table_body .refCheckbox:checked").length;
        	
        	if (checkedCount == allCheckboxCount) {
        		$("#refHeaderCheckbox").removeClass("mixStatus").prop("checked",true);
			}else {
				if (checkedCount == 0) {
					$("#refHeaderCheckbox").removeClass("mixStatus").prop("checked",false);
				}else {
					$("#refHeaderCheckbox").addClass("mixStatus").prop("checked",false);
				}
			}
        	$("#refButton").data('clicked', false);
        });

        $(document).on("click", "#refButton", function(e) {
            var idList = getCheckedControlGroupIds();
            var siteIdList = getCheckedGroupIdsByLevelName("Building");
            if(!($('#displayButton').data('clicked'))){
            	$.bizinfo("Group selection changed. Please click apply selection button in AC configuration panel.");
            } else if (idList.length == 0) {
                $.bizinfo("An error occurred. Please make a selection in AC configuration panel and try submitting again.");
            } else if (siteIdList.length == 0) {
                $.bizinfo("Please select site group or site and click apply selection in AC Configuration panel.");
            } else {
            	$(this).data('clicked', true);
                populateGraph('stats_ref_graph', "ref_");
            }
            return false;
        });
        
        // For download for Refrigerant
        $(document).on("click", "#refDownloadButton", function(e) {
            var idList = getCheckedControlGroupIds();
            var opts = $("#group_grouplevel")[0].options;
            if (idList.length == 0) {
                $.bizinfo("An error occurred. Please make a selection in AC configuration panel and try submitting again.");
            } else if (opts.length == 0) {
                $.bizinfo("Please select site group or site and click apply selection in AC Configuration panel.");
            } else if (!($('#refButton').data('clicked'))){
            	$.bizinfo("Please click on Apply Selection button if changes are made to any of the above entries before downloading data.");
        	} else {
                downloadData("ref_");
            }
            return false;
        });
        
        // hiding hourly info for working hours
        $(document).on("change", "#ref_parameter", function(e) {
        	var type = $("#ref_type").val();
            if ($(this).val() == "workinghours" && type == "chronological") {
            	$("#today").hide();
            	$("#past24hours").hide();
            	var peroid = $('input[name="ref_period"]:checked').val();
            	if(peroid == "today" || peroid == "past24hours"){
            		$('input[value="date_range"]').iCheck('check');
            	}
            } else {
            	$("#today").show();
            	$("#past24hours").show();
            }
            $("#refButton").data('clicked', false);
        });
        
        $(document).on("change", "#ref_type", function(e) {
        	var parameter = $("#ref_parameter").val();
            if (parameter == "workinghours" && $(this).val() == "chronological") {
            	$("#today").hide();
            	$("#past24hours").hide();
            	var peroid = $('input[name="ref_period"]:checked').val();
            	if(peroid == "today" || peroid == "past24hours"){
            		$('input[value="date_range"]').iCheck('check');
            	}
            } else {
            	$("#today").show();
            	$("#past24hours").show();
            }
            $("#refButton").data('clicked', false);
        });
        
        $('input[name="ref_period"]').on('ifChecked', function(event){
        	$("#refButton").data('clicked', false);
    	});
    }
    
	function bindStatsEffEvent(){
        $(document).on("click", "#efficiencyButton", function(e) {
            var idList = getCheckedControlGroupIds();
            var opts = $("#efficiency_grouplevel")[0].options;
            if(!($('#displayButton').data('clicked'))){
            	$.bizinfo("Group selection changed. Please click apply selection button in AC configuration panel.");
            } else if (idList.length == 0) {
                $.bizinfo("An error occurred. Please make a selection in AC configuration panel and try submitting again.");
            } else if (opts.length == 0) {
                $.bizinfo("Please select site group or site and click apply selection in AC Configuration panel.");
            } else {
            	$(this).data('clicked', true);
                populateEfficiencyRankingGraph();
            }
            return false;
        });
        
        // For download for efficiency
        $(document).on("click", "#efficiencyDownloadButton", function(e) {
            var idList = getCheckedControlGroupIds();
            var opts = $("#efficiency_grouplevel")[0].options;
            if (idList.length == 0) {
                $.bizinfo("An error occurred. Please make a selection in AC configuration panel and try submitting again.");
            } else if (opts.length == 0) {
                $.bizinfo("Please select site group or site and click apply selection in AC Configuration panel.");
            } else if (!($('#efficiencyButton').data('clicked'))){
            	$.bizinfo("Please click on Apply Selection button if changes are made to any of the above entries before downloading data.");
        	} else {
                downloadData("efficiency_");
            }
            return false;
        });
        
        $(document).on("change", "#efficiency_grouplevel", function(e) {
            $("#efficiencyButton").data('clicked', false);
        });
        
        $('input[name="efficiency_period"]').on('ifChecked', function(event){
        	$("#efficiencyButton").data('clicked', false);
    	});
	}
    
    function bindStatsCommonEvent(){
        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
        });
        
        var end_date = new Date();
        var start_date = new Date(end_date.getFullYear(), end_date.getMonth(), end_date.getDate() - 6);
        
        var end_month = end_date.getMonth()+1;
        
        if(parseInt(end_month) < 10){
        	end_month = "0"+end_month;
        }
        
        var end_day = end_date.getDate()
        
        if(parseInt(end_day) < 10){
        	end_day = "0"+end_day;
        }
        
        var start_month = start_date.getMonth()+1;
        
        if(parseInt(start_month) < 10){
        	start_month = "0"+start_month;
        }
        
        var start_day = start_date.getDate()
        
        if(parseInt(start_day) < 10){
        	start_day = "0"+start_day;
        }

	    var end_date_string = end_day+"/"+end_month+"/"+end_date.getFullYear();
	    
	    var start_date_string = start_day+"/"+start_month+"/"+start_date.getFullYear();
	    
	    $('#idu_datepicker > input[name="idu_end"]').val(end_date_string);
        $('#idu_datepicker > input[name="idu_start"]').val(start_date_string);
        
        $('#group_datepicker > input[name="group_end"]').val(end_date_string);
        $('#group_datepicker > input[name="group_start"]').val(start_date_string);
        
        $('#efficiency_datepicker > input[name="efficiency_end"]').val(end_date_string);
        $('#efficiency_datepicker > input[name="efficiency_start"]').val(start_date_string);
        
        $('#ref_datepicker > input[name="ref_end"]').val(end_date_string);
        $('#ref_datepicker > input[name="ref_start"]').val(start_date_string);

        $('#idu_datepicker').datepicker({
            keyboardNavigation: false,
            forceParse: false,
            autoclose: true,
            endDate: "0d",
            startDate: "-3y",
            format: 'dd/mm/yyyy'
        }).on('changeDate', function(e) {
            $("#iduButton").data('clicked', false);
        });
        
        $('#group_datepicker').datepicker({
            keyboardNavigation: false,
            forceParse: false,
            autoclose: true,
            endDate: "0d",
            startDate: "-3y",
            format: 'dd/mm/yyyy'
        }).on('changeDate', function(e) {
            $("#groupButton").data('clicked', false);
        });
        
        $('#efficiency_datepicker').datepicker({
            keyboardNavigation: false,
            forceParse: false,
            autoclose: true,
            endDate: "0d",
            startDate: "-3y",
            format: 'dd/mm/yyyy'
        }).on('changeDate', function(e) {
            $("#efficiencyButton").data('clicked', false);
        });
        
        $('#ref_datepicker').datepicker({
            keyboardNavigation: false,
            forceParse: false,
            autoclose: true,
            endDate: "0d",
            startDate: "-3y",
            format: 'dd/mm/yyyy'
        }).on('changeDate', function(e) {
            $("#refButton").data('clicked', false);
        });
        
        $(document).on("click", "#displayButton", function(e) {
            var idList = getCheckedGroupIds();
            if (idList.length == 0) {
                $.bizinfo("An error occurred. Please make a selection in AC configuration panel and try submitting again.");
            } else {
            	$('#checkboxMenuTree').jstree().save_state();
            	
                populateGroupSelects("group_");
                
                populateGroupSelects("efficiency_");

                $("#iduHeaderCheckbox").removeClass("mixStatus").prop("checked",false);
                
                populateIDU(idList);
                
                var siteIdList = getCheckedGroupIdsByLevelName('Building');
                
                $("#refHeaderCheckbox").removeClass("mixStatus").prop("checked",false);
                
                if(siteIdList.length != 0){
                	populateREF(siteIdList);
                }else{
                	initREFTable("");
                }
                
                GLOBAL_VAL.apply_bool = true;
                
                $("#displayButton").data('clicked', true);
                
                $("#groupButton").data('clicked', false);
                
                $("#iduButton").data('clicked', false);
                
                $("#refButton").data('clicked', false);
                
                $("#efficiencyButton").data('clicked', false);
            }
            return false;
        });
        
        $(document).on("click", "#inner a[data-toggle='tab']",function() {
            var target = $(this).attr("href");
            if (target == "#statsGroup" || target == "#efficiencyRanking") {

            } else {
                $(target + " .dataTables_scrollBody").mCustomScrollbar("update");
                $(target + " .dataTables_scroll").resize();
            }
        });

        //on load
        window.onload = loadDefault;
    }
    
    function loadDefault(){
    	var idList = getCheckedControlGroupIds();
        var siteIdList = getCheckedGroupIdsByLevelName('Building');

        if(idList.length != 0){
        	populateIDU(idList);
        	//populateGraph('stats_group_graph', "group_");
        }
        
        if(siteIdList.length != 0){
        	populateREF(siteIdList);
        }else{
        	initREFTable("");
        }
    }

    function populateGroupSelects(type) {
        var tree = $.jstree.reference('#checkboxMenuTree');
        var checkedList = tree.get_checked(true);
        var optionsHtml = "";
        var temp = [];
        var unique;
        for (var i = 0; i < checkedList.length; i++) {
            var levelName = checkedList[i].original.groupTypeLevelName;
            if (typeof levelName !== "undefined") {
                temp.push(checkedList[i].original.groupTypeLevelID + "-" + checkedList[i].original.groupTypeLevelName);
            }
        }

        unique = temp.filter(function(itm, i, types) {
            return i == types.indexOf(itm);
        });

		unique.sort(function(a,b){
			a=a.split("-");
		    b=b.split("-");
		    var an=parseInt(a[0],10);
		    var bn=parseInt(b[0],10);
		    return an<bn?1:(an>bn?-1:(a[1]<b[1]?-1:(a[1]>b[1]?1:0)));
		});
		
		unique.reverse();
        for (var i = 0; i < unique.length; i++) {
        	var filter_group = true;
            var temp_array = unique[i].split("-");
            if(type == "efficiency_" || type == "group_"){
            	if(type == "group_"){
            		if($("#"+type+"parameter").val() == "power_consumption")
            			filter_group = false;
            	}
            	console.log(filter_group);
            	if(temp_array[0] <= 6){
            		optionsHtml += "<option value=" + temp_array[0] + ">" + temp_array[1] + "</option>";
            	}else if(!filter_group){
            		optionsHtml += "<option value=" + temp_array[0] + ">" + temp_array[1] + "</option>";
            	}
            }
        }

        $("#"+type+"grouplevel").html(optionsHtml);
    }
    
    //Sorting logic for alphanumeric types
    jQuery.fn.dataTableExt.oSort['natural-asc']  = function(a,b) {
        return sortAlphaNum(a,b);
    };

    jQuery.fn.dataTableExt.oSort['natural-desc'] = function(a,b) {
        return sortAlphaNum(a,b) * -1;
    }; 
    
    function sortAlphaNum(a,b) {
    	var reA = /[^a-zA-Z]/g;
        var reN = /[^0-9]/g;
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
    //end of Sorting logic for alphanumeric types
    
    function downloadData(type){
    	var parameter = null;
    	if(type != 'efficiency_'){
    		parameter = $("#" + type + "parameter").val();
    	}
    	var display = $("#" + type + "type").val();
        var id = [];
        var url = "../stats/downloadStatisticsByGroup.htm";
        var idType = 'group';
        var title = $("#" + type + "parameter :selected").text();
        var parameterOption = null;
        var start_date = null;
        var end_date = null; 
        
        var period = "'" + $('input[name="' + type + 'period"]:checked').val() + "'";
        if (period == "'date_range'") {
            start_date = $('#'+type+'datepicker > input[name="' + type + 'start"]').val();
            end_date = $('#'+type+'datepicker > input[name="' + type + 'end"]').val();
            
            var start_temp = start_date.split('/');
            var end_temp = end_date.split('/');
	        start_date = "'" + start_temp[2] + "-" + start_temp[1] + "-" + start_temp[0] + "'";
	        end_date = "'" + end_temp[2] + "-" + end_temp[1] + "-" + end_temp[0] + "'";
            period = null;
        }
        
        if (type == "group_") {
            var compare = $("#" + type + "grouplevel").val();
            var compare_name = $(
                "#" + type + "grouplevel :selected").text();
            id = getCheckedGroupIdsByLevelName(compare_name);
            url = "../stats/downloadStatisticsByGroup.htm";
            idType = 'group';
            grouplevel = ",'grouplevel': " + compare;
        } else if (type == "idu_") {
        	id = _getCheckedIds(type);
        	if(id.length == 0){
        		$.bizinfo("Please select at least 1 check box to proceed download");
        		return false;
        	}
        	url = "../stats/downloadStatisticsByAircon.htm";
        	idType = 'indoorUnit';
        	grouplevel = "";
        } else if (type == "ref_"){
        	var compare = 6;
            var compare_name = "Building";
        	id = _getCheckedIds(type);
        	if(id.length == 0){
        		$.bizinfo("Please select at least 1 check box to proceed download");
        		return false;
        	}
        	url = "../stats/downloadStatisticsByRefrigerantCircuit.htm";
        	idType = 'refrigerantcircuit';
        	grouplevel = ",'grouplevel': " + compare;
        } else {
        	var compare = $("#" + type + "grouplevel").val();
            var compare_name = $(
                "#" + type + "grouplevel :selected").text();
        	id = getCheckedGroupIdsByLevelName(compare_name);
        	parameter = 'efficiency';
        	url = "../stats/downloadEfficiencyRanking.htm";
        	idType = 'group';
        	grouplevel = ",'grouplevel': " + compare;
        	display = "accumulated";
        }
        
        if (parameter === "capacity") {
            parameterOption = "'heat'";
        }else if(parameter === "workinghours"){
        	parameterOption = "'onoff'";
        }else if(parameter === "efficiency"){
        	parameterOption = "'cop'";
        }
        
        data_send = "{ 'id': '" + id.toString() + "','idType': '" + idType + "','type': '" + display + "','parameter': '" + parameter + "', 'parameterOption' : " + parameterOption + ",'startDate': " + start_date + ",'endDate': " + end_date + ",'period': " + period + "" + grouplevel + ", 'addCustName':'yes', 'fileType':'csv'}";
        console.log(data_send);

        $.ajax({
            url: url,
            beforeSend: function (request){
            	getToken(request);
            },
            data: {
                "json_request": data_send,
                "AJAXREQUEST": true
            },
            cache: false,
            method: "POST",
            success: function(data) {
	        	console.log(data);
	        	if(data != '{"errorMessage":"no.records.found"}'){
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
		            	date_range = period.substring(1, period.length - 1);
		            }
		            
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
		            
		            var fileName = parameter+"_"+type+date_range+"("+timestamp+").csv";
		        	
		            // Download new Logic via plugin
		            downloadAs(data, fileName);
	        	}else{
	        		$.bizinfo("No Records Found");
	        	}
            }
        });
    }
    
    function populateGraph(e, type) {
        var parameter = $("#" + type + "parameter").val();
        var display = $("#" + type + "type").val();
        var id = [];
        var url = "../stats/statisticsByGroup.htm";
        var idType = 'group';
        var title = $("#" + type + "parameter :selected").text();
        var parameterOption = null;
        var start_date = null;
        var end_date = null;

        var graph_type = "spline";

        //change title
        $("#"+type+"type_title").text(title);

        var period = "'" + $('input[name="' + type + 'period"]:checked').val() + "'";
        if (period == "'date_range'") {
            start_date = $('#'+type+'datepicker > input[name="' + type + 'start"]').val();
            end_date = $('#'+type+'datepicker > input[name="' + type + 'end"]').val();
            
            var start_temp = start_date.split('/');
            var end_temp = end_date.split('/');
	        start_date = "'" + start_temp[2] + "-" + start_temp[1] + "-" + start_temp[0] + "'";
	        end_date = "'" + end_temp[2] + "-" + end_temp[1] + "-" + end_temp[0] + "'";
            period = null;
        }

        var grouplevel = "";
        var yaxis_title = "";
        
        if (type == "group_") {
            var compare = $("#" + type + "grouplevel").val();
            var compare_name = $(
                "#" + type + "grouplevel :selected").text();
            id = getCheckedGroupIdsByLevelName(compare_name);
            url = "../stats/statisticsByGroup.htm";
            idType = 'group';
            grouplevel = ",'grouplevel': " + compare;
        } else if (type == "idu_") {
        	id = _getCheckedIds(type);
        	if(id.length == 0){
        		$.bizinfo("Please select at least 1 check box to view data");
        		return false;
        	}
        	url = "../stats/statisticsByAircon.htm";
        	idType = 'indoorUnit';
        	grouplevel = "";
        } else {
        	var compare = 6;
            var compare_name = "Building";
        	id = _getCheckedIds(type);
        	if(id.length == 0){
        		$.bizinfo("Please select at least 1 check box to view data");
        		return false;
        	}
        	url = "../stats/statisticsByRefrigerantCircuit.htm";
        	idType = 'refrigerantcircuit';
        	grouplevel = ",'grouplevel': " + compare;
        }
        
        if (display == "chronological") {
            grouplevel = "";
        } else {
            graph_type = "column";
        }
        
        if (parameter === "capacity") {
            parameterOption = "'heat'";
        }else if(parameter === "workinghours"){
        	parameterOption = "'onoff'";
        }else if(parameter === "efficiency"){
        	parameterOption = "'cop'";
        }
        
        if(parameter == "workinghours" && type == "ref_" && period == null && display == "chronological"){
        	if(start_date == end_date){
        		$.bizinfo("Start data and end date can't be the same!!!");
        		return false;
        	}
        }
        
        $("#" + type + "total_current_capacity").html("");

        data_send = "{ 'id': '" + id.toString() + "','idType': '" + idType + "','type': '" + display + "','parameter': '" + parameter + "', 'parameterOption' : " + parameterOption + ",'startDate': " + start_date + ",'endDate': " + end_date + ",'period': " + period + "" + grouplevel + "}";

        console.log(data_send);

        $.axs(url, {
            "json_request": data_send
        },
        function(data) {
        	//console.log("Start :"+ JSON.stringify(data));
        	var legend = null;
        	var stacking = null;
        	$("#total_current_capacity").html("");
            if (typeof data.errorMessage === 'undefined') {
            	
                if (parameter == "power_consumption") {
                	if(display == "accumulated"){
                		var color = GLOBAL_VAL.colors[9];
                		if (type == "idu_") {
                			color = GLOBAL_VAL.colors[0];
                		}
                		
                		if(type != "ref_"){
		                    data.series = [{
		                        borderWidth: 0,
		                        name: title,
		                        data: data.series,
		                        color: color,
		                        tooltip: {valueSuffix: ' kWh'}
		                    }];
	                	}
                	}
                	yaxis_title = "Power Consumption (kWh)";
                } else if(parameter == "capacity") {
                	if(display == "accumulated") {
                		if(data.total == null)
                			data.total = 0;
                		$("#" + type + "total_current_capacity").html("Total: "+data.total+"kW");
		                data.series[0].borderWidth = 0;
		                data.series[1].borderWidth = 0;

		                data.series[2].type = "spline";
		                
		                data.series[0].tooltip = {valueSuffix: ' kW'};
	                	data.series[1].tooltip = {valueSuffix: ' kW'};
	                	data.series[2].tooltip = {valueSuffix: ' &deg;C'};
	                	
	                	data.series[0].color = GLOBAL_VAL.colors[10];
		                data.series[1].color = GLOBAL_VAL.colors[11];
		                data.series[2].color = GLOBAL_VAL.colors[12];
	                	
	                	data.series[2].yAxis = 1;
                	}else{
                		$("#" + type + "total_current_capacity").html("");
                		var j = 2;
                		for(var i = 0; i < data.series.length; i++){
                			data.series[i].tooltip = {valueSuffix: ' kW'};
                			if(i == j){
                				data.series[i].tooltip = {valueSuffix: ' &deg;C'};
                    			data.series[i].yAxis = 1;
                				j = j + 3;
                			}
                		}
                	}
                	yaxis_title = "Capacity (kW)";
                	
                	var yAxis = [{
                        title: {
                            text: yaxis_title,
                            style: {
                                color: "#646464"
                            },
                            useHTML: true
                        },
                        labels: {
                            format: '{value}',
                            style: {
                                color: "#646464"
                            }
                        }
                    },{
                        title: {
                            text: "Temperature (&deg;C)",
                            style: {
                                color: "#646464"
                            },
                            useHTML: true
                        },
                        labels: {
                            format: '{value}',
                            style: {
                                color: "#646464"
                            }
                        },
                        opposite: true
                    }];
                	
                } else if (parameter == "efficiency") {
                	if(display == "accumulated") {
	                	data.series[0].borderWidth = 0;
	                	data.series[0].color = GLOBAL_VAL.colors[13];
	                	data.series[0].tooltip = {valueSuffix: ' %'};
                	}
                	yaxis_title = "Efficiency (%)";
                } else if (parameter == "workinghours") {
                	var data_temp = [];
                	if(display == "accumulated") {
	                	for(var i = 0; i < data.series.length; i++){
	                		data.series[i].borderWidth = 0;
	                		data.series[i].color = GLOBAL_VAL.colors[i+1];
	                		data.series[i].tooltip = {valueSuffix: ' Hours'};
	                	}
                	}else{
                		for(var i = 0; i < data.series.length; i++){
	                		data.series[i].tooltip = {valueSuffix: ' Hours'};
	                	}
                	}
                	stacking = 'normal';
                	yaxis_title = "Working Hours (Hours)";
                } else if (parameter == "difftemperature") {
                	if(display == "accumulated"){
	                	for(var i = 0; i < data.series.length - 1; i++){
	                		data.series[i].borderWidth = 0;
	                		data.series[i].tooltip = {valueSuffix: '&deg;C'};
	                		data.series[i].color = GLOBAL_VAL.colors[i+7];
	                	}
	                	data.series.pop();
                	}else{
                		var data_temp = [];
                		for(var i = 0; i < data.series.length; i++){
                			if(data.series[i].name.indexOf("Different Temp") == -1){
                				data_temp.push(data.series[i]);
                				data_temp.push(data.series[i].tooltip = {valueSuffix: '&deg;C'});
                			}
                		}
                		data.series = data_temp;
                	}
                	yaxis_title = "Temperature (&deg;C)";
                }
                legend = 'Legend:';
            } else {
                data.series = [];
            }
            
            if(parameter != "capacity"){
            	var yAxis = [{
                    title: {
                        text: yaxis_title,
                        style: {
                            color: "#646464"
                        },
                        useHTML: true
                    },
                    labels: {
                        format: '{value}',
                        style: {
                            color: "#646464"
                        }
                    }
                }];
            }
            
            
            var min = max = 0;
            var scroll = false;
            if(data.series.length > 0){
	            if(data.categories.length > 0){
	            	if(display == "chronological"){
	            		if(data.categories.length > 12){
	            			max = 11;
	            			scroll = true;
	            		}else{
	            			max = data.categories.length - 1;
	            			scroll = false;
	            		}
	            	}else{
	            		if(data.categories.length > 3){
	            			max = 2;
	            			scroll = true;
	            		}else{
	            			max = data.categories.length - 1;
	            			scroll = false;
	            		}
	            	}
	            }
            }
            //console.log(data.categories.length +" max: "+max+" scroll:"+scroll);
            //console.log("END: "+JSON.stringify(data.series));
            $('#' + e).highcharts({
                credits: {
                    enabled: false
                },
                legend: {
                	enabled: true,
                    title: {
                        text: legend,
                        style: {
                            color: '#646464'
                        }
                    },
                    align: 'right',
                    layout: 'vertical',
                    verticalAlign: 'top',
            		itemStyle: {			
            			color: '#646464'
            		},
            		itemHoverStyle: {
            			color: '#646464'
            		}
                },
                rangeSelector: {
                    enabled: false
                },
                navigator: {
                    enabled: false
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
                    height: 15
                },
                chart: {
                    backgroundColor: null,
                    type: graph_type,
                    zoomType: 'xy',
                    //height: height,
                    plotBorderWidth: 0,
                    animation: true,
                    style: {
                        fontFamily: 'panasonic'
                    }
                },
                title: {
                    text: null
                },
                xAxis: {
                    min: min,
                    max: max,
                    categories: data.categories,
                    labels: {
                        style: {
                            color: "#646464"
                        }
                    }
                },
                yAxis: yAxis,
                tooltip: {
                    pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y} </b><br/>',
                    shared: true,
                    useHTML: true,
                    valueSuffix: ' '+yaxis_title
                },
                series: data.series,
                plotOptions: {
                    column: {
                        stacking: stacking
                    }
                }
                //colors: ['#04A22C']
            });
        }, "post");
    }
    
    function populateEfficiencyRankingGraph() { 
    	var id = [];
        var url = "../stats/efficiencyRanking.htm";
        var idType = 'group';
        var type = "efficiency_";
        var start_date = null;
        var end_date = null;
        var display = "accumulated";
        var period = "'" + $('input[name="' + type + 'period"]:checked').val() + "'";
        
        if (period == "'date_range'") {
            start_date = $('#'+type+'datepicker > input[name="' + type + 'start"]').val();
            end_date = $('#'+type+'datepicker > input[name="' + type + 'end"]').val();
            
            var start_temp = start_date.split('/');
            var end_temp = end_date.split('/');
	        start_date = "'" + start_temp[2] + "-" + start_temp[1] + "-" + start_temp[0] + "'";
	        end_date = "'" + end_temp[2] + "-" + end_temp[1] + "-" + end_temp[0] + "'";
            period = null;
        }
        
        var compare = $("#" + type + "grouplevel").val();
        var compare_name = $("#" + type + "grouplevel :selected").text();
        id = getCheckedGroupIdsByLevelName(compare_name);
        var grouplevel = ",'grouplevel': " + compare;
    	
        data_send = "{ 'id': '" + id.toString() + "','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'seer','startDate': " + start_date + ",'endDate': " + end_date + ",'period': " + period + "" + grouplevel + "}";
        //console.log("INPUT: "+ data_send);

        $.axs(url, {
            "json_request": data_send
        },
        function(data) {
			//console.log("Start: " +JSON.stringify(data));
			var images=['VE10_30.png', 'VE10_30.png', 'VE10_30.png', 'VE40.png', 'VE50.png', 'VE60.png', 'VE70.png', 'VE80.png', 'VE90.png', 'VE100.png', 'VE110_150.png', 'VE110_150.png', 'VE110_150.png', 'VE110_150.png', 'VE110_150.png', 'VE160_200.png', 'VE160_200.png', 'VE160_200.png', 'VE160_200.png', 'VE160_200.png'];
			var data_efficiency = []; 
			if(typeof data.errorMessage === 'undefined'){
				for(var i = 0, k = 0; i < data.value.length; i++){
	               var val = (data.value[i] < 1) ? Math.ceil(data.value[i]): Math.round(data.value[i]);
	
					for(var j = 0; j <= ((val-1)/10)+1; j++){
						   data_efficiency[k] = {};
						   data_efficiency[k].x = i;
						   data_efficiency[k].y = (j * 10)-5;
						   data_efficiency[k].marker = {symbol : "url("+ GLOBAL_VAL.imgPath+images[j - 1]+")"};
						   k++;
					}
				 }
			}
			//console.log("END: " +JSON.stringify(data));

		    var chart = new Highcharts.Chart({
		        chart: {
		            renderTo: "stats_efficiency_graph",
		            marginRight: 10,
		            type: 'bar',
		            backgroundColor: null,
	                style: {
	                	fontFamily: 'panasonic'
	                }
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
		    	exporting :{
		    		enabled:false
		    	},
		        xAxis: [{
		            tickWidth: 0,
		            lineWidth: 0,
		            categories: data.compare_by,
		    		gridLineWidth: 1,
		    		gridLineColor: "#B3B3B3",
		    		offset: 15,
		    		labels: {	    		    			
		    			style: {
		                    color: '#4d4d4d'
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
		                    color: '#4d4d4d'
		                }
		    		},
		            startOnTick: false,
		            title: {
	                    text: null
	                }
		    	}],
		        tooltip: {
		    		shared: true,
		    		useHTML: true,
		    		formatter: function () {
		    		if (false) {
		    		  var s = '<span><b>' + this.x + '</b></span><table>';
		    		  $.each(this.points, function () {
		    			s += '<tr><td align = "left" style = "color:' + this.series.color + ';">' + this.series.name + ': ' + '</td>' +
		    			  '<td><b>' + this.y + '</b></td></tr>';
		    		  });
		    		  return s + '</table>';
		    		} else {
		    		  return false;
		    		}
		        }},
		        series: [{
		            data: data_efficiency,
		           	type: 'scatter',
		           	animation:false
		        }]
		    });
		}, "post");
    }
    
    menu('visualization');
});