package com.bsj.dao;

import com.bsj.vo.ReplyVO;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ReplyDAO extends DAOBase {
    public List<ReplyVO> getReplies(int threadID) {
        return getSqliteTemplate().queryForList("SELECT * FROM replies WHERE thread = ?", threadID)
                .stream().map(ReplyDAO::buildReplyVO)
                .collect(Collectors.toList());
    }

    public ReplyVO getFirstReply(int threadID) {
        try {
            Map<String, Object> result = getSqliteTemplate().queryForMap("SELECT * FROM replies WHERE thread = ? ORDER BY id ASC LIMIT 1", threadID);
            return buildReplyVO(result);
        }
        catch(DataAccessException e) {
            return null;
        }
    }

    public int createReply(int threadID, String content) {
        Date now = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return getSqliteTemplate().update(
                "INSERT INTO replies (content, thread, create_date) VALUES(?,?,?)",
                content,
                threadID,
                dateFormat.format(now)
        );
    }

    public void deleteReplies(int threadID) {
        getSqliteTemplate().update("DELETE FROM replies WHERE thread = ?", threadID);
    }

    private static ReplyVO buildReplyVO(Map<String, Object> row) {
        ReplyVO reply = new ReplyVO();
        reply.setId((Integer) row.get("id"));
        reply.setContent((String) row.get("content"));
        reply.setCreateDate((String) row.get("create_date"));
        reply.setThreadID((Integer) row.get("thread"));
        return reply;
    }
}
