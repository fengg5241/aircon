<!-- <div id="acRemoteControlModal" class="modal fade modeless"> -->
<!--     <div class="modal-dialog modal-lg"> -->
<!--         <div class="modal-content"> -->
<!--             <div class="modal-header"> -->
<!--                 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button> -->
<!--                  <h2 class="modal-title font-bold">AC Controller</h2> -->

<!--             </div> -->
            <div class="col-lg-12 dialogBackground">
                <div id="control"> <!-- style="height: 261px;" -->
					<div class="ibox">
						<div class="ibox-content ibox-curve"
							style="background-color: #F4F5F7; height: 100%;">
							
							<div class="row">
								<div class="col-xs-12 animated fadeInRight">
									<div class="row">
									
										<div id="tempSettingFrame" class="col-xs-2 nopadding-left animated fadeInRight ">
											<div class="text-center dialogGreyBackground" style="padding-bottom:10px;;">
												<div class="row">
													<div class="col-xs-12" style="padding-top: 25px;">
														<div id = "powerControl" style="margin-right:3px;" class="text-center selected controlButton">
																<spring:message code="label.operation"/>
														</div>
													</div>
												</div>
												
												<div class="row">
													<div class="col-xs-12">
												<div id="powerControlPanel" class="row greyGradient speech-bubble speech-bubble-power controlButton" linked-id="powerControl">
													<div class="col-xs-12 nopadding ">
														<div class="col-xs-6 powerStatusButton" control-type="off"><spring:message code="label.OFF"/></div>
														<div class="col-xs-6 powerStatusButton" control-type="on"><spring:message code="label.ON"/></div>
													</div>
												</div>
												</div></div>
												
<!-- 												<div class="row"> -->
<!-- 													<div class="col-xs-12"> -->
<!-- 													<button type="button" class="btn bizButton greyGradient resetButton" data-resetType="operation"><span class="glyphicon glyphicon-repeat"></span> &nbsp;Operation</button> -->
<!-- 												</div></div> -->
											</div>
										</div>
										
										<div id = remoteControlRightFrame class="col-xs-10 ">
										<div class="row dialogGreyBackground"><div class="col-xs-3" style="padding:0 10px 10px 10px;">
											<div class="text-center">
												<h5 class="font-bold text-center"
													style="margin:3px 0 5px 0; color: #676A63;"><spring:message code="label.set"/></h5>
												<h5 class="font-bold text-center"
													style="margin:3px 0 5px 0; color: #676A63;"><spring:message code="label.temperature"/></h5>
												<p>
													<button id = "increaseTempButton" type="button" class="btn bizButton greyGradient tempButton">
														<span class="glyphicon glyphicon-chevron-up"></span></button>
												</p>
												<h2>
													<span id="tempLabel">_ _</span><span>°C</span>
												</h2>
												<p>
													<button id = "decreaseTempButton" type="button" class="btn bizButton greyGradient tempButton">
														<span class="glyphicon glyphicon-chevron-down"></span></button>
												</p>
<!-- 												<button type="button" class="btn bizButton greyGradient resetButton" data-resetType="temp"><span class="glyphicon glyphicon-repeat"></span> &nbsp;Set Temp.</button> -->
											</div>
										</div>
										<div id = "modeSelectionFrame" class="col-xs-9" style="padding:0 10px 10px 10px;">
										<h5 class="font-bold"
													style="margin:3px 0 0 0; color: #676A63;"><spring:message code="label.pleaseselect"/>:</h5>
												<div class="row">
													<div class="animated fadeInRight col-xs-12"
														style="padding-top: 10px;">
														<div class="col-xs-3 no-padding">
															<div id = "modeControl" style="margin-right:3px;" class="text-center greyGradient  controlButton">
																<div class="selected"></div>
															</div>
														</div>
														<div class="col-xs-3 no-padding">
															<div id="fanSpeedControl" style="margin-right:3px;" class="text-center greyGradient  controlButton">
																<div></div>
															</div>
														</div>
														<div class="col-xs-3 no-padding">
															<div id="flapControl" style="margin-right:3px;" class="text-center greyGradient  controlButton">
																<div class=""></div>
															</div>
														</div>
														<div class="col-xs-3 no-padding">
															<div id="energySavingControl" style="" class="text-center greyGradient controlButton">
																<div class=""></div>
															</div>
														</div>
													</div>
												</div>
												
											
												<div id="modeControlPanel" class="row greyGradient speech-bubble speech-bubble-mode" linked-id="modeControl">
													<div class="col-xs-12 ">
														<div class="col-xs-5ths autoMode" control-type="auto"><div class="controlIcon"></div></div>
														<div class="col-xs-5ths heatMode" control-type="heat"><div class="controlIcon"></div></div>
														<div class="col-xs-5ths dryMode" control-type="dry"><div class="controlIcon"></div></div>
														<div class="col-xs-5ths coolMode" control-type="cool"><div class="controlIcon"></div></div>
														<div class="col-xs-5ths fanMode" control-type="fan"><div class="controlIcon"></div></div>
													</div>
												</div>
												
												<div id="fanSpeedControlPanel" class="row greyGradient speech-bubble speech-bubble-fanSpeed" style="display:none" linked-id="fanSpeedControl">
													<div class="col-xs-12 ">
				<!-- 										<div class="col-xs-5ths mixFanSpeed" control-type="mix"><div></div></div> -->
														<div class="col-xs-3 autoFanSpeed" control-type="auto"><div class="controlIcon"></div></div>
														<div class="col-xs-3 highFanSpeed" control-type="high"><div class="controlIcon"></div></div>
														<div class="col-xs-3 mediumFanSpeed" control-type="medium"><div class="controlIcon"></div></div>
														<div class="col-xs-3 lowFanSpeed" control-type="low"><div class="controlIcon"></div></div>
													</div>
												</div>
												
												<div id="flapControlPanel" class="row greyGradient speech-bubble speech-bubble-flap" style="display:none" linked-id="flapControl">
													<div class="col-xs-12 ">
<!-- 														<div class="col-xs-7ths offFlap" control-type="off"><div class="controlIcon"></div></div> -->
														<div class="col-xs-2 f1Flap" control-type="f1"><div class="controlIcon"></div></div>
														<div class="col-xs-2 f2Flap" control-type="f2"><div class="controlIcon"></div></div>
														<div class="col-xs-2 f3Flap" control-type="f3"><div class="controlIcon"></div></div>
														<div class="col-xs-2 f4Flap" control-type="f4"><div class="controlIcon"></div></div>
														<div class="col-xs-2 f5Flap" control-type="f5"><div class="controlIcon"></div></div>
														<div class="col-xs-2 swingFlap" control-type="swing"><div class="controlIcon"></div></div>
													</div>
												</div>
												
												<div id="energySavingControlPanel" class="row greyGradient speech-bubble speech-bubble-energySaving" style="display:none" linked-id="energySavingControl">
													<div class="col-xs-12 ">
														<div class="col-xs-6 energySavingButton" control-type="0"><div class="controlIcon" style="float:right"><spring:message code="label.OFF"/></div></div>
														<div class="col-xs-6 energySavingButton" control-type="1"><div class="controlIcon"><spring:message code="label.ON"/></div></div>
													</div>
												</div>
												
<!-- 												<div class="row"> -->
<!-- 													<div class="animated fadeInRight col-xs-12"> -->
<!-- 														<div class="col-xs-3 no-padding"> -->
<!-- 															<button type="button" class="btn bizButton greyGradient resetButton" data-resetType="mode"><span class="glyphicon glyphicon-repeat"></span> &nbsp;Mode</button> -->
<!-- 														</div> -->
<!-- 														<div class="col-xs-3 no-padding"> -->
<!-- 															<button type="button" class="btn bizButton greyGradient resetButton" data-resetType="fanSpeed"><span class="glyphicon glyphicon-repeat"></span> &nbsp;Fan Speed</button> -->
<!-- 														</div> -->
<!-- 														<div class="col-xs-3 no-padding"> -->
<!-- 															<button type="button" class="btn bizButton greyGradient resetButton" data-resetType="flap"><span class="glyphicon glyphicon-repeat"></span> &nbsp;Flap</button> -->
<!-- 														</div> -->
<!-- 														<div class="col-xs-3 no-padding"> -->
<!-- 															<button id = "energySavingSetButton" type="button" class="btn bizButton greyGradient resetButton" data-resetType="energySaving"><span class="glyphicon glyphicon-repeat"></span>Energy Saving</button> -->
<!-- 														</div> -->
<!-- 													</div> -->
<!-- 												</div> -->
										</div>
										</div>
									</div></div>
								</div>
							</div>
							<!-- ===============			prohibition setting             =====================-->
							
							<div id= "prohibitionPanel" class="row dialogGreyBackground" style="color: #676A63;">
								<div class="row">
									<h3 class="font-bold text-center col-xs-12"><spring:message code="label.prohibitions"/></h3>
								</div>
								<div class="row">
								<div class="col-xs-12 text-center">
									<div class="col-xs-2">
										<h5><spring:message code="label.oper"/>.</h5>
										<div id="operSetting" class="whiteBackground">
											<div class="radio">
											  <label><input type="radio" name="operSettingRadio" id="operSettingAccept" value="accept" control-type="PROHIBITION_POWERSTATUS-0">
											    <spring:message code="label.accept"/></label>
											</div>
											<div class="radio">
											  <label><input type="radio" name="operSettingRadio" id="operSettingProhibit" value="prohibit" control-type="PROHIBITION_POWERSTATUS-1">
											    <spring:message code="label.prohibit"/></label>
											</div>
										</div>
									</div>
									
									
									<div class="col-xs-2">
										<h5><spring:message code="label.settemp"/>.</h5>
										<div id="tempSetting" class="whiteBackground">
											<div class="radio">
											  <label><input type="radio" name="tempSettingRadio" id="tempSettingAccept" value="accept" control-type="PROHIBITION_SET_TEMP-0">
											    <spring:message code="label.accept"/></label>
											</div>
											<div class="radio">
											  <label><input type="radio" name="tempSettingRadio" id="tempSettingProhibit" value="prohibit" control-type="PROHIBITION_SET_TEMP-1">
											    <spring:message code="label.prohibit"/></label>
											</div>
										</div>
									</div>
									
									
									<div class="col-xs-2">
										<h5><spring:message code="label.mode"/></h5>
										<div id="modeSetting" class="whiteBackground">
											<div class="radio">
											  <label><input type="radio" name="modeSettingRadio" id="modeSettingAccept" value="accept" control-type="PROHIBITON_MODE-0">
											    <spring:message code="label.accept"/></label>
											</div>
											<div class="radio">
											  <label><input type="radio" name="modeSettingRadio" id="modeSettingProhibit" value="prohibit" control-type="PROHIBITON_MODE-1">
											    <spring:message code="label.prohibit"/></label>
											</div>
										</div>
									</div>
									
									
									<div class="col-xs-2">
										<h5><spring:message code="label.fanspeed"/></h5>
										<div id="fanSpeedSetting" class="whiteBackground">
											<div class="radio">
											  <label><input type="radio" name="fanSpeedSettingRadio" id="fanSpeedSettingAccept" value="accept" control-type="PROHIBITION_FANSPEED-0">
											    <spring:message code="label.accept"/></label>
											</div>
											<div class="radio">
											  <label><input type="radio" name="fanSpeedSettingRadio" id="fanSpeedSettingProhibit" value="prohibit" control-type="PROHIBITION_FANSPEED-1">
											    <spring:message code="label.prohibit"/></label>
											</div>
										</div>
									</div>
									
									
									<div class="col-xs-2">
										<h5><spring:message code="label.flap"/></h5>
										<div id="flapSetting" class="whiteBackground">
											<div class="radio">
											  <label><input type="radio" name="flapSettingRadio" id="flapSettingAccept" value="accept" control-type="PROHIBITION_WINDRIECTION-0">
											    <spring:message code="label.accept"/></label>
											</div>
											<div class="radio">
											  <label><input type="radio" name="flapSettingRadio" id="flapSettingProhibit" value="prohibit" control-type="PROHIBITION_WINDRIECTION-1">
											    <spring:message code="label.prohibit"/></label>
											</div>
										</div>
									</div>
									
									<div class="col-xs-2">
										<h5 style="white-space:nowrap"><spring:message code="label.energysaving"/></h5>
										<div id="energSavingSetting" class="whiteBackground">
											<div class="radio">
											  <label><input type="radio" name="energSavingSettingRadio" id="energSavingSettingAccept" value="accept" control-type="PROHIBITION_ENERGY_SAVING-0">
											    <spring:message code="label.accept"/></label>
											</div>
											<div class="radio">
											  <label><input type="radio" name="energSavingSettingRadio" id="energSavingSettingProhibit" value="prohibit" control-type="PROHIBITION_ENERGY_SAVING-1">
											    <spring:message code="label.prohibit"/></label>
											</div>
										</div>
									</div>
								</div>
								</div>
								
								<div class="row">
									<div class="col-xs-12 text-center" style="margin-top: -10px; margin-bottom: 20px;">
										<div class="col-xs-2"><button type="button" class="btn bizButton greyGradient resetButton" data-resetType="prohibition" data-resetId="operSetting">
<!-- 										<span class="glyphicon glyphicon-repeat"></span> &nbsp;Oper. -->
										</button></div>
										<div class="col-xs-2"><button type="button" class="btn bizButton greyGradient resetButton" data-resetType="prohibition" data-resetId="tempSetting">
<!-- 										<span class="glyphicon glyphicon-repeat"></span> &nbsp;Set Temp -->
										</button></div>
										<div class="col-xs-2"><button type="button" class="btn bizButton greyGradient resetButton" data-resetType="prohibition" data-resetId="modeSetting">
<!-- 										<span class="glyphicon glyphicon-repeat"></span> &nbsp;Mode -->
										</button></div>
										<div class="col-xs-2"><button type="button" class="btn bizButton greyGradient resetButton" data-resetType="prohibition" data-resetId="fanSpeedSetting">
<!-- 										<span class="glyphicon glyphicon-repeat"></span> &nbsp;Fan Speed -->
										</button></div>
										<div class="col-xs-2"><button type="button" class="btn bizButton greyGradient resetButton" data-resetType="prohibition" data-resetId="flapSetting">
<!-- 										<span class="glyphicon glyphicon-repeat"></span> &nbsp;Flap -->
										</button></div>
										<div class="col-xs-2"><button id = "energySavingSetButton" type="button" class="btn bizButton greyGradient resetButton" data-resetType="prohibition" data-resetId="energSavingSetting">
<!-- 										<span class="glyphicon glyphicon-repeat"></span>Energy Saving -->
										</button></div>
									</div>
									
								</div>
							</div>
							
							<!-- ============			Apply Setting button        =================  -->
							<div class="row text-right">
								<button id = "remoteControlButton" type="button" class="btn bizButton greyGradient" style="margin:10px 0 10px 0"><spring:message code="label.applysetting"/> &nbsp;<span class="fa fa-caret-right"></span></button>
							</div>
							
						</div>
					</div>
				</div>
            </div>
<!--             <div class="modal-footer"> -->
<!-- 				<div class="row"> -->
<!-- 					<button id = "remoteControlButton" type="button" class="btn btn-default">Apply Setting</button> -->
<!-- 				</div> -->
<!--             </div> -->
<!--         </div> -->
<!--         /.modal-content -->
<!--     </div> -->
<!--     /.modal-dialog -->
<!-- </div> -->


