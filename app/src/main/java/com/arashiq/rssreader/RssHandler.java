package com.arashiq.rssreader;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import android.util.Log;

import com.arashiq.rssreader.model.RssFeed;
import com.arashiq.rssreader.model.RssItem;

/**
 * Created by A13054 on 2015/09/01.
 */
public class RssHandler extends DefaultHandler {

    RssFeed rssFeed;
    RssItem rssItem;

    String lastElementName = "";

    final int Rss_TITLE = 1;
    final int Rss_LINK = 2;
    final int Rss_AUTHOR = 3;
    final int Rss_CATEGORY = 4;
    final int Rss_PUBDATE = 5;
    final int Rss_COMMENTS = 6;
    final int Rss_DESCRIPTION = 7;

    int currentFlag = 0;

    public RssHandler() {

    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        rssFeed = new RssFeed();
        rssItem = new RssItem();

    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);
        String text = new String(ch, start, length);
        Log.i("i", "detail：" + text);

        switch (currentFlag) {
            case Rss_TITLE:
                rssItem.setTitle(text);
                currentFlag = 0;
                break;
            case Rss_PUBDATE:
                rssItem.setPubdate(text);
                currentFlag = 0;
                break;
            case Rss_CATEGORY:
                rssItem.setCategory(text);
                currentFlag = 0;
                break;
            case Rss_LINK:
                rssItem.setLink(text);
                currentFlag = 0;// 设置完后，重置为开始状态
                break;
            case Rss_AUTHOR:
                rssItem.setAuthor(text);
                currentFlag = 0;// 设置完后，重置为开始状态
                break;
            case Rss_DESCRIPTION:
                rssItem.setDescription(text);
                currentFlag = 0;// 设置完后，重置为开始状态
                break;
            case Rss_COMMENTS:
                rssItem.setComments(text);
                currentFlag = 0;// 设置完后，重置为开始状态
                break;
            default:
                break;
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if ("chanel".equals(localName)) {
            currentFlag = 0;
            return;
        }
        if ("item".equals(localName)) {
            rssItem = new RssItem();
            return;
        }
        if ("title".equals(localName)) {
            currentFlag = Rss_TITLE;
            return;
        }
        if ("description".equals(localName)) {
            currentFlag = Rss_DESCRIPTION;
            return;
        }
        if ("link".equals(localName)) {
            currentFlag = Rss_LINK;
            return;
        }
        if ("pubDate".equals(localName)) {
            currentFlag = Rss_PUBDATE;
            return;
        }
        if ("category".equals(localName)) {
            currentFlag = Rss_CATEGORY;
            return;
        }

        if ("author".equals(localName)) {
            currentFlag = Rss_AUTHOR;
            return;
        }

        if ("comments".equals(localName)) {
            currentFlag = Rss_COMMENTS;
            return;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        super.endElement(uri, localName, qName);
        if ("item".equals(localName)) {

            rssFeed.addItem(rssItem);
            return;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    public RssFeed getRssFeed() {
        return rssFeed;
    }

}
