package com.example.parasolid.embedding;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtException;
import ai.onnxruntime.OrtSession;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@Service
public class EmbeddingService {

    private static final Logger log = LoggerFactory.getLogger(EmbeddingService.class);

    private final EmbeddingConfig config;
    private final PointCloudPreprocessor preprocessor;

    private OrtEnvironment env;
    private OrtSession session;

    public EmbeddingService(EmbeddingConfig config) {
        this.config = config;
        this.preprocessor = new PointCloudPreprocessor(config.targetPointCount());
    }

    @PostConstruct
    public void init() {
        try {
            if (!Files.exists(config.modelPath())) {
                log.warn("Embedding model file does not exist at path: {}", config.modelPath());
            }

            this.env = OrtEnvironment.getEnvironment();
            var options = new OrtSession.SessionOptions();
            options.setIntraOpNumThreads(Runtime.getRuntime().availableProcessors());

            this.session = env.createSession(config.modelPath().toString(), options);
            log.info("Loaded ONNX model from {} (version: {})", config.modelPath(), config.modelVersion());

            if (config.warmupEnabled()) {
                runWarmup();
            }
        } catch (OrtException e) {
            throw new EmbeddingException("Failed to initialize ONNX Runtime session", e);
        }
    }

    @PreDestroy
    public void shutdown() {
        try {
            if (session != null) session.close();
        } catch (OrtException e) {
            log.warn("Error closing OrtSession", e);
        }
        try {
            if (env != null) env.close();
        } catch (OrtException e) {
            log.warn("Error closing OrtEnvironment", e);
        }
    }

    private void runWarmup() {
        try {
            log.info("Running warmup inference for embedding model...");
            float[] dummy = new float[config.targetPointCount() * 3];
            float[] result = runModel(dummy);
            validateDimensions(result);
            log.info("Warmup finished. Effective embedding dimension: {}", result.length);
        } catch (Exception e) {
            log.warn("Warmup inference failed: {}", e.getMessage(), e);
        }
    }

    private void validateDimensions(float[] embedding) {
        if (config.expectedDimensions() > 0 && embedding.length != config.expectedDimensions()) {
            throw new EmbeddingException("Embedding dimension mismatch. Expected "
                    + config.expectedDimensions() + " but got " + embedding.length);
        }
    }

    public float[] embed(List<Point3D> points) {
        if (session == null) {
            throw new EmbeddingException("ONNX Session is not initialized. Model path: " + config.modelPath());
        }
        if (points == null || points.isEmpty()) {
            throw new IllegalArgumentException("points must not be null or empty");
        }

        float[] tensor = preprocessor.preprocess(points);
        float[] embedding = runModel(tensor);
        validateDimensions(embedding);
        return embedding;
    }

    private float[] runModel(float[] tensor) {
        try {
            long[] shape = new long[] { 1, config.targetPointCount(), 3 };
            try (OnnxTensor input = OnnxTensor.createTensor(env, tensor, shape)) {
                String inputName = session.getInputNames().iterator().next();
                Map<String, OnnxTensor> inputs = Map.of(inputName, input);

                try (OrtSession.Result result = session.run(inputs)) {
                    String outputName = session.getOutputNames().iterator().next();
                    Object value = result.get(outputName).get().getValue();
                    if (value instanceof float[] floats) {
                        return floats;
                    } else if (value instanceof float[][] matrix && matrix.length > 0) {
                        return matrix[0];
                    } else {
                        throw new EmbeddingException("Unexpected ONNX output type: " + value.getClass());
                    }
                }
            }
        } catch (OrtException e) {
            throw new EmbeddingException("Error during ONNX inference", e);
        }
    }
}
