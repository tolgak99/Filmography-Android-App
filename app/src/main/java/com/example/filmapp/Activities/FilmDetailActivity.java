package com.example.filmapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.filmapp.Entity.DB;
import com.example.filmapp.Entity.Result;
import com.example.filmapp.R;

import java.util.ArrayList;

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
        Button delete_button = findViewById(R.id.delete_button);

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
                SaveToDB();
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //saveData();
                DeleteFromDB();
            }
        });
    }

    private void SaveToDB()
    {
        DB db = DB.getInstance(this);
        db.AddNewItem(result.getTitle(),result.getYear(),result.getImdbID(),result.getType(), result.getPoster());

        ArrayList<Result> films = new ArrayList<Result>();

        films = db.getFilmList();
    }

    private void DeleteFromDB()
    {
        DB db = DB.getInstance(this);
        db.DeleteFilmFromDB(result.getTitle());
    }
}