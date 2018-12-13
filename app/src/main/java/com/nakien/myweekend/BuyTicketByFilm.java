package com.nakien.myweekend;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.nakien.myweekend.fragment.ScheduleByFilmFragment;

import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class BuyTicketByFilm extends AppCompatActivity {
    private Toolbar toolbar;
    private HorizontalCalendar calendarView;
    String title;
    String id_film;
    Calendar currentDate = Calendar.getInstance();
    private String selectedDate = (currentDate.get(Calendar.DATE)) + "/" + (currentDate.get(Calendar.MONTH) + 1) + "/" + currentDate.get(Calendar.YEAR);
    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket_by_film);
        Intent intent = getIntent();
        title = intent.getStringExtra("name");
        id_film = intent.getStringExtra("id_film");
        initWidget();
        actionBar(title);
        calendarView.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                selectedDate = (date.get(Calendar.DATE)) + "/" + (date.get(Calendar.MONTH) + 1) + "/" + date.get(Calendar.YEAR);
                if (fm.findFragmentById(R.id.id_container_film) != null) {
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.id_container_film, ScheduleByFilmFragment.newInstance(selectedDate, id_film));
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.commit();
                }
            }

            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView, int dx, int dy) {
                super.onCalendarScroll(calendarView, dx, dy);
                if (calendarView.getPositionOfCenterItem() == 3) {
                    selectedDate = (currentDate.get(Calendar.DATE)) + "/" + (currentDate.get(Calendar.MONTH) + 1) + "/" + currentDate.get(Calendar.YEAR);
                    if (fm.findFragmentById(R.id.id_container_film) != null) {
                        FragmentTransaction transaction = fm.beginTransaction();
                        transaction.replace(R.id.id_container_film, ScheduleByFilmFragment.newInstance(selectedDate, id_film));
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        transaction.commit();
                    }
                }
            }
        });
    }

    public void initWidget() {
        toolbar = findViewById(R.id.toolBarBuyTicketByFilm);
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.add(Calendar.MONTH, 1);
        calendarView = new HorizontalCalendar.Builder(this, R.id.calendarViewFilm)
                .range(start, end)
                .datesNumberOnScreen(7)
                .defaultSelectedDate(start)
                .build();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.id_container_film, ScheduleByFilmFragment.newInstance(selectedDate, id_film));
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    public void actionBar(String title) {
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
}