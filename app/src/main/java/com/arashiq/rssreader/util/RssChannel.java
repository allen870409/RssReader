package com.arashiq.rssreader.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by A13054 on 2015/09/01.
 */
public enum  RssChannel {
    PRIMARY_NEWS(0,"主要", "http://www3.nhk.or.jp/rss/news/cat0.xml"),
    SOCIAL_NEWS(1,"社会", "http://www3.nhk.or.jp/rss/news/cat1.xml"),
    CULTURE_NEWS(2,"文化・エンタメ", "http://www3.nhk.or.jp/rss/news/cat2.xml"),
    SCIENCE_NEWS(3,"科学・医療", "http://www3.nhk.or.jp/rss/news/cat3.xml"),
    POLITICS_NEWS(4,"政治", "http://www3.nhk.or.jp/rss/news/cat4.xml"),
    ECONOMY_NEWS(5,"経済", "http://www3.nhk.or.jp/rss/news/cat5.xml"),
    INTERNATIONAL_NEWS(6,"国際", "http://www3.nhk.or.jp/rss/news/cat6.xml"),
    SPORT_NEWS(7,"スポーツ", "http://www3.nhk.or.jp/rss/news/cat7.xml");

    private int id;
    private String title;
    private String url;

    RssChannel(int id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public static List<String> getAllTitles(){
        LinkedList<String> titles = new LinkedList<>();
        for(RssChannel rc : RssChannel.values()){
            titles.add(rc.title);
        }
        return titles;
    }

    public static List<RssChannel> getAll(){
        return Arrays.asList(RssChannel.values());
    }


}
