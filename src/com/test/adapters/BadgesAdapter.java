package com.test.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.test.models.Badge;
import com.test.openbadges.R;

public class BadgesAdapter extends ArrayAdapter<Badge> {

	private int layout;
	private ArrayList<Badge> list;
	
	public BadgesAdapter(Context context, int layout, ArrayList<Badge> list) {
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
        	
        Badge badge = list.get(position);
        if (badge != null) {
        	
        	TextView txtName = (TextView) v.findViewById(R.id.lblBName);
        	TextView txtBadges = (TextView) v.findViewById(R.id.lblBDesc);
        	
        	if (txtName != null)
        		txtName.setText(badge.getName());
        	
        	if (txtBadges != null)
        		txtBadges.setText(String.valueOf(badge.getDescription()));
        }
        return v;
    }

}
