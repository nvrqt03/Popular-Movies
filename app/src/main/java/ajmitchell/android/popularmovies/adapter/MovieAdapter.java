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
    private Movie.Result movieResults;

    public MovieAdapter(Context context, List images) {
        this.context = context;
        this.imageList = images;
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
        // we want to get a list of the movies from the api - popular movies and highly rated. but we should get a list of those movies.
        // but we want to post those as pics, so we'll be posting the images to our recyclerView

        movieResults = imageList.get(position);
        String imageUrl = Constants.BASE_IMAGE_URL + movieResults.getPosterPath();
        Picasso.get()
                .load(imageUrl)
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        if (imageList == null) {
            return 0;
        }
        return imageList.size();
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
