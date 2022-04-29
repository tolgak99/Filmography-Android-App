package com.example.filmapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.example.filmapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //hideDeafultAppBar();
        navigateLoginPage();
    }

    void hideDeafultAppBar()
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
    }

    void navigateLoginPage()
    {
        Intent toLoginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(toLoginIntent);
    }

}