package com.devintensive.devintensive.ui.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageHelper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.devintensive.devintensive.R;
import com.devintensive.devintensive.data.managers.DataManager;
import com.devintensive.devintensive.utils.CircleTransform;
import com.devintensive.devintensive.utils.ConstantManager;
import com.devintensive.devintensive.utils.RoundImage;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class MainActivity extends BaseActivity /*implements View.OnClickListener*/ {

    private static final String TAG = ConstantManager.TAG_PREFIX + "Main Activity";

    private boolean mCurrentEditMode = false;
    private DataManager mDataManager;
    private ImageView mCalling;
    private NavigationView mNavigationView;
    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;
    private DrawerLayout mNavigationDrawer;
    private FloatingActionButton mFab;
    private RelativeLayout mProfilePlaceHolder;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private AppBarLayout mAppBarLayout;
    private ImageView mProfileImage;

    private EditText mUserPhone, mUserMail, mUserVk, mUserGit, mUserBio;
    /*@BindView(R.id.phone_et)EditText mUserPhone;
    @BindView(R.id.email_et)EditText mUserMail;
    @BindView(R.id.vk_et)EditText mUserVk;
    @BindView(R.id.git_et)EditText mUserGit;
    @BindView(R.id.about_et)EditText mUserBio;*/

    private ImageView mImageView;
    private RoundImage mRoundImage;
    private Bitmap bm;

    private List<EditText> mUserInfoViews;

    private AppBarLayout.LayoutParams mAppBarParams = null;
    private File mPhotoFile = null;
    private Uri mSelectedImage = null;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.d(TAG, "onCreate");

        NavigationView mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mDataManager = DataManager.getInstance();
        mCalling = (ImageView) findViewById(R.id.call_img);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_container);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationDrawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mProfilePlaceHolder = (RelativeLayout) findViewById(R.id.profile_placeholder);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        mProfileImage = (ImageView)findViewById(R.id.user_photo_img);

        mUserPhone = (EditText) findViewById(R.id.phone_et);
        mUserMail = (EditText) findViewById(R.id.email_et);
        mUserVk = (EditText) findViewById(R.id.vk_et);
        mUserGit = (EditText) findViewById(R.id.git_et);
        mUserBio = (EditText) findViewById(R.id.about_et);





        mUserInfoViews = new ArrayList<>();
        mUserInfoViews.add(mUserPhone);
        mUserInfoViews.add(mUserMail);
        mUserInfoViews.add(mUserVk);
        mUserInfoViews.add(mUserGit);
        mUserInfoViews.add(mUserBio);


        setupToolbar();
        setupDrawer();
        loadUserInfoValue();
        Picasso.with(this)
                .load(mDataManager.getPreferencesManager().LoadUserPhoto())
                .placeholder(R.drawable.avatar_image_2) //TODO:make  placeholder transformation + crop
                .into(mProfileImage);





        if (savedInstanceState == null) {
            //1st time activity start
            //showSnackbar("Its 1st time run");
        } else {
            mCurrentEditMode = savedInstanceState.getBoolean(ConstantManager.EDIT_MOD_KEY, false);
            changeEditMode(mCurrentEditMode);
            //activity was created
            //showSnackbar("already created");
        }
    }
    /**
     * Float action button onClick
     */
    @OnClick(R.id.fab)
    protected void OnClickFAB(){
        showSnackbar("click");
        if (!mCurrentEditMode) {

            changeEditMode(true);
            mCurrentEditMode = true;
        } else {

            changeEditMode(false);
            mCurrentEditMode = false;
        }
    }


    /**
     * call img button onClick
     */
    @OnClick(R.id.call_img)
    protected void makeCall() {
        Uri phone = Uri.parse("tel:" + mUserPhone.getText());
        Intent callIntent = new Intent(Intent.ACTION_DIAL, phone);
        startActivity(callIntent);
    }


    /**
     * email img button onClick
     */
    @OnClick(R.id.email_img)
    protected void sendEmail() {
        Uri email = Uri.parse("mailto:" + mUserMail.getText());
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, email);
        startActivity(Intent.createChooser(emailIntent, "Send feedback"));
    }


    /**
     * vk img button onClick
     */
    @OnClick(R.id.vk_img)
    protected void viewVkProfile() {
        Uri vk = Uri.parse("https://" + mUserVk.getText());
        Intent vkIntent = new Intent(Intent.ACTION_VIEW, vk);
        startActivity(vkIntent);
    }


    /**
     * git img button onClick
     */
    @OnClick(R.id.git_img)
    protected void viewRepo() {
        Uri git = Uri.parse("https://" + mUserGit.getText());
        Intent gitIntent = new Intent(Intent.ACTION_VIEW, git);
        startActivity(gitIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
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


    //
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




    /**
     * profile placeholder img button onClick
     */
    @OnClick(R.id.profile_placeholder)
    protected void OnClickProfilePlaceholder(){
        showDialog(ConstantManager.LOAD_PROFILE_PHOTO);
    }


    /**
     * Back button onClick
     */
    @Override
    public void onBackPressed() {
        if (mNavigationDrawer.isDrawerOpen(GravityCompat.START)) {
            mNavigationDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    /**
     * Save instance state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ConstantManager.EDIT_MOD_KEY, mCurrentEditMode);
    }

    private void showSnackbar(String messege) {
        Snackbar.make(mCoordinatorLayout, messege, Snackbar.LENGTH_LONG).show();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();

        mAppBarParams = (AppBarLayout.LayoutParams) mCollapsingToolbar.getLayoutParams();

        if (actionbar != null) {
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);


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



    /**
     * get resuls from another Activity(photo from camera or gallery)
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ConstantManager.REQUEST_GALLERY_PICTURE:
                if (resultCode == RESULT_OK && data != null) {
                    mSelectedImage = data.getData();

                    insertProfileImage(mSelectedImage);
                }
                break;
            case ConstantManager.REQUEST_CAMERA_PICTURE:
                if (resultCode == RESULT_OK && mPhotoFile != null){
                    mSelectedImage = Uri.fromFile(mPhotoFile);

                    insertProfileImage(mSelectedImage);
                }
                break;
        }
    }

    /**
     * edit mode on user profile
     * @param mode  if true - enabled,if false - disabled
     */
    private void changeEditMode(boolean mode) {
        if (mode) {
            mFab.setImageResource(R.drawable.ic_done_black_24dp);
            for (EditText userValue : mUserInfoViews) {
                userValue.setEnabled(true);
                userValue.setFocusable(true);
                userValue.setFocusableInTouchMode(true);

                // On Edit mode enable focus jump on phone edittext
                mUserPhone.requestFocus();
                mUserPhone.setPressed(true);
                mUserPhone.setCursorVisible(true);
                mUserPhone.setSelection(mUserPhone.length());


                showProfilePlaceholder();
                lockToolbar();
                mCollapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT);
            }
        } else {
            mFab.setImageResource(R.drawable.ic_create_black_24dp);
            for (EditText userValue : mUserInfoViews) {
                userValue.setEnabled(false);
                userValue.setFocusable(false);
                userValue.setFocusableInTouchMode(false);

                hideProfilePlaceholder();
                unlockToolbar();
                mCollapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.white));

                saveUserInfoValue();


            }
        }
    }

    private void loadUserInfoValue() {
        List<String> userData = mDataManager.getPreferencesManager().loadUserProfileData();
        for (int i = 0; i < userData.size(); i++) {
            mUserInfoViews.get(i).setText(userData.get(i));
        }
    }

    private void saveUserInfoValue() {
        List<String> userData = new ArrayList<>();
        for (EditText userFieldView : mUserInfoViews) {
            userData.add(userFieldView.getText().toString());
        }
        mDataManager.getPreferencesManager().saveUserProfileData(userData);
    }

    private void loadPhotoFromGallery() {
        Intent takeGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        takeGalleryIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(takeGalleryIntent, getString(R.string.user_profile_chose_message)), ConstantManager.REQUEST_GALLERY_PICTURE);
    }

    private void loadPhotoFromCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){



        Intent takeCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        try {
            mPhotoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
            //TODO:  handle exception
        }
        if (mPhotoFile != null) {
            //TODO: transfer photofile to intent
            takeCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
            startActivityForResult(takeCaptureIntent, ConstantManager.REQUEST_CAMERA_PICTURE);
        }
        }else {
            ActivityCompat.requestPermissions(this, new String []{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            },ConstantManager.CAMER_REQUEST_PERMISSION_CODE);
            Snackbar.make(mCoordinatorLayout, "Need permission granted for correct application work",Snackbar.LENGTH_LONG)
                    .setAction("Allow", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openApplicationSettings();
                        }
                    }).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ConstantManager.CAMER_REQUEST_PERMISSION_CODE && grantResults.length == 2){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                ///TODO: handle permission (permission granted)
                // for example  show message or any logic code
            }
        }
        if (grantResults[1]== PackageManager.PERMISSION_GRANTED){
            ///TODO: handle permission (permission granted)
            // for example  show message or any logic code
        }
    }

    private void hideProfilePlaceholder() {
        mProfilePlaceHolder.setVisibility(View.GONE);
    }

    private void showProfilePlaceholder() {
        mProfilePlaceHolder.setVisibility(View.VISIBLE);
    }

    private void lockToolbar() {
        mAppBarLayout.setExpanded(true, true);
        mAppBarParams.setScrollFlags(0);
        mCollapsingToolbar.setLayoutParams(mAppBarParams);
    }

    private void unlockToolbar() {
        mAppBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        mCollapsingToolbar.setLayoutParams(mAppBarParams);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case ConstantManager.LOAD_PROFILE_PHOTO:
                String[] selectItems = {getString(R.string.user_profile_dialog_galery), getString(R.string.user_profile_dialog_camera), getString(R.string.user_profile_dialog_cancel)};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.user_profile_dialog_title));
                builder.setItems(selectItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int choiseItem) {
                        switch (choiseItem) {
                            case 0:
                                //TODO: load from gallery
                                loadPhotoFromGallery();
                                //showSnackbar("load from gallery");
                                break;
                            case 1:
                                //TODO: load from camera
                                loadPhotoFromCamera();
                                //showSnackbar("load from camera");
                                break;
                            case 2:
                                //TODO: cancel
                                dialog.cancel();
                               // showSnackbar("cancel");
                                break;
                        }
                    }
                });
                return builder.create();

            default:
                return null;
        }

    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE,"image/jpeg");
        values.put(MediaStore.MediaColumns.DATA,image.getAbsolutePath());

        this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        return image;
    }


    private void insertProfileImage(Uri selectedImage) {
        Picasso.with(this)
                .load(selectedImage)
                .into(mProfileImage);
                 //TODO:make  placeholder transformation + crop
        mDataManager.getPreferencesManager().SaveUserPhoto(selectedImage);
    }

    public void openApplicationSettings(){
        Intent appSwttingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));

        startActivityForResult(appSwttingsIntent,ConstantManager.PERMISSION_REQUEST_SETTINGS_CODE);
    }


}




