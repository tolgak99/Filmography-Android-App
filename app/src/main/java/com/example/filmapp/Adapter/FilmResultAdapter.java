package com.example.filmapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.filmapp.Activities.FilmDetailActivity;
import com.example.filmapp.Entity.Result;
import com.example.filmapp.R;

import java.util.List;

public class FilmResultAdapter extends RecyclerView.Adapter<FilmResultAdapter.ViewHolder> {
    private List<Result> filmResultItemList;
    private Result result;

    public FilmResultAdapter(List<Result> filmResultItemList) {
        this.filmResultItemList = filmResultItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_film_list_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        result = filmResultItemList.get(position);

        holder.date_textview.setText(result.getTitle());
        holder.status_textview.setText(result.getYear());
        holder.degree_value_textview.setText(result.getType());
        holder.degree_textview.setText("Tür");

        String imageURL = result.getPoster();
        Glide.with(holder.film_poster)
                .load(imageURL)
                .fitCenter()
                .override(500)
                .into(holder.film_poster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateDetailActivity(v.getContext());
            }
        });
    }

    @Override
    public int getItemCount() {
        return filmResultItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView date_textview;
        private TextView status_textview;
        private TextView degree_value_textview;
        private TextView degree_textview;
        private ImageView film_poster;

        public ViewHolder(View v) {
            super(v);
            date_textview = v.findViewById(R.id.date_textview);
            status_textview = v.findViewById(R.id.status_textview);
            degree_value_textview = v.findViewById(R.id.degree_value_textview);
            degree_textview = v.findViewById(R.id.degree_textview);
            film_poster = v.findViewById(R.id.image_view);
        }
    }

    private void navigateDetailActivity(Context context)
    {
        Intent intent = new Intent(context, FilmDetailActivity.class);
        intent.putExtra("result",result);
        context.startActivity(intent);
    }
}
