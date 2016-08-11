<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=1920, initial-scale=1.0">
<title><spring:message code="label.usertitle" /></title>
<link rel="shortcut icon" href="../assets/img/favicon.ico">
<link href="../assets/css/bootstrap.min.css" rel="stylesheet">
<link href="../assets/font-awesome/css/font-awesome.css"
	rel="stylesheet">
<!-- Toastr style -->
<link href="../assets/css/plugins/toastr/toastr.min.css"
	rel="stylesheet">
<link href="../assets/css/animate.css" rel="stylesheet">


<link href="../assets/css/plugins/chosen/chosen.css" rel="stylesheet">

<link
	href="../assets/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">

<link
	href="../assets/css/plugins/jQuerymCustomScrollbar/jquery.mCustomScrollbar.css"
	rel="stylesheet">
<link href="../assets/css/style.css" rel="stylesheet">
<link href="../assets/css/plugins/jsTree/style.min.css" rel="stylesheet" />
<link href="../assets/css/user.css" rel="stylesheet">
<link href="../assets/css/notification.css" rel="stylesheet">
<link href="../assets/css/plugins/sweetalert/sweetalert.css"
	rel="stylesheet">
<link href="../assets/css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="../assets/css/plugins/dataTables/jquery.dataTables.min.css"
	rel="stylesheet">
<link href="../assets/css/plugins/dataTables/select.bootstrap.min.css"
	rel="stylesheet">
<link href="../assets/css/plugins/dataTables/select.dataTables.min.css"
	rel="stylesheet">



<!-- dashboard -->
<link type="text/css" rel="stylesheet" href="../assets/css/header.css">




<!-- Mainly scripts -->
<script src="../assets/js/jquery-2.1.1.js"></script>

<!-- jquery mCustomScrollbar -->
<script
	src="../assets/js/plugins/jQuerymCustomScrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
</head>
<body>
	<div id="wrapper">
		<%@ include file="../common/header.jsp"%>
		<div id="page-wrapper" class="gray-bg dashbard-1"
			style="margin-left: -15px; margin-right: -15px;">
			<div class="row">
				<%-- <%@ include file="../common/left_menutree.jsp"%> --%>

				<div class="col-xs-12">
					<%@ include file="../../../assets/template/user/userView.jsp"%>
				</div>

			</div>
		</div>
		<%@ include file="../../../assets/template/common/footer.jsp" %>
		<input id="curPageNameHidden" type="hidden" value="homeScreen">
		<input id="hiddenPermissionNameList" type="hidden" value="${sessionInfo.permissionNameList}">
	</div>

	<script src="../assets/js/bootstrap.min.js"></script>
	<script src="../assets/js/plugins/slimscroll/jquery.slimscroll.js"></script>
	<!-- Custom and plugin javascript -->
	<script src="../assets/js/jqueryAjax.js"></script>
	<script src="../assets/js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script src="../assets/js/plugins/pace/pace.min.js"></script>
	<!-- jQuery UI -->
	<script src="../assets/js/plugins/jquery-ui/jquery-ui.min.js"></script>
	<!-- Toastr -->
	<script src="../assets/js/plugins/toastr/toastr.min.js"></script>

	<!-- High Charts -->
	<script src="../assets/js/plugins/highcharts/highcharts.js"
		type="text/javascript"></script>
	<script src="../assets/js/plugins/highcharts/modules/exporting.js"></script>

	<!-- Chosen -->
	<script src="../assets/js/plugins/chosen/chosen.jquery.js"></script>

	<!-- Peity -->
	<script src="../assets/js/plugins/peity/jquery.peity.js"></script>
	<script src="../assets/js/demo/peity-demo.js"></script>
	<!-- P5 -->
	<script src="../assets/js/plugins/p5/p5.min.js"></script>
	<script src="../assets/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script src="../assets/js/plugins/idle-timer/idle-timer.min.js"></script>
	<!-- jsTree -->
	<script src="../assets/js/plugins/jsTree/jstree.js"></script>

	<script src="../assets/js/jquery.multiple.select.js"></script>

	<script src="../assets/js/plugins/iCheck/icheck.min.js"></script>
	<!-- dataTables -->
	<script src="../assets/js/plugins/dataTables/jquery.dataTables.min.js"></script>
	<script src="../assets/js/plugins/dataTables/dataTables.select.min.js"></script>
	<script
		src="../assets/js/plugins/dataTables/dataTables.bootstrap.min.js"></script>
		<!-- File Download -->
	<script src="../assets/js/plugins/FileSaver/Blob.js"></script>
	<script src="../assets/js/plugins/FileSaver/FileSaver.min.js"></script>
	<script src="../assets/js/util.js"></script>
	<script src="../assets/js/useraccount.js"></script>
	<script src="../assets/js/userupdate.js"></script>
	 <script src="../assets/js/userlog.js"></script> 
	 <!-- <script src="../assets/js/session.js"></script> -->
</body>
</html>
