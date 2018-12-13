package com.nakien.myweekend;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nakien.myweekend.model.Cinema;
import com.nakien.myweekend.model.CinemasCenter;
import com.nakien.myweekend.model.Film;
import com.nakien.myweekend.model.RecordBooking;
import com.nakien.myweekend.model.Schedule;
import com.nakien.myweekend.model.Ticket;
import com.nakien.myweekend.util.Util;

import java.util.ArrayList;
public class BookTicket extends AppCompatActivity{
    private Toolbar toolbar;
    private TextView txtFilmName;
    private TextView txtTotal;
    private Button btnContinue;
    private TableLayout tableMap;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private String key;
    private ArrayList<String> listSelected = new ArrayList<>();
    private ArrayList<Button> listChair = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        initWidget();
        loadDatabase();
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Confirm.class);
                intent.putExtra("key", key);
                intent.putStringArrayListExtra("list", listSelected);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*Check login*/
        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(), LogIn.class));
        }
        /**/
    }

    private void initWidget() {
        toolbar = findViewById(R.id.toolBarBookTicket);
        txtFilmName = findViewById(R.id.txtF_Name);
        txtTotal = findViewById(R.id.txtTotal);
        btnContinue = findViewById(R.id.btnContinue);
        tableMap = findViewById(R.id.tableMap);

    }

    public void actionBar(String title, String subtilte){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle(subtilte);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void loadDataForChair(Character tu_hang, Character den_hang, int index){
        for(char i = tu_hang; i <= den_hang; i++) {
            TableRow row = new TableRow(this);
            tableMap.addView(row);
            for (int j = 1; j <= index; j++) {
                final Button chair = new Button(this);
                String str = String.valueOf(i) + j;
                chair.setText(str);
                chair.getBackground().setColorFilter(getResources().getColor(R.color.enableChair), PorterDuff.Mode.MULTIPLY);
                Log.d("loadDataForChair", chair.getWidth() + "" + chair.getHeight());
                chair.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String value = chair.getText().toString();
                        if(listSelected.indexOf(value) == -1){
                            listSelected.add(value);
                            chair.getBackground().setColorFilter(getResources().getColor(R.color.selectedChair), PorterDuff.Mode.MULTIPLY);
                        }
                        else{
                            listSelected.remove(value);
                            chair.getBackground().setColorFilter(getResources().getColor(R.color.enableChair), PorterDuff.Mode.MULTIPLY);
                        }
                        stateBtnContinue();
                    }
                });
                listChair.add(chair);
                row.addView(chair);
            }
        }
    }

    public void stateBtnContinue(){
        if(listSelected.isEmpty()){
            btnContinue.setEnabled(false);
            Log.d("stateBtnContinue", "disable");
        }
        else{
            btnContinue.setEnabled(true);
            Log.d("stateBtnContinue", "enable");
        }
    }

    public void loadDisableChair(ArrayList<String> listDisableChair){
        for(int i = 0; i < listChair.size(); i++){
            if(listDisableChair.indexOf(listChair.get(i).getText()) != -1) {
                listChair.get(i).getBackground().setColorFilter(getResources().getColor(R.color.disable), PorterDuff.Mode.MULTIPLY);
                listChair.get(i).setTextColor(Color.WHITE);
                listChair.get(i).setEnabled(false);
            }
        }
    }

    public void loadDatabase(){
        DatabaseReference mRefSchedule = database.getReference("SUATCHIEU");
        final DatabaseReference mRefCinemaCenter = database.getReference("CUMRAP");
        final DatabaseReference mRefFilm = database.getReference("PHIM");
        final DatabaseReference mRefCinema = database.getReference("RAP");
        mRefSchedule.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Schedule sch = dataSnapshot.getValue(Schedule.class);
                sch.setKey(dataSnapshot.getKey());
                mRefCinemaCenter.child(sch.getId_cum()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String title = dataSnapshot.getValue(CinemasCenter.class).getTenCum();
                        String subtitle = sch.getId_rap() + " -  " + sch.getNgay() + " - " + sch.getGio_bd();
                        actionBar(title, subtitle);
                        txtTotal.setText("Giá vé: " + Util.formatMoney(dataSnapshot.getValue(CinemasCenter.class).getGia_ve()) + "đ/vé");
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                mRefFilm.child(sch.getId_film()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        txtFilmName.setText(dataSnapshot.getValue(Film.class).getTenphim());
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                String str = sch.getId_cum() +"-"+sch.getId_rap();
                mRefCinema.child(str).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Cinema cinema = new Cinema(dataSnapshot.getValue(Cinema.class));
                        Character hangDau = cinema.getHang_dau().charAt(0);
                        Character hangCuoi = cinema.getHang_cuoi().charAt(0);
                        int index = cinema.getSo_ghe_moi_hang();
                        loadDataForChair(hangDau, hangCuoi, index);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        final ArrayList<String> listDisableChair = new ArrayList<>();
        DatabaseReference mRef = database.getReference("PHIEUDATVE");
        final DatabaseReference mRefChild = database.getReference("CT_PHIEUDATVE");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(final DataSnapshot data1 : dataSnapshot.getChildren()){
                    if(data1.getValue(Ticket.class).getId_suatchieu()!=null){
                        final Ticket ticket = new Ticket(data1.getValue(Ticket.class));
                        if(ticket.getId_suatchieu().equals(key)){
                            mRefChild.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    listDisableChair.clear();
                                    for(DataSnapshot data : dataSnapshot.getChildren()) {
                                        RecordBooking record = new RecordBooking(data.getValue(RecordBooking.class));
                                        if(record.getId_phieudatve().equals(ticket.getId_phieudatve())){
                                            listDisableChair.add(record.getId_ghe());
                                        }
                                    }
                                    if(!listDisableChair.isEmpty())
                                        loadDisableChair(listDisableChair);
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("BookTicket", "onDestroy");
    }
}
