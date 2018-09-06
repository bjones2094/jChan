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
            <form action = "${contextPath}/${board.name}/thread/${thread.id}/submit/" method = "post" autocomplete="off" enctype = "multipart/form-data">
                <table style = "width: 100%">
                    <tr class = "submitFormTitle">
                        <td style = "text-align: center" colspan = "2">
                            Post a reply:
                        </td>
                    </tr>
                    <tr>
                        <td style = "width: 100%" colspan = "2">
                            <textArea rows = "6" name = "replyContent" style = "width: 100%" placeholder = "Description" maxlength="200"></textArea>
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
            <br />
        </c:if>

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