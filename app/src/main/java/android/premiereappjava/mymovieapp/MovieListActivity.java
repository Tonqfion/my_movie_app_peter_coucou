package android.premiereappjava.mymovieapp;

import android.content.Intent;
import android.os.Bundle;
import android.premiereappjava.mymovieapp.adapter.MovieRecyclerViewAdapter;
import android.premiereappjava.mymovieapp.adapter.OnMovieListener;
import android.premiereappjava.mymovieapp.api.ApiConstants;
import android.premiereappjava.mymovieapp.models.Result;
import android.premiereappjava.mymovieapp.viewmodel.MovieListViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {

    private RecyclerView recyclerView;
    private MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    private MovieListViewModel movieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupSearchView();
        recyclerView = findViewById(R.id.recyclerView);
        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
        observeDataChange();
        configureRecyclerView();
        movieListViewModel.searchMoviesApi(ApiConstants.SHOW_POPULAR_STRING, 1);
    }

    private void observeDataChange(){
        movieListViewModel.getMovies().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                if (results != null) {
                        movieRecyclerViewAdapter.setmMovies(results);
                }
            }
        });
    }

    private void configureRecyclerView() {
        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter( this);
        recyclerView.setAdapter(movieRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)) {
                    movieListViewModel.searchNextPage();
                }
            }
        });
    }

    @Override
    public void onMovieClick(int position) {
        // Toast.makeText(this, "The position is " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("movieId", movieRecyclerViewAdapter.getSelectedMovie(position).getId());
        startActivity(intent);
    }

    private void setupSearchView() {
        final SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMoviesApi(query, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}