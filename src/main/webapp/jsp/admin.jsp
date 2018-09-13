<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href = "<c:url value="/resources/css/main.css" />" />
<link rel="stylesheet" href = "<c:url value="/resources/css/nsfw.css" />" />
<c:set var = "contextPath" value="<%=request.getContextPath()%>" />

<html>
    <head>
        <title>jChan</title>
    </head>
    <body class="boardBody">
        <%@include file="banner.jsp"%>
        <br />
        <div class="header">
            Admin Login
        </div>
        <br />

        <div class = loginForm>
            <c:choose>
                <c:when test="${not sessionScope.admin}">
                    <form action = "${contextPath}/admin/login" method = "post">
                        <table style = "width: 100%;">
                            <tr>
                                <td>
                                    Username: &nbsp;
                                </td>
                                <td>
                                    <input type = "text" name = "username" style = "width: 100%" placeholder="Username" size="16" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Password: &nbsp;
                                </td>
                                <td>
                                    <input type = "password" name = "password" style = "width: 100%" placeholder="Password" size="16" />
                                </td>
                            </tr>
                            <tr>
                                <td style = "text-align: right" colspan = "2">
                                    <input type = "submit" value = "Submit" />
                                </td>
                            </tr>
                        </table>
                        <input type = "hidden" name = "sentFromURL" value = "${sentFromURL}" />
                    </form>
                </c:when>
                <c:otherwise>
                    <form action = "${contextPath}/admin/logout" method = "post">
                        <table style = "width: 100%">
                            <tr>
                                <td class = "submitFormTitle">
                                    You are already logged in
                                </td>
                            </tr>
                            <tr>
                                <td style = "text-align: center">
                                    <input type = "submit" value = "Sign out" />
                                </td>
                            </tr>
                        </table>
                        <input type = "hidden" name = "sentFromURL" value = "${sentFromURL}" />
                    </form>
                </c:otherwise>
            </c:choose>
        </div>

        <c:if test="${not empty submitError}">
            <div class = "submitError">
                    ${submitError}
            </div>
        </c:if>
    </body>
</html>