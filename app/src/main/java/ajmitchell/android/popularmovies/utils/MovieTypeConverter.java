package ajmitchell.android.popularmovies.utils;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import ajmitchell.android.popularmovies.model.Movie;

public abstract class MovieTypeConverter {

    @TypeConverter
    public static List<Movie> stringToMovieList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Movie>>() {}.getType();
        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String fromList(List<Movie> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }


}
