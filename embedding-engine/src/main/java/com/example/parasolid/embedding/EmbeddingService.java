package com.example.parasolid.embedding;

import com.example.parasolid.core.PartGeometryInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Placeholder ONNX-based embedding service.
 * Currently returns a fixed vector; later wire in ONNX Runtime.
 */
@Service
public class EmbeddingService {

    private final String modelPath;

    public EmbeddingService(@Value("${embedding.model-path}") String modelPath) {
        this.modelPath = modelPath;
    }

    public float[] embed(PartGeometryInfo info) {
        // TODO: use ONNX Runtime with model at modelPath.
        // Deterministic dummy embedding for now.
        return new float[] {1.0f, 0.5f, 0.25f};
    }
}
