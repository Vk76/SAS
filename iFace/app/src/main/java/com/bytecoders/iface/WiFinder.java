package com.bytecoders.iface;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;
//import android.support.annotation.NonNull;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.WIFI_SERVICE;


public class WiFinder extends AppCompatActivity {
    private DatabaseReference mref;
    private String secretKey;
    TextView stats;
    int curr=0;
    String abc;
    int flag=0;
    Button ex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wi_finder);
        ex=(Button)findViewById(R.id.button4);
        stats = findViewById(R.id.curr_wifi_stat);
        secretKey = getString(R.string.psswd);
        connectWifi();
        ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void connectWifi()
    {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String fd = df.format(c);
        try {
//            String ssid = AESHelper.encrypt(fd, secretKey);
//

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.System.canWrite(this.getApplicationContext())) {

                } else {
                    Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + this.getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }


            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//remember id

            String ip="522954944";

              String value=String.valueOf(wifiManager.getConnectionInfo().getIpAddress());

            //Toast.makeText(getApplicationContext(),value+" "+ip,Toast.LENGTH_LONG).show();
            if(value.equals(ip)) {

                mref = FirebaseDatabase.getInstance().getReference().child("ABC");
                mref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long l = dataSnapshot.getChildrenCount();
                        abc = String.valueOf(dataSnapshot.child("Attendance").getValue());

                        if (flag == 0) {
                            curr = Integer.parseInt(abc) + 1;
                            mref.child("Attendance").setValue(curr);
                            flag = 1;
                            stats.setText("Attendance posted successfully and current attendance: " + curr);

//                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
//                            startActivity(intent);


                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

        }
        catch (Exception e)
        {
            Log.d("IFACE:WIFI","NOT CONNECTED");
        }
    }
}
