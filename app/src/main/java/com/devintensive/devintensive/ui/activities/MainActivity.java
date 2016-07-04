package com.devintensive.devintensive.ui.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.devintensive.devintensive.R;
import com.devintensive.devintensive.data.managers.DataManager;
import com.devintensive.devintensive.utils.ConstantManager;
import com.devintensive.devintensive.utils.RoundImage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG= ConstantManager.TAG_PREFIX+"Main Activity";

    private boolean mCurrentEditMode=false;
    private DataManager mDataManager;
    private ImageView mCalling;
    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;
    private DrawerLayout mNavigationDrawer;
    private FloatingActionButton mFab;
    private EditText mUserPhone,mUserMail,mUserVk,mUserGit,mUserBio;
    private ImageView mImageView;
    private RoundImage mRoundImage;

    private List<EditText> mUserInfoViews;

    //UI interface
    //initializing static parameters
    //@param savedInstanceState - object with parameters saved in Bundle - state UI

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        mDataManager = DataManager.getInstance();
        mCalling = (ImageView)findViewById(R.id.call_img);
        mCoordinatorLayout=(CoordinatorLayout)findViewById(R.id.main_coordinator_container);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mNavigationDrawer=(DrawerLayout)findViewById(R.id.navigation_drawer);
        mFab = (FloatingActionButton)findViewById(R.id.fab);
        mUserPhone=(EditText)findViewById(R.id.phone_et);
        mUserMail=(EditText)findViewById(R.id.email_et);
        mUserVk=(EditText)findViewById(R.id.vk_et);
        mUserGit=(EditText)findViewById(R.id.git_et);
        mUserBio=(EditText)findViewById(R.id.about_et);

        mImageView = (ImageView)findViewById(R.id.avatar_img_view);



        mUserInfoViews = new ArrayList<>();
        mUserInfoViews.add(mUserPhone);
        mUserInfoViews.add(mUserMail);
        mUserInfoViews.add(mUserVk);
        mUserInfoViews.add(mUserGit);
        mUserInfoViews.add(mUserBio);

        mFab.setOnClickListener(this);

        setupToolbar();
        setupDrawer();
        loadUserInfoValue();
       // List<String> test = mDataManager.getPreferencesManager().loadUserProfileData();


        if (savedInstanceState == null){
            //1st time activity start
            //showSnackbar("Its 1st time run");
        }
        else {
            mCurrentEditMode = savedInstanceState.getBoolean(ConstantManager.EDIT_MOD_KEY,false);
            changeEditMode(mCurrentEditMode);
            //activity was created
            //showSnackbar("already created");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            mNavigationDrawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    //metod starts on activity start be for UI is visible
    //
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }


    //metod starts after UI visible
    //here using audio/video/broadcast receiver
    //metod have to be as light as posible
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                showSnackbar("click");
                if (!mCurrentEditMode){
                    changeEditMode(true);
                    mCurrentEditMode=true;
                }else {
                    changeEditMode(false);
                    mCurrentEditMode= false;
                }
               /* showProgress();
                runWithDelay();*/
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawer.isDrawerOpen(GravityCompat.START)){
            mNavigationDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ConstantManager.EDIT_MOD_KEY, mCurrentEditMode);
    }

    private void showSnackbar(String messege){
        Snackbar.make(mCoordinatorLayout,messege,Snackbar.LENGTH_LONG).show();
    }

    private void setupToolbar(){
       setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar!=null){
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        Bitmap bm= BitmapFactory.decodeResource(getResources(),R.drawable.avatar_image_2);
        mRoundImage = new RoundImage(bm);
        mImageView.setImageDrawable(mRoundImage);

        navigationView.setNavigationItemSelectedListener(new NavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                showSnackbar(item.getTitle().toString());
                item.setChecked(true);
                mNavigationDrawer.closeDrawer(GravityCompat.START);

                return false;
            }
        });
    }

    private void changeEditMode(boolean mode){
        if (mode) {
            mFab.setImageResource(R.drawable.ic_done_black_24dp);
            for (EditText userValue : mUserInfoViews) {
                userValue.setEnabled(true);
                userValue.setFocusable(true);
                userValue.setFocusableInTouchMode(true);
            }
        } else {
            mFab.setImageResource(R.drawable.ic_create_black_24dp);
            for (EditText userValue : mUserInfoViews) {
                userValue.setEnabled(false);
                userValue.setFocusable(false);
                userValue.setFocusableInTouchMode(false);
                saveUserInfoValue();
            }
        }
    }
    private void loadUserInfoValue(){
        List<String> userData = mDataManager.getPreferencesManager().loadUserProfileData();
        for (int i = 0; i < userData.size(); i++){
            mUserInfoViews.get(i).setText(userData.get(i));
        }
    }

    private void saveUserInfoValue(){
        List<String> userData = new ArrayList<>();
        for (EditText userFieldView : mUserInfoViews){
            userData.add(userFieldView.getText().toString());
        }
        mDataManager.getPreferencesManager().saveUserProfileData(userData);
        }
    }

        /*private void runWithDelay(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //TODO: run with delay
                hideProgress();
            }
        },5000);

    }*/

