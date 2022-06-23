package android.premiereappjava.mymovieapp.request;

import android.premiereappjava.mymovieapp.api.ApiConstants;
import android.premiereappjava.mymovieapp.api.MovieApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestService {

    private static final Retrofit.Builder retrofitBuilder =
            new Retrofit
            .Builder()
            .baseUrl(ApiConstants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static final Retrofit retrofit = retrofitBuilder.build();
    private static final MovieApi movieApi = retrofit.create(MovieApi.class);

    public static MovieApi getMovieApi(){
        return movieApi;
    }


}
