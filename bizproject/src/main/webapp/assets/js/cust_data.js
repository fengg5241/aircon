 $(function(){
        	
	 
	 
	 $("#custRegistrationLink").click(function(){
	     $("#displayButton").prop('disabled', true);

	 });
	 $("#cutOffRequestLink,#areaAllocationLink,#systemconfigTabLink").click(function(){
	     $("#displayButton").prop('disabled', false);

	 });
	 
	 
	 
   			var option = '';
  		 	$.axs("../cust_data/listcustomers.htm", {
               }, function(data) {
            	   option += '<option value="">' + 'Select Customers...' + '</option>';
			if( $(data).length !=0)
			{
				
				$.each(data,function(i,l){
					option += '<option value="'+ l[0] + '">' + l[1] + '</option>';						
					
				});
				$('#cstmr_name').html(option);
			}

           });
  		 		
	       	$("#cstmr_name").change(function(){	
	       		console.log("cust value"+$("#cstmr_name").val());
	       		var option1 = '';
	       		if($("#cstmr_name").val() != "")
	       		{
		       		$.axs("../cust_data/listsiteid.htm", {"customer_name":$("#cstmr_name").val()
		       		}, function(data) {
		        	   console.log(data);
					if( $(data).length !=0)
					{
						
						
						$(".insert_all_sites").html('<div class="form-control text-center cust_all_siteids" style="overflow-y:auto;height: 350px;background-color:#eee"></div>');
						option1 += '<div class="col-sm-12" style="padding: 9px;"><div class="col-sm-2"><input type="checkbox" class="all_site"  name="all_site" value="1" style="padding: 41px;"></div><div class="col-sm-10" style="text-align:left;">All</div></div>';		
	
						$.each(data,function(i,j){

							option1 += '<div class="col-sm-12" style="padding: 9px;"><div class="col-sm-2"><input type="checkbox" class="site_id_class" disabled="disabled" id="site_id" name="site_id" value="'+j[0]+'" style="padding: 41px;"></div><div class="col-sm-10" style="text-align:left;">'+j[1]+'</div></div>'
							
						});
						$('.cust_all_siteids').html(option1);
						
					}else
					{
						$(".insert_all_sites").html('');
						$('.cust_all_siteids').html('');
					}
					
		      		 });
	       	}else{
	       		$(".insert_all_sites").html('');
	       		$('.cust_all_siteids').html('');
	       	}
	       		
	       	});
	       	
	       $(document).on('change', '.all_site', function (){   

	    	   if ($(this).is(':checked')) {
	    		   $('.site_id_class').each(function(){ this.checked = true; });
	    	   }	
	    	   else{
	    		   $('.site_id_class').each(function(){ this.checked = false; });
	    	   }
	       		
	       	})

        	
        	$("#register_site").click(function(){
        		
        		if ($("#cstmr_name").val() != ""){
        		
	            		var selected_siteids = [];
	           		 $(".site_id_class:checked").each(function() {
	           			 selected_siteids.push($(this).val());
	           		    });
	
	           		 
	           		 console.log(JSON.stringify({"site_id":selected_siteids}));
	           		$.axs("../cust_data/postcustreg.htm", {
	                       "customer_name": $("#cstmr_name").val(),
	                       "json_request": JSON.stringify({"site_id":selected_siteids})
	                   }, function(data) {
	                   	$('#myModal').modal('hide')
	   					$.bizalert(data['errorMessage']);
	                   	$('.insert_all_sites').html('');
	                   	//$("#cstmr_name").val("");
	                   	location.reload();
	   					
	                   },"POST");
        		}
        		else{
        			
        			$.bizalert('Please select Customers');
        		}
        			

                
        	});
       	 
        });