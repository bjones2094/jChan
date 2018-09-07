<div class="replyHeader">
    <table style = "width: 100%">
        <tr>
            <td style = "text-align: left">No. ${reply.id}</td>
            <td style = "text-align: right">${reply.createDate}</td>
        </tr>
    </table>
</div>
<div class="replyBody">
    <table cellspacing="10">
        <tr valign = "top">
            <c:if test="${not empty reply.imagePath}">
                <td>
                    <a target = "_blank" href = "${contextPath}/images/${board.directory}/${reply.imagePath}">
                        <img src = "${contextPath}/images/${board.directory}/${reply.imagePath}" style = "width: 125px; border: solid 1px var(--trim-color)" />
                    </a>
                </td>
            </c:if>
            <td>
                ${reply.content}
            </td>
        </tr>
    </table>
</div>
<div class="replyFooter">
    &nbsp;
</div>