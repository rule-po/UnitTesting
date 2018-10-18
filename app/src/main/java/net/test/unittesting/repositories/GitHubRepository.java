package net.test.unittesting.repositories;

import android.util.Log;

import net.test.unittesting.models.SearchResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GitHubRepository {

    private final GitHubApi githubApi;


    public GitHubRepository(final GitHubApi api){
        this.githubApi = api;
    }

    public void searchRepos(String query, final GithubRepositoryCallback callback) {
        Call<SearchResponse> call = githubApi.searchRepos(query);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call,
                                   Response<SearchResponse> response) {
                callback.handleGithubResponse(response);
            }

            @Override
            public void onFailure(Call<SearchResponse> call,
                                  Throwable t) {
                Log.e("", "", t);
                callback.handleGitHubError();
            }
        });
    }

    public interface GithubRepositoryCallback{
        void handleGithubResponse(Response<SearchResponse> response);
        void handleGitHubError();
    }
}
