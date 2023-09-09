package com.example.securebike;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Controlbike extends AppCompatActivity {
    CardView card1,card2;
    MqttAndroidClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controlbike);

        card1=findViewById(R.id.start_card);
        card2=findViewById(R.id.stop_card);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDevices("off");
            }

        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {setDevices("on");
            }
        });

        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(Controlbike.this, "tcp://broker.hivemq.com", clientId);
        client = new MqttAndroidClient(this, "tcp://broker.hivemq.com",clientId);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    //Toast.makeText(this,"connected!!",Toast.LENGTH_LONG).show();
                    Toast.makeText(Controlbike.this, "connected", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Toast.makeText(this,"connection failed!! "+exception,Toast.LENGTH_LONG).show();
                    Toast.makeText(Controlbike.this, "connection failed!!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                // subText.setText(new String(message.getPayload()));
                // txt1.setText(new String(message.getPayload()));

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });


    }
    public  void setDevices(String msg)
    {
        try
        {
            client.publish("securebike/devices", msg.getBytes(), 0, false);


        }
        catch (Exception ex)
        {
            Toast.makeText(this,""+ex,Toast.LENGTH_LONG).show();
        }
    }
}