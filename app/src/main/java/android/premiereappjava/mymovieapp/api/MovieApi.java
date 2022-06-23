package android.premiereappjava.mymovieapp.api;

import android.premiereappjava.mymovieapp.models.MovieDetailsModel;
import android.premiereappjava.mymovieapp.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    // GET movies
    @GET("/3/search/movie?")
    Call<MovieSearchResponse> searchMovies(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );


    // GET popular
    @GET("/3/movie/popular?")
    Call<MovieSearchResponse> showPopularMovies(
            @Query("api_key") String key
    );

    // GET unique by ID
    @GET("3/movie/{id}?")
    Call<MovieDetailsModel> getMovieById(
            @Path("id") int id,
            @Query("api_key") String key
    );

}
