package com.example.nutrigrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    String username,email,password,phone;
    TextInputLayout usernameinput,emailinput,passwordinput,phoneinput;

    ProgressDialog progressDoalog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameinput = findViewById(R.id.username);
        passwordinput = findViewById(R.id.password);
        emailinput = findViewById(R.id.name);
        phoneinput = findViewById(R.id.phone);

    }

    public void register(View view) {

        TextInputEditText usernametext = (TextInputEditText) usernameinput.getEditText();
        if (usernametext != null) {
            username = usernametext.getText().toString();
            // Use the 'text' variable containing the retrieved text
        }
        TextInputEditText emailtext = (TextInputEditText) emailinput.getEditText();
        if (emailtext != null) {
            email = emailtext.getText().toString();
            // Use the 'text' variable containing the retrieved text
        }
        TextInputEditText passwordtext = (TextInputEditText) passwordinput.getEditText();
        if (passwordtext != null) {
            password = passwordtext.getText().toString();
            // Use the 'text' variable containing the retrieved text
        }
        TextInputEditText phonetext = (TextInputEditText) phoneinput.getEditText();
        if (phonetext != null) {
            phone = phonetext.getText().toString();
            // Use the 'text' variable containing the retrieved text
        }

        if(username.equals(""))
        {
            Url.p(getApplicationContext(),"Must fill this field");
            usernameinput.requestFocus();
        }
        else if (email.equals("")){
            Url.p(getApplicationContext(),"Must fill this field");
            emailinput.requestFocus();
        }
        else if(password.equals("")){
            Url.p(getApplicationContext(),"Must fill this field");
            passwordinput.requestFocus();
        }
        else if(phone.equals("")){
            Url.p(getApplicationContext(),"Must fill this field");
            phoneinput.requestFocus();
        }else{

            try {

                progressDoalog = new ProgressDialog(Register.this);

                progressDoalog.setMessage("Sending Data..");
                progressDoalog.setTitle("Nutrigrade is");

                progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                progressDoalog.show();


////////////////Validation
                // Email Validation
                boolean emailstat = false;
                String emailval = email.trim();
//                if (!Patterns.EMAIL_ADDRESS.matcher(emailval).matches()) {
                if (emailval.isEmpty() || !emailval.matches("^[a-zA-Z]*$")){
                    // Invalid email format
                    emailinput.setError("Name must only contain alphabets format");
                    progressDoalog.dismiss();
                    emailinput.requestFocus();
                } else {
                    emailstat = true;
                    emailinput.setError(null);
                }

// Password Validation
                boolean passwordstat = false;
                String passwordval = password.trim();
                if (passwordval.length() < 6) {
                    // Password length less than 6 characters
                    passwordinput.setError("Password must be at least 6 characters long");
                    progressDoalog.dismiss();
                    passwordinput.requestFocus();
                } else {
                    passwordstat = true;
                    passwordinput.setError(null);
                }

// Username Validation
                boolean usernamestat = false;
                String usernameval = username.trim();
                if (username.isEmpty() || !usernameval.matches("^[a-zA-Z][a-zA-Z0-9]*$")) {
                    // Username field is empty or does not start with a letter
                    usernameinput.setError("Username must start with a letter and can contain letters, numbers, and characters");
                    progressDoalog.dismiss();
                    usernameinput.requestFocus();
                } else {
                    usernamestat = true;
                    usernameinput.setError(null);
                }
                // Phone Number Validation for Indian Numbers
                boolean phonestat = false;
                String phoneNumber = phone.trim();
//                if (phoneNumber.isEmpty() || !phoneNumber.matches("^[+][0-9]{10}$")) {
                if (phoneNumber.isEmpty() || !phoneNumber.matches("^[0-9]{10}$")) {
                    // Phone number field is empty or does not match the Indian phone number format
//                    phoneinput.setError("Please enter a valid Indian phone number starting with a plus sign and followed by 12 digits");
                    phoneinput.setError("Please enter a valid Indian phone number of 10 digits");
                    progressDoalog.dismiss();
                    phoneinput.requestFocus();
                } else {
                    phonestat = true;
                    phoneinput.setError(null);
                }
                if (emailstat && passwordstat && usernamestat && phonestat) {
                    //Call API
                    APIService service = Connection.getcon().create(APIService.class);
                    Map<String, String> userData = new HashMap<>();
                    userData.put("username", username);
                    userData.put("name", email);
                    userData.put("password", password);
                    userData.put("phone", phone);

                    Call<List<Logindata>> call = service.register(userData);
                    Log.i("Hit Check: ", "Success");

                    call.enqueue(new Callback<List<Logindata>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<Logindata>> call, @NonNull Response<List<Logindata>> response) {
                            Log.i("onResponse", response.message());
                            try {
                                List<Logindata> userList = response.body();
                                for (Logindata n : userList) {
                                    if (n.getResult().contains("Success")) {

                                        Url.p(getApplicationContext(), "Registered successfully.");
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);

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
//                                Url.p(getApplicationContext(), "Something went wrong : " + e);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Logindata>> call, Throwable t) {
//                            Url.p(getApplicationContext(), t.getMessage());
                            Url.p(getApplicationContext(), "Some error occured, please try again.");
                            progressDoalog.dismiss();
                        }
                    });
                }
            } catch(Exception e){
                progressDoalog.dismiss();
                e.printStackTrace();
            }
        }
    }
}