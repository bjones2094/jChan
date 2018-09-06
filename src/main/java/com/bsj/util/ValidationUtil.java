package com.bsj.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class ValidationUtil {
    private static final long MAX_IMAGE_SIZE = 3 * 1024 * 1024;

    public static String validateReplySubmission(String replyContent, MultipartFile imageUpload) {
        String errorMessage = null;
        if(StringUtils.isBlank(replyContent)) {
            if(imageUpload == null || imageUpload.isEmpty()) {
                errorMessage = "Submission must have a description or an image.";
            }
            else if(imageUpload.getSize() > MAX_IMAGE_SIZE) {
                errorMessage = "Image must be less than 3MB";
            }
        }
        return errorMessage;
    }
}
