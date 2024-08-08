package com.example.nutrigrade;

import static androidx.core.widget.TextViewKt.addTextChangedListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.SearchEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    RecyclerView r1;
    LinearLayout v1;
    EditText searchEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        v1 = findViewById(R.id.norecord);
        r1 = findViewById(R.id.list_prod);
        searchEvent = findViewById(R.id.search);
        // Make the TextView editable using SpannableString
        SpannableString spannableString = new SpannableString(searchEvent.getText());
        searchEvent.setText(spannableString);
        searchEvent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do something before the text is changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // React to text changes here
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do something after the text has changed
                String newText = s.toString();
//                Toast.makeText(Home.this, "Text changed to: " + newText, Toast.LENGTH_SHORT).show();
                try {
                    APIService service = Connection.getcon().create(APIService.class);
                    Map<String, String> userData = new HashMap<>();
                    userData.put("key", newText);
                    userData.put("min","1");
                    userData.put("max", "1000");
                    Call<List<Productdata>> call = service.product_search(userData);
                    Log.i("call", call.toString());
                    //Api response
                    call.enqueue(new Callback<List<Productdata>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<Productdata>> call, @NonNull Response<List<Productdata>> response) {
                            Log.i("onResponse", response.message());
                            try {
                                List<Productdata> userList = response.body();
                                    if (userList == null) {
                                        Log.i("Hit :", "userList == null");
                                        v1.setVisibility(View.VISIBLE);
                                        r1.setVisibility(View.GONE);
                                    } else{
                                            for (Productdata n : userList) {
//                                                Url.p(getApplicationContext(),n.getResult());
                                                if(n.getResult().contains("Success")) {
                                                    Log.i("Hit :", n.getName());
                                                    r1.setVisibility(View.VISIBLE);
                                                    v1.setVisibility(View.GONE);
                                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                                                    r1.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

                                                    //  Log.i("autolog", "recyclerView.setLayoutManager(layoutManager);");

                                                    ProductAdapter recyclerViewAdapter = new ProductAdapter(Home.this, userList);
                                                    // Log.i("autolog", "RecyclerViewAdapter recyclerViewAdapter =new RecyclerViewAdapter(getApplicationContext(), userList);");
                                                    r1.setAdapter(recyclerViewAdapter);
                                                    break;
                                                } else if (n.getResult().contains("Empty")) {
                                                    r1.setVisibility(View.GONE);
                                                    v1.setVisibility(View.VISIBLE);
                                                }
                                            }

                                    }
//                            }else{
//                                    Url.p(getApplicationContext(),"userlist empty");
//                                }
//                                progressDoalog.dismiss();
//                                Url.p(getApplicationContext(),"ds4");

                            } catch (Exception e) {
//                                progressDoalog.dismiss();
//                                Url.p(getApplicationContext(), "Something went wrong : " + e);
                                Url.p(getApplicationContext(), "Some error occured, please try again.");

                            }
                            //  Log.i("autolog", "List<User> userList = response.body();");

                            //  Log.i("autolog", "recyclerView.setAdapter(recyclerViewAdapter);");
                        }

                        @Override
                        public void onFailure(Call<List<Productdata>> call, Throwable t) {
//                            Url.p(getApplicationContext(), t.getMessage());
                            Url.p(getApplicationContext(), "Some error occured, please try again.");
                        }
                    });

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    public void profile(View view) {
        Intent i = new Intent(getApplicationContext(), Profile.class);
        startActivity(i);
    }
}