package com.bsj.dao;

import com.bsj.vo.ReplyVO;
import com.bsj.vo.ThreadVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class ThreadDAO extends DAOBase {
    @Autowired
    private ReplyDAO replyDAO;

    public List<ThreadVO> getThreads(int boardID) {
        return getSqliteTemplate().queryForList("SELECT * FROM threads WHERE board = ? ORDER BY id DESC", boardID)
                .stream().map(ThreadDAO::buildThreadVO)
                .collect(Collectors.toList());
    }

    public Map<ThreadVO, ReplyVO> getThreadsWithFirstReply(int boardID) {
        return getThreads(boardID).stream()
                .collect(
                        Collectors.toMap(
                                Function.identity(),
                                thread -> replyDAO.getFirstReply(thread.getId()),
                                (u, v) -> {
                                    throw new IllegalStateException(String.format("Duplicate keys %s", u));
                                },
                                LinkedHashMap::new
                        )
                );
    }

    public ThreadVO getThread(int threadID) {
        Map<String, Object> result = getSqliteTemplate().queryForMap("SELECT * FROM threads WHERE id = ?", threadID);
        return buildThreadVO(result);
    }

    private int getThreadCount(int boardID) {
        return getSqliteTemplate().queryForObject("SELECT COUNT(id) FROM threads", Integer.class);
    }

    private int getOldestThreadID(int boardID) {
        return getSqliteTemplate().queryForObject("SELECT MIN(id) FROM threads WHERE board = ?", Integer.class, boardID);
    }

    public void createThread(int boardID, String threadName, String replyContent) {
        int nextThreadID = getNextID("threads");
        Date now = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        getSqliteTemplate().update(
                "INSERT INTO threads (id, board, title, create_date) VALUES (?, ?, ?, ?)",
                nextThreadID,
                boardID,
                threadName,
                dateFormat.format(now)
        );
        replyDAO.createReply(nextThreadID, replyContent);

        if(getThreadCount(boardID) > 10) {
            deleteOldestThread(boardID);
        }
    }

    private void deleteOldestThread(int boardID) {
        int oldestThreadID = getOldestThreadID(boardID);
        getSqliteTemplate().update("DELETE FROM threads WHERE id = ?", oldestThreadID);
        replyDAO.deleteReplies(oldestThreadID);
    }

    private static ThreadVO buildThreadVO(Map<String, Object> row) {
        ThreadVO thread = new ThreadVO();
        thread.setId((Integer) row.get("id"));
        thread.setBoardID((Integer) row.get("board"));
        thread.setTitle((String) row.get("title"));
        thread.setCreateDate((String) row.get("create_date"));
        return thread;
    }
}
