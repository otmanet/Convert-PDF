package com.example.ConvertFilePdfToWord.Model;

public class fileDetails {

    private String name;
    private String url;
    private String absolutePath;

    public fileDetails() {
        super();
    }

    public fileDetails(String name, String url, String absolutePath) {
        this.name = name;
        this.url = url;
        this.absolutePath = absolutePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

}
