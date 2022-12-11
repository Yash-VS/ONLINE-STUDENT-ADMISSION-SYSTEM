package com.example.studentadmissionsystem;

public class student {
    public String jee_Score;
    public String email;

    public student(String email, String jee_Score){
        this.email=email;
        this.jee_Score=jee_Score;
    }

    public String getEmail() {
        return email;
    }

    public String getJee_Score() {
        return jee_Score;
    }
}
