<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href = "<c:url value="/resources/css/main.css" />" />
<c:set var = "contextPath" value="<%=request.getContextPath()%>" />

<div>
    <table style = "width: 100%">
        <tr class = "bannerText">
            <td style = "text-align: left">
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

            <td style = "text-align: right">
                <a class = "bannerLink" href="${contextPath}">home</a>
                &nbsp; / &nbsp;
                <a class = "bannerLink" href="${contextPath}/admin">admin</a>
            </td>
        </tr>
    </table>
</div>
<hr />