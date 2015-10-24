<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Spring Web MVC project</title>
        <script type="text/javascript" src="<c:url value="resources/js/ajax_functions.js" />"></script>
        <script type="text/javascript" src="<c:url value="resources/js/one.js" />"></script>
    </head>

    <body>
        <form action="#" name="firstForm" id="firstForm">

            <table>
                <tr>
                    <td>Name ::</td>
                    <td><input id="name" name="name" value="" type="text"/></td>

                </tr>
                <tr>
                    <td>Age ::</td>
                    <td><input id="age" name="age" value="" type="text"/></td>

                </tr>
                <tr>
                    <td><input type="button" name="submit" id="submit" value="Submit" onclick="javascript:onSubmit();"/> </td>
                    <td><input type="button" name="submit11" id="submit11" value="Display List" onclick="javascript:onDisplay();"/></td>

                </tr>
            </table>

        </form>
        <div id="messageDiv">

        </div>
    </body>
</html>
