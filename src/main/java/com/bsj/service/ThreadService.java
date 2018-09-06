package com.bsj.service;

import com.bsj.dao.ReplyDAO;
import com.bsj.dao.ThreadDAO;
import com.bsj.vo.ReplyVO;
import com.bsj.vo.ThreadVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ThreadService {
    @Autowired
    private ThreadDAO threadDAO;

    @Autowired
    private ReplyDAO replyDAO;

    public ThreadVO getThread(int id) {
        return threadDAO.getThread(id);
    }

    public Map<ThreadVO, ReplyVO> getThreadsWithFirstReply(int boardID) {
        Map<ThreadVO, ReplyVO> threadsWithReplies = new LinkedHashMap();
        for(ThreadVO thread : threadDAO.getThreads(boardID)) {
            ReplyVO firstReply = replyDAO.getFirstReply(thread.getId());
            if(firstReply != null) {
                threadsWithReplies.put(thread, firstReply);
            }
        }
        return threadsWithReplies;
    }

    public void createThread(int boardID, String threadName, String replyContent) {
        try {
            int threadID = threadDAO.createThread(boardID, threadName);
            replyDAO.createReply(threadID, replyContent);

            if(threadDAO.getThreadCount(boardID) > 10) {
                deleteOldestThread(boardID);
            }
        }
        catch(SQLException e) {
            // TODO: Make a logger
        }
    }

    public void deleteOldestThread(int boardID) {
        int oldestThreadID = threadDAO.getOldestThreadID(boardID);
        threadDAO.deleteThread(oldestThreadID);
        replyDAO.deleteReplies(oldestThreadID);
    }
}
