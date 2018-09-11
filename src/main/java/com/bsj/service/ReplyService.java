package com.bsj.service;

import com.bsj.dao.BoardDAO;
import com.bsj.dao.ImageDAO;
import com.bsj.dao.ReplyDAO;
import com.bsj.vo.ReplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class ReplyService {
    @Autowired
    private BoardDAO boardDAO;

    @Autowired
    private ReplyDAO replyDAO;

    @Autowired
    private ImageDAO imageDAO;

    public List<ReplyVO> getReplies(int threadID) {
        return replyDAO.getReplies(threadID);
    }

    public ReplyVO getFirstReply(int threadID) {
        return replyDAO.getFirstReply(threadID);
    }

    public int createReply(int boardID, int threadID, String content, MultipartFile imageUpload) throws Exception {
        int replyID = replyDAO.createReply(threadID, content);
        if(imageUpload != null && !imageUpload.isEmpty()) {
            String directory = boardDAO.getDirectory(boardID);
            String imagePath = imageDAO.saveImage(threadID, replyID, directory, imageUpload);
            replyDAO.associateImageToReply(replyID, imagePath);
        }

        return replyID;
    }

    public void deleteReply(int boardID, int replyID) {
        String directory = boardDAO.getDirectory(boardID);
        String fileName = replyDAO.getImageName(replyID);
        replyDAO.deleteReply(replyID);
        imageDAO.deleteImage(directory, fileName);
    }

    public void deleteReplies(int threadID) {
        replyDAO.deleteReplies(threadID);
    }
}
