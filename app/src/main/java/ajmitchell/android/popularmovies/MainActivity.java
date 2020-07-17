package ajmitchell.android.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;

import ajmitchell.android.popularmovies.model.Movie;

public class MainActivity extends AppCompatActivity {

    Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ArrayAdapter<Movie> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1,
//                movies);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
//        recyclerView.setAdapter(adapter);
        // here we will set the onclick to the recyclerView instead of the button.
        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMovieInfoActivity();
            }
        });
    }

    private void launchMovieInfoActivity() {
        Intent intent = new Intent(this, MovieInfoActivity.class);
        intent.putExtra(MovieInfoActivity.EXTRA_INFO, true);
        startActivity(intent);
    }

}