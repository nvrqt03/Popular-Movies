package ajmitchell.android.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import ajmitchell.android.popularmovies.adapter.MovieAdapter;
import ajmitchell.android.popularmovies.apiClients.MovieApiClient;
import ajmitchell.android.popularmovies.model.Movie;
import ajmitchell.android.popularmovies.utils.Constants;
import ajmitchell.android.popularmovies.apiClients.MovieDataApi;
import ajmitchell.android.popularmovies.database.MovieDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieListener {

    String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<Movie.Result> movieList;
    private ActionBar actionBar;
    private MovieDatabase mDb;
    private MovieViewModel movieViewModel;
    private Parcelable mLayoutManagerSavedState;
// test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, calculateNoOfColumns(this)));
        adapter = new MovieAdapter(MainActivity.this, movieList, this);
        recyclerView.setAdapter(adapter);
        mDb = MovieDatabase.getInstance(getApplicationContext());
        getMovies(Constants.POPULAR);

        if (savedInstanceState != null) {
            mLayoutManagerSavedState = savedInstanceState.getParcelable("KEY_INSTANCE_STATE_RV_POSITION");
            adapter.setMovies((List<Movie.Result>) mLayoutManagerSavedState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("KEY_INSTANCE_STATE_RV_POSITION", recyclerView.getLayoutManager().onSaveInstanceState());
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
                        movieList, //should this be movies from
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
    public void onMovieClick(Movie.Result movie) {
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
                actionBar.setTitle(Constants.popular);
                return true;
            case R.id.highest_rated:
                getMovies(Constants.HIGHEST_RATED);
                actionBar.setTitle(Constants.highest_rated);
                return true;
            case R.id.coming_soon:
                getMovies(Constants.COMING_SOON);
                actionBar.setTitle(Constants.coming_soon);
                return true;
            case R.id.favorites:
                getFavorites();
                actionBar.setTitle(Constants.FAVORITES);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getFavorites() {
        final LiveData<List<Movie.Result>> favorites = mDb.movieDao().getAllMovies();
        movieViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MovieViewModel.class);
        favorites.observe(this, new Observer<List<Movie.Result>>() {
            @Override
            public void onChanged(List<Movie.Result> results) {
                movieViewModel.getAllMovies().removeObserver(this);
                adapter.setMovies(results);
                recyclerView.setAdapter(adapter);
                actionBar.setTitle("Favorites");
            }
        });
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }

}