package com.nakien.myweekend.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.nakien.myweekend.BookTicket;
import com.nakien.myweekend.R;
import com.nakien.myweekend.model.Schedule;
import java.util.ArrayList;
import java.util.HashMap;

public class ExListSchduleAdapter extends BaseExpandableListAdapter {
    private Context context;
    private int layoutHeader;
    private int layoutChild;
    private ArrayList<String> listHeader;
    private HashMap<String, ArrayList<Schedule>> listChild;

    public ExListSchduleAdapter(Context context, int layoutHeader, int layoutChild, ArrayList<String> listHeader, HashMap<String, ArrayList<Schedule>> listChild) {
        this.context = context;
        this.layoutHeader = layoutHeader;
        this.layoutChild = layoutChild;
        this.listHeader = listHeader;
        this.listChild = listChild;
    }

    @Override
    public int getGroupCount() {
        return listHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listChild.get(listHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listChild.get(listHeader.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String)getGroup(i);
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layoutHeader, null);
        }
        TextView txtHeader = view.findViewById(R.id.textViewScheduleCinemasFilmName);
        txtHeader.setText(headerTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final Schedule child = (Schedule) getChild(i, i1);
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layoutChild, null);
        }
        Button btnTime = view.findViewById(R.id.buttonTimeSchedule);
        btnTime.setText(child.getGio_bd());
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BookTicket.class);
                intent.putExtra("key", child.getKey());
                context.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1){
        return true;
    }
}
