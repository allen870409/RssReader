package com.arashiq.rssreader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arashiq.rssreader.R;
import com.arashiq.rssreader.model.RssFeed;
import com.arashiq.rssreader.model.RssItem;
import com.arashiq.rssreader.util.RssHelper;
import com.bumptech.glide.Glide;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FeedListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<RssItem> itemList;


    public FeedListAdapter(Context context, List<RssItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.feed_item, parent, false);
        final RssItemViewHolder rssItemViewHolder = new RssItemViewHolder(view);
        return rssItemViewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final RssItemViewHolder holder = (RssItemViewHolder) viewHolder;
        RssItem rssItem = itemList.get(position);
        holder.tvTitle.setText(rssItem.getTitle());
        holder.tvDesc.setText(rssItem.getDescription());
        Glide.with(context)
                .load(rssItem.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.avatar)
                .crossFade()
                .into(holder.ivItemImage);
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }



    public class RssItemViewHolder extends RecyclerView.ViewHolder{
        @InjectView(R.id.tv_title)
        TextView tvTitle;
        @InjectView(R.id.tv_desc)
        TextView tvDesc;
        @InjectView(R.id.iv_item_image)
        ImageView ivItemImage;
        public RssItemViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

}
