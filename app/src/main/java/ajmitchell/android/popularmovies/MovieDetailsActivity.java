package ajmitchell.android.popularmovies;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import ajmitchell.android.popularmovies.adapter.ReviewAdapter;
import ajmitchell.android.popularmovies.adapter.TrailerAdapter;
import ajmitchell.android.popularmovies.apiClients.MovieApiClient;
import ajmitchell.android.popularmovies.apiClients.MovieDataApi;
import ajmitchell.android.popularmovies.model.Movie;
import ajmitchell.android.popularmovies.model.Review;
import ajmitchell.android.popularmovies.model.Video;
import ajmitchell.android.popularmovies.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity implements TrailerAdapter.OnTrailerListener{

    private Movie.Result movie;
    ActionBar actionBar;
    private int movieId;
    private Video.Result mVideo;
    private List<Video.Result> trailerList;
    private List<Review.Result> reviewList;
    TrailerAdapter trailerAdapter;
    RecyclerView trailerRecyclerView;
    RecyclerView reviewRecyclerView;
    ReviewAdapter reviewAdapter;
    public static final String TAG = "MovieDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

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

        getReviews(movieId);
        getTrailers(movieId);
        getOverview(movie);

        String imageUrl = movie.getPosterPath();
        String fullImageUrl = Constants.BASE_IMAGE_URL + imageUrl;
        Picasso.get()
                .load(fullImageUrl)
                .into(image);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, "Data not available", Toast.LENGTH_SHORT).show();
    }

    private void getOverview(Movie.Result movieResult) {

        TextView title = findViewById(R.id.title_tv);
        TextView overView = findViewById(R.id.overView_tv);
        TextView voteAvg = findViewById(R.id.vote_avg_tv);
        TextView releaseDate = findViewById(R.id.release_date_tv);

        Double voteAverage = movieResult.getVoteAverage();
        String voteAvgText = Double.toString(voteAverage) + "/10";

        title.setText(movieResult.getTitle());
        overView.setText(movieResult.getOverview());
        voteAvg.setText(voteAvgText);

        String date = movieResult.getReleaseDate();
        String[] dateParts = date.split("-");
        String year = dateParts[0];
        releaseDate.setText(year);

    }

    private void getTrailers(int id) {

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

    }

    @Override
    public void onTrailerClick(int position) {

    }

    private void getReviews(int id) {
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
    }


}
