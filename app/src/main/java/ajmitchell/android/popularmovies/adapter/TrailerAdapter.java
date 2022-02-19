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
import ajmitchell.android.popularmovies.model.Movie;
import ajmitchell.android.popularmovies.model.Video;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private Context context;
    private List<Video.Result> trailerList;
    private Video.Result trailerResults;
    public OnTrailerListener mOnTrailerListener;

    public TrailerAdapter(Context context, List trailerList, OnTrailerListener onTrailerListener) {
        this.context = context;
        this.trailerList = trailerList;
        this.mOnTrailerListener = onTrailerListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.trailer_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView, mOnTrailerListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        trailerResults = trailerList.get(position);
        TextView trailerTextView = holder.textView.findViewById(R.id.trailer_item);
        trailerTextView.setText(trailerResults.getName());
    }

    @Override
    public int getItemCount() {
        if (trailerList == null) {
            return 0;
        }
        return trailerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnTrailerListener onTrailerListener;
        TextView textView;

        public ViewHolder(@NonNull View itemView, OnTrailerListener onTrailerListener) {
            super(itemView);
            this.onTrailerListener = onTrailerListener;

            textView = itemView.findViewById(R.id.trailer_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) { onTrailerListener.onTrailerClick(getAdapterPosition()); }
    }

    public interface OnTrailerListener {
        void onTrailerClick (int position);
    }
}
