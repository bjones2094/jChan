package com.bsj.service;

import com.bsj.dao.ThreadDAO;
import com.bsj.vo.ReplyVO;
import com.bsj.vo.ThreadVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ThreadService {
    @Autowired
    private ThreadDAO threadDAO;

    @Autowired
    private ReplyService replyService;

    public ThreadVO getThread(int id) {
        return threadDAO.getThread(id);
    }

    public Map<ThreadVO, ReplyVO> getThreadsWithFirstReply(int boardID) {
        Map<ThreadVO, ReplyVO> threadsWithReplies = new LinkedHashMap();
        for(ThreadVO thread : threadDAO.getThreads(boardID)) {
            ReplyVO firstReply = replyService.getFirstReply(thread.getId());
            if(firstReply != null) {
                threadsWithReplies.put(thread, firstReply);
            }
        }
        return threadsWithReplies;
    }

    public void createThread(int boardID, String threadName, String replyContent, MultipartFile imageUpload) {
        try {
            int threadID = threadDAO.createThread(boardID, threadName);
            replyService.createReply(threadID, replyContent, boardID, imageUpload);

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
        replyService.deleteReplies(oldestThreadID);
    }
}
