package com.jayway.asyncservlet.domain;

import org.springframework.util.concurrent.ListenableFuture;

public interface RepoListService {

    ListenableFuture<RepoListDto> search(String query);
}
