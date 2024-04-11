package com.quynhlm.dev.demoonthi1.Api;

import com.quynhlm.dev.demoonthi1.Model.Motorbike;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface ApiServer {
    @GET("/motorbike")
    Call<List<Motorbike>> getAllMotor();
    @DELETE("/motorbike/{id}")
    Call<Void> deleteMotor(@Path("id") String id);
    @Multipart
    @POST("/motorbike/uploadImage")
    Call<Motorbike> createMotorbike(
            @PartMap Map<String, RequestBody> requestBodyMap,
            @Part MultipartBody.Part image
    );
    @Multipart
    @PUT("/motorbike/uploadImage/{id}")
    Call<Motorbike> updateMotorbike(
            @Path("id") String id,
            @PartMap Map<String, RequestBody> requestBodyMap,
            @Part MultipartBody.Part image
    );
}
