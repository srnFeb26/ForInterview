package com.example.interview.models;


import java.util.List;

public class Children {
    private String author;
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
