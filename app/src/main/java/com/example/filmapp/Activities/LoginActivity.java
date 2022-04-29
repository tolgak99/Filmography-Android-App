package com.example.filmapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.filmapp.Activities.MainFilmListActivity;
import com.example.filmapp.Entity.TextValidator;
import com.example.filmapp.Entity.Validation;
import com.example.filmapp.R;

public class LoginActivity extends AppCompatActivity {

    boolean mailValid;
    boolean passValid;

    EditText userNameInput;
    EditText passInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideDeafultAppBar();
        setContentView(R.layout.activity_login);

        if (getLoginStateFromLocalData())
            navigateFilmListActivity();

        userNameInput = (EditText) findViewById(R.id.loginUserNameInput);
        passInput = (EditText) findViewById(R.id.loginPasswordInput);

        Button loginButton = (Button) findViewById(R.id.loginButton);
        TextView txtRegister = (TextView) findViewById(R.id.createAccountText);
        TextView resetPass = (TextView) findViewById(R.id.resetPasswordButton);
        TextView mailError = (TextView) findViewById(R.id.mailErrorText);
        TextView passError = (TextView) findViewById(R.id.passwordErrorText);

        ImageView instaButton = (ImageView) findViewById(R.id.instaImage);
        ImageView linkedinButton = (ImageView) findViewById(R.id.linkedinImage);
        ImageView websiteButton = (ImageView) findViewById(R.id.websiteImage);

        userNameInput.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_user, 0, 0, 0);
        passInput.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_pass, 0, 0, 0);

        userNameInput.addTextChangedListener(new TextValidator(userNameInput) {
            @Override public void validate(TextView textView, String text) {
                mailValid =  Validation.mailValidation(text);

                if (!mailValid)
                {
                    userNameInput.setTextColor(Color.parseColor("Red"));
                    userNameInput.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_user, 0, R.drawable.ic_action_error, 0);
                    mailError.setText("Must Be Mail Type");
                    mailError.setVisibility(View.VISIBLE);
                }
                else
                {
                    userNameInput.setTextColor(Color.parseColor("White"));
                    userNameInput.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_user, 0, 0, 0);
                    mailError.setVisibility(View.INVISIBLE);
                }
            }
        });

        passInput.addTextChangedListener(new TextValidator(passInput) {
            @Override public void validate(TextView textView, String text) {
                passValid =  Validation.entryNullException(text);

                if (!passValid)
                {
                    passInput.setTextColor(Color.parseColor("Red"));
                    passInput.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_pass, 0, R.drawable.ic_action_error, 0);
                    passError.setText("Password must be filled!");
                    passError.setVisibility(View.VISIBLE);
                }
                else
                {
                    passInput.setTextColor(Color.parseColor("White"));
                    passInput.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_pass, 0, 0, 0);
                    passError.setVisibility(View.INVISIBLE);
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (passValid && mailValid) {
                    saveLoginData(true);
                    Intent toMainIntent = new Intent(LoginActivity.this, MainFilmListActivity.class);
                    toMainIntent.putExtra("username", userNameInput.getText().toString());
                    startActivity(toMainIntent);
                //}
                //else if (!passValid)
                //{
                //    showKeyboard(passInput,LoginActivity.this);
                //    passInput.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_pass, 0, R.drawable.ic_action_error, 0);
                //    passError.setText("Password must be filled!");
                //    passError.setVisibility(View.VISIBLE);
                //}
                //else
                //{
                 //   showKeyboard(userNameInput,LoginActivity.this);
                //}
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Intent toRegisterIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                //startActivity(toRegisterIntent);
            }
        });

        resetPass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Intent toRegisterIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                //startActivity(toRegisterIntent);
            }
        });

        linkedinButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.linkedin.com/in/tolga-kalaycıoğlu-2abb39175/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        instaButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.instagram.com/tolgak99");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        websiteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.tolgakalaycioglu.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    void hideDeafultAppBar()
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
    }

    public static void showKeyboard(EditText mEtSearch, Context context) {
        mEtSearch.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public static void hideSoftKeyboard(EditText mEtSearch, Context context) {
        mEtSearch.clearFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEtSearch.getWindowToken(), 0);
    }

    private void navigateFilmListActivity()
    {
        Intent toMainIntent = new Intent(LoginActivity.this, MainFilmListActivity.class);
        startActivity(toMainIntent);
    }

    private void saveLoginData(boolean isLogin)
    {
        // Save Data
        String CONST_DATA = "LOGIN_STATE";
        SharedPreferences preferences = this.getSharedPreferences(CONST_DATA, getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(CONST_DATA,isLogin);
        editor.apply();
    }

    private Boolean getLoginStateFromLocalData()
    {
        Boolean result;
        String CONST_DATA = "LOGIN_STATE";
        SharedPreferences preferences = this.getSharedPreferences(CONST_DATA, getApplicationContext().MODE_PRIVATE);
        result = preferences.getBoolean(CONST_DATA, false);
        return result;
    }
}