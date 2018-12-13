package com.nakien.myweekend;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nakien.myweekend.adapter.MenuAdapter;
import com.nakien.myweekend.adapter.UltraPagerAdapter;
import com.nakien.myweekend.model.Film;
import com.nakien.myweekend.item.MenuItem;
import com.nakien.myweekend.util.Util;
import com.squareup.picasso.Picasso;
import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraDepthScaleTransformer;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipperADS;
    ListView lvMenu;
    DrawerLayout drawerLayoutHomepage;
    UltraViewPager viewPager;
    TextView txtFilmName, txtDate_Time;
    Button btnMainDatVe, btnAge;
    MenuAdapter adapter;
    FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        initWidget();
        actionBar();
        setViewFlipperADS();
        setItemForMenu();
        clickItemOfMenu();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            updateItem();
        }
        final ArrayList<Film> listFilm = new ArrayList<>();
        DatabaseReference myref = database.getReference("PHIM");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listFilm.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Film f = new Film(data.getValue(Film.class));
                    if(f.getId_trangthaiphim().equals("t2")){
                        listFilm.add(f);
                    }
                }
                PagerAdapter adapter = new UltraPagerAdapter(MainActivity.this, true, listFilm);
                viewPager.setAdapter(adapter);
                setUIViewPager(listFilm);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void updateItem() {
        if(adapter != null){
            ArrayList<MenuItem> list = new ArrayList<>();
            list.add(new MenuItem(R.drawable.user, "Quản lý tài khoản"));
            list.add(new MenuItem(R.drawable.film, "Đặt vé theo phim"));
            list.add(new MenuItem(R.drawable.cinema, "Đặt vé theo cụm rạp"));
            list.add(new MenuItem(R.drawable.signout, "Đăng xuất"));
            adapter.refresh(list);
        }

    }

    public void initWidget(){
        toolbar = findViewById(R.id.toolBarHomePage);
        viewFlipperADS = findViewById(R.id.viewFlipperADS);
        lvMenu = findViewById(R.id.listViewMenu);
        drawerLayoutHomepage = findViewById(R.id.drawerLayoutHomepage);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        viewPager.setClickable(true);
        txtFilmName = findViewById(R.id.textViewFilmName);
        txtDate_Time = findViewById(R.id.textViewDate_Time);
        btnAge = findViewById(R.id.buttonAge);
        btnMainDatVe = findViewById(R.id.buttonMainDatVe);
    }

    public void actionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayoutHomepage.openDrawer(GravityCompat.START);
            }
        });
    }

    public void setViewFlipperADS(){
        final List<String> arr_ads = new ArrayList<>();
        arr_ads.add("https://www.cgv.vn/media/wysiwyg/Newsoffer2/OCT18/4DX_IMMORTAL_popup_1920-X-1000.jpg");
        arr_ads.add("http://www.funny.kim/wp-content/uploads/2016/10/Halloween-Banner-5.jpg");
        arr_ads.add("https://cdn-images-1.medium.com/max/1200/1*GV1PskBFLgQFx8oDAUE6TQ.jpeg");
        arr_ads.add("http://www.dcthriftymom.com/wp-content/uploads/2018/10/Venom-Banner.png");

        for(int i=0; i<arr_ads.size(); i++){
            ImageView image = new ImageView(getApplicationContext());
            Picasso.get().load(arr_ads.get(i)).into(image);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipperADS.addView(image);
        }
        viewFlipperADS.setFlipInterval(3000);
        viewFlipperADS.setAutoStart(true);
        viewFlipperADS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), ListFilm.class));
            }
        });
    }

    public void setItemForMenu(){
        ArrayList<MenuItem> arrMenuItem = new ArrayList<>();
        arrMenuItem.add(new MenuItem(R.drawable.user, "Đăng nhập"));
        arrMenuItem.add(new MenuItem(R.drawable.film, "Đặt vé theo phim"));
        arrMenuItem.add(new MenuItem(R.drawable.cinema, "Đặt vé theo cụm rạp"));
        adapter = new MenuAdapter(MainActivity.this, R.layout.menu_layout, arrMenuItem);
        lvMenu.setAdapter(adapter);
        Log.d("MainActivity", "setItemForMenu");
    }

    public void logOut(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Bạn có muốn đăng xuất không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mAuth.signOut();
                dialogInterface.dismiss();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void clickItemOfMenu(){
        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        startActivity(new Intent(getApplicationContext(), LogIn.class));
                        break;
                    case 1:
                        finish();
                        startActivity(new Intent(getApplicationContext(), ListFilm.class));
                        break;
                    case 2:
                        finish();
                        startActivity(new Intent(getApplicationContext(), ListCinemas.class));
                        break;
                    case 3:
                        logOut();
                        break;
                }

            }
        });
    }

    public void setUIViewPager(final ArrayList<Film> listFilm){
        viewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        //Multi Screen
        viewPager.setMultiScreen(0.7f);
        viewPager.setItemRatio(0.5f);
        viewPager.setRatio(1.0f);
        viewPager.setMaxHeight(3000);

        //initialize built-in indicator
        viewPager.initIndicator();
        //set the alignment
        viewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        //construct built-in indicator, and add it to  UltraViewPager
        viewPager.getIndicator().build();
        //set an infinite loop
        viewPager.setInfiniteLoop(true);
        viewPager.setPageTransformer(false, new UltraDepthScaleTransformer());
        //set item mac dinh ban dau
        viewPager.setCurrentItem(0);
        setFilmInfo(listFilm, 0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageSelected(int position) {
                final int index = viewPager.getCurrentItem();
                setFilmInfo(listFilm, index);
                //Toast.makeText(MainActivity.this, id+"", Toast.LENGTH_LONG).show();
                btnMainDatVe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), BuyTicketByFilm.class);
                        intent.putExtra("id_film", listFilm.get(index).getId_film());
                        intent.putExtra("name", listFilm.get(index).getTenphim());
                        startActivity(intent);
                    }
                });
            }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }


    public void setFilmInfo(ArrayList<Film> listFilm, int index){
        String filmName = listFilm.get(index).getTenphim();
        String date = listFilm.get(index).getKhoichieu();
        String time = Util.minToHour(listFilm.get(index).getThoiluong());
        String age = listFilm.get(index).getId_dotuoi();
        txtFilmName.setText(filmName);
        txtDate_Time.setText(time + " " + date);
        btnAge.setText(age);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("APP", "onDestroy");
    }
}
