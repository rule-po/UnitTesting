package net.test.unittesting.search;

import net.test.unittesting.models.SearchResponse;
import net.test.unittesting.models.SearchResult;
import net.test.unittesting.repositories.GitHubRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import retrofit2.Response;


public class SearchPresenterTest {

    /*
     * testirat req s query
     * query == null
     * query = ""
     * succ
     * err
     * prazan respons
     * */

    private SearchPresenter presenter;

    @Mock
    private SearchViewContract viewContract;

    @Mock
    GitHubRepository repository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        presenter = Mockito.spy(new SearchPresenter(viewContract, repository));
    }

    @Test
    public void searchGithubRepos(){
        String query = "test";

        presenter.searchGithubRepos(query);

        Mockito.verify(repository, Mockito.times(1)).searchRepos(query, presenter);
    }

    @Test
    public void searchGithubRepos_NullQuery(){
        presenter.searchGithubRepos(null);
        Mockito.verify(repository, Mockito.never()).searchRepos(null, presenter);
    }

    @Test
    public void searchGithubRepos_EmptyQuery(){
        String query = "";
        presenter.searchGithubRepos(query);
        Mockito.verify(repository, Mockito.never()).searchRepos(query, presenter);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void handleGithubResponse_Success(){
        Response response = Mockito.mock(Response.class);
        SearchResponse searchResponse = Mockito.mock(SearchResponse.class);
        Mockito.doReturn(true).when(response).isSuccessful();
        Mockito.doReturn(searchResponse).when(response).body();
        List<SearchResult> searchResults = Collections.singletonList(new SearchResult());
        Mockito.doReturn(searchResults).when(searchResponse).getSearchResults();

        presenter.handleGithubResponse(response);

        Mockito.verify(viewContract, Mockito.times(1)).displaySearchResult(searchResults, 0);
    }

}
