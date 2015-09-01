package com.arashiq.rssreader.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by A13054 on 2015/08/06.
 */
public class RssItem extends RealmObject {
    @PrimaryKey
    private String id;
    private String title;
    private String link;
    private String description;
    private String category;
    private String author;
    private RssFeed feed;

    private boolean isFavorite;

    public RssItem(String id, String title, String link, String description, String category, String author, RssFeed feed, boolean isFavorite) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.category = category;
        this.author = author;
        this.feed = feed;
        this.isFavorite = isFavorite;
    }

    @Override
    public String toString() {
        return title;
    }

    public String getid() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public RssFeed getFeed() {
        return feed;
    }
}
