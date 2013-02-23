package com.test.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.test.models.Group;
import com.test.openbadges.R;

public class GroupsAdapter extends ArrayAdapter<Group> {

	private int layout;
	private ArrayList<Group> list;
	
	public GroupsAdapter(Context context, int layout, ArrayList<Group> list) {
		super(context, layout, list);
		
		this.layout = layout;
		this.list = list;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
    		
		View v = convertView;
        if (convertView == null) {
        	LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	v = vi.inflate(layout, null);
        }
        	
        Group group = list.get(position);
        if (group != null) {
        	
        	TextView txtName = (TextView) v.findViewById(R.id.lblGName);
        	TextView txtBadges = (TextView) v.findViewById(R.id.lblGBadges);
        	
        	if (txtName != null)
        		txtName.setText(group.getName());
        	
        	if (txtBadges != null)
        		txtBadges.setText(String.valueOf(group.getBadges()));
        }
        return v;
    }

}
