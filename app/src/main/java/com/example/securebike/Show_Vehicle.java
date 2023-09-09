package com.example.securebike;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.securebike.Adapter.ShowVehicle_Adapter;
import com.example.securebike.Pojo.UserInfo;
import com.example.securebike.Pojo.VehicleInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Show_Vehicle extends AppCompatActivity {
    ListView listView;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    UserInfo userInfo;

    ArrayList<VehicleInfo> slist;

    ShowVehicle_Adapter adapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_vehicle);

        listView=findViewById(R.id.lv_vehicle);

        SharedPreferences sh=this.getSharedPreferences("SecureBike",MODE_PRIVATE);
        Gson gson=new Gson();

        String json=sh.getString("user","");
        if(!json.equals(""))
        {
            userInfo=gson.fromJson(json, UserInfo.class);

        }
        databaseReference= firebaseDatabase.getInstance().getReference(AppConstant.BaseURL+ "/Vehicle_Info").child(userInfo.getContact());
        //name.setText(userInfo.getName());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                slist=new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    VehicleInfo ve = dataSnapshot.getValue(VehicleInfo.class);

                    slist.add(ve);


                }
                adapter = new ShowVehicle_Adapter(Show_Vehicle.this, slist);

                listView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}