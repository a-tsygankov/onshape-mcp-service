package com.example.parasolid.core;

public record PartGeometryInfo(
        BoundingBox boundingBox,
        int edges,
        int faces,
        int vertices
) {
}
