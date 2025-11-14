package com.example.parasolid.embedding;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class EmbeddingConfig {

    private final Path modelPath;
    private final String modelVersion;
    private final int expectedDimensions;
    private final int targetPointCount;
    private final boolean warmupEnabled;

    public EmbeddingConfig(
            @Value("${embedding.model-path:./models/shape-embed.onnx}") String modelPath,
            @Value("${embedding.model-version:v1}") String modelVersion,
            @Value("${embedding.expected-dimensions:256}") int expectedDimensions,
            @Value("${embedding.target-point-count:2048}") int targetPointCount,
            @Value("${embedding.warmup-enabled:true}") boolean warmupEnabled
    ) {
        this.modelPath = Path.of(modelPath).toAbsolutePath().normalize();
        this.modelVersion = modelVersion;
        this.expectedDimensions = expectedDimensions;
        this.targetPointCount = targetPointCount;
        this.warmupEnabled = warmupEnabled;
    }

    public Path modelPath() { return modelPath; }
    public String modelVersion() { return modelVersion; }
    public int expectedDimensions() { return expectedDimensions; }
    public int targetPointCount() { return targetPointCount; }
    public boolean warmupEnabled() { return warmupEnabled; }
}
