package com.amazein.library.helper.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.amazein.library.helper.Scrollable;
import com.iapps.library.R;

import java.util.ArrayList;

public class GenericDDAdapterNoFilter extends BaseAdapter implements Filterable, Scrollable {

	private ArrayList<String> mItemOri = new ArrayList<String>();
	private ArrayList<String> mItem = new ArrayList<String>();
	private Activity context;

	private boolean filterable = false;
	public void setFilterable(boolean filterable) {
		this.filterable = filterable;
	}

	public GenericDDAdapterNoFilter(Activity context, ArrayList<String> list) {
		this.context = context;
		this.mItem = list;
		mItemOri = list;
	}

	@Override
	public int getCount() {
		try {
			return mItem.size();
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public String getItem(int pos) {
		return mItem.get(pos);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		final ViewHolder holder;
		final String obj = getItem(position);

		if(convertView == null){
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.cell_genericddadapter, parent, false);
			holder.tvLeftSide = (TextView) convertView.findViewById(R.id.tvLeftSide);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvLeftSide.setText(obj);
		return convertView;
	}

	@Override
	public String getIndicatorForPosition(int childposition, int groupposition) {
		try {
			return Character.toString(mItemOri.get(childposition).charAt(0));
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public int getScrollPosition(int childposition, int groupposition) {
		return childposition;
	}

	public static class ViewHolder{
		TextView tvLeftSide;
        TextView tvRightSide;
	}


	@Override
	public Filter getFilter() {
		return mFilter;
	}

	private Filter mFilter = new Filter() {
		@Override
		public String convertResultToString(Object resultValue) {
			return ((String)resultValue);
		}

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			String filterName = constraint.toString().toLowerCase().trim();
			if(filterable) {
				final ArrayList<String> originalItem = mItemOri;
				int count = originalItem.size();
				final ArrayList<String> nItems = new ArrayList<>(count);
				String filterableName;
				for (int i = 0; i < count; i++) {
					filterableName = originalItem.get(i);
					if(filterableName.toLowerCase().trim().contains(filterName)) {
						nItems.add(filterableName);
					}
				}
				results.values = nItems;
				results.count = nItems.size();
			}
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			if(filterable) {
				mItem = (ArrayList<String>) results.values;
			}
			notifyDataSetChanged();
		}
	};


}
