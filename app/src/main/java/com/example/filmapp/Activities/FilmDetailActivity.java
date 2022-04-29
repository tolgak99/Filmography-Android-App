package com.example.filmapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.filmapp.Entity.Result;
import com.example.filmapp.R;

public class FilmDetailActivity extends AppCompatActivity {

    private Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);

        TextView title_textview = findViewById(R.id.title_textview);;
        TextView year_textview = findViewById(R.id.year_textview);;
        TextView type_textview = findViewById(R.id.type_textview);;
        Button save_button = findViewById(R.id.save_button);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null)
        {
            result = bundle.getParcelable("result");
            title_textview.setText(result.getTitle());
            year_textview.setText(result.getYear());
            type_textview.setText(result.getType());
        }

        // Show result data at screen

        // When pressed save button save to db

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //saveData();
            }
        });
    }
}