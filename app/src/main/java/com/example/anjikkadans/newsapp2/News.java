package com.example.anjikkadans.newsapp2;

public class News {
    private String newsTitle;
    private String newsType;
    private String newsURL;

    public News(String newsTitle, String newsType, String newsURL) {
        this.newsTitle = newsTitle;
        this.newsType = newsType;
        this.newsURL = newsURL;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsType() {
        return newsType;
    }

    public String getNewsURL() {
        return newsURL;
    }
}
