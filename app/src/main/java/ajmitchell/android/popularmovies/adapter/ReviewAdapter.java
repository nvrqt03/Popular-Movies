package ajmitchell.android.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ajmitchell.android.popularmovies.R;
import ajmitchell.android.popularmovies.model.Review;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private Context context;
    private List<Review.Result> reviewList;
    private Review.Result reviewResults;

    public ReviewAdapter(Context context, List<Review.Result> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.review_item, parent, false);
        ReviewAdapter.ViewHolder viewHolder = new ReviewAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        reviewResults = reviewList.get(position);
        TextView reviewTextView = holder.revTextView.findViewById(R.id.review_content);
        TextView authorTextView = holder.authTextView.findViewById(R.id.review_author);

        reviewTextView.setText(reviewResults.getContent());
        authorTextView.setText(reviewResults.getAuthor());

    }


    @Override
    public int getItemCount() {
        if (reviewList == null) {
            return 0;
        }
        return reviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView revTextView;
        TextView authTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            revTextView = itemView.findViewById(R.id.review_content);
            authTextView = itemView.findViewById(R.id.review_author);
        }
    }
}

