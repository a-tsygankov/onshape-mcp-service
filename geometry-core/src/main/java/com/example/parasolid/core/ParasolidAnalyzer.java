package com.example.parasolid.core;

import org.springframework.stereotype.Service;

/**
 * Placeholder Parasolid analyzer.
 * Later you will plug in a real Parasolid SDK and geometry kernel.
 */
@Service
public class ParasolidAnalyzer {

    public PartGeometryInfo analyze(byte[] parasolidBytes) {
        // TODO: integrate real Parasolid parsing
        BoundingBox bbox = new BoundingBox(0, 0, 0, 100, 80, 60);
        return new PartGeometryInfo(bbox, 120, 64, 32);
    }
}
