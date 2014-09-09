package com.jayway.asyncservlet.github;

import com.jayway.asyncservlet.domain.RepoDto;
import com.jayway.asyncservlet.domain.RepoListDto;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureAdapter;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

class RepositoryListDtoAdapter extends ListenableFutureAdapter<RepoListDto, ResponseEntity<GitHubItems>> {

    private final String query;

    public RepositoryListDtoAdapter(String query, ListenableFuture<ResponseEntity<GitHubItems>> gitHubItems) {
        super(gitHubItems);
        this.query = query;
    }

    @Override
    protected RepoListDto adapt(ResponseEntity<GitHubItems> responseEntity) throws ExecutionException {
        GitHubItems gitHubItems = responseEntity.getBody();
        List<RepoDto> repoDtos = gitHubItems.items().stream().map(toRepositoryDto).collect(Collectors.toList());
        return new RepoListDto(query, gitHubItems.totalCount(), repoDtos);
    }

    private static Function<GitHubItem, RepoDto> toRepositoryDto = item -> {
        GitHubOwner owner = item.owner();
        return new RepoDto(item.fullName(), item.getUrl(), item.description(), owner.userName(), owner.url(), owner.avatarUrl());
    };
}
