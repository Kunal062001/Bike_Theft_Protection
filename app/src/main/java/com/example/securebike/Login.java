package com.example.securebike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.securebike.Pojo.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class Login extends AppCompatActivity {
    EditText etcontact,etpassword;
    Button btn_login;
    TextView tv_reg;
    String Pattern = "[0-9]{10}";
    DatabaseReference databaseReference;
    int flg = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etcontact=findViewById(R.id.edit_contact);
        etpassword=findViewById(R.id.edit_pass);
        btn_login=findViewById(R.id.btn_login);
        tv_reg=findViewById(R.id.tv_reg);

        tv_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,Registration.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /**   if (etcontact.getText().toString().equals("1234567890") && etpassword.getText().toString().equals("1234"))
                {
                    Intent intent=new Intent(Login.this,Dashboard.class);
                    startActivity(intent);
                    Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Login.this, "Login Unsuccessfully", Toast.LENGTH_SHORT).show();
                }**/


                if (etcontact.getText().toString().isEmpty()) {
                    etcontact.setError("Enter Contact Number");
                    return;
                }
                if (etpassword.getText().toString().isEmpty()) {
                    etpassword.setError("Enter Password");
                    return;
                }

                databaseReference= FirebaseDatabase.getInstance().getReference(AppConstant.BaseURL+ "/Bike_User");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        flg = 0;
                        for (DataSnapshot data : snapshot.getChildren()) {

                            UserInfo userInfo = data.getValue(UserInfo.class);

                            assert userInfo != null;
                            String contact = userInfo.getContact();
                            String password = userInfo.getPassword();

                            if (contact.equals(etcontact.getText().toString()) && etcontact.getText().toString().trim().matches(Pattern)) {
                                if (password.equals(etpassword.getText().toString())) {
                                    SharedPreferences.Editor sh=getSharedPreferences("SecureBike",MODE_PRIVATE).edit();
                                    Gson gson=new Gson();
                                    String json=gson.toJson(userInfo);
                                    sh.putString("user",json);
                                    sh.commit();
                                    Toast.makeText(Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                        flg=1;
                                    Intent intent = new Intent(Login.this, Dashboard.class);
                                    startActivity(intent);
                                }
                            }

                        }
                        if (flg==0)
                        {
                            Toast.makeText(Login.this, "Invalid Details", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}