package net.test.unittesting.search;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.test.unittesting.models.SearchResponse;
import net.test.unittesting.models.SearchResult;
import net.test.unittesting.repositories.GitHubRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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

    @Test
    public void handleGithubResponse_Empty(){

        Response response = Mockito.mock(Response.class);
        Mockito.doReturn(true).when(response).isSuccessful();
        Mockito.doReturn(null).when(response).body();

        presenter.handleGithubResponse(response);

        Mockito.verify(viewContract, Mockito.times(1)).displayError("Error");
    }

    @Test
    public void handleGithubResponse_Error(){

        Response response = Mockito.mock(Response.class);
        Mockito.doReturn(false).when(response).isSuccessful();

        presenter.handleGithubResponse(response);

        Mockito.verify(viewContract, Mockito.times(1)).displayError("Error2");
    }

    @Test
    public void handleGithubResponse_PreDrefinedMockResult(){
        Response response = Mockito.mock(Response.class);
        Gson gson = new GsonBuilder().create();
        String jsonRaw = getJson("json/repos.json");
        SearchResponse searchResponses = gson.fromJson(jsonRaw, SearchResponse.class);
        //TODO: napravit test sa evaluacijom rezultata.

    }


    public String getJson(String path){
        URL url = this.getClass().getClassLoader().getResource(path);

        File file = new File(url.getPath());
        StringBuilder fileContents = new StringBuilder((int)file.length());

        try (Scanner scanner = new Scanner(file)) {
            while(scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + System.lineSeparator());
            }
            return fileContents.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

}
