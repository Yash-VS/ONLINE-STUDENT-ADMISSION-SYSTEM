package com.example.studentadmissionsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class prefernce_list extends AppCompatActivity {

    Spinner spinner, spinner2, spinner3;

    String email_key = "";
    StringBuilder myName;

    String p1 = "", p2 = "", p3 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_prefernce_list);

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

        spinner = findViewById(R.id.spinner_1);
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.brew_array,
                        android.R.layout.simple_spinner_item);

        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(staticAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                 switch (position) {
                    case 0:{
                        p1 = "cse";
                        break;
                    }

                    case 1:{
                        // Whatever you want to happen when the second item gets selected
                        p1 = "cce";
                        break;
                    }

                    case 2:{
                        p1 = "ece";
                        break;
                    }

                    case 3:{
                        p1 = "mme";
                        break;
                     }

                     case 4: {
                         p1  = "dual_cse";
                         break;
                     }

                     case 5: {
                         p1 = "dual_ece";
                         break;
                     }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        spinner2 = findViewById(R.id.spinner_2);
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter2 = ArrayAdapter
                .createFromResource(this, R.array.brew_array,
                        android.R.layout.simple_spinner_item);

        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(staticAdapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                switch (position) {
                    case 0:{
                        p2 = "cse";
                        break;
                    }

                    case 1:{
                        // Whatever you want to happen when the second item gets selected
                        p2 = "cce";
                        break;
                    }

                    case 2:{
                        p2 = "ece";
                        break;
                    }

                    case 3:{
                        p2 = "mme";
                        break;
                    }

                    case 4: {
                        p2  = "dual_cse";
                        break;
                    }

                    case 5: {
                        p2 = "dual_ece";
                        break;
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        spinner3 = findViewById(R.id.spinner_3);
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter3 = ArrayAdapter
                .createFromResource(this, R.array.brew_array,
                        android.R.layout.simple_spinner_item);

        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(staticAdapter);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                switch (position) {
                    case 0:{
                        p3 = "cse";
                        break;
                    }

                    case 1:{
                        // Whatever you want to happen when the second item gets selected
                        p3 = "cce";
                        break;
                    }

                    case 2:{
                        p3 = "ece";
                        break;
                    }

                    case 3:{
                        p3 = "mme";
                        break;
                    }

                    case 4: {
                        p3  = "dual_cse";
                        break;
                    }

                    case 5: {
                        p3 = "dual_ece";
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

    }

    public void goBack(View view){
        Intent intent = new Intent(prefernce_list.this, During_Councel.class);
        startActivity(intent);
    }

    public void onClick(View view){
        HashMap<String,Object> m = new HashMap<String, Object>();
        m.put("pref_1",p1);
        m.put("pref_2",p2);
        m.put("pref_3",p3);
        Toast.makeText(prefernce_list.this, "Prefernces Submit Successfully!!", Toast.LENGTH_SHORT).show();
        FirebaseDatabase.getInstance().getReference("course_pref").child(myName.toString()).setValue(m);
    }


}