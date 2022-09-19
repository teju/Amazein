package com.amazein.ui.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazein.R;
import com.amazein.interfaces.ViewClickListener;
import com.amazein.model.cert.HolderTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanpyaeaung on 30/11/16.
 */

public class HomeHealthPacDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {


    private final Context context;
    private List<HolderTag> holderTags = new ArrayList<>();
    public ViewClickListener viewClickListener;

    public void setViewClickListener(ViewClickListener viewClickListener) {
        this.viewClickListener = viewClickListener;
    }

    public HomeHealthPacDetailsAdapter(Context context, List<HolderTag> holderTags) {
        this.context = context;
        this.holderTags = holderTags;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_health_pack_details, parent, false);
        return new HomeHealthPackDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {
            final HomeHealthPackDetailsViewHolder cardLimitViewHolder = (HomeHealthPackDetailsViewHolder) holder;
            cardLimitViewHolder.itemView.setTag(position);
            if(position %2 == 0) {
                cardLimitViewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.White));
            } else {
                cardLimitViewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public int getItemCount() {
        return 5;
    }

    private static class HomeHealthPackDetailsViewHolder extends RecyclerView.ViewHolder {

        TextView tv_txt;
        HomeHealthPackDetailsViewHolder(View itemView) {
            super(itemView);
            tv_txt = itemView.findViewById(R.id.tv_txt);
        }
    }


}

