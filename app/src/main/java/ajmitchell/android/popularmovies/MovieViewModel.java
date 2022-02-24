package ajmitchell.android.popularmovies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import ajmitchell.android.popularmovies.database.MovieRepository;
import ajmitchell.android.popularmovies.model.Movie;

public class MovieViewModel extends AndroidViewModel {
    private MovieRepository repository;
    private LiveData<List<Movie.Result>> allMovies;
    // implement view model for main activity
    public List<Movie.Result> movieList;
    public ActionBar actionBar;

    public List<Movie.Result> setMovieList(List<Movie.Result> movieList) {
        this.movieList = movieList;
        return movieList;
    }

    public MovieViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieRepository(application);
        allMovies = repository.getAllMovies();
        movieList = new ArrayList<>();
    }

    public LiveData<List<Movie.Result>> getAllMovies() {
        return allMovies;
    }

    public List<Movie.Result> getMovieList() {
        return movieList;
    }
}
