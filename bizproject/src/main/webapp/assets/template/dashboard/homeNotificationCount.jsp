<div class="notifyhomepanel panel-default" style="height:430px;">
	<div class="notifyhomepanel-heading">
		<div class="row">
			<div class="col-xs-12">
				<div class="col-xs-1 text-center"></div>
				<div class="col-xs-9  text-center">
					<b><spring:message code="label.notifications"/></b>
				</div>
				<div class="col-xs-1 text-center"></div>
			</div>
		</div>
	</div>
	<div class="notifyhomepanel-body" style="padding-bottom:0;height:370px;">
		<div class="row">
			<div class="col-xs-12">
				<div class="col-xs-1 text-center"></div>
				<div class="col-xs-9  text-center" style="margin-top: 26px;">
					<b><spring:message code="label.alarmsummary"/>.</b>
				</div>
				<div class="col-xs-1  text-center"></div>
			</div>
		</div>
		<br><br>
		<div class="row" style="margin-left: -20px;">
			<div class="col-xs-12">

				<button type="button" class="btn btn-default btn-lg col-xs-12"
					style="margin-top: 5px; background-color: #E6E6E6;" disabled>
					<div class="row">
						<div class="col-xs-12">

							<div class="col-xs-4 ">
								<img alt="image" id="critical"
									src="../assets/img/HomeScreen_Alarm_icon_critical.png">
							</div>
							<div class="col-xs-2 ">
								<text style="font-size:15px"><spring:message code="label.critical"/></text>
								<br> <label id="criticalcount"></label> &nbsp;&nbsp;&nbsp; <b><spring:message code="label.units"/></b>
							</div>
							<div class="col-xs-4 "></div>
						</div>

					</div>
				</button>
			</div>
		</div>

		<div class="row" style="margin-left: -20px;">
			<div class="col-xs-12">


				<button type="button" class="btn btn-default btn-lg col-xs-12"
					style="margin-top: 10px; background-color: #E6E6E6;" disabled>
					<div class="row">
						<div class="col-xs-12">

							<div class="col-xs-4 ">
								<img alt="image" id="noncritical"
									src="../assets/img/HomeScreen_Alarm_icon_noncritical.png">
							</div>
							<div class="col-xs-2 ">
								<text style="font-size:15px"><spring:message code="label.noncritical"/></text>
								<br> <label id="noncriticalcount"></label> &nbsp;&nbsp;&nbsp; <b><spring:message code="label.units"/></b>
							</div>
							<div class="col-xs-4 "></div>
						</div>

					</div>
				</button>
			</div>
		</div>
		 <br><br>
		 <p class="text-center" style="margin-bottom: 15px; margin-top: 12px;padding-bottom:20px;"> <b><spring:message code="label.totalalarms"/> :</b><label id="totalcount"></label></p>
<!-- 		<div class="row"> -->
<!-- 			<div class="col-xs-12"> -->
<!-- 				<div class="col-xs-1 text-center"></div> -->
<!-- 				<div class="col-xs-9  text-center"> -->
<!-- 					<b><spring:message code="label.totalalarms"/> :</b><label id="totalcount"></label> -->
<!-- 				</div> -->
<!-- 				<div class="col-xs-1  text-center"></div> -->

<!-- 			</div> -->
<!-- 		</div> -->
	</div>

</div>