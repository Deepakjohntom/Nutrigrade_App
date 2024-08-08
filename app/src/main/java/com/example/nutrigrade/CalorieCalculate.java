package com.example.nutrigrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalorieCalculate extends AppCompatActivity {

    TextInputLayout heightlayout,weightlayout,agelayout,genderlayout;
    String height,weight,age,gender;
    TextView responsetext;
    ProgressDialog progressDoalog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_calculate);

        heightlayout = findViewById(R.id.height);
        weightlayout = findViewById(R.id.weight);
        agelayout = findViewById(R.id.age);
        genderlayout = findViewById(R.id.gender);
        responsetext = findViewById(R.id.response);

        responsetext.setVisibility(View.GONE);
    }

    public void calculate(View view) {
        TextInputEditText heightlayoutEditText = (TextInputEditText) heightlayout.getEditText();
        if (heightlayoutEditText != null) {
            height = heightlayoutEditText.getText().toString();
            // Use the 'text' variable containing the retrieved text
        }
        TextInputEditText weightlayoutEditText = (TextInputEditText) weightlayout.getEditText();
        if (weightlayoutEditText != null) {
            weight = weightlayoutEditText.getText().toString();
            // Use the 'text' variable containing the retrieved text
        }
        TextInputEditText agelayoutEditText = (TextInputEditText) agelayout.getEditText();
        if (agelayoutEditText != null) {
            age = agelayoutEditText.getText().toString();
            // Use the 'text' variable containing the retrieved text
        }
        TextInputEditText genderlayoutEditText = (TextInputEditText) genderlayout.getEditText();
        if (genderlayoutEditText != null) {
            gender = genderlayoutEditText.getText().toString();
            // Use the 'text' variable containing the retrieved text
        }
        try {

            progressDoalog = new ProgressDialog(CalorieCalculate.this);

            progressDoalog.setMessage("Sending Data..");
            progressDoalog.setTitle("Nutrigrade is");

            progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            progressDoalog.show();

// Username Validation
            boolean heightstat = false;
            String heightval = height.trim();
            if (height.isEmpty() || !heightval.matches("^[0-9]*$")) {
                // Username field is empty or does not start with a letter
                heightlayout.setError("Height must only contain numbers");
                progressDoalog.dismiss();
                heightlayout.requestFocus();
            } else {
                heightstat = true;
                heightlayout.setError(null);
            }
            boolean weightstat = false;
            String weightval = weight.trim();
            if (weight.isEmpty() || !weightval.matches("^[0-9]*$")) {
                // Username field is empty or does not start with a letter
                weightlayout.setError("Weight must only contain numbers");
                progressDoalog.dismiss();
                weightlayout.requestFocus();
            } else {
                weightstat = true;
                weightlayout.setError(null);
            }
            boolean agestat = false;
            String ageval = age.trim();
            if (age.isEmpty() || !ageval.matches("^[0-9]*$")) {
                // Username field is empty or does not start with a letter
                agelayout.setError("Age must only contain numbers");
                progressDoalog.dismiss();
                agelayout.requestFocus();
            } else {
                agestat = true;
                agelayout.setError(null);
            }
            boolean genderstat = false;
            String genderval = gender.trim();
            if (gender.isEmpty()) {
                // Username field is empty or does not start with a letter
                genderlayout.setError("Gender must only contain alphabets");
                progressDoalog.dismiss();
                genderlayout.requestFocus();
            } else {
                genderstat = true;
                genderlayout.setError(null);
            }
            if(Objects.equals(gender, "Male")){
                gender = "1";
            }else if(Objects.equals(gender, "Female")){
                gender = "2";
            }

            if (heightstat && weightstat && agestat && genderstat) {
                //Call API
                APIService service = Connection.getcon().create(APIService.class);
                Map<String, String> userData = new HashMap<>();
                userData.put("weight", weight);
                userData.put("height", height);
                userData.put("age", age);
                userData.put("gender", gender);

                Call<List<Caloriedata>> call = service.calculateCalorie(userData);
                Log.i("Hit Check: ", "Success");

                call.enqueue(new Callback<List<Caloriedata>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Caloriedata>> call, @NonNull Response<List<Caloriedata>> response) {
                        Log.i("onResponse", response.message());
                        try {
                            List<Caloriedata> userList = response.body();
                            for (Caloriedata n : userList) {
                                if (n.getResult().contains("Success")) {
                                    responsetext.setVisibility(View.VISIBLE);
//                                    Url.p(getApplicationContext(), "Registered successfully.");
//                                    Url.p(getApplicationContext(), n.getResponse());
                                    responsetext.setText(n.getResponse());

                                } else if (n.getResult().contains("Failed")) {
                                    Url.p(getApplicationContext(), "Failed to register.");
                                } else if (n.getResult().contains("User already exists")) {
                                    Url.p(getApplicationContext(), "User already exists.");
                                } else if (n.getResult().contains("Empty")) {
                                    Url.p(getApplicationContext(), "Fields cannot be empty.");
                                }
                            }
                            progressDoalog.dismiss();
                            //   Varii.p(getApplicationContext(),"ds4");

                        } catch (Exception e) {
                            progressDoalog.dismiss();
                            Url.p(getApplicationContext(), "Some error occured, please try again.");
//                            Url.p(getApplicationContext(), "Something went wrong : " + e);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Caloriedata>> call, Throwable t) {
//                        Url.p(getApplicationContext(), t.getMessage());
                        progressDoalog.dismiss();
                        Url.p(getApplicationContext(), "Some error occured, please try again.");
                    }
                });
            }
        } catch(Exception e){
            progressDoalog.dismiss();
            e.printStackTrace();
        }
    }

}