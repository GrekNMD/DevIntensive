package com.devintensive.devintensive.data.network;


import com.devintensive.devintensive.data.network.req.UserLoginReq;
import com.devintensive.devintensive.data.network.res.UserListRes;
import com.devintensive.devintensive.data.network.res.UserModelRes;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface RestService{

    @POST("login")
    Call<UserModelRes> loginUser (@Body UserLoginReq req);
    @GET("user/{userId}")
    Call<UserModelRes> loginToken(@Path("userId") String userId);

    /*@Multipart
    @POST("user/{userId}/publicValues/profilePhoto")
    Call<UploadPhotoRes> uploadPhoto(@Path("userId") String userId, @Part MultipartBody.Part file);*/

    @GET("user/list?orderBy=rating")
    Call<UserListRes> getUserList();

}
