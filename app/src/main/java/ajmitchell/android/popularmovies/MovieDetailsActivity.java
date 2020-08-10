package ajmitchell.android.popularmovies;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ajmitchell.android.popularmovies.model.Movie;
import ajmitchell.android.popularmovies.model.Video;
import ajmitchell.android.popularmovies.utils.Constants;

public class MovieDetailsActivity extends AppCompatActivity {

    private Movie.Result movie;
    ActionBar actionBar;
    private int movieId;
    private Video.Result mVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        ImageView image = findViewById(R.id.imageView);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        movie = intent.getParcelableExtra("Movie Details");

        populateUi(movie);
        String imageUrl = movie.getPosterPath();
        String fullImageUrl = Constants.BASE_IMAGE_URL + imageUrl;
        Picasso.get()
                .load(fullImageUrl)
                .into(image);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, "Data not available", Toast.LENGTH_SHORT).show();
    }

    private void populateUi(Movie.Result movieResult) {

        TextView title = findViewById(R.id.title_tv);
        TextView overView = findViewById(R.id.overView_tv);
        TextView voteAvg = findViewById(R.id.vote_avg_tv);
        TextView releaseDate = findViewById(R.id.release_date_tv);
        VideoView videoView = findViewById(R.id.video_view);

        Double voteAverage = movieResult.getVoteAverage();
        String voteAvgText = Double.toString(voteAverage) + "/10";

        int id = movieResult.getId();
        String key = mVideo.getKey();
        String movieTitle = movieResult.getTitle();
        String testMovie = "?language=en-us#play=";
        String movieTrailerPath = Constants.BASE_URL + id + movieTitle + testMovie + key;


        title.setText(movieResult.getTitle());
        overView.setText(movieResult.getOverview());
        voteAvg.setText(voteAvgText);
        releaseDate.setText(movieResult.getReleaseDate());

        Uri uri = Uri.parse(movieTrailerPath);
        videoView.setVideoURI(uri);
    }

    private void getTrailer(int key) {

    }
}