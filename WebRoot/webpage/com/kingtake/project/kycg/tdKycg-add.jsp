<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>TD_KYCG</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tdKycgController.do?doAdd" tiptype="1">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							cgdm:
						</label>
					</td>
					<td class="value">
					     	 <input id="cgdm" name="cgdm" type="text" style="width: 150px" class="inputxt"  
								               datatype="*"
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">cgdm</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							xmdm:
						</label>
					</td>
					<td class="value">
					     	 <input id="xmdm" name="xmdm" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">xmdm</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							jcbh:
						</label>
					</td>
					<td class="value">
					     	 <input id="jcbh" name="jcbh" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">jcbh</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							cgmc:
						</label>
					</td>
					<td class="value">
					     	 <input id="cgmc" name="cgmc" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">cgmc</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							完成单位:
						</label>
					</td>
					<td class="value">
					     	 <input id="wcdw" name="wcdw" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">完成单位</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							xmzlxr:
						</label>
					</td>
					<td class="value">
					     	 <input id="xmzlxr" name="xmzlxr" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">xmzlxr</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							xmzlxrs:
						</label>
					</td>
					<td class="value">
					     	 <input id="xmzlxrs" name="xmzlxrs" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">xmzlxrs</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							jglxr:
						</label>
					</td>
					<td class="value">
					     	 <input id="jglxr" name="jglxr" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">jglxr</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							jglxfs:
						</label>
					</td>
					<td class="value">
					     	 <input id="jglxfs" name="jglxfs" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">jglxfs</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							jdsj:
						</label>
					</td>
					<td class="value">
							   <input id="jdsj" name="jdsj" type="text" style="width: 150px" 
					      						class="Wdate" onClick="WdatePicker()"
								                
								               >    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">jdsj</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							jddd:
						</label>
					</td>
					<td class="value">
					     	 <input id="jddd" name="jddd" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">jddd</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							jdxs:
						</label>
					</td>
					<td class="value">
					     	 <input id="jdxs" name="jdxs" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">jdxs</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							cgzt:
						</label>
					</td>
					<td class="value">
					     	 <input id="cgzt" name="cgzt" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">cgzt</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							sqrq:
						</label>
					</td>
					<td class="value">
							   <input id="sqrq" name="sqrq" type="text" style="width: 150px" 
					      						class="Wdate" onClick="WdatePicker()"
								                
								               >    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">sqrq</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							gdh:
						</label>
					</td>
					<td class="value">
					     	 <input id="gdh" name="gdh" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">gdh</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							scrq:
						</label>
					</td>
					<td class="value">
							   <input id="scrq" name="scrq" type="text" style="width: 150px" 
					      						class="Wdate" onClick="WdatePicker()"
								                
								               >    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">scrq</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							sbrq:
						</label>
					</td>
					<td class="value">
							   <input id="sbrq" name="sbrq" type="text" style="width: 150px" 
					      						class="Wdate" onClick="WdatePicker()"
								                
								               >    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">sbrq</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							jdsph:
						</label>
					</td>
					<td class="value">
					     	 <input id="jdsph" name="jdsph" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">jdsph</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							tzbh:
						</label>
					</td>
					<td class="value">
					     	 <input id="tzbh" name="tzbh" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">tzbh</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							clsbrq:
						</label>
					</td>
					<td class="value">
							   <input id="clsbrq" name="clsbrq" type="text" style="width: 150px" 
					      						class="Wdate" onClick="WdatePicker()"
								                
								               >    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">clsbrq</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							sppj:
						</label>
					</td>
					<td class="value">
					     	 <input id="sppj" name="sppj" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">sppj</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							zsbh:
						</label>
					</td>
					<td class="value">
					     	 <input id="zsbh" name="zsbh" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">zsbh</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							wcr:
						</label>
					</td>
					<td class="value">
					     	 <input id="wcr" name="wcr" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">wcr</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							zslqr:
						</label>
					</td>
					<td class="value">
					     	 <input id="zslqr" name="zslqr" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">zslqr</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							zslqrq:
						</label>
					</td>
					<td class="value">
							   <input id="zslqrq" name="zslqrq" type="text" style="width: 150px" 
					      						class="Wdate" onClick="WdatePicker()"
								                
								               >    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">zslqrq</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							bz:
						</label>
					</td>
					<td class="value">
						  	 <textarea style="width:600px;" class="inputxt" rows="6" id="bz" name="bz"></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">bz</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/project/kycg/tdKycg.js"></script>		