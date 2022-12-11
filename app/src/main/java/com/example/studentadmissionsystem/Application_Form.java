package com.example.studentadmissionsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Application_Form extends AppCompatActivity {

    EditText username,fathername,mothername,date,phnNo,schoolname,tenthper,twelveper,jeescore,jeephy,jeechem,jeemath,jeeadv,description,emai;
    Button button;
    ImageView back;
    private FirebaseAuth mAuth;

    String email_key = "";
    StringBuilder myName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_application_form);


        username = findViewById(R.id.Name_App);
        fathername = findViewById(R.id.Father_Name_App);
        mothername = findViewById(R.id.Mother_Name_App);
        date = findViewById(R.id.date);
        phnNo = findViewById(R.id.land_area);
        schoolname = findViewById(R.id.School_name);
        tenthper = findViewById(R.id.tenth_percent);
        twelveper = findViewById(R.id.twelth_percent);
        jeescore = findViewById(R.id.JEE_SCORE);
        jeephy = findViewById(R.id.JEE_SCORE_Physics);
        jeechem = findViewById(R.id.JEE_SCORE_Chemistry);
        jeemath = findViewById(R.id.JEE_SCORE_Maths);
        jeeadv = findViewById(R.id.JEE_Advance_rank);
        description = findViewById(R.id.description);
        button = findViewById(R.id.add_btn);


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

        Log.d("gg",email_key);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(phnNo.getText().toString().length() != 10){
                    Toast.makeText(Application_Form.this, "Phone No should be less than 10", Toast.LENGTH_SHORT).show();
                }
                else if(username.getText().toString().equals("") || jeescore.getText().toString().equals("")){
                    Toast.makeText(Application_Form.this, "Enter All Details!", Toast.LENGTH_SHORT).show();

                }

                else{
                    HashMap<String,Object> m = new HashMap<String, Object>();
                    m.put("username",username.getText().toString());
                    m.put("fathername",fathername.getText().toString());
                    m.put("mothername",mothername.getText().toString());
                    m.put("date",date.getText().toString());
                    m.put("phnNo",phnNo.getText().toString());
                    m.put("schoolname",schoolname.getText().toString());
                    m.put("tenthper",tenthper.getText().toString());
                    m.put("twelthper",twelveper.getText().toString());
                    m.put("jeescore",jeescore.getText().toString());
                    m.put("jeephy",jeephy.getText().toString());
                    m.put("jeechem",jeechem.getText().toString());
                    m.put("jeemath",jeemath.getText().toString());
                    m.put("jeeadv",jeeadv.getText().toString());
                    m.put("description",description.getText().toString());
//                database = FirebaseDatabase.getInstance();
//                databaseReference = database.getReference("student_admission").child("user");
//                databaseReference.setValue("start here");
                    FirebaseDatabase.getInstance().getReference("student_data").child(myName.toString()).setValue(m);
                    Toast.makeText(Application_Form.this, "Your Application Form Has Been Submitted!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void goBack(View view){
        Intent intent = new Intent(Application_Form.this, BeforeCouncellingHome.class);
        startActivity(intent);
    }
}