package com.example.interview.models;

import java.util.List;

public class SearchResultData {
    private String params;
    private String query;
    private long nbHits;
    private List<Hit> hits;

    public String getParams() {
        return params;
    }

    public String getQuery() {
        return query;
    }

    public long getNbHits() {
        return nbHits;
    }

    public List<Hit> getHits() {
        return hits;
    }
}
