package com.nakien.myweekend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nakien.myweekend.R;
import com.nakien.myweekend.item.History;
import com.nakien.myweekend.util.Util;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class ListHistoryAdapter extends BaseAdapter{
    private Context context;
    private int layout;
    private ArrayList<History> itemList;

    public ListHistoryAdapter(Context context, int layout, ArrayList<History> itemList) {
        this.context = context;
        this.layout = layout;
        this.itemList = itemList;
    }

    private class ViewHolder{
        private ImageView img;
        private TextView txtHistoryDTBook;
        private TextView txtHistoryFilmName;
        private TextView txtHistoryDateTime;
        private TextView txtHistoryCCenter;
        private TextView txtHistoryCinema;
        private TextView txtHistoryTicket;
        private TextView txtHistoryTotal;
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
        ListHistoryAdapter.ViewHolder holder;
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            holder = new ListHistoryAdapter.ViewHolder();
            //ánh xạ dữ liệu vào ViewHolder
            holder.img = view.findViewById(R.id.imgHistory);
            holder.txtHistoryFilmName = view.findViewById(R.id.txtHistoryFilmName);
            holder.txtHistoryDTBook = view.findViewById(R.id.txtHistoryDTBook);
            holder.txtHistoryDateTime = view.findViewById(R.id.txtHistoryDateTime);
            holder.txtHistoryCCenter = view.findViewById(R.id.txtHistoryCCenter);
            holder.txtHistoryCinema = view.findViewById(R.id.txtHistoryCinema);
            holder.txtHistoryTicket = view.findViewById(R.id.txtHistoryTicket);
            holder.txtHistoryTotal = view.findViewById(R.id.txtHistoryTotal);
            view.setTag(holder);
        }
        else{
            holder = (ListHistoryAdapter.ViewHolder) view.getTag();
        }
        //gán giá trị cho ViewHolder
        Picasso.get().load(itemList.get(i).getImgBanner()).into(holder.img);
        holder.txtHistoryFilmName.setText(itemList.get(i).getFilmName());
        holder.txtHistoryDTBook.setText(itemList.get(i).getDateTimeBook());
        holder.txtHistoryDateTime.setText("Suất: " +itemList.get(i).getDateTime());
        holder.txtHistoryCCenter.setText(itemList.get(i).getCinemasCenter());
        holder.txtHistoryCinema.setText("Rạp " + itemList.get(i).getCinema());
        holder.txtHistoryTicket.setText(itemList.get(i).getTicket()+" vé");
        holder.txtHistoryTotal.setText(Util.formatMoney(itemList.get(i).getTotal())+"đ");
        return view;
    }
}
