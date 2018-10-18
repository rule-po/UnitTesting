package net.test.unittesting.search;

import net.test.unittesting.models.SearchResult;

import java.util.List;

public interface SearchViewContract {
    void displaySearchResult(List<SearchResult> searchResults, Integer totalCount);
    void displayError();
    void displayError(String message);
}
