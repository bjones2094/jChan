<script src = "<c:url value="/resources/js/main.js" />"></script>

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
    <table style = "width: 100%">
        <tr>
            <c:choose>
                <c:when test = "${(sessionScope.admin) and (loop.index ne 0)}">
                    <td style = "text-align: left">
                        <input type = "button" value = "Delete Reply" class = "replyFooterButton deleteThreadButton" onclick="confirmDelete('deleteReplySubmit', 'reply')"/>
                        <form id = "deleteReplySubmit" hidden = "true" action = "${threadLink}/${reply.id}/delete" method = "post">
                            <input type = "hidden" name = "boardID" value = "${board.id}" />
                        </form>
                    </td>
                </c:when>
                <c:when test = "${(sessionScope.admin) and (loop.index eq 0)}">
                    <td style = "text-align: left">
                        <input type = "button" value = "Delete Thread" class = "replyFooterButton deleteThreadButton" onclick="confirmDelete('deleteThreadSubmit', 'thread')"/>
                        <form id = "deleteThreadSubmit" hidden = "true" action = "${threadLink}/delete" method = "post">
                            <input type = "hidden" name = "boardID" value = "${board.id}" />
                        </form>
                    </td>
                </c:when>
                <c:otherwise>
                    &nbsp;
                </c:otherwise>
            </c:choose>
        </tr>
    </table>
</div>