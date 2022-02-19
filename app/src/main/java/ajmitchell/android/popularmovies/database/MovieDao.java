package ajmitchell.android.popularmovies.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ajmitchell.android.popularmovies.model.Movie;
import ajmitchell.android.popularmovies.model.Review;
import ajmitchell.android.popularmovies.model.Video;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movie_results_table ORDER BY releaseDate")
    LiveData<List<Movie.Result>> getAllMovies();

    //may need to use @Query to select the specific movie to add
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie.Result movie);

    @Query("DELETE FROM movie_results_table WHERE id = :movieId")
    void delete(int movieId);

    @Query("SELECT id FROM movie_results_table WHERE id = :movieId")
    LiveData<Movie.Result> getMovieById(int movieId);

}

