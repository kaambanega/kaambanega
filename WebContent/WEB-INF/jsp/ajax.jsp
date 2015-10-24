<%-- 
    Document   : ajax
    Created on : Oct 18, 2015, 11:56:42 PM
    Author     : Fenil Jariwala
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:choose>
    <c:when test="${task eq 'insert'}">

        ${message}
    </c:when>
    <c:when test="${task eq 'display'}">

        <table>
            <thead>
            	<th>ID</th>
            	<th>Name</th>
            	<th>Age</th>
        	</thead>
        	<c:forEach items="${list}" var="l">
            	<tr>
                	<td>${l.id}</td>
                	<td>${l.name}</td>
                	<td>${l.age}</td>
            	</tr>
        	</c:forEach>
    	</table>
	</c:when>

</c:choose>
