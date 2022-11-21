package com.amazein.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.amazein.R;
import com.amazein.helper.autolooppager.RecycleAdapter;
import com.amazein.library.helper.ViewClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanpyaeaung on 30/11/16.
 */

public class HomeHeaderAdapter extends RecycleAdapter<HomeHeaderAdapter.ViewHolder>  {


    private final Context context;
    private List<Integer> arrayList = new ArrayList<>();
    public ViewClickListener viewClickListener;

    public void setViewClickListener(ViewClickListener viewClickListener) {
        this.viewClickListener = viewClickListener;
    }

    public HomeHeaderAdapter(Context context, List<Integer> arrayList,ViewClickListener viewClickListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.viewClickListener = viewClickListener;

    }



    @Override
    protected int getCount() {
        return arrayList.size();
    }

    @Override
    protected ViewHolder onCreateViewHolder(ViewGroup container) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_header, null, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, int position) {
        try {
            final ViewHolder cardLimitViewHolder = (ViewHolder) holder;
            cardLimitViewHolder.imageView.setTag(position);
            cardLimitViewHolder.imageView.setImageResource(arrayList.get(position));
            /*if(!TextUtils.isEmpty(arrayList.get(position))) {
                cardLimitViewHolder.imageView.setVisibility(View.VISIBLE);
                new BaseUIHelper().loadImage(context, arrayList.get(position), cardLimitViewHolder.imageView);
            } else {
                cardLimitViewHolder.imageView.setVisibility(View.GONE);
            }*/

            cardLimitViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewClickListener.onViewClick((int)v.getTag());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onRecycleViewHolder(ViewHolder holder) {

    }

    public static class ViewHolder extends RecycleAdapter.ViewHolder {
        private ImageView imageView;

        protected ViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
        }
    }


}

