/**
 * 
 */
package com.sc.donateblood.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sc.donateblood.R;
import com.sc.donateblood.info.DBUserDetails;
import com.sc.donateblood.view.DBSearchScreen;

/**
 * @author Deepesh
 * 
 */
public class DBSearchListAdapter extends BaseAdapter {
	/** ArrayList<DBUserDetails>. **/
	private ArrayList<DBUserDetails> dbUserDetails;
	/** LayoutInflater . **/
	private LayoutInflater minflater;
	/** LocationActivity instance. **/
	public DBSearchScreen act;
	/** View. **/
	private View row;

	/**
	 * Constructor.
	 * 
	 * @param context
	 *            Context
	 * @param resource
	 * 
	 *            resource
	 */
	public DBSearchListAdapter(Context context,
			ArrayList<DBUserDetails> dbUserDetails, DBSearchScreen act) {
		this.dbUserDetails = dbUserDetails;
		this.act = act;
	}

	public void setInflater(LayoutInflater inflater) {
		minflater = inflater;
	}

	@Override
	public int getCount() {
		return dbUserDetails.size();
	}

	@Override
	public Object getItem(int position) {
		return dbUserDetails.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		return getCustomView(position, view, viewGroup);
	}

	public View getCustomView(final int position, View convertView,
			ViewGroup parent) {
		ViewDataHolder holder = null;
		DBUserDetails userDetails = (DBUserDetails) getItem(position);

		row = convertView;
		if (row == null) {
			holder = new ViewDataHolder();
			row = minflater.inflate(R.layout.search_list_item, null);

			holder.name = (TextView) row.findViewById(R.id.text_search_name);
			holder.distance = (TextView) row
					.findViewById(R.id.text_search_distance);
			holder.name.setText(userDetails.name);
			holder.distance.setText(userDetails.distance + " km");
			row.setTag(holder);

		} else {
			holder = (ViewDataHolder) row.getTag();
		}
		row.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				act.startUserDetailsScreen(dbUserDetails.get(position));
			}
		});
		return row;
	}

	/**
	 * View Holder class for gym.
	 */
	public static class ViewDataHolder {
		private TextView name;
		private TextView distance;
	}

}
