<%@ page import="java.io.*,java.util.*"%>
<%@ page import="javax.servlet.*,java.text.*"%>
<%
	Date date = new Date();
	SimpleDateFormat ft = new SimpleDateFormat("E dd.MM.yy - hh:mm a");
	String fd = ft.format(date);
	String[] temp = fd.split("-");
	fd = "<div style='padding-top:15px;padding-left:11px;font-size:21px;color:#626465;'>"
			+ temp[0]
			+ "</div><div style='padding-bottom:31px;padding-left:9px;font-size:32px;color:#626465'><b>"
			+ temp[1] + "</b></div>";
%>
<div class="col-xs-12">
	<div class="animated fadeInDown padding0">
		<div class="row">
			<div class="col-xs-4">
				<div class="row">
					<div class="col-xs-12">
						<%@ include file="homeEnergyGraph.jsp"%>
					</div>
					<div class="col-xs-12">
						<%@ include file="homeEfficiencyGraph.jsp"%>
					</div>
				</div>
			</div>
			<div class="col-xs-8" style="padding-left:0">
				<div class="row">
					<div class="col-xs-9">
						<!-- weather -->
						<%@ include file="homeWeather.jsp"%>
					</div>
					<div class="col-xs-3" style="padding-left:0">
						<%@ include file="homeNotificationCount.jsp"%>
					</div>
					<div class="col-xs-5">
						<%@ include file="homeEcoBuildingList.jsp"%>
					</div>
					<div class="col-xs-7" style="padding-left:0">
						<!-- map -->
						<%@ include file="homeMap.jsp"%>
					</div>
				</div>
			</div>
		</div>
		<!-- <div class="col-xs-12 text-center">
				<p class="m-t" >
				<small>Copyright &copy; 2016  Panasonic Corporation</small>
			</p>
		</div> -->
		
	</div>
</div>