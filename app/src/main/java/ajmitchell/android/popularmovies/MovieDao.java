package ajmitchell.android.popularmovies;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ajmitchell.android.popularmovies.model.Movie;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movie_results_table ORDER BY releaseDate")
    List<Movie.Result> getAllMovies();

    @Insert
    void insertMovie(Movie.Result movie);

//    @Update
//    void updateMovie(Movie.Result movie);

    @Delete
    void delete(Movie.Result movie);

    @Query("DELETE FROM movie_results_table")
    void deleteAllMovies();
}

