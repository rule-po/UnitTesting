package net.test.unittesting.search;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.test.unittesting.SearchResultRVAdapter;
import net.test.unittesting.repositories.GitHubApi;
import net.test.unittesting.R;
import net.test.unittesting.models.SearchResponse;
import net.test.unittesting.models.SearchResult;
import net.test.unittesting.repositories.GitHubRepository;

import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity implements SearchViewContract {

    private SearchResultRVAdapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etSearchQuery = findViewById(R.id.et_search_query);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubRepository repository = new GitHubRepository(retrofit.create(GitHubApi.class));
        final SearchPresenterContract presenter = new SearchPresenter(this, repository);

        etSearchQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v,
                                          int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    presenter.searchGithubRepos(etSearchQuery.getText().toString());
                    return true;
                }
                return false;
            }
        });

        RecyclerView rvRepos = findViewById(R.id.rv_repos);
        rvAdapter = new SearchResultRVAdapter();
        rvRepos.setHasFixedSize(true);
        rvRepos.setAdapter(rvAdapter);
    }


    private void handleResponse(@NonNull Response<SearchResponse> response) {
        if (response.isSuccessful()) {
            SearchResponse searchResponse = response.body();
            if (searchResponse != null) {
                handleSearchResults(searchResponse.getSearchResults());
            } else {
                handleError("E102 - System error");
            }
        } else {
            handleError("E101 - System error");
        }
    }

    private void handleError(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void handleSearchResults(List<SearchResult> searchResults) {

    }

    @Override
    public void displaySearchResult(List<SearchResult> searchResults, Integer totalCount) {
        rvAdapter.updateResults(searchResults);
    }

    @Override
    public void displayError() {

    }

    @Override
    public void displayError(String message) {

    }
}
