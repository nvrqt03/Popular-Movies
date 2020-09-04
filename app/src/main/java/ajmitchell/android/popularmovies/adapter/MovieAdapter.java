package ajmitchell.android.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import ajmitchell.android.popularmovies.R;
import ajmitchell.android.popularmovies.model.Movie;
import ajmitchell.android.popularmovies.utils.Constants;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private List<Movie.Result> imageList;
    private Movie.Result movieResults;
    public OnMovieListener mOnMovieListener;

    public MovieAdapter(Context context, List images, OnMovieListener onMovieListener) {
        this.context = context;
        this.imageList = images;
        this.mOnMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.movie_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView, mOnMovieListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // we want to get a list of the movies from the api - popular movies and highly rated. but we should get a list of those movies.
        // but we want to post those as pics, so we'll be posting the images to our recyclerView

        movieResults = imageList.get(position);
        String imageUrl = Constants.BASE_IMAGE_URL + movieResults.getPosterPath();
        Picasso.get()
                .load(imageUrl)
                //.resize(800, 1100)
                //.centerInside()
                .into(holder.image);
        Picasso.get()
                .setLoggingEnabled(true);

    }

    @Override
    public int getItemCount() {
        if (imageList == null) {
            return 0;
        }
        return imageList.size();
    }

    public Movie.Result getPositionMovie() {
        return movieResults;
    }

    public void setMovies(List<Movie.Result> movieEntries) {
        imageList = movieEntries;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        OnMovieListener onMovieListener;
        public ImageView image;

        public ViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
            super(itemView);
            this.onMovieListener = onMovieListener;

            image = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onMovieListener.onMovieClick(getAdapterPosition());
        }
    }

    public interface OnMovieListener {
        void onMovieClick(int position);
    }
}
