<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<style>
<!--
body,td,th {
	font-size: 14px;
	color: #000000;
}
-->
</style>

<table border="1" width="100%" cellpadding="0" cellspacing="0" id="tONoticeReceive_table" >
	<tr bgcolor="#E6E6E6">
		<th align="center" bgcolor="#EEEEEE" width="4%">序号</th>
				  <th align="center" bgcolor="#EEEEEE" width="8%">
						办理人
				  </th>
				  <th align="center" bgcolor="#EEEEEE" width="15%">
						文件接收时间
				  </th>
				  <th align="center" bgcolor="#EEEEEE" width="15%">
						文件办理时间
				  </th>
				  <th align="center" bgcolor="#EEEEEE" width="13%">
						办理意见
				  </th>
				  
	</tr>
	<tbody id="add_tONoticeReceive_table">
    <c:forEach items="${stepList}" var="rf" varStatus="stuts">
      <tr height="10">
        <td align="center"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
        <td align="center"><span>${rf.operateUserName}
          </span></td>
        <td align="center"><span>
              <fmt:formatDate value='${rf.receiveTime}' type="date" pattern="MM月dd日 HH:mm" />
          </span></td>
        <td align="center"><span>
            <fmt:formatDate value='${rf.operateTime}' type="date" pattern="MM月dd日 HH:mm" />
          </span></td>
        <td align="center" title="${rf.operateTip}"><c:choose>
            <c:when test="${fn:length(rf.operateTip)<8}">${rf.operateTip}</c:when>
            <c:otherwise>${fn:substring(rf.operateTip,0,8)}...</c:otherwise>
          </c:choose></td>
      </tr>
    </c:forEach>
  </tbody>
</table>
