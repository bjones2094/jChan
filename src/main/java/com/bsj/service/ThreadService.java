package com.bsj.service;

import com.bsj.dao.BoardDAO;
import com.bsj.dao.ImageDAO;
import com.bsj.dao.ThreadDAO;
import com.bsj.vo.ReplyVO;
import com.bsj.vo.ThreadVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class ThreadService {
    @Autowired
    private BoardDAO boardDAO;

    @Autowired
    private ThreadDAO threadDAO;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private ImageDAO imageDAO;

    public ThreadVO getThread(int id) {
        return threadDAO.getThread(id);
    }

    public Map<ThreadVO, ReplyVO> getThreadsWithFirstReply(int boardID) {
        Map<ThreadVO, ReplyVO> threadsWithReplies = new LinkedHashMap();
        for(ThreadVO thread : threadDAO.getThreadsOrderByStaleness(boardID)) {
            ReplyVO firstReply = replyService.getFirstReply(thread.getId());
            if(firstReply != null) {
                threadsWithReplies.put(thread, firstReply);
            }
        }
        return threadsWithReplies;
    }

    public int createThread(int boardID, String threadName, String replyContent, MultipartFile imageUpload, String postedBy) throws Exception {
        int threadID = threadDAO.createThread(boardID, threadName);
        replyService.createReply(boardID, threadID, replyContent, imageUpload, postedBy);
        if(threadDAO.getThreadCount(boardID) > 10) {
            deleteOldestThread(boardID);
        }
        return threadID;
    }

    public void deleteThread(int threadID, int boardID) {
        List<String> fileNames = threadDAO.getImageNames(threadID);
        threadDAO.deleteThread(threadID);
        replyService.deleteReplies(threadID);
        String directory = boardDAO.getDirectory(boardID);
        for(String fileName : fileNames) {
            imageDAO.deleteImage(directory, fileName);
        }
    }

    public void deleteOldestThread(int boardID) {
        int oldestThreadID = threadDAO.getStalestThreadID(boardID);
        deleteThread(boardID, oldestThreadID);
    }
}
