<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href = "<c:url value="/resources/css/main.css" />" />
<c:set var = "contextPath" value="<%=request.getContextPath()%>" />

<div>
    Boards: &nbsp; /
    <c:forEach items = "${boards}" var = "boardName">
        &nbsp;
        <a class="bannerLink" href="${contextPath}/${boardName}">
                ${boardName}
        </a>
        &nbsp; /
    </c:forEach>
</div>