package com.bsj.controller;

import com.bsj.service.ImageService;
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

@Controller()
@RequestMapping("/images")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping("/{directory}/{fileName}")
    public @ResponseBody ResponseEntity<byte[]> getImage(@PathVariable String directory,
                                                         @PathVariable String fileName) {
        try {
            byte[] content = imageService.getFileContents(directory, fileName);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(content);
        }
        catch(IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{fileName}")
    public @ResponseBody ResponseEntity<byte[]> getImage(@PathVariable String fileName) {
        try {
            byte[] content = imageService.getFileContents("", fileName);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(content);
        }
        catch(IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
