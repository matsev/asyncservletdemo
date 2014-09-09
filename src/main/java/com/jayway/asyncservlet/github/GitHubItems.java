package com.jayway.asyncservlet.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
class GitHubItems {

    @JsonProperty("total_count")
    private int totalCount;

    @JsonProperty("items")
    private List<GitHubItem> items;

    int totalCount() {
        return totalCount;
    }

    List<GitHubItem> items() {
        return items;
    }

}
