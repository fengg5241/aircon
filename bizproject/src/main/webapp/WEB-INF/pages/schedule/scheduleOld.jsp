<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=1920, initial-scale=1.0">
<title>Panasonic Smart Cloud | Schedule</title>
<link rel="shortcut icon" href="../assets/img/favicon.ico">
<link href="../assets/css/bootstrap.min.css" rel="stylesheet">
<link href="../assets/font-awesome/css/font-awesome.css"
	rel="stylesheet">
<link href="../assets/css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="../assets/css/plugins/fullcalendar/fullcalendar.css"
	rel="stylesheet">
<link href="../assets/css/plugins/fullcalendar/fullcalendar.print.css"
	rel='stylesheet' media='print'>
<link href="../assets/css/animate.css" rel="stylesheet">
<!-- dashboard -->
<link rel="stylesheet" href="../assets/css/header.css">
<link href="../assets/css/style.css" rel="stylesheet">
<link href="../assets/css/plugins/jsTree/style.min.css" rel="stylesheet"/>

</head>
<body>
	<div id="wrapper">
		<%@ include file="../common/header.jsp"%>
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<div class="row">
				<%@ include file="../common/left_menutree.jsp"%>
				<div class="rightViewFrame">
			<div class="wrapper wrapper-content">
				<div class="row animated fadeInDown">
					<div class="col-lg-10">
						<div class="ibox float-e-margins" style="margin-bottom: 0px;">
							<div class="ibox-title ibox-title-curves2" style="padding-bottom: 0px;">
								<h2>Schedule</h2>
								<div class="ibox-content1">
									<div id="calendar"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-2">
						<div class="ibox">
							<div class="ibox-title ibox-title-curves" style="height: 896px;">
								<h2>Schedule Template</h2>
								<span class="fa fa-plus-circle fa-2x pull-right"></span>

								<div class="ibox float-e-margins">
									<div class="ibox-title ibox-title-curves1"
										style="color: #ffffff;">
										DRAG AND DROP SCHEDULER <span
											class="label label-warning pull-right"></span> 
									</div>
									<div class="ibox-content">
										<div id='external-events'>
											<div class='external-event navy-bgs' data-id='50'
												style="color: #292828;">0830 hrs,On 25°C cool,swing flap 
												</div>
											<div class='external-event navy-bgr' data-id='51'
												style="color: #292828;">1930 hrs,On 24°C cool,high fan speed 
												</div>
											<div class='external-event navy-bgs' data-id='52'
												style="color: #292828;">1330 hrs,On 25°C cool,flap position 1
												 </div>
											<div class='external-event navy-bgr' data-id='53'
												style="color: #292828;">1000 hrs,On 21°C cool,medium fan speed
												 </div>
											<div class='external-event navy-bgs' data-id='54'
												style="color: #292828;">0830 hrs,On 26°C cool,swing flap 
												</div>
											<div class='external-event navy-bgr' data-id='55'
												style="color: #292828;">1330 hrs,On 24°C cool,high fan speed 
												</div>
											<div class='external-event navy-bgs' data-id='56'
												style="color: #292828;">1630 hrs,On 25°C cool,flap position 1
												 </div>
											<div class='external-event navy-bgr' data-id='57'
												style="color: #292828;">1330 hrs,On 22°C cool,high fan speed 
												</div>
											<div class='external-event navy-bgs' data-id='58'
												style="color: #292828;">1300 hrs, off</div>
											<div class='external-event navy-bgr' data-id='59'
												style="color: #292828;">1730 hrs, off</div>
											<div class='external-event ' id='schedule-trash'>
												<a class="open-small-chat"> <i id="calendarTrash"
													class="fa fa-trash" title="Calendar Trash"></i>
												</a>
											</div>
										</div>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
		
		</div>
		</div>
		</div>
	</div>
	<!-- Mainly scripts -->


	<script src="../assets/js/plugins/fullcalendar/moment.min.js"></script>
	<script src="../assets/js/jquery-2.1.1.js"></script>
	<script src="../assets/js/bootstrap.min.js"></script>
	<script src="../assets/js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script src="../assets/js/plugins/slimscroll/jquery.slimscroll.js"></script>
	<!-- Custom and plugin javascript -->
	<script src="../assets/js/jqueryAjax.js"></script>
	<!-- jsTree -->
	<script src="../assets/js/plugins/jsTree/jstree.js"></script>
	<script src="../assets/js/inspinia.js"></script>
	<script src="../assets/js/plugins/pace/pace.min.js"></script>
	<!-- jQuery UI custom -->
	<script src="../assets/js/plugins/jquery-ui/jquery-ui.js"></script>
	<!-- jQuery UI touch -->
	<script
		src="../assets/js/plugins/jquery-ui-touch-punch/jquery.ui.touch-punch.min.js"></script>
	<!-- iCheck -->
	<script src="../assets/js/plugins/iCheck/icheck.min.js"></script>
	<!-- Full Calendar -->
	<script src="../assets/js/plugins/fullcalendar/fullcalendar.min.js"></script>
	<script src="../assets/js/util.js"></script>
	<script src="../assets/js/schedule.js"></script>



</body>
</html>