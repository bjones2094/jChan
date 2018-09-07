package com.bsj.service;

import com.bsj.dao.ReplyDAO;
import com.bsj.vo.ReplyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class ReplyService {
    private static final Logger log = LoggerFactory.getLogger(ReplyService.class);

    @Autowired
    private ReplyDAO replyDAO;

    public List<ReplyVO> getReplies(int threadID) {
        return replyDAO.getReplies(threadID);
    }

    public int createReply(int threadID, String content) {
        try {
            return replyDAO.createReply(threadID, content);
        }
        catch(SQLException e) {
            log.error(e.getMessage(), e);
            return -1;
        }
    }

    public void associateImageToReply(int replyID, String imagePath) {
        replyDAO.associateImageToReply(replyID, imagePath);
    }
}
