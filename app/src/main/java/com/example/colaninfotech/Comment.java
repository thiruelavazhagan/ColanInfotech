package com.example.colaninfotech;

public class Comment {
    private String id,uid,comment;

    public Comment(String id, String uid, String comment) {
        this.id = id;
        this.uid = uid;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
