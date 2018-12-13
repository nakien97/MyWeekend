package com.nakien.myweekend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;;
import android.widget.TextView;
import com.nakien.myweekend.R;
import com.nakien.myweekend.model.CinemasCenter;
import java.util.ArrayList;

public class ListCinemasAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<CinemasCenter> itemList;

    public ListCinemasAdapter(Context context, int layout, ArrayList<CinemasCenter> itemList) {
        this.context = context;
        this.layout = layout;
        this.itemList = itemList;
    }

    private class ViewHolder{
        private TextView txtCinemasName;
        private TextView txtCinemasAddress;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.txtCinemasName = view.findViewById(R.id.textViewCinemasName);
            holder.txtCinemasAddress = view.findViewById(R.id.textViewCienamsAddress);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }
        CinemasCenter item = itemList.get(i);
        holder.txtCinemasName.setText(item.getTenCum());
        holder.txtCinemasAddress.setText(item.getDiachi());
        return view;
    }
}
