package net.test.unittesting.search;

import net.test.unittesting.models.SearchResponse;
import net.test.unittesting.repositories.GitHubRepository;

import retrofit2.Response;

public class SearchPresenter implements SearchPresenterContract, GitHubRepository.GithubRepositoryCallback {

    private final SearchViewContract viewContract;
    private final GitHubRepository repository;

    public SearchPresenter(SearchViewContract viewContract, GitHubRepository repository) {
        this.viewContract = viewContract;
        this.repository = repository;
    }

    @Override
    public void searchGithubRepos(String query) {
        if (query != null && query.length() > 0)
            repository.searchRepos(query, this);
    }

    @Override
    public void handleGithubResponse(Response<SearchResponse> response) {
        if (response.isSuccessful()){
            SearchResponse searchResponse = response.body();
            if (searchResponse != null && searchResponse.getSearchResults() != null){
                viewContract.displaySearchResult(searchResponse.getSearchResults(), searchResponse.getTotalCount());
            }else{
                viewContract.displayError("Error");
            }
        }else {
            viewContract.displayError("Error");
        }

    }

    @Override
    public void handleGitHubError() {
        viewContract.displayError();
    }


}
