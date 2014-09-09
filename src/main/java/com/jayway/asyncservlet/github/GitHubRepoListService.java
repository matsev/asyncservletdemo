package com.jayway.asyncservlet.github;

import com.jayway.asyncservlet.domain.RepoListDto;
import com.jayway.asyncservlet.domain.RepoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

@Service
class GitHubRepoListService implements RepoListService {
    // API spec https://developer.github.com/v3/search/#search-repositories
    // https://developer.github.com/v3/#pagination
    // https://developer.github.com/v3/#rate-limiting
    private static final String QUESTIONS_URL = "https://api.github.com/search/repositories?q={query}";

    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

    @Override
    public ListenableFuture<RepoListDto> search(String query) {
        ListenableFuture<ResponseEntity<GitHubItems>> gitHubItems = asyncRestTemplate.getForEntity(QUESTIONS_URL, GitHubItems.class, query);
        return new RepositoryListDtoAdapter(query, gitHubItems);
    }
}
