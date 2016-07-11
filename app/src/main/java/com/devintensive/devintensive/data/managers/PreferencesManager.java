package com.devintensive.devintensive.data.managers;


import android.content.SharedPreferences;
import android.net.Uri;

import com.devintensive.devintensive.R;
import com.devintensive.devintensive.utils.ConstantManager;
import com.devintensive.devintensive.utils.DevintensiveApplication;

import java.util.ArrayList;
import java.util.List;

public class PreferencesManager {

    private SharedPreferences mSharedPreferences;

    private static final String[] USER_FIELDS = {ConstantManager.USER_PHONE_KEY, ConstantManager.USER_EMAIL_KEY,
            ConstantManager.USER_VK_KEY, ConstantManager.USER_GIT_KEY, ConstantManager.USER_ABOUT_KEY};

    private static final String[] USER_FIELDS_DATA = {"+972546457112","greknmd@gmail.com","vk.com/id167541569","github.com/GrekNMD/DevIntensive","Lorem ipsum dolor sit amet,consectetur adipiscing elit.\n" +
            "        Integer molestie pellentesque massa, eget laoreet metus consectetur sit amet."};

    public PreferencesManager() {
        this.mSharedPreferences = DevintensiveApplication.getSharedPreferences();
    }

    public void saveUserProfileData(List<String> userFields){
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        for (int i = 0; i < USER_FIELDS.length; i++) {
            editor.putString(USER_FIELDS[i], userFields.get(i));
        }
        editor.apply();
    }

    public List<String> loadUserProfileData(){
        List<String> userFields = new ArrayList<>();
        for (int i = 0; i < USER_FIELDS.length; i++){
            userFields.add(mSharedPreferences.getString(USER_FIELDS[i], USER_FIELDS_DATA[i]));
        }
        return userFields;
    }

    public void SaveUserPhoto(Uri uri){

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.USER_PHOTO_KEY,uri.toString());
        editor.apply();
    }

    public Uri LoadUserPhoto(){

        return Uri.parse(mSharedPreferences.getString(ConstantManager.USER_PHOTO_KEY,"android.resource://com.devintensive.devintensive/drawable/avatar_image_2"));
    }

}
