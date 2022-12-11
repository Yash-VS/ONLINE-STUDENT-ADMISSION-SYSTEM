package com.example.studentadmissionsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class After_Councel extends AppCompatActivity {

    Button res;
    String st="1";

    String email_key = "";
    public StringBuilder myName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_after_councel);



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email_key = user.getEmail();
        } else {
            // No user is signed in
        }


        myName = new StringBuilder(email_key);
        for(int i = 0; i<email_key.length(); i++){
            if(email_key.charAt(i) == '@' || email_key.charAt(i) == '.' || email_key.charAt(i) == '#' || email_key.charAt(i) == '[' || email_key.charAt(i) == ']'){
                myName.setCharAt(i, '*');
            }
        }
        res = findViewById(R.id.res);
        Log.d("nnn",myName.toString());
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("final_admission").child(myName.toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        st = snapshot.child("state").getValue().toString();
                        Intent intent3 = new Intent(After_Councel.this, Final_result.class);
                        intent3.putExtra("state", st);
                        startActivity(intent3);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    }

    public  void fab(View view){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(After_Councel.this, "Logout Successully!!", Toast.LENGTH_SHORT).show();

        Intent maini = new Intent(After_Councel.this,MainActivity.class);
        maini.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(maini);
    }
}