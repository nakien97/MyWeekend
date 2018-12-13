package com.nakien.myweekend;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nakien.myweekend.adapter.ProfileFragmentAdapter;
import com.nakien.myweekend.model.User;

public class Profile extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView txtName;
    private TabLayout tabProfile;
    private ViewPager pagerProfile;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        initWidget();
        actionBar();
    }
    @Override
    protected void onStart() {
        super.onStart();
        final String key = mAuth.getCurrentUser().getUid();
        DatabaseReference myRef = database.getReference("USERS");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = null;
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    if(data.getKey().equals(key)){
                        user = new User(data.getValue(User.class));
                    }
                }
                txtName.setText(user.getName());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void initWidget(){
        toolbar = findViewById(R.id.toolBarProfile);
        txtName = findViewById(R.id.textViewProfileName);
        pagerProfile = findViewById(R.id.viewPagerProfile);
        pagerProfile.setAdapter(new ProfileFragmentAdapter(getSupportFragmentManager()));
        tabProfile = findViewById(R.id.tabProfile);
        tabProfile.setupWithViewPager(pagerProfile);
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
