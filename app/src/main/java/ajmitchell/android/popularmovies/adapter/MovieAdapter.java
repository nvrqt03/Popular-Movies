package ajmitchell.android.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ajmitchell.android.popularmovies.R;
import ajmitchell.android.popularmovies.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> mMoviesList;

    public MovieAdapter(List<Movie> movies) {
        mMoviesList = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.movie_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = mMoviesList.get(position);

        ImageView imageView = holder.image;
        ;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
//        public TextView title;
//        public TextView posterPath;
//        public TextView overView;
//        public TextView voteAvg;
//        public TextView releaseDate;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageView);
//            title =  itemView.findViewById(R.id.title_tv);
//            overView = itemView.findViewById(R.id.overView_tv);
//            voteAvg = itemView.findViewById(R.id.vote_avg_tv);
//            releaseDate = itemView.findViewById(R.id.release_date_tv);

        }
    }
}
