package com.example.nutrigrade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Viewfeedback extends AppCompatActivity {

    TextView feedback_productname,feedback_feedback,feedback_reply;
    String id,name,feedback,user_id,product_id,ratings,reply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewfeedback);

        Intent i= getIntent();
        id=i.getStringExtra("id");
        name=i.getStringExtra("name");
        feedback=i.getStringExtra("feedback");
        user_id=i.getStringExtra("user_id");
        product_id=i.getStringExtra("product_id");
        ratings=i.getStringExtra("ratings");
        reply=i.getStringExtra("reply");

//        Url.p(getApplicationContext(),reply);
        feedback_productname = findViewById(R.id.feedback_productname);
        feedback_feedback = findViewById(R.id.feedback);
        feedback_reply = findViewById(R.id.reply);

        feedback_productname.setText(name);
        feedback_feedback.setText(feedback);
        feedback_reply.setText(reply);

    }
}