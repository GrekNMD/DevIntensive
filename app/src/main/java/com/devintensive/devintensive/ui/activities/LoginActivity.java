package com.devintensive.devintensive.ui.activities;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.devintensive.devintensive.R;
import com.devintensive.devintensive.data.managers.DataManager;
import com.devintensive.devintensive.data.network.req.UserLoginReq;
import com.devintensive.devintensive.data.network.res.UserModelRes;
import com.devintensive.devintensive.utils.NetworkStatusCheker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button mSignIn;
    private TextView mRememberPassword;
    private EditText mLogin,mPassword;
    private CoordinatorLayout mCoordinatorLayout;

    private DataManager mDataManager;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_login);

        mDataManager = DataManager.getInstance();

        mSignIn = (Button) findViewById(R.id.login_btn);
        mRememberPassword = (TextView) findViewById(R.id.remember_txt);
        mLogin = (EditText) findViewById(R.id.login_email_et);
        mPassword = (EditText) findViewById(R.id.login_password_et);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_container);


        mSignIn.setOnClickListener(this);
        mRememberPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.login_btn:
                signIn();
                break;
            case R.id.remember_txt:
                rememberPassword();
                break;
        }

    }

    private void showSnackbar(String message){
        Snackbar.make(mCoordinatorLayout,message,Snackbar.LENGTH_LONG).show();
    }

    private void rememberPassword(){
        Intent rememberIntent = new Intent (Intent.ACTION_VIEW, Uri.parse("http://devintensive.softdesign-apps.ru/forgotpass"));
        startActivity(rememberIntent);
    }

    /*private void loginSuccess(Response<UserModelRes> response){

        showSnackbar(response.body().getData().getToken());
        mDataManager.getPreferencesManager().saveAuthToken(response.body().getData().getToken());
        mDataManager.getPreferencesManager().saveUserId(response.body().getData().getUser().getId());

        Intent loginIntent = new Intent(this,MainActivity.class);
        startActivity(loginIntent);
    }*/

    private void loginSuccess(UserModelRes userModel){

        showSnackbar(userModel.getData().getToken());
        mDataManager.getPreferencesManager().saveAuthToken(userModel.getData().getToken());
        mDataManager.getPreferencesManager().saveUserId(userModel.getData().getUser().getId());
        saveUserValues(userModel);
        saveUserValueFields(userModel);

        Intent loginIntent = new Intent(this,MainActivity.class);
        startActivity(loginIntent);
    }


    private void signIn(){
        if(NetworkStatusCheker.isNetworkAvailable(this)){
            Call<UserModelRes> call = mDataManager.loginUser(new UserLoginReq(mLogin.getText().toString(),mPassword.getText().toString()));
            call.enqueue(new Callback<UserModelRes>() {
                @Override
                public void onResponse(Call<UserModelRes> call, Response<UserModelRes> response){
                    if (response.code() == 200) {
                        loginSuccess(response.body());
                    }else if(response.code() == 404){
                        showSnackbar("wrong login or password");
                    }else {
                        showSnackbar("Fire in the hole!!!");
                    }
                }

                @Override
                public void onFailure(Call<UserModelRes> call, Throwable t){
                    // TODO:handle exeption retrofit
                }
            });
        }else {
            showSnackbar("Network not available right now,try again later");
        }

    }

    private void saveUserValues(UserModelRes userModel){
        int[] userValues = {
                userModel.getData().getUser().getProfileValues().getRating(),
                userModel.getData().getUser().getProfileValues().getLinesCode(),
                userModel.getData().getUser().getProfileValues().getProjects()
        };

        mDataManager.getPreferencesManager().saveUserProfileValues(userValues);

    }

    private void saveUserValueFields(UserModelRes userModel){
        List<String> userFields = new ArrayList<String>();
        userFields.add(userModel.getData().getUser().getContacts().getPhone());
        userFields.add(userModel.getData().getUser().getContacts().getEmail());
        userFields.add(userModel.getData().getUser().getContacts().getVk());
        userFields.add(userModel.getData().getUser().getRepositories().getRepo().get(0).getGit());
        userFields.add(userModel.getData().getUser().getPublicInfo().getBio());
        String s = userModel.getData().getUser().getFirstName()+" "+userModel.getData().getUser().getSecondName();

        mDataManager.getPreferencesManager().saveUserProfileDataFields(userFields,s);

    }
}
