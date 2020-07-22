package ajmitchell.android.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ajmitchell.android.popularmovies.adapter.MovieAdapter;
import ajmitchell.android.popularmovies.model.Movie;
import ajmitchell.android.popularmovies.utils.Constants;
import ajmitchell.android.popularmovies.utils.JsonUtils;
import ajmitchell.android.popularmovies.utils.MovieDataApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    Button mButton;
    Movie mMovie;
    RecyclerView recyclerView;
    MovieAdapter adapter;
    List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ArrayAdapter<Movie> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1,
//                movies);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        getMovies();
    }

    private void launchMovieInfoActivity() {
        Intent intent = new Intent(this, MovieInfoActivity.class);
        intent.putExtra(MovieInfoActivity.EXTRA_INFO, true);
        startActivity(intent);
    }

    public void getMovies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieDataApi movieApi = retrofit.create(MovieDataApi.class);



        Call<Movie> call = movieApi.getMovies(Constants.POPULAR, Constants.API_KEY, Constants.LANGUAGE, Constants.PAGE);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movieDetails = response.body();
                List<Movie.Result> movieList = movieDetails.getResults();

                    adapter = new MovieAdapter(MainActivity.this, movieList);
                    recyclerView.setAdapter(adapter);
                    Log.d(TAG, "onResponse: " + movieList.toString());

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}