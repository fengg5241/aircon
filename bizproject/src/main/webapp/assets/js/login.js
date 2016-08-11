$(document).ready(
		function() {
			
			
			/*window.onbeforeunload = function() {
		        return "Do you want to close your session Press ";
		    }*/
			
			/*$("#language").change(function () {
				
				var englishlang="germany";
				var germanlang="english";
				var firstDropVal = $('#language').val();
				var hash = location.hash.replace('?','');
				if(firstDropVal == englishlang){
				var myURL = document.location;
				document.location = myURL + "?lang=de";
				
			}else {
				
				document.location = myURL + "?lang=en";
			}
				window.location.reload(false) 
				localStorage.setItem('language', $('option:selected', this).index());
				
		    });
			
			if (localStorage.getItem('language')) {
		        $("#language option").eq(localStorage.getItem('language')).prop('selected', true);
		    }*/
			/*language();*/
			/*formValidation();*/
			function language()
			{
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
					$('.chosen-select').width("500px");
			        $('.chosen-select-deselect').chosen({ allow_single_deselect: true });
					for ( var selector in config) {
						$("#select").chosen(config[selector]);			
						$("#language").chosen(config[selector]);

					}
			}
			
			$('.dropdown-inverse li > a').click(function(e){
			    $('.status').text(this.innerHTML);
			});
		
			/*$("#hi").click(function(e){				
				window.location.replace("../login/showChangePassword.htm");
			});*/
			//Default Login page.
			$("#signin").click(function() { 
				var username = document.getElementById("username").value;
	            var password = document.getElementById("password").value;
	            
	            
	            if ((username == null || username == "") && (password == null || password == "")){ 
	            	document.getElementById('userred').innerText = "Please Enter UserID."; 
	            	document.getElementById('passwordred').innerText = "Please Enter Password."; 	            		            		              														              					
					return false;		            
	            }
	            else if ((username != null || username != "") && (password == null || password == "")){
	            	document.getElementById('passwordred').innerText = "Please Enter Password."; 	            		            		              														              					
					return false;		            
	            }
	            else if ((username == null || username == "") && (password != null || password != "")){
	            	document.getElementById('userred').innerText = "Please Enter UserID."; 	            		            		              														              					
					return false;		            
	            }
	            /*else if (password.length < 4 || password.length > 20) {
	            	document.getElementById('passwordred').innerText = "Please Enter Password between 4 to 20 characters"; 	            		            		              					
					return false;
	            }
				else if (password.length > 4 || password.length < 20) {
	            	$("#passwordred").hide();	            	
					return false;
	            } */
	            else if (username == password) { 
	            	$("#passwordred").show();        		
	            	document.getElementById('passwordred').innerText = "password must be different than userid"; 	            		            		              					
					return false;
	            }
				
			});
			
			$('#username').blur(function(){ 
				var username = document.getElementById("username").value;				
				if (username == null || username == "") {
	            	document.getElementById('userred').innerText = "Please Enter UserID"; 	            		            		              														              					
					return false;		            
	            }				
				else if (username.length < 4 || username.length > 20) {
	            	document.getElementById('userred').innerText = "Please Enter between 4 to 20 characters"; 	            		            		              					
					return false;
	            }
				else if (username.length > 4 || username.length < 20) {
	            	$("#userred").hide();	              					
					return false;
	            }
			})
			
			
			$('#password').blur(function(){ 
				var username = document.getElementById("username").value;
				var password = document.getElementById("password").value;
				if (password == null || password == "") {
	            	document.getElementById('passwordred').innerText = "Please Enter Password"; 	            		            		              														              					
					return false;		            
	            }				
				else if (password.length < 8 || password.length > 20) {
	            	document.getElementById('passwordred').innerText = "Please Enter between 8 to 20 characters with atleast 1 alphabet and 1 character"; 	            		            		              					
					return false;
	            }
				else if (password.length > 8 || password.length < 20) {
	            	$("#passwordred").hide();	              					
					return false;
	            }
			})
					
			//Change Password Logic.
			$('#changeuser').blur(function(){ 
				var changeuser = document.getElementById("changeuser").value;
				var changepass = document.getElementById("changepass").value;
				var changenewpass = document.getElementById("changenewpass").value;
				var changecnfpass = document.getElementById("changecnfpass").value;				
				if (changeuser == null || changeuser == "") {
	            	document.getElementById('changeuserred').innerText = "Please Enter UserID"; 	            		            		              														              					
					return false;		            
	            }				
				else if (changeuser.length < 4 || changeuser.length > 20) {
	            	document.getElementById('changeuserred').innerText = "Please Enter between 4 to 20 characters"; 	            		            		              					
					return false;
	            }
				else if (changeuser.length > 4 || changeuser.length < 20) {
	            	$("#changeuserred").hide();	              					
					return false;
	            }
			})
			
			
			$('#changepass').blur(function(){ 
				var changeuser = document.getElementById("changeuser").value;
				var changepass = document.getElementById("changepass").value;
				var changenewpass = document.getElementById("changenewpass").value;
				var changecnfpass = document.getElementById("changecnfpass").value;
				if (changepass == null || changepass == "") {
	            	document.getElementById('changepassred').innerText = "Please Enter Password"; 	            		            		              														              					
					return false;		            
	            }				
				else if (changepass.length < 8 || changepass.length > 20) {
	            	document.getElementById('changepassred').innerText = "Please Enter between 8 to 20 characters with atleast 1 alphabet and 1 character"; 	            		            		              					
					return false;
	            }
				else if (changeuser == changepass) { 
	            	$("#changepassred").show();        		
	            	document.getElementById('changepassred').innerText = "Currentpassword must be different than User ID"; 	            		            		              					
					return false;
	            }
				else if (changepass.length > 8 || changepass.length < 20) {
	            	$("#changepassred").hide();	              					
					return false;
	            }
			})
			
			$('#changenewpass').blur(function(){ 
				var changeuser = document.getElementById("changeuser").value;
				var changepass = document.getElementById("changepass").value;
				var changenewpass = document.getElementById("changenewpass").value;
				var changecnfpass = document.getElementById("changecnfpass").value;
				if (changenewpass == null || changenewpass == "") {
	            	document.getElementById('changenewpassred').innerText = "Please Enter Password"; 	            		            		              														              					
					return false;		            
	            }				
				else if (changenewpass.length < 8 || changenewpass.length > 20) {
	            	document.getElementById('changenewpassred').innerText = "Please Enter between 8 to 20 characters with atleast 1 alphabet and 1 character"; 	            		            		              					
					return false;
	            }
				else if (changepass == changenewpass) { 
	            	$("#changenewpassred").show();        		
	            	document.getElementById('changenewpassred').innerText = "Newpassword must be different than Current Password"; 	            		            		              					
					return false;
	            }
				else if (changenewpass.length > 8 && changenewpass.length < 20) {
	            	$("#changenewpassred").hide();	              					
					return false;
	            }
				
			})
			
			$('#changecnfpass').blur(function(){ 
				var changeuser = document.getElementById("changeuser").value;
				var changepass = document.getElementById("changepass").value;
				var changenewpass = document.getElementById("changenewpass").value;
				var changecnfpass = document.getElementById("changecnfpass").value;
				if (changecnfpass == null || changecnfpass == "") {
	            	document.getElementById('changecnfpassred').innerText = "Please Enter Password"; 	            		            		              														              					
					return false;		            
	            }				
				else if (changecnfpass.length < 8 || changecnfpass.length > 20) {
	            	document.getElementById('changecnfpassred').innerText = "Please Enter between 8 to 20 characters with atleast 1 alphabet and 1 character"; 	            		            		              					
					return false;
	            }
				else if (changecnfpass.length > 8 || changecnfpass.length < 20) {
	            	$("#changecnfpassred").hide();	              					
					return false;
	            }
			})
			
			$("#changesumbit").click(function() {
				var changeuser = document.getElementById("changeuser").value;
				var changepass = document.getElementById("changepass").value;
				var changenewpass = document.getElementById("changenewpass").value;
				var changecnfpass = document.getElementById("changecnfpass").value;
				
				if ((changeuser == null || changeuser == "") && (changepass == null || changepass == "")){ 
	            	document.getElementById('changeuserred').innerText = "Please Enter UserID."; 
	            	document.getElementById('changepassred').innerText = "Please Enter Current Password."; 	            		            		              														              					
					return false;		            
	            }
				else if ((changeuser != null || changeuser != "") && (changepass != null || changepass != "") && (changenewpass == null || changenewpass == "") && (changecnfpass == null || changecnfpass == "")){ 
	            	document.getElementById('changenewpassred').innerText = "Please Enter New password."; 
	            	document.getElementById('changecnfpassred').innerText = "Please Enter Confirm Password."; 	            		            		              														              					
					return false;		            
	            }
				
			});
			
			
			//First Time login.
			$('#firstusername').blur(function(){ 
				var firstusername = document.getElementById("firstusername").value;
							
				if (firstusername == null || firstusername == "") {
	            	document.getElementById('firstusernamered').innerText = "Please Enter UserID"; 	            		            		              														              					
					return false;		            
	            }				
				else if (firstusername.length < 4 || firstusername.length > 20) {
	            	document.getElementById('firstusernamered').innerText = "Please Enter between 4 to 20 characters"; 	            		            		              					
					return false;
	            }
				else if (firstusername.length > 4 || firstusername.length < 20) {
	            	$("#firstusernamered").hide();	              					
					return false;
	            }
			})
			
			$('#firstnewuserid').blur(function(){ 
				var firstusername = document.getElementById("firstusername").value;
				var firstnewuserid = document.getElementById("firstnewuserid").value;
				var firstcurrentpassword = document.getElementById("firstcurrentpassword").value;
				var firstnewpassword = document.getElementById("firstnewpassword").value;
				if (firstnewuserid == null || firstnewuserid == "") {
	            	document.getElementById('firstnewuseridred').innerText = "Please Enter New User ID"; 	            		            		              														              					
					return false;		            
	            }				
				else if (firstnewuserid.length < 4 || firstnewuserid.length > 20) {
	            	document.getElementById('firstnewuseridred').innerText = "Please Enter between 4 to 20 characters"; 	            		            		              					
					return false;
	            }
				else if (firstusername == firstnewuserid) { 
	            	$("#firstnewuseridred").show();        		
	            	document.getElementById('firstnewuseridred').innerText = "New User ID must be different than Current User ID"; 	            		            		              					
					return false;
	            }
				else if (firstnewuserid.length > 4 || firstnewuserid.length < 20) {
	            	$("#firstnewuseridred").hide();	              					
					return false;
	            }
			})
			
			$('#firstcurrentpassword').blur(function(){ 
				var firstusername = document.getElementById("firstusername").value;
				var firstnewuserid = document.getElementById("firstnewuserid").value;
				var firstcurrentpassword = document.getElementById("firstcurrentpassword").value;
				var firstnewpassword = document.getElementById("firstnewpassword").value;
				if (firstcurrentpassword == null || firstcurrentpassword == "") {
	            	document.getElementById('firstcurrentpasswordred').innerText = "Please Enter Password"; 	            		            		              														              					
					return false;		            
	            }				
				else if (firstcurrentpassword.length < 8 || firstcurrentpassword.length > 20) {
	            	document.getElementById('firstcurrentpasswordred').innerText = "Please Enter between 8 to 20 characters with atleast 1 alphabet and 1 character"; 	            		            		              					
					return false;
	            }
				else if (firstnewuserid == firstcurrentpassword) { 
	            	$("#firstcurrentpasswordred").show();        		
	            	document.getElementById('firstcurrentpasswordred').innerText = "Newpassword must be different than Current Password"; 	            		            		              					
					return false;
	            }
				else if (firstcurrentpassword.length > 8 && firstcurrentpassword.length < 20) {
	            	$("#firstcurrentpasswordred").hide();	              					
					return false;
	            }
				
			})
			
			$('#firstnewpassword').blur(function(){ 
				var firstusername = document.getElementById("firstusername").value;
				var firstnewuserid = document.getElementById("firstnewuserid").value;
				var firstcurrentpassword = document.getElementById("firstcurrentpassword").value;
				var firstnewpassword = document.getElementById("firstnewpassword").value;
				var firstconfirmpassword= document.getElementById("firstconfirmpassword").value;
				if (firstnewpassword == null || firstnewpassword == "") {
	            	document.getElementById('firstnewpasswordred').innerText = "Please Enter Password"; 	            		            		              														              					
					return false;		            
	            }				
				else if (firstnewpassword.length < 8 || firstnewpassword.length > 20) {
	            	document.getElementById('firstnewpasswordred').innerText = "Please Enter between 8 to 20 characters with atleast 1 alphabet and 1 character"; 	            		            		              					
					return false;
	            }
				else if (firstcurrentpassword == firstnewpassword) { 
	            	$("#firstnewpasswordred").show();        		
	            	document.getElementById('firstnewpasswordred').innerText = "Newpassword must be different than Current Password"; 	            		            		              					
					return false;
	            }
				else if (firstnewpassword.length > 8 && firstnewpassword.length < 20) {
	            	$("#firstnewpasswordred").hide();	              					
					return false;
	            }
				
			})
			
			$('#firstconfirmpassword').blur(function(){ 
				var firstusername = document.getElementById("firstusername").value;
				var firstnewuserid = document.getElementById("firstnewuserid").value;
				var firstcurrentpassword = document.getElementById("firstcurrentpassword").value;
				var firstnewpassword = document.getElementById("firstnewpassword").value;
				var firstconfirmpassword= document.getElementById("firstconfirmpassword").value;
				if (firstconfirmpassword == null || firstconfirmpassword == "") {
	            	document.getElementById('firstconfirmpasswordred').innerText = "Please Enter Password"; 	            		            		              														              					
					return false;		            
	            }				
				else if (firstconfirmpassword.length < 8 || firstconfirmpassword.length > 20) {
	            	document.getElementById('firstconfirmpasswordred').innerText = "Please Enter between 8 to 20 characters with atleast 1 alphabet and 1 character"; 	            		            		              					
					return false;
	            }
				else if (firstconfirmpassword != firstnewpassword) { 
	            	$("#firstconfirmpasswordred").show();        		
	            	document.getElementById('firstconfirmpasswordred').innerText = "ConfirmPassword must be same as New Password"; 	            		            		              					
					return false;
	            }
				else if (firstconfirmpassword.length > 8 && firstconfirmpassword.length < 20) {
	            	$("#firstconfirmpasswordred").hide();	              					
					return false;
	            }
				
			})
			
			
			
			$("#firstchangesubmit").click(function() {
				var firstusername = document.getElementById("firstusername").value;
				var firstnewuserid = document.getElementById("firstnewuserid").value;
				var firstcurrentpassword = document.getElementById("firstcurrentpassword").value;
				var firstnewpassword = document.getElementById("firstnewpassword").value;
				var firstconfirmpassword= document.getElementById("firstconfirmpassword").value;
				
				if ((firstusername == null || firstusername == "") && (firstnewuserid == null || firstnewuserid == "")&& (firstcurrentpassword == null || firstcurrentpassword == "")){ 
	            	document.getElementById('firstusernamered').innerText = "Please Enter UserID.";
	            	document.getElementById('firstnewuseridred').innerText = "Please Enter  New UserID.";
	            	document.getElementById('firstcurrentpasswordred').innerText = "Please Enter Current Password."; 	            		            		              														              					
					return false;		            
	            }
				else if ((firstusername == null || firstusername == "") && (firstnewuserid == null || firstnewuserid == "")&& (firstcurrentpassword == null || firstcurrentpassword == "")&& (firstnewpassword == null || firstnewpassword == "")&& (firstconfirmpassword == null || firstconfirmpassword == "")){ 
	            	document.getElementById('firstnewpasswordred').innerText = "Please Enter New password."; 
	            	document.getElementById('firstconfirmpasswordred').innerText = "Please Enter Confirm Password."; 	            		            		              														              					
					return false;		            
	            }
				
			});
			
			
			
			
			
			
			
			
			
			$("#loginForm").click(function() {
				var username = document.getElementById("username").value;
	            var password = document.getElementById("password").value;
	            if (username == null || username == "") {
	            	document.getElementById('user').innerText = "Please Enter Username"; 	            		            		              														              					
					return false;		            
	            }
	            if (username.length < 4 || username.length > 20) {
	            	document.getElementById('user').innerText = "Please Enter Userid between 4 to 20 characters"; 	            		            		              					
					return false;
	            }
	            if (username.length > 4 || username.length < 20) {
	            	$("#user").hide();	              					
					return false;
	            }
	            if (password == null || password == "") {            	
	            	document.getElementById('pass').innerText = "Please Enter Password"; 	            	           			              					
				return false;
	            }
	            if (password.length < 8 || password.length > 20) {	            	
	            	document.getElementById('pass').innerText = "Please Enter Password between 8 to 20 characters with atleast 1 alphabet and 1 number"; 	            	           			              					
				return false;
	            }
	            if (password.length > 8 || password.length < 20) {	            	
	            	$("#pass").hide(); 	            	           			              					
				return false;
	            }
			});
			
			$("#loginForms").click(function() {  
				var username = document.getElementById("username").value;
                var password = document.getElementById("password").value;
            
				if ((username == null || username == "") && (password == null || password == "" )) {
					document.getElementById('user').innerText = "Please Enter Username";
					/*document.getElementById('pass').innerText = "Please Enter Password"; */	
					return false;
				}
				else if(username == password){
				    	document.getElementById('pass').innerText = "Password is not same as UserID"; 	
				    }
								
				else  if (password.length < 8 || password.length > 20) {	            	
	            	document.getElementById('pass').innerText = "Please Enter Password between 8 to 20 characters with atleast 1 alphabet and 1 number"; 	            	           			              					
				return false;
	            }
				else  if (password.length > 8 || password.length < 20) {	            	
	            	$("#pass").hide(); 	            	           			              					
				return false;
				
				} 
				/*else if ((username != null || username != "") && (password == null || password == "" )) {
					$("#user").hide();
					document.getElementById('pass').innerText = "Please Enter Password"; 	
					return false;
				}*/
			
			});
			
			/*$("#language").click(function() { 
				
				var username = document.getElementById("username").value;
                var password = document.getElementById("password").value;
            
			
				if (password == null || password == "") {            	
	            	document.getElementById('pass').innerText = "Please Enter Password"; 	            	           			              					
				return false;
	            }
				else  if (password.length < 8 || password.length > 20) {	            	
	            	document.getElementById('pass').innerText = "Please Enter Password between 8 to 20 characters with atleast 1 alphabet and 1 number"; 	            	           			              					
				return false;
	            }
				else  if (password.length > 8 || password.length < 20) {	            	
	            	$("#pass").hide(); 	            	           			              					
				return false;
	            }
			});*/
			
			
			/*-- First Time Login --*/
			$("#firstloginForm").click(function() { $.bizalert("hi");
				var Currentuserid = document.getElementById("username").value;
	            var newuserid = document.getElementById("newuserid").value;
	            var currentpassword = document.getElementById("currentpassword").value;
	            var newpassword = document.getElementById("newpassword").value;
	            var confirmpassword = document.getElementById("confirmpassword").value;
	            
	            if (Currentuserid == null || Currentuserid == "") {
	            	document.getElementById('head').innerText = "Please Enter Username"; 	            		            		              														              					
					return false;		            
	            }
	            if (username.length < 4 || username.length > 20) {
	            	document.getElementById('user').innerText = "Please Enter Userid between 4 to 20 characters"; 	            		            		              					
					return false;
	            }
	            if (username.length > 4 || username.length < 20) {
	            	$("#user").hide();	              					
					return false;
	            }
	            if (password == null || password == "") {            	
	            	document.getElementById('pass').innerText = "Please Enter Password"; 	            	           			              					
				return false;
	            }
	            if (password.length < 8 || password.length > 20) {	            	
	            	document.getElementById('pass').innerText = "Please Enter Password between 8 to 20 characters with atleast 1 alphabet and 1 number"; 	            	           			              					
				return false;
	            }
	            if (password.length > 8 || password.length < 20) {	            	
	            	$("#pass").hide(); 	            	           			              					
				return false;
	            }
			});
			
			
			
			
			/*--  CHANGE PASSWORD VALIDATION LOGIC --*/
			
			$("#changeuserid").click(function() {
				var username = document.getElementById("changeuserid").value;
				var password = document.getElementById("changepassword").value;
				var usernamenew = document.getElementById("changenewpassword").value;
				var passwordnew = document.getElementById("changeconfirmpassword").value;
	            /*if ((password != null || password != "")||(usernamenew != null || usernamenew != "")||(passwordnew != null || passwordnew != "")) {	            	
	            	$("#changepasswordred,#changenewpasswordred,#changeconfirmpasswordred").hide();
					return false;		            
	            }*/	            	            
			});
			
			
			
			$("#changepassword").click(function() {
				var username = document.getElementById("changeuserid").value;
				var password = document.getElementById("changepassword").value;
				var usernamenew = document.getElementById("changenewpassword").value;
				var passwordnew = document.getElementById("changeconfirmpassword").value;
	            if (username == null || username == "") {
	            	$("#changeuseridred").show();
	            	document.getElementById('changeuseridred').innerText = "Please Enter Userid"; 	            	
					return false;		            
	            }	            
	            else if (username.length < 4 || username.length > 20)  {
	            	$("#changeuseridred").show();
	            	document.getElementById('changeuseridred').innerText = "Please Enter Userid between 4 to 20 characters";	            		            	
					return false;
	            }
	            else  if (username.length > 4 || username.length < 20) {
	            	$("#changeuseridred").hide();	              					
					return false;
	            }
	            else if ((username != null || username != "")||(usernamenew != null || usernamenew != "")||(passwordnew != null || passwordnew != "")) {	            	
	            	$("#changeuseridred,#changenewpasswordred,#changeconfirmpasswordred").hide();
					return false;		            
	            }	            
			});
			
			$("#changenewpassword").click(function() {
				var username = document.getElementById("changeuserid").value;
				var password = document.getElementById("changepassword").value;
				var usernamenew = document.getElementById("changenewpassword").value;
				var passwordnew = document.getElementById("changeconfirmpassword").value;
				console.log(username+"..."+password);
				if (username == null || username == "") {
	            	document.getElementById('changeuseridred').innerText = "Please Enter Userid"; 
	            	$("#changeuseridred").show();	
					return false;		            
	            }
				else  if (password == null || password == "") {
	            	document.getElementById('changepasswordred').innerText = "Please Enter Current Password"; 
	            	$("#changepasswordred").show();	
					return false;		            
	            }
	         
	            else if ((username == null || username == "") &&(password == null || password == "")) {$("#changeuseridred").show();$("#changepasswordred").show();
	            	document.getElementById('changeuseridred').innerText = "Please Enter User Id";
	            	document.getElementById('changepasswordred').innerText = "Please Enter Current Password";	            	
					return false;		            
	            }
	            else if (password.length < 8 || password.length > 20) {
	            	document.getElementById('changepasswordred').innerText = "Please Enter Password between 4 to 20 characters";
	            	$("#changepasswordred").show();	
					return false;
	            }
	            else if (password.length > 8 && password.length < 20) {
	            	$("#changepasswordred").hide();	              					
					return false;
	            }
	            
	            if (username == password) {
	            	$("#changepasswordred").show();	
	            	document.getElementById('changepasswordred').innerText = "password must be different than userid"; 	            		            		              					
					return false;
	            }
	            /*if (password.match(/\d/)  || password.match(/[a-zA-Z]/) ) { $.bizalert("hi");
            	document.getElementById('changepasswordred').innerText = "Password must contain 4 to 20 characters and 1 alphabet and 1 number"; 	              					
				return false;
	            }*/
	            /*re = /^(?=.*\d)(?=.*[a-zA-Z]).{4,20}$/;
	            if(password.match(re)) {
	              $.bizalert("Error: password must contain at least one number (0-9)!");
	              form.pwd1.focus();
	              return false;
	            }
	            re = /[A-Z]/;
	            if(!re.test(form.pwd1.value)) {
	              $.bizalert("Error: password must contain at least one uppercase letter (A-Z)!");
	              form.pwd1.focus();
	              return false;
	            }*/
			});
			
			$("#changeconfirmpassword").click(function() {
				var username = document.getElementById("changeuserid").value;
				var password = document.getElementById("changepassword").value;
				var usernamenew = document.getElementById("changenewpassword").value;
				var passwordnew = document.getElementById("changeconfirmpassword").value;
				if (username == null || username == "") {
	            	document.getElementById('changeuseridred').innerText = "Please Enter Userid"; 
	            	$("#changeuseridred").show();	
					return false;		            
	            }
				else  if (password == null || password == "") {
	            	document.getElementById('changepasswordred').innerText = "Please Enter Current Password"; 
	            	$("#changepasswordred").show();	
					return false;		            
	            }
				else  if (usernamenew == null || usernamenew == "") {
	            	document.getElementById('changenewpasswordred').innerText = "Please Enter New Password"; 
	            	$("#changenewpasswordred").show();	
					return false;		            
	            }
	            else if ((username == null || username == "") &&(password == null || password == "")&&(usernamenew == null || usernamenew == "")) {$("#changeuseridred").show();$("#changepasswordred").show();
	            	document.getElementById('changeuseridred').innerText = "Please Enter User Id";
	            	document.getElementById('changepasswordred').innerText = "Please Enter Current Password";
	            	document.getElementById('changenewpasswordred').innerText = "Please Enter New Password";	
					return false;		            
	            }
	            else if (usernamenew.length < 4 || usernamenew.length > 20) {
	            	document.getElementById('changenewpasswordred').innerText = "Please Enter Password between 4 to 20 characters";
	            	$("#changenewpasswordred").show();	
					return false;
	            }
	            else if (usernamenew.length > 4 || usernamenew.length < 20) {
	            	$("#changenewpasswordred").hide();	              					
					return false;
	            }
	            
	             if (password == usernamenew) {$("#changenewpasswordred").show();
	            	document.getElementById('changenewpasswordred').innerText = "newpassword must be different than current password"; 	            		            		              					
					return false;
	            }
	            
			});
			
			
			
			
			function formValidation() {
				// Make quick references to our fields.								
				var Username = document.getElementById('username');
				var Password = document.getElementById('password');
				var Language = document.getElementById('language');
				// To check empty form fields.
				if (Username.value.length == 0) {
				document.getElementById('head').innerText = "Please Enter valid Username"; 
				Username.focus();
				return false;
				}
				// Check each input in the order that it appears in the form.
				if (inputAlphabet(firstname, "* For your name please use alphabets only *")) {
				if (lengthDefine(username, 6, 8)) {
				if (emailValidation(email, "* Please enter a valid email address *")) {
				if (trueSelection(state, "* Please Choose any one option")) {
				if (textAlphanumeric(addr, "* For Address please use numbers and letters *")) {
				if (textNumeric(zip, "* Please enter a valid zip code *")) {
				return true;
				}
				}
				}
				}
				}
				}
				return false;
				}
				// Function that checks whether input text is numeric or not.
				function textNumeric(inputtext, alertMsg) {
				var numericExpression = /^[0-9]+$/;
				if (inputtext.value.match(numericExpression)) {
				return true;
				} else {
				document.getElementById('p6').innerText = alertMsg; // This segment displays the validation rule for zip.
				inputtext.focus();
				return false;
				}
				}
				// Function that checks whether input text is an alphabetic character or not.
				function inputAlphabet(inputtext, alertMsg) {
				var alphaExp = /^[a-zA-Z]+$/;
				if (inputtext.value.match(alphaExp)) {
				return true;
				} else {
				document.getElementById('p1').innerText = alertMsg; // This segment displays the validation rule for name.
				//alert(alertMsg);
				inputtext.focus();
				return false;
				}
				}
				// Function that checks whether input text includes alphabetic and numeric characters.
				function textAlphanumeric(inputtext, alertMsg) {
				var alphaExp = /^[0-9a-zA-Z]+$/;
				if (inputtext.value.match(alphaExp)) {
				return true;
				} else {
				document.getElementById('p5').innerText = alertMsg; // This segment displays the validation rule for address.
				inputtext.focus();
				return false;
				}
				}
				// Function that checks whether the input characters are restricted according to defined by user.
				function lengthDefine(inputtext, min, max) {
				var uInput = inputtext.value;
				if (uInput.length >= min && uInput.length <= max) {
				return true;
				} else {
				document.getElementById('p2').innerText = "* Please enter between " + min + " and " + max + " characters *"; // This segment displays the validation rule for username
				inputtext.focus();
				return false;
				}
				}
				// Function that checks whether a option is selected from the selector and if it's not it displays an alert message.
				function trueSelection(inputtext, alertMsg) {
				if (inputtext.value == "Please Choose") {
				document.getElementById('p4').innerText = alertMsg; //this segment displays the validation rule for selection.
				inputtext.focus();
				return false;
				} else {
				return true;
				}
				}
				// Function that checks whether an user entered valid email address or not and displays alert message on wrong email address format.
				function emailValidation(inputtext, alertMsg) {
				var emailExp = /^[w-.+]+@[a-zA-Z0-9.-]+.[a-zA-z0-9]{2,4}$/;
				if (inputtext.value.match(emailExp)) {
				return true;
				} else {
				document.getElementById('p3').innerText = alertMsg; // This segment displays the validation rule for email.
				inputtext.focus();
				return false;
				}
				}
			
		});