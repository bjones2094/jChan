<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href = "<c:url value="/resources/css/main.css" />" />
<script src = "<c:url value="/resources/js/main.js" />"></script>
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
        <%@include file="banner.jsp"%>
        <br />
        <div class="header">
            /${board.name}/ - ${board.description}
        </div>

        <br /><br />

        <table class="actionButtonsTable" cellspacing="5">
            <tr>
                <td>
                    <form action = "javascript:unhideElement('submitFormDiv')">
                        <input type = "submit" value = "Start a thread" class = "replyFooterButton actionButton">
                    </form>
                </td>
                <td>
                    <form action = "${contextPath}/${board.name}/catalog">
                        <input type = "submit" value = "Catalog" class = "replyFooterButton actionButton">
                    </form>
                </td>
            </tr>
        </table>

        <div id = "submitFormDiv" class = "submitForm" hidden = "true">
            <form action = "${contextPath}/${board.name}/submit/" method = "post" autocomplete="off" enctype="multipart/form-data">
                <table style = "width: 100%">
                    <tr>
                        <td style = "width: 100%" colspan = "2">
                            <input type = "text" name = "threadName" style = "width: 100%" placeholder="Title" size="16" />
                        </td>
                    </tr>
                    <tr >
                        <td style = "width: 100%" colspan = "2">
                            <textArea rows = "6" name = "replyContent" style = "width: 100%" placeholder="Description" maxlength="200"></textArea>
                        </td>
                    </tr>
                    <tr>
                        <td style = "text-align: left">
                            <input type = "file" name = "imageUpload" accept = "image/*" value = "Upload a File" />
                        </td>
                        <td style = "text-align: right">
                            <input type = "submit" value = "Submit" />
                        </td>
                    </tr>
                </table>
                <input type = "hidden" name = "boardID" value = "${board.id}" />
            </form>
        </div>

        <c:if test="${not empty submitError}">
            <div class = "submitError">
                ${submitError}
            </div>
        </c:if>
        <c:if test="${not empty displayMessage}">
            <div class = "displayMessage">
                    ${displayMessage}
            </div>
        </c:if>

        <br />

        <div class = "replyList">
            <c:forEach items = "${threads}" var = "thread">
                <c:set var = "threadLink" value = "${contextPath}/${board.name}/thread/${thread.key.id}" />
                <c:set var = "reply" value = "${thread.value}" />
                <%@include file = "reply/replyBoardView.jsp"%>
                <br />
            </c:forEach>
            </ul>
        </div>
    </body>
</html>