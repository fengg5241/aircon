/*
 *
 *   INSPINIA - Responsive Admin Theme
 *   version 2.0
 *
 */

$(document).ready(
		function() {
			// Add body-small class if window less than 768px
			if ($(this).width() < 769) {
				$('body').addClass('body-small');
			} else {
				$('body').removeClass('body-small');
			}
			
			if (document.location.href.search("/home/homeScreen.htm")==-1){
				initMenuTree();
			}

			$.ajaxSettings.async = false;

			// Collapse ibox function
			$('.collapse-link').click(
					function() {
						var ibox = $(this).closest('div.ibox');
						var button = $(this).find('i');
						var content = ibox.find('div.ibox-content');
						content.slideToggle(200);
						button.toggleClass('fa-chevron-up').toggleClass(
								'fa-chevron-down');
						ibox.toggleClass('').toggleClass('border-bottom');
						setTimeout(function() {
							ibox.resize();
							ibox.find('[id^=map-]').resize();
						}, 50);
					});

			// Close ibox function
			$('.close-link').click(function() {
				var content = $(this).closest('div.ibox');
				content.remove();
			});

			// Close menu in canvas mode
			$('.close-canvas-menu').click(function() {
				$("body").toggleClass("mini-navbar");
				SmoothlyMenu();
			});

			// Open close right sidebar
			$('.right-sidebar-toggle').click(function() {
				$('#right-sidebar').toggleClass('sidebar-open');
			});

			// Small todo handler
			$('.check-link').click(
					function() {
						var button = $(this).find('i');
						var label = $(this).next('span');
						button.toggleClass('fa-check-square').toggleClass(
								'fa-square-o');
						label.toggleClass('todo-completed');
						return false;
					});
			
			
			// Minimalize menu
			$('.navbar-minimalize').click(function() {
				$("body").toggleClass("mini-navbar");
				SmoothlyMenu();

				/*setTimeout(
				    function () {
				    	//if(window.location.href.search("http://localhost:8080/b2bdemo/dashboard/") >= 0){
				        	//resize();
				        //}
				}, 600);*/

			});

			// Tooltips demo
			$('.tooltip-demo').tooltip({
				selector : "[data-toggle=tooltip]",
				container : "body"
			});

			// Move modal to body
			// Fix Bootstrap backdrop issue with animation.css
			$('.modal').appendTo("body");

			function initMenuTree() {
				$.axs("../group/getGroupHierarchyTree.htm", {
					"userRole" : "1"
				}, function(data) {
					$('#checkboxMenuTree').jstree({
						'core' : {
							'data' : data,
							"themes" : {
								icons : false,
								dots : false
							}
						},
						'state':{
							'events':""
						},
						'plugins' : [ "themes", "json_data", "checkbox","wholerow","state", "ui" ],
						'checkbox': {
							'tie_selection' : false
						}
					}).bind('ready.jstree', function(e, data) {
				        $('#checkboxMenuTree').jstree().restore_state();
				        if (hasSelectedGroupInSession()) {
				        	$("#displayButton").click();
				        	$("#displayButton").data('clicked', true);
						}
				    }).bind('check_node.jstree', function(e, s, data) {
				    	$("#displayButton").data('clicked', false);
				    }).bind('uncheck_node.jstree', function(e, s, data) {
				    	$("#displayButton").data('clicked', false);
				    });
//					.bind('after_open.jstree', function(e, data) {
//				    	makeTreeHeightAuto();
//				    }).bind('after_close.jstree', function(e, data) {
//				    	makeTreeHeightAuto();
//				    });
				});
			}
			
			function makeTreeHeightAuto(){
				var treeHeight = $("#checkboxMenuTree>ul").height();
				var scrollbar = "";
		    	if (treeHeight > 648) {treeHeight = 648;scrollbar="auto";}
		    	else {scrollbar="hidden";}
		    	console.log(treeHeight);
		    	$("#checkboxMenuTree").height(treeHeight);
			}

			// Full height of sidebar
			function fix_height() {
				var heightWithoutNavbar = $("body > #wrapper").height() - 105;

				var navbarHeigh = $('nav.navbar-default').height();
				var wrapperHeigh = $('#page-wrapper').height();

				if (navbarHeigh > wrapperHeigh) {
					$('#page-wrapper').css("min-height", navbarHeigh + "px");
				}

				if (navbarHeigh < wrapperHeigh) {
					$('#page-wrapper').css("min-height",
							$(window).height() + "px");
				}

				$('#map').css("height", ($(window).height() - 430) + "px");
				$('#seer_table').css("height",
						($(window).height() - 601) + "px");
				//$('#calendar').fullCalendar('option', 'contentHeight', $(window).height() - 140);
			}

			//fix_height();

			$(document).bind("load resize scroll", function() {
				if (!$("body").hasClass('body-small')) {
					//fix_height();
				}
			});

			$("[data-toggle=popover]").popover();

			var height = $(window).height() - 316 + "px";
			// Add slimscroll to element
			$('.full-height-scroll').slimscroll({
				height : height
			});

		});

// Minimalize menu when screen is less than 768px
$(window).bind("resize", function() {
	if ($(this).width() < 769) {
		$('body').addClass('body-small');
		$("#company_logo").hide();
	} else {
		$('body').removeClass('body-small');
		$("#company_logo").removeAttr('style');
		$('body').removeClass('mini-navbar');
	}
});

function getCheckedGroupIds() {

	var checkedIdList = new Array();

	
		var tree = $.jstree.reference('#checkboxMenuTree');
		if (tree != null) {
			var checkedList = tree.get_checked(true);
	
			for (var i = 0; i < checkedList.length; i++) {
				//Only need to store group node, no need to store company node.
				if (typeof (checkedList[i].original.groupId) != "undefined") { 
					checkedIdList.push(checkedList[i].li_attr.dataId);
				}
			}
		}

	
	
	return checkedIdList;
}

function getCheckedControlGroupIds() {
	var checkedIdList = new Array();
	/*
	var firstClick = false;
	var activeBtn = $('#displayButton').attr('class').split(" ");
	for(var i = 0; i < activeBtn.length ;i++){
		if(activeBtn[i] == "active"){
			console.log(activeBtn[i]);
			firstclick = true;        
		}
	}
	if(firstClick){
*/

		var tree = $.jstree.reference('#checkboxMenuTree');
		if (tree != null) {
			var checkedList = tree.get_bottom_checked(true);
			var tempList = tree.get_top_checked(true);
			console.log(tempList);
			for (var i = 0; i < checkedList.length; i++) {
				if (typeof (checkedList[i].original.groupId) != "undefined") { 
					checkedIdList.push(checkedList[i].li_attr.dataId);
				}
			}
		}
	/*
	}else{
		alert("Please Select Site Group and Apply Selection.")
	}
	*/
	return checkedIdList;
}

function getCheckedGroupIdsByLevelName(levelName) {
	var checkedIdList = new Array();
	var tree = $.jstree.reference('#checkboxMenuTree');
	if (tree != null) {
		var checkedList = tree.get_checked(true);
		for (var i = 0; i < checkedList.length; i++) {
			if (typeof (checkedList[i].original.groupId) != "undefined") { 
				var groupLevelName = checkedList[i].original.groupTypeLevelName;
				if (groupLevelName == levelName) {
					checkedIdList.push(checkedList[i].li_attr.dataId);
				}
			}
		}
	}
	return checkedIdList;
}

function saveSelectedGroupIdsIntoSession(idList){
	var storage = window.localStorage;
	storage.setItem("selectedGroupIds",idList);
}

function getSelectedGroupIdsFromSession(){
	var storage = window.localStorage;
	var groupIds = storage.getItem("selectedGroupIds");
	if (groupIds == null) {groupIds = [];}
	return groupIds;
}

function hasSelectedGroupInSession(){
	return window.localStorage.getItem("jstree") != null;
}

// Local Storage functions
// Set proper body class and plugins based on user configuration
$(document).ready(
		function() {
			if (localStorageSupport) {

				var collapse = localStorage.getItem("collapse_menu");
				var fixedsidebar = localStorage.getItem("fixedsidebar");
				var fixednavbar = localStorage.getItem("fixednavbar");
				var boxedlayout = localStorage.getItem("boxedlayout");
				var fixedfooter = localStorage.getItem("fixedfooter");

				var body = $('body');

				if (fixedsidebar == 'on') {
					body.addClass('fixed-sidebar');
					$('.sidebar-collapse').slimScroll({
						height : '100%',
						railOpacity : 0.9
					});
				}

				if (collapse == 'on') {
					if (body.hasClass('fixed-sidebar')) {
						if (!body.hasClass('body-small')) {
							body.addClass('mini-navbar');
						}
					} else {
						if (!body.hasClass('body-small')) {
							body.addClass('mini-navbar');
						}

					}
				}

				if (fixednavbar == 'on') {
					$(".navbar-static-top").removeClass('navbar-static-top')
							.addClass('navbar-fixed-top');
					body.addClass('fixed-nav');
				}

				if (boxedlayout == 'on') {
					body.addClass('boxed-layout');
				}

				if (fixedfooter == 'on') {
					$(".footer").addClass('fixed');
				}
			}

			$(document).on(
					"click",
					"li.groupMenuItem",
					function(e) {
						if (typeof $(this).attr("group-id") !== "undefined") {
							$.axs('../login/saveLastGroupSelection.htm', {
								id : $(this).attr("group-id")
							}, function(data) {
								//console.log($(this).attr("group-id"));
							}, 'post');
						}
						if ($(this).attr("group-criteria") == "1") {
							$("#side-menu li.groupMenuItem>a").removeClass(
									"menuHighLight");
							$("#" + $(this).attr("id") + "> a").addClass(
									"menuHighLight");
						} else {
							if ($(this).children("ul").length == 0
									|| $(this).hasClass("active")) {
								$("#side-menu li.groupMenuItem>a").removeClass(
										"menuHighLight");
								$("#" + $(this).attr("id") + "> a").addClass(
										"menuHighLight");
							}
						}
						e.stopPropagation();
					});

			$(document).on(
					"click",
					".menuCheckbox",
					function(e) {
						var newChecked = $(this).prop("checked");
						var $ulDom = $(this).parent().siblings();
						if (typeof ($ulDom) != "undefined") {
							$ulDom.contents().find("input.menuCheckbox").prop(
									"checked", newChecked);
						}

						var $liDom = $(this).parent().parent();
						_setParentCheckStatus($liDom, newChecked);
					});

			//    $(document).on("click", "#saveSetting", function(e) {
			//    	var checkedIdList = getCheckedGroupIds();
			//    	
			//		$.axs('../login/saveCheckedGroups.htm', {idList : checkedIdList} ,function (data) {
			//			//console.log($(this).attr("group-id"));
			//		},'post');
			//    	
			//    });

			function _setParentCheckStatus($liDom, newChecked) {
				if (newChecked) {
					var allChecked = true;
					$liDom.siblings().each(
							function(index) {
								if (!$(this).find("input.menuCheckbox").prop(
										"checked")) {
									allChecked = false;
									return false;
								}
							});
					if (allChecked) {
						var $liParnet = $liDom.parent().parent();
						if (typeof ($liParnet) != "undefined"
								&& $liParnet.hasClass("groupMenuItem")) {
							$liParnet.children('a').children(
									"input.menuCheckbox").prop("checked",
									newChecked);
							_setParentCheckStatus($liParnet, newChecked);
						}
					}
				} else {
					var $liParnet = $liDom.parent().parent();
					if (typeof ($liParnet) != "undefined"
							&& $liParnet.hasClass("groupMenuItem")) {
						$liParnet.children('a').children("input.menuCheckbox")
								.prop("checked", newChecked);
						_setParentCheckStatus($liParnet, newChecked);
					}
				}
			}
		});

// check if browser support HTML5 local storage
function localStorageSupport() {
	return (('localStorage' in window) && window['localStorage'] !== null)
}

// For demo purpose - animation css script
function animationHover(element, animation) {
	element = $(element);
	element.hover(function() {
		element.addClass('animated ' + animation);
	}, function() {
		//wait for animation to finish before removing classes
		window.setTimeout(function() {
			element.removeClass('animated ' + animation);
		}, 2000);
	});
}

function SmoothlyMenu() {
	if (!$('body').hasClass('mini-navbar') || $('body').hasClass('body-small')) {
		// Hide menu in order to smoothly turn on when maximize menu
		$('#side-menu').hide();
		if ($('body').hasClass('body-small')) {
			$("#company_logo").hide();
		} else {
			$("#company_logo").removeAttr('style');
		}
		// For smoothly turn on menu
		setTimeout(function() {
			$('#side-menu').fadeIn(500);
		}, 100);
	} else if ($('body').hasClass('fixed-sidebar')) {
		$('#side-menu').hide();
		$("#company_logo").removeAttr('style');
		setTimeout(function() {
			$('#side-menu').fadeIn(500);
		}, 300);
	} else {
		// Remove all inline style from jquery fadeIn function to reset menu state
		$('#side-menu').removeAttr('style');
		$("#company_logo").hide();
	}
}

// Dragable panels
function WinMove() {
	var element = "[class*=col]";
	var handle = ".ibox-title";
	var connect = "[class*=col]";
	$(element).sortable({
		handle : handle,
		connectWith : connect,
		tolerance : 'pointer',
		forcePlaceholderSize : true,
		opacity : 0.8
	}).disableSelection();
}

//menu highlight
var menuid = ['home', 'acsettings', 'visualization', 'notification', 'schedule', 'user', 'settings', 'settings_op', 'ca_installtion'];

function getKey(v){
	var k;
	$.each( menuid, function( key, value ) {
	  if(v == value){
		  k = key;
	  }
	});
	return k;
}

function menu(e) {
	var id = getKey(e);
	$('#' + menuid[id]).addClass("active");
	if(id == 1){
		acSettingSelectedIcon();
	}
	for(var key in menuid){
		if(id != key){
			$('#' + menuid[key]).removeClass("active");
			if(key == 1){
				acSettingIcon();
			}
		}
	}
}

function menuDashBoard(data) {
	console.log(data);
	var id = getKey(data);
	if (id != null) {
		if (!$('#' + menuid[id]).hasClass('active')) {
			$('#' + menuid[id]).addClass('tabshover');
			if(id == 1){
				acSettingSelectedIcon();
			}
		}
		for(var key in menuid){
			if(key != id){
				$('#' + menuid[key]).removeClass('tabshover');
			}
		}
	}
}

function menuLeave(e) {
	for(var key in menuid){
		$('#' + menuid[key]).removeClass('tabshover');
		if(key == 1 && !$('#' + menuid[1]).hasClass('active')){
			acSettingIcon();
		}
	}
}

function acSettingSelectedIcon(){
	$('#' + menuid[1] +" > a > span").attr("class", "menuAcSettings_selected");
}

function acSettingIcon(){
	$('#' + menuid[1] +" > a > span").attr("class", "menuAcSettings");
}