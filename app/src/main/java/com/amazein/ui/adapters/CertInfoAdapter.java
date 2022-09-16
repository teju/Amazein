package com.amazein.ui.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amazein.R;
import com.amazein.helper.BaseUIHelper;
import com.amazein.interfaces.ViewClickListener;
import com.amazein.model.cert.HolderTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanpyaeaung on 30/11/16.
 */

public class CertInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {


    private final Context context;
    private List<HolderTag> holderTags = new ArrayList<>();
    public ViewClickListener viewClickListener;

    public void setViewClickListener(ViewClickListener viewClickListener) {
        this.viewClickListener = viewClickListener;
    }

    public CertInfoAdapter(Context context, List<HolderTag> holderTags) {
        this.context = context;
        this.holderTags = holderTags;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cert_info, parent, false);
        return new CertInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {
            final CertInfoViewHolder cardLimitViewHolder = (CertInfoViewHolder) holder;
            cardLimitViewHolder.itemView.setTag(position);
            cardLimitViewHolder.tv_tag_name.setText(holderTags.get(position).getTagName());
            cardLimitViewHolder.tv_tag_value.setText(holderTags.get(position).getTagValue());
            if(!TextUtils.isEmpty(holderTags.get(position).getLogo())) {
                cardLimitViewHolder.img_logo.setVisibility(View.VISIBLE);
                new BaseUIHelper().loadImage(context, holderTags.get(position).getLogo(), cardLimitViewHolder.img_logo);
            } else {
                cardLimitViewHolder.img_logo.setVisibility(View.GONE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return holderTags.size();
    }

    private static class CertInfoViewHolder extends RecyclerView.ViewHolder {

        TextView tv_tag_name,tv_tag_value;
        ImageView img_logo;
        CertInfoViewHolder(View itemView) {
            super(itemView);
            tv_tag_name = itemView.findViewById(R.id.tv_tag_name);
            tv_tag_value = itemView.findViewById(R.id.tv_tag_value);
            img_logo = itemView.findViewById(R.id.img_logo);
        }
    }


}

