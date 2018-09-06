package com.bsj.service;

import com.bsj.dao.BoardDAO;
import com.bsj.dao.ReplyDAO;
import com.bsj.vo.ReplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@Component
public class ReplyService {
    @Autowired
    private ReplyDAO replyDAO;

    @Autowired
    private BoardDAO boardDAO;

    public List<ReplyVO> getReplies(int threadID) {
        return replyDAO.getReplies(threadID);
    }

    public ReplyVO getFirstReply(int threadID) {
        return replyDAO.getFirstReply(threadID);
    }

    public void createReply(int threadID, String content, int boardID, MultipartFile imageUpload) {
        try {
            int replyID = replyDAO.createReply(threadID, content);
            if(imageUpload != null) {
                String filePath = boardDAO.saveImage(threadID, replyID, boardID, imageUpload);
                if(filePath != null) {
                    replyDAO.associateFileToReply(replyID, filePath);
                }
            }
        }
        catch(SQLException e) {
            // TODO: Make a Logger
        }
    }

    public void deleteReplies(int threadID) {
        replyDAO.deleteReplies(threadID);
    }
}
