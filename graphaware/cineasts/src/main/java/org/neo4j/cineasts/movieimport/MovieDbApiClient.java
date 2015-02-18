package org.neo4j.cineasts.movieimport;

import org.codehaus.jackson.map.ObjectMapper;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MovieDbApiClient {

    private final String baseUrl = "https://api.themoviedb.org/3";
    private final String apiKey;
    protected final ObjectMapper mapper;

    public MovieDbApiClient(String apiKey) {
        this.apiKey = apiKey;
        mapper = new ObjectMapper();
    }

    public Map getMovie(String id) {
        return loadJsonData(id, buildMovieUrl(id));
    }

    private Map loadJsonData(String id, String url) {
        try {
            Map value = mapper.readValue(new URL(url), Map.class);
            if (value.isEmpty()) return Collections.singletonMap("not_found",System.currentTimeMillis());
            return value;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get data from " + url, e);
        }
    }

    private String buildMovieUrl(String movieId) {
        return String.format("%s/movie/%s?api_key=%s", baseUrl, movieId,apiKey);
    }

    public Map getPerson(String id) {
        return loadJsonData(id, buildPersonUrl(id));
    }

    private String buildPersonUrl(String personId) {
        return String.format("%s/person/%s?api_key=%s", baseUrl, personId, apiKey);
    }
}
