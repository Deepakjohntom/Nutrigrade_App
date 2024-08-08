package com.example.nutrigrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Feedback extends AppCompatActivity {

    RecyclerView r1;
    LinearLayout v1;
    ProgressDialog progressDoalog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        v1 = findViewById(R.id.norecord_feedback_list);
        r1 = findViewById(R.id.feedback_list);
        try {
            APIService service = Connection.getcon().create(APIService.class);
            Map<String, String> userData = new HashMap<>();
            SharedPreferences sharedPreferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE);
            String log_id = sharedPreferences.getString("log_id", null);
            userData.put("user_id", log_id);
//            Url.p(getApplicationContext(),log_id);
            Call<List<Feedbackdata>> call = service.viewfeedback(userData);
            Log.i("call", call.toString());
            //Api response
            call.enqueue(new Callback<List<Feedbackdata>>() {
                @Override
                public void onResponse(@NonNull Call<List<Feedbackdata>> call, @NonNull Response<List<Feedbackdata>> response) {
                    Log.i("onResponse", response.message());
                    try {
                        List<Feedbackdata> userList = response.body();
                        if (userList == null) {
                            Log.i("Hit :", "userList == null");
                            v1.setVisibility(View.VISIBLE);
                            r1.setVisibility(View.GONE);
                        } else{
//                            for (Feedbackdata n : userList) {
//                                                Url.p(getApplicationContext(),n.getResult());
//                                if(n.getResult().contains("Success")) {
//                                    Log.i("Hit :", n.getName());
                                    r1.setVisibility(View.VISIBLE);
                                    v1.setVisibility(View.GONE);
                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                                    r1.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

                                    //  Log.i("autolog", "recyclerView.setLayoutManager(layoutManager);");

                                    FeedbackAdapter recyclerViewAdapter = new FeedbackAdapter(Feedback.this, userList);
                                    // Log.i("autolog", "RecyclerViewAdapter recyclerViewAdapter =new RecyclerViewAdapter(getApplicationContext(), userList);");
                                    r1.setAdapter(recyclerViewAdapter);
//                                    break;
//                                } else if (n.getResult().contains("Empty")) {
//                                    r1.setVisibility(View.GONE);
//                                    v1.setVisibility(View.VISIBLE);
//                                }
//                            }

                        }
//                            }else{
//                                    Url.p(getApplicationContext(),"userlist empty");
//                                }
//                                progressDoalog.dismiss();
//                                Url.p(getApplicationContext(),"ds4");

                    } catch (Exception e) {
//                                progressDoalog.dismiss();
                                Url.p(getApplicationContext(), "Something went wrong : " + e);
//                        Url.p(getApplicationContext(), "Some error occured, please try again.");

                    }
                    //  Log.i("autolog", "List<User> userList = response.body();");

                    //  Log.i("autolog", "recyclerView.setAdapter(recyclerViewAdapter);");
                }

                @Override
                public void onFailure(Call<List<Feedbackdata>> call, Throwable t) {
                            Url.p(getApplicationContext(), t.getMessage());
//                    Url.p(getApplicationContext(), "Some error occured, please try again.");
                }
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}