package com.example.colaninfotech;

import java.io.Serializable;

public class Data implements Serializable {
    private String id,name,full_name,description,language;
    private Owner owner;

    public Data(String id, String name, String full_name, String description, String language, Owner owner) {
        this.id = id;
        this.name = name;
        this.full_name = full_name;
        this.description = description;
        this.language = language;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
