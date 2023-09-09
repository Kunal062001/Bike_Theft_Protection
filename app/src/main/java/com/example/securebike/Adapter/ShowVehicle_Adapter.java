package com.example.securebike.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.securebike.Pojo.VehicleInfo;
import com.example.securebike.R;

import java.util.ArrayList;

public class ShowVehicle_Adapter extends ArrayAdapter<String> {
public static int flg=0;
private Activity context;
        ArrayList<VehicleInfo> slist=new ArrayList<>();
public ShowVehicle_Adapter(Activity context, ArrayList<VehicleInfo> slist) {
        super(context, R.layout.activity_show_vehicle);
        this.context = context;
        this.slist = slist;
        }
public int getCount() {
        return slist.size();

        }
public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.show_vehicleinfo, null,true);

        /*TextView slot=(TextView) rowView.findViewById(R.id.tv_slotno);*/
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView vehicletype=(TextView) rowView.findViewById(R.id.tv_vehitype);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView vehicleno=(TextView) rowView.findViewById(R.id.tv_veno);




        VehicleInfo vehicleInfo = slist.get(position);
        // name.setText(maintenanceInfo.getName());
        // slot.setText(vehicleInfo.getSlotNo());
        vehicleno.setText(vehicleInfo.getVehicleNo());
        vehicletype.setText(vehicleInfo.getVehicleType());


        return rowView;
        }


        }
