package com.bsj.service;

import com.bsj.dao.BoardDAO;
import com.bsj.dao.ImageDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class ImageService {
    private static final Logger log = LoggerFactory.getLogger(ImageService.class);

    @Autowired
    private ImageDAO imageDAO;

    @Autowired
    private BoardDAO boardDAO;

    public byte[] getFileContents(String directory, String fileName) throws IOException {
        return imageDAO.getFileContents(directory, fileName);
    }

    public String saveImage(int threadID, int replyID, int boardID, MultipartFile imageUpload) {
        String directory = boardDAO.getDirectory(boardID);
        try {
            return imageDAO.saveImage(threadID, replyID, directory, imageUpload);
        }
        catch(IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
