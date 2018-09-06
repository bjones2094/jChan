package com.bsj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller()
@RequestMapping("/images")
public class ImageController {
    @Autowired
    private String uploadedImageDirectory;

    @GetMapping("/{directory}/{fileName}")
    public @ResponseBody ResponseEntity<byte[]> getImage(
            @PathVariable String directory,
            @PathVariable String fileName
    ) {
        try {
            String path = uploadedImageDirectory + "/" + directory + "/" + fileName;
            byte[] content = Files.readAllBytes(Paths.get(path));
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(content);
        }
        catch(IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
