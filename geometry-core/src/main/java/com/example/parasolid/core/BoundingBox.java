package com.example.parasolid.core;

public record BoundingBox(
        double minX, double minY, double minZ,
        double maxX, double maxY, double maxZ
) {
}
