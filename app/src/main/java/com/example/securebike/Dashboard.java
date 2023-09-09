package com.example.securebike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Dashboard extends AppCompatActivity {
    CardView startbike,controlbike,show_bike;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        startbike=findViewById(R.id.card1);
        controlbike=findViewById(R.id.card2);
        show_bike=findViewById(R.id.card3);

        startbike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Dashboard.this, Add_Vehicle.class);
                startActivity(intent);
            }
        });

        show_bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Dashboard.this, Show_Vehicle.class);
                startActivity(intent);
            }
        });

        controlbike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Dashboard.this,Controlbike.class);
                startActivity(intent);
            }
        });
    }
}

