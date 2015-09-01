package com.arashiq.rssreader.dao;

import android.content.Context;

import com.arashiq.rssreader.model.RssFeed;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RssFeedDao {

    private Realm mRealm;

    public RssFeedDao(Context context) {
        mRealm = Realm.getInstance(context);
    }

    public RssFeed find(int id) {
        return mRealm.where(RssFeed.class).equalTo("id", id).findAll().first();
    }

    public boolean isFeedExist(String address) {
        RssFeed feed = mRealm.where(RssFeed.class).equalTo("feed_address", address.toLowerCase()).findFirst();
        return feed != null;
    }

    public List<RssFeed> getAllFeedList() {
        RealmResults<RssFeed> RssFeeds = mRealm.where(RssFeed.class).findAll();
        return RssFeeds;
    }

    public List<RssFeed> getItemListByFeedId(String feedId) {
        RealmResults<RssFeed> RssFeeds = mRealm.where(RssFeed.class)
                .equalTo("feed.id", feedId)
                .findAll();
        return RssFeeds;
    }
}
