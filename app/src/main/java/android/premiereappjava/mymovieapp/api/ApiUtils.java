package android.premiereappjava.mymovieapp.api;

import android.icu.text.MessageFormat;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class ApiUtils {


    private ApiUtils() {
        // pas d'instanciation
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String findUniqueMovieByIdUrl(int movieId) {
        return MessageFormat.format(" {}{}{}", ApiConstants.UNIQUE_MOVIE_DETAILS_BY_ID_URL, movieId, ApiConstants.API_KEY);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String searchMoviesByTitleStringUrl(String searchString) {
        return MessageFormat.format(" {}{}{}", ApiConstants.SEARCH_MOVIE_URL, searchString, ApiConstants.API_KEY);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String findMovieByExternalId(String externalId) {
        return MessageFormat.format(" {}{}{}", ApiConstants.FIND_MOVIE_BY_EXTERNAL_ID_URL, externalId, ApiConstants.API_KEY);
    }
}
