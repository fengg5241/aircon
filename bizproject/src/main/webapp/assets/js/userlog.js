

$(document).ready(function() {
    var GLOBAL_VAL = {
       
        OperLogTable:null,
        OperLogTables:null
      
        
        
        
    };
    
    // highlight menu
    menu('user');
          
    
    $('#operationLogModal').on('show.bs.modal', function (e) {
    	var userupdate_id = $("#updateuserid option:selected").val();	
	    var useridval=userupdate_id.split('-')[0];
	    var userroletypeval=userupdate_id.split('-')[1];
		var User_id =  useridval
    	if(User_id == "" || User_id == null){
    		
    		$.bizalert("Select User Id to View History.");	
    	}
	});
    	
    
    	$('#operationLogModal').on('shown.bs.modal', function (e) {
    		var userupdate_id = $("#updateuserid option:selected").val();	
    	    var useridval=userupdate_id.split('-')[0];
    	    var userroletypeval=userupdate_id.split('-')[1];
    		var User_id =  useridval  		
    		$.axs("../usermanagement/viewLog.htm", { 
    	    	"user_id":User_id
			}, function(data) { console.log(data);
			var acOperLogTableHtml = "";
            if (data != null && typeof(data) != "undefined") {
            	for (var i = 0; i < data.length; i++) {
					var log = data[i];
					acOperLogTableHtml += getOperLogLineHtml(log);
				}
            }
				
            initOperLogTable(acOperLogTableHtml);
            
            $("#operLogTable_wrapper .dataTables_scrollBody").mCustomScrollbar({
                scrollButtons: {
                    enable: true
                },
                theme: "dark-2"
            });
			});
    		
            
    	})
   
    
    function getOperLogLineHtml(unitVO) {
    		var user_name = $("#updateuserid option:selected").text();
        var html = "<tr>"+getTDHtml(unitVO.date_time, "logdate") + getTDHtml(user_name,"user_name")+ getTDHtml(unitVO.update_data, "logupdateddata")  + getTDHtml(unitVO.update_by, "logupdatedby") + "</tr>";
        
        return html;
    }
  
    function initOperLogTable(detailHtml){
        if (GLOBAL_VAL.operLogTable != null) {
        	GLOBAL_VAL.operLogTable.destroy();
		}
        $("#acOperLogTbody").html(detailHtml);
        
        GLOBAL_VAL.operLogTable = $("#operLogTable").DataTable({
            "ordering": true,
            "paging":   false,
            "filter": true,
            "info" : false,
            "responsive":   true,
            "deferRender":    true,
            "scrollCollapse": false,
            "scroller": true,
            "scrollY":  "300px",
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

  /* start  */
    $('#roleLogModal').on('shown.bs.modal', function (e) {
		
		$.axs("../usermanagement/viewRoleLog.htm", { 
			"companyId" :1
		}, function(data) { console.log(data);
		var acRoleLogTableHtml = "";
        if (data != null && typeof(data) != "undefined") {
        	for (var i = 0; i < data.length; i++) {
				var log = data[i];
				acRoleLogTableHtml += getOperLogsLineHtml(log);
			}
        }
			
        initOperLogTables(acRoleLogTableHtml);
        
        $("#operLogTables_wrapper .dataTables_scrollBody").mCustomScrollbar({
            scrollButtons: {
                enable: true
            },
            theme: "dark-2"
        });
		});
		
        
	})
	function initOperLogTables(detailHtml){
        if (GLOBAL_VAL.operLogTables != null) {
        	GLOBAL_VAL.operLogTables.destroy();
		}
        $("#acOperLogTbodys").html(detailHtml);
        
        GLOBAL_VAL.operLogTables = $("#operLogTables").DataTable({
            "ordering": true,
            "paging":   false,
            "filter": true,
            "info" : false,
            "responsive":   true,
            "deferRender":    true,
            "scrollCollapse": false,
            "scroller": true,
            "scrollY":  "300px",
            "language": {
			          "searchPlaceholder": "Search"
			 },
			 "search": {
	   			    "caseInsensitive": false
			  }
        });
    }
    
    function getOperLogsLineHtml(unitVO) {
        var html = "<tr>"+getTDHtml(unitVO.action, "roleaction") + getTDHtml(unitVO.roleName, "rolename")  + getTDHtml(unitVO.string_agg, "logupdatedby")+
		getTDHtml(unitVO.login_id, "roleactionby")+getTDHtml(unitVO.updatedate, "roledateandtime")+ "</tr>";
        
        return html;
    }
});