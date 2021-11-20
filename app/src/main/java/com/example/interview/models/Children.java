package com.example.interview.models;

import androidx.room.PrimaryKey;

import java.util.List;

public class Children {
    @PrimaryKey
    private String author;
    @PrimaryKey
    private String text;
    private List<Children> children;

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public List<Children> getChildren() {
        return children;
    }
}
