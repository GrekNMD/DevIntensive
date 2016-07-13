package com.devintensive.devintensive.data.network;


import com.devintensive.devintensive.data.network.req.UserLoginReq;
import com.devintensive.devintensive.data.network.res.UserModelRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface RestService{

    @POST("login")
    Call<UserModelRes> loginUser (@Body UserLoginReq req);

}
