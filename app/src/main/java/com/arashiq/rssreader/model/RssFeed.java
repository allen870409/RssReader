package com.arashiq.rssreader.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by A13054 on 2015/08/06.
 */
public class RssFeed{
    private String id;
    private String title;
    private String address;
    private String link;
    private String description;
    private List<RssItem> items;

    public RssFeed(String id, String title, String address, String link, String description, List<RssItem> items) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.address = address;
        this.description = description;
        this.items = items != null ? items : new ArrayList<RssItem>();
    }

    public RssFeed() {
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

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public List<RssItem> getItems() {
        return items;
    }
}