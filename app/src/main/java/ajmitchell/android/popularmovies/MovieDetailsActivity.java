package ajmitchell.android.popularmovies;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.List;

import ajmitchell.android.popularmovies.adapter.ReviewAdapter;
import ajmitchell.android.popularmovies.adapter.TrailerAdapter;
import ajmitchell.android.popularmovies.apiClients.MovieApiClient;
import ajmitchell.android.popularmovies.apiClients.MovieDataApi;
import ajmitchell.android.popularmovies.model.Movie;
import ajmitchell.android.popularmovies.model.Review;
import ajmitchell.android.popularmovies.model.Video;
import ajmitchell.android.popularmovies.utils.AppExecutors;
import ajmitchell.android.popularmovies.utils.Constants;
import ajmitchell.android.popularmovies.database.MovieDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity implements TrailerAdapter.OnTrailerListener {

    private Movie.Result movie;
    private ActionBar actionBar;
    private int movieId;
    private List<Video.Result> trailerList;
    private List<Review.Result> reviewList;
    private TrailerAdapter trailerAdapter;
    private RecyclerView trailerRecyclerView;
    private RecyclerView reviewRecyclerView;
    private ReviewAdapter reviewAdapter;
    private String fullImageUrl;
    private MovieDatabase mDb;
    private String voteAvgText;
    ToggleButton favoriteImage;
    public Boolean isFavorite;
    public MovieDetailsViewModel movieDetailsViewModel;


    public static final String TAG = "MovieDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mDb = MovieDatabase.getInstance(getApplicationContext());

        trailerRecyclerView = findViewById(R.id.trailer_recyclerView);
        trailerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        trailerAdapter = new TrailerAdapter(MovieDetailsActivity.this, trailerList, this);
        trailerRecyclerView.setAdapter(trailerAdapter);

        reviewRecyclerView = findViewById(R.id.review_recyclerView);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewAdapter = new ReviewAdapter(MovieDetailsActivity.this, reviewList);
        reviewRecyclerView.setAdapter(reviewAdapter);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        ImageView image = findViewById(R.id.imageView);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        movie = intent.getParcelableExtra("Movie Details");
        movieId = movie.getId();
        Log.d(TAG, "onCreate: " + movieId);

        getReviews(movieId);
        getTrailers(movieId);
        getOverview(movie);
        isFavorite(movieId);

        favoriteImage = findViewById(R.id.favorites);
        favoriteImage.setChecked(false);
        favoriteImage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isPressed())
                if (isChecked) {
                    saveToFavorites();
                } else {
                    removeFromFavorites();
                }
            }
        });

        String imageUrl = movie.getPosterPath();
        fullImageUrl = Constants.BASE_IMAGE_URL + imageUrl;
        Picasso.get()
                .load(fullImageUrl)
                .into(image);
    }

    public void isFavorite(int id) {
        movieDetailsViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MovieDetailsViewModel.class);
        LiveData<Movie.Result> favorites = movieDetailsViewModel.getMovieById(id);
        favorites.observe(this, new Observer<Movie.Result>() {
            @Override
            public void onChanged(Movie.Result result) {
                favorites.removeObserver(this);
                if (result == null) {
                    isFavorite = false;
                    favoriteImage.setChecked(false);
                } else if (movie.getId() == result.getId() && !favoriteImage.isChecked()) {
                    isFavorite = true;
                    favoriteImage.setChecked(true);
                } else {
                    isFavorite = true;
                    favoriteImage.setChecked(true);
                }
            }
        });
    }

    public void saveToFavorites() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!isFavorite)
                mDb.movieDao().insertMovie(movie);
            }
        });
        Toast.makeText(MovieDetailsActivity.this, R.string.addToFavorites, Toast.LENGTH_SHORT).show();
    }

    public void removeFromFavorites() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.movieDao().delete(movie.getId());
            }
        });
        Toast.makeText(this, R.string.removeFromFavorite, Toast.LENGTH_SHORT).show();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.dataNotAvail, Toast.LENGTH_SHORT).show();
    }

    private String getOverview(Movie.Result movieResult) {

        TextView title = findViewById(R.id.title_tv);
        TextView overView = findViewById(R.id.overView_tv);
        TextView voteAvg = findViewById(R.id.vote_avg_tv);
        TextView releaseDate = findViewById(R.id.release_date_tv);

        Double voteAverage = movieResult.getVoteAverage();
        voteAvgText = Double.toString(voteAverage); // <--

        title.setText(movieResult.getTitle());
        overView.setText(movieResult.getOverview());
        voteAvg.setText(voteAvgText);

        String date = movieResult.getReleaseDate();
        String[] dateParts = date.split("-");
        String year = dateParts[0]; //<--
        releaseDate.setText(year);

        return movieResult.getOverview();
    }

    private List<Video.Result> getTrailers(int id) {

        MovieDataApi movieApi = MovieApiClient.getMovieDataApi();
        Call<Video> call = movieApi.getTrailer(id, Constants.API_KEY, Constants.LANGUAGE);

        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                Video videoDetails = response.body();
                trailerList = videoDetails.getResults();

                trailerAdapter = new TrailerAdapter(
                        MovieDetailsActivity.this,
                        trailerList,
                        trailerAdapter.mOnTrailerListener);

                trailerRecyclerView.setAdapter(trailerAdapter);
            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return trailerList;
    }

    @Override
    public void onTrailerClick(int position) {

        Video.Result trailer = trailerList.get(position);
        String key = trailer.getKey();
        String youtube = Constants.YOUTUBE;
        //Uri.parse(youtube + key);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtube + key));
        startActivity(intent);
    }

    private List<Review.Result> getReviews(int id) {
        MovieDataApi movieDataApi = MovieApiClient.getMovieDataApi();
        Call<Review> call = movieDataApi.getReviews(id, Constants.API_KEY, Constants.LANGUAGE, Constants.PAGE);
        call.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                Review reviewDetails = response.body();
                reviewList = reviewDetails.getResults();
                Log.d(TAG, "onResponse: " + reviewList.toString());
                reviewAdapter = new ReviewAdapter(
                        MovieDetailsActivity.this,
                        reviewList);
                reviewRecyclerView.setAdapter(reviewAdapter);
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return reviewList;
    }

}
