<div class="replyHeader">
    <table style = "width: 100%">
        <tr>
            <td style = "text-align: left">No. ${reply.id}</td>
            <td style = "text-align: right">${reply.createDate}</td>
        </tr>
    </table>
</div>
<div class="replyBody">
    ${reply.content}
</div>
<div class="replyFooter">
    <c:choose>
        <c:when test="${not empty threadLink}">
            <form action = "${threadLink}" style = "margin-bottom: 3px">
                <input type = "submit" value = "View Thread" style = "margin-top: 3px"/>
            </form>
        </c:when>
        <c:otherwise>
            &nbsp;
        </c:otherwise>
    </c:choose>
</div>