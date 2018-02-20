package com.example.adolfo.rssyoutube;


public class Videos {

    private String miniaturaURL;
    private String title;
    private String id;
    private String views;


    public Videos() {
    }

    public Videos(String miniaturaURL, String title, String views, String id) {
        this.id = id;
        this.miniaturaURL = miniaturaURL;
        this.title = title;
        this.views = views;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMiniaturaURL() {
        return miniaturaURL;
    }

    public void setMiniaturaURL(String miniaturaURL) {
        this.miniaturaURL = miniaturaURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return getTitle() + " - " + getViews();
    }

}
