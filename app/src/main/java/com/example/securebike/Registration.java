package com.example.securebike;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.securebike.Pojo.UserInfo;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {
    EditText etcontact,etname,etaddress,etpassword;
    Button btn_reg;
    String Pattern = "[0-9]{10}";
    DatabaseReference databaseReference;
    int flg = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etaddress=findViewById(R.id.edit_address);
        etcontact=findViewById(R.id.edit_contact);
        etname=findViewById(R.id.edit_name);
        etpassword=findViewById(R.id.edit_password);
        btn_reg=findViewById(R.id.btn_reg);

        databaseReference= FirebaseDatabase.getInstance().getReference(AppConstant.BaseURL+ "/Bike_User");

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etname.getText().toString().isEmpty()){
                    etname.setError("Enter Name");
                    return;
                }
                if( etcontact.getText().toString().isEmpty()){
                    etcontact.setError("Enter Contact Number");
                    return;
                }
                if(!etcontact.getText().toString().trim().matches(Pattern))
                {
                    etcontact.setError("Please enter valid 10 digit phone number");
                    return;
                }
                if(etaddress.getText().toString().isEmpty()){
                    etaddress.setError("Enter Address");
                    return;
                }
                if(etpassword.getText().toString().isEmpty()){
                    etpassword.setError("Enter Password");
                    return;
                }

                UserInfo userInfo=new UserInfo();

                userInfo.setName(etname.getText().toString());
                userInfo.setContact(etcontact.getText().toString());
                userInfo.setAddress(etaddress.getText().toString());
                userInfo.setPassword(etpassword.getText().toString());

                databaseReference.child(userInfo.getContact()).setValue(userInfo, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(Registration.this,"Registration Done",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Registration.this,Login.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}