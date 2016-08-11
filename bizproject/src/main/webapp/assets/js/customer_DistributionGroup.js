/**
 * 
 */
$(document).ready(function() {


    var customerSetting = false;
    $('#final_add_update').hide();

    $('.idu_table_view_name1').hide();
    //$('#cust_name_search').hide();
    //$('.distribution_group_name').hide();
    hideTrigger();
    $('#Remove').hide();

    $("#distributionGroupOverviewBody").mCustomScrollbar({
        scrollButtons: {
            enable: true
        },
        theme: "dark-2"
    });

    var GLOBAL_VAL = {
        scrollPulseY: "80px",
        scrollY: "400px",
        scrollY2: "334px",
        DistributeDataTable: null,
        DistributeDataPulseTable: null,
        DistributeDataDetailTable: null,
        DistributeDataPulseDetailTable: null,
        dataBool: false
    };

    GLOBAL_VAL.DistributeDataTable = $('#table_distribution_group_pls_id').DataTable({
        "paging": false,
        "filter": true,
        "info": false,
        "responsive": true,
        "deferRender": true,
        "scrollCollapse": false,
        "scroller": true,
        "stateSave": true,
        "scrollY": GLOBAL_VAL.scrollY,
        "language": {
            "searchPlaceholder": "Search"
        },
        "search": {
            "caseInsensitive": false
        }
    });

    GLOBAL_VAL.DistributeDataPulseTable = $('#table_distribution_group_idu_id').DataTable({
        "paging": false,
        "filter": true,
        "info": false,
        "responsive": true,
        "deferRender": true,
        "scrollCollapse": false,
        "scroller": true,
        "stateSave": true,
        "scrollY": GLOBAL_VAL.scrollPulseY,
        "language": {
            "searchPlaceholder": "Search"
        },
        "search": {
            "caseInsensitive": false
        }
    });

    Distribtion_group_table(null);

    GLOBAL_VAL.DistributeDataPulseDetailTable = $('#table_distribution_group_pls_id_final_window_customer').DataTable({
        "paging": false,
        "filter": true,
        "info": false,
        "responsive": true,
        "deferRender": true,
        "scrollCollapse": false,
        "scroller": true,
        "stateSave": true,
        "scrollY": GLOBAL_VAL.scrollPulseY,
        "language": {
            "searchPlaceholder": "Search"
        },
        "search": {
            "caseInsensitive": false
        }
    });

    GLOBAL_VAL.DistributeDataDetailTable = $('#table_distribution_group_idu_id_final_window_customer').DataTable({
        "paging": false,
        "filter": true,
        "info": false,
        "responsive": true,
        "deferRender": true,
        "scrollCollapse": false,
        "scroller": true,
        "stateSave": true,
        "scrollY": GLOBAL_VAL.scrollY,
        "language": {
            "searchPlaceholder": "Search"
        },
        "search": {
            "caseInsensitive": false
        }
    });

    fetchDistributionGroupDevice(null);


    $("#table_distribution_group_pls_id_wrapper .dataTables_scrollBody").mCustomScrollbar({
        scrollButtons: {
            enable: true
        },
        theme: "dark-2"
    });

    $("#table_distribution_group_idu_id_wrapper .dataTables_scrollBody").mCustomScrollbar({
        scrollButtons: {
            enable: true
        },
        theme: "dark-2"
    });


    var locpathname = window.location.pathname;
    if (locpathname.lastIndexOf('/') + 1 < locpathname.length) {
        if (locpathname.substring(locpathname.lastIndexOf('/') + 1).indexOf('viewSetting') > -1) {
            customerSetting = true;

        } else {
            customerSetting = false;
        }


    } else {
        customerSetting = false;


    }

    if (customerSetting) {
        var customerid = null;

        //Another way gettign all Customer list?
        $.axs("../customer/sessionInfo.htm", {

        }, function(data) {
            var option = '<option value="">Select Customers</option>';
            console.log("sessionId " + data);
            console.log("all session data");
            console.log(data);



            option += '<option selected value="' + data + '">' + data + '</option>';
            $('#cust_name_search').html(option);


        });

        // on page loading distribution goup drop down
        $.axs(" ../customer/distributionGroup.htm", {}, function(data) {

            var option = '';
            option += '<option value="">Please Select..</option><option value="NEW">New</option>';
            $.each(data, function(i, l) {
                option += '<option value="' + l[0] + '">' + l[1] + '</option>';
            });
            $('#dgroup_gropdown').show();
            $('#dgroup_gropdown').html(option);

        });

        $(document).on("click", "#displayButton", function(e) {

            var tree = $.jstree.reference('#checkboxMenuTree');
            var checkedList = tree.get_checked(true);
            var optionsHtml = "";
            var opHtml = "";
            var optHtml = '';
            //var optHtml = "";		
            var temp = [];
            var unique;
            console.log(checkedList[0].original.companyId);
            console.log(checkedList[0].original['companyID']);
            //customerid=checkedList[0].original['companyId'];
            if (checkedList[0].original['companyId'] != undefined && checkedList[0].original['companyId'] != null) {
                customerid = checkedList[0].original['companyId'];
            }
            if (checkedList[0].original['companyID'] != undefined && checkedList[0].original['companyID'] != null) {
                customerid = checkedList[0].original['companyID'];
            }


            for (var i = 0; i < checkedList.length; i++) {
                var categoryName = checkedList[i].original.groupCategory;
                var levelName = checkedList[i].original.groupTypeLevelName;
                if (typeof categoryName !== "undefined") {
                    temp.push(checkedList[i].original.groupTypeLevelName + "-" + checkedList[i].original.groupTypeLevelID);
                }
                if (levelName == "floor" || levelName == "Floor") {
                    opHtml += "<option id=" + checkedList[i].original.groupId + ">" + checkedList[i].original.groupName + "</option>";
                }
                if (levelName == "Building" || levelName == "Building") {
                    if (checkedList.length > 0) {
                        $("#selectlabel").show();
                        $("#groupSelect").show();
                        $("#idtypes").show();
                        $("#idmaintypes").show();
                        optHtml += "<option value=" + checkedList[i].original.groupId + ">" + checkedList[i].original.groupName + "</option>";

                        /*
                        optHtml +=	'<div class="col-sm-12" style="padding: 9px;"><div class="col-sm-2"><input type="checkbox" class="SiteNumber"  value="'+checkedList[i].original.groupId+'" style="padding: 41px;"></div><div class="col-sm-10" style="text-align:left;">'+checkedList[i].original.groupName+'</div></div>'
                        */
                    } else {
                        //optHtml += "<option value="+ +">"+ +"</option>";	
                    }
                }
            }

            unique = temp.filter(function(itm, i, types) {
                return i == types.indexOf(itm);
            });

            for (var i = unique.length - 1; i >= 0; i--) { /*console.log(i);*/
                var temp_array = unique[i].split("-");
                optionsHtml += "<option value=" + temp_array[1] + ">" + temp_array[0] + "</option>";
            }


            $('#dropdown-menu-customer-site').html(optHtml);
            $('#dropdown-menu-customer-site').SumoSelect({
                triggerChangeCombined: false,
                placeholder: "Please Select",
                selectAll: false
            });
            $('#dropdown-menu-customer-site')[0].sumo.reload();
            console.log(optHtml);

        });



    } else {
    	
    	$('#DBgroup').on('shown.bs.modal', function () {
    		Distribtion_group_table(null);
    	});
    	
    	 $(document).on("click", "#DBgroup a[data-toggle='tab']", function() {
             var target = $(this).attr("href");
             if (target == "#gropDetails" && GLOBAL_VAL.dataBool == false) {
 				fetchDistributionGroupDevice(null);
 				GLOBAL_VAL.dataBool = true;
 			}
         });

        $("#displayButtonDistribution").click(function() {
            $.axs("../adapter/CostomerNames.htm", {}, function(data) {
                //console.log(data);
                var optionHtml = '<option value="">Please Select..</option>';
                $.each(data, function(i, l) {
                    optionHtml += '<option value="' + l[0] + '">' + l[1] + '</option>';
                });
                $('#costomer_name_distribution').html(optionHtml);
            });
        });


        $("#costomer_name_distribution").change(function() {


            var str1 = strings['j.spring.message.label.adddist'];
            var Customerid = $("#costomer_name_distribution option:selected").val();


            var url = "../customer/distributionGroup.htm"
            var dataObj = {
                "Customerid": Customerid
            }
            if (customerSetting) {
                url = "../customer/distributionGroup.htm"
                dataObj = {};
            } else {
                var costomerid = $("#costomer_name_distribution option:selected").val();
                url = "../adapter/distributionGroup.htm"
                    //url = "../adapter/distributionGroup.htm"
                    //var CustomerName = $("#costomer_name_distribution option:selected").val();
                    //dataObj = {"Customerid" : CustomerName}
            }



            $.ax(url, dataObj, null, 'post', "json", function(data) {
                var option = '';
                var option2 = '';
                console.log($(data));
                if ($(data).length == 0) {
                    //console.log(data);
                    option += '<option value="">Please Select..</option><option value="NEW">' + str1 + '</option>';

                    $('#dgroup_gropdown').html(option);
                } else {
                    option += '<option value="">Please Select..</option><option value="NEW">' + str1 + '</option>';
                    $.each(data, function(i, l) {

                        option += '<option value="' + l[0] + '">' + l[1] + '</option>';
                    });

                    $('#Remove').show();

                    $('#dgroup_gropdown').html(option);
                }
            }, function(error) {
                console.log("customer: " + Customerid)
                var option = '';
                if (!customerSetting) {
                    if (Customerid == null || Customerid == undefined) {
                        $.bizalert("Error getting distibution group.");
                    } else if (Customerid == "") {
                        //$.bizalert("Please select customer");
                    }
                    option += '<option value="">Please Select..</option><option value="NEW">' + str1 + '</option>';
                    $('#dgroup_gropdown').html(option);
                } else {
                    $.bizalert("Error getting distibution group.");
                }

            });

            var siteOption = '';
            if (Customerid == '') {
                $('#dgroup_gropdown_type_adapter').html(siteOption);
            } else {
                $.axs("../adapter/getsitenames.htm", {
                    "costomer_id": Customerid
                }, function(data) {

                    $.each(data, function(i, l) {
                        //siteOption += '<div class="col-sm-12" style="padding: 9px;"><div class="col-sm-2"><input type="checkbox" class="siteiddrop" id="site_id" name="site_id" value="'+l[0]+'" style="padding: 41px;"></div><div class="col-sm-10" style="text-align:left;">'+l[1]+'</div></div>'


                        siteOption += "<option value=" + l[0] + ">" + l[1] + "</option>";

                    });
                    //$('.dropdown-menu-sitenames-pannel').html(siteOption);
                    $('#dropdown-menu-customer-site').html(siteOption);
                    $('#dropdown-menu-customer-site').SumoSelect({
                        triggerChangeCombined: false,
                        placeholder: "Please Select",
                        selectAll: false
                    });
                    $('#dropdown-menu-customer-site')[0].sumo.reload();


                });
            }
        });

    }




    $('#dropdown-menu-customer-site').change(function(event) {


        if ($(this).val() != null) {
            for (var i = 0; i < $(this).val().length; i++) {
                if ($(this).val().length > 1000000) {
                    //alert($(this).val());
                    var $this = $(this);
                    $this[0].sumo.unSelectAll();
                    $.each(last_valid_selection, function(i, e) {
                        $this[0].sumo.selectItem($this.find('option[value="' + e + '"]').index());
                    });
                    console.log("test: " + JSON.stringify(last_valid_selection))
                } else {
                    last_valid_selection = $(this).val()[i];
                    //selected_siteids.push($(this).val()[i]);
                    console.log($(this).val()[i]);

                }
            }
        }

        if (customerSetting) {
            var dataObj = {
                "site_id": $(this).val()
            }
            fetchDistributionGroupAdapter(dataObj)
        } else {

            var cust_name = $('#costomer_name_distribution option:selected').val();
            var dataObj = {
                "site_id": $(this).val(),
                "cust_id": cust_name
            }
            fetchDistributionGroupAdapter(dataObj)

        }

    });

    function fetchDistributionGroupAdapter(data) {

        var option1 = '';
        var url = "../customer/DistributionGroupCaNames.htm";
        if (customerSetting) {
            url = "../customer/DistributionGroupCaNames.htm";
            console.log("url" + url)
        } else {
            url = "../adapter/DistributionGroupCaNames.htm";

        }
        console.log(data)
        $.axs(url, data, function(data) {
            if ($(data).length > 0) {

                $.each(data, function(i, l) {


                    //option1 += '<div class="col-sm-12" style="padding: 9px;"><div class="col-sm-2"><input type="checkbox" class="CustomerCAdrop"  value="'+l[2]+'" style="padding: 41px;"></div><div class="col-sm-10" style="text-align:left;">'+l[1]+'</div></div>'


                    option1 += "<option value=" + l[2] + ">" + l[1] + "</option>";
                });

                console.log(option1)
                $('#dropdown-menu-customer-cloud-adatpor').html(option1);
                $('#dropdown-menu-customer-cloud-adatpor').SumoSelect({
                    triggerChangeCombined: false,
                    placeholder: "Please Select",
                    selectAll: false
                });
                $('#dropdown-menu-customer-cloud-adatpor')[0].sumo.reload();


                $("#table_distribution_group_idu_id_final_window").empty();
                $("#table_distribution_group_pls_id_final_window").empty();
                $(".idu_table_view_name1").hide();
                $(".idu_table_view_name").hide();
                $("#final_add_update").hide();

            } else {
                $('#dgroup_gropdown_type_adapter').html('');

            }




        });


    }

    $('#dropdown-menu-customer-cloud-adatpor').change(function(event) {

        if ($(this).val() != null) {
            for (var i = 0; i < $(this).val().length; i++) {
                console.log("t " + $(this).val()[i]);
            }
        }
        fetchDistributionGroupDevice($(this).val());
    });

    function fetchDistributionGroupDevice(CAoptions) {
        var my_htmlPls = '';
        var my_htmlIDU = '';
        var my_htmlIDU2 = '';


        var my_dropdown = '';
        var DropDownVRF = "";
        var DropDownGHP = "";
        $('#final_add_update').show();
        $('.idu_table_view_name').show();
        $('.idu_table_view_name1').show();

        var Ca_id = $("#dgroup_gropdown_type_adapter option:selected").val();

        var customer_id_distributiongroup = $("#costomer_name_distribution option:selected").val();



        $.axs("../customer/DistributionGroupDropDown.htm", {
            "Cust_id": customer_id_distributiongroup
        }, function(data) {

            var GHPDisGroupOptions = "";
            var VRFDisGroupOptions = "";
            var typeVRF = data['VRF'];
            var typeGHP = data['GHP'];


            $.each(typeVRF, function(i, l) {


                VRFDisGroupOptions += "<option value=" + typeVRF[i][0] + " >" + typeVRF[i][1] + "</option>'";

            });
            $.each(typeGHP, function(i, l) {

                GHPDisGroupOptions += "<option value=" + typeGHP[i][0] + " >" + typeGHP[i][1] + "</option>'";
            });



            if (CAoptions != null) {
                $.axs("../customer/ShowDistributionGroupPls.htm", {
                    "Ca_id": CAoptions
                }, function(data) {

                    var NXdistributiongroup = data[0];
                    var EXdistributiongroup = data[1];




                    $.each(NXdistributiongroup, function(i, l) {



                        var optionHtml = GHPDisGroupOptions;

                        if (NXdistributiongroup[i][5] == "1") {
                            optionHtml = VRFDisGroupOptions;


                        }

                        var facilityId = NXdistributiongroup[i][1];
                        my_htmlPls += "<tr id=" + NXdistributiongroup[i][0] + " data-ficilityid=" + facilityId + "><td>" + NXdistributiongroup[i][3] + "</td><td>" + NXdistributiongroup[i][5] + "</td><td>" + NXdistributiongroup[i][4] + "</td><td>" +
                            "<select my_id='" + NXdistributiongroup[i][0] + "' id='pls" + NXdistributiongroup[i][3] + "' class='pls_type_id form-control'><option>Please Select</option>" + optionHtml + "</select></td></tr>";
                    });

                    $.each(EXdistributiongroup, function(i, l) {



                        var optionHtml = GHPDisGroupOptions;

                        if (EXdistributiongroup[i][5] == "1") {
                            optionHtml = VRFDisGroupOptions;


                        }
                        //	3,"[2:{08007B92000202000017}]",14,1,"PLC_CA6_01","1",14,"test1"
                        var facilityId = EXdistributiongroup[i][1];
                        my_htmlPls += "<tr id=" + EXdistributiongroup[i][0] + " data-ficilityid=" + facilityId + "><td>" + EXdistributiongroup[i][3] + "</td><td>" + EXdistributiongroup[i][5] + "</td><td>" + EXdistributiongroup[i][4] + "</td><td>" +
                            "<select my_id='" + EXdistributiongroup[i][0] + "' id='pls" + EXdistributiongroup[i][3] + "' class='pls_type_id2 form-control'>" + '<option value=' + EXdistributiongroup[i][6] + '>' + EXdistributiongroup[i][7] + '</option>' + "</select></td></tr>";
                    });




                    $("#table_distribution_group_pls_id_final_window_customer").html("<thead><tr class='greyGradient'><th class='text-center'>Port</th><th class='text-center'>Meter Type</th><th class='text-center'>Device Name</th><th class='text-center'>Distribution Group</th></tr></thead>" + my_htmlPls);
                    
                    if (GLOBAL_VAL.DistributeDataPulseDetailTable != null) {
                        GLOBAL_VAL.DistributeDataPulseDetailTable.destroy();
                    }
                    
                    if ($.fn.dataTable.isDataTable('#table_distribution_group_pls_id_final_window_customer')) {
                        GLOBAL_VAL.DistributeDataPulseDetailTable = $('#table_distribution_group_pls_id_final_window_customer').DataTable();
                    } else {
                        GLOBAL_VAL.DistributeDataPulseDetailTable = $('#table_distribution_group_pls_id_final_window_customer').DataTable({

                            "paging": false,
                            "filter": true,
                            "info": false,
                            "responsive": true,
                            "deferRender": true,
                            "scrollCollapse": false,
                            "scroller": true,
                            "stateSave": true,
                            "scrollY": GLOBAL_VAL.scrollPulseY,
                            "language": {
                                "searchPlaceholder": "Search"
                            },
                            "search": {
                                "caseInsensitive": false
                            }

                        });
                    }



                    $("#table_distribution_group_pls_id_final_window_customer_wrapper .dataTables_scrollBody").mCustomScrollbar({
                        scrollButtons: {
                            enable: true
                        },
                        theme: "dark-2"
                    });
                }, 'post');

                var optionHtmlEmptyDeviceType = '<option value="">Please Select..</option>';

                $.axs("../adapter/ShowDistributionGroupIdu.htm", {
                    "Ca_id": CAoptions,
                }, function(data) {
                    var NXdistributiongroup = data[0];
                    var EXdistributiongroup = data[1];

                    var other_Distribution_group_names = "";
                    var other_Distribution_group_names1 = "";
                    var existing_distribution_name = "";

                    $.each(NXdistributiongroup, function(i, l) {


                        if (NXdistributiongroup[i][3] == "VRF") {
                            var optionHtml1 = VRFDisGroupOptions;
                        }
                        //console.log(VRFDisGroupOptions);
                        var facilityId = NXdistributiongroup[i][5];
                        my_htmlIDU2 += "<tr id=" + NXdistributiongroup[i][0] + " data-ficilityid=" + facilityId + "><td>" + NXdistributiongroup[i][1] + "</td>" +
                            "<td>" + NXdistributiongroup[i][2] + "</td><td id='idu_type'>" + NXdistributiongroup[i][3] + "</td>" +
                            "<td id='dcolumn'>" +
                            "<select class='idu_type_id form-control' my_id='" + NXdistributiongroup[i][0] + "' id='" + i + "' style='';>" +
                            "<option>Please Select</option>" + optionHtml1 + "</select></td></tr>";

                    });

                    var Distribition_Group_id = '';
                    if (EXdistributiongroup.length != 0) {
                        $.each(EXdistributiongroup, function(i, l) {



                            //	23,"0-15-2","IDU_15_02","VRF","[2:{08007B92000202000002}]",12,"dist4



                            var facilityId = EXdistributiongroup[i][4];

                            my_htmlIDU += "<tr id=" + EXdistributiongroup[i][0] + " data-ficilityid=" + facilityId + "><td>" + EXdistributiongroup[i][1] + "</td>" +
                                "<td>" + EXdistributiongroup[i][2] + "</td><td id='idu_type'>" + EXdistributiongroup[i][3] + "</td>" +
                                "<td id='dcolumn'>" +
                                "<select class='idu_type_id2 form-control' my_id='" + EXdistributiongroup[i][0] + "'>" + '<option value=' + EXdistributiongroup[i][5] + '>' + EXdistributiongroup[i][6] + '</option>' + "</select></td>" +
                                "</tr>";

                        });
                    }
                    console.log("hai");
                    console.log(Distribition_Group_id);




                    $.each(EXdistributiongroup, function(i, l) {
                        var exiting_id = parseInt(EXdistributiongroup[i][4]);
                        console.log(typeVRF);
                        console.log(exiting_id);
                        $("#existing" + i).val(exiting_id);
                    });

                    $("#table_distribution_group_idu_id_final_window_customer").html("<thead><tr class='greyGradient'><th class='text-center'>S-link</th><th class='text-center'>Device Name</th><th class='text-center'>Ac Type</th><th class='text-center'>Distribution Group</th></tr></thead>"+ my_htmlIDU + my_htmlIDU2);

                    if (GLOBAL_VAL.DistributeDataDetailTable != null) {
                        GLOBAL_VAL.DistributeDataDetailTable.destroy();
                    }
                    
                    if ($.fn.dataTable.isDataTable('#table_distribution_group_idu_id_final_window_customer')) {
                        GLOBAL_VAL.DistributeDataDetailTable = $('#table_distribution_group_idu_id_final_window_customer').DataTable();
                    } else {
                        GLOBAL_VAL.DistributeDataDetailTable = $('#table_distribution_group_idu_id_final_window_customer').DataTable({

                            "paging": false,
                            "filter": true,
                            "info": false,
                            "responsive": true,
                            "deferRender": true,
                            "scrollCollapse": false,
                            "scroller": true,
                            "stateSave": true,
                            "scrollY": GLOBAL_VAL.scrollY2,
                            "language": {
                                "searchPlaceholder": "Search"
                            },
                            "search": {
                                "caseInsensitive": false
                            }

                        });
                    }



                    $("#table_distribution_group_idu_id_final_window_customer_wrapper .dataTables_scrollBody").mCustomScrollbar({
                        scrollButtons: {
                            enable: true
                        },
                        theme: "dark-2"
                    });



                    console.log(NXdistributiongroup);
                    console.log(EXdistributiongroup);


                    $.each(data, function(i, l) {
                        var optionHtml = GHPDisGroupOptions;
                        if (data[i][3] != null) {

                            if (data[i][3] == "VRF") {
                                optionHtml = VRFDisGroupOptions;
                            }
                            //console.log(VRFDisGroupOptions);
                            var facilityId = data[i][5];
                            my_htmlIDU += "<tr id=" + data[i][0] + " data-ficilityid=" + facilityId + "><td>" + data[i][1] + "</td><td>" + data[i][2] + "</td><td id='idu_type'>" + data[i][3] + "</td><td id='dcolumn'><select class='idu_type_id form-control' my_id='" + data[i][0] + "' id='" + i + "' style=><option>Please Select</option>" + optionHtml + "</select></td></tr>";

                        } else {
                            my_htmlIDU += "<tr id=" + data[i][0] + " data-ficilityid=" + facilityId + "><td>" + data[i][1] + "</td><td>" + data[i][2] + "</td><td id='idu_type'>" + data[i][3] + "</td><td id='dcolumn'><select class='idu_type_id form-control' my_id='" + data[i][0] + "' id='" + i + "' style=><option>Please Select</option>" + optionHtml + "</select></td></tr>";
                        }
                        $("#table_distribution_group_idu_id_final_window").html('<thead><tr class=greyGradient><th class=text-center>S-link</th><th class=text-center>Device Name</th><th class=text-center>Ac Type</th><th class=text-center>Distribution Group</th></tr></thead><tbody class="table-responsive_distribution_group table-striped">' + my_htmlIDU + '</tbody>');

                    });

                }, 'post');



                $('#final_add_update').show();
            } else {
                //$.bizalert("Please select Cloud Adaptor");
                //$("#table_distribution_group_idu_id_final_window_customer").empty();
                //$("#table_distribution_group_pls_id_final_window_customer").empty();
                $(".head_me").hide();


                $(".idu_table_view_name1").hide();

                $(".idu_table_view_name").hide();
                $("#final_add_update").hide();
                
                $("#table_distribution_group_idu_id_final_window_customer").html("<thead><tr class='greyGradient'><th class='text-center'>S-link</th><th class='text-center'>Device Name</th><th class='text-center'>Ac Type</th><th class='text-center'>Distribution Group</th></tr></thead>" + my_htmlIDU + my_htmlIDU2);

                if (GLOBAL_VAL.DistributeDataDetailTable != null) {
                    GLOBAL_VAL.DistributeDataDetailTable.destroy();
                }

                if ($.fn.dataTable.isDataTable('#table_distribution_group_idu_id_final_window_customer')) {
                    GLOBAL_VAL.DistributeDataDetailTable = $('#table_distribution_group_idu_id_final_window_customer').DataTable();
                } else {
                    GLOBAL_VAL.DistributeDataDetailTable = $('#table_distribution_group_idu_id_final_window_customer').DataTable({
                        "paging": false,
                        "filter": true,
                        "info": false,
                        "responsive": true,
                        "deferRender": true,
                        "scrollCollapse": false,
                        "scroller": true,
                        "stateSave": true,
                        "scrollY": GLOBAL_VAL.scrollY,
                        "language": {
                            "searchPlaceholder": "Search"
                        },
                        "search": {
                            "caseInsensitive": false
                        }

                    });
                }



                $("#table_distribution_group_idu_id_final_window_customer_wrapper .dataTables_scrollBody").mCustomScrollbar({
                    scrollButtons: {
                        enable: true
                    },
                    theme: "dark-2"
                });
                
                $("#table_distribution_group_pls_id_final_window_customer").html("<thead><tr class='greyGradient'><th class='text-center'>Port</th><th class='text-center'>Meter Type</th><th class='text-center'>Device Name</th><th class='text-center'>Distribution Group</th></tr></thead>" + my_htmlPls);
                
                if (GLOBAL_VAL.DistributeDataPulseDetailTable != null) {
                    GLOBAL_VAL.DistributeDataPulseDetailTable.destroy();
                }
                
                if ($.fn.dataTable.isDataTable('#table_distribution_group_pls_id_final_window_customer')) {
                    GLOBAL_VAL.DistributeDataPulseDetailTable = $('#table_distribution_group_pls_id_final_window_customer').DataTable();
                } else {
                    GLOBAL_VAL.DistributeDataPulseDetailTable = $('#table_distribution_group_pls_id_final_window_customer').DataTable({
                        "paging": false,
                        "filter": true,
                        "info": false,
                        "responsive": true,
                        "deferRender": true,
                        "scrollCollapse": false,
                        "scroller": true,
                        "stateSave": true,
                        "scrollY": GLOBAL_VAL.scrollPulseY,
                        "language": {
                            "searchPlaceholder": "Search"
                        },
                        "search": {
                            "caseInsensitive": false
                        }

                    });
                }



                $("#table_distribution_group_pls_id_final_window_customer_wrapper .dataTables_scrollBody").mCustomScrollbar({
                    scrollButtons: {
                        enable: true
                    },
                    theme: "dark-2"
                });
            }

        }, 'post');



    }

    /*
     * ==============================    overview  ===========================
     */

    function hideTrigger() {
        $('#new_group_name').hide();
        $('#dgroup_gropdown_type').hide();
        $('#Calculation_gropdown_type').hide();
        $('#Add').hide();
        $('.type').hide();
        $('.CalculationCode').hide();
        $('#Remove').show();
        //$('#Retrieve').hide();
        $('.bizButton-retrieve').show();
        $('.distribution_group_name').hide();


    }


    $("#dgroup_gropdown").change(function() {
        var value = $("#dgroup_gropdown option:selected").val();
        if (value == 'NEW') {


            $('#new_group_name').show();
            $('#dgroup_gropdown_type').show();
            $('#Calculation_gropdown_type').show();
            $('#Add').show();
            $('.type').show();
            $('.distribution_group_name').show();

            $('.CalculationCode').show();
            $('#Remove').hide();
            //$('#Retrieve').hide();
            $('.bizButton-retrieve').hide();

            $('#row2').css('margin-top: 0px;');
        } else if (value == '') {
            hideTrigger();
            $('#Remove').hide();
        } else {
            hideTrigger()

            var url = " ../customer/displayDistributiongroup1.htm"
            if (customerSetting) {
                url = " ../customer/displayDistributiongroup1.htm";
            } else {
                url = " ../adapter/displayDistributiongroup1.htm"
            }

            var d_groupid = $("#dgroup_gropdown option:selected").val();
            //console.log(d_groupid);
            $.axs(url, {
                    'Dgroupid': d_groupid
                },
                //					$.axs(" ../customer/displayDistributiongroup1.htm",{'Dgroupid':2},
                function(data) {
                    if (data != null && (data[0] == null || data[1] == null)) {
                        if (data[0] == null) {
                            $("#table_distribution_group_idu_id tbody").empty();
                        }

                        if (data[1] == null) {
                            $("#table_distribution_group_pls_id tbody").empty();
                        }
                    } else {
                        Distribtion_group_table(data);
                        //console.log(data)
                    }


                }, 'post');
        }
    });

    /*
     * ==============================    Details  ===========================
     */


    $(document).on('click', '#final_add_update', function() {

        if (customerSetting) {
            if (customerid == '' || customerid == 'undefined' || customerid == null) {
                $.bizalert("Please choose Customer");
            }
        } else {
            customerid = $("#costomer_name_distribution option:selected").val();
        }
        var paramArray = [];
        $('.idu_type_id option:selected').each(function(index) {
            if ($(this).val() != "Please Select") {
                var $tr = $(this).parent().parent().parent();
                var iduId = $tr.attr("id"),
                    disGroupId = $(this).val(),
                    facilityId = $tr.attr("data-ficilityid");

                paramArray.push({
                    "id": iduId,
                    "dist_grp": disGroupId,
                    "device": 0,
                    "facilityId": facilityId
                });
            }
        })

        if (paramArray.length == 0) {
            $.bizalert("Please choose distribution group for indoorunits also");
            return false;
        }
        $('.pls_type_id option:selected').each(function(index) {
            if ($(this).val() != "Please Select") {
                var $tr = $(this).parent().parent().parent();
                var plsId = $tr.attr("id"),
                    disGroupId = $(this).val(),
                    facilityId = $tr.attr("data-ficilityid");

                paramArray.push({
                    "id": plsId,
                    "dist_grp": disGroupId,
                    "device": 1,
                    "facilityId": facilityId
                });
            }
        });

        ;

        console.log(paramArray);


        var siteId = $("#dgroup_gropdown2 option:selected").val();

        var data = {
            "AJAXREQUEST": true,
            "avoidCacheDate": new Date().getTime(),
            "json_request": JSON.stringify({
                "fileType": customerid,
                "idType": JSON.stringify({
                    'dgroupplsiduList': paramArray
                })
            })
        };
        /*
        if(customerSetting){
      		data = {"AJAXREQUEST": true, "avoidCacheDate": new Date().getTime(),"json_request": JSON.stringify({"fileType":customerid,"idType":JSON.stringify({'dgroupplsiduList':paramArray})})};
		}else{
      		data = {"AJAXREQUEST": true, "avoidCacheDate": new Date().getTime(),"json_request": JSON.stringify({"fileType":customerid,"idType":JSON.stringify({'dgroupplsiduList':paramArray})})};
			
		}
		*/

        $.ajax({
            url: "../adapter/updateDistributionGroupIduPls.htm",
            cache: true,
            type: 'POST',
            data: data,
            async: true,
            beforeSend: function(request) {
                getToken(request);
            },
            success: function(data, success) {
                //console.log(data);
                $.bizalert(JSON.parse(data).errorMessage);
            },
            error: function(jqxhr, textStatus, error) {
                console.log("error", arguments);
            },
            complete: function(jqxhr, textStatus) {
                console.log("complete", arguments);
            }
        });




    })

    $("#site_name_topolgy").change(function() {

        var site_id = $("#site_name_topolgy option:selected").val();
        var cust_id = $("#costomer_name option:selected").val();
        var option4 = '<option value="">Please Select..</option>'
        if (site_id === '' || cust_id === '') {

            $('#topology_ca_name').html(option4);
        } else {
            $.axs(" ../customer/CaNames.htm", {
                "site_id": site_id,
                "cust_id": cust_id
            }, function(data) {
                if ($(data).length > 0) {
                    $.each(data, function(i, l) {
                        option4 += '<option topology_ad_id="' + l[2] + '" value="' + l[0] + '">' + l[1] +
                            '</option>';
                    });

                    $('#topology_ca_name').html(option4);
                } else {

                    $('#topology_ca_name').html('');

                }

            });
        }

    });

    $("#ditribution_group").click(
        function() {
            var option = '';
            var option1 = '';

            $.axs(" ../customer/costomerdata.htm", {}, function(data) {
                if ($(data).length != 0) {
                    option1 += '<option value="">Select..</option>';

                    $.each(data, function(i, l) {
                        option1 += '<option value="' + l[0] + '">' +
                            l[1] + '</option>';

                    });
                    $('#costomer_name').html(option1);

                }

            });

        });


    /*$("#costomer_name").change(function() {

    			var costomer_id = $("#costomer_name option:selected").val();
    			option = '<option value="">Please Select..</option>'
    			if(costomer_id != ''){
    				$.axs(" ../customer/getsitenames.htm", {
    					"costomer_id" : costomer_id
    				}, function(data) {
    					
    					//console.log(data);
    					if ($(data).length != 0) {
    						
    						$.each(data, function(i, l) {
    							option += '<option value="'+ l[0] + '">' + l[1]+'</option>';
    						});

    						$('#site_name_topolgy').html(option);
    					}
    					else{
    						
    						$('#site_name_topolgy').html('');
    						
    					}
    				});

    			}else{
    				
    				$('#site_name_topolgy').html(option);
    				$('#topology_ca_name').html(option);
    			}



    		});
    */
    function Distribtion_group_table(data) {
        var my_distribution_idu_table = '';
        var my_distribution_pls_table = '';
        if (data != null) {
            var Distribtion_indoorUnit_PLS = data;
            var my_distribution_idu = Distribtion_indoorUnit_PLS[1];
            var my_distribution_pls = Distribtion_indoorUnit_PLS[0];
            $.each(my_distribution_idu, function(i, l) {
                my_distribution_idu_table += "<tr><td>" + my_distribution_idu[i]['deviceName'] + "</td><td>" + my_distribution_idu[i]['siteName'] + "</td> <td>" + my_distribution_idu[i]['s_linkaddress'] + "</td><td>" + my_distribution_idu[i]['caName'] + "</td></tr>";
            });
        }

        if (GLOBAL_VAL.DistributeDataTable != null) {
            GLOBAL_VAL.DistributeDataTable.destroy();
        }

        $("#table_distribution_group_pls_id").html("<thead><tr class='greyGradient'><th class='text-center'>Device Name</th><th class='text-center'>Site</th><th class='text-center'>Device S-Link Address</th><th class='text-center'>CA Name</th></tr></thead>" + my_distribution_idu_table);

        if ($.fn.dataTable.isDataTable('#table_distribution_group_pls_id')) {
            GLOBAL_VAL.DistributeDataTable = $('#table_distribution_group_pls_id').DataTable();
        } else {
            GLOBAL_VAL.DistributeDataTable = $('#table_distribution_group_pls_id').DataTable({

                "paging": false,
                "filter": true,
                "info": false,
                "responsive": true,
                "deferRender": true,
                "scrollCollapse": false,
                "scroller": true,
                "stateSave": true,
                "scrollY": GLOBAL_VAL.scrollY,
                "language": {
                    "searchPlaceholder": "Search"
                },
                "search": {
                    "caseInsensitive": false
                }

            });
        }



        $("#table_distribution_group_pls_id_wrapper .dataTables_scrollBody").mCustomScrollbar({
            scrollButtons: {
                enable: true
            },
            theme: "dark-2"
        });

        if (data != null) {
            $.each(my_distribution_pls, function(i, l) {

                my_distribution_pls_table += "<tr><td>" + my_distribution_pls[i]['meterName'] + "</td> <td>" + my_distribution_pls[i]['siteName'] + "</td> <td>" + my_distribution_pls[i]['portnumber'] + "</td><td>" + my_distribution_pls[i]['caName'] + "</td></tr>";
                console.log(my_distribution_pls);

            });
        }

        if (GLOBAL_VAL.DistributeDataPulseTable != null) {
            GLOBAL_VAL.DistributeDataPulseTable.destroy();
        }

        $("#table_distribution_group_idu_id").html("<thead><tr class='greyGradient'><th class='text-center'>Device Name</th><th class='text-center'>Site</th><th class='text-center'>Port</th><th class='text-center'>CA Name</th></tr></thead>" + my_distribution_pls_table);


        if ($.fn.dataTable.isDataTable('#table_distribution_group_idu_id')) {
            GLOBAL_VAL.DistributeDataPulseTable = $('#table_distribution_group_idu_id').DataTable();
        } else {
            GLOBAL_VAL.DistributeDataPulseTable = $('#table_distribution_group_idu_id').DataTable({

                "paging": false,
                "filter": true,
                "info": false,
                "responsive": true,
                "deferRender": true,
                "scrollCollapse": false,
                "scroller": true,
                "stateSave": true,
                "scrollY": GLOBAL_VAL.scrollPulseY,
                "language": {
                    "searchPlaceholder": "Search"
                },
                "search": {
                    "caseInsensitive": false
                }

            });
        }

        $("#table_distribution_group_idu_id_wrapper .dataTables_scrollBody").mCustomScrollbar({
            scrollButtons: {
                enable: true
            },
            theme: "dark-2"
        });
    }

    $("#dgroup_gropdown2").change(
        function() {
            var site_name = $('#dgroup_gropdown2 option:selected').val();
            var cust_name = $('#costomer_name_distribution option:selected').val();
            var option1 = '<option value="">Please Select..</option>'
            if (site_name === '' || cust_name === '') {
                $('#dgroup_gropdown_type_adapter').html(option1);
            } else {

                $.axs(" ../customer/CaNames.htm", {
                    "site_id": site_name,
                    "cust_id": cust_name
                }, function(data) {
                    if ($(data).length > 0) {

                        $.each(data, function(i, l) {
                            option1 += '<option value="' + l[2] + '">' +
                                l[1] + '</option>';

                        });
                        $('#dgroup_gropdown_type_adapter').html(option1);
                    } else {
                        $('#dgroup_gropdown_type_adapter').html('');

                    }

                });
            }

        });


    $(document).on('change', '.idu_type_id', function() {
        var idu_id_values = '';
        var my_id = this.id;
        var my_value = $('#' + my_id).val();

        if (jQuery.inArray($('#' + my_id).attr('my_id'), idu_id_values) != -1) {
            $.bizalert("Previous Data Selected has been selected");
        }

    })

    $(document).on('change', '.pls_type_id ', function() {
        var pls_id_values = '';
        var my_id = this.id;
        var my_value = $('#' + my_id).val();
        if (jQuery.inArray($('#' + my_id).attr('my_id'), pls_id_values) != -1) {
            $.bizalert("Previous Data Selected has been changed");
        }
    })


    $("#Add").click(function() {
        var option = '';
        var DG_GroupName = $('#new_group_name').val();
        var distribution_group_type_div = $("#dgroup_gropdown_type option:selected").val();

        var distribution_group_cal_div = $("#Calculation_gropdown_type option:selected").val();

        var url = "../customer/addDistributionGroup.htm";
        var dataObj = {
            "DG_GroupName": DG_GroupName,
            "type": distribution_group_type_div,
            "cal": distribution_group_cal_div
        };
        if (customerSetting) {
            url = "../customer/addDistributionGroup.htm"

        } else {
            url = "../adapter/addDistributionGroup.htm"
                //var CustomerName = $("#cust_name_search option:selected").val();
            var CustomerName = $("#costomer_name_distribution option:selected").val();

            dataObj = {
                "DG_GroupName": DG_GroupName,
                "type": distribution_group_type_div,
                "cal": distribution_group_cal_div,
                "CustomerName": CustomerName
            }

        }


        if (DG_GroupName !== '' && distribution_group_cal_div !== '' && distribution_group_type_div !== '') {
            console.log("cus:" + CustomerName)
            var validated = false
            if (CustomerName != undefined) {
                if (CustomerName !== '') {
                    validated = true;
                }
            } else {
                validated = true;
            }

            if (validated) {
                $.axs(url,
                    dataObj,
                    function(data) {
                        if (data.errorMessage == "SUCCESS") {
                            $.bizalert(" Distribution Group Added successfully");

                            hideTrigger();
                            var url = "../customer/distributionGroup.htm";
                            var dataObj = {};
                            if (customerSetting) {
                                url = "../customer/distributionGroup.htm"
                                dataObj = {};
                            } else {
                                url = "../customer/distributionGroup.htm"
                                    //url = "../adapter/distributionGroup.htm"
                                    //var CustomerName = $("#costomer_name_distribution option:selected").val();
                                console.log("customername1: " + CustomerName)
                                    //dataObj = {"Customerid" : CustomerName}
                            }


                            $.axs(url, dataObj, function(data) {
                                console.log(data + "mdklsmakl");
                                option += '<option value="">Please Select..</option><option value="NEW">New</option>';
                                $.each(data, function(i, l) {
                                    option += '<option value="' + l[0] + '">' + l[1] + '</option>';
                                });
                                $('#dgroup_gropdown').show();
                                $('#dgroup_gropdown').html(option);

                                load_latestDistribution();

                            });



                        } else if (data.errorMessage == "DUPLICATE") {

                            $('#new_group_name').hide();
                            $('#dgroup_gropdown_type').hide();
                            $('#Calculation_gropdown_type').hide();
                            $('#Add').hide();
                            $('.type').hide();
                            $('#Remove').show();
                            $('.bizButton-retrieve').show();
                            $('.distribution_group_name').hide();
                            $('.CalculationCode').hide();
                            var dataObj = {};
                            var url = "../customer/distributionGroup.htm";
                            if (customerSetting) {
                                url = "../customer/distributionGroup.htm"
                                dataObj = {};
                            } else {
                                url = "../customer/distributionGroup.htm"
                                    //url = "../adapter/distributionGroup.htm"
                                    //var CustomerName = $("#costomer_name_distribution option:selected").val();
                                console.log("customername: " + CustomerName)
                                    //dataObj = {"Customerid" : CustomerName}
                            }

                            $.axs(url, dataObj, function(data) {
                                console.log(data + "mdklsmakl");
                                option += '<option value="">Please Select..</option><option value="NEW">NEW</option>';
                                $.each(data, function(i, l) {
                                    option += '<option value="' + l[0] + '">' + l[1] + '</option>';
                                });
                                $('#dgroup_gropdown').show();
                                $('#dgroup_gropdown').html(option);
                            });


                            $.bizalert("DistributionGroup Name  already exits");
                        }




                    });




            } else {

                $.bizalert("Please select customer");
            }

        } else {
            $.bizalert(" Distribution Group name should not be empty! and please select remaining  options");

        }
    });




    $("#Remove").click(function() {
        var option = '';
        var d_groupid = $("#dgroup_gropdown option:selected").val();

        var url = "../customer/deleteDistributionGroup.htm"
        var dataObj = {
            "d_groupid": d_groupid
        }
        var method = 'get'
        if (customerSetting) {
            url = "../customer/deleteDistributionGroup.htm"
            dataObj = {
                "d_groupid": d_groupid
            };
            method = 'get';
        } else {
            var costomerid = $("#costomer_name_distribution option:selected").val();
            url = "../adapter/deleteDistributionGroup.htm"
            var CustomerName = $("#costomer_name_distribution option:selected").val();
            dataObj = {
                "d_groupid": d_groupid,
                "costomerid": CustomerName
            }
            method = 'post';
        }

        var validated = false
        if (CustomerName != undefined) {
            if (CustomerName !== '') {
                validated = true;
            }
        } else {
            validated = true;
        }




        if (d_groupid !== null && validated) {
            console.log("dg:" + d_groupid)
            $.axs(url, dataObj,
                function(data) {
                    if (data.errorMessage == "SUCCESS") {


                        var url = "../customer/distributionGroup.htm"
                        var dataObj = {
                            "Customerid": CustomerName
                        }
                        if (customerSetting) {
                            url = "../customer/distributionGroup.htm"
                            dataObj = {};
                        } else {
                            var costomerid = $("#costomer_name_distribution option:selected").val();
                            url = "../customer/distributionGroup.htm"
                                //url = "../adapter/distributionGroup.htm"
                                //var CustomerName = $("#costomer_name_distribution option:selected").val();
                                //dataObj = {"Customerid" : CustomerName}
                        }

                        $.axs(url, dataObj, function(data) {
                            console.log(data);
                            option += '<option value="">Please Select..</option><option value="NEW">NEW</option>';
                            $.each(data, function(i, l) {
                                option += '<option value="' + l[0] + '">' + l[1] + '</option>';
                            });
                            $('#dgroup_gropdown').html(option);
                        });
                        load_latestDistribution();

                        $('.idu_table_view_name').hide();
                        $.bizalert("Distribution group has been deleted success fully!");

                        $("#table_distribution_group_idu_id").empty();
                        $("#table_distribution_group_pls_id").empty();
                    } else {
                        $.bizalert("deleting distribution group has been failed!");
                    }

                }, method);




        }
    });

});




function load_latestDistribution() {

    var CAoptions = $('.CustomerCAdrop:checked').map(function(_, el) {
        return $(el).val();
    }).get();
    var my_htmlPls = '';
    var my_htmlIDU = '';
    var my_htmlIDU2 = '';


    var my_dropdown = '';
    var DropDownVRF = "";
    var DropDownGHP = "";
    $('#final_add_update').show();
    $('.idu_table_view_name').show();
    $('.idu_table_view_name1').show();

    var Ca_id = $("#dgroup_gropdown_type_adapter option:selected").val();

    var customer_id_distributiongroup = $("#costomer_name_distribution option:selected").val();



    $.axs("../customer/DistributionGroupDropDown.htm", {
        "Cust_id": customer_id_distributiongroup
    }, function(data) {

        var GHPDisGroupOptions = "";
        var VRFDisGroupOptions = "";
        var typeVRF = data['VRF'];
        var typeGHP = data['GHP'];


        $.each(typeVRF, function(i, l) {


            VRFDisGroupOptions += "<option value=" + typeVRF[i][0] + " >" + typeVRF[i][1] + "</option>'";

        });
        $.each(typeGHP, function(i, l) {

            GHPDisGroupOptions += "<option value=" + typeGHP[i][0] + " >" + typeGHP[i][1] + "</option>'";
        });



        if (CAoptions.length != 0) {
            $.axs("../customer/ShowDistributionGroupPls.htm", {
                "Ca_id": CAoptions
            }, function(data) {

                var NXdistributiongroup = data[0];
                var EXdistributiongroup = data[1];




                $.each(NXdistributiongroup, function(i, l) {



                    var optionHtml = GHPDisGroupOptions;

                    if (NXdistributiongroup[i][5] == "1") {
                        optionHtml = VRFDisGroupOptions;


                    }

                    var facilityId = NXdistributiongroup[i][1];
                    my_htmlPls += "<tr id=" + NXdistributiongroup[i][0] + " data-ficilityid=" + facilityId + "><td>" + NXdistributiongroup[i][3] + "</td><td>" + NXdistributiongroup[i][5] + "</td><td>" + NXdistributiongroup[i][4] + "</td><td>" +
                        "<select my_id='" + NXdistributiongroup[i][0] + "' id='pls" + NXdistributiongroup[i][3] + "' class='pls_type_id form-control'><option>Please Select</option>" + optionHtml + "</select></td></tr>";
                });

                $.each(EXdistributiongroup, function(i, l) {



                    var optionHtml = GHPDisGroupOptions;

                    if (EXdistributiongroup[i][5] == "1") {
                        optionHtml = VRFDisGroupOptions;


                    }
                    //	3,"[2:{08007B92000202000017}]",14,1,"PLC_CA6_01","1",14,"test1"
                    var facilityId = EXdistributiongroup[i][1];
                    my_htmlPls += "<tr id=" + EXdistributiongroup[i][0] + " data-ficilityid=" + facilityId + "><td>" + EXdistributiongroup[i][3] + "</td><td>" + EXdistributiongroup[i][5] + "</td><td>" + EXdistributiongroup[i][4] + "</td><td>" +
                        "<select my_id='" + EXdistributiongroup[i][0] + "' id='pls" + EXdistributiongroup[i][3] + "' class='pls_type_id2 form-control'>" + '<option value=' + EXdistributiongroup[i][6] + '>' + EXdistributiongroup[i][7] + '</option>' + "</select></td></tr>";
                });




                $("#table_distribution_group_pls_id_final_window_customer").html("<thead><tr class='greyGradient'><th class='text-center'>Port</th><th class='text-center'>Meter Type</th><th class='text-center'>Device Name</th><th class='text-center'>Distribution Group</th></tr></thead>" + '<tbody id="tbody_div_pls">' + my_htmlPls + '</tbody>');
            }, 'post');

            var optionHtmlEmptyDeviceType = '<option value="">Please Select..</option>';

            $.axs("../adapter/ShowDistributionGroupIdu.htm", {
                "Ca_id": CAoptions,
            }, function(data) {
                var NXdistributiongroup = data[0];
                var EXdistributiongroup = data[1];

                var other_Distribution_group_names = "";
                var other_Distribution_group_names1 = "";
                var existing_distribution_name = "";

                $.each(NXdistributiongroup, function(i, l) {


                    if (NXdistributiongroup[i][3] == "VRF") {
                        var optionHtml1 = VRFDisGroupOptions;
                    }
                    //console.log(VRFDisGroupOptions);
                    var facilityId = NXdistributiongroup[i][5];
                    my_htmlIDU2 += "<tr id=" + NXdistributiongroup[i][0] + " data-ficilityid=" + facilityId + "><td>" + NXdistributiongroup[i][1] + "</td>" +
                        "<td>" + NXdistributiongroup[i][2] + "</td><td id='idu_type'>" + NXdistributiongroup[i][3] + "</td>" +
                        "<td id='dcolumn'>" +
                        "<select class='idu_type_id dgroup_gropdown_cloud_adapter form-control' my_id='" + NXdistributiongroup[i][0] + "' id='" + i + "' style=>" +
                        "<option>Please Select</option>" + optionHtml1 + "</select></td></tr>";

                });

                var Distribition_Group_id = '';
                if (EXdistributiongroup.length != 0) {
                    $.each(EXdistributiongroup, function(i, l) {



                        //	23,"0-15-2","IDU_15_02","VRF","[2:{08007B92000202000002}]",12,"dist4



                        var facilityId = EXdistributiongroup[i][4];

                        my_htmlIDU += "<tr id=" + EXdistributiongroup[i][0] + " data-ficilityid=" + facilityId + "><td>" + EXdistributiongroup[i][1] + "</td>" +
                            "<td>" + EXdistributiongroup[i][2] + "</td><td id='idu_type'>" + EXdistributiongroup[i][3] + "</td>" +
                            "<td id='dcolumn'>" +
                            "<select class='idu_type_id2 dgroup_gropdown_cloud_adapter form-control' my_id='" + EXdistributiongroup[i][0] + " style= >" + '<option value=' + EXdistributiongroup[i][5] + '>' + EXdistributiongroup[i][6] + '</option>' + "</select></td>" +
                            "</tr>";

                    });
                }
                console.log("hai");
                console.log(Distribition_Group_id);




                $.each(EXdistributiongroup, function(i, l) {
                    var exiting_id = parseInt(EXdistributiongroup[i][4]);
                    console.log(typeVRF);
                    console.log(exiting_id);
                    $("#existing" + i).val(exiting_id);
                });

                $("#table_distribution_group_idu_id_final_window_customer").html("<thead><tr class='greyGradient'><th class='text-center'>S-link</th><th class='text-center'>Device Name</th><th class='text-center'>Ac Type</th><th class='text-center'>Distribution Group</th></tr></thead>" + '<tbody id="tbody_div_idu">' + my_htmlIDU + '' + my_htmlIDU2 + '</tbody>');



                console.log(NXdistributiongroup);
                console.log(EXdistributiongroup);


                $.each(data, function(i, l) {
                    var optionHtml = GHPDisGroupOptions;
                    if (data[i][3] != null) {

                        if (data[i][3] == "VRF") {
                            optionHtml = VRFDisGroupOptions;
                        }
                        //console.log(VRFDisGroupOptions);
                        var facilityId = data[i][5];
                        my_htmlIDU += "<tr id=" + data[i][0] + " data-ficilityid=" + facilityId + "><td>" + data[i][1] + "</td><td>" + data[i][2] + "</td><td id='idu_type'>" + data[i][3] + "</td><td id='dcolumn'><select class='idu_type_id form-control' my_id='" + data[i][0] + "' id='" + i + "' style=><option>Please Select</option>" + optionHtml + "</select></td></tr>";

                    } else {
                        my_htmlIDU += "<tr id=" + data[i][0] + " data-ficilityid=" + facilityId + "><td>" + data[i][1] + "</td><td>" + data[i][2] + "</td><td id='idu_type'>" + data[i][3] + "</td><td id='dcolumn'><select class='idu_type_id form-control' my_id='" + data[i][0] + "' id='" + i + "' style=><option>Please Select</option>" + optionHtml + "</select></td></tr>";
                    }
                    $("#table_distribution_group_idu_id_final_window").html('<thead><tr class=greyGradient><th class=text-center>S-link</th><th class=text-center>Device Name</th><th class=text-center>Ac Type</th><th class=text-center>Distribution Group</th></tr></thead><tbody class="table-responsive_distribution_group table-striped">' + my_htmlIDU + '</tbody>');

                });

            }, 'post');



            $('#final_add_update').show();
        } else {
            //$.bizalert("Please select Cloud Adaptor");
            $("#table_distribution_group_idu_id_final_window_customer").empty();
            $("#table_distribution_group_pls_id_final_window_customer").empty();
            $(".head_me").hide();


            $(".idu_table_view_name1").hide();

            $(".idu_table_view_name").hide();
            $("#final_add_update").hide();


        }

    }, 'post');
}