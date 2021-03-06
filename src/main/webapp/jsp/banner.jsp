<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href = "<c:url value="/resources/css/main.css" />" />
<script src = "<c:url value="/resources/js/main.js" />"></script>
<c:set var = "contextPath" value="<%=request.getContextPath()%>" />

<div>
    <table style = "width: 100%">
        <tr class = "bannerText">
            <td style = "text-align: left; width: 33%">
                Boards:
                &nbsp; /
                <c:forEach items = "${boards}" var = "boardName">
                    &nbsp;
                    <a class="bannerLink" href="${contextPath}/${boardName}">
                            ${boardName}
                    </a>
                    &nbsp; /
                </c:forEach>
            </td>
            <td style = "text-align: center; width: 33%">
                <a class = "bannerLink" href="${contextPath}">Home</a>
            </td>
            <td style = "text-align: right; width: 33%">
                <form id = "adminLinkForm" action = "${contextPath}/admin" method = "post">
                    <input type = "hidden" name = "sentFromURL" value = "${pageContext.request.requestURL}" />
                </form>
                <a class = "bannerLink" onclick="submitForm('adminLinkForm')">
                    <c:choose>
                        <c:when test = "${sessionScope.admin}">
                            Sign out
                        </c:when>
                        <c:otherwise>
                            Sign in
                        </c:otherwise>
                    </c:choose>
                </a>
            </td>
        </tr>
    </table>
    <hr style = "margin-top: 2px" />
</div>