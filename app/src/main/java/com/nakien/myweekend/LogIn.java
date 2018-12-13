package com.nakien.myweekend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {
    private EditText edtUserName;
    private EditText edtPass;
    private Button btnLogIn;
    private Button btnRegister;
    private Toolbar toolbarLogIn;
    private ProgressDialog dialog;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initWidget();
        actionBar();
        mAuth = FirebaseAuth.getInstance();
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogIn();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this, Register.class);
                finish();
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), Profile.class));
        }

    }

    public void initWidget(){
        edtUserName = findViewById(R.id.editText);
        edtPass = findViewById(R.id.editText2);
        btnLogIn = findViewById(R.id.buttonLogIn);
        btnRegister = findViewById(R.id.buttonDangKy);
        toolbarLogIn = findViewById(R.id.toolBarLogin);
        dialog = new ProgressDialog(this);
    }

    public void actionBar(){
        setSupportActionBar(toolbarLogIn);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLogIn.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void userLogIn(){
        String username = edtUserName.getText().toString().trim();
        String password = edtPass.getText().toString();
        if(username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ dữ liệu", Toast.LENGTH_SHORT).show();
            return;
        }
        dialog.setMessage("Đang đăng nhập....");
        dialog.show();
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LogIn.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else{
                            Toast.makeText(LogIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
    }


}
