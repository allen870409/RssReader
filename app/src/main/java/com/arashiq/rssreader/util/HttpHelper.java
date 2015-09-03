package com.arashiq.rssreader.util;

import com.arashiq.rssreader.model.RssItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Created by A13054 on 2015/09/02.
 */
public class HttpHelper {

    public static RssItem addImageAndDetail(RssItem item) {
        Document doc = null;
        String feedUrl = item.getLink();
        String imageUrl;
        String prefix;
        String suffixUrl;
        String realImageUrl = null;
        try {
            doc = Jsoup.connect(item.getLink()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element imageElement = doc.getElementById("news_image");
        Element textBodyElement = doc.getElementById("news_textbody");
        Element textMoreElement = doc.getElementById("news_textmore");

        prefix = feedUrl.substring(0, feedUrl.lastIndexOf("/"));

        if(imageElement != null){
            imageUrl = imageElement.attr("src");
            if(imageUrl.indexOf("http://") == 0){
                realImageUrl = imageUrl;
            }else{
                suffixUrl = imageUrl.substring(imageUrl.lastIndexOf("/") + 1, imageUrl.length());
                realImageUrl = prefix  + "/" + suffixUrl;
            }
        }

        String detail = textBodyElement.text() + "\n\n" + textMoreElement.text().replaceAll("<br>", "\n");
        item.setImageUrl(realImageUrl);
        item.setDetail(detail);
        return item;
    }
}
