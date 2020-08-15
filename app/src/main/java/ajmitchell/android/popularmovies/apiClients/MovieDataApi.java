package ajmitchell.android.popularmovies.apiClients;

import java.util.List;

import ajmitchell.android.popularmovies.model.Movie;
import ajmitchell.android.popularmovies.model.Video;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static ajmitchell.android.popularmovies.utils.Constants.API_KEY;

public interface MovieDataApi {

    @GET("/3/movie/{category}")
    Call<Movie> getMovies (
            @Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("/3/movie/{movie_id}/videos")
    Call<Video> getTrailer (
        @Path("movie_id") int movieId,
        @Query("api_key") String apiKey,
        @Query("language") String language
        );
}
