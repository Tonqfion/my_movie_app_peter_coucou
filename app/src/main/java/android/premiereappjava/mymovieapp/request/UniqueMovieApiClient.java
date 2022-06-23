package android.premiereappjava.mymovieapp.request;

import android.premiereappjava.mymovieapp.AppExecutors;
import android.premiereappjava.mymovieapp.api.ApiConstants;
import android.premiereappjava.mymovieapp.models.MovieDetailsModel;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class UniqueMovieApiClient {

    private MovieDetailsModel mMovie;
    private static UniqueMovieApiClient instance;
    private UniqueMovieApiClient.RetrieveUniqueMovieRunnable retrieveUniqueMoviesRunnable;

    public static UniqueMovieApiClient getInstance() {
        if (instance == null) {
            instance = new UniqueMovieApiClient();
        }
        return instance;
    }

    private UniqueMovieApiClient() {

    }

    public MovieDetailsModel getMovie() {
        return mMovie;
    }

    public void searchUniqueMovie(int movieId) {
        if (retrieveUniqueMoviesRunnable != null) {
            retrieveUniqueMoviesRunnable = null;
        }
        retrieveUniqueMoviesRunnable = new UniqueMovieApiClient.RetrieveUniqueMovieRunnable(movieId);

        final Future<?> myHandler = AppExecutors.getInstance().networkIO().submit(retrieveUniqueMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
                // Timeout cancel retrofit call
            }
        }, 4000, TimeUnit.MILLISECONDS);
    }


    private class RetrieveUniqueMovieRunnable implements Runnable {
        private final int movieId;
        boolean cancelRequest;

        public RetrieveUniqueMovieRunnable(int movieId) {
            this.movieId = movieId;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                    Response<MovieDetailsModel> response = getMovieById(movieId).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    assert response.body() != null;
                    mMovie = response.body();
                    Log.v("Response", response.body().toString());
                } else {
                    assert response.errorBody() != null;
                    String error = response.errorBody().string();
                    Log.v("tag", "error" + error);
                    mMovie = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMovie = null;
            }

        }

        private Call<MovieDetailsModel> getMovieById(int movieId) {
            return RequestService.getMovieApi().getMovieById(
                    movieId,
                    ApiConstants.API_KEY
            );
        }


        private void cancelRequest(){
            Log.v("tag", "cancelling search request");
            cancelRequest = true;
        }

    }
}
