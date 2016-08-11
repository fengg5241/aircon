$(document).ready(
		function() {
			menu('user');
			
			
		//	$('#updateuserid').html(option);
			//Get RoleList.(only User ID)
			getUserIdList();
			function getUserIdList()
		    {    			
				$.axs("../usermanagement/getmultiplecompanyUserGroup.htm", {    
					"type":"add",
					"companyids" :"1"
		        }, function(data) {
		        		console.log(data);   			        	
		        });	
		    }	
			
			
			initrolelist();
			function initrolelist(){
				$.axs("../usermanagement/getRoleList.htm", {  
					"companyId": ""
		        }, function(data) {    
		        	var optionrole='<option value="" disabled selected>Please Select</option>';
		        	if(data.length>0){
		        for(var i=0; i<data.length;i++){
		        	
					var role_type = data[i].role_name;							
					optionrole += "<option value="+data[i].role_id+"-"+data[i].roletype_id+">"+data[i].role_name+"</option>";
					}
		        
					$('#rolelist').html(optionrole);
		        	}
					//$('#updaterolelist').html(option);
		        }, "post");
			}
			
			$("#userid").keypress(function (e) {       
		        if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) { 
		        	$.bizinfo("Please click on generate userid");
		                  return false;
		       }
		      });	
			
			
			
			//Get UserIdListFull.(Full details of User ID)
			//getUserIdListFull();
			function getUserIdListFull(){
				
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
			
			//$("#checkboxMenuTrees").attr("disabled", "disabled").off('click');
			//Get User Group Tree hierarchy.
			
			onloadpage();
			function onloadpage() {
			var items = [];
			items =  $("#hiddenPermissionNameList").val();
			if((items.indexOf("navi-CA Installation") != -1)){
				initMenuTrees();					
			}else{
			initMenuTree();				
			}
			}
			
			
			function initMenuTree() { 
				$.axs("../usermanagement/getUserGroup.htm", { /*/usermanagement/getUsergroup.htm*/
					"type":"add"
				}, function(data) { console.log(data);
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
			
			function initMenuTrees() { 
				$.axs("../usermanagement/getUserGroup.htm", { /*/usermanagement/getUsergroup.htm*/
					"type":"add"
				}, function(data) { console.log(data);
					$('#checkboxMenuTrees').jstree({
						'core' : {
							'data' : data,
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
						$('#checkboxMenuTrees').jstree().check_all();
						$("#checkboxMenuTrees").attr("disabled", "disabled").off('click');						
				    });

				});
			}
			
			
			
			
			getUserIdListFull();
			$("#updateusertabs").click(function() {
				//getUserIdListFull();
				var option = '<option value="" disabled selected>Please Select</option>';
				$.axs("../usermanagement/getRoleList.htm", {  
					"companyId": ""
		        }, function(data) {        console.log(data);	        	
		        for(var i=0; i<data.length;i++){
					var role_type = data[i].role_name;							
					option += "<option  values="+data[i].role_name+"  value="+data[i].role_id+">"+data[i].role_name+"</option>";
					}
				//	$('#rolelist').html(option);
				//	$('#updaterolelist').html(option);
		        }, "post");
			});
			
			
			
			
			$("#newusertabs").click(function() {
				$.axs("../usermanagement/getRoleList.htm", {  
					"companyId": ""
		        }, function(data) {    
		        	var optionrole='<option value="" disabled selected>Please Select</option>';
		        	console.log(data);	        	
		        for(var i=0; i<data.length;i++){
		        	
					var role_type = data[i].role_name;							
					//optionrole += "<option values="+data[i].roletype_id+" value="+data[i].role_id+">"+data[i].role_name+"</option>";
					optionrole += "<option value="+data[i].role_id+"-"+data[i].roletype_id+">"+data[i].role_name+"</option>";
					}
		        
					$('#rolelist').html(optionrole);
					//$('#updaterolelist').html(option);
		        }, "post");
				
				onloadpage();
				
			});
			
			$("#rolelist").change(function(){
				var items = [];
				items =  $("#hiddenPermissionNameList").val();
				var Role_id = $("#rolelist option:selected").val();	
	    	    var roleidval=Role_id.split('-')[0];
	    	    var roletypeval=Role_id.split('-')[1];
				if(((items.indexOf("tab-System Settings/Customer Registration") != -1) && !(items.indexOf("navi-home") != -1))&&(roletypeval==1)){
					$.jstree.destroy ();
					initMenuTrees();
				}else if((items.indexOf("navi-CA Installation") != -1)){
					$.jstree.destroy ();
					initMenuTrees();
				}else{
					$.jstree.destroy ();
					initMenuTree();
				}
			});
					
			
			
			var today = new Date();
		    var dd = today.getDate();
		    var mm = today.getMonth()+1; 
		    var yyyy = today.getFullYear()
		    today = dd+'/'+mm+'/'+yyyy;
			
			$(document).on("click", "#registerUser", function(e) {
		    	var idList = getCheckedGroupIds();
		    	url = "../usermanagement/userRegistration.htm";		    	
		    	var items = [];
				items =  $("#hiddenPermissionNameList").val();
		    	var User_id = $("#userid").val();
	    	    var Role_id = $("#rolelist option:selected").val();	
	    	    var roleidval=Role_id.split('-')[0];
	    	    var roletypeval=Role_id.split('-')[1];
	    	  //  var roletype = $("#rolelist option:selected").values();
	    	    console.log(roleidval+"dfdfd"+roletypeval);
	    	    if((items.indexOf("navi-CA Installation") != -1)){
	    	    	if(User_id =="" ||User_id ==null){
			    		$.bizinfo("Please click on generate userid");		    		
			    	}else if(roleidval =="" ||roleidval ==null) {
			    		$.bizinfo("Please select role");		    		
			    	}else{		    				    				    				    				    		
			    		var User_id = $("#userid").val();
			    	    var Role_id = roleidval		
			    	    
			    	    $.ajax({
			                url: url,
			                beforeSend: function (request){
			                	getToken(request);
			                },
			                type: "POST",
			                data: {
			                	"companyId":"",
				    	    	"loginId" : User_id,
								"role_Id" : Role_id,
								"group_id" : idList.toString(),
								"AJAXREQUEST": true
			                },
			                success: function(data) {
			    	        	console.log(JSON.stringify(data));
			    	        	     
			    	            var Role_i= "New User Details_"+today;		    	            		    	           		    	            
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
					        	
			    	            $.bizinfo("Downloading new userid & password. Please do not delete the CSV file.");
			    	            getUserIdListFull();
			    	            getUserIdListFulltable();
			                }
			            });
			    	    
			    	    
			    	    
			    	    
			    	    
			    	  //  window.location.reload();
			    	    
				}
	    	    }else if(((items.indexOf("tab-System Settings/Customer Registration") != -1) && !(items.indexOf("navi-home") != -1))&&(roletypeval==3 || roletypeval==2)){
		    	if(User_id =="" ||User_id ==null){
		    		$.bizinfo("Please click on generate userid");		    		
		    	}else if(roleidval =="" ||roleidval ==null) {
		    		$.bizinfo("Please select role ");		    		
		    	}else if(idList.length == 0) {
		    		$.bizinfo("Please choose usergroup.");		    		
		    	}else if(unique.length>1){
		    		$.bizinfo("Please select only one customer ");
		    	}else{		    				    				    				    				    		
		    		var User_id = $("#userid").val();
		    	    var Role_id = roleidval		
		    	    		    	    		    	    
		    	    $.ajax({
		                url: url,
		                beforeSend: function (request){
		                	getToken(request);
		                },
		                type: "POST",
		                data: {
		                	"companyId":unique[0],
			    	    	"loginId" : User_id,
							"role_Id" : Role_id,
							"group_id" : idList.toString(),
							"AJAXREQUEST": true
		                },
		                success: function(data) {
		    	        	console.log(JSON.stringify(data));
		    	        	     
		    	        	var Role_i= "New User Details_"+today;		    	            		    	           		    	            
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
				        	
		    	            $.bizinfo("Downloading new userid & password. Please do not delete the CSV file.");
		    	            getUserIdListFull();
		    	            getUserIdListFulltable();
		                }
		            });
		    	    
		    	    
		    	    
		    	    
		    	    
		    	  
		    	    
			}
			
	    	    }else{
	    	    	if(User_id =="" ||User_id ==null){
			    		$.bizinfo("Please click on generate userid");		    		
			    	}else if(roleidval =="" ||roleidval ==null) {
			    		$.bizinfo("Please select role");		    		
			    	}else if(idList.length == 0) {
			    		$.bizinfo("Please choose usergroup.");		    		
			    	}else{		    				    				    				    				    		
			    		var User_id = $("#userid").val();
			    	    var Role_id = roleidval		
			    	    console.log(User_id+"............"+Role_id+"....."+unique);
			    	    
			    	    $.ajax({
			                url: url,
			                beforeSend: function (request){
			                	getToken(request);
			                },
			                type: "POST",
			                data: {
			                	"companyId":"",
				    	    	"loginId" : User_id,
								"role_Id" : Role_id,
								"group_id" : idList.toString(),
								"AJAXREQUEST": true
			                },
			                success: function(data) {
			    	        	console.log(JSON.stringify(data));
			    	        	     
			    	        	var Role_i= "New User Details_"+today;		    	            		    	           		    	            
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
			    	            
			    	            $.bizinfo("Downloading new userid & password. Please do not delete the CSV file.");
			    	            getUserIdListFull();
			    	            getUserIdListFulltable();
			                }
			            });
			    	  
			    	    
				}
	    	    }    	
		    	return false;
		    	
		    });
			
			
			
		
			
		});


	function getCheckedGroupIds() {
		var checkedIdList = new Array();
		var checkedcompanylist =new Array();
		var tree = $.jstree.reference('#checkboxMenuTrees');
		
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
							var $childNode = $('#checkboxMenuTrees').jstree().get_node(childNodeStr);														
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
		unique = checkedcompanylist.filter(function(itm,i,types) {
		    return i==types.indexOf(itm);
		});
		console.log(checkedcompanylist);
		console.log(unique);
		return checkedIdList;
		return unique;
	}
	
	function getCheckedControlGroupIds() {
		var checkedIdList = new Array();
		var tree = $.jstree.reference('#checkboxMenuTrees');
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
		var tree = $.jstree.reference('#checkboxMenuTrees');
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
	
	
	
	
	
	
	
	//menu highlight
	var menuid = ['home', 'acsettings', 'visualization', 'notification', 'schedule', 'settings', 'user'];
	
	function getKey(v){
		var k;
		$.each( menuid, function( key, value ) {
		  if(v == value){
			  k = key;
		  }
		});
		return k;
	}
	
	function menu(e) {
		var id = getKey(e);
		$('#' + menuid[id]).addClass("active");
		if(id == 1){
			acSettingSelectedIcon();
		}
		for(var key in menuid){
			if(id != key){
				$('#' + menuid[key]).removeClass("active");
				if(key == 1){
					acSettingIcon();
				}
			}
		}
	}
	
	function menuDashBoard(data) {
		var id = getKey(data);
		if (id != null) {
			if (!$('#' + menuid[id]).hasClass('active')) {
				$('#' + menuid[id]).addClass('tabshover');
				if(id == 1){
					acSettingSelectedIcon();
				}
			}
			for(var key in menuid){
				if(key != id){
					$('#' + menuid[key]).removeClass('tabshover');
				}
			}
		}
	}
	
	function menuLeave(e) {
		for(var key in menuid){
			$('#' + menuid[key]).removeClass('tabshover');
			if(key == 1 && !$('#' + menuid[1]).hasClass('active')){
				acSettingIcon();
			}
		}
	}
	
	function acSettingSelectedIcon(){
		$('#' + menuid[1] +" > a > img").attr("src", "../assets/img/AC_Settings_Menu_Selected.png");
	}
	
	function acSettingIcon(){
		$('#' + menuid[1] +" > a > img").attr("src", "../assets/img/AC_Settings_Menu.png");
	}