package ajmitchell.android.popularmovies.apiClients;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ajmitchell.android.popularmovies.MainActivity;
import ajmitchell.android.popularmovies.adapter.MovieAdapter;
import ajmitchell.android.popularmovies.model.Movie;
import ajmitchell.android.popularmovies.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieApiClient extends AppCompatActivity {

    private static MovieDataApi movieDataApi;

    public static MovieDataApi getMovieDataApi() {
        if (movieDataApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            movieDataApi = retrofit.create(MovieDataApi.class);
        }
        return movieDataApi;
    }
}







