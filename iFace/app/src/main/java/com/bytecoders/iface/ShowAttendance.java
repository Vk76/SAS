package com.bytecoders.iface;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ShowAttendance extends AppCompatActivity {
    private DatabaseReference mref;
    TextView tv;
    int ans;
    String abc;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendance);
        tv = (TextView) findViewById(R.id.ar);

//

        mref = FirebaseDatabase.getInstance().getReference().child("ABC");

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                abc=String.valueOf(dataSnapshot.child("Attendance").getValue());

                if(flag==0){
                    Toast.makeText(getApplicationContext(),"Attendance"+abc,Toast.LENGTH_LONG).show();
                    //tv.setText("Attendance:");
                    tv.append(" "+abc);
                    flag=1;


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
