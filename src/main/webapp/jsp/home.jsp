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
            jChan
        </div>
        <div class = "homeImageDiv">
            <img src = "${contextPath}/images/${mainImage}" style = "border: solid; width: 100%" />
        </div>
    </body>
</html>