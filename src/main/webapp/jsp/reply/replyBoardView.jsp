<script src = "<c:url value="/resources/js/main.js" />"></script>

<div class="replyHeader">
    <table style = "width: 100%">
        <tr>
            <c:choose>
                <c:when test="${not empty reply.postedBy}">
                    <td style = "text-align: left; color: red;">
                        <b>${reply.postedBy} ## Admin</b>
                    </td>
                </c:when>
                <c:otherwise>
                    <td style = "text-align: left; color: darkgreen;">
                        <b>Anonymous</b>
                    </td>
                </c:otherwise>
            </c:choose>
            <td style = "text-align: right">
                ${reply.createDate} &nbsp; No. ${reply.id}
            </td>
        </tr>
        <c:if test = "${not empty thread.key.title}">
            <tr>
                <td style = "text-align: left">
                    <b>${thread.key.title}</b>
                </td>
            </tr>
        </c:if>
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
            <c:if test = "${sessionScope.admin}">
                <td style = "text-align: left">
                    <input type = "button" value = "Delete Thread" class = "replyFooterButton deleteThreadButton" onclick="confirmDelete('deleteThreadSubmit', 'thread')"/>
                    <form id = "deleteThreadSubmit" hidden = "true" action = "${threadLink}/delete" method = "post">
                        <input type = "hidden" name = "boardID" value = "${board.id}" />
                    </form>
                </td>
            </c:if>
            <td style = "text-align: right">
                <form action = "${threadLink}" style = "margin-bottom: 2px">
                    <input type = "submit" value = "View Thread" class=" replyFooterButton viewThreadButton"/>
                </form>
            </td>
        </tr>
    </table>
</div>