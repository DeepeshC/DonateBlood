/**
 * 
 */
package com.sc.donateblood.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sc.donateblood.R;

/**
 * @author Deepesh
 * 
 */
public class DBGrouopSpinnerAdapter extends ArrayAdapter<String[]> {
	/** Gym list. **/
	private String[] groupList;
	/** LayoutInflater . **/
	private LayoutInflater minflater;

	public DBGrouopSpinnerAdapter(Context context, int resource,
			String[] groupList) {
		super(context, resource);
		this.groupList = groupList;
	}

	public void setInflater(LayoutInflater inflater) {
		minflater = inflater;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent) {
		ViewDataHolder holder = null;
		String groupName = groupList[position];
		View row = convertView;
		if (row == null) {
			holder = new ViewDataHolder();
			row = minflater.inflate(R.layout.group_spinner_list_item, parent,
					false);
			holder.groupName = (TextView) row
					.findViewById(R.id.spinnerGroupName);
			row.setTag(holder);

		} else {
			holder = (ViewDataHolder) row.getTag();
		}
		holder.groupName.setText(groupName);
		holder.groupName.setTag(groupName);
		return row;
	}

	@Override
	public int getCount() {
		return groupList.length;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * View Holder class for gym.
	 */
	public static class ViewDataHolder {
		private TextView groupName;
	}
}
