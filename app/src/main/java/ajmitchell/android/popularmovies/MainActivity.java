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
        adapter = new MovieAdapter();
        recyclerView.setAdapter(adapter);

//        recyclerView.setAdapter(adapter);
        // here we will set the onclick to the recyclerView instead of the button.
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

        Call<List<Movie>> call = movieApi.getMovieList(Constants.API_KEY);

        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.isSuccessful()) {
                    adapter.setMovie(response.body());
                } else {
                    Log.d(TAG, "onResponse: something's wrong");
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {

            }
        });
    }

}