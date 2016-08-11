<div class="modal fade col-lg-12" id="alarmDetailDialog" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog modal-lg">
    <div class="modal-content bizModal">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h3 class="modal-title text-center font-bold">Fault Diagnosis</h3>
      </div>
      <div class="modal-body" style="color:#646464">
      	<div class="row">
	        <div class="col-lg-12 nopadding">
	        	<div class="col-xs-4"><p class="font-bold">Location</p><p id="locationValue" class="text-center dialogGreyBackground">Xiamen University</p></div>
				<div class="col-xs-4"><p class="font-bold">Timezone</p><p id="timeZoneValue" class="text-center dialogGreyBackground">GMT +8:00</p></div>
				<div class="col-xs-4"><p class="font-bold">Device Name</p><p id="deviceNameValue" class="text-center dialogGreyBackground">ODU No.3</p></div>
			</div>
		</div>
		<div class="row">
	        <div class="col-lg-12 nopadding">
	        	<div class="col-xs-4"><p class="font-bold">CA Name</p><p id="caNameValue" class="text-center dialogGreyBackground">CA 01</p></div>
				<div class="col-xs-4"><p class="font-bold">Occurred Date/Time:</p><p id="occurredTimeValue" class="text-center dialogGreyBackground">11/11/2015 10:15</p></div>
	        	<div class="col-xs-4"><p class="font-bold">Model Name</p><p id="modelNameValue" class="text-center dialogGreyBackground">AB-20GE2E5</p></div>
			</div>
		</div>
		
		<div class="row">
	        <div class="col-lg-12 col-xs-12 nopadding ">
	        	<p clas="font-bold">Diagnosis Result:</p>
	        	<p id="criticleDiagnosisResult" class="detailValue dialogGreyBackground">
					<span class="font-bold">Alert Message:</span><span class="alarmCodeValue"></span> Serial Communication Errors</br></br>
					<span class="font-bold">Diagnosis Result Number:</span>14</br></br>
					
					<span class="font-bold">Symptom:</span></br>
					-Air conditioner does not run at all although power is turned on.</br></br>
					
					<span class="font-bold">Diagnosis:</span></br>
					-indoor unit is detecting error signal from main outdoor unit.</br></br>
					-Error in receiving serial communication signal.When turning on he power supply,the number of connected indoor
					 units does not correspond to the number set.</br></br>
					 <span class="font-bold">Possible Causes:</span></br>
					 1) Miswiring</br>
					 2) AC power failure</br>
					 3) Blown fuse</br>
	        	</p>
	        	<p id="nonCriticleDiagnosisResult" class="detailValue dialogGreyBackground" style="display:none">
					<span class="font-bold">Alert Message:</span><span class="alarmCodeValue"></span> Starter Lock</br></br>
					<span class="font-bold">Prediction Result Number:</span>12</br></br>
					
					<span class="font-bold">Prediction Results:</span></br></br>
					1) Error detection</br>
						&#149 During cranking, a Starter Locked error is assumed when any of the following conditions occur 5times in hour:the start power primacy current meets the following
						condition, no revolution pulse is detected , no input from the crank angle sensor,no input from the cam angle sensor.</br></br>
						&#149 When 32A or more is detected for 1.0 second or more</br></br>
					2) Troubleshooting</br>
						&#149 Temporarily remove ignition plugs from all cylinders.</br></br>
						&#149 Can the engine crank be rotated?(To rotate the crankshaft,follow the procedure in the)</br></br>
						&#149 Periodic inspection and Parts Replacement Manual.)</br></br>
						&#149 Replace engine if he engine crankshaft does not rotate.
	        	</p>
			</div>
		</div>
		<div class="row text-center">
        	<button type="button" style="width:150px" class="btn bizButton greyGradient">Download &nbsp;<span class="fa fa-caret-right"></button>
      	</div>
		
      </div>
      	
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
