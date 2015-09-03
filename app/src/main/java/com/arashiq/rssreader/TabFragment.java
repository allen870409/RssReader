package com.arashiq.rssreader;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arashiq.rssreader.adapter.FeedListAdapter;
import com.arashiq.rssreader.model.RssFeed;
import com.arashiq.rssreader.model.RssItem;
import com.arashiq.rssreader.util.RssHelper;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.InjectView;

public class TabFragment extends Fragment {

    private String channelUrl;
    private Context context;
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private RssFeed feed;
    private List<RssItem> rssItemList;
    private FeedListAdapter feedListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        context = getActivity();
        channelUrl = bundle.getString("channelUrl");
        this.createProgressDialog();
        ParseFeedTask parseFeedTask = new ParseFeedTask();
        try {
            rssItemList = parseFeedTask.execute(channelUrl).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = (RecyclerView)inflater.inflate(R.layout.fragment_feed_list, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        feedListAdapter = new FeedListAdapter(context, rssItemList);
        recyclerView.setAdapter(feedListAdapter);
        return recyclerView;
    }

    private void createProgressDialog() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("wait");
        progressDialog.setMessage("getting...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
    }

    private class ParseFeedTask extends AsyncTask<String, Integer, List<RssItem>> {
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected List<RssItem> doInBackground(String... params) {
            String feedAddress = params[0];
            try {
                feed = RssHelper.parseFeed(feedAddress, context, progressDialog);
                progressDialog.setProgress(100);
            } catch (IOException e) {
                Log.e("parse", e.getMessage());
            } catch (XmlPullParserException e) {
                Log.e("parse", e.getMessage());
            }
            return feed.getItems();
        }

        @Override
        protected void onPostExecute(List<RssItem> itemList) {
            progressDialog.dismiss();
        }
    }
}
