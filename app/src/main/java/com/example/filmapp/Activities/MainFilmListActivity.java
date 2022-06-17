package com.example.filmapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.filmapp.Adapter.FilmResultAdapter;
import com.example.filmapp.Entity.DB;
import com.example.filmapp.Entity.FilmResult;
import com.example.filmapp.Entity.Result;
import com.example.filmapp.Entity.TextValidator;
import com.example.filmapp.Entity.Validation;
import com.example.filmapp.Fragments.FilmFragment;
import com.example.filmapp.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainFilmListActivity extends AppCompatActivity {

    private RecyclerView film_list_recyclerview;
    private EditText film_search_text;
    private String film_name = "Batman";
    private Button search_button;

    private ImageView searchListImage;
    private ImageView savedListImage;
    private ImageView rateImage;

    private boolean isFragmentDisplayed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_film_list);

        film_list_recyclerview = findViewById(R.id.film_list_recycleview);
        film_search_text = findViewById(R.id.film_search_text);
        searchListImage = findViewById(R.id.searchListImage);
        savedListImage = findViewById(R.id.savedListImage);
        search_button = findViewById(R.id.search_button);
        rateImage = findViewById(R.id.rate_image);

        hideDeafultAppBar();

        /*film_search_text.addTextChangedListener(new TextValidator(film_search_text) {
            @Override public void validate(TextView textView, String text) {

                film_name = text;
                getFilmListFromNetwork();
            }
        });*/

        film_name = getFilmNameFromLocalData();
        if (film_name != null)
            getFilmListFromNetwork();

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                film_name = film_search_text.getText().toString();
                if (film_name != null)
                {
                    saveFilmNameData(film_name);
                    getFilmListFromNetwork();
                }
            }
        });

        searchListImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFilmListFromNetwork();
            }
        });

        savedListImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetFilmListFromSavedList();
            }
        });

        rateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFragmentDisplayed) {
                    displayFragment();
                } else {
                    closeFragment();
                }
            }
        });

    }

    private void getFilmListFromNetwork()
    {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://api.collectapi.com/imdb/imdbSearchByName?query=" + film_name)
                .method("GET", null)
                .addHeader("authorization", "apikey 2XzqtUByNqxTJfcfAbsS8r:3zFoFx3xowkgMZlpSfpv2N")
                .addHeader("content-type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("Fail: " , "Network Fail");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful())
                {
                    final String responseBody = response.body().string();
                    Log.e("Success: " , responseBody);

                    FilmResult filmResult = new Gson().fromJson(responseBody, FilmResult.class);

                    MainFilmListActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                setAdapterRecyclerView(filmResult.getResult());
                            }
                    });
                }
            }
        });
    }

    private void saveFilmNameData(String name)
    {
        // Save Data
        String CONST_DATA = "FILM_NAME";
        SharedPreferences preferences = this.getSharedPreferences(CONST_DATA, getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CONST_DATA,name);
        editor.apply();
    }

    private String getFilmNameFromLocalData()
    {
        String result;
        String CONST_DATA = "FILM_NAME";
        SharedPreferences preferences = this.getSharedPreferences(CONST_DATA, getApplicationContext().MODE_PRIVATE);
        result = preferences.getString(CONST_DATA, "Batman");
        return result;
    }

    private void GetFilmListFromSavedList()
    {
        ArrayList<Result> films = new ArrayList<Result>();
        films = GetSavedListFromDB();

        setAdapterRecyclerView(films);
    }

    private ArrayList<Result> GetSavedListFromDB()
    {
        DB db = DB.getInstance(this);
        return db.getFilmList();
    }

    private void setAdapterRecyclerView(List<Result> resultList)
    {
        FilmResultAdapter adapter = new FilmResultAdapter(resultList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        film_list_recyclerview.setLayoutManager(mLayoutManager);
        film_list_recyclerview.setAdapter(adapter);
    }

    public void displayFragment() {
        FilmFragment simpleFragment = FilmFragment.newInstance();
        // Get the FragmentManager and start a transaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        // Add the SimpleFragment.
        fragmentTransaction.add(R.id.fragment_container,
                simpleFragment).addToBackStack(null).commit();

        //mButton.setText(R.string.close);

        isFragmentDisplayed = true;
    }

    public void closeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        FilmFragment simpleFragment = (FilmFragment) fragmentManager
                .findFragmentById(R.id.fragment_container);

        if (simpleFragment != null) {
            // Create and commit the transaction to remove the fragment.
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }
        // Update the Button text.
        //mButton.setText(R.string.open);
        // Set boolean flag to indicate fragment is closed.
        isFragmentDisplayed = false;


    }

    void hideDeafultAppBar()
    {
        getSupportActionBar().hide();
    }

}