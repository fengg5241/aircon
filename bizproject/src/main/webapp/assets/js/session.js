$(document).ready(
		function() {
			
	var timeOutModal = $('#bizTimeOutalert').on('shown.bs.modal', function () {
	    clearTimeout(timeOutModal.data('hideInteval'))
	    var id = setTimeout(function(){
	        timeOutModal.modal('hide');
	        window.location.replace("../login/logout.htm");
	    },60000*3);
	    timeOutModal.data('hideInteval', id);
	})
	
	$("#bizTimeOutAlertConfirmButton").click(function(){
		window.location.reload();
//    	$("#displayButton").click();
	});
	
	$("#bizTimeOutAlertCloseButton").click(function(){
		 window.location.replace("../login/logout.htm");
	});
			
			//Session timeout.
    $( document ).idleTimer( 60000*60*3 );
	$( document ).on( "idle.idleTimer", function(event, elem, obj){	    	    
		$('#bizTimeOutalert').modal();
//		swal({
//			title: "User Session Expired due to Inactive State",		                
//            text: "Would you like to Continue Session or Automatically Logout in 3 Minutes?.",            
//            showCancelButton: true,
//            confirmButtonColor:"#76BB55",
//            cancelButtonColor: '#d33',
//            confirmButtonText: "Continue",
//            cancelButtonText: "Cancel",
//            timer: 60000*3,
//            closeOnConfirm: false,
//            closeOnCancel: false },
//        function (isConfirm) {
//            if (isConfirm) {                   
//            	window.location.reload();
//            	//$("#displayButton").click();
//            } else {
//            	window.location.replace("../login/logout.htm");
//            }
//        });
		
		
    });
});