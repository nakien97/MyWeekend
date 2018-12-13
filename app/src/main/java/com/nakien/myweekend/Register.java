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
import com.google.firebase.database.*;
import com.nakien.myweekend.model.User;

public class Register extends AppCompatActivity {
    private EditText edtPhone;
    private EditText edtPass;
    private EditText edtReTypePass;
    private EditText edtEmail;
    private EditText edtName;
    private Button btnRegister;
    private Toolbar toolBarRegister;
    private ProgressDialog dialog;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.nakien.myweekend.R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        initWidget();
        actionBar();
        clickBtnRegidter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null) {
            finish();
        }
    }

    public void initWidget(){
        edtEmail = findViewById(com.nakien.myweekend.R.id.editTextEmail);
        edtPass = findViewById(com.nakien.myweekend.R.id.editTextPass);
        edtName = findViewById(com.nakien.myweekend.R.id.editTextName);
        edtReTypePass = findViewById(com.nakien.myweekend.R.id.editTextReType);
        edtPhone = findViewById(com.nakien.myweekend.R.id.editTextPhone);
        btnRegister = findViewById(com.nakien.myweekend.R.id.buttonRegister);
        toolBarRegister = findViewById(R.id.toolBarRegister);
        dialog = new ProgressDialog(this);
    }

    public void actionBar(){
        setSupportActionBar(toolBarRegister);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBarRegister.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void registerUser(){
        final String name = edtName.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        final String password = edtPass.getText().toString().trim();
        String retype = edtReTypePass.getText().toString().trim();
        final String phone = edtPhone.getText().toString().trim();

        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()){
            Toast.makeText(Register.this, "Vui lòng nhập đầy đủ dữ liệu!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.equals(retype)){
            Toast.makeText(Register.this, "Mật khẩu được nhập lại không trùng khớp!", Toast.LENGTH_SHORT).show();
            edtReTypePass.setText("");
            edtPass.setText("");
            return;
        }
        if(password.length() < 6) {
            Toast.makeText(Register.this, "Mật khẩu phải có ít nhất 6 ký tự!", Toast.LENGTH_SHORT).show();
            edtReTypePass.setText("");
            edtPass.setText("");
            return;
        }
        dialog.setMessage("Đang xử lý....");
        dialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(email, name, password, phone);
                            FirebaseDatabase.getInstance().getReference("USERS")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(Register.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                                mAuth.signOut(); //// check lại
                                                Intent intent = new Intent(Register.this, LogIn.class);
                                                startActivity(intent);
                                            }
                                            else{
                                                Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else{
                            Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });

    }

    public void clickBtnRegidter() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }
}