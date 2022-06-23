package android.premiereappjava.mymovieapp.response;

import android.premiereappjava.mymovieapp.models.Result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieSearchResponse {
    @SerializedName("total_results")
    @Expose
    private int totalResults;

    @SerializedName("results")
    @Expose
    private List<Result> movies;

    public int getTotalResults() {
        return totalResults;
    }

    public List<Result> getMovies() {
        return movies;
    }

    @Override
    public String toString() {
        return "MovieSearchResponse{" +
                "totalResults=" + totalResults +
                ", movies=" + movies +
                '}';
    }
}
