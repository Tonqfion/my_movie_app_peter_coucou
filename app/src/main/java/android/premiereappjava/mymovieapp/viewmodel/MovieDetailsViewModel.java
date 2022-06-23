package android.premiereappjava.mymovieapp.viewmodel;

import android.premiereappjava.mymovieapp.models.MovieDetailsModel;
import android.premiereappjava.mymovieapp.repository.MovieRepository;

import androidx.lifecycle.ViewModel;

public class MovieDetailsViewModel extends ViewModel {

    private MovieRepository movieRepository;
    // Live Data

    public MovieDetailsViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public MovieDetailsModel getMovie() {return  movieRepository.getUniqueMovie();}

    public void getUniqueMovieByIdApi(int movieId) {
        movieRepository.getUniqueMovieByIdApi(movieId);
    }
}
