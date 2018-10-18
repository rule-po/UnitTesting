package net.test.unittesting.repositories;

import net.test.unittesting.models.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GitHubApi {
    @Headers({"Accept: application/vnd.github.mercy-preview+json"})
    @GET("search/repositories")
    Call<SearchResponse> searchRepos(@Query("q") String term);
}
