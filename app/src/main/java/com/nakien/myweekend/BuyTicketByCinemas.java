package com.nakien.myweekend;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.nakien.myweekend.fragment.ScheduleByCinemasFragment;
import java.util.Calendar;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class BuyTicketByCinemas extends AppCompatActivity {
    private Toolbar toolbar;
    private HorizontalCalendar calendarView;
    String title;
    String id_cum;
    Calendar currentDate = Calendar.getInstance();
    private String selectedDate = (currentDate.get(Calendar.DATE)) + "/" + (currentDate.get(Calendar.MONTH) + 1) + "/" + currentDate.get(Calendar.YEAR);
    FragmentManager fm = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket_by_cinemas);
        Intent intent = getIntent();
        title = intent.getStringExtra("name");
        id_cum = intent.getStringExtra("id_cum");
        initWidget();
        actionBar(title);
        calendarView.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                selectedDate = (date.get(Calendar.DATE)) + "/" + (date.get(Calendar.MONTH) + 1) + "/" + date.get(Calendar.YEAR);
                if(fm.findFragmentById(R.id.id_container) != null) {
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.id_container, ScheduleByCinemasFragment.newInstance(selectedDate, id_cum));
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.commit();
                }
            }

            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView, int dx, int dy) {
                super.onCalendarScroll(calendarView, dx, dy);
                if(calendarView.getPositionOfCenterItem() == 3){
                    selectedDate = (currentDate.get(Calendar.DATE)) + "/" + (currentDate.get(Calendar.MONTH) + 1) + "/" + currentDate.get(Calendar.YEAR);
                    if(fm.findFragmentById(R.id.id_container) != null) {
                        FragmentTransaction transaction = fm.beginTransaction();
                        transaction.replace(R.id.id_container, ScheduleByCinemasFragment.newInstance(selectedDate, id_cum));
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        transaction.commit();
                    }
                }
            }
        });
    }
    public void initWidget(){
        toolbar = findViewById(R.id.toolBarBuyTicketByCinemas);
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.add(Calendar.MONTH, 1);
        calendarView = new HorizontalCalendar.Builder(this, R.id.calendarViewCinemas)
                .range(start, end)
                .datesNumberOnScreen(7)
                .defaultSelectedDate(start)
                .build();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.id_container, ScheduleByCinemasFragment.newInstance(selectedDate, id_cum));
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }
    public void actionBar(String title){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), ListCinemas.class));
            }
        });
    }
}
