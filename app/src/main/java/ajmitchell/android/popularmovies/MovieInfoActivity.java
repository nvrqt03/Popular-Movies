package ajmitchell.android.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MovieInfoActivity extends AppCompatActivity {

    public static final String EXTRA_INFO = "extra_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        String imageAddress = "https://m.media-amazon.com/images/M/MV5BMTUyNTI4NTIwNl5BMl5BanBnXkFtZTgwMjQ4MTI0NDE@._V1_.jpg";
        ImageView imageView = findViewById(R.id.imageView);
        int chappie = R.drawable.ic_ole_chappie;

        Picasso.get()
                .load(imageAddress)
                .resize(500, 0)
                .into(imageView);

        Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        });
    }
}