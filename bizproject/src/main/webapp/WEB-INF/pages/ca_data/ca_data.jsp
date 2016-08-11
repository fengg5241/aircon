<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=1920, initial-scale=1.0">
<title>Panasonic Smart Cloud | CA Registration</title>
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
<link href="../assets/css/grouptopolgy.css" rel="stylesheet">
  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

<script src="../assets/js/jquery-2.1.1.js"></script>

<script src="../assets/js/jquery-2.1.1.js"></script> 
<script src="../assets/js/jqueryAjax.js"></script>
<script src="../assets/js/bootstrap.min.js"></script>
<script src="../assets/js/moment.js"></script>

<!-- <script src="../assets/js/bootstrap-datetimepicker.js"></script>-->
<!-- Idle Timer -->
<script src="../assets/js/plugins/idle-timer/idle-timer.min.js"></script>
<script src="../assets/js/input_mask.js"></script>
<script src="../assets/js/jqueryAjax.js"></script>
<script src="../assets/js/plugins/slimscroll/jquery.slimscroll.js"></script>
<script src="../assets/js/plugins/sweetalert/sweetalert.min.js"></script>
<!-- Open Street Map -->
<script src="../assets/js/plugins/leaflet/leaflet.js" type="text/javascript"></script>
<script src="../assets/js/customer_DistributionGroup.js"></script>   
<!-- dataTables -->
	<script src="../assets/js/plugins/dataTables/jquery.dataTables.min.js"></script>
	<script src="../assets/js/plugins/dataTables/dataTables.select.min.js"></script>
	<script src="../assets/js/plugins/dataTables/dataTables.bootstrap.min.js"></script>	
	<script src="../assets/js/util.js"></script>
	<script src="../assets/js/plugins/dataTables/moment.min.js"></script>
	<script src="../assets/js/plugins/dataTables/datetime-moment.js"></script>
	<script src="../assets/js/plugins/jsTree/jstree.js"></script>
	<script src="../assets/js/inspinia.js"></script>   
	  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
	     
	<script src="../assets/js/timepicker.js"></script>   	  
<script src="../assets/js/ca_installation.js"></script>   

 <script src="../assets/js/topolgy.js"></script>  
 
<!--  check box java script -->
<script src="../assets/js/plugins/multiselect/bootstrap-multiselect.js"></script>  
        <style>
        #RetriveTopologyId label {width:200px;}
        .ca_data_info > div > label{width:105px;}
        </style>
        
    </head>    
<body>


<div id="wrapper">
<%@ include file="../common/header.jsp"%>
<%@ include file="../../../assets/template/ca_data/ca_data.jsp"%> 
<%@ include file="../../../assets/template/retrieve_topology/RetrieveToplogy.jsp"%>
<%@ include file="../../../assets/template/distribution_group/DistributionGroup.jsp"%>

</div>
<script src="../assets/js/plugins/jQuerymCustomScrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="../assets/js/acconfig_ca.js"></script>  
<!-- <script src="../assets/js/session.js"></script>  -->
<script src="../assets/js/moment-timezone-with-data.js"></script>
</body>
</html>