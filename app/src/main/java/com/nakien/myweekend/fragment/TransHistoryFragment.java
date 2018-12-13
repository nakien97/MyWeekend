package com.nakien.myweekend.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nakien.myweekend.R;
import com.nakien.myweekend.adapter.ListHistoryAdapter;
import com.nakien.myweekend.item.History;
import com.nakien.myweekend.model.Cinema;
import com.nakien.myweekend.model.CinemasCenter;
import com.nakien.myweekend.model.Film;
import com.nakien.myweekend.model.RecordBooking;
import com.nakien.myweekend.model.Schedule;
import com.nakien.myweekend.model.Ticket;

import java.util.ArrayList;

public class TransHistoryFragment extends Fragment {
    private View rootView;
    private ListView lvHistory;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private ArrayList<History> list = new ArrayList<>();
    private String imgBanner;
    private String dateTimeBook;
    private String dateTime;
    private String filmName;
    private String cinemasCenter;
    private String cinema;
    private int ticket;
    private long total;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_trans_history_layout, container, false);
        lvHistory = rootView.findViewById(R.id.lvHistory);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        DatabaseReference mRef = database.getReference("PHIEUDATVE");
        final DatabaseReference mRefSchedule = database.getReference("SUATCHIEU");
        final DatabaseReference mRefFilm = database.getReference("PHIM");
        final DatabaseReference mRefCinemasCenter = database.getReference("CUMRAP");
        final DatabaseReference mRefRecord = database.getReference("CT_PHIEUDATVE");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    final Ticket t = data.getValue(Ticket.class);
                    final History history = new History();
                    if(t.getEmail_kh().equals(mAuth.getCurrentUser().getEmail())){
                        dateTimeBook = t.getNgay_lap();
                        total = t.getTong_tien();
                        history.setDateTimeBook(dateTimeBook);
                        history.setTotal(total);
                        mRefSchedule.child(t.getId_suatchieu()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.getValue(Schedule.class) != null){
                                            Schedule sch = dataSnapshot.getValue(Schedule.class);
                                            sch.setKey(dataSnapshot.getKey());
                                            cinema = sch.getId_rap();
                                            dateTime = sch.getGio_bd()+"-"+sch.getNgay();
                                            history.setDateTime(dateTime);
                                            history.setCinema(cinema);
                                            mRefFilm.child(sch.getId_film())
                                                    .addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            if(dataSnapshot.getValue(Film.class) != null){
                                                                imgBanner = dataSnapshot.getValue(Film.class).getUrl_banner_doc();
                                                                filmName = dataSnapshot.getValue(Film.class).getTenphim();
                                                                history.setImgBanner(imgBanner);
                                                                history.setFilmName(filmName);
                                                            }
                                                        }
                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });
                                            mRefCinemasCenter.child(sch.getId_cum())
                                                    .addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            if(dataSnapshot.getValue(CinemasCenter.class) != null)
                                                                cinemasCenter = dataSnapshot.getValue(CinemasCenter.class).getTenCum();
                                                            history.setCinemasCenter(cinemasCenter);
                                                        }
                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                                        }
                                                    });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                        final int[] count = {0};
                        mRefRecord.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for(DataSnapshot dataRecord : dataSnapshot.getChildren()){
                                            RecordBooking record = dataRecord.getValue(RecordBooking.class);
                                            if(t.getId_phieudatve().equals(record.getId_phieudatve())){
                                                count[0]++;
                                            }
                                        }
                                        ticket = count[0];
                                        history.setTicket(ticket);
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                        list.add(history);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        lvHistory.setAdapter(new ListHistoryAdapter(getActivity(), R.layout.item_list_trans_history_layout, list));
        return rootView;
    }
}
