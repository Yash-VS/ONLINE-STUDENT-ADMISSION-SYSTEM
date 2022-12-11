package com.example.studentadmissionsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
class preference {
    String pref1,pref2,pref3;
    public preference(String pref1, String pref2, String pref3) {
        this.pref1 = pref1;
        this.pref2 = pref2;
        this.pref3 = pref3;
    }
}

public class Admin_home extends AppCompatActivity {
    TreeMap<String, String> std_score = new TreeMap<String, String>(Collections.reverseOrder());
    TreeMap<String, preference> std_pref = new TreeMap<String, preference>();
    TreeMap<String, String> crs_seats = new TreeMap<String, String>();
    public String pref1 = null,pref2 = null,pref3 = null;
    public String seat1 = "10" ,seat2 = null ,seat3 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_admin_home);
    }
    student std;

    boolean done;
    public void show_result(View view) {
        FirebaseDatabase.getInstance().getReference("admin_process").setValue("2");
        Toast.makeText(Admin_home.this, "Result has been displayed successfully!", Toast.LENGTH_SHORT).show();


        FirebaseDatabase.getInstance().getReference("student_data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String email = snapshot1.getKey().toString();
                        std_score.put(snapshot1.child("jeescore").getValue().toString(), email);
                        Log.d("std",email);
                    }

                    for (Map.Entry<String, String> entry : std_score.entrySet()) {
                        FirebaseDatabase.getInstance().getReference("course_pref").child(entry.getValue()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    String email= snapshot.getKey().toString();
                                    pref1 = snapshot.child("pref_1").getValue().toString();
                                    pref2 = snapshot.child("pref_2").getValue().toString();
                                    pref3 = snapshot.child("pref_3").getValue().toString();
                                    preference temp = new preference(pref1, pref2, pref3);
                                    std_pref.put(email, temp);
                                    Log.d("pref",entry.getValue().toString()+":  "+pref1+" "+pref2+" "+pref3);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    FirebaseDatabase.getInstance().getReference("course_data").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                    crs_seats.put(snapshot1.getKey().toString(), snapshot1.getValue().toString());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        for (Map.Entry<String, String> entry : crs_seats.entrySet()) {
            Log.d("seats",entry.getKey()+":  "+entry.getValue());
        }
        for (Map.Entry<String, preference> entry : std_pref.entrySet()) {
            Log.d("pref",entry.getKey()+":  "+entry.getValue().pref1+" "+entry.getValue().pref2+" "+entry.getValue().pref3);
        }


        HashMap<String,String> result= new HashMap<String, String>();
        for (Map.Entry<String, String> entry : std_score.entrySet()) {
            preference temp = std_pref.get(entry.getValue());
            FirebaseDatabase.getInstance().getReference("final_admission").child(entry.getValue()).child("state").setValue("1");
            pref1=temp.pref1;
            pref2=temp.pref2;
            pref3=temp.pref3;
            int seat1= Integer.parseInt(crs_seats.get(temp.pref1));
            int seat2= Integer.parseInt(crs_seats.get(temp.pref2));
            int seat3= Integer.parseInt(crs_seats.get(temp.pref3));
            Log.d("seats", entry.getValue() + " " +String.valueOf(seat1) + " " + String.valueOf(seat2) + " " + String.valueOf(seat3));

            if(seat1>0){
                Log.d("result",pref1);
                result.put(entry.getValue(),pref1);
                crs_seats.put(pref1, String.valueOf(seat1-1));
            }
            else if(seat2>0){
                Log.d("result",pref2);
                result.put(entry.getValue(),pref2);
                crs_seats.put(pref2, String.valueOf(seat2-1));
            }
            else if(seat3>0){
                Log.d("result",pref3);
                result.put(entry.getValue(),pref3);
                crs_seats.put(pref3, String.valueOf(seat3-1));
            }
            else{
                result.put(entry.getValue(),"NA");
            }
        }

        Iterator hmIterator = result.entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            Log.d("result",mapElement.getKey() + " " + mapElement.getValue());
        }
        FirebaseDatabase.getInstance().getReference("final_result").setValue(result);
    }


    public void touch(View view){
        switch (view.getId()){
            case R.id.start_application:{
                Toast.makeText(Admin_home.this, "Application window has been started successfully!", Toast.LENGTH_SHORT).show();
                FirebaseDatabase.getInstance().getReference("admin_process").setValue("0");
                break;
            }
            case R.id.start_councel:{
                Toast.makeText(Admin_home.this, "councelling has been started successfully!", Toast.LENGTH_SHORT).show();
                FirebaseDatabase.getInstance().getReference("admin_process").setValue("1");
                break;
            }
        }
    }


    public void fab(View view){
        FirebaseAuth.getInstance().signOut();
        Intent maini = new Intent(Admin_home.this,MainActivity.class);
        maini.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(maini);
    }
}