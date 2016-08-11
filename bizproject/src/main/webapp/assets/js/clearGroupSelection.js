$(document).ready(
		function() {
			$("#signin").on("click",function(){
				window.localStorage.removeItem("jstree");
			});
			$("#hi").click(function(e){				
				window.location.replace("../login/showChangePassword.htm");
			});
});