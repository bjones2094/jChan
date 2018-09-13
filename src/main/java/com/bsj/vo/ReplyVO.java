package com.bsj.vo;

public class ReplyVO {
    private Integer id;
    private String content;
    private String createDate;
    private Integer threadID;
    private String imagePath;
    private String postedBy;

    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public Integer getThreadID() {
        return threadID;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setThreadID(Integer threadID) {
        this.threadID = threadID;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }
}
