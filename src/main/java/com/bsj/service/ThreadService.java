package com.bsj.service;

import com.bsj.dao.ReplyDAO;
import com.bsj.dao.ThreadDAO;
import com.bsj.vo.ReplyVO;
import com.bsj.vo.ThreadVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ThreadService {
    private static final Logger log = LoggerFactory.getLogger(ThreadService.class);

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

    public int createThread(int boardID, String threadName) {
        try {
            int threadID = threadDAO.createThread(boardID, threadName);
            if(threadDAO.getThreadCount(boardID) > 10) {
                deleteOldestThread(boardID);
            }
            return threadID;
        }
        catch(Exception e) {
            log.error(e.getMessage(), e);
            return -1;
        }
    }

    public void deleteThread(int threadID) {
        threadDAO.deleteThread(threadID);
    }

    public void deleteOldestThread(int boardID) {
        int oldestThreadID = threadDAO.getOldestThreadID(boardID);
        threadDAO.deleteThread(oldestThreadID);
        replyDAO.deleteReplies(oldestThreadID);
    }
}
