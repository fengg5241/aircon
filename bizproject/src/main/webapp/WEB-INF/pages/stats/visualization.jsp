<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=1920, initial-scale=1.0">
        <title><spring:message code="label.visultitle" /></title>
        <link rel="shortcut icon" href="../assets/img/favicon.ico">
        <link href="../assets/css/bootstrap.min.css" rel="stylesheet">
        <link href="../assets/font-awesome/css/font-awesome.css" rel="stylesheet">
        <!-- Toastr style -->
        <link href="../assets/css/plugins/toastr/toastr.min.css" rel="stylesheet">
        <link href="../assets/css/animate.css" rel="stylesheet">
        <link href="../assets/css/plugins/ionRangeSlider/normalize.css" rel="stylesheet">
        <link href="../assets/css/plugins/ionRangeSlider/ion.rangeSlider.css" rel="stylesheet">
        <link href="../assets/css/plugins/ionRangeSlider/ion.rangeSlider.skinModern.css" rel="stylesheet">
        <link href="../assets/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
        <link href="../assets/css/plugins/chosen/chosen.css" rel="stylesheet">
        <link href="../assets/css/plugins/IcSwitch/lc_switch.css" rel="stylesheet">
        <link href="../assets/css/plugins/weekpicker/week-picker-view.css" rel="stylesheet">
        <link href="../assets/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
        <link href="../assets/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
        <link href="../assets/css/plugins/jQuerymCustomScrollbar/jquery.mCustomScrollbar.css" rel="stylesheet">
        <link href="../assets/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
        <!-- dashboard -->
        <link href="../assets/css/plugins/iCheck/custom_blue.css" rel="stylesheet">
        <link href="../assets/css/plugins/jsTree/style.min.css" rel="stylesheet" />
        <link href="../assets/css/style.css" rel="stylesheet">
        <link rel="stylesheet" href="../assets/css/header.css">
        <!-- Mainly scripts -->
        <script src="../assets/js/jquery-2.1.1.js"></script>
        <!-- jquery mCustomScrollbar -->
        <script src="../assets/js/plugins/jQuerymCustomScrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
        <link href="../assets/css/plugins/dataTables/jquery.dataTables.min.css" rel="stylesheet">
		<link href="../assets/css/plugins/dataTables/select.bootstrap.min.css" rel="stylesheet">
		<link href="../assets/css/plugins/dataTables/select.dataTables.min.css" rel="stylesheet">
		<link rel="stylesheet" href="../assets/css/visualization.css">
    </head>
    <body>
        <%@ include file="../common/header.jsp"%>
         <div id="page-wrapper" class="gray-bg">
             <div class="row">
                 <%@ include file="../common/left_menutree.jsp"%>
                 <div id="rightViewFrame" class="rightViewFrame">
                     <%@ include file="../../../assets/template/stats/stats.jsp" %>
                 </div>
             </div>
         </div>
         <%@ include file="../../../assets/template/common/footer.jsp" %>
        <script src="../assets/js/bootstrap.min.js"></script>
        <script src="../assets/js/plugins/metisMenu/jquery.metisMenu.js"></script>
        <script src="../assets/js/plugins/slimscroll/jquery.slimscroll.js"></script>
        <!-- Custom and plugin javascript -->
        <script src="../assets/js/jqueryAjax.js"></script>
        <!-- jsTree -->
        <script src="../assets/js/plugins/jsTree/jstree.js"></script>
        <script src="../assets/js/inspinia.js"></script>
        <script src="../assets/js/plugins/pace/pace.min.js"></script>
        <!-- jQuery UI -->
        <script src="../assets/js/plugins/jquery-ui/jquery-ui.min.js"></script>
        <!-- Toastr -->
        <script src="../assets/js/plugins/toastr/toastr.min.js"></script>
        <!-- High Charts -->
        <script src="../assets/js/plugins/highcharts/highcharts-custom.js"></script>
        <!-- IonRangeSlider -->
        <script src="../assets/js/plugins/ionRangeSlider/ion.rangeSlider.js"></script>
        <!-- Data picker -->
        <script src="../assets/js/plugins/datapicker/bootstrap-datepicker.js"></script>
        <!-- Chosen -->
        <script src="../assets/js/plugins/chosen/chosen.jquery.js"></script>
        <script src="../assets/js/plugins/sweetalert/sweetalert.min.js"></script>
        <!-- Idle Timer -->
		<script src="../assets/js/plugins/idle-timer/idle-timer.min.js"></script>
        <!-- IC Switch -->
        <script src="../assets/js/plugins/IcSwitch/lc_switch.js"></script>
        <!-- Week picker -->
        <script src="../assets/js/plugins/weekpicker/week-picker.js"></script>
        <!-- iCheck -->
        <script src="../assets/js/plugins/iCheck/icheck.min.js"></script>
        <!-- Data picker -->
        <script src="../assets/js/plugins/datapicker/bootstrap-datepicker.js"></script>
        <!-- dataTables -->
		<script src="../assets/js/plugins/dataTables/jquery.dataTables.min.js"></script>
		<script src="../assets/js/plugins/dataTables/dataTables.select.min.js"></script>
		<script src="../assets/js/plugins/dataTables/dataTables.bootstrap.min.js"></script>
		<!-- File Download -->
		<script src="../assets/js/plugins/FileSaver/Blob.js"></script>
		<script src="../assets/js/plugins/FileSaver/FileSaver.min.js"></script>
		<script src="../assets/js/plugins/FileSaver/FileSaverService.js"></script>
		<!-- custom -->
		<script src="../assets/js/util.js"></script>
        <script src="../assets/js/visualization.js"></script>
        <!-- <script src="../assets/js/session.js"></script> -->
    </body>
</html>