package com.bsj.vo;

public class ThreadVO {
    private Integer id;
    private Integer boardID;
    private String title;
    private String createDate;

    public Integer getId() {
        return id;
    }

    public Integer getBoardID() {
        return boardID;
    }

    public String getTitle() {
        return title;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setBoardID(Integer boardID) {
        this.boardID = boardID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
