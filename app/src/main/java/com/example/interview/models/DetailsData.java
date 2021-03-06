package com.example.interview.models;

import java.util.List;

public class DetailsData {
    private String title;
    private String author;
    private String text;
    private int points;
    private List<Children> children;

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public int getPoints() {
        return points;
    }

    public List<Children> getChildren() {
        return children;
    }

    public String getTitle() {
        return title;
    }
}
