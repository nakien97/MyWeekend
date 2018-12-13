package com.nakien.myweekend;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nakien.myweekend.adapter.FDAdapter;
import com.nakien.myweekend.item.FD;
import com.nakien.myweekend.model.Film;
import com.nakien.myweekend.util.Util;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class FilmDetail extends AppCompatActivity {
    ImageView imgBanner;
    Button btnDatVe;
    TextView txtMoTa;
    ListView lvFilmDetail;
    Toolbar toolbar;
    private FirebaseDatabase database;
    private Film film;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);
        initWidget();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        final String idFilm = new String(intent.getStringExtra("idFilm"));
        DatabaseReference myref = database.getReference("PHIM");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    if(data.getKey().equals(idFilm)){
                        film = new Film(data.getValue(Film.class));
                        if(film.getId_trangthaiphim().equals("t1"))
                            btnDatVe.setVisibility(View.INVISIBLE);
                        break;
                    }
                }
                if(film != null){
                    actionBar(film.getTenphim());
                    updateUI(film);
                    setItemForListView(film);
                    btnDatVe.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), BuyTicketByFilm.class);
                            intent.putExtra("id_film", film.getId_film());
                            intent.putExtra("name", film.getTenphim());
                            startActivity(intent);
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void updateUI(Film film) {
        Picasso.get().load(film.getUrl_banner_doc()).into(imgBanner);
        imgBanner.setScaleType(ImageView.ScaleType.FIT_XY);
        txtMoTa.setText(film.getMieuta());


    }

    public void initWidget(){
        toolbar = findViewById(R.id.toolBarFilmDetail);
        imgBanner = findViewById(R.id.imageFD_Banner);
        txtMoTa = findViewById(R.id.textViewFD_MoTa);
        btnDatVe = findViewById(R.id.buttonFD_DatVe);
        lvFilmDetail = findViewById(R.id.listViewFD);
        database = FirebaseDatabase.getInstance();
    }

    public void actionBar(String title){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void setItemForListView(Film film){
        ArrayList<FD> list = new ArrayList<>();
        String min = Util.minToHour(film.getThoiluong());
        list.add(new FD("Độ tuổi", film.getId_dotuoi()));
        list.add(new FD("Khởi chiếu", film.getKhoichieu()));
        list.add(new FD("Thể loại", film.getTheloai()));
        list.add(new FD("Đạo diễn", film.getDaodien()));
        list.add(new FD("Diễn viên", film.getDienvien()));
        list.add(new FD("Thời lượng", min));

        FDAdapter adapter = new FDAdapter(getApplicationContext(), R.layout.fd_layout, list);
        lvFilmDetail.setAdapter(adapter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("FilmDetail", "onDestroy");
    }
}
