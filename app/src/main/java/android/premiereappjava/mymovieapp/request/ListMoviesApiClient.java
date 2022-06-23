package android.premiereappjava.mymovieapp.request;

import android.premiereappjava.mymovieapp.AppExecutors;
import android.premiereappjava.mymovieapp.api.ApiConstants;
import android.premiereappjava.mymovieapp.models.Result;
import android.premiereappjava.mymovieapp.response.MovieSearchResponse;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class ListMoviesApiClient {

    private MutableLiveData<List<Result>> mMovies;
    private static ListMoviesApiClient instance;
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    public static ListMoviesApiClient getInstance() {
        if (instance == null) {
            instance = new ListMoviesApiClient();
        }
        return instance;
    }

    private ListMoviesApiClient() {
        mMovies = new MutableLiveData<>();
    }

    public LiveData<List<Result>> getMovies() {
        return mMovies;
    }

    public void searchMoviesApi(String query, int pageNumber) {
        if (retrieveMoviesRunnable != null) {
            retrieveMoviesRunnable = null;
        }
        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        final Future<?> myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
                // Timeout cancel retrofit call
            }
        }, 4000, TimeUnit.MILLISECONDS);
    }


    private class RetrieveMoviesRunnable implements Runnable {

        private final String query;
        private final int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            Response<MovieSearchResponse> response;
            try {
                if (ApiConstants.SHOW_POPULAR_STRING.equals(query)) {
                    response = showPopularMovies().execute();
                } else {
                    response = getMovies(query, pageNumber).execute();
                }
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    assert response.body() != null;
                    List<Result> movieList = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());
                    if (pageNumber == 1) {
                        mMovies.postValue(movieList);
                    } else {
                        List<Result> currentMovies = mMovies.getValue();
                        assert currentMovies != null;
                        currentMovies.addAll(movieList);
                        mMovies.postValue(currentMovies);
                    }
                } else {
                    assert response.errorBody() != null;
                    String error = response.errorBody().string();
                    Log.v("tag", "error" + error);
                    mMovies.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }

        }

        private Call<MovieSearchResponse> getMovies(String query, int pageNumber) {
            return RequestService.getMovieApi().searchMovies(
                    ApiConstants.API_KEY,
                    query,
                    pageNumber
            );
        }

        private Call<MovieSearchResponse> showPopularMovies() {
            return RequestService.getMovieApi().showPopularMovies(
                    ApiConstants.API_KEY
            );
        }


        private void cancelRequest(){
            Log.v("tag", "cancelling search request");
            cancelRequest = true;
        }

    }

}
