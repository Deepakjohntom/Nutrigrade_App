package com.example.nutrigrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Productdetails extends AppCompatActivity {

    String id,name,details,image,grade,feedback,ingredients,prons,cons,calorie;
    TextView nametext,descriptiontext,prodgrade,ingredientstextview,pronstextview,constextview,calorietextview;
    TextInputLayout feedbacklayout;
    ImageView prodimage;
    ProgressDialog progressDoalog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);

        //feedback
        feedbacklayout = findViewById(R.id.feedbackinput);

        Intent i= getIntent();
        id=i.getStringExtra("prod_id");
        name=i.getStringExtra("prod_name");
        details=i.getStringExtra("prod_details");
        ingredients=i.getStringExtra("prod_ingredients");
        prons=i.getStringExtra("prod_pros");
        cons=i.getStringExtra("prod_cons");
        calorie=i.getStringExtra("prod_calorie");
        image=i.getStringExtra("prod_image");
        grade=i.getStringExtra("prod_grade");

        nametext = findViewById(R.id.name);
        descriptiontext = findViewById(R.id.details);
        prodimage = findViewById(R.id.prod_image);
        prodgrade = findViewById(R.id.prod_grade);
        ingredientstextview = findViewById(R.id.ingredients);
        pronstextview = findViewById(R.id.pros);
        constextview = findViewById(R.id.cons);
        calorietextview = findViewById(R.id.calorie);

        int radius = 30; // You can adjust the radius as needed
        RoundedCorners roundedCorners = new RoundedCorners(radius);
        String prodimageUrl = Url.url+"storage/"+image;
        Glide.with(this)
                .load(prodimageUrl)
                .transform(new CenterCrop(), roundedCorners) // Apply CenterCrop and RoundedCorners transformations
//                .placeholder(R.drawable.deepfakelogo) // Placeholder image while loading
                .error(R.drawable.error)
                .into(prodimage);
//        String prodgradeUrl = Url.url+"productimages/"+grade;
//        Glide.with(this)
//                .load(prodgradeUrl)
////                .transform(new CenterCrop(), roundedCorners) // Apply CenterCrop and RoundedCorners transformations
////                .placeholder(R.drawable.deepfakelogo) // Placeholder image while loading
//                .error(R.drawable.error)
//                .into(prodgrade);

        nametext.setText(name);
        descriptiontext.setText(details);
        prodgrade.setText("Grade: "+grade);
        ingredientstextview.setText("Ingredients: "+ingredients);
        pronstextview.setText("Pros: "+prons);
        constextview.setText("Cons: "+cons);
        calorietextview.setText("Calorie: "+calorie);


/////////////////////////Old COde for Saving IDs to array sharedpreference
        // Retrieve the existing array of product IDs from SharedPreferences
//        SharedPreferences sharedPreferences = getSharedPreferences("product_ids", Context.MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("product_ids", null);
//        int[] productIds;
//        if (json != null) {
//            productIds = gson.fromJson(json, int[].class);
//        } else {
//            productIds = new int[0]; // Initialize an empty array if no data exists
//        }
//        int parsedID = Integer.parseInt(id);
//        if (!containsId(productIds, parsedID)) {
//            int[] newProductIds = Arrays.copyOf(productIds, productIds.length + 1);
//            newProductIds[productIds.length] = parsedID;
//
//            // Save the updated array back to SharedPreferences
//            String newJson = gson.toJson(newProductIds);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("product_ids", newJson);
////            editor.remove("product_ids"); //to remove the key in shared preference
//            editor.apply();
//        }


    }

    public void givefeedback(View view) {
//        Url.p(getApplicationContext()," "+id);
        TextInputEditText feedbacklayoutEditText = (TextInputEditText) feedbacklayout.getEditText();
        if (feedbacklayoutEditText != null) {
            feedback = feedbacklayoutEditText.getText().toString();
            // Use the 'text' variable containing the retrieved text
        }
        try {

            progressDoalog = new ProgressDialog(Productdetails.this);

            progressDoalog.setMessage("Sending Data..");
            progressDoalog.setTitle("Nutrigrade is");

            progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            progressDoalog.show();

// Username Validation
            boolean feedbackstat = false;
            String feedbackval = feedback.trim();
            if (feedbackval.isEmpty()) {
                // Username field is empty or does not start with a letter
                feedbacklayout.setError("Type your message to give feedback.");
                progressDoalog.dismiss();
                feedbacklayout.requestFocus();
            } else {
                feedbackstat = true;
                feedbacklayout.setError(null);
            }

            if (feedbackstat) {

                // Get saved credentials
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("SESSION", Context.MODE_PRIVATE);
                String session_log_id = sharedPreferences.getString("log_id", null);
                //Call API
                APIService service = Connection.getcon().create(APIService.class);
                Map<String, String> userData = new HashMap<>();
                userData.put("feedback", feedback);
                userData.put("userId", session_log_id);
                userData.put("id", id);

                Call<List<Feedbackdata>> call = service.feedback(userData);
                Log.i("Hit Check: ", "Success");

                call.enqueue(new Callback<List<Feedbackdata>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Feedbackdata>> call, @NonNull Response<List<Feedbackdata>> response) {
                        Log.i("onResponse", response.message());
                        try {
                            List<Feedbackdata> userList = response.body();
                            for (Feedbackdata n : userList) {
                                if (n.getResult().contains("Success")) {

//                                    Url.p(getApplicationContext(), "Registered successfully.");
                                    Url.p(getApplicationContext(), "Your feedback is submitted.");

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
//                            Url.p(getApplicationContext(), "Something went wrong : " + e);
                            Url.p(getApplicationContext(), "Some error occured, please try again.");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Feedbackdata>> call, Throwable t) {
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

    // Function to check if an ID already exists in the array
    private boolean containsId(int[] array, int id) {
        for (int value : array) {
            if (value == id) {
                return true;
            }
        }
        return false;
    }

    public void downloadproductfile(View view) {
        //////////Add product details to array in sharedpreference like map
        // Retrieve the existing array of product IDs from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("product_ids", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("product_ids", null);
        Productdata[] productArray;
        if (json != null) {
            Type type = new TypeToken<Productdata[]>() {}.getType();
            productArray = gson.fromJson(json, type);
        } else {
            productArray = new Productdata[0]; // Initialize an empty array if no data exists
        }

// Check if Productdata details are already present in the array
        int parsedID = Integer.parseInt(id);
        boolean productExists = false;
        for (Productdata product : productArray) {
            if (Integer.parseInt(product.getId()) == parsedID) {
                // Product details already exist in the array
                productExists = true;
                break;
            }
        }

// Add product details to the array if it doesn't already exist
        if (!productExists) {
            Productdata product = new Productdata(id, name, grade, image);
            Productdata[] newProductArray = Arrays.copyOf(productArray, productArray.length + 1);
            newProductArray[productArray.length] = product;

            // Save the updated array back to SharedPreferences
            String newJson = gson.toJson(newProductArray);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("product_ids", newJson);
            editor.apply();
            Log.i("shared prod"," "+newJson);
        }
        String url = Url.url+"download/"+id; // Replace this with the desired URL
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}