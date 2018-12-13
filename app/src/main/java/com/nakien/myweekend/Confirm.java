package com.nakien.myweekend;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.nakien.myweekend.item.History;
import com.nakien.myweekend.model.CinemasCenter;
import com.nakien.myweekend.model.Film;
import com.nakien.myweekend.model.RecordBooking;
import com.nakien.myweekend.model.Schedule;
import com.nakien.myweekend.model.Ticket;
import com.nakien.myweekend.model.User;
import com.nakien.myweekend.util.SendEmail;
import com.nakien.myweekend.util.Util;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class Confirm extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView txtTotalTicket;
    private TextView txtPrice;
    private TextView txtTransTotal;
    private TextView txtShowTotal;
    private TextView txtFilmName;
    private TextView txtAge;
    private TextView txtTimeDate;
    private TextView txtCinemasCenter;
    private TextView txtCinema;
    private TextView txtChair;
    private TextView txtCancel;
    private Button btnConfirm;
    private ImageView imgFilm;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private ArrayList<String> listChair = new ArrayList<>();
    private ArrayList<String> list = new ArrayList<>();
    private String key;
    private long don_gia;
    private long tong_tien;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        listChair = intent.getStringArrayListExtra("list");
        Collections.sort(listChair);
        key = intent.getStringExtra("key");
        initWidget();
        actionBar();
        showSelectChair();
        clickCancel();
        clickConfirm();
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference mRefSchedule = database.getReference("SUATCHIEU");
        final DatabaseReference mRefCinemasCenter = database.getReference("CUMRAP");
        final DatabaseReference mRefFilm = database.getReference("PHIM");
        mRefSchedule.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Schedule sch = dataSnapshot.getValue(Schedule.class);
                sch.setKey(dataSnapshot.getKey());
                //txtTotalTicket.append(listChair.size() +"");

                //
                txtTotalTicket.append(listChair.size() +"");
                //


                txtTimeDate.setText(sch.getGio_bd() + " - " + sch.getNgay());
                txtCinema.append(sch.getId_rap());
                mRefFilm.child(sch.getId_film()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Film f = dataSnapshot.getValue(Film.class);
                        Picasso.get().load(f.getUrl_banner_doc()).into(imgFilm);
                        imgFilm.setScaleType(ImageView.ScaleType.FIT_XY);
                        txtFilmName.setText(f.getTenphim());
                        final DatabaseReference mRef = database.getReference("DOTUOI");
                        mRef.child(f.getId_dotuoi()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                txtAge.setText(dataSnapshot.getKey() + " - " + dataSnapshot.child("diengiai").getValue());
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
                mRefCinemasCenter.child(sch.getId_cum()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        CinemasCenter cinemas = dataSnapshot.getValue(CinemasCenter.class);
                        txtCinemasCenter.setText(cinemas.getTenCum());
                        txtPrice.append(Util.formatMoney(cinemas.getGia_ve()) +"đ");
                        don_gia = cinemas.getGia_ve();
                        txtShowTotal.append(Util.formatMoney(cinemas.getGia_ve() * listChair.size()) + "đ");
                        tong_tien = cinemas.getGia_ve() * listChair.size();
                        txtTransTotal.append(Util.formatMoney(cinemas.getGia_ve() * listChair.size()) + "đ");

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

    }

    private void initWidget() {
        toolbar = findViewById(R.id.toolBarConfirm);
        txtTotalTicket = findViewById(R.id.txtTotalTicket);
        txtPrice = findViewById(R.id.txtPrice);
        txtTransTotal = findViewById(R.id.txtTransTotal);
        txtFilmName = findViewById(R.id.txtFilmName);
        txtAge = findViewById(R.id.txtAge);
        txtTimeDate = findViewById(R.id.txtTimeDate);
        txtCinemasCenter = findViewById(R.id.txtCinemasCenter);
        txtCinema = findViewById(R.id.txtCinemas);
        txtChair = findViewById(R.id.txtChair);
        txtShowTotal = findViewById(R.id.txtShowTotal);
        txtCancel = findViewById(R.id.txtCancel);
        btnConfirm = findViewById(R.id.btnTransConfirm);
        imgFilm = findViewById(R.id.imageViewFilm);
        dialog = new ProgressDialog(this);
    }

    public void actionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void showSelectChair(){
        for(int i = 0; i < listChair.size(); i++){
            txtChair.append(listChair.get(i));
            if(i != listChair.size()-1){
                txtChair.append(", ");
            }
        }
    }

    public void clickCancel(){
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCancel();
            }
        });
    }

    public void clickConfirm(){
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogConfirm();
            }
        });
    }


    public void showDialogCancel(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Bạn có muốn hủy giao dịch không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void showDialogConfirm(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Bạn muốn xác nhận?");
        builder.setCancelable(false);
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                pushRecordIntoDB();

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void sendConfirmEmail(String bookDate) {
        History history = new History();
        history.setDateTimeBook(bookDate);
        history.setFilmName(txtFilmName.getText().toString());
        history.setDateTime(txtTimeDate.getText().toString());
        history.setCinemasCenter(txtCinemasCenter.getText().toString());
        history.setCinema(txtCinema.getText().toString());
        //history.setTicket(Integer.parseInt(txtTotalTicket.getText().toString()));
        String chair = "";
        for (int i = 0; i < listChair.size(); i++){
            chair += listChair.get(i);
            if(i != listChair.size()-1)
                chair += ", ";
        }
        history.setTotal(tong_tien);
        try {
            SendEmail.send(history, mAuth.getCurrentUser().getEmail(), chair);
        } catch (MailjetException e) {
            e.printStackTrace();
        } catch (MailjetSocketTimeoutException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void pushRecordIntoDB(){
        dialog.setMessage("Đang xử lý....");
        dialog.show();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String id_suatchieu = key;
        String email_kh = mAuth.getCurrentUser().getEmail();
        Calendar calendar = Calendar.getInstance();
        final String ngay_lap = Util.formatDateTime2String(calendar);
        String id_phieudatve = mAuth.getCurrentUser().getUid() +"_"+ id_suatchieu +"_";
        for(int i = 0; i <listChair.size(); i++){
            id_phieudatve += listChair.get(i);
            if(i != listChair.size()-1)
                id_phieudatve += "_";
        }
        Ticket ticket = new Ticket(email_kh, id_phieudatve, id_suatchieu, ngay_lap, tong_tien);
        DatabaseReference mRef = database.getReference("PHIEUDATVE");
        final String finalId_phieudatve = id_phieudatve;
        final boolean[] fail = {false};
        mRef.child(id_phieudatve)
                .setValue(ticket)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            final DatabaseReference mRefChild = database.getReference("CT_PHIEUDATVE");
                            for(int i = 0; i < listChair.size(); i++){
                                RecordBooking record = new RecordBooking(listChair.get(i), finalId_phieudatve, don_gia);
                                mRefChild.push()
                                        .setValue(record)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(!task.isSuccessful()){
                                            fail[0] = true;
                                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            dialog.dismiss();
                            if(fail[0] == false){
                                sendConfirmEmail(ngay_lap);
                                showDialogOK();
                            }
                        }
                        else{
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void showDialogOK() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bạn đã đặt vé phim thành công");
        builder.setMessage("Kiểm tra hộp thư email hoặc lịch sử giao dịch trong ứng dụng để biết thêm chi tiết");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
