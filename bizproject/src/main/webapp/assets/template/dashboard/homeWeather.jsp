<div class="notifyhomepanel panel-default" style="height:430px;">
	<div class="notifyhomepanel-heading">
		<div class="row">
			<div class="col-xs-12 text-center">
				<b><spring:message code="label.weather"/></b>
			</div>
		</div>
	</div>
	<div class="notifyhomepanel-body" style="padding:5px 50px 0 15px;height:370px;">
		<div id="homeWeather">
			<div class="row">
				<div class="col-xs-4 text-center" style="padding-right:0">
					<select id="weatherlocation">

					</select>
					<p id="locationTime"></p>
					<canvas id="weatherIcon" width="128" height="128"></canvas>
					<p id="locationTemp"><span id="tempNumber"></span>&#176C</p>
					<p id="locationWeather"></p>
				</div>	
				<div class="col-xs-8" style="padding-left:0">
					<div id="tempChart" style="min-width: 280px; height: 260px; margin: 30px  auto 0 auto"></div>
				</div>	
			</div>
		</div>
	</div>
</div>