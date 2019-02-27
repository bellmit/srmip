var OFFICE_CONTROL_OBJ;//控件对象
var IsFileOpened;      //控件是否打开文档
var fileType ;
var fileTypeSimple;



function setFileOpenedOrClosed(bool)
{
	OFFICE_CONTROL_OBJ=document.getElementById("TANGER_OCX");
	IsFileOpened = bool;
	fileType = OFFICE_CONTROL_OBJ.DocType ;
}



function TANGER_OCX_openFromUrl(templateUrl,bool)
{
	OFFICE_CONTROL_OBJ=document.getElementById("TANGER_OCX");
	OFFICE_CONTROL_OBJ.openFromUrl(templateUrl);
}
