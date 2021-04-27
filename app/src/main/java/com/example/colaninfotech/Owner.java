package com.example.colaninfotech;

public class Owner {
    private String avatar_url,type;

    public Owner(String avatar_url, String type) {
        this.avatar_url = avatar_url;
        this.type = type;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
