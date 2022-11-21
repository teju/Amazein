package com.amazein.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amazein.R;
import com.amazein.library.helper.ViewClickListener;
import com.amazein.model.cert.HolderTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanpyaeaung on 30/11/16.
 */

public class TestPacDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {


    private final Context context;
    private List<HolderTag> holderTags = new ArrayList<>();
    public ViewClickListener viewClickListener;

    public void setViewClickListener(ViewClickListener viewClickListener) {
        this.viewClickListener = viewClickListener;
    }

    public TestPacDetailsAdapter(Context context, List<HolderTag> holderTags) {
        this.context = context;
        this.holderTags = holderTags;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tests_pack_details, parent, false);
        return new HomeHealthPackDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {
            final HomeHealthPackDetailsViewHolder cardLimitViewHolder = (HomeHealthPackDetailsViewHolder) holder;
            cardLimitViewHolder.itemView.setTag(position);

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public int getItemCount() {
        return 4;
    }

    private static class HomeHealthPackDetailsViewHolder extends RecyclerView.ViewHolder {

        TextView tv_txt;
        HomeHealthPackDetailsViewHolder(View itemView) {
            super(itemView);
            tv_txt = itemView.findViewById(R.id.tv_txt);
        }
    }


}

