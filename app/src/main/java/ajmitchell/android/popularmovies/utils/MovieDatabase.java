package ajmitchell.android.popularmovies.utils;

import android.content.Context;
import android.util.Log;
import android.util.LogPrinter;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import ajmitchell.android.popularmovies.MovieDao;
import ajmitchell.android.popularmovies.model.Movie;

@Database(entities = {Movie.Result.class}, version = 1, exportSchema = false)
@TypeConverters(MovieTypeConverter.class)
public abstract class MovieDatabase extends RoomDatabase {
    public static final String LOG_TAG = MovieDatabase.class.getSimpleName();
    public static final Object LOCK = new Object();
    public static final String DATABASE_NAME = "movieApp";
    private static MovieDatabase sInstance;

    public static MovieDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MovieDatabase.class, MovieDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }
    public abstract MovieDao movieDao();
}
