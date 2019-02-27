<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>提出修改意见</title>
</head>
<body>
  <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
    <tr>
      <td align="right" width="20%">
      <label class="Validform_label">确认状态：</label>
      </td>
      <td class="value" width="80%"  >
         <select id="qrzt" style="width:150px;height: 25px;">
         <option value="1">通过</option>
         <option value="0">返回修改</option>
         </select>
      </td>
    </tr>
    <tr>
      <td align="right" width="20%">
      <label class="Validform_label">意见内容：</label>
      </td>
      <td class="value" width="80%">
      <textarea rows="5" style="width: 90%;height: 100px;" id="msgText">${msgText}</textarea>
      </td>
    </tr>
  </table>
  <script type="text/javascript">
  //获取修改意见
  function getMsgText(){
	 return document.getElementById("msgText").value ;
  }
  
  //获取确认状态
  function getQrzt(){
	 return document.getElementById("qrzt").value;
  }
  
  function closeParent(){
	  frameElement.api.opener.close();
  }
  </script>
</body>
</html>