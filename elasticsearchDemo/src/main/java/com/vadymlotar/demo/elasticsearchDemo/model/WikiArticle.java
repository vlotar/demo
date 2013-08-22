package com.vadymlotar.demo.elasticsearchDemo.model;

/**
 * Representation of one WIKI article in Java.
 * User: vlotar
 */
public class WikiArticle {

    private String id;
    private String name;

    public WikiArticle(String id, String name) {
        this.id = id;
        this.name = name;
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
}
