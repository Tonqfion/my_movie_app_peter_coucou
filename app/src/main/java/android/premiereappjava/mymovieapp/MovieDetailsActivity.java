package android.premiereappjava.mymovieapp;

import android.os.Bundle;
import android.premiereappjava.mymovieapp.api.ApiConstants;
import android.premiereappjava.mymovieapp.models.MovieDetailsModel;
import android.premiereappjava.mymovieapp.request.RequestService;
import android.premiereappjava.mymovieapp.viewmodel.MovieDetailsViewModel;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView imageViewDetails;
    private TextView titleDetails, descriptionDetails;
    private RatingBar ratingBarDetail;
    private MovieDetailsViewModel movieDetailsViewModel;
    private MovieDetailsModel movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        movieDetailsViewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);

        imageViewDetails = findViewById(R.id.imageView_details);
        titleDetails = findViewById(R.id.textView_title_details);
        descriptionDetails = findViewById(R.id.textView_description_details);
        ratingBarDetail = findViewById(R.id.ratingBar_details);
        int moviedId = (int) getIntent().getExtras().get("movieId");

        RequestService.getMovieApi().getMovieById(moviedId, ApiConstants.API_KEY).enqueue(new Callback<>() {

            @Override
            public void onResponse(@NonNull Call<MovieDetailsModel> call, @NonNull Response<MovieDetailsModel> response) {
                assert response.body() != null;
                titleDetails.setText(response.body().getTitle());

            }

            @Override
            public void onFailure(@NonNull Call<MovieDetailsModel> call, @NonNull Throwable t) {

            }
        });


    }




    private void getDataFromIntent() {
        if (getIntent().hasExtra("movieId")) {
            int moviedId = (int) getIntent().getExtras().get("movieId");
            movieDetailsViewModel.getUniqueMovieByIdApi(moviedId);
        }
    }
}