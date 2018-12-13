package com.nakien.myweekend.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nakien.myweekend.R;
import com.nakien.myweekend.adapter.ExListSchduleAdapter;
import com.nakien.myweekend.model.Film;
import com.nakien.myweekend.model.Schedule;
import com.nakien.myweekend.util.Util;

import java.util.ArrayList;
import java.util.HashMap;

public class ScheduleByCinemasFragment extends Fragment {
    private View rootView;
    private ExpandableListView lvSchedule;
    private ArrayList<String> listFilm = new ArrayList<>();
    private HashMap<String, ArrayList<Schedule>> listTime = new HashMap<>();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String selectedDate;
    private String id_cum;

    public static ScheduleByCinemasFragment newInstance(String date, String id_cum) {
        ScheduleByCinemasFragment myFragment = new ScheduleByCinemasFragment();
        Bundle args = new Bundle();
        args.putString("date", date);
        args.putString("id_cum", id_cum);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            selectedDate = bundle.getString("date");
            id_cum = bundle.getString("id_cum");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_schedule_by_cinemas_layout, container, false);
        lvSchedule = rootView.findViewById(R.id.listViewSchedule);

        final DatabaseReference mRefChild = database.getReference("SUATCHIEU");
        DatabaseReference mRefFilm = database.getReference("PHIM");
        mRefFilm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    final Film film = new Film(data.getValue(Film.class));
                    listFilm.add(film.getTenphim());
                    mRefChild.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<Schedule> list = new ArrayList<>();
                            for(DataSnapshot data : dataSnapshot.getChildren()){
                                Schedule sch = new Schedule(data.getValue(Schedule.class));
                                sch.setKey(data.getKey());
                                if(sch.getId_cum().equals(id_cum) && sch.getId_film().equals(film.getId_film()) && sch.getNgay().equals(selectedDate) && Util.isInvalidTime(sch.getGio_bd()+"-"+sch.getNgay())){
                                    list.add(sch);
                                }
                            }
                            if(list.size() > 0)
                                listTime.put(listFilm.get(listFilm.indexOf(film.getTenphim())), list);
                            else{
                                listFilm.remove(listFilm.indexOf(film.getTenphim()));
                            }
                            lvSchedule.setAdapter(new ExListSchduleAdapter(getActivity(), R.layout.item_list_schdule_by_cinemas_layout, R.layout.item_time_schedule_layout, listFilm, listTime));
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
        return rootView;
    }
}
