function fileFormatter(value, row, index) {
    var url = "tODiskController.do?viewDiskFile&fileid="+row.id+"&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0";
    return '<a href="'+url+'" style="color:blue;text-decoration: underline;">' + value+'.'+row.extend + '</a>';
}