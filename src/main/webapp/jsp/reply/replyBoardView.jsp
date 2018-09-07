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
            <c:if test = "${sessionScope.admin}">
                <td style = "text-align: left">
                    <input type = "button" value = "Delete Thread" class = "deleteThreadButton" onclick="confirmThreadDelete()"/>
                    <form id = "deleteThreadSubmit" hidden = "true" action = "${threadLink}/delete" method = "post">
                        <input type = "hidden" name = "boardID" value = "${board.id}" />
                    </form>
                </td>
            </c:if>
            <td style = "text-align: right">
                <form action = "${threadLink}" style = "margin-bottom: 2px">
                    <input type = "submit" value = "View Thread" style = "margin-top: 3px; margin-right: 3px"/>
                </form>
            </td>
        </tr>
    </table>
</div>

<script type="application/javascript">
    function confirmThreadDelete() {
        if(confirm("Are you sure you want to delete this thread?")) {
            document.getElementById('deleteThreadSubmit').submit();
        }
    }
</script>