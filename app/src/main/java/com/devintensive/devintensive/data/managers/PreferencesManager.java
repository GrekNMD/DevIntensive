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
    private String userName;

    private static final String[] USER_FIELDS = {
            ConstantManager.USER_NAME_KEY,
            ConstantManager.USER_PHONE_KEY,
            ConstantManager.USER_EMAIL_KEY,
            ConstantManager.USER_VK_KEY,
            ConstantManager.USER_GIT_KEY,
            ConstantManager.USER_ABOUT_KEY
    };

    private static final String[] USER_VALUES = {
            ConstantManager.USER_RATING_VALUE,
            ConstantManager.USER_CODE_LINES_VALUE,
            ConstantManager.USER_PROJECTS_VALUE,
    };

    /*private static final String[] USER_FIELDS_DATA = {"+972546457112","greknmd@gmail.com","vk.com/id167541569","github.com/GrekNMD/DevIntensive","Lorem ipsum dolor sit amet,consectetur adipiscing elit.\n" +
            "        Integer molestie pellentesque massa, eget laoreet metus consectetur sit amet."};*/

    //private  String[] USER_FIELDS_DATA ;

    public PreferencesManager() {
        this.mSharedPreferences = DevintensiveApplication.getSharedPreferences();
    }


    /*Save user data on local change without name, userFields have all data except name of user,but USER_FIELDS[0] have name of user*/
    public void saveUserProfileData(List<String> userFields){
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        for (int i = 0; i < USER_FIELDS.length; i++) {
            editor.putString(USER_FIELDS[i+1], userFields.get(i));
        }
        editor.apply();
    }

    public List<String> loadUserProfileData(){
        List<String> userFields = new ArrayList<>();
        for (int i = 0; i < USER_FIELDS.length; i++){
            userFields.add(mSharedPreferences.getString(USER_FIELDS[i],""));//USER_FIELDS_DATA[i]
        }
        return userFields;
    }

    public void SaveUserPhoto(Uri uri){

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.USER_PHOTO_KEY,uri.toString());
        editor.apply();
    }

    public List<String> loadUserProfileValues(){
        List<String> userValues = new ArrayList<>();
        userValues.add(mSharedPreferences.getString(ConstantManager.USER_RATING_VALUE,"0"));
        userValues.add(mSharedPreferences.getString(ConstantManager.USER_CODE_LINES_VALUE,"0"));
        userValues.add(mSharedPreferences.getString(ConstantManager.USER_PROJECTS_VALUE,"0"));
        return userValues;
    }

    public void saveUserProfileValues(int[] userValues){
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        for (int i = 0; i < USER_VALUES.length; i++) {
            editor.putString(USER_VALUES[i], String.valueOf(userValues[i]));
        }
        editor.apply();
    }

    public Uri LoadUserPhoto(){

        return Uri.parse(mSharedPreferences.getString(ConstantManager.USER_PHOTO_KEY,"android.resource://com.devintensive.devintensive/drawable/avatar_image_2"));
    }

    public void saveAuthToken(String authToken){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.AUTH_TOKEN_KEY,authToken);
        editor.apply();
    }

    public String getAuthToken(){
        return mSharedPreferences.getString(ConstantManager.AUTH_TOKEN_KEY,"null");
    }

    public void saveUserId(String userId){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.USER_ID_KEY,userId);
        editor.apply();
    }

    public String getUserId(){
        return mSharedPreferences.getString(ConstantManager.USER_ID_KEY,"null");
    }

    /* save data from network to @userDataFields */
    public void saveUserProfileDataFields(List<String> userDataFields){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        for (int i = 0; i < userDataFields.size(); i++) {
            editor.putString(USER_FIELDS[i], userDataFields.get(i));
        }
        editor.apply();
    }

}
