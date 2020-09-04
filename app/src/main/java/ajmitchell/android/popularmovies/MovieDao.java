package ajmitchell.android.popularmovies;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ajmitchell.android.popularmovies.model.Movie;
import ajmitchell.android.popularmovies.model.Review;
import ajmitchell.android.popularmovies.model.Video;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movie_results_table ORDER BY releaseDate")
    List<Movie.Result> getAllMovies();

    //may need to use @Query to select the specific movie to add
    @Insert
    void insertMovie(Movie.Result movie);

    @Query("DELETE FROM movie_results_table WHERE id = :movieId")
    void delete(int movieId);

    @Query("DELETE FROM movie_results_table")
    void deleteAllMovies();
}

