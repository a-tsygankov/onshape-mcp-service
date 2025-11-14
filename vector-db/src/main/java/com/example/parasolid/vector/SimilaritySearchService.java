package com.example.parasolid.vector;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimilaritySearchService {

    private final String qdrantUrl;
    private final ObjectMapper mapper = new ObjectMapper();

    public SimilaritySearchService(@Value("${qdrant.url}") String qdrantUrl) {
        this.qdrantUrl = qdrantUrl;
    }

    public List<com.example.parasolid.api.SimilarityController.Match> findSimilar(String partId, int topK) {
        try {
            String json = "{"
                    + ""limit":" + topK + ","
                    + ""filter":{"must":[{"key":"edges","match":{"value":120}}]}"
                    + "}";

            String response = Request.post(qdrantUrl + "/collections/parts/points/scroll")
                    .bodyString(json, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

            JsonNode root = mapper.readTree(response);
            List<com.example.parasolid.api.SimilarityController.Match> matches = new ArrayList<>();
            if (root.has("result") && root.get("result").has("points")) {
                for (JsonNode p : root.get("result").get("points")) {
                    String id = p.get("id").asText();
                    double score = 1.0; // placeholder
                    matches.add(new com.example.parasolid.api.SimilarityController.Match(id, score));
                }
            }
            return matches;
        } catch (Exception e) {
            throw new RuntimeException("Failed to search similar parts in Qdrant", e);
        }
    }
}
