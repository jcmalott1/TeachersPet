package com.example.teacherspet.model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * Custom Array Adapter
 *  
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class PetAdapter extends SimpleAdapter{
    private int[] colors = new int[] { 0x30FF0000, 0x300000FF };

    public PetAdapter(Context context, List<HashMap<String, String>> items, int resource, String[] from, int[] to) {
        super(context, items, resource, from, to);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        int colorPos = position % colors.length;
        view.setBackgroundColor(colors[colorPos]);
        return view;
    }
}
