package com.arashiq.rssreader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arashiq.rssreader.model.RssItem;
import com.arashiq.rssreader.util.IntentCode;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.Bind;

public class RssItemDetailActivity extends BaseActivity {
    @Bind(R.id.tv_item_title)
    TextView tvItemTitle;
    @Bind(R.id.iv_item_image)
    ImageView ivItemImage;
    @Bind(R.id.tv_item_detail)
    TextView tvItemDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){
            RssItem item = (RssItem)extras.getSerializable(IntentCode.ITEM_DETAIL);
            tvItemTitle.setText(item.getTitle());
            if(item.getImageUrl() == null){
                ivItemImage.setVisibility(View.GONE);
            }else {
                Glide.with(this)
                        .load(item.getImageUrl())
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .into(ivItemImage);
            }
            tvItemDetail.setText(item.getDetail());
        }

    }
}
