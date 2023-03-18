package com.parking;

public enum PagesLinks {
    RESERVATION("http://localhost:3000/#/");
    private String path;
    PagesLinks(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
