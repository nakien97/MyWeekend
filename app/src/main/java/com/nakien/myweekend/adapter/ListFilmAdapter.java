package com.nakien.myweekend.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nakien.myweekend.BuyTicketByFilm;
import com.nakien.myweekend.FilmDetail;
import com.nakien.myweekend.R;
import com.nakien.myweekend.model.Film;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;


public class ListFilmAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Film> itemList;
    private boolean nowShowing;

    public ListFilmAdapter(Context context, int layout, ArrayList<Film> itemList, boolean nowShowing) {
        this.context = context;
        this.layout = layout;
        this.itemList = itemList;
        this.nowShowing = nowShowing;
    }

    private class ViewHolder{
        private ImageView imgBanner;
        private TextView txtFilmName;
        private Button btnDatVe;
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
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder = new ViewHolder();
            //ánh xạ dữ liệu vào ViewHolder
            holder.imgBanner = view.findViewById(R.id.itemBanner);
            holder.txtFilmName = view.findViewById(R.id.textViewListItemName);
            holder.btnDatVe = view.findViewById(R.id.buttonListItemDatVe);
            if (nowShowing == false) {
                holder.btnDatVe.setVisibility(View.INVISIBLE);
            }
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }
        //gán giá trị cho ViewHolder
        if(itemList.get(i).getUrl_banner_ngang() != null){
            Picasso.get().load(itemList.get(i).getUrl_banner_ngang())
                    .error(R.drawable.errorimg)
                    .into(holder.imgBanner);
            holder.imgBanner.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        else{
            Picasso.get()
                    .load(itemList.get(i).getUrl_banner_doc())
                    .error(R.drawable.errorimg)
                    .into(holder.imgBanner);
            holder.imgBanner.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        holder.txtFilmName.setText(itemList.get(i).getTenphim());
        holder.imgBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FilmDetail.class);
                intent.putExtra("idFilm", itemList.get(i).getId_film());
                context.startActivity(intent);
            }
        });
        holder.btnDatVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BuyTicketByFilm.class);
                intent.putExtra("id_film", itemList.get(i).getId_film());
                intent.putExtra("name", itemList.get(i).getTenphim());
                context.startActivity(intent);
            }
        });
        return view;
    }
}
