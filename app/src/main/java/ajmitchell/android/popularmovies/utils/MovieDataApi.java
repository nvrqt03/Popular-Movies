package ajmitchell.android.popularmovies.utils;

import java.util.List;

import ajmitchell.android.popularmovies.model.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static ajmitchell.android.popularmovies.utils.Constants.API_KEY;

public interface MovieDataApi {
    @GET("movie/popular")
    Call<List<Movie>> getMovieList(
            @Query("api_key") String key
    );

}
