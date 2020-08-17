package ajmitchell.android.popularmovies;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import ajmitchell.android.popularmovies.adapter.TrailerAdapter;
import ajmitchell.android.popularmovies.apiClients.MovieApiClient;
import ajmitchell.android.popularmovies.apiClients.MovieDataApi;
import ajmitchell.android.popularmovies.model.Movie;
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
    private List<Video.Result> trailers;
    TrailerAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        ImageView image = findViewById(R.id.imageView);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        movie = intent.getParcelableExtra("Movie Details");

        movieId = movie.getId();

        getTrailers(movieId);
        populateUi(movie);


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

    private void populateUi(Movie.Result movieResult) {

        TextView title = findViewById(R.id.title_tv);
        TextView overView = findViewById(R.id.overView_tv);
        TextView voteAvg = findViewById(R.id.vote_avg_tv);
        TextView releaseDate = findViewById(R.id.release_date_tv);


        recyclerView = findViewById(R.id.trailer_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TrailerAdapter(MovieDetailsActivity.this, trailers, this);
        recyclerView.setAdapter(adapter);

        Double voteAverage = movieResult.getVoteAverage();
        String voteAvgText = Double.toString(voteAverage) + "/10";

        title.setText(movieResult.getTitle());
        overView.setText(movieResult.getOverview());
        voteAvg.setText(voteAvgText);
        releaseDate.setText(movieResult.getReleaseDate());



    }

    private void getTrailers(int id) {

        MovieDataApi movieApi = MovieApiClient.getMovieDataApi();
        Call<Video> call = movieApi.getTrailer(id, Constants.API_KEY, Constants.LANGUAGE);

        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                Video videoDetails = response.body();
                trailers = videoDetails.getResults();

                adapter = new TrailerAdapter(
                        MovieDetailsActivity.this,
                        trailers,
                        adapter.mOnTrailerListener);

                recyclerView.setAdapter(adapter);
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
}
//    MovieDataApi movieApi = MovieApiClient.getMovieDataApi();
//    Call<Movie> call = movieApi.getMovies(category, Constants.API_KEY, Constants.LANGUAGE, Constants.PAGE);
//
//        call.enqueue(new Callback<Movie>() {
//@Override
//public void onResponse(Call<Movie> call, Response<Movie> response) {
//        Movie movieDetails = response.body();
//        movieList = movieDetails.getResults();
//        adapter = new MovieAdapter(MainActivity.this,
//        movieList,
//        adapter.mOnMovieListener);
//        recyclerView.setAdapter(adapter);
//        }
//
//@Override
//public void onFailure(Call<Movie> call, Throwable t) {
//        t.printStackTrace();
//        }
//        });