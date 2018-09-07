<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href = "<c:url value="/resources/css/main.css" />" />
<c:set var = "contextPath" value="<%=request.getContextPath()%>" />

<div>
    <a class = "bannerLink" href="${contextPath}">home</a>
    &nbsp; / &nbsp;
    <a class = "bannerLink" href="${contextPath}/admin">admin</a>
    <c:forEach items = "${boards}" var = "boardName">
        &nbsp; / &nbsp;
        <a class="bannerLink" href="${contextPath}/${boardName}">
                ${boardName}
        </a>
    </c:forEach>
</div>
<hr />