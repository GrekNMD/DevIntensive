package com.devintensive.devintensive.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.devintensive.devintensive.R;
import com.devintensive.devintensive.utils.ConstantManager;

public class MainActivity extends AppCompatActivity {


    private static final String TAG= ConstantManager.TAG_PREFIX+"Main Activity";

    //UI interface
    //initializing static parameters
    //@param savedInstanceState - object with parameters saved in Bundle - state UI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        if (savedInstanceState == null){
            //1st time activity start
        }
        else {
            //activity was created
        }
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
}
