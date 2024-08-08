package com.example.nutrigrade;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIService {
    @FormUrlEncoded
    @POST("api/register")
    Call<List<Logindata>> register(@FieldMap Map<String, String> userData);
    @FormUrlEncoded
    @POST("api/login")
    Call<List<Logindata>> login(@FieldMap Map<String, String> userData);
    @FormUrlEncoded
    @POST("api/product/search")
    Call<List<Productdata>> product_search(@FieldMap Map<String, String> userData);
    @FormUrlEncoded
    @POST("api/product/addfeedback")
    Call<List<Feedbackdata>> feedback(@FieldMap Map<String, String> userData);
    @FormUrlEncoded
    @POST("api/viewfeedback")
    Call<List<Feedbackdata>> viewfeedback(@FieldMap Map<String, String> userData);
    @FormUrlEncoded
    @POST("api/download_products")
    Call<List<Productdata>> download_products(@FieldMap Map<String, String> userData);
    @FormUrlEncoded
    @POST("api/calorie")
    Call<List<Caloriedata>> calculateCalorie(@FieldMap Map<String, String> userData);

}
