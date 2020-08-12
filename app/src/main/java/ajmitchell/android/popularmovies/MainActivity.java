package ajmitchell.android.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ajmitchell.android.popularmovies.adapter.MovieAdapter;
import ajmitchell.android.popularmovies.model.Movie;
import ajmitchell.android.popularmovies.model.Video;
import ajmitchell.android.popularmovies.utils.Constants;
import ajmitchell.android.popularmovies.utils.JsonUtils;
import ajmitchell.android.popularmovies.utils.MovieDataApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieListener {

    String TAG = "MainActivity";
    Button mButton;
    Movie.Result mMovie;
    RecyclerView recyclerView;
    MovieAdapter adapter;
    List<Movie.Result> movieList;
    ActionBar actionBar;
    String option = "";
    int mMovieId;
    List <Video.Result> trailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MovieAdapter(MainActivity.this, movieList, this);
        recyclerView.setAdapter(adapter);

        getMovies(Constants.POPULAR);


    }

    public void getMovies(String category) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieDataApi movieApi = retrofit.create(MovieDataApi.class);
        Call<Movie> call = movieApi.getMovies(category, Constants.API_KEY, Constants.LANGUAGE, Constants.PAGE);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movieDetails = response.body();
                movieList = movieDetails.getResults();
                adapter = new MovieAdapter(MainActivity.this, movieList, adapter.mOnMovieListener);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getTrailers(int movieId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieDataApi movieApi = retrofit.create(MovieDataApi.class);
        Call<Video> call = movieApi.getTrailer(movieId, Constants.API_KEY, Constants.LANGUAGE);

        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                Video movieDetails = response.body();
                trailer = movieDetails.getResults();
                adapter = new MovieAdapter(MainActivity.this, trailer, adapter.mOnMovieListener);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onMovieClick(int position) {
        Movie.Result movie = movieList.get(position);
        mMovieId = movie.getId();
        getTrailers(mMovieId);
        Video.Result trailerResults = trailer.get(position);

        String trailerKey = trailerResults.getKey();
        Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
        intent.putExtra("Movie Details", movie);
        intent.putExtra("Trailer Details", trailerKey);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular_item:
                getMovies(Constants.POPULAR);
                return true;
            case R.id.highest_rated:
                getMovies(Constants.TOP_RATED);
                return true;
            case R.id.coming_soon:
                getMovies(Constants.COMING_SOON);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    }