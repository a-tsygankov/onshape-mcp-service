package com.example.parasolid.embedding;

import java.util.ArrayList;
import java.util.List;

public class PointCloudPreprocessor {

    private final int targetPointCount;

    public PointCloudPreprocessor(int targetPointCount) {
        if (targetPointCount <= 0) {
            throw new IllegalArgumentException("targetPointCount must be positive");
        }
        this.targetPointCount = targetPointCount;
    }

    public int targetPointCount() {
        return targetPointCount;
    }

    public float[] preprocess(List<Point3D> points) {
        if (points == null || points.isEmpty()) {
            throw new IllegalArgumentException("points must not be null or empty");
        }

        var pts = new ArrayList<Point3D>(points);

        double cx = 0.0, cy = 0.0, cz = 0.0;
        for (Point3D p : pts) {
            cx += p.x();
            cy += p.y();
            cz += p.z();
        }
        int n = pts.size();
        cx /= n;
        cy /= n;
        cz /= n;

        double maxR = 0.0;
        var centered = new ArrayList<Point3D>(pts.size());
        for (Point3D p : pts) {
            double nx = p.x() - cx;
            double ny = p.y() - cy;
            double nz = p.z() - cz;
            centered.add(new Point3D(nx, ny, nz));
            double r = Math.sqrt(nx * nx + ny * ny + nz * nz);
            if (r > maxR) {
                maxR = r;
            }
        }

        if (maxR == 0.0) {
            maxR = 1.0;
        }

        var normalized = new ArrayList<Point3D>(centered.size());
        for (Point3D p : centered) {
            normalized.add(new Point3D(p.x() / maxR, p.y() / maxR, p.z() / maxR));
        }

        var fixed = new ArrayList<Point3D>(targetPointCount);
        if (normalized.size() >= targetPointCount) {
            fixed.addAll(normalized.subList(0, targetPointCount));
        } else {
            fixed.addAll(normalized);
            Point3D last = normalized.get(normalized.size() - 1);
            while (fixed.size() < targetPointCount) {
                fixed.add(last);
            }
        }

        float[] tensor = new float[targetPointCount * 3];
        int idx = 0;
        for (Point3D p : fixed) {
            tensor[idx++] = (float) p.x();
            tensor[idx++] = (float) p.y();
            tensor[idx++] = (float) p.z();
        }
        return tensor;
    }
}
