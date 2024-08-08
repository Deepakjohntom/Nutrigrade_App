package com.example.nutrigrade;

import android.content.Context;
import android.widget.Toast;

import okhttp3.OkHttpClient;

public class Url {
    public static void p(Context c, String m){
        Toast.makeText(c,m,Toast.LENGTH_SHORT).show();
    }
    //public static String url="http://192.168.1.7/detail/";

    public static String url="http://192.168.199.107:8000/";
    public static OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
}
