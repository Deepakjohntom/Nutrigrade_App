package com.example.nutrigrade;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Connection {


    public static Retrofit getcon() throws Exception {
        Retrofit retrofit1 = null;
        if (retrofit1 == null) {
            retrofit1 = new Retrofit.Builder()
                    .baseUrl(Url.url)
                    .client(Url.okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
//                Log.i("autolog", "build();");
        }
        return retrofit1;
    }
}
