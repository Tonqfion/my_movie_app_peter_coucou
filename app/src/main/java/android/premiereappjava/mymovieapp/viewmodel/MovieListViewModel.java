package android.premiereappjava.mymovieapp.viewmodel;

import android.premiereappjava.mymovieapp.models.Result;
import android.premiereappjava.mymovieapp.repository.MovieRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    private MovieRepository movieRepository;

    // Live Data

    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<Result>> getMovies() {
        return movieRepository.getMovies();
    }

    public void searchMoviesApi(String query, int pageNumber) {
        movieRepository.searchMoviesApi(query, pageNumber);
    }

    public void searchNextPage() {
        movieRepository.searchNexPage();
    }
}
