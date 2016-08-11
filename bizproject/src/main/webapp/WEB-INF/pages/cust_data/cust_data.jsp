<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=1920, initial-scale=1.0">
<title><spring:message code="label.custdatatitle" /></title>
<link rel="shortcut icon" href="../assets/img/favicon.ico">
<link href="../assets/css/bootstrap.min.css" rel="stylesheet">
<link href="../assets/font-awesome/css/font-awesome.css" rel="stylesheet">
<!-- Toastr style -->
<link href="../assets/css/plugins/toastr/toastr.min.css" rel="stylesheet">
<link href="../assets/css/animate.css" rel="stylesheet">
<!-- Open Street Map -->
<link href="../assets/css/plugins/leaflet/leaflet.css" rel="stylesheet">
<link href="../assets/css/plugins/ionRangeSlider/normalize.css" rel="stylesheet">
<link href="../assets/css/plugins/ionRangeSlider/ion.rangeSlider.css" rel="stylesheet">
<link href="../assets/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css" rel="stylesheet">
<link href="../assets/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<link href="../assets/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="../assets/css/plugins/IcSwitch/lc_switch.css" rel="stylesheet">
<link href="../assets/css/plugins/weekpicker/week-picker-view.css" rel="stylesheet" />
<link href="../assets/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="../assets/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="../assets/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<link href="../assets/css/plugins/jQuerymCustomScrollbar/jquery.mCustomScrollbar.css" rel="stylesheet">
<link href="../assets/css/plugins/dataTables/jquery.dataTables.min.css" rel="stylesheet">
<link href="../assets/css/plugins/dataTables/select.bootstrap.min.css" rel="stylesheet">
<link href="../assets/css/plugins/dataTables/select.dataTables.min.css" rel="stylesheet">
<link href="../assets/css/style.css" rel="stylesheet">
<link href="../assets/css/plugins/jsTree/style.min.css" rel="stylesheet"/>
<link type="text/css" rel="stylesheet" href="../assets/css/header.css">
<link href="../assets/css/bootstrap-datetimepicker.css" rel="stylesheet"> 
<link href="../assets/css/ca_installation.css" rel="stylesheet"> 
<script src="../assets/js/jquery-2.1.1.js"></script>
<script src="../assets/js/plugins/jQuerymCustomScrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="../assets/js/jquery-2.1.1.js"></script> 
<script src="../assets/js/jqueryAjax.js"></script>
<script src="../assets/js/bootstrap.min.js"></script>
<script src="../assets/js/moment.js"></script>
<script src="../assets/js/bootstrap-datetimepicker.js"></script>
<script src="../assets/js/plugins/idle-timer/idle-timer.min.js"></script>
<script src="../assets/js/util.js"></script>
<script src="../assets/js/input_mask.js"></script>
<script src="../assets/js/jqueryAjax.js"></script>
<script src="../assets/js/plugins/slimscroll/jquery.slimscroll.js"></script>
<script src="../assets/js/plugins/jsTree/jstree.js"></script>
<script src="../assets/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="../assets/js/inspinia.js"></script>      
<script src="../assets/js/cust_data.js"></script>
<!-- <script src="../assets/js/session.js"></script>  -->
        <style>
        label {width:200px;}
        input[type=text],select{width:268px;}
        </style>
    </head>    
<body>

<div id="wrapper">
<%@ include file="../common/header.jsp"%>

<%@ include file="../../../assets/template/cust_data/cust_data.jsp"%> 	
</div>
</body>
</html>