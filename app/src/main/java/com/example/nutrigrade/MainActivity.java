package com.example.nutrigrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDoalog;
    String usernametext,passwordtext;
    TextInputLayout username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    public void login(View view) throws Exception {

        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Sending Data..");
        progressDoalog.setTitle("Nutrigrade");

        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progressDoalog.show();

        TextInputEditText uname = (TextInputEditText) username.getEditText();
        if (uname != null) {
            usernametext = uname.getText().toString();
            // Use the 'text' variable containing the retrieved text
        }

        TextInputEditText textInputEditText2 = (TextInputEditText) password.getEditText();
        if (textInputEditText2 != null) {
            passwordtext = textInputEditText2.getText().toString();
            // Use the 'text' variable containing the retrieved text
        }

        APIService service = Connection.getcon().create(APIService.class);
        Map<String, String> userData = new HashMap<>();
        userData.put("username", usernametext);
        userData.put("password", passwordtext);

        Call<List<Logindata>> call = service.login(userData);
        Log.i("Hit Check: ", "Success");

        call.enqueue(new Callback<List<Logindata>>() {
            @Override
            public void onResponse(@NonNull Call<List<Logindata>> call, @NonNull Response<List<Logindata>> response) {
                Log.i("onResponse", response.message());
                try {
                    List<Logindata> userList = response.body();
                    for (Logindata n : userList) {
                        if (n.getResult().contains("Success")) {

                            Log.i("username: ", n.getUsername());

                            // Save credentials
                            Context context = view.getContext(); // Obtain the Context from the View
//                                    SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
                            SharedPreferences sharedPreferences = context.getSharedPreferences("SESSION", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            // Set Session data
                            editor.putString("name", n.getName());
                            editor.putString("username", n.getUsername());
                            editor.putString("log_id", n.getId());
                            editor.putString("token", n.getToken());
                            editor.apply();

                            Url.p(getApplicationContext(), "Welcome, "+ n.getUsername());
                            Intent i = new Intent(getApplicationContext(), Home.class);
                            startActivity(i);

                        } else if (n.getResult().contains("Failed")) {
                            Url.p(getApplicationContext(), "Incorrect username or password");
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
                    Url.p(getApplicationContext(), "Something went wrong : " + e);
//                    Url.p(getApplicationContext(), "Some error occured, please try again.");

                }
                //  Log.i("autolog", "List<User> userList = response.body();");

                //  Log.i("autolog", "recyclerView.setAdapter(recyclerViewAdapter);");
            }

            @Override
            public void onFailure(Call<List<Logindata>> call, Throwable t) {
                Url.p(getApplicationContext(), t.getMessage());
                progressDoalog.dismiss();
//                Url.p(getApplicationContext(), "Some error occured, please try again.");
            }
        });
    }

    public void onDoItNowClicked(View view) {
        Intent i = new Intent(getApplicationContext(), Register.class);
        startActivity(i);
    }
}