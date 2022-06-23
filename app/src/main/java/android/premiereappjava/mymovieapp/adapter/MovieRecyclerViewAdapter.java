package android.premiereappjava.mymovieapp.adapter;

import android.premiereappjava.mymovieapp.R;
import android.premiereappjava.mymovieapp.api.ApiConstants;
import android.premiereappjava.mymovieapp.models.Result;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Result> mMovies;
    private OnMovieListener onMovieListener;

    public MovieRecyclerViewAdapter(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view, onMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((MovieViewHolder) holder).title.setText(mMovies.get(position).getTitle());
        if (mMovies.get(position).getReleaseDate() != null && mMovies.get(position).getReleaseDate().length() >= 5) {
            ((MovieViewHolder) holder).release.setText(mMovies.get(position).getReleaseDate().substring(0,4));
        } else {
            ((MovieViewHolder) holder).release.setText(mMovies.get(position).getReleaseDate());
        }
        ((MovieViewHolder) holder).language.setText(mMovies.get(position).getOriginalLanguage());
        ((MovieViewHolder) holder).ratingBar.setRating((float) (mMovies.get(position).getVoteAverage() / 2));
        if (mMovies.get(position).getPosterPath() != null) {
            Glide.with(holder.itemView.getContext()).load(ApiConstants.POSTER_BASE_URL_SMALL + mMovies.get(position).getPosterPath()).into(((MovieViewHolder) holder).imageView);
        }
        // Find placeholder
    }

    @Override
    public int getItemCount() {
        if (mMovies != null) {
            return mMovies.size();
        }
        return 0;
    }

    public void setmMovies(List<Result> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }

    public Result getSelectedMovie(int position) {
        if (mMovies != null && mMovies.size() > 0) {
            return mMovies.get(position);
        }
        return null;
    }
}
