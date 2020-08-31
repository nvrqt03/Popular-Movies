package ajmitchell.android.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import ajmitchell.android.popularmovies.adapter.MovieAdapter;
import ajmitchell.android.popularmovies.apiClients.MovieApiClient;
import ajmitchell.android.popularmovies.model.Movie;
import ajmitchell.android.popularmovies.utils.Constants;
import ajmitchell.android.popularmovies.apiClients.MovieDataApi;
import ajmitchell.android.popularmovies.utils.MovieDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieListener {

    String TAG = "MainActivity";
    RecyclerView recyclerView;
    MovieAdapter adapter;
    List<Movie.Result> movieList;
    ActionBar actionBar;
    MovieDatabase mDb;


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
        mDb = MovieDatabase.getInstance(getApplicationContext());
        getMovies(Constants.POPULAR);

    }

    public void getMovies(String category) {
        MovieDataApi movieApi = MovieApiClient.getMovieDataApi();
        Call<Movie> call = movieApi.getMovies(category, Constants.API_KEY, Constants.LANGUAGE, Constants.PAGE);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movieDetails = response.body();
                movieList = movieDetails.getResults();
                adapter = new MovieAdapter(MainActivity.this,
                        movieList,
                        adapter.mOnMovieListener);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onMovieClick(int position) {
        Movie.Result movie = movieList.get(position);
        Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
        intent.putExtra("Movie Details", movie);
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
                getMovies(Constants.HIGHEST_RATED);
                actionBar.setTitle("Highest Rated");
                return true;
            case R.id.coming_soon:
                getMovies(Constants.COMING_SOON);
                actionBar.setTitle("Coming Soon");
                return true;
            case R.id.favorites:
                getFavorites();
                actionBar.setTitle("Favorites");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getFavorites() {
        List<Movie.Result> movieList = mDb.movieDao().getAllMovies();
        adapter = new MovieAdapter(MainActivity.this,
                movieList,
                adapter.mOnMovieListener);
        recyclerView.setAdapter(adapter);
    }

}