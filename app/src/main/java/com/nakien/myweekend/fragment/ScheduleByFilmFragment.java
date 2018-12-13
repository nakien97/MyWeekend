package com.nakien.myweekend.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nakien.myweekend.R;
import com.nakien.myweekend.adapter.ExListSchduleAdapter;
import com.nakien.myweekend.model.CinemasCenter;
import com.nakien.myweekend.model.Schedule;
import com.nakien.myweekend.util.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ScheduleByFilmFragment extends Fragment {
    private View rootView;
    private ExpandableListView lvSchedule;
    private ArrayList<String> listCinemas = new ArrayList<>();
    private HashMap<String, ArrayList<Schedule>> listTime = new HashMap<>();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String selectedDate;
    private String id_film;

    public static ScheduleByFilmFragment newInstance(String date, String id_film) {
        ScheduleByFilmFragment myFragment = new ScheduleByFilmFragment();
        Bundle args = new Bundle();
        args.putString("date", date);
        args.putString("id_film", id_film);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            selectedDate = bundle.getString("date");
            id_film = bundle.getString("id_film");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_schedule_by_cinemas_layout, container, false);
        lvSchedule = rootView.findViewById(R.id.listViewSchedule);

        final DatabaseReference mRefChild = database.getReference("SUATCHIEU");
        DatabaseReference mRefCinemas = database.getReference("CUMRAP");
        mRefCinemas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    final CinemasCenter cinemas = new CinemasCenter(data.getValue(CinemasCenter.class));
                    listCinemas.add(cinemas.getTenCum());
                    mRefChild.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<Schedule> list = new ArrayList<>();
                            for(DataSnapshot data : dataSnapshot.getChildren()){
                                Schedule sch = new Schedule(data.getValue(Schedule.class));
                                sch.setKey(data.getKey());
                                if(sch.getId_film().equals(id_film) && sch.getId_cum().equals(cinemas.getIdCumRap()) && sch.getNgay().equals(selectedDate) && Util.isInvalidTime(sch.getGio_bd()+"-"+sch.getNgay())){
                                    list.add(sch);
                                }
                            }
                            if(list.size() > 0)
                                listTime.put(listCinemas.get(listCinemas.indexOf(cinemas.getTenCum())), list);
                            else{
                                listCinemas.remove(listCinemas.indexOf(cinemas.getTenCum()));
                            }
                            lvSchedule.setAdapter(new ExListSchduleAdapter(getActivity(), R.layout.item_list_schdule_by_cinemas_layout, R.layout.item_time_schedule_layout, listCinemas, listTime));
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    lvSchedule.setAdapter(new ExListSchduleAdapter(getActivity(), R.layout.item_list_schdule_by_cinemas_layout, R.layout.item_time_schedule_layout, listCinemas, listTime));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return rootView;
    }
}
