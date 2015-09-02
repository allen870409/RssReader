package com.arashiq.rssreader.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.arashiq.rssreader.model.RssItem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by A13054 on 2015/09/02.
 */
public class HttpHelper {

    public static RssItem addImageAndDetail(RssItem item) {
        Document doc = null;
        String feedUrl = item.getLink();
        String imageUrl = null;
        String prefix = null;
        String suffixUrl = null;
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

//        String detail = textBodyElement.text() + "\n" + textMoreElement.text().replaceAll("<br>", "\n");
        item.setImageUrl(realImageUrl);
//        item.setDetail(detail);
        return item;
    }

    private static Bitmap getURLImage(String url) {
        Bitmap bmp = null;
        if(url == null){
            return null;
        }
        try {
            URL myurl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.connect();
            InputStream is = conn.getInputStream();
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

}
