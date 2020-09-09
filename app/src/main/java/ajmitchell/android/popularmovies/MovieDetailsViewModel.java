package ajmitchell.android.popularmovies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ajmitchell.android.popularmovies.model.Movie;

public class MovieDetailsViewModel extends AndroidViewModel {

    private MovieRepository repository;
    //private LiveData<Movie.Result> selectedMovie;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieRepository(getApplication());
        //selectedMovie = repository.getMovieById(movie.getId());
    }
    public void insert(Movie.Result movie) {
        repository.insert(movie);
    }

    public void delete(Movie.Result movie) {
        repository.delete(movie);
    }

//    public LiveData<Movie.Result> getMovie() {
//        return repository.getMovieById(selectedMovie);
//    }

}
