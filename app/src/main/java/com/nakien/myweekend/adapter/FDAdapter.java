package com.nakien.myweekend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.nakien.myweekend.R;
import com.nakien.myweekend.item.FD;

import java.util.List;

public class FDAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<FD> itemList;

    public FDAdapter(Context context, int layout, List<FD> itemList) {
        this.context = context;
        this.layout = layout;
        this.itemList = itemList;
    }

    private class ViewHolder{
        private TextView txtKey;
        private TextView txtValue;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            FDAdapter.ViewHolder holder = new FDAdapter.ViewHolder();
            //ánh xạ dữ liệu vào ViewHolder
            holder.txtKey= view.findViewById(R.id.textViewKey);
            holder.txtValue = view.findViewById(R.id.textViewValue);
            //gán giá trị cho ViewHolder
            FD item = itemList.get(i);
            holder.txtKey.setText(item.getKey());
            holder.txtValue.setText(item.getValue());
            view.setTag(holder);
        }
        else{
            view.getTag();
        }
        return view;
    }
}
