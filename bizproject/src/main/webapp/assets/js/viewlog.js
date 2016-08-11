$(document).ready(
		function() {
			
			var GLOBAL_VAL = {
					
					updateuser_viewlog_table: null,
			      
			    };
			
			initUserTable("");
		    function initUserTable(html) {
		        if (GLOBAL_VAL.updateuser_viewlog_table != null) {
		            GLOBAL_VAL.updateuser_viewlog_table.destroy();
		        }
		        
		        $("#updateuser_viewlog_table_body").html(html);    
		        GLOBAL_VAL.updateuser_viewlog_table = $('#updateuser_viewlog_table')
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
	                "aoColumns": [
	                              { "bSortable": false },
	                              { "bSortable": false },
	                              { "bSortable": false }	                              
	                            ] 
	            });
	    }
		        
		    function initUserScroll(){
		        $("#updateuser_viewlog_table_wrapper .dataTables_scrollBody")
		            .mCustomScrollbar({
		                scrollButtons: {
		                    enable: true
		                },
		                theme: "dark-2"
		            });
		    }
		    
			//View Update History.
			$(document).on("click", "#viewupdatehistory", function(e) {	
				var User_id = $("#updateuserid option:selected").val();
				var userListHtml = "";
				console.log("....."+User_id);
		    	    $.axs("../usermanagement/viewLog.htm", { 
		    	    	"user_id":User_id
					}, function(data) { console.log(data);
						for(var i=0; i<data.length;i++){							
							userListHtml += getUserIdHtml(data[i]);							
							}
						initUserTable(userListHtml);
			        	initUserScroll();

					});
		    	
		    });
			
			function getUserIdHtml(unitVO) {
		    	var refUnitId = "id" + unitVO.id;
				return "<tr id=" + refUnitId + "tr refDomId=" + refUnitId + ">"+
						getTDHtml(unitVO.date_time, "logdate") + getTDHtml(unitVO.update_data, "logupdateddata")  + getTDHtml(unitVO.update_by, "logupdatedby")+
						"</tr>"; 
		    }
			
			function getTDHtml(string, className) {
		        return "<td class='" + className + "'>" + string + "</td>";
		    }
			
			menu('user');
			
		});


