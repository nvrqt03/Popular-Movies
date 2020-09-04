package ajmitchell.android.popularmovies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import ajmitchell.android.popularmovies.model.Movie;

public class MovieDetailsViewModel extends AndroidViewModel {

    private MovieRepository repository;
    private LiveData<Movie.Result> movie;

    public MovieDetailsViewModel(@NonNull Application application, int movieId) {
        super(application);
        repository = new MovieRepository(getApplication());
        movie = repository.getMovieById(movieId);
    }

    public LiveData<Movie.Result> getMovie() {
        return movie;
    }

}
