package main.adapter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LabworkApi {
    DELETE_BY_ID("/{id}"),
    GET_BY_ID("/{id}"),
    PUT_BY_ID("/{id}");

    private final String endpoint;

    public String buildUrl(String baseEndpoint, Object... args) {
        return baseEndpoint + String.format(endpoint.replaceAll("(\\{[^}]+})", "%s"), args);
    }
}