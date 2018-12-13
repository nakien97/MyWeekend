package com.nakien.myweekend.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nakien.myweekend.R;
import com.nakien.myweekend.adapter.ListFilmAdapter;
import com.nakien.myweekend.model.Film;

import java.util.ArrayList;

public class ComingSoonFilmFragment extends Fragment {
    private View rootView;
    private ListView lvComingSoon;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_comingsoon_film_layout, container, false);
        lvComingSoon = rootView.findViewById(R.id.lvComingSoonFilm);
        DatabaseReference mRef = database.getReference("PHIM");
        final ArrayList<Film> list = new ArrayList<>();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Film film = new Film(data.getValue(Film.class));
                    if(film.getId_trangthaiphim().equals("t1"))
                        list.add(film);
                }
                if(!list.isEmpty())
                   lvComingSoon.setAdapter(new ListFilmAdapter(getActivity(), R.layout.item_listfilm_layout, list, false));

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return rootView;
    }
}