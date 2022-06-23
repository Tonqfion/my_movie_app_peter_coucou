package android.premiereappjava.mymovieapp.repository;

import android.premiereappjava.mymovieapp.models.MovieDetailsModel;
import android.premiereappjava.mymovieapp.models.Result;
import android.premiereappjava.mymovieapp.request.ListMoviesApiClient;
import android.premiereappjava.mymovieapp.request.UniqueMovieApiClient;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MovieRepository {


    private static MovieRepository instance;

    private final ListMoviesApiClient mListMoviesApiClient;
    private final UniqueMovieApiClient mUniqueMovieApiClient;
    private String mQuery;
    private int mPageNumber;




    public static MovieRepository getInstance() {
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository() {
        // not instanciable
        mListMoviesApiClient = ListMoviesApiClient.getInstance();
        mUniqueMovieApiClient = UniqueMovieApiClient.getInstance();
    }

    public LiveData<List<Result>> getMovies() {return mListMoviesApiClient.getMovies();}

    public MovieDetailsModel getUniqueMovie() {return  mUniqueMovieApiClient.getMovie();}

    public void searchMoviesApi(String query, int pageNumber) {
        mQuery = query;
        mPageNumber = pageNumber;
        mListMoviesApiClient.searchMoviesApi(query, pageNumber);
    }

    public void getUniqueMovieByIdApi(int movieId) {
        mUniqueMovieApiClient.searchUniqueMovie(movieId);
    }

    public void searchNexPage() {
        mPageNumber++;
        mListMoviesApiClient.searchMoviesApi(mQuery, mPageNumber);

    }
}
