package com.amazein.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazein.R;
import com.amazein.model.NavigationDataModel;

import java.util.ArrayList;

public class DrawerItemCustomAdapter extends ArrayAdapter<NavigationDataModel> {

    Context mContext;
    int layoutResourceId;
    ArrayList<NavigationDataModel> data = new ArrayList<>();

    public DrawerItemCustomAdapter(Context mContext, int layoutResourceId, ArrayList<NavigationDataModel> data) {

        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);

        ImageView imageViewIcon = (ImageView) listItem.findViewById(R.id.imageViewIcon);
        TextView textViewName = (TextView) listItem.findViewById(R.id.textViewName);

        NavigationDataModel folder = data.get(position);


        imageViewIcon.setImageResource(folder.icon);
        textViewName.setText(folder.name);

        return listItem;
    }
}