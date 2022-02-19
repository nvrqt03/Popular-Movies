package ajmitchell.android.popularmovies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import ajmitchell.android.popularmovies.database.MovieRepository;
import ajmitchell.android.popularmovies.model.Movie;

public class MovieDetailsViewModel extends AndroidViewModel {

    private MovieRepository repository;
    private LiveData<Movie.Result> movie;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieRepository(getApplication());
    }


//    public void insert(Movie.Result movie) {
//        repository.insert(movie);
//    }
//
//    public void delete(Movie.Result movie) {
//        repository.delete(movie);
//    }


    public LiveData<Movie.Result>  getMovieById(int id) {
        return repository.getMovieById(id);
    }

}
