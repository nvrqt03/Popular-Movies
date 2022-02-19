package ajmitchell.android.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ajmitchell.android.popularmovies.R;
import ajmitchell.android.popularmovies.model.Movie;
import ajmitchell.android.popularmovies.utils.Constants;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private List<Movie.Result> imageList;
    public OnMovieListener mOnMovieListener;

    public MovieAdapter(Context context, List<Movie.Result> images, OnMovieListener onMovieListener) {
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
        Movie.Result movieResults = imageList.get(position);  // <-- how do I update this if it becomes LiveData? there will be no get() method
        String imageUrl = Constants.BASE_IMAGE_URL + movieResults.getPosterPath();
        Picasso.get()
                .load(imageUrl)
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

    public void setMovies(List<Movie.Result> movieEntries) {
        this.imageList = movieEntries;
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
            onMovieListener.onMovieClick(imageList.get(getAdapterPosition()));
        }
    }

    public interface OnMovieListener {
        void onMovieClick(Movie.Result movie);
    }
}
