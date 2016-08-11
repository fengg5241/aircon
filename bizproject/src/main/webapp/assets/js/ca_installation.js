$(function(){
	
        	$("#ca_mac_address_reg").mask("************");

    		var option = '<option value="">Select Customers</option>';
    		var option1 = '';
    		var option4 = '<option value="">Please Select</option>';
    		
    		$.axs("../ca_data/listcustomers_adapters.htm", {
    		
                 }, function(data) {
            	 
                	 console.log(data);
				if( $(data).length !=0)
				{
						
					$.each(data,function(i,l){
					option += '<option value="'+ l[0] + '">' + l[1] + '</option>';});
					$('#customer_name').html(option);
					$('#cust_name_search').html(option);
					$('#cust_name').html(option);
				}

             });
    		
    		/*$.axs("../ca_data/getdomain.htm", {
            }, function(data) {
            	console.log(data);
            	// console.log(JSON.stringify(data));
            	// console.log(JSON.parse(JSON.stringify(data)));
            	// $('#sec_domain').val(JSON.parse(JSON.stringify(data)));

			});
    		*/
    		
    		var data = {"AJAXREQUEST": true, "avoidCacheDate": new Date().getTime()};
    		
    		 $.ajax({
    		        url: "../ca_data/getdomain.htm",
    		        cache: true,
    		        type: 'GET',
    		        data: data,
    		        async: true,
    		        beforeSend: function (request){
    		        	getToken(request);
    	            },
    		        success: function(data, success) {
    		        	  //console.log(data);
    		        	  $('#sec_domain').val(data);
		        	}
		        	/*error: function(jqxhr, textStatus, error) { 
		        	  console.log("error", arguments);
		        	},
		        	complete: function(jqxhr, textStatus) {
		        	  console.log("complete", arguments);
		        	}*/
    		    });
    		 
    		
    		

    		
    		
    		
    		$.axs("../ca_data/listtimezones.htm", {
            }, function(data) {
       	 
				if( $(data).length !=0)
				{					
	
					$.each(data,function(i,l){
						option4 += '<option value="'+ l[0] + '">' + l[1] + '</option>';						
						
					});
					$('#Timezone').html(option4);
				}
			});
    		

        	$.axs("../ca_data/listcamodel.htm", {
             }, function(data) {
            	 console.log(data);
					if( $(data).length !=0)
				{
						
					$.each(data,function(i,l){
						option1 += '<option value="'+ data[i] + '">' + data[i] + '</option>';						
						
					});
					$('#ca_model_reg').html(option1);
				}
				
        	 });
        	
        	$("#calender-icon").click(function(){
        		console.log("cali click");
        		$("#installed_at").trigger('focus');
        	});
        	
        	$("#installed_at").datetimepicker({
        		dateFormat: 'yy-mm-dd',
        		showTimezone: false,
        		timezone: "+0000",
        		timeFormat: 'HH:mm:00'
        	});
        	
        	

        	
        	$("#cust_name_search").change(function(){	
        		var option =  '<option value="">' + 'Select Mac Address...' + '</option>';

        		var option1 = '';
        		var search_mac = $("#cust_name_search").val();
	       		if($("#cust_name_search").val() != "")
	       		{
	        		$.axs("../ca_data/listMac_Casearch.htm", {
	        			"cust_id":search_mac
	                }, function(data) {
	                	
	                	console.log(data);
	                	
	                	
	                	if( $(data).length ===0)
						{
	                		$(".all_search_display").val('');
						}
	                	
						$.each(data,function(i,l){
							option += '<option model_name="'+l[2] + '" value="'+ l[1] + '">' + l[1] + '</option>';						
							
						});
						$('#ca_mac').html(option);
	
	           	 	},"post");
        		}
	       		else{
	       			$('#ca_mac').html(option);
		       	}

        	});
        	
        	$(".ca_mac_show").change(function(){
        		
        		var ca_mac = $(".ca_mac_show").val();
        		console.log(ca_mac);
        		var data_send = "{'ca_mac':"+ca_mac+"}";
        	if($(".ca_mac_show").val() != "")	
        	{
                $.axs("../ca_data/postviewCa.htm", {
                    "ca_mac": ca_mac
                }, function(data) {

					console.log(data);
					if( $(data).length !=0)
					{
						
						$("#location").val(data[0][0]);
						$("#latitude").val(data[0][1]);
						$("#longitude").val(data[0][2]);
						
						var date=new Date(data[0][3]);
						var hours = date.getHours(); // minutes part from the timestamp
						var minutes = date.getMinutes(); // seconds part from the timestamp
						var seconds = date.getSeconds(); // will display time in 10:30:23 format
						var year = date.getFullYear();
						var date_day = date.getDate();
						var month = date.getMonth()+1;
						//var formattedTime = year+'-'+month+'-'+date_day+' ' +hours + ':' + minutes + ':' + seconds;
						
						var formattedTimeUtc = moment.utc(data[0][3]).format('YYYY-MM-DD HH:mm:ss');
						var m = moment.utc(formattedTimeUtc, "YYYY-MM-DD HH:mm:ss");
						var formattedTime = m.tz(data[0][9]).format('YYYY-MM-DD HH:mm:ss');
						//var formattedTime = moment.tz("2016-03-03 07:00:00", data[0][9]).format('YYYY-MM-DD HH:mm:ss');
						//var formattedTime = formattedTimeUtc.tz("Asia/Tokyo").format('YYYY-MM-DD HH:mm:ss');
						//var formattedTime = moment(formattedTimeUtc).tz('Asia/Singapore').format('HH');
						//var stampString = '2016-03-03 07:00:00';

						
						
						
						
						console.log(formattedTimeUtc);
						console.log(data[0][9]);
						console.log(formattedTime);
						//console.log(formattedTime);
						//console.log(formattedTimeUtc);
						//console.log(data[0][0].getTimezoneOffset());
						//console.log(formattedTime);
					//	var timezoneTime = moment(formattedTimeUtc, 'YYYY-MM-DD HH:mm:ss').tz('Asia/Singapore').format('YYYY-MM-DD HH:mm:ss');


						
						
						//moment.utc('2014-02-19 05:24:32 AM').toDate();
						/*console.log(moment.parseZone(data[0][3]));
						console.log(moment(data[0][3]).toDate());
						console.log(timezoneTime);*/

						    
						$("#installation_date,#service_start").val(formattedTime);
						
						if(data[0][4]==='1')
						{
							var status_on = "Enabled";
						}
						else
						{
							var status_on = "Disabled";
						}
						
						$("#status").val(status_on);
						$("#ca_model").val(data[0][5]);
						$("#ca_name").val(data[0][6]);
						$("#building").val(data[0][7]);
						$("#fw_version").val(data[0][8]);
						$("#location").val(data[0][1]+' - '+data[0][2]);
						
						
					}else
					{
					
						$(".all_search_display").val('');
						$.bizalert("No Data Found");
					}
					
                });
        	}else{
        		$(".all_search_display").val('');
        	}
                
        	});
        	
        	
        	$("#cust_name").change(function(){	
        	
        		var option = '<option value="">Select Mac address</option>';
        		var option1 = '<option value="">Select Site</option>';
        		
        		console.log($("#cust_name").val());
        		
	       		if($("#cust_name").val() != "")
	       		{
					$.axs("../ca_data/listsiteidcust.htm", {
	        			"cust_id":$("#cust_name").val()
	                }, function(data) {
	                	
	                	console.log(data);
	                	$.each(data,function(i,l){
							option1 += '<option value="'+ l[0] + '">' + l[1] + '</option>';						
							
						});
	                		
	                	$('#site_id').html(option1);

	                });
	        		
	        		$.axs("../ca_data/listMacaddress_cust.htm", {
	        			"cust_id":$("#cust_name").val()
	                }, function(data) {
						$.each(data,function(i,l){

							option += '<option model_name="'+l[2] + '" value="'+ l[1] + '">' + l[1] + '</option>';						
							
						});
						$('#ca_mac_address').html(option);
						 $("#model_name").val($("#ca_mac_address option:selected").attr("model_name"))
						

	                },"post");
	       		}
	       		else{
	       			
	       			$('#site_id').html(option1);
	       			$('#ca_mac_address').html(option);
	       			
	       		}

        		


        	});

        	$("#associate").click(function(){
        		var ca_mac_address = $("#ca_mac_address").val();
 
        		//var data_send = $("#installed_at").val();
        		//console.log(data_send);
        		if ($("#cust_name").val() != "" &&  $("#ca_mac_address").val() != "" && $("#site_id").val() != "" && $("#ca_name_associate").val() != "" && $("#Timezone").val() != "" 
        				&& $("#Latitude").val() != "" && $("#Longitude").val() != "" && $("#installed_at").val() != "" && $("#Latitude").val() != "" && $("#Latitude").val() != "") {
        			
        			
                    $.axs("../ca_data/postCaassociate.htm", {
                   	 "cust_name":$("#cust_name").val(),
                   	 "ca_mac_address": $("#ca_mac_address").val(),
                   	 "site_id": $("#site_id").val(),
                   	 "ca_name_associate": $("#ca_name_associate").val(),
                   	 "Timezone": $("#Timezone").val(),
                   	 "Latitude": $("#Latitude").val(),
                   	 "Longitude": $("#Longitude").val(),
                   	 "installed_at": $("#installed_at").val(),
                   	 "status": "1",
                   	 "model_name":$("#model_name").val()
                   }, function(data) {
                   	//$('#myModal2').modal('hide')
                   	console.log(data);
                   	$.bizalert(data['errorMessage']);

   					
                   },"post");
                    
        		}
        		else{
        			
        			$.bizalert('Please enter all fields');
        		}
        		
        		

                
        	});
        	
        	$("#customer_name").change(function(){
        		
        		
        		$("#permission_grp").val($("#customer_name").val());
        		
        	});
	
        	$("#register").click(function(){
        		console.log($('#sec_domain').val());

        		
        		var selected_mac = $("#ca_mac_address_reg").val();
        		var Uppper_mac = selected_mac.replace(/-/g, '').toUpperCase();
        
    
        		if ($("#ca_mac_address_reg").val() != "" &&  $("#customer_name").val() != "") {
	        			$.axs("../ca_data/Register_Ca.htm", {
	   	        		 "ca_mac_address_reg": Uppper_mac,
	                   	 "ca_model": $("#ca_model_reg").val(),
	                   	 "customer_name": $("#customer_name").val(),
	                   	 "sec_domain":$("#sec_domain").val(),
						 "permission":$("#permission").val()
		   	            }, function(data) {
		   	            
		   	            	//$('#myModal1').modal('hide')
		   	            	//alert(data['errorMessage']);
		   					$.bizalert(data['errorMessage']);
		   					
		   	       	 },"post");
        		}
        		else{
        			$.bizalert("Please enter All fields");
        		}

	        	/*$.axs("../ca_data/Register_Ca.htm", {
	        		 "ca_mac_address_reg": Uppper_mac,
                	 "ca_model": $("#ca_model_reg").val(),
                	 "customer_name": $("#customer_name").val(),
                	 "sec_domain":$("#sec_domain").val()
	            }, function(data) {
	            
	            	$('#myModal1').modal('hide')
					$.bizalert(data['errorMessage']);
					
	       	 },"post");*/
       		 });

        });