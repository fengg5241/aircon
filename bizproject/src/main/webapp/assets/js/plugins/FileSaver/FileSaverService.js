function downloadAs(data,fileName){

try {
    var isFileSaverSupported = !!new Blob;
    
    if(isFileSaverSupported){
        var blob = new Blob([data], {type: "text/csv;charset=utf-8"});
        saveAs(blob, fileName, false);
    }
    $.bizinfo("Downloaded");
} catch (e) {
    $.bizinfo("File Download not supported on your browser!!!");
    
}   

}