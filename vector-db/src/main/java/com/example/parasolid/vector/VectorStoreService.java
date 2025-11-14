package com.example.parasolid.vector;

import com.example.parasolid.core.PartGeometryInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class VectorStoreService {

    private final String qdrantUrl;
    private final ObjectMapper mapper = new ObjectMapper();

    public VectorStoreService(@Value("${qdrant.url}") String qdrantUrl) {
        this.qdrantUrl = qdrantUrl;
    }

    public void upsertEmbedding(String partId, float[] vector, PartGeometryInfo info) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("edges", info.edges());
            payload.put("faces", info.faces());
            payload.put("vertices", info.vertices());

            Map<String, Object> point = new HashMap<>();
            point.put("id", partId);
            point.put("vector", vector);
            point.put("payload", payload);

            Map<String, Object> body = new HashMap<>();
            body.put("points", new Object[]{ point });

            String json = mapper.writeValueAsString(body);

            Request.put(qdrantUrl + "/collections/parts/points")
                    .bodyString(json, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent();
        } catch (Exception e) {
            throw new RuntimeException("Failed to upsert embedding into Qdrant", e);
        }
    }
}
