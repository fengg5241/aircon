//common alert start
(function($){ 
    $.fn.bizalert = function(param){
    	if (typeof param == 'string') {
    		var message = param;
    		param = {"message":message};
		}
    	
        var options = $.extend({}, $.fn.bizalert.defaults, param);
        this.each(function(){
        	$('#bizalertTitle').text(options.title);
        	$('#bizalertBody').text(options.message);
        	if(options.showConfirmButton){
        		 $("#bizalertConfirmButton").show();
        		 if(typeof options.confirmCallBack === 'function'){
            		 $("#bizalertConfirmButton").unbind().click(options.confirmCallBack);
            	}
        	}else {
        		$("#bizalertConfirmButton").hide();
        	}
        	
        	$('#'+options.id).modal(options);
        }); 
    };
    $.fn.bizalert.defaults = {"id":"bizalert","message":"","title":"","confirmCallBack":null,"showConfirmButton":false};
    
    $.fn.bizinfo = function(param){
    	if (typeof param == 'string') {
    		var message = param;
    		param = {"message":message};
		}
    	
        var options = $.extend({}, $.fn.bizinfo.defaults, param);
        this.each(function(){
        	$('#bizinfoTitle').text(options.title);
        	$('#bizinfoBody').html(options.message);
        	$('#bizinfo').modal();
        }); 
    };
    $.fn.bizinfo.defaults = {"id":"bizinfo","message":"","title":"","confirmCallBack":null,"showConfirmButton":false};
    
})(jQuery);

$(function() {
    //Vertically Centering Bootstrap Modals
    function reposition() {
        var modal = $(this), dialog = modal.find('.modal-dialog');
        modal.css('display', 'block');
        
        // Dividing by two centers the modal exactly, but dividing by three
        // or four works better for larger screens.
        dialog.css("margin-top", Math.max(0, ($(window).height() - dialog
                .height()) / 2));
    }
    // Reposition when a modal is shown
    $('.modal').on('show.bs.modal', reposition);
    // Reposition when the window is resized
    $(window).on('resize', function() {
        $('.modal:visible').each(reposition);
    });
    
    $.extend({ 
    		bizalert:function(options){$("#bizalert").bizalert(options);} 
    	});
    
    $.extend({ 
    		bizinfo:function(options){$("#bizinfo").bizinfo(options);} 
	});
});


//For CSRF

var authorizationToken = null;

$(document).bind("ajaxStart", function(){
	//if(authorizationToken == null){
		var data = {avoidCacheDate: new Date().getTime(), "AJAXREQUEST": true};
	    $.ajax({
	        type: "get",
	        data: data,
	        url: "../csrf/getCSRFToken.htm",
	        async:false,
	        success: function(d){
	        	if(d != "no.records.found" && d != "some.error.occurred"){
	        		//console.log("Got CSRF token");
	        		authorizationToken = d;
	        	}else{
	        		console.log("Error in getting CSRF token");
	        		authorizationToken = null;
	        	}
	        }
    	});
	//}
});

function getToken(request){
	request.setRequestHeader("ctk", authorizationToken);
}

