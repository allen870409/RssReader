package com.arashiq.rssreader.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arashiq.rssreader.R;
import com.arashiq.rssreader.RssItemDetailActivity;
import com.arashiq.rssreader.model.RssItem;
import com.arashiq.rssreader.util.HttpHelper;
import com.arashiq.rssreader.util.IntentCode;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;

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

        AddItemDetailTask addItemDetailTask = new AddItemDetailTask();
        try {
            rssItem = addItemDetailTask.execute(rssItem).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(rssItem.getImageUrl() == null){
            holder.ivItemImage.setVisibility(View.GONE);
        }else {
            Glide.with(context)
                    .load(rssItem.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.ivItemImage);
        }
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }



    public class RssItemViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_desc)
        TextView tvDesc;
        @Bind(R.id.iv_item_image)
        ImageView ivItemImage;

        @OnClick(R.id.cv_rss_item)
        public void onItemClick() {
            editAt(getAdapterPosition());
        }


        public RssItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private class AddItemDetailTask extends AsyncTask<RssItem, Integer, RssItem> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected RssItem doInBackground(RssItem... params) {
            RssItem item = params[0];
            item = HttpHelper.addImageAndDetail(item);
            return item;
        }

        @Override
        protected void onPostExecute(RssItem item) {
        }
    }

    public void editAt(int position) {
        RssItem item = itemList.get(position);
        Intent intent = new Intent(context, RssItemDetailActivity.class);
        intent.putExtra(IntentCode.ITEM_DETAIL, item);
        context.startActivity(intent);
    }

}
