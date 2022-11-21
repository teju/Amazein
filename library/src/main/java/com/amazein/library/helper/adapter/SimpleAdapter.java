package com.amazein.library.helper.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.amazein.library.helper.Scrollable;
import com.amazein.library.helper.SimpleData;
import com.iapps.library.R;

import java.util.ArrayList;

public class SimpleAdapter extends BaseAdapter implements Filterable, Scrollable {

	private ArrayList<SimpleData> simpledata;
	private ArrayList<SimpleData> originalData;
	private Activity context;
	private boolean leftAligned = false;
	private boolean isFilterable = false;

	public SimpleAdapter(Activity context, ArrayList<SimpleData> simpledata) {
		this.context = context;
		this.simpledata = simpledata;
		this.originalData = simpledata;
	}

	public void leftAligned(boolean leftAligned){
		this.leftAligned = leftAligned;
	}

	public void setFilterable(boolean filterable) {
		isFilterable = filterable;
	}

	@Override
	public int getCount() {
		try {
			return simpledata.size();
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public SimpleData getItem(int pos) {
			return simpledata.get(pos);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		final ViewHolder holder;

		if(convertView == null){
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.cell_simpleadapter, parent, false);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

			if(leftAligned){
				holder.tvTitle.setGravity(Gravity.LEFT);
			}

			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvTitle.setText(simpledata.get(position).getName());

		return convertView;
	}

	@Override
	public Filter getFilter() {
		return mFilter;
	}

	@Override
	public String getIndicatorForPosition(int childposition, int groupposition) {
		try {
			return Character.toString(simpledata.get(childposition).getName().charAt(0));
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public int getScrollPosition(int childposition, int groupposition) {
		return childposition;
	}

	public static class ViewHolder{
        TextView tvTitle;
	}

	private Filter mFilter = new Filter() {
		@Override
		public String convertResultToString(Object resultValue) {
				return ((SimpleData)resultValue).getName();
		}

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			String filterName = constraint.toString().toLowerCase().trim();
			FilterResults results = new FilterResults();
			if (isFilterable) {
				final ArrayList<SimpleData> simpledatalist = originalData;
				int count = simpledatalist.size();
				final ArrayList<SimpleData> newlist = new ArrayList<>(count);
				String filterableName;
				for(int i = 0; i < count; i++) {
					filterableName = simpledatalist.get(i).getName();
					if(filterableName.toLowerCase().trim().contains(filterName)) {
						newlist.add(simpledatalist.get(i));
					}
				}

				results.values = newlist;
				results.count = newlist.size();
			}
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			if (isFilterable) {
				simpledata = (ArrayList<SimpleData>) results.values;
			}
			notifyDataSetChanged();
		}
	};

}
