package com.nakien.myweekend.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nakien.myweekend.FilmDetail;
import com.nakien.myweekend.R;
import com.nakien.myweekend.model.Film;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class UltraPagerAdapter extends PagerAdapter {
    private Context context;
    private boolean isMultiScr;
    private ArrayList<Film> list;


    public UltraPagerAdapter(Context context, boolean isMultiScr, ArrayList<Film> list) {
        this.context = context;
        this.isMultiScr = isMultiScr;
        this.list = list;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.viewpager_layout, null);
        ImageView image = linearLayout.findViewById(R.id.imgFilm);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FilmDetail.class);
                intent.putExtra("idFilm", list.get(position).getId_film());
                context.startActivity(intent);
            }
        });
        if(!list.isEmpty()){
            Picasso.get().load(list.get(position).getUrl_banner_doc()).into(image);
        }
        container.addView(linearLayout);
        return linearLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LinearLayout view = (LinearLayout) object;
        container.removeView(view);
    }


}
