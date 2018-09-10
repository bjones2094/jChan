package com.bsj.service;

import com.bsj.dao.BoardDAO;
import com.bsj.dao.ImageDAO;
import com.bsj.dao.ReplyDAO;
import com.bsj.dao.ThreadDAO;
import com.bsj.vo.ReplyVO;
import com.bsj.vo.ThreadVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class ThreadService {
    private static final Logger log = LoggerFactory.getLogger(ThreadService.class);

    @Autowired
    private BoardDAO boardDAO;

    @Autowired
    private ThreadDAO threadDAO;

    @Autowired
    private ReplyDAO replyDAO;

    @Autowired
    private ImageDAO imageDAO;

    public ThreadVO getThread(int id) {
        return threadDAO.getThread(id);
    }

    public Map<ThreadVO, ReplyVO> getThreadsWithFirstReply(int boardID) {
        Map<ThreadVO, ReplyVO> threadsWithReplies = new LinkedHashMap();
        for(ThreadVO thread : threadDAO.getThreadsOrderByStaleness(boardID)) {
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

    public void deleteThread(int threadID, int boardID) {
        List<String> fileNames = threadDAO.getImageNames(threadID);
        threadDAO.deleteThread(threadID);
        replyDAO.deleteReplies(threadID);
        String directory = boardDAO.getDirectory(boardID);
        for(String fileName : fileNames) {
            imageDAO.deleteImage(directory, fileName);
        }
    }

    public void deleteOldestThread(int boardID) {
        int oldestThreadID = threadDAO.getStalestThreadID(boardID);
        threadDAO.deleteThread(oldestThreadID);
        replyDAO.deleteReplies(oldestThreadID);
    }
}
