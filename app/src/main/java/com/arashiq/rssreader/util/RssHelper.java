package com.arashiq.rssreader.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.arashiq.rssreader.model.RssFeed;
import com.arashiq.rssreader.model.RssItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RssHelper {

    public static final String TAG_CHANNEL = "channel";
    public static final String TAG_TITLE = "title";
    public static final String TAG_LINK = "link";
    public static final String TAG_DESCRIPTION = "description";
    public static final String TAG_ITEM = "item";

    public static RssFeed parseFeed(String address, Context context, ProgressDialog progressDialog) throws IOException, XmlPullParserException {
        URL url = new URL(address);
        Log.d("url", address);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int fileSize = conn.getContentLength();
        InputStream inputStream = conn.getInputStream();
        String tmpFileName = "tmp.xml";
        OutputStream fileOutputStream = context.openFileOutput(tmpFileName, Context.MODE_PRIVATE);
        byte[] buffer = new byte[4096];

        int length, downloadedSize = 0;
        while ((length = inputStream.read(buffer)) > 0) {
            fileOutputStream.write(buffer, 0, length);
            if (fileSize > 0) {
                downloadedSize += length;
                if (progressDialog != null) {
                    progressDialog.setProgress(90 * downloadedSize / fileSize);
                }
            }
        }
        fileOutputStream.close();
        inputStream.close();

        if (progressDialog != null) {
            progressDialog.setProgress(90);
        }

        inputStream = context.openFileInput(tmpFileName);
        RssFeed feed = RssHelper.parseFeed(address, inputStream);

        if (progressDialog != null) {
            progressDialog.setProgress(95);
        }

        return feed;
    }

    public static RssFeed parseFeed(String address, InputStream inputStream) throws XmlPullParserException, IOException {
        RssFeed feed = null;
        RssItem item = null;
        String feedTitle = null, feedLink = null, feedDescription = null;
        String itemTitle = null, itemLink = null, itemDescription = null, itemCategory = null, itemAuthor = null;
        List<RssItem> itemList = new ArrayList<RssItem>();

        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inputStream, "utf-8");
        int event = parser.getEventType();
        boolean itemLevel = false;

        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    if (TAG_TITLE.equalsIgnoreCase(parser.getName())) {
                        if (itemLevel) {
                            itemTitle = parser.nextText();
                        } else {
                            feedTitle = parser.nextText();
                        }
                    } else if (TAG_LINK.equalsIgnoreCase(parser.getName())) {
                        if (itemLevel) {
                            itemLink = parser.nextText();
                        } else {
                            feedLink = parser.nextText();
                        }
                    } else if (TAG_DESCRIPTION.equalsIgnoreCase(parser.getName())) {
                        if (itemLevel) {
                            itemDescription = parser.nextText();
                        } else {
                            feedDescription = parser.nextText();
                        }
                    } else if (TAG_ITEM.equalsIgnoreCase(parser.getName())) {
                        itemLevel = true;
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (TAG_CHANNEL.equalsIgnoreCase(parser.getName())) {
                        feed = new RssFeed(null, feedTitle, address, feedLink, feedDescription, itemList);
                        feedTitle = null;
                        feedLink = null;
                        feedDescription = null;
                        itemList = new ArrayList<RssItem>();
                    } else if (TAG_ITEM.equalsIgnoreCase(parser.getName())) {
                        itemLevel = false;
                        item = new RssItem(null, itemTitle, itemLink, itemDescription, itemCategory, itemAuthor, feed, false);
                        itemList.add(item);
                        itemTitle = null;
                        itemLink = null;
                        itemDescription = null;
                        itemCategory = null;
                        itemAuthor = null;
                    }
                    break;
            }
            event = parser.next();
        }

        return feed;
    }

    public static void writeFeed(RssFeed feed, OutputStream outputStream) {

    }
}