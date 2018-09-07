package com.bsj.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Repository
public class ImageDAO {
    @Value("${uploaded.images.directory}")
    private String uploadedImageDirectory;

    public byte[] getFileContents(String directory, String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(uploadedImageDirectory + "/" + directory + "/" + fileName));
    }

    public String saveImage(int threadID, int replyID, String directory, MultipartFile imageUpload) throws IOException {
        String fileExtension;
        if(imageUpload.getName().lastIndexOf(".") > 0) {
            fileExtension = imageUpload.getName().substring(imageUpload.getName().lastIndexOf("."));
        }
        else {
            fileExtension = ".jpg";
        }

        String fileName = threadID + "_" + replyID + fileExtension;
        String totalName = uploadedImageDirectory + "/" + directory + "/" + fileName;

        FileOutputStream os = new FileOutputStream(totalName);
        os.write(imageUpload.getBytes());

        return fileName;
    }
}
