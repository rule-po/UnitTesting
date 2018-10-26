package net.test.unittesting.repositories;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import retrofit2.Call;
import retrofit2.Callback;

public class GithubRepositoryTest {
    private  GitHubRepository repository;

    @Mock
    GitHubApi gitHubApi;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        repository = Mockito.spy(new GitHubRepository(gitHubApi));
    }

    @Test
    public void searchRepos(){
       GitHubRepository.GithubRepositoryCallback githubRepositoryCallback
               = Mockito.mock(GitHubRepository.GithubRepositoryCallback.class);

        Call call = Mockito.mock(Call.class);
        String query = "test";

        Mockito.doReturn(call).when(gitHubApi).searchRepos(query);
        Mockito.doNothing().when(call).enqueue(Mockito.any(Callback.class));

        repository.searchRepos(query,githubRepositoryCallback);
        Mockito.verify(gitHubApi, Mockito.times(1)).searchRepos(query);
    }

}
