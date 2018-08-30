<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href = "<c:url value="/resources/css/main.css" />" />
<c:set var = "contextPath" value="<%=request.getContextPath()%>" />
<c:choose>
    <c:when test="${board.nsfw}">
        <link rel="stylesheet" href = "<c:url value="/resources/css/nsfw.css" />" />
    </c:when>
    <c:otherwise>
        <link rel="stylesheet" href = "<c:url value="/resources/css/sfw.css" />" />
    </c:otherwise>
</c:choose>

<html>
    <head>
        <title>jChan</title>
    </head>
    <body class="boardBody">
        <div class="header">
            /${board.name}/ - ${board.description}
        </div>

        <%@include file="banner.jsp"%>

        <hr /><br />

        <div class = "submitForm">
            <form action = "${contextPath}/${board.name}/thread/${thread.id}/submit/" method = "post" autocomplete="off">
                <table style = "width: 100%">
                    <tr class = "submitFormTitle">
                        <td style = "text-align: center">Post a reply:</td>
                    </tr>
                    <tr >
                        <td style = "width: 100%">
                            <textArea rows = "6" name = "replyContent" style = "width: 100%" placeholder = "Reply" maxlength="200"></textArea>
                        </td>
                    </tr>
                    <tr>
                        <td style = "text-align: right">
                            <input type = "submit" value = "Submit" />
                        </td>
                    </tr>
                </table>
            </form>
        </div>

        <div class = "replyList">
            <div class="threadTitle">
                ${thread.title}
            </div>

            <c:forEach items = "${replies}" var = "reply">
                <%@include file = "reply.jsp" %>
                <br />
            </c:forEach>
        </div>
    </body>
</html>