/**
 * 
 */
$(document).ready(function() {
	   $(window).load( function(){

	        $('#tablebody').mCustomScrollbar({               
	            scrollButtons:{enable:true},
	            advanced:{updateOnContentResize: true},
	            autoHideScrollbar:true,
	            theme: "dark-2"

	        });

	    });

    $("#displayButtonRetrive").click(
        function() {
            var option1 = '';
            var option = '';

            $.axs("../ca_data/listcustomers_adapters.htm", {}, function(data) {
                if ($(data).length != 0) {
                    option1 += '<option value="">Please Select..</option>';
                    $.each(data, function(i, l) {
                        option1 += '<option value="' + l[0] + '">' + l[1] + '</option>';

                    });

                    $('#costomer_name').html(option1);

                }

            });

        });

    $("#site_name_topolgy").change(function() {

        var site_id = $("#site_name_topolgy option:selected").val();
        var cust_id = $("#costomer_name option:selected").val();
        var option4 = '<option value="">Please Select..</option>'
        if (site_id === '' || cust_id === '') {

            $('#topology_ca_name').html(option4);
        } else {


            $.axs("../adapter/CaNames.htm", {
                "site_id": site_id,
                "cust_id": cust_id
            }, function(data) {

                console.log($(data).length);


                if ($(data).length > 0) {
                    $.each(data, function(i, l) {
                        option4 += '<option topology_ad_id="' + l[2] + '" value="' + l[0] + '">' + l[1] + '</option>';
                    });

                    $('#topology_ca_name').html(option4);
                } else {

                    $('#topology_ca_name').html('');

                }

            });
        }
        
    });



    $("#costomer_name").change(function() {

        var costomer_id = $("#costomer_name option:selected").val();
        option = '<option value="">Please Select..</option>'
        if (costomer_id != '') {
            $.axs("../adapter/getsitenames.htm", {
                "costomer_id": costomer_id
            }, function(data) {

                console.log(data);
                if ($(data).length != 0) {

                    $.each(data, function(i, l) {
                        option += '<option value="' + l[0] + '">' + l[1] + '</option>';
                    });

                    $('#site_name_topolgy').html(option);
                } else {

                    $('#site_name_topolgy').html('');

                }
            });

        } else {

            $('#site_name_topolgy').html(option);
            $('#topology_ca_name').html(option);
        }

    });

    //TopologyRetrieval
    $("#TopologyRetrieve").click(function() {


        var my_htmlIDU = '';
        var my_htmlODU = '';
        var my_htmlPLS = '';

        var costomer_id = $("#costomer_name option:selected").val();
        var site_id = $("#site_name_topolgy option:selected").val();
        var adapter_id = $("#topology_ca_name option:selected").val();
        var cloud_adapter_id = $("#topology_ca_name option:selected").attr("topology_ad_id");
        var facility_id = $("#topology_ca_name option:selected").val();

        $.axs("../adapter/RetrieveTopolgy.htm", {
            "site_id": site_id,
            "cloud_adapter_id": cloud_adapter_id,
            "costomer_id": costomer_id,
            "adapter_id": adapter_id,
            "check_existing": 1

        }, function(data1) {

            console.log(data1);


            if ($(data1[0]).length > 0 || $(data1[1]).length > 0 || $(data1[2]).length > 0) {

               // console.log("asasa");

                $('.status').attr('id', 'update');
                $('.status').html("Update");

                $.each(data1[0], function(i, j) {
                    var flag = data1[0][i][1];
                    var iduflag = '';
                    if (flag == null) {
                        iduflag = 'Main'
                    } else {
                        iduflag = 'Sub'
                    }

                    //console.log(data1[1][i].mainIduAddress);
                    my_htmlIDU += "<tr  class='loop_td' loop_numbers='" + i + "' id='" + "IDU" + "'><td id='32'>" + data1[0][i][0] + "</td><td>IDU</td>" +
                        "<td>" + iduflag + "</td><td>" + data1[0][i][2] + "</td>" +
                        "<td class='nr'><input class='device_names_class toplogy-cell'  type='text'  value='" + data1[0][i][3] + "'></td>" +
                        "<td>--</td><td >" + data1[0][i][4] + "</td><td id='hide_td'>" + data1[0][i][5] + "</td></tr>";

                });
                $.each(data1[1], function(i, j) {

                    my_htmlODU += "<tr  class='loop_td' loop_numbers='" + i + "' id='" + "ODU" + "'><td id='32'>" + data1[1][i][0] + "</td><td>" + "ODU" + "</td><td>---</td><td>" + data1[1][i][1] + "</td>" +
                        "<td class='nr'><input class='device_names_class toplogy-cell'  type='text'  value='" + data1[1][i][2] + "'/></td>" +
                        "<td>--</td>	<td>" + data1[1][i][3] + "</td>" +
                        "	<td id='hide_td'>" + data1[1][i][4] + "</td></tr>";

                });
                $.each(data1[2], function(i, j) {
                    my_htmlPLS += "<tr  class='loop_td' loop_numbers='" + i + "' id='" + "PLS" + "'><td id='32'>---</td><td>" + "PLS" + "</td><td>---</td><td></td><td class='nr'><input class='device_names_class toplogy-cell'  type='text'  value='" + data1[2][i][1] + "'></td><td>" + data1[2][i][2] + "</td><td>" + data1[2][i][3] + "</td><td id='hide_td'>" + data1[2][i][4] + "</td></tr>";
                });


                $("#table_topology_id1").html('<tbody id="tbody_div4">' + my_htmlIDU + '' + my_htmlODU + '' + my_htmlPLS + '</tbody>');
                


            } else {
            	$('.status').attr('id', 'topology_confirm');
                $('.status').html("Confirm");
                
                $.axs("../adapter/RetrieveTopolgy.htm", {
                    "site_id": site_id,
                    "adapter_id": adapter_id,
                    "cloud_adapter_id": cloud_adapter_id,
                    "costomer_id": costomer_id,
                    "check_existing": 0
                        //"check_existing" :0,

                }, function(data) {

                    console.log("platform data"+data);
                    var oduParent = null;
                    var oduCategory = null;
                    Odu_Parent_Facilityds = [];
                    
                    
                    //pulse_json.push({

                    $.each(data[1], function(i, j) {
                    	console.log(data[1][i]);
                    	
                    	if(data[1][i].category=="AC-VRF")
                    	{
                    		oduCategory = "VRF";
                    	}
                    	if(data[1][i].category=="AC-GHP")
                    	{
                    		oduCategory = "GHP";
                    	}
                    	if(data[1][i].category=="AC-PAC")
                    	{
                    		oduCategory = "PAC";
                    	}
                    	
                    	
                        if (data[1][i].refrigCircuitGroupOduId != '0') {
                            var child_refrig_circuit = data[1][i].refrigCircuitId;
                            //console.log(child_refrig_circuit);
                            $.each(data[1], function(a, b) {
                                //consoel.log(data[1][a].refrig_circuit);
                                if (data[1][a].refrigCircuitId === child_refrig_circuit && data[1][a].adapterid === data[1][i].adapterid && data[1][a].connectionNumber === data[1][i].connectionNumber) {

                                    if (data[1][a].refrigCircuitGroupOduId === '0') {
                                        oduParent = data[1][a].facilityId;

                                    }
                                }

                            })

                        } else {
                            Odu_Parent_Facilityds.push({
                                "circuit_no": data[1][i].refrigCircuitId,
                                "facility_id": data[1][i].facilityId,
                                "connectionNumber": data[1][i].connectionNumber
                            })
                            oduParent = null;
                        }
                        my_htmlODU += "<tr  class='loop_td' loop_numbers='" + i + "' id='" + data[1][i].type + "'><td id='32'>" + data[1][i].s_link + "</td><td>" + data[1][i].type + "</td><td>---</td><td>" + data[1][i].model + "</td><td class='nr'>" +
                        "<input class='device_names_class toplogy-cell'  type='text'  value=''/></td><td>---</td><td>" + data[1][i].facilityId + "</td><td id='hide_td'>" + data[1][i].connectionType + "</td><td id='hide_td'>" + data[1][i].connectionNumber + "</td>" +
                        "<td id='hide_td'>" + data[1][i].refrigCircuitGroupOduId + "</td><td id='hide_td'>" + data[1][i].refrigCircuitId + "</td><td id='hide_td'>" + oduParent + "</td>" +
                        		"<td id='hide_td'>" + oduCategory + "</td><td id='hide_td'>"
                        		+ data[1][i].ratedCoolEffi +"</td><td id='hide_td'>"+ data[1][i].ratedHeatEffi +"</td><td id='hide_td'>"+ data[1][i].ratedCapRef +"</td><td id='hide_td'>"+ data[1][i].avgRatedEffi +"</td><td id='hide_td'>"+ data[1][i].rated_Cool_Capacity +"</td><td id='hide_td'>"+ data[1][i].rated_Heat_Capacity +"</td><td id='hide_td'>"+ data[1][i].rated_Cool_Power +"</td><td id='hide_td'>"+ data[1][i].rated_Heat_Power +"</td></tr>";

                    });

                    $.each(data[0], function(i, j) {
                        var flag = data[0][i].mainIduFlag;
                        var iduflag = '';
                        var Odu_Idu = '0';
                        var iduParent = null;
                        if (flag != 0) {
                            iduflag = 'Main'
                        } else {
                            iduflag = 'Sub'
                        }

                        $.each(Odu_Parent_Facilityds, function(q, w) {

                            if (parseInt(Odu_Parent_Facilityds[q].circuit_no) === parseInt(data[0][i].refrigCircuitId) && parseInt(Odu_Parent_Facilityds[q].connectionNumber) === parseInt(data[0][i].connectionNumber)) {

                            		 Odu_Idu = Odu_Parent_Facilityds[q].facility_id;

                            }
                     
                        });

                        console.log(data[0][i].mainIduAddress);
                        
                        
                        
                        if (data[0][i].mainIduFlag == '0')
						{
    						$.each(data[0],function(a,b){
   		                     
    							if(data[0][i].connectionNumber === data[0][a].connectionNumber)
    							{
    								//if (data[0][a].mainIduFlag == '0')
    								//{
    									//iduParent = data[0][a].facilityId;
    									
    									iduParent = data[0][i].mainIduAddress;
    									console.log(iduParent);
    									
    								//}
    								/*if(data[0][i].mainIduAddress === data[0][a].connectionIduAddress && data[0][a].mainIduFlag =='1')
    								{
    									iduParent = data[0][a].facilityId;
    								}*/

    							}
    							
    						});
                        	
						}
                        

                        /*if (data[0][i].mainIduFlag == '0') {
                            var child_con_number = data[0][i].connectionNumber;
                            $.each(data[0], function(a, b) {
                                if (child_con_number === data[0][a].connectionNumber) {
                                    if (data[0][i].mainIduAddress === data[0][a].connectionIduAddress) {
                                        console.log(data[0][i].facilityId + "" + data[0][a].facilityId);
                                        iduParent = data[0][a].facilityId;
                                    }

                                }

                            })

                        } else {
                            iduParent = null;
                        }*/

                    //   console.log(data);
						


                        my_htmlIDU += "<tr  class='loop_td' loop_numbers='" + i + "' id='" + data[0][i].type + "'>" +
                            "<td>" + data[0][i].slink + "</td><td>" + data[0][i].type + "</td>" +
                            "<td>" + iduflag + "</td><td>" + data[0][i].deviceModel + "</td>" +
                            "<td class='nr'><input class='device_names_class toplogy-cell'  type='text'  value='' ></td>" +
                            "<td>---</td><td>" + data[0][i].facilityId + "</td><td id='hide_td'>" + data[0][i].connectionIduAddress + "</td><td id='hide_td'>" + data[0][i].mainIduAddress + "</td><td id='hide_td'>" + data[0][i].refrigCircuitId + "</td>" +
                            "<td id='hide_td'>" + data[0][i].connectionType + "</td><td id='hide_td'>" + data[0][i].centralControlAddress + "</td><td id='hide_td'>" + iduParent + "</td><td id='hide_td'>" + Odu_Idu + "</td><td id='hide_td'>" + data[0][i].connectionNumber + "</td></tr>";


                    });

                    $.each(data[2], function(i, j) {
                        my_htmlPLS += "<tr  class='loop_td' loop_numbers='" + i + "' id='" + data[2][i].type + "'><td id='32'>---</td><td>" + data[2][i].type + "</td><td>---</td><td></td><td class='nr'><input class='device_names_class toplogy-cell'  type='text'  value=''></td><td>" + data[2][i].port_number + "</td><td>" + data[2][i].facilityId + "</td><td id='hide_td'>" + data[2][i].connectionType + "</td><td id='hide_td'>" + data[2][i].metertype + "</td><td id='hide_td'>" + data[2][i].multi_factor + "</td></tr>";
                    });

                    $("#table_topology_id1").html('<tbody id="tbody_div4">' + my_htmlIDU + '' + my_htmlODU + '' + my_htmlPLS + '</tbody>');

                }, 'post');

            }
            
            
            

            
        }, 'post');

    });
    
    

    
   // console.log($("#IDU").html());

    $(document).on("click", "#update", function() {
        idu_json = [];
        pulse_json = [];
        odu_json = [];

        $("#tbody_div4").find('tr').each(function(i, l) {

            $curTD = $(this).find("td");

            if (this.id == 'ODU') {
                if ($curTD.eq(4).find('input').val() !== '') {
                    odu_json.push({
                        'id': $curTD.eq(7).html(),
                        'deviceName': $curTD.eq(4).find('input').val()
                    });
                    //console.log(odu_json);
                }

            }

            if (this.id == 'IDU') {

                if ($curTD.eq(4).find('input').val() !== '') {
                    idu_json.push({
                        'id': $curTD.eq(7).html(),
                        'deviceName': $curTD.eq(4).find('input').val()
                    });

                }

            }

            if (this.id == 'PLS') {
                if ($curTD.eq(4).find('input').val() !== '') {
                    pulse_json.push({
                        'id': $curTD.eq(7).html(),
                        'deviceName': $curTD.eq(4).find('input').val()
                    });

                }

            }

        });

        var empty_val = '0';

        var isValid;
        $(".device_names_class").each(function() {
            var element = $(this);
            if (element.val() == "") {
                isValid = false;
            }
        });


        console.log(isValid);

        if (isValid !== false) {


            console.log(odu_json);
            console.log(idu_json);
            console.log(pulse_json);

            $.axs("../adapter/UpdateRetrieveToplogy.htm", {
                    'topology_pls': JSON.stringify({
                        'topologyList': pulse_json
                    }),
                    'topologyIdu': JSON.stringify({
                        'topologyList': idu_json
                    }),
                    'topologyOdu': JSON.stringify({
                        'topologyList': odu_json
                    }),


                },
                function(data) {
                	console.log(data.status);
                	if(data.status == "true"){
                		$.bizinfo("Successfully Updated Topology");
                	}else{
                		$.bizinfo("Failed Updating Topology");
                	}
                	$("#table_topology_id1").html("");
                	$('#RetriveTopologyId').modal('hide');
                }, 'post');

        } else {

            $.bizalert("Please Enter Dname For All Fields");

        }

    });

    $(document).on("click", "#topology_confirm", function() {
        idu_json = [];
        pulse_json = [];
        odu_json = [];
        idu = [];

        $("#tbody_div4").find('tr').each(function(i, l) {

            $curTD = $(this).find("td");
            if (this.id == 'PLS') {
                if ($curTD.eq(4).find('input').val() !== '') {
                    pulse_json.push({
                        'deviceName': $curTD.eq(4).find('input').val(),
                        'facilityId': $curTD.eq(6).html(),
                        'port_number': $curTD.eq(5).html(),
                        'connectionType': $curTD.eq(7).html(),
                        'metertype': $curTD.eq(8).html(),
                        'multi_factor': $curTD.eq(9).html()
                    });
                }
            }

            if (this.id == 'ODU') {
                if ($curTD.eq(4).find('input').val() !== '') {
                    odu_json.push({
                    	   's_link': $curTD.eq(0).html(),
                           'model': $curTD.eq(3).html(),
                           'deviceName': $curTD.eq(4).find('input').val(),
                           'facilityId': $curTD.eq(6).html(),
                           'connectionType': $curTD.eq(7).html(),
                           'connectionNumber': $curTD.eq(8).html(),
                           'refrigCircuitGroupOduId': $curTD.eq(9).html(),
                           'refrigCircuitId': $curTD.eq(10).html(),
                           'parent_id': $curTD.eq(11).html(),
                           'category': $curTD.eq(12).html(),
                           'RatedCoolEffi': $curTD.eq(13).html(),
                           'RatedHeatEffi': $curTD.eq(14).html(),
                           'RatedCapRef': $curTD.eq(15).html(),
                           'avgRatedEffi': $curTD.eq(16).html(),
                           
                           'rated_Cool_Capacity': $curTD.eq(17).html(),
                            'rated_Heat_Capacity': $curTD.eq(18).html(),
                            'rated_Cool_Power': $curTD.eq(19).html(),
                            'rated_Heat_Power': $curTD.eq(20).html()
                            
                    });
                    //console.log(odu_json);
                }
            }

            if (this.id == 'IDU') {
                if ($curTD.eq(4).find('input').val() !== '') {
/*
                	
 my_htmlIDU += "<tr  class='loop_td' loop_numbers='" + i + "' id='" + data[0][i].type + "'>" +
 "<td>" + data[0][i].slink + "</td>" +
 "<td>" + data[0][i].type + "</td>" +
 "<td>" + iduflag + "</td>" +
 "<td>" + data[0][i].deviceModel + "</td>" +
 "<td class='nr'><input class='device_names_class toplogy-cell'  type='text'  value='' ></td>" +
 "<td>---</td>" +
 "<td>" + data[0][i].facilityId + "</td>" +
"<td id='hide_td'>" + data[0][i].connectionIduAddress + "</td>" +
"<td id='hide_td'>" + data[0][i].mainIduAddress + "</td>" +
"<td id='hide_td'>" + data[0][i].refrigCircuitId + "</td>" +
 "<td id='hide_td'>" + data[0][i].connectionType + "</td>" +
"<td id='hide_td'>" + data[0][i].centralControlAddress + "</td>" +
"<td id='hide_td'>" + iduParent + "</td>" +
"<td id='hide_td'>" + Odu_Idu + "</td>" +
"</tr>";
*/
                	
                	
                    idu_json.push({
                        's_link': $curTD.eq(0).html(),
                        'model': $curTD.eq(3).html(),
                        'deviceName': $curTD.eq(4).find('input').val(),
                        'facilityId': $curTD.eq(6).html(),
                        'connectionIduAddress': $curTD.eq(7).html(),
                        'mainIduAddress': $curTD.eq(8).html(),
                        'refrigCircuitId': $curTD.eq(9).html(),
                        'connectionType': $curTD.eq(10).html(),
                        'centralControlAddress': $curTD.eq(11).html(),
                        'parent_id': $curTD.eq(12).html(),
                        'odu_facility_id': $curTD.eq(13).html(),
                        'connectionNumber': $curTD.eq(14).html()
                    });
                }
            }

        });

        var empty_val = '0';

        var isValid;
        $(".device_names_class").each(function() {
            var element = $(this);
            if (element.val() == "") {
                isValid = false;
            }
        });

        if (isValid !== false) {
        	if(pulse_json.length != 0 ||  idu_json.length != 0 || odu_json.length != 0){
	            $.axs("../adapter/InsertRetrieveToplogy.htm", {
	                    'topology_pls': JSON.stringify({
	                        'topologyList': pulse_json
	                    }),
	                    'adapter_id': $("#topology_ca_name option:selected").attr("topology_ad_id"),
	                    'site_id': $("#site_name_topolgy option:selected").val(),
	                    'topologyIdu': JSON.stringify({
	                        'topologyList': idu_json
	                    }),
	                    'topologyOdu': JSON.stringify({
	                        'topologyList': odu_json
	                    }),
	
	                },
	                function(data) {
	                	console.log(data.status);
	                	if(data.status == "true"){
	                		$.bizinfo("Topology Successfully Retrieved");
	                	}else{
	                		$.bizalert("Failed Topology Retrieval");
	                	}
	                	$("#table_topology_id1").html("");
	                	$('#RetriveTopologyId').modal('hide');
	                }, 'post');
        	}else{
        		$.bizalert("Please retrieve topology");
        	}
        } else {
            $.bizalert("Please Enter Dname For All Fields");
        }
    });
});