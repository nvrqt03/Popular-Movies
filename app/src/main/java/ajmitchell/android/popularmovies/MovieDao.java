package ajmitchell.android.popularmovies;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ajmitchell.android.popularmovies.model.Movie;

public interface MovieDao {
    @Query("SELECT * FROM movie_table ORDER BY releaseDate")
    LiveData<List<Movie>> getAllMovies();

    @Insert
    void insertMovie(Movie movie);

    @Update
    void updateMovie(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("DELETE FROM movie_table")
    void deleteAllMovies();
}

