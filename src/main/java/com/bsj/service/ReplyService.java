package com.bsj.service;

import com.bsj.dao.ReplyDAO;
import com.bsj.vo.ReplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReplyService {
    @Autowired
    private ReplyDAO replyDAO;

    public List<ReplyVO> getReplies(int threadID) {
        return replyDAO.getReplies(threadID);
    }

    public int createReply(int threadID, String content) {
        return replyDAO.createReply(threadID, content);
    }
}
