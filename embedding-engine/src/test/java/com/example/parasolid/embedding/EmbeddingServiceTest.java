package com.example.parasolid.embedding;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmbeddingServiceTest {

    @Test
    void embedProducesVectorWhenModelExists() {
        Path model = Path.of("./models/shape-embed.onnx").toAbsolutePath().normalize();
        Assumptions.assumeTrue(Files.exists(model), "Model file not found at " + model + ", skipping test.");

        EmbeddingConfig config = new EmbeddingConfig(
                model.toString(),
                "test-v1",
                256,
                8,
                false
        );
        EmbeddingService service = new EmbeddingService(config);
        service.init();

        List<Point3D> pts = List.of(
                new Point3D(0, 0, 0),
                new Point3D(1, 0, 0),
                new Point3D(0, 1, 0),
                new Point3D(0, 0, 1)
        );

        float[] embedding = service.embed(pts);
        assertNotNull(embedding);
        assertEquals(256, embedding.length);

        service.shutdown();
    }
}
