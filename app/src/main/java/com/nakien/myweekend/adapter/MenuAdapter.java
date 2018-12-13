package com.nakien.myweekend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nakien.myweekend.R;
import com.nakien.myweekend.item.MenuItem;
import java.util.List;

public class MenuAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<MenuItem> itemList;

    public MenuAdapter(Context context, int layout, List<MenuItem> itemList) {
        this.context = context;
        this.layout = layout;
        this.itemList = itemList;
    }

    private class ViewHolder{
        private TextView txtMenuItem;
        private ImageView imgMenuItem;
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
        ViewHolder holder;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder = new ViewHolder();
            //ánh xạ dữ liệu vào ViewHolder
            holder.imgMenuItem = view.findViewById(R.id.imgMenuItem);
            holder.txtMenuItem = view.findViewById(R.id.txtMenuItem);
            view.setTag(holder);
        }
        else{
            holder = (MenuAdapter.ViewHolder) view.getTag();
        }
        //gán giá trị cho ViewHolder
        MenuItem item = itemList.get(i);
        holder.txtMenuItem.setText(item.getTxtMenuItem());
        holder.imgMenuItem.setImageResource(item.getImgMenuItem());
        view.setTag(holder);
        return view;
    }

    public void refresh(List<MenuItem> list){
        this.itemList.clear();
        this.itemList = list;
        this.notifyDataSetChanged();
    }

}
