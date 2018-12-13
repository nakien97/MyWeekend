package com.nakien.myweekend.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nakien.myweekend.LogIn;
import com.nakien.myweekend.MainActivity;
import com.nakien.myweekend.R;
import com.nakien.myweekend.model.User;

public class ProfileFragment extends Fragment {
    private EditText edtEmail;
    private EditText edtName;
    private EditText edtPhone;
    private EditText edtOldPass;
    private EditText edtNewPass;
    private EditText edtReType;
    private Button btnUpdate;
    private View rootView;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String pw;
    private ProgressDialog dialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile_layout, container, false);
        initView();
        mAuth = FirebaseAuth.getInstance();
        final String key = mAuth.getCurrentUser().getUid();
        DatabaseReference myRef = database.getReference("USERS");
        myRef.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = new User(dataSnapshot.getValue(User.class));
                edtName.setText(user.getName());
                edtEmail.setText(user.getEmail());
                pw = user.getPassword().trim();
                edtPhone.setText(user.getPhone());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        upDateProfile();
        return rootView;
    }

    public void initView(){
        edtEmail = rootView.findViewById(R.id.editTextProfileEmail);
        edtName = rootView.findViewById(R.id.editTextProfileName);
        edtPhone = rootView.findViewById(R.id.editTextProfilePhone);
        edtOldPass = rootView.findViewById(R.id.editTextPW_OldPass);
        edtNewPass = rootView.findViewById(R.id.editTextPW_NewPass);
        edtReType = rootView.findViewById(R.id.editTextPW_Retype);
        btnUpdate = rootView.findViewById(R.id.buttonProfileUpdate);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Đang xử lý....");
        dialog.setCanceledOnTouchOutside(false);
    }

    public void upDateProfile(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                String email = edtEmail.getText().toString();
                String name = edtName.getText().toString().trim();
                String oldPass = edtOldPass.getText().toString().trim();
                String newPass = edtNewPass.getText().toString().trim();
                String reType = edtReType.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();
                if(name.isEmpty() || phone.isEmpty()){
                    Toast.makeText(getActivity(), "Họ tên và SDT không được rỗng!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
                final DatabaseReference myRef = database.getReference("USERS");
                if(oldPass.equals("") && newPass.equals("")){           //khong doi password
                    String id = mAuth.getCurrentUser().getUid();
                    User user = new User(email, name, pw, phone);
                    myRef.child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                dialog.dismiss();
                                Toast.makeText(getActivity(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                                getActivity().recreate();
                            }
                            else{
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    });
                }
                else if((oldPass.isEmpty() || newPass.isEmpty()))
                {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else if(!oldPass.isEmpty() && !newPass.isEmpty()){                           //doi password
                    if(!oldPass.equals(pw)){
                        edtNewPass.setText("");
                        edtOldPass.setText("");
                        edtReType.setText("");
                        Toast.makeText(getActivity(), "Bạn đã nhập sai mật khẩu hiện tại!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        return;
                    }
                    if(!newPass.equals(reType)){
                        edtNewPass.setText("");
                        edtReType.setText("");
                        Toast.makeText(getActivity(), "Xác nhận mật khẩu mới không trùng khớp!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        return;
                    }
                    final User user = new User(email, name, newPass, phone);
                    final String id = mAuth.getCurrentUser().getUid();
                    mAuth.getCurrentUser().updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                myRef.child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            dialog.dismiss();
                                            mAuth.signOut();
                                            showDialog();
                                        }
                                        else{
                                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    }
                                });

                            }
                            else{
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    });
                }
            }
        });
    }
    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.app_name);
        builder.setMessage("Cập nhật thành công - Vui lòng đăng nhập lại!");
        builder.setCancelable(false);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                getActivity().finish();
                startActivity(new Intent(getActivity(), LogIn.class));
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}