package com.example.studentadmissionsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.TreeMap;

public class Final_result extends AppCompatActivity {

    TextView textView;

    String email_key = "";
    StringBuilder myName;

    String seat;
    String st="1";

    Button a, b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_final_result);
        textView = findViewById(R.id.textView2);


        Intent intent3 = getIntent();
        st = intent3.getStringExtra("state");
        Log.d("state", st);


        a = findViewById(R.id.accept);
        b = findViewById(R.id.Reject);

        if (Objects.equals(st, "0")) {
            textView.setText("You already have selected your choice");
            a.setVisibility(View.INVISIBLE);
            b.setVisibility(View.INVISIBLE);
        }


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email_key = user.getEmail();
        } else {
            // No user is signed in
        }


        assert email_key != null;
        myName = new StringBuilder(email_key);
        for (int i = 0; i < email_key.length(); i++) {
            if (email_key.charAt(i) == '@' || email_key.charAt(i) == '.' || email_key.charAt(i) == '#' || email_key.charAt(i) == '[' || email_key.charAt(i) == ']') {
                myName.setCharAt(i, '*');
            }
        }

        FirebaseDatabase.getInstance().getReference("final_result").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                        seat = snapshot.child(myName.toString()).getValue().toString();

                        if(seat.equals("NA")){
                            Log.d("check", myName.toString());
                            textView.setText("Sorry, You haven't got any seat.");
                            a.setVisibility(View.INVISIBLE);
                            b.setVisibility(View.INVISIBLE);
                        }
                        else if(Objects.equals(st, "1")){
                            Log.d("check", myName.toString());
                            textView.setText("Congratulations!, You got " + seat + " seat.");
                        }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Final_result.this, "Congrats You have accepted the seat successfully!", Toast.LENGTH_SHORT).show();
                FirebaseDatabase.getInstance().getReference("final_admission").child(myName.toString()).child("seat").setValue(seat);
                FirebaseDatabase.getInstance().getReference("final_admission").child(myName.toString()).child("state").setValue("0");
                a.setVisibility(View.INVISIBLE);
                b.setVisibility(View.INVISIBLE);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Final_result.this, "You have rejected the seat offer!", Toast.LENGTH_SHORT).show();
                FirebaseDatabase.getInstance().getReference("final_admission").child(myName.toString()).child("state").setValue("0");
                a.setVisibility(View.INVISIBLE);
                b.setVisibility(View.INVISIBLE);
            }
        });
    }


    public void goBack(View view){
        Intent intent = new Intent(Final_result.this, After_Councel.class);
        startActivity(intent);
    }
}