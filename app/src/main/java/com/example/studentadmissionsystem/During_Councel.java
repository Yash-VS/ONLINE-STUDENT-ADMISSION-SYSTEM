package com.example.studentadmissionsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class During_Councel extends AppCompatActivity {

    Button pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_during_councel);

        pref = findViewById(R.id.pref);
        pref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(During_Councel.this, prefernce_list.class);
                startActivity(intent3);
            }
        });
    }

    public  void fab(View view){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(During_Councel.this, "Logout Successully!!", Toast.LENGTH_SHORT).show();

        Intent maini = new Intent(During_Councel.this,MainActivity.class);
        maini.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(maini);
    }
}