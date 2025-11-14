package com.example.parasolid.embedding;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PointCloudPreprocessorTest {

    @Test
    void preprocessProducesCorrectLengthAndNormalization() {
        PointCloudPreprocessor pre = new PointCloudPreprocessor(4);

        List<Point3D> pts = List.of(
                new Point3D(1, 0, 0),
                new Point3D(0, 1, 0),
                new Point3D(0, 0, 1)
        );

        float[] tensor = pre.preprocess(pts);
        assertEquals(4 * 3, tensor.length);

        for (int i = 0; i < tensor.length; i += 3) {
            double r = Math.sqrt(
                    tensor[i] * tensor[i] +
                    tensor[i + 1] * tensor[i + 1] +
                    tensor[i + 2] * tensor[i + 2]
            );
            assertTrue(r <= 1.0001, "Point radius should be <= 1 but was " + r);
        }
    }

    @Test
    void throwsOnEmptyInput() {
        PointCloudPreprocessor pre = new PointCloudPreprocessor(4);
        assertThrows(IllegalArgumentException.class, () -> pre.preprocess(List.of()));
    }
}
