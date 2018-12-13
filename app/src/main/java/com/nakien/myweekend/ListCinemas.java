package com.nakien.myweekend;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nakien.myweekend.adapter.ListCinemasAdapter;
import com.nakien.myweekend.model.CinemasCenter;

import java.util.ArrayList;

public class ListCinemas extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView lvCinemas;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ArrayList<CinemasCenter> listCinemas;
    private ListCinemasAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cinemas);
        initWidget();
        actionBar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference mRef = database.getReference("CUMRAP");
        listCinemas = new ArrayList<>();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCinemas.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    CinemasCenter cinemas = new CinemasCenter(data.getValue(CinemasCenter.class));
                    listCinemas.add(cinemas);
                }
                adapter = new ListCinemasAdapter(getApplicationContext(), R.layout.item_list_cinemas_layout, listCinemas);
                lvCinemas.setAdapter(adapter);
                lvCinemas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getApplicationContext(), BuyTicketByCinemas.class);
                        intent.putExtra("name", listCinemas.get(i).getTenCum());
                        intent.putExtra("id_cum", listCinemas.get(i).getIdCumRap());
                        finish();
                        startActivity(intent);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void initWidget(){
        toolbar = findViewById(R.id.toolBarListCinemas);
        lvCinemas = findViewById(R.id.listViewCinemas);
    }

    public void actionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
