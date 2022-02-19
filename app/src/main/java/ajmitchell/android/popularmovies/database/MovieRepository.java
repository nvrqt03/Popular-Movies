package ajmitchell.android.popularmovies.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import ajmitchell.android.popularmovies.database.MovieDao;
import ajmitchell.android.popularmovies.model.Movie;
import ajmitchell.android.popularmovies.database.MovieDatabase;

public class MovieRepository {
    private MovieDao movieDao;
    private LiveData<List<Movie.Result>> allMovies;

    // passing application because in our ViewModel we will get application passed - this is also a subclass of context so we can use as context
    public MovieRepository(Application application) {
        MovieDatabase database = MovieDatabase.getInstance(application);
        movieDao = database.movieDao();
        allMovies = movieDao.getAllMovies();
    }


    public void insert(Movie.Result movie) {
        new InsertMovieAsyncTask(movieDao).execute(movie);
    }

    public void delete(Movie.Result movie) {
        new DeleteMovieAsyncTask(movieDao).execute(movie);
    }

    public LiveData<List<Movie.Result>> getAllMovies() {
        return allMovies;
    }

    public LiveData<Movie.Result> getMovieById(int movieId) {
        return movieDao.getMovieById(movieId);
    }

    private static class InsertMovieAsyncTask extends AsyncTask<Movie.Result, Void, Void> {
        private MovieDao movieDao;
        private InsertMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }
        @Override
        protected Void doInBackground(Movie.Result... results) {
            movieDao.insertMovie(results[0]);
            return null;
        }
    }

    private static class DeleteMovieAsyncTask extends AsyncTask<Movie.Result, Void, Void> {
        private MovieDao movieDao;
        private DeleteMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }
        @Override
        protected Void doInBackground(Movie.Result... results) {
            movieDao.delete(results[0].getId());
            return null;
        }
    }

}
