package com.nakien.myweekend;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nakien.myweekend.adapter.ListFilmFragmentAdapter;

public class ListFilm extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager pagerListFilm;
    private TabLayout tabListFilm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_film);

        initWidget();
        actionBar();
    }

    private void initWidget() {
        toolbar = findViewById(R.id.toolBarListFilm);
        pagerListFilm = findViewById(R.id.viewPagerListFilm);
        pagerListFilm.setAdapter(new ListFilmFragmentAdapter(getSupportFragmentManager()));
        tabListFilm = findViewById(R.id.tabListFilm);
        tabListFilm.setupWithViewPager(pagerListFilm);
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
