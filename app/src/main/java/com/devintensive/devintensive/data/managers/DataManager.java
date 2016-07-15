package com.devintensive.devintensive.data.managers;


import android.content.Context;

import com.devintensive.devintensive.data.network.RestService;
import com.devintensive.devintensive.data.network.ServiceGenerator;
import com.devintensive.devintensive.data.network.req.UserLoginReq;
import com.devintensive.devintensive.data.network.res.UserListRes;
import com.devintensive.devintensive.data.network.res.UserModelRes;
import com.devintensive.devintensive.utils.DevintensiveApplication;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class DataManager {

    private static DataManager INSTANCE = null;

    private PreferencesManager mPreferencesManager;
    private Context mContext;
    private RestService mRestService;

    public DataManager(){
        this.mPreferencesManager = new PreferencesManager();
        this.mContext = DevintensiveApplication.getContext();
        this.mRestService = ServiceGenerator.createService(RestService.class);
    }
    public static DataManager getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }

    public Context getContext(){
        return mContext;
    }
    public PreferencesManager getPreferencesManager() {
        return mPreferencesManager;
    }

    //region ========= Network ===========
    public Call<UserModelRes> loginUser(UserLoginReq userLoginReq){
        return mRestService.loginUser(userLoginReq);
    }

    public Call<UserListRes> getUserList(){
        return mRestService.getUserList();
    }

    public RestService getRestService() {
        return mRestService;
    }

    public Call<UserModelRes> loginToken(String userId){
        return mRestService.loginToken(userId);
    }
   /* public Call<ResponseBody> photoToServer(String userId, MultipartBody.Part file){
        return mRestService.uploadPhoto(userId, file);
    }*/
    //endregion

    //region ========= Database ===========

//endregion
}
