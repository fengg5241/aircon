/*****************************************************************
                  jQuery Ajax generic function       
*****************************************************************/
$(function(){
	
	//Added by seshu.
	function getConfirmation(){
        var retVal = confirm("Your Session  Is  already expired Please login again ?");
        if( retVal == true ){               
        }
        else{	
        	window.location.replace("../login/loginPage.htm");
        }
     }
    /**
     * url: A string containing the URL to which the request is sent.
     * data: Data to be sent to the server. For example{"date": new Date().getTime(), "state": 1}
     * async: By default, all requests are sent asynchronously (i.e. this is set to true by default).
     * type: The HTTP method to use for the request.default value is 'GET'.
     * dataType: The type of data that you're expecting back from the server. i.e.xml、html、json、text
     * successfn: A function to be called if the request succeeds.
     * errorfn: A function to be called if the request fails.
     */
    jQuery.ax=function(url, data, async, type, dataType, successfn, errorfn) {
        async = (async==null || async=="" || typeof(async)=="undefined")? "true" : async;
        type = (type==null || type=="" || typeof(type)=="undefined")? "post" : type;
        dataType = (dataType==null || dataType=="" || typeof(dataType)=="undefined")? "json" : dataType;
        data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
        data.avoidCacheDate = new Date().getTime();
        data.AJAXREQUEST = true;
        $.ajax({
            type: type,
            beforeSend: function (request){
                getToken(request);
            },
            async: async,
            data: data,
            url: url,
            dataType: dataType,
            success: function(d){
                successfn(d);
            },
            error: function(e){
                errorfn(e);
            }
        });
    };
    
    /**
     * url 
     * data 
     * successfn 
     */
    jQuery.axs=function(url, data, successfn) {
        data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
        data.avoidCacheDate = new Date().getTime();
        data.AJAXREQUEST = true;
        $.ajax({
            type: "get",
            beforeSend: function (request){
            	getToken(request);
            },
            data: data,
            url: url,
            dataType: "json",
            success: function(d){
                successfn(d);
            }
        });
    };
    
    /**
     * url 
     * data 
     * successfn 
     */
    jQuery.axs=function(url, data, successfn,type) {
        data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
        data.avoidCacheDate = new Date().getTime();
        data.AJAXREQUEST = true;
        $.ajax({
            type: type,
            beforeSend: function (request){
            	getToken(request);
            },
            data: data,
            url: url,
            dataType: "json",
            success: function(d){
                successfn(d);
            }
        });
    };
    
    
    /**
     * url 
     * data 
     * successfn 
     * errorfn 
     */
    jQuery.axse=function(url, data, successfn, errorfn) {
        data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
        data.avoidCacheDate = new Date().getTime();
        data.AJAXREQUEST = true;
        $.ajax({
            type: "get",
            beforeSend: function (request){
            	getToken(request);
            },
            data: data,
            url: url,
            dataType: "json",
            success: function(d){
                successfn(d);
            },
            error: function(e){
                errorfn(e);
            }
        });
    };
    
});
