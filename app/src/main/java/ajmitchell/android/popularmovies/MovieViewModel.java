package ajmitchell.android.popularmovies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ajmitchell.android.popularmovies.database.MovieRepository;
import ajmitchell.android.popularmovies.model.Movie;

public class MovieViewModel extends AndroidViewModel {
private MovieRepository repository;
private LiveData<List<Movie.Result>> allMovies;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieRepository(application);
        allMovies = repository.getAllMovies();
    }

    public LiveData<List<Movie.Result>> getAllMovies() {
        return allMovies;
    }
}
