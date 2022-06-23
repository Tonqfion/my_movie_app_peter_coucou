package android.premiereappjava.mymovieapp.api;

public class ApiConstants {

    public static final String API_BASE_URL = "https://api.themoviedb.org";
    public static final String POSTER_BASE_URL_LARGE = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/";
    public static final String POSTER_BASE_URL_SMALL = "https://image.tmdb.org/t/p/w500";
    public static final String API_KEY = "37cdcb82feacdac36e5bf8a9f1f196f1";
    public static final String UNIQUE_MOVIE_DETAILS_BY_ID_URL = API_BASE_URL + "movie/";
    public static final String SEARCH_MOVIE_URL = API_BASE_URL + "search/movie?";
    public static final String FIND_MOVIE_BY_EXTERNAL_ID_URL = API_BASE_URL + "external/";
    public static final String SHOW_POPULAR_STRING = "SHOW_POPULAR_ON_LOAD_PAGE";

    private ApiConstants() {
        // pas d'instanciation
    }
}
