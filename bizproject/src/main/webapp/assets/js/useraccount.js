$(document)
		.ready(
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
					
					

					
					var GLOBAL_VAL = {
					        							
							user_table: null,
							updateuser_viewlog_table: null,
							role_table:null
					      
					    };
					
					var option = '<option value="" disabled selected>Please Select</option>';
					
					initUserTable("");
					initUserlogTable("");
					initRolelogTable("");
				    function initUserTable(html) {
				        if (GLOBAL_VAL.user_table != null) {
				            GLOBAL_VAL.user_table.destroy();
				        }

				        $("#user_table_body").html(html);

				        GLOBAL_VAL.user_table = $('#user_table')
				            .DataTable({
				                "paging": false,
				                "filter": true,
				                "info": false,
				                "responsive": true,
				                "deferRender": true,
				                "scrollCollapse": false,
				                "scroller": true,
				                "scrollY": 750,
				                "language": {
				                    "searchPlaceholder": "Search"
				                },
				       			"search": {"caseInsensitive": false},
				                "aoColumns": [
				                              { "bSortable": true },
				                              { "bSortable": true },
				                              { "bSortable": true },
				                              { "bSortable": true },
				                              { "bSortable": false }
				                            ] 
				            });
				    }
				    
				    
				    var items = [];
				   items =  $("#hiddenPermissionNameList").val();
				   console.log(items);
				  if(items.indexOf("component-User Account/Update User/customerIdTree") != -1 ){
					  $("#companydivlist").show();
					  $(".textRoletype").show();
					  $("#companylistids,#companylistid").hide();
					  $("#companylist").show();					  
					  $('#tab_logic tr').find('td:eq(1),th:eq(1)').show();
					  $('#br,#brs,#hideme').hide();
					  var list= "";
					  var lists= "";
					  var complist='<option value="" disabled selected>Select Company</option>';
					  /*$.axs("../usermanagement/getcompanylist.htm", {

						}, function(data) {//console.log(data);
							for (var i=0;i<data.length;i++){
								lists += '<div class="col-sm-12" style="padding: 9px;"><div class="col-sm-2"><input type="checkbox" class="siteiddrops" id="site_id" name="site_id" value="'+data[i].companyid+'" style="padding: 41px;"></div><div class="col-sm-10" style="text-align:left;">'+data[i].companyname+'</div></div>'

								list += '<div class="col-sm-12" style="padding: 9px;"><div class="col-sm-2"><input type="checkbox" class="siteiddrop" id="site_id" name="site_id" value="'+data[i].companyid+'" style="padding: 41px;"></div><div class="col-sm-10" style="text-align:left;">'+data[i].companyname+'</div></div>'
								complist += "<option value="+data[i].companyid+">"+data[i].companyname+"</option>";
							}
							$('#com').html(list);
							$('#con').html(lists);
							$('#companylistuser').html(complist);
						}, "post");*/
					  
					   
				  }else{
					  $('#br,#brs,#hideme').show();
					  $('#applycompany,#applycompanys').hide();
					  $("#companylist").hide();	
					  var roletypeid = "3";
					  $('#tab_logic tr').find('td:eq(1),th:eq(1)').remove();
				  }
					
				  $(document).on('change', '#companylistuser', function(e) {
					  $("#user_table_body").empty();
				  });
				  
				  
				  $(document).on('change', '#companylistuser', function(e) {
					 // GLOBAL_VAL.user_table.destory();
					  getUserIdListFulltable();
					  function getUserIdListFulltable(){
							var userListHtml = "";
							
							$.axs("../usermanagement/getUserlistundercompany.htm", { 
				    	    	"companyids" : $("#companylistuser option:selected").val()
							}, function(data) { console.log(data);
							if(data.errorMessage !='no.records.found'){
							for(var i=0; i<data.length;i++){							
								userListHtml += getUserIdHtml(data[i]);							
								}
							initUserTable(userListHtml);
				        	initUserScroll();
							}else {
								
								$("#user_table_body").html( '<div class="col-sm-12">No records found </div>');
							}
							});
						}												
			        });
				  
				  
				  $(document).on("click", "#applycompany", function(e) {
						 
						var selected_siteids = [];
								
				  		 $(".siteiddrop:checked").each(function() {
				  			 selected_siteids.push($(this).val());
				  		    });
				  		 
				  		
				  		 
				  		if (selected_siteids.length ==0){
				  			$.bizinfo("Select Any Customer");
				  		}else{$.jstree.destroy ();
				  		$.axs("../usermanagement/getmultiplecompanyUserGroup.htm", {    
							"type": "add",
							"companyids": selected_siteids.toString()
							
				        }, function(data) {
				        	$('#checkboxMenuTrees').jstree({
								'core' : {
									'data' : data,
									"themes" : {
										icons : false,
										dots : false
									}
								},
								'plugins' : [ "themes", "json_data", "checkbox","wholerow", "ui" ]
							});  			        	
				        });	 
				  }
				  		 
				  });
				  
				  $(document).on("click", "#applycompanys", function(e) {
						 
						var selected_siteids = [];
								
				  		 $(".siteiddrops:checked").each(function() {
				  			 selected_siteids.push($(this).val());
				  		    });
				  		
				  		if (selected_siteids.length ==0){
				  			$.bizinfo("Select Any Customer");
				  		}else{$.jstree.destroy ();
				  		$.axs("../usermanagement/getmultiplecompanyUserGroup.htm", {    
							"type": "add",
							"companyids": selected_siteids.toString()
							
				        }, function(data) {
				        	$('#checkboxMenuTree').jstree({
								'core' : {
									'data' : data,
									"themes" : {
										icons : false,
										dots : false
									}
								},
								'plugins' : [ "themes", "json_data", "checkbox","wholerow", "ui" ]
							});   			        	
				        });
				  		}
				  });
				  
					
				    function initUserScroll(){
				        $("#user_table_wrapper .dataTables_scrollBody")
				            .mCustomScrollbar({
				                scrollButtons: {
				                    enable: true
				                },
				                theme: "dark-2"
				            });
				    }
				    
				    function initUserlogTable(html) {
				        if (GLOBAL_VAL.updateuser_viewlog_table != null) {
				            GLOBAL_VAL.updateuser_viewlog_table.destroy();
				        }
				        
				        $("#updateuser_viewlog_table_body").html(html);    
				        GLOBAL_VAL.updateuser_viewlog_table = $('#updateuser_viewlog_table')
			            .DataTable({
			            	"ordering": true,
			                "paging":   false,
			                "filter": true,
			                "info" : false,
			                "responsive":   true,
			                "deferRender":    true,
			                "scrollCollapse": false,
			                "scroller": true,
			                "scrollY":  "400px",
			                "language": {
			    			          "searchPlaceholder": "Search"
			    			 },
			    			 "search": {
			    	   			    "caseInsensitive": false
			    			  } 
			            });
			    }
				    function initUsersScroll(){
				        $("#updateuser_viewlog_table_wrapper .dataTables_scrollBody")
				            .mCustomScrollbar({
				                scrollButtons: {
				                    enable: true
				                },
				                theme: "dark-2"
				            });
				    }
				    
				    function initRolelogTable(html) {
				        if (GLOBAL_VAL.role_table != null) {
				            GLOBAL_VAL.role_table.destroy();
				        }
				        
				        $("#role_table_body").html(html);    
				        GLOBAL_VAL.role_table= $('#role_table')
			            .DataTable({
			                "paging": false,
			                "filter": true,
			                "info": false,
			                "responsive": true,
			                "deferRender": true,
			                "scrollCollapse": false,
			                "scroller": true,
			                "scrollY": 400,
			              //  "table-layout":fixed,
			                "language": {
			                    "searchPlaceholder": "Search"
			                },
			       			"search": {"caseInsensitive": false},
			                "aoColumns": [
			                              { "bSortable": true },
			                              { "bSortable": true },
			                              { "bSortable": true },
			                              { "bSortable": true },
			                              { "bSortable": true }
			                            ] 
			            });
				     //   GLOBAL_VAL.role_table=      $('#role_table').dataTable().fnAdjustColumnSizing( false );
			    }
				    function initRoleScroll(){
				        $("#role_table_wrapper .dataTables_scrollBody")
				            .mCustomScrollbar({
				                scrollButtons: {
				                    enable: true
				                },
				                theme: "dark-2"
				            });
				    }
					
				   
					
					//Generate New User ID.
					$("#passwordgeneration").click(function() {
						$.axs("../usermanagement/getGeneratedUserId.htm", {
							"companyId":1
						}, function(data) {console.log(data);
							for(var key in data) {
				        	    var userid = data[Object.keys(data)[0]];
				        	    $("#userid").val(userid);
				        	}
						}, "post");
					});
					
					
					
					
					
					//Get RoleList.
					window.load = getRolelist();
					function getRolelist()
				    {    	
						/*$('#rolelist').empty(); 
						$("#rolelist>option").remove();
						$("#rolelist option[value='option']").remove();*/
						$.axs("../usermanagement/getRoleList.htm", {  
							"companyId": ""
				        }, function(data) { //       console.log(data);	        	
				        for(var i=0; i<data.length;i++){
							var role_type = data[i].role_name;							
							//option += "<option  values="+data[i].role_name+"  value="+data[i].role_id+">"+data[i].role_name+"</option>";
							option += "<option  values="+data[i].role_name+" value="+data[i].role_id+"-"+data[i].roletype_id+">"+data[i].role_name+"</option>";
							}
						//	$('#rolelist').html(option);
							$('#updaterolelist').html(option);
				        }, "post");
						
						
				    }
					
					
					getRolelists();
					function getRolelists()
				    {    	
						
						$.axs("../usermanagement/getRoleList.htm", {  
							"companyId": ""
				        }, function(data) {        //console.log(data);	        	
				        for(var i=0; i<data.length;i++){
				        	$("#triggerclick").trigger("click");
							}
							
				        }, "post");
						
						
				    }
					
					
					getRoletypes();
					function getRoletypes()
					{ 
					/*var optionsHtml = "";
						$.axs("../usermanagement/getRoleType.htm", {}, function(data) {console.log(data);
						for(var i=0; i<data.length;i++){
						var role_type = data[i].role_type_name;
						//optionsHtml += "<option value="+data[i].role_type_name+">"+data[i].role_type_id+"</option>";
						optionsHtml += "<option value="+data[i].role_type_id+">"+data[i].role_type_name+"</option>";
						}
						$('#roletypes').html(optionsHtml);
						});*/
						var option = '<option value="" disabled>Please Select</option>';
						$.axs("../usermanagement/getRoleType.htm", {}, function(data) {							
							if( $(data).length !=0)
							{
								$.each(data,function(i,l){
									option += '<option value="'+ data[i].role_type_id + '">' + data[i].role_type_name + '</option>';
								});
								
								$('.roletype').html(option);								
							}
						});	
					}
					
					getFunctionalgroup();
					function getFunctionalgroup()
				    {    	
						var funopt = "";
						var itemlist = [];
						itemlist =  $("#hiddenPermissionNameList").val();
						  // console.log(itemlist);
						  if(itemlist.indexOf("component-User Account/Update User/customerIdTree") != -1 ){												  
							  var roletypeid = ""
						  }else{
							  roletypeid = "3"
						  }	
						
						
						$.axs("../usermanagement/getFuncGrpList.htm", {
							"role_type_id" :roletypeid
 				        }, function(data) {        	  console.log(data);	      	
 				       if( $(data).length !=0)
						{
							$.each(data,function(i,l){
								funopt += '<option value="'+ data[i].functional_id + '">' + data[i].functional_name + '</option>';
							});
							
							$('.roll').html(funopt);								
						}
				        });
						
						
				    }
					
					//getPermissiondetails();
					function getPermissiondetails(){ 
						$.axs("../usermanagement/getPermissionDetails.htm", {
													
						}, function(data) {//console.log(data);
							/*for(var key in data) {
				        	    var userid = data[Object.keys(data)[0]];
				        	    $("#userid").val(userid);
				        	}*/
						});
					}
					
					//Get User Group Tree hierarchy.
					//initMenuTree();
					function initMenuTree() { 
						$.axs("../usermanagement/getUserGroup.htm", { /*/usermanagement/getUsergroup.htm*/
							"type":"update"
						}, function(data) { console.log(data);
							$('#checkboxMenuTree').jstree({
								'core' : {
									'data' : data,
									"themes" : {
										icons : false,
										dots : false
									}
								},
								'plugins' : [ "themes", "json_data", "checkbox","wholerow", "ui" ]
							});

						});
					}
					
					
					
					$("#userlisttabs").click(function() {
						if(items.indexOf("component-User Account/Update User/customerIdTree") != -1 ){
							getUserIdListFulltable();
						}else{
						getUserIdListFulltable();
						}
						
					});
					
					
					function getUserIdListFulltable(){
						var userListHtml = "";
						
						$.axs("../usermanagement/getUserIdListFull.htm", { 
			    	    	"companyId" : ""
						}, function(data) { console.log(data);
						for(var i=0; i<data.length;i++){							
							userListHtml += getUserIdHtml(data[i]);							
							}
						initUserTable(userListHtml);
			        	initUserScroll();
						},"post");
					}
					
					function getUserIdHtml(unitVO) {
				    	var refUnitId = "id" + unitVO.id;
						return "<tr id=" + refUnitId + "tr refDomId=" + refUnitId + ">"+
								getTDHtml(unitVO.loginId, "userid") + getTDHtml(unitVO.account_state, "useraccountstate")  + getTDHtml(unitVO.registered_date, "userregitdate")  +getTDHtml(unitVO.registered_by, "userregistby")  +
								getTDHtml('<a class="functionalgroup" data-id='+ unitVO.id+' data-rolename='+ unitVO.rolename+' data-account_state='+ unitVO.account_state+' href="javascript:void(0);">Edit</a>',"useraccess")  +"</tr>"; 
				    }
					
					function getTDHtml(string, className) {
				        return "<td class='" + className + "'>" + string + "</td>";
				    }
					
					
				
					var optHtml ="";
					var onloadlist = [];
					var offloadlist = [];
					
					
					
					
					
					$('#updateresetpassword').prop('disabled', true);
					//active reset password after user id selection.
					$("#updateuserid").change(function () {
						$.jstree.destroy ();
						
						if($("#updateuserid option:selected").val() != "" ||$("#updateuserid option:selected").val() != null){ 							
							$('#updateresetpassword').prop('disabled', false); 
						}
						else {							
							$('#updateresetpassword').prop('disabled', true);
						}
						
						var value =$("#updateuserid option:selected").text();
						$.axs("../usermanagement/getUserIdListFull.htm", { 
			    	    	"companyId" : ""
						}, function(data) { console.log(data);
						for(var i=0; i<data.length;i++){
							var update_account_state = data[i].account_state;
							var update_user_Id=data[i].loginId;
							var update_id=data[i].id;	
							var update_rolename=data[i].rolename;							
							if(update_user_Id == value)
								{								
								$('#updaterolelist>option[values="' + update_rolename + '"]').prop('selected', true);
						        $('#updateaccountstate>option[value="' + update_account_state + '"]').prop('selected', true);
								}																					
							}
						});
						
						onloadupdatepage();
						function onloadupdatepage() {
						var items = [];
						items =  $("#hiddenPermissionNameList").val();
						var Role_id = $("#updaterolelist option:selected").val();	
			    	    var roleidval=Role_id.split('-')[0];
			    	    var roletypeval=Role_id.split('-')[1];
			    	    
			    	    var userupdate_id = $("#updateuserid option:selected").val();	
			    	    var useridval=userupdate_id.split('-')[0];
			    	    var userroletypeval=userupdate_id.split('-')[1];
			    		
			    	    
						companys();
						
						function companys(){							
						$.axs("../usermanagement/getcompanylist.htm", { 			    	    	
						}, function(data) { console.log(data);
						var companylength = data.length;
						if((items.indexOf("navi-CA Installation") != -1) && (userroletypeval == 1 ) ){
							initMenuTreesupdate();					
						}else if((items.indexOf("navi-CA Installation") != -1) && (userroletypeval == 2 ||userroletypeval == 3 ) ){
							initMenuTreeupdate();							
							if(companylength>1){$.bizinfo("Please select only one customer to user update? User will not update for multiple customer selection");}							
						}else if (((items.indexOf("tab-System Settings/Customer Registration") != -1) && !(items.indexOf("navi-home") != -1))&&(userroletypeval == 1 )){ 
							initMenuTreesupdate();							
						}else if (((items.indexOf("tab-System Settings/Customer Registration") != -1) && !(items.indexOf("navi-home") != -1))&&(userroletypeval == 2 ||userroletypeval == 3 )){ 
							initMenuTreeupdate();							
							if(companylength>1){$.bizinfo("Please select only one customer to user update? User will not update for multiple customer selection");}
						}else{							
							initcustomergroup();														
						}						  					  						   
						});						
						}			    	    						
						}
						
						
						
				    });
					
					function initMenuTreesupdate(){
						var userupdate_id = $("#updateuserid option:selected").val();	
			    	    var useridval=userupdate_id.split('-')[0];
			    	    var userroletypeval=userupdate_id.split('-')[1];
						$.axs("../usermanagement/getUserAssignedDetail.htm", { 
							"type":"update",
			    	    	"userId" : useridval,
						}, function(data) { console.log(data);
						$('#checkboxMenuTree').jstree({
							'core' : {
								'data' : data.group_strucutre,
								"themes" : {
									icons : false,
									dots : false
								},
								"state" : { "checkbox_disabled" : true } ,
								checkbox: {
							        real_checkboxes: true,
							            two_state: true
							    },
								"types" : {
					                "types": {
					                "disabled" : { 
					                      "check_node" : true, 
					                      "uncheck_node" : true 
					                    } 
					                }
					            }
							},
							'plugins' : [ "themes", "json_data", "checkbox","wholerow", "ui" ,"changed","types"]
						}).bind('loaded.jstree', function(e, data) {
							onloadlist =getOnloadCheckedGroupIds();
							$('#checkboxMenuTree').jstree().check_all();
							$("#checkboxMenuTree").attr("disabled", "disabled").off('click');	
						     
					    }).on("changed.jstree", function (e, data) {
					    	//offloadlist = getCheckedGroupIds();
					    	
						    });
						});	
					}
					
					function initcustomergroup(){
						var userupdate_id = $("#updateuserid option:selected").val();	
			    	    var useridval=userupdate_id.split('-')[0];
			    	    var userroletypeval=userupdate_id.split('-')[1];
			    	    var usercompanyid=userupdate_id.split('-')[2];
							$.axs("../usermanagement/getCustomerDetail.htm", { 
								"type":"update",
				    	    	"userId" : useridval,
				    	    	"companyId":usercompanyid
							}, function(data) { 
							$('#checkboxMenuTree').jstree({
								'core' : {
									'data' : data.groupVoCompanyWise,
									"themes" : {
										icons : false,
										dots : false
									}
								},
								'plugins' : [ "themes", "json_data", "checkbox","wholerow", "ui","changed" ]
							}).bind('loaded.jstree', function(e, data) {
								onloadlist =getOnloadCheckedGroupIds();
							      
						    }).on("changed.jstree", function (e, data) {
						    //	offloadlist = getCheckedGroupIds();
						    	
							    });;
							});
						}
					
					
					
					function initMenuTreeupdate(){
						var userupdate_id = $("#updateuserid option:selected").val();	
			    	    var useridval=userupdate_id.split('-')[0];
			    	    var userroletypeval=userupdate_id.split('-')[1];
						$.axs("../usermanagement/getUserAssignedDetail.htm", { 
							"type":"update",
			    	    	"userId" : useridval,
						}, function(data) { console.log(data);
						$('#checkboxMenuTree').jstree({
							'core' : {
								'data' : data.group_strucutre,
								"themes" : {
									icons : false,
									dots : false
								}
							},
							'plugins' : [ "themes", "json_data", "checkbox","wholerow", "ui" ,"changed"]
						}).bind('loaded.jstree', function(e, data) {
							onloadlist =getOnloadCheckedGroupIds();
						     
					    }).on("changed.jstree", function (e, data) {
					    	//offloadlist = getCheckedGroupIds();
					    
						    });
						});	
					}
					
					
					
					
					//Update User.
					$(document).on("click", "#updateuser", function(e) {
						var items = [];
						items =  $("#hiddenPermissionNameList").val();
						var Role_id = $("#updaterolelist option:selected").val();	
			    	    var roleidval=Role_id.split('-')[0];
			    	    var roletypeval=Role_id.split('-')[1];
				    	var idList = getOnloadCheckedGroupIds();
				    	var items = [];
						items =  $("#hiddenPermissionNameList").val();
						var userupdate_id = $("#updateuserid option:selected").val();	
			    	    var useridval=userupdate_id.split('-')[0];
			    	    var userroletypeval=userupdate_id.split('-')[1];
				    	var User_id = useridval		
				    	if((items.indexOf("navi-CA Installation") != -1)){
				    		if(User_id == ""||User_id ==null){
					    		$.bizinfo("Please select user id");
					    		}
					    	else {
					    		var userupdate_id = $("#updateuserid option:selected").val();	
					    	    var useridval=userupdate_id.split('-')[0];
					    	    var userroletypeval=userupdate_id.split('-')[1];
					    		var User_id = useridval
					    	    var Role_id = roleidval
					    	    
					    	    var Account_state = $("#updateaccountstate option:selected").text();
					    	   
					    	    if(Account_state =="Invalid"){
					    	    	$.bizalert({
					        			"title":"",
					        			"message":"Do you really wants to make it as invalid once it is set as invalid cannot be make it back to valid again.",
					        			"showConfirmButton":true,
					        			"confirmCallBack":invalidaccount
					        		});				    	    	
					    	    }else{
					    	    	invalid();
					    	    }				    	   
					    	}
				    	}else{
				    	if(User_id == ""||User_id ==null){
				    		$.bizinfo("Please select user id");
				    		}
				    	else if(idList.length == 0){
				    		$.bizinfo("Please choose required groups");				    		
				    	}else {
				    		var userupdate_id = $("#updateuserid option:selected").val();	
				    	    var useridval=userupdate_id.split('-')[0];
				    	    var userroletypeval=userupdate_id.split('-')[1];
				    		var User_id = useridval
				    	    var Role_id = roleidval
				    	    var Account_state = $("#updateaccountstate option:selected").text();
				    	   
				    	    if(Account_state =="Invalid"){
				    	    	$.bizalert({
				        			"title":"",
				        			"message":"Do you really wants to make it as invalid once it is set as invalid cannot be make it back to valid again.",
				        			"showConfirmButton":true,
				        			"confirmCallBack":invalidaccount
				        		});				    	    	
				    	    }else{
				    	    	invalid();
				    	    }				    	   
				    	}
					}
				    	return false;
				    });
					
					function invalid() {
						var Role_id = $("#updaterolelist option:selected").val();	
			    	    var roleidval=Role_id.split('-')[0];
			    	    var roletypeval=Role_id.split('-')[1];
			    	    var userupdate_id = $("#updateuserid option:selected").val();	
			    	    var useridval=userupdate_id.split('-')[0];
			    	    var userroletypeval=userupdate_id.split('-')[1];
						var User_id = useridval
			    	    var Role_id = roleidval
			    	    var Account_state = $("#updateaccountstate option:selected").text();
			    	    var idLists = getFinalCheckedGroupIds();
			    	    	getCheckedGroupIds();	
			    	    if(uniqueupdate.length >1){
			    	    	$.bizinfo("Please select only one customer to user update? User will not update for multiple customer selection");			    	    	
			    	    	checkedupdatecompanylist =[];
			    	    }else{
						$.axs("../usermanagement/updateUser.htm", { 
			    	    	"companyId":uniqueupdate[0],
							"userId" : User_id,
							"role_Id" : Role_id,
							"account_state" :Account_state,
							"group_id":idLists.toString(),
							"old_group_id":onloadlist.toString(),
							"userInfoUpdated":true
		                	
						}, function(data) { console.log(data);
						if(data.errorMessage == "user.role.type.cannot.changed"){
							$.bizinfo("User roletype cannot changed");
						}else{
					     $.bizinfo("User details successfully updated");					     
						}
						

						},"post");
			    	    }
					}
					
					
					function getallUserIdListFull(){
						
						var option = '<option value="" disabled selected>Please Select</option>';
						var optHtml = '<option value="" disabled selected>Please Select</option>';
						$.axs("../usermanagement/getUserIdListFull.htm", { 
			    	    	"companyId" : ""
						}, function(data) {// console.log(data);
						for(var i=0; i<data.length;i++){
							var update_account_state = data[i].account_state;
							var update_user_Id=data[i].loginId;
							var update_id=data[i].id;
							option += "<option  values="+data[i].id+"  value="+data[i].id+"-"+data[i].roletypeid+"-"+data[i].companyid+">"+data[i].loginId+"</option>";
							
							optHtml += "<option value="+data[i].id+">"+data[i].account_state+"</option>";
							}
						$('#updateuserid').html(option);
						//$('#updateaccountstate').html(optHtml);
						}, "post");
					}
					
					function getallUserRolelist()
				    {    	
						/*$('#rolelist').empty(); 
						$("#rolelist>option").remove();
						$("#rolelist option[value='option']").remove();*/
						$.axs("../usermanagement/getRoleList.htm", {  
							"companyId": ""
				        }, function(data) { //       console.log(data);	        	
				        for(var i=0; i<data.length;i++){
							var role_type = data[i].role_name;							
							//option += "<option  values="+data[i].role_name+"  value="+data[i].role_id+">"+data[i].role_name+"</option>";
							option += "<option  values="+data[i].role_name+" value="+data[i].role_id+"-"+data[i].roletype_id+">"+data[i].role_name+"</option>";
							}
						//	$('#rolelist').html(option);
							$('#updaterolelist').html(option);
				        }, "post");
						
						
				    }
					
					function addnewrole(){
						$.axs("../usermanagement/getRoleList.htm", {  
							"companyId": ""
				        }, function(data) {    
				        	var optionrole='<option value="" disabled selected>Please Select</option>';
				        	console.log(data);	        	
				        for(var i=0; i<data.length;i++){
				        	
							var role_type = data[i].role_name;							
							//optionrole += "<option values="+data[i].roletype_id+" value="+data[i].role_id+">"+data[i].role_name+"</option>";
							optionrole += "<option values="+data[i].role_name+" value="+data[i].role_id+"-"+data[i].roletype_id+">"+data[i].role_name+"</option>";
							}
				        
							$('#updaterolelist').html(optionrole);
							//$('#updaterolelist').html(option);
				        }, "post");
					}
					
					function getallUserAccountlist()
				    {    												
						var select = document.getElementById("updateaccountstate"); 
						var options = ["Valid", "Invalid", "Locked"]; 												
						select.innerHTML = '<option value="" disabled selected>Please Select</option>';						
						for(var i = 0; i < options.length; i++) {
						    var opt = options[i];
						    select.innerHTML += "<option value=\"" + opt + "\">" + opt + "</option>";

						}							        												
				    }
					
					
					function invalidaccount() {
						var Role_id = $("#updaterolelist option:selected").val();	
			    	    var roleidval=Role_id.split('-')[0];
			    	    var roletypeval=Role_id.split('-')[1];
			    	    var userupdate_id = $("#updateuserid option:selected").val();	
			    	    var useridval=userupdate_id.split('-')[0];
			    	    var userroletypeval=userupdate_id.split('-')[1];
						var User_id = useridval
			    	    var Role_id = roleidval
			    	    var Account_state = $("#updateaccountstate option:selected").text();
						var idLists = getFinalCheckedGroupIds();
		    	    	getCheckedGroupIds();
			    	    if(uniqueupdate.length>1){
			    	    	$.bizinfo("Please select only one customer to user update? User will not update for multiple customer selection");			    	    	
			    	    	checkedupdatecompanylist =[];
			    	    }else{
						$.axs("../usermanagement/updateUser.htm", { 
			    	    	"companyId":uniqueupdate[0],
							"userId" : User_id,
							"role_Id" : Role_id,
							"account_state" :Account_state,
							"group_id":idLists.toString(),
							"old_group_id":onloadlist.toString(),
							"userInfoUpdated":true
		                	
						}, function(data) { console.log(data);
						if(data.errorMessage == "user.role.type.cannot.changed"){
							$.bizinfo("User roletype cannot changed");
						}else{
					     $.bizinfo("User details successfully terminated");
					     getallUserIdListFull();
					     getallUserRolelist();
					     getallUserAccountlist();
					     $.jstree.destroy ();
						}
						

						},"post");
			    	    }
					}
					
					//deselect group selection.
					$("#resetgroup").click( function()
					           {
						//$('#checkboxMenuTree').jstree("deselect_all");

						var items = [];
						items =  $("#hiddenPermissionNameList").val();
						var userupdate_id = $("#updateuserid option:selected").val();	
			    	    var useridval=userupdate_id.split('-')[0];
			    	    var userroletypeval=userupdate_id.split('-')[1];
			    	    
			    	    companys();
						
						function companys(){							
						$.axs("../usermanagement/getcompanylist.htm", { 			    	    	
						}, function(data) { console.log(data);
						var companylength = data.length;
						if((items.indexOf("navi-CA Installation") != -1) && (userroletypeval == 1 ) ){$.jstree.destroy ();
							initMenuTreesupdate();					
						}else if((items.indexOf("navi-CA Installation") != -1) && (userroletypeval == 2 ||userroletypeval == 3 ) ){$.jstree.destroy ();
							initMenuTreeupdate();							
							if(companylength>1){$.bizinfo("Please select only one customer to user update? User will not update for multiple customer selection");}							
						}else if (((items.indexOf("tab-System Settings/Customer Registration") != -1) && !(items.indexOf("navi-home") != -1))&&(userroletypeval == 1 )){ $.jstree.destroy ();
							initMenuTreesupdate();							
						}else if (((items.indexOf("tab-System Settings/Customer Registration") != -1) && !(items.indexOf("navi-home") != -1))&&(userroletypeval == 2 ||userroletypeval == 3 )){ $.jstree.destroy ();
							initMenuTreeupdate();							
							if(companylength>1){$.bizinfo("Please select only one customer to user update? User will not update for multiple customer selection");}
						}else{	$.jstree.destroy ();						
							initcustomergroup();							
							
						}						  					  						   
						});						
						}																					
					           }
					        );
					
					var today = new Date();
				    var dd = today.getDate();
				    var mm = today.getMonth()+1; 
				    var yyyy = today.getFullYear()
				    today = dd+'/'+mm+'/'+yyyy;
					
					
					
					$("#updateresetpassword").click(function() {
											
						var User_id = $("#updateuserid option:selected").text();
						url = "../usermanagement/resetUser.htm";	
			    	    
			    	    $.ajax({
			                url: url,
			                beforeSend: function (request){
			                	getToken(request);
			                },
			                data: {
			                	"loginId" : User_id,
			                	"AJAXREQUEST": true
			                },
			                success: function(data) {
			    	        	console.log(JSON.stringify(data));
			    	        	     
			    	            var Role_i= "Reset Password Details"+today;		    	            		    	           		    	            
			    	            var fileName = User_id+"_"+"("+Role_i+").csv";
			    	            
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
			    	    
			    	    
			    	    
					});
					
					
					
					function getTDHtml(string, className) {
				        return "<td class='" + className + "'>" + string + "</td>";
				    }
					
					
					
					
					var checkedupdatecompanylist =new Array();
					function getCheckedGroupIds() {
						var checkedIdList = new Array();
						var checkedcompanylist =new Array();
						var tree = $.jstree.reference('#checkboxMenuTree');
						
						if (tree != null) {
							var checkedList = tree.get_top_checked(true);	//	console.log(JSON.stringify(checkedList));
							for (var i = 0; i < checkedList.length; i++) {				
								var node = checkedList[i];
								if (node.parent == "#") {	
									if (typeof (node.original.groupId) == "undefined") {//mean it is company, need get children
										for (var j = 0; j < node.children.length; j++) {
											//get id from childNode j1_1
										//	var childNode = node.children[i].split("_")[1];	
											var childNodeStr = node.children[j];
											console.log(JSON.stringify(childNodeStr));
											var $childNode = $('#checkboxMenuTree').jstree().get_node(childNodeStr);														
											checkedIdList.push($childNode.original.groupId);
											
											if(typeof (node.original.groupId) == "undefined")
												checkedcompanylist.push(node.original.companyID);
											else
												checkedcompanylist.push($childNode.original.companyId);
										}
									}
								}
								else {
									checkedIdList.push(checkedList[i].li_attr.dataId);
									checkedcompanylist.push(checkedList[i].original.companyId);

								}
							}
						}
						console.log(checkedIdList);
						uniqueupdate = checkedcompanylist.filter(function(itm,i,types) {
						    return i==types.indexOf(itm);
						});
						console.log(checkedcompanylist);
						console.log(uniqueupdate);
						return checkedIdList;
						return uniqueupdate;
						
					}
					
					
					function getFinalCheckedGroupIds() {
						var checkedIdListss = new Array();						
						var tree = $.jstree.reference('#checkboxMenuTree');
						
						if (tree != null) {
							var checkedList = tree.get_top_checked(true);	//	console.log(JSON.stringify(checkedList));
							for (var i = 0; i < checkedList.length; i++) {				
								var node = checkedList[i];
								if (node.parent == "#") {	
									if (typeof (node.original.groupId) == "undefined") {//mean it is company, need get children
										for (var j = 0; j < node.children.length; j++) {
											//get id from childNode j1_1
										//	var childNode = node.children[i].split("_")[1];	
											var childNodeStr = node.children[j];
											console.log(JSON.stringify(childNodeStr));
											var $childNode = $('#checkboxMenuTree').jstree().get_node(childNodeStr);														
											checkedIdListss.push($childNode.original.groupId);											
										}
									}
								}
								else {
									checkedIdListss.push(checkedList[i].li_attr.dataId);									

								}
							}
						}
						console.log(checkedIdListss);
																		
						return checkedIdListss;
						
						
					}
					
					
					
					
					function getOnloadCheckedGroupIds(){
						var checkedIdLists = new Array();
						var tree = $.jstree.reference('#checkboxMenuTree');
						if (tree != null) {
							var checkedLists = tree.get_checked(true);
					
							for (var i = 0; i < checkedLists.length; i++) {
								if (typeof (checkedLists[i].original.groupId) != "undefined") { 
									checkedIdLists.push(checkedLists[i].li_attr.dataId);
								}
							}
						}
						return checkedIdLists;
					}
					
					function getCheckedControlGroupIds() {
						var checkedIdList = new Array();
						var tree = $.jstree.reference('#checkboxMenuTree');
						if (tree != null) {
							var checkedList = tree.get_bottom_checked(true);

							for (var i = 0; i < checkedList.length; i++) {
								if (typeof (checkedList[i].original.groupId) != "undefined") { 
									checkedIdList.push(checkedList[i].li_attr.dataId);
								}
							}
						}
						return checkedIdList;
					}

					function getCheckedGroupIdsByLevelName(levelName) {
						var checkedIdList = new Array();
						var tree = $.jstree.reference('#checkboxMenuTree');
						if (tree != null) {
							var checkedList = tree.get_checked(true);
							for (var i = 0; i < checkedList.length; i++) {
								if (typeof (checkedList[i].original.groupId) != "undefined") { 
									var groupLevelName = checkedList[i].original.groupTypeLevelName;
									if (groupLevelName == levelName) {
										checkedIdList.push(checkedList[i].li_attr.dataId);
									}
								}
							}
						}
						return checkedIdList;
					}
					
					
					//Reset UserSelection.
					$("#resetselectionuser").click( function(){
						var items = [];
						items =  $("#hiddenPermissionNameList").val();
						var Role_id = $("#rolelist option:selected").val();	
			    	    var roleidval=Role_id.split('-')[0];
			    	    var roletypeval=Role_id.split('-')[1];
						if((items.indexOf("navi-CA Installation") != -1)){
					
						}else if(((items.indexOf("tab-System Settings/Customer Registration") != -1) && !(items.indexOf("navi-home") != -1))&&(roletypeval==1)){
							
						}
						else if(((items.indexOf("tab-System Settings/Customer Registration") != -1) && !(items.indexOf("navi-home") != -1))&&(roletypeval==3 || roletypeval==2)){
							$('#checkboxMenuTrees').jstree("deselect_all");
						}else{
						$('#checkboxMenuTrees').jstree("deselect_all");
						}
					}					 
					 );
					
					
					$(document).on('click', 'a.functionalgroup', function(e) {
						$.jstree.destroy ();
			            $('#updateusertabs').click();	
			            $('#updateresetpassword').prop('disabled', false);			           			           
			            $('#updateuserid>option[values="' + $(this).attr("data-id") + '"]').prop('selected', true);
			            $('#updaterolelist>option[values="' + $(this).attr("data-rolename") + '"]').prop('selected', true);
			            $('#updateaccountstate>option[value="' + $(this).attr("data-account_state") + '"]').prop('selected', true);		
			            
			            var Role_id = $("#updaterolelist option:selected").val();	
			    	    var roleidval=Role_id.split('-')[0];
			    	    var roletypeval=Role_id.split('-')[1];
			    	    			    	    			            
			            var items = [];
						items =  $("#hiddenPermissionNameList").val();
						function initmenuchecked(){
							var userupdate_id = $("#updateuserid option:selected").val();	
				    	    var useridval=userupdate_id.split('-')[0];
				    	    var userroletypeval=userupdate_id.split('-')[1];
							$.axs("../usermanagement/getUserAssignedDetail.htm", { 
								"type":"update",
				    	    	"userId" : useridval
							}, function(data) { console.log(data);
							$('#checkboxMenuTree').jstree({
								'core' : {
									'data' : data.group_strucutre,
									"themes" : {
										icons : false,
										dots : false
									},
									"state" : { "checkbox_disabled" : true } ,
									checkbox: {
								        real_checkboxes: true,
								            two_state: true
								    },
									"types" : {
						                "types": {
						                "disabled" : { 
						                      "check_node" : true, 
						                      "uncheck_node" : true 
						                    } 
						                }
						            }
								},
								'plugins' : [ "themes", "json_data", "checkbox","wholerow", "ui" ,"changed","types"]
							}).bind('loaded.jstree', function(e, data) {
								onloadlist =getOnloadCheckedGroupIds();
								$('#checkboxMenuTree').jstree().check_all();
								$("#checkboxMenuTree").attr("disabled", "disabled").off('click');	
							     
						    }).on("changed.jstree", function (e, data) {
						    	//offloadlist = getCheckedGroupIds();
						    	
							    });
							});
						}
						
						function initmenuuncheked(){ 
						var userupdate_id = $("#updateuserid option:selected").val();	
			    	    var useridval=userupdate_id.split('-')[0];
			    	    var userroletypeval=userupdate_id.split('-')[1];
							$.axs("../usermanagement/getUserAssignedDetail.htm", { 
								"type":"update",
				    	    	"userId" : useridval
							}, function(data) { 
							$('#checkboxMenuTree').jstree({
								'core' : {
									'data' : data.group_strucutre,
									"themes" : {
										icons : false,
										dots : false
									}
								},
								'plugins' : [ "themes", "json_data", "checkbox","wholerow", "ui","changed" ]
							}).bind('loaded.jstree', function(e, data) {
								onloadlist =getOnloadCheckedGroupIds();
							      
						    }).on("changed.jstree", function (e, data) {
						    	//offloadlist = getCheckedGroupIds();
						    	
							    });;
							});
						}
						
						
						function customergroup(){
							var userupdate_id = $("#updateuserid option:selected").val();	
				    	    var useridval=userupdate_id.split('-')[0];
				    	    var userroletypeval=userupdate_id.split('-')[1];
				    	    var usercompanyid=userupdate_id.split('-')[2];
				    	    
							$.axs("../usermanagement/getCustomerDetail.htm", { 
								"type":"update",
				    	    	"userId" : useridval,
				    	    	"companyId":usercompanyid								
								}, function(data) { 
								$('#checkboxMenuTree').jstree({
									'core' : {
										'data' : data.groupVoCompanyWise,
										"themes" : {
											icons : false,
											dots : false
										}
									},
									'plugins' : [ "themes", "json_data", "checkbox","wholerow", "ui","changed" ]
								}).bind('loaded.jstree', function(e, data) {
									onloadlist =getOnloadCheckedGroupIds();
								      
							    }).on("changed.jstree", function (e, data) {
							    	//offloadlist = getCheckedGroupIds();
							    	
								    });;
								});
							}
						
						
						companys();
						function companys(){
						$.axs("../usermanagement/getcompanylist.htm", { 
			    	    	
						}, function(data) { 
							var  companylength =data.length ;
						  if((items.indexOf("navi-CA Installation") != -1)&&(roletypeval == 1)){ 
								initmenuchecked();
							}else if((items.indexOf("navi-CA Installation") != -1)&&(roletypeval == 2 ||roletypeval == 3 )){
								initmenuuncheked();
								if(companylength>1){$.bizinfo("Please select only one customer to user update? User will not update for multiple customer selection");}							
							}else if(((items.indexOf("tab-System Settings/Customer Registration") != -1) && !(items.indexOf("navi-home") != -1))&&(roletypeval == 1 )){
								initmenuchecked();
							}else if (((items.indexOf("tab-System Settings/Customer Registration") != -1) && !(items.indexOf("navi-home") != -1))&&(roletypeval == 2 ||roletypeval == 3 )){ 
								initmenuuncheked();
								console.log(companylength);
								if(companylength>1){$.bizinfo("Please select only one customer to user update? User will not update for multiple customer selection");}
							}else {							
								customergroup();								
								
							}
						});						
						}
						
						
						
			            
			        });
										
					$("#add_row")
					.on("click",function() {var newid = 0; 
					/*$('.Table_Middle').show();*/
								$.each($("#tab_logic tr"),function() {
													if (parseInt($(this).data("id")) > newid) {
														newid = parseInt($(this).data("id"));
														}});
								newid++;
								var tr = $("<tr></tr>", {
									id : "addr" + newid,
									"data-id" : newid
								});
								$.each($("#tab_logic tbody tr:nth(0) td"),
												function() {
													var cur_td = $(this);
													var children = cur_td
															.children();
													if ($(this).data("name") != undefined) {
														var td = $("<td></td>",{
																	"data-name" : $(cur_td).data("name"),
																	"class" : $(cur_td).data("name")
																});
														var c = $(cur_td).find($(children[0]).prop('tagName')).prop("disabled",false).clone().val("");
														c.attr("name",$(cur_td).data("name")+ newid);
														c.appendTo($(td));
														td.appendTo($(tr));
													} else {
														var td = $("<td></td>",{
																	'text' : $('#tab_logic tr').length
																})
															.appendTo($(tr));
													}
												});

								$(tr).find("td img.row-remove").hide();

								// add the new row
								$(tr).appendTo($('#tab_logic'));										
								
							//	$(tr).find("td img.save_row").show();
							//	$(tr).find("td img.edit_row").hide();
																																									
								
								var rolenamess ="";
								$(tr).find("td select.roletype").on(
										"change",
										function() {
			                
											var roletypeid	= $(this).val();	
											var option="";
											$.axs("../usermanagement/getFuncGrpList.htm", {
												"role_type_id" :roletypeid
					 				        }, function(data) {        	  
					 				        	console.log(data);	      	
					 				       $.each(data,function(i,l){
												option += '<option value="'+ data[i].functional_id + '">' + data[i].functional_name + '</option>';
											});
					 				      $(tr).find('td:eq(2) select').html(option);
					 				     $(tr).find('td:eq(2) select').trigger("chosen:updated");							 				      
									        });																																							
											return roletypeid;
										});
								
								$(tr).find("td img.row-remove").on(
										"click",
										function() {
											
											var rolenames	= $(tr).find("td input.rolename").val();
											$.axs("../usermanagement/getRoleList.htm", {  
												"companyId": ""
									        }, function(data) {        	        	
									        for(var i=0; i<data.length;i++){
												var role_type = data[i].role_name;
												var role_id =data[i].role_id;
												console.log(role_type+"....."+rolenames)
												if(role_type == rolenames ){ 
												$.axs("../usermanagement/deleteRole.htm", {
													"roleId" :role_id														
						 				        }, function(data) {       	        	
										        	if(data.errorMessage=="role.assignedto.other.user"){
										        		$.bizinfo("This role is assigned to an existing user and it can not be deleted");
										        	}else{
										        		$.bizinfo("Role deleted succesfully");
										        		$(tr).remove();
										        	}
										        },"post")
											}
												}
												
									        }, "post"); 
										
                                            
										});
								$(this).closest("tr").find("td input.rolename").on("change",function() {
									var rara = $(this).val();
									console.log(rara);
								});
								var prerolename = "";										
								$(tr).find("td input.rolename").on(
										"click",
										function() {
			                
											prerolename = $(this).val();
											console.log(prerolename);	
																																																		
											
										});
								
								$(tr).find("td img.edit_row").on(
										"click",
										function() {											
											$(this).closest("tr").contents().find("input").prop("disabled",false);
											$(this).closest("tr").contents().find("select").prop("disabled",false).trigger("chosen:updated");																																												
																						
											$(tr).find("td img.edit_row").hide();											
											$(tr).find("td img.delete").show();
											
										});
								
								$(tr).find("td img.delete").on(
										"click",
										function() {											
											
											var rolenames	= rolenamess;												
											var Functional	= $(this).closest("tr").find("td select.roll").val();
											//$.bizinfo("chalo"+Functional);
											var itemlist = [];
											itemlist =  $("#hiddenPermissionNameList").val();
											  // console.log(itemlist);
											  if(itemlist.indexOf("component-User Account/Update User/customerIdTree") != -1 ){												  
												  var roletypeid	= $(this).closest("tr").find("td select.chosen-select").val();
											  }else{
												  roletypeid = "3"
											  }	
											  Rolenamess	= $(tr).find("td input.rolename").val();
											$.axs("../usermanagement/getRoleList.htm", {  
												"companyId": ""
									        }, function(data) {        	        	
									        for(var i=0; i<data.length;i++){
												var role_type = data[i].role_name;
												var role_id =data[i].role_id;
												console.log(role_id+"....."+rolenames+"...."+role_type);
												
												if(role_type == prerolename){ 
												//	console.log(".");
													break;
												}else if (role_type == Rolenamess){
													break;									
												}else{
													
												}																											    
												}
									        console.log(role_id+"....."+rolenames+"...."+roletypeid+"...."+Functional);
									        Rolenamess	= $(tr).find("td input.rolename").val();
									        
									        if(Rolenamess == "" ||Rolenamess == null ||Functional == "" ||Functional == null ||roletypeid =="" ||roletypeid == null) {	
									        	$.bizinfo("Please enter required fields");														
											}else if(roletypeid == 1 && ((Functional.indexOf("1") != -1) ||(Functional.indexOf("2") != -1)||(Functional.indexOf("3") != -1)
													||(Functional.indexOf("4") != -1)||(Functional.indexOf("5") != -1)||(Functional.indexOf("6") != -1)||(Functional.indexOf("7") != -1)
													||(Functional.indexOf("8") != -1)||(Functional.indexOf("9") != -1)||(Functional.indexOf("10") != -1)
													)){
												$.bizinfo("Select functional groups related to Panasonic roletype");														
											}else if(roletypeid == 2 && ((Functional.indexOf("1") != -1) ||(Functional.indexOf("2") != -1)||(Functional.indexOf("3") != -1)
													||(Functional.indexOf("4") != -1)||(Functional.indexOf("5") != -1)||(Functional.indexOf("6") != -1)||(Functional.indexOf("15") != -1)
													||(Functional.indexOf("16") != -1)||(Functional.indexOf("17") != -1)||(Functional.indexOf("18") != -1)||(Functional.indexOf("19") != -1)
													)){
												$.bizinfo("Select functional groups related to installer roletype");
												
											}else if(roletypeid == 3 && ((Functional.indexOf("7") != -1) ||(Functional.indexOf("8") != -1)||(Functional.indexOf("9") != -1)
													||(Functional.indexOf("10") != -1)||(Functional.indexOf("15") != -1)
													||(Functional.indexOf("16") != -1)||(Functional.indexOf("17") != -1)||(Functional.indexOf("18") != -1)||(Functional.indexOf("19") != -1)
													)){
												$.bizinfo("Select functional groups related to customer roletype");														
											}else if(items.indexOf("navi-CA Installation") != -1 && (roletypeid == 2 || roletypeid ==3)){
													$.bizinfo("Edit only Panasonic roles");
												   }else{
											$.axs("../usermanagement/edditRole.htm", {
												"role_name" :Rolenamess,
												"roletype_id" :roletypeid,
												"functional_id" :Functional.toString(),
												"roleId" :role_id	
					 				        }, function(data) {   console.log(data);    	        	
					 				        	if(data.errormessage=="role_name.exist"){
					 				        		$.bizinfo("Rolename exist");
					 				        		$(tr).find("input").prop("disabled",false);
									        		$(tr).find("select").prop("disabled",false).trigger("chosen:updated");
									        	}else{
									        		$.bizinfo("Role edited succesfully.");
									        		$(tr).find("input").prop("disabled",true);
									        		$(tr).find("select").prop("disabled",true).trigger("chosen:updated");
													$(tr).find("td img.edit_row").show();
													$(tr).find("td img.save_row").hide();
													$(tr).find("td img.delete").hide();
									        	}
									        },"post")
									       
									         }
									        }, "post"); 
											
											
											
											
											if(Rolenamess == "" ||Rolenamess == null ||Functional == "" ||Functional == null ||roletypeid =="" ||roletypeid == null) {	
									        														
											}else if(roletypeid == 1 && ((Functional.indexOf("1") != -1) ||(Functional.indexOf("2") != -1)||(Functional.indexOf("3") != -1)
													||(Functional.indexOf("4") != -1)||(Functional.indexOf("5") != -1)||(Functional.indexOf("6") != -1)||(Functional.indexOf("7") != -1)
													||(Functional.indexOf("8") != -1)||(Functional.indexOf("9") != -1)||(Functional.indexOf("10") != -1)
													)){
																									
											}else if(roletypeid == 2 && ((Functional.indexOf("1") != -1) ||(Functional.indexOf("2") != -1)||(Functional.indexOf("3") != -1)
													||(Functional.indexOf("4") != -1)||(Functional.indexOf("5") != -1)||(Functional.indexOf("6") != -1)||(Functional.indexOf("15") != -1)
													||(Functional.indexOf("16") != -1)||(Functional.indexOf("17") != -1)||(Functional.indexOf("18") != -1)||(Functional.indexOf("19") != -1)
													)){
												
												
											}else if(roletypeid == 3 && ((Functional.indexOf("7") != -1) ||(Functional.indexOf("8") != -1)||(Functional.indexOf("9") != -1)
													||(Functional.indexOf("10") != -1)||(Functional.indexOf("15") != -1)
													||(Functional.indexOf("16") != -1)||(Functional.indexOf("17") != -1)||(Functional.indexOf("18") != -1)||(Functional.indexOf("19") != -1)
													)){
																								
											}else if(items.indexOf("navi-CA Installation") != -1 && (roletypeid == 2 || roletypeid ==3)){
													
												   }else{	
														$(this).closest("tr").contents().find("input").prop("disabled",true);
														$(this).closest("tr").contents().find("select").prop("disabled",true).trigger("chosen:updated");
														$(tr).find("td img.edit_row").show();
														$(tr).find("td img.save_row").hide();
														$(tr).find("td img.delete").hide();
									       
									         }		
											

										});
								
								
								
								
								
								$(tr).find("td img.save_row").on("click",
										function() { 
									
									   
									var itemlist = [];
									itemlist =  $("#hiddenPermissionNameList").val();
									  // console.log(itemlist);
									  if(itemlist.indexOf("component-User Account/Update User/customerIdTree") != -1 ){												  
										  var roletypeid	= $(tr).find("td select.chosen-select").val();
									  }else{
										  roletypeid = "3"
									  }		
									  	var samerole = "";											  											  											  
										rolenamess	= $(tr).find("td input.rolename").val();												
										var Functional	= $(tr).find("td select.roll").val();
										$.axs("../usermanagement/getfullrolelist.htm", {  
											"companyId": ""
								        }, function(data) {    // console.log(data);  	
								        if(data.errorMessage !== 'no.records.found'){
								        for(var i=0; i<data.length;i++){
											var role_type = data[i].rolename;
											var role_id =data[i].roleid;
											//console.log(role_id+"....."+rolenames+"...."+role_type);
											
											if(role_type  === rolenamess){ 														
												samerole = role_type;														
											}else {														
											}																											    
											}

										
										//console.log(rolenamess+"......."+Functional+"..."+roletypeid+"........"+samerole);	
										if(rolenamess == "" ||rolenamess == null ||Functional == "" ||Functional == null ||roletypeid =="" ||roletypeid == null) {													
											$.bizinfo("Please enter required fields");													
										}/*else if (rolenamess == samerole){
											$.bizinfo("Duplicate role cannot create.(Rolename already exist)");													
										}*/
										else if((items.indexOf("navi-CA Installation") != -1 && items.indexOf("tab-System Settings/Cut Off Request") != -1) && (roletypeid == 2 || roletypeid ==3)){
												$.bizinfo("Create only Panasonic roles");
											   }else if(((items.indexOf("tab-System Settings/Customer Registration") != -1) && !(items.indexOf("navi-home") != -1))&&(roletypeid == 1 && (Functional.indexOf("19") != -1))){
												   $.bizinfo("You do not have the privilege to create another Panasonic admin role");
											   }/*else if((!(items.indexOf("navi-CA Installation") != -1) && items.indexOf("navi-home") != -1)&& Functional.indexOf("6") != -1 ){
												   $.bizinfo("Create only customer roles");
											   }*/else {
											$.axs("../usermanagement/addNewRole.htm", {
												"role_name" :rolenamess,
												"roletype_id" :roletypeid,
												"functional_id" :Functional.toString()
					 				        }, function(data) {       console.log(data);	        	
					 				       if(data.errormessage=="role_name.alreadyexist"){
								        		$.bizinfo("rolename already exist ");
								        		$(tr).find("input").prop("disabled",false);
								        		$(tr).find("select").prop("disabled",false).trigger("chosen:updated");
								        		$(tr).find("td img.save_row").show();
												$(tr).find("td img.edit_row").hide();												
								        	}else{
								        		$.bizinfo("Role added succesfully.");
								        		$(tr).find("input").prop("disabled",true);
								        		$(tr).find("select").prop("disabled",true).trigger("chosen:updated");
								        		$(tr).find("td img.save_row").hide();
												$(tr).find("td img.edit_row").show();
												$(tr).find("td img.row-remove").show();
												addnewrole();
								        	}
									        },"post"); 
											   }
																								
											
											
											
										
										 
								        }else {
								        	$.axs("../usermanagement/addNewRole.htm", {
												"role_name" :rolenamess,
												"roletype_id" :roletypeid,
												"functional_id" :Functional.toString()
					 				        }, function(data) {       console.log(data);	        	
					 				       if(data.errormessage =="role_name.alreadyexist"){
								        		$.bizinfo("rolename already exist");
								        		$(tr).find("input").prop("disabled",false);
								        		$(tr).find("select").prop("disabled",false).trigger("chosen:updated");
								        		$(tr).find("td img.save_row").show();
												$(tr).find("td img.edit_row").hide();
												
								        	}else{
								        		$.bizinfo("Role added succesfully.");
								        		$(tr).find("input").prop("disabled",true);
								        		$(tr).find("select").prop("disabled",true).trigger("chosen:updated");
								        		$(tr).find("td img.save_row").hide();
												$(tr).find("td img.edit_row").show();
												$(tr).find("td img.row-remove").prop("disabled",false);
												addnewrole();
								        	}
									        },"post"); 
								        	
											
								        
											
								        }
								        });
											
										
												
																				
										});

								//Modified by ravi
								$(tr).find('.chosen-select').chosen().width("95%");
								
							});
					
					
					
					
					
					
					
					
					
					$("#triggerclick")
							.on("click",function() {var newid = 0; 
							/*$('.Table_Middle').show();*/
										$.each($("#tab_logic tr"),function() {
															if (parseInt($(this).data("id")) > newid) {
																newid = parseInt($(this).data("id"));
																}});
										newid++;
										var tr = $("<tr></tr>", {
											id : "addr" + newid,
											"data-id" : newid
										});
										$.each($("#tab_logic tbody tr:nth(0) td"),
														function() {
															var cur_td = $(this);
															var children = cur_td
																	.children();
															if ($(this).data("name") != undefined) {
																var td = $("<td></td>",{
																			"data-name" : $(cur_td).data("name"),
																			"class" : $(cur_td).data("name")
																		});
																var c = $(cur_td).find($(children[0]).prop('tagName')).clone().val("");
																c.attr("name",$(cur_td).data("name")+ newid);
																c.appendTo($(td));
																td.appendTo($(tr));
															} else {
																var td = $("<td></td>",{
																			'text' : $('#tab_logic tr').length
																		})
																	.appendTo($(tr));
															}
														});

										

										// add the new row
										$(tr).appendTo($('#tab_logic'));
										
											
										$(tr).find("td img.save_row").show();
										$(tr).find("td img.edit_row").hide();
										var funopts = "";
										$.axs("../usermanagement/getRoleList.htm", {  
											
								        }, function(data) {//console.log(data);
								        if(data.errorMessage !== 'no.records.found'){
								        	$(data).each(function(i,l){

								        	var role_type = data[i].roletype_id;
								        	var function_group_ids = data[i].functionalgroupids.split(',');		
								        	
											$(tr).find('td input[name="name'+((parseInt(i)+1))+'"]').val(data[i++].role_name);
											$(tr).find('td input[name="name'+((parseInt(i)+1))+'"]').prop("disabled",true);

											$(tr).find('td select[name="mails'+((parseInt(i)))+'"]').val(parseInt(role_type));
											$(tr).find('td select[name="mails'+((parseInt(i)))+'"]').trigger("chosen:updated");
											$(tr).find('td select[name="mails'+((parseInt(i)))+'"]').prop("disabled",true).trigger("chosen:updated");
											
											$(tr).find('td select[name="mail'+((parseInt(i)))+'"]').val(function_group_ids);
											$(tr).find('td select[name="mail'+((parseInt(i)))+'"]').trigger("chosen:updated");
											$(tr).find('td select[name="mail'+((parseInt(i)))+'"]').prop("disabled",true).trigger("chosen:updated");
										//	console.log("my value"+$(tr).find('td input[name="name'+((parseInt(i)+1))+'"]').val());

											if($(tr).find('td input[name="name'+((parseInt(i)+1))+'"]').val()!="")
											{
												//console.log("my values are"+$(tr).find('td input[name="name'+((parseInt(i)+1))+'"]').val());
												$(tr).find('td .save_row').hide();
												$(tr).find('td .edit_row').show();
												//console.log("exit");
											}else {
												$(tr).find("td img.save_row").show();
												$(tr).find("td img.edit_row").hide(); 
											}
											//$(tr).find("td img.save_row").hide();
											//$(tr).find("td img.edit_row").show();
							        	}); 
								        }
								        
											
								        }, "post");
										
										
										
										var rolenamess ="";
										$(tr).find("td select.roletype").on(
												"change",
												function() {
					                
													var roletypeid	= $(this).val();	
													var option="";
													$.axs("../usermanagement/getFuncGrpList.htm", {
														"role_type_id" :roletypeid
							 				        }, function(data) {        	  
							 				        	console.log(data);	      	
							 				       $.each(data,function(i,l){
														option += '<option value="'+ data[i].functional_id + '">' + data[i].functional_name + '</option>';
													});
							 				      $(tr).find('td:eq(2) select').html(option);
							 				     $(tr).find('td:eq(2) select').trigger("chosen:updated");							 				      
											        });																																							
													return roletypeid;
												});
										
										$(tr).find("td img.row-remove").on(
												"click",
												function() {
													var rolenames	= $(tr).find("td input.rolename").val();
													$.axs("../usermanagement/getRoleList.htm", {  
														"companyId": ""
											        }, function(data) {        	        	
											        for(var i=0; i<data.length;i++){
														var role_type = data[i].role_name;
														var role_id =data[i].role_id;
														//console.log(role_type+"....."+rolenames)
														if(role_type == rolenames ){ 
														$.axs("../usermanagement/deleteRole.htm", {
															"roleId" :role_id														
								 				        }, function(data) {       	        	
												        	if(data.errorMessage=="role.assignedto.other.user"){
												        		$.bizinfo("This role is assigned to an existing user and it can not be deleted");
												        	}else{
												        		$.bizinfo("Role deleted succesfully");
												        		$(tr).remove();
												        	}
												        },"post")
													}
														}
														
											        }, "post");
                                                    
												});
										$(this).closest("tr").find("td input.rolename").on("change",function() {
											var rara = $(this).val();
											console.log(rara);
										});
										var prerolename = "";										
										$(tr).find("td input.rolename").on(
												"click",
												function() {
					                
													prerolename = $(this).val();
													console.log(prerolename);	
																																																				
													
												});
										
										$(tr).find("td img.edit_row").on(
												"click",
												function() {											
													$(this).closest("tr").contents().find("input").prop("disabled",false);
													$(this).closest("tr").contents().find("select").prop("disabled",false).trigger("chosen:updated");																																												
																								
													$(tr).find("td img.edit_row").hide();											
													$(tr).find("td img.delete").show();
													
												});
										
										var Rolenamess ='';
										var roletypeid ='';
										$(tr).find("td img.delete").on(
												"click",
												function() {											
													
													var rolenames	= rolenamess;												
													var Functional	= $(this).closest("tr").find("td select.roll").val();
													//$.bizinfo("chalo"+Functional);
													var itemlist = [];
													itemlist =  $("#hiddenPermissionNameList").val();
													  // console.log(itemlist);
													  if(itemlist.indexOf("component-User Account/Update User/customerIdTree") != -1 ){												  
														   roletypeid	= $(this).closest("tr").find("td select.chosen-select").val();
													  }else{
														  roletypeid = "3"
													  }	
													  Rolenamess	= $(tr).find("td input.rolename").val();
													$.axs("../usermanagement/getRoleList.htm", {  
														"companyId": ""
											        }, function(data) {        	        	
											        for(var i=0; i<data.length;i++){
														var role_type = data[i].role_name;
														var role_id =data[i].role_id;
														console.log(role_id+"....."+rolenames+"...."+role_type);
														
														if(role_type == prerolename){ 
														//	console.log(".");
															break;
														}else if (role_type == Rolenamess){
															break;									
														}else{
															
														}																											    
														}
											        console.log(role_id+"....."+rolenames+"...."+roletypeid+"...."+Functional);
											        Rolenamess	= $(tr).find("td input.rolename").val();
											        
											        if(Rolenamess == "" ||Rolenamess == null ||Functional == "" ||Functional == null ||roletypeid =="" ||roletypeid == null) {	
											        	$.bizinfo("Please enter required fields");														
													}else if(items.indexOf("navi-CA Installation") != -1 && (roletypeid == 2 || roletypeid ==3)){
														$.bizinfo("Edit only Panasonic roles");
													}else if(roletypeid == 1 && ((Functional.indexOf("1") != -1) ||(Functional.indexOf("2") != -1)||(Functional.indexOf("3") != -1)
															||(Functional.indexOf("4") != -1)||(Functional.indexOf("5") != -1)||(Functional.indexOf("6") != -1)||(Functional.indexOf("7") != -1)
															||(Functional.indexOf("8") != -1)||(Functional.indexOf("9") != -1)||(Functional.indexOf("10") != -1)
															)){
														$.bizinfo("Select functional groups related to Panasonic roletype");														
													}else if(roletypeid == 2 && ((Functional.indexOf("1") != -1) ||(Functional.indexOf("2") != -1)||(Functional.indexOf("3") != -1)
															||(Functional.indexOf("4") != -1)||(Functional.indexOf("5") != -1)||(Functional.indexOf("6") != -1)||(Functional.indexOf("15") != -1)
															||(Functional.indexOf("16") != -1)||(Functional.indexOf("17") != -1)||(Functional.indexOf("18") != -1)||(Functional.indexOf("19") != -1)
															)){
														$.bizinfo("Select functional groups related to installer roletype");
														
													}else if(roletypeid == 3 && ((Functional.indexOf("7") != -1) ||(Functional.indexOf("8") != -1)||(Functional.indexOf("9") != -1)
															||(Functional.indexOf("10") != -1)||(Functional.indexOf("15") != -1)
															||(Functional.indexOf("16") != -1)||(Functional.indexOf("17") != -1)||(Functional.indexOf("18") != -1)||(Functional.indexOf("19") != -1)
															)){
														$.bizinfo("Select functional groups related to customer roletype");														
													}else{
													$.axs("../usermanagement/edditRole.htm", {
														"role_name" :Rolenamess,
														"roletype_id" :roletypeid,
														"functional_id" :Functional.toString(),
														"roleId" :role_id	
							 				        }, function(data) {   console.log(data);    	        	
							 				        	if(data.errormessage=="role_name.exist"){
							 				        		$.bizinfo("Rolename exist");
											        	}else{
											        		$.bizinfo("Role edited succesfully.");
											        	}
											        },"post")
											       
											         }
											        }, "post"); 
													
													 if(Rolenamess == "" ||Rolenamess == null ||Functional == "" ||Functional == null ||roletypeid =="" ||roletypeid == null) {	
												        															
														}else if(roletypeid == 1 && ((Functional.indexOf("1") != -1) ||(Functional.indexOf("2") != -1)||(Functional.indexOf("3") != -1)
																||(Functional.indexOf("4") != -1)||(Functional.indexOf("5") != -1)||(Functional.indexOf("6") != -1)||(Functional.indexOf("7") != -1)
																||(Functional.indexOf("8") != -1)||(Functional.indexOf("9") != -1)||(Functional.indexOf("10") != -1)
																)){
																													
														}else if(roletypeid == 2 && ((Functional.indexOf("1") != -1) ||(Functional.indexOf("2") != -1)||(Functional.indexOf("3") != -1)
																||(Functional.indexOf("4") != -1)||(Functional.indexOf("5") != -1)||(Functional.indexOf("6") != -1)||(Functional.indexOf("15") != -1)
																||(Functional.indexOf("16") != -1)||(Functional.indexOf("17") != -1)||(Functional.indexOf("18") != -1)||(Functional.indexOf("19") != -1)
																)){
															
															
														}else if(roletypeid == 3 && ((Functional.indexOf("7") != -1) ||(Functional.indexOf("8") != -1)||(Functional.indexOf("9") != -1)
																||(Functional.indexOf("10") != -1)||(Functional.indexOf("15") != -1)
																||(Functional.indexOf("16") != -1)||(Functional.indexOf("17") != -1)||(Functional.indexOf("18") != -1)||(Functional.indexOf("19") != -1)
																)){
																												
														}else if(items.indexOf("navi-CA Installation") != -1 && (roletypeid == 2 || roletypeid ==3)){
																
															   }else{
																   $(this).closest("tr").contents().find("input").prop("disabled",true);
																	$(this).closest("tr").contents().find("select").prop("disabled",true).trigger("chosen:updated");
																	$(tr).find("td img.edit_row").show();
																	$(tr).find("td img.save_row").hide();
																	$(tr).find("td img.delete").hide();
															   }		
													
													
													
												});
																													
										// Modified by ravi
										$(tr).find('.chosen-select').chosen().width("95%");
										
									});
					
					
					
					
				

					// Sortable Code
					var fixHelperModified = function(e, tr) {
						var $originals = tr.children();
						var $helper = tr.clone();

						$helper.children().each(function(index) {
							$(this).width($originals.eq(index).width())
						});

						return $helper;
					};
					/*$(tr).find("td#role_types").hide();*/
					$(".table-sortable tbody").sortable({
						helper : fixHelperModified
					}).disableSelection();

					$(".table-sortable thead").disableSelection();

					//$("#add_row").trigger("click");
					$(document).on(
							"click",
							"#inner a[data-toggle='tab']",
							function() {
								var target = $(this).attr("href");
								if (target == "#newusertab"
										|| target == "#updateusertab") {

								} else {
									$(target + " .dataTables_scrollBody")
											.mCustomScrollbar("update");
									$(target + " .dataTables_scroll").resize();
								}
							});
					menu('user');
				});