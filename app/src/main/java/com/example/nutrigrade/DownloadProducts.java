package com.example.nutrigrade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadProducts extends AppCompatActivity {

    RecyclerView r1;
    LinearLayout v1;
    ProgressDialog progressDoalog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_products);

        r1 = findViewById(R.id.download_products);
        v1= findViewById(R.id.norecord);

        // Retrieve the SharedPreferences object
        SharedPreferences sharedPreferences = getSharedPreferences("product_ids", Context.MODE_PRIVATE);
// Retrieve the product data as a string
        String productDataString = sharedPreferences.getString("product_ids", null);
        // Parse the product data into a list of Product objects
        List<Productdata> products = new ArrayList<>();
        if (productDataString != null) {
            Type type = new TypeToken<List<Productdata>>() {}.getType();
            products = new Gson().fromJson(productDataString, type);
        }
// Display the product data using a loop
        for (Productdata product : products) {
            Log.d("Product Details", "ID: " + product.getId() + ", Name: " + product.getName() + ", Grade: " + product.getGrade()+", Image: "+product.getImage());
        }
        if (products.isEmpty()) {
            Log.i("Hit :", "userList == null");
            v1.setVisibility(View.VISIBLE);
            r1.setVisibility(View.GONE);
        } else{
            Log.i("Hit :", "userList exists");
            r1.setVisibility(View.VISIBLE);
            v1.setVisibility(View.GONE);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
            r1.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

            //  Log.i("autolog", "recyclerView.setLayoutManager(layoutManager);");

            DownloadProductAdapter recyclerViewAdapter = new DownloadProductAdapter(getApplicationContext(), products);
            // Log.i("autolog", "RecyclerViewAdapter recyclerViewAdapter =new RecyclerViewAdapter(getApplicationContext(), userList);");
            r1.setAdapter(recyclerViewAdapter);

        }

//        try {
//
////            progressDoalog = new ProgressDialog(this.getApplicationContext());
////
////            progressDoalog.setMessage("Sending Data..");
////            progressDoalog.setTitle("MY APP NAME");
////
////            progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//
////            progressDoalog.show();
//            Log.i("Hit AddCOmp2:","success");
//            APIService service=Connection.getcon().create(APIService.class);
//            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("SESSION", Context.MODE_PRIVATE);
//            String log_id = sharedPreferences.getString("log_id", null);
//            Map<String, String> userData = new HashMap<>();
//            userData.put("log_id", String.valueOf(1));
//            Call<List<Productdata>> call = service.download_products(userData);
//            Log.i("Hit AddCOmp3:","success");
//            call.enqueue(new Callback<List<Productdata>>() {
//                @Override
//                public void onResponse(Call<List<Productdata>> call, Response<List<Productdata>> response) {
//                    // Log.i("onResponse", response.message());
//                    Log.i("Hit :","onresponse");
//                    try {
//                        List<Productdata> userList = response.body();
//                        Log.i("Hit :","userList");
////                        for (Productdata n : userList) {
//
//                        if (userList == null) {
//                            Log.i("Hit :", "userList == null");
//                            v1.setVisibility(View.VISIBLE);
//                            r1.setVisibility(View.GONE);
//                        } else{
//                            Log.i("Hit :", "userList exists");
//                            r1.setVisibility(View.VISIBLE);
//                            v1.setVisibility(View.GONE);
//                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
//                            r1.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
//
//                            //  Log.i("autolog", "recyclerView.setLayoutManager(layoutManager);");
//
//                            ProductAdapter recyclerViewAdapter = new ProductAdapter(getApplicationContext(), userList);
//                            // Log.i("autolog", "RecyclerViewAdapter recyclerViewAdapter =new RecyclerViewAdapter(getApplicationContext(), userList);");
//                            r1.setAdapter(recyclerViewAdapter);
//
//                        }
////                        }
////                        progressDoalog.dismiss();
//                        //   Urli.p(getApplicationContext(),"ds4");
//
//                    }catch(Exception e)
//                    {
////                        progressDoalog.dismiss();
//                        Url.p(getApplicationContext(),"Something went wrong : "+e);
//
//                    }
//                    //  Log.i("autolog", "List<User> userList = response.body();");
//
//                    //  Log.i("autolog", "recyclerView.setAdapter(recyclerViewAdapter);");
//                }
//
//                @Override
//                public void onFailure(Call<List<Productdata>> call, Throwable t) {
//                    Url.p(getApplicationContext(), t.getMessage());
////                    Url.p(context.getApplicationContext(), "Bad Network..! please try again later");
////                    progressDoalog.dismiss();
//                }
//            });
//
//        } catch (Exception e) {
////            progressDoalog.dismiss();
//            e.printStackTrace();
//        }

    }

    public void downloadproduct(View view) {
        Url.p(getApplicationContext(),"downloadproduct");
    }
}