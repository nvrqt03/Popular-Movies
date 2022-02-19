package ajmitchell.android.popularmovies.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static List<String> movieImageList;
    private static JsonArray resultArray;
    private String posterPath;

    public static List<String> parseMovieDB(JsonObject movieObject) {
        resultArray = movieObject.getAsJsonArray("results");
        movieImageList = jsonArrayToList(resultArray);
        return movieImageList;
    }

    public static List<String> jsonArrayToList(JsonArray array) {
        List<String> movieImageList = new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {
            movieImageList.add(String.valueOf(i));
        }
        return movieImageList;
    }
}
