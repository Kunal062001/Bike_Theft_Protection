package com.example.securebike;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.securebike.Pojo.UserInfo;
import com.example.securebike.Pojo.VehicleInfo;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class Add_Vehicle extends AppCompatActivity {
    EditText et_Vehicalno,et_dcontact,et_dname;
    Button btn_add;
    RadioGroup radioGroup;
    RadioButton two_wheeler,four_wheeler;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    int flg = 0;
    String Pattern = "[0-9]{10}";
    String vehiclePattern="^[A-Z]{2}\\s[0-9]{2}\\s[A-Z]{2}\\s[0-9]{4}$";
    UserInfo  userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehical);

        et_Vehicalno=findViewById(R.id.edit_vehicleNo);
        // slot=findViewById(R.id.edit_slot);
        btn_add=findViewById(R.id.btn_add);
        radioGroup = (RadioGroup)findViewById(R.id.groupradio);
        two_wheeler=findViewById(R.id.two_wheeler);
        four_wheeler=findViewById(R.id.four_wheeler);
       /* et_dcontact=findViewById(R.id.edit_dcontact);
        et_dname=findViewById(R.id.edit_name);
*/
        radioGroup.clearCheck();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override


            public void onCheckedChanged(RadioGroup group, int checkedId)
            {

                RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
            }
        });

        databaseReference= firebaseDatabase.getInstance().getReference(AppConstant.BaseURL+ "/Vehicle_Info");
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String twowheeler= two_wheeler.getText().toString();
                String fourwheeler=four_wheeler.getText().toString();


               /* if( slot.getText().toString().isEmpty()){
                    slot.setError("Enter slot Number");
                    return;
                }
*/
                if( et_Vehicalno.getText().toString().isEmpty()){
                    et_Vehicalno.setError("Enter Vehicle No");
                    return;
                }
                if(!et_Vehicalno.getText().toString().trim().matches(vehiclePattern))
                {
                    et_Vehicalno.setError("Please enter valid vehicle number");
                    return;
                }
                /*SharedPreferences.Editor sh=getSharedPreferences("SmartParking",MODE_PRIVATE).edit();
                Gson gson=new Gson();
                String json=gson.toJson(vehicleInfo);
                sh.putString("vehicle",json);
                sh.commit();
*/

                VehicleInfo vehicleInfo=new VehicleInfo();

                //vehicleInfo.setSlotNo(slot.getText().toString());
                vehicleInfo.setContact(userInfo.getContact());
                vehicleInfo.setVehicleNo(et_Vehicalno.getText().toString());

                vehicleInfo.setName(userInfo.getName());


                //  userInfo.setVehicleType(radioGroup.toString());

                if (two_wheeler.isChecked()) {
                    vehicleInfo.setVehicleType(twowheeler);
                } else {
                    vehicleInfo.setVehicleType(fourwheeler);



                }
                databaseReference.child(userInfo.getContact()).push().setValue(vehicleInfo, new DatabaseReference.CompletionListener() {

                    // databaseReference.child(userInfo.getContact()).child(vehicleInfo.getVehicleNo()).setValue(vehicleInfo, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(Add_Vehicle.this,"Vehicle Added",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Add_Vehicle.this,Dashboard.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);

                    }
                });
            }
        });

        SharedPreferences sh=this.getSharedPreferences("SecureBike",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sh.getString("user","");
        if(!json.equals(""))
        {
            userInfo=gson.fromJson(json,UserInfo.class);
        }
    }
}