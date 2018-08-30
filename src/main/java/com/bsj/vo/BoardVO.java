package com.bsj.vo;

public class BoardVO {
    private int id;
    private String name;
    private String description;
    private String directory;
    private boolean nsfw;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setNsfw(boolean nsfw) {
        this.nsfw = nsfw;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDirectory() {
        return directory;
    }

    public boolean isNsfw() {
        return nsfw;
    }
}
