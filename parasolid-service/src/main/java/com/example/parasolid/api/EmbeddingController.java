package com.example.parasolid.api;

import com.example.parasolid.core.PartGeometryInfo;
import com.example.parasolid.core.ParasolidAnalyzer;
import com.example.parasolid.embedding.EmbeddingService;
import com.example.parasolid.vector.VectorStoreService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/embedding")
public class EmbeddingController {

    private final ParasolidAnalyzer analyzer;
    private final EmbeddingService embeddingService;
    private final VectorStoreService vectorStoreService;

    public EmbeddingController(ParasolidAnalyzer analyzer,
                               EmbeddingService embeddingService,
                               VectorStoreService vectorStoreService) {
        this.analyzer = analyzer;
        this.embeddingService = embeddingService;
        this.vectorStoreService = vectorStoreService;
    }

    @PostMapping(value = "/from-parasolid", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public EmbeddingResponse embedFromParasolid(@RequestParam("file") MultipartFile file,
                                                @RequestParam("partId") String partId) throws Exception {
        byte[] data = file.getBytes();
        PartGeometryInfo info = analyzer.analyze(data);
        float[] embedding = embeddingService.embed(info);
        vectorStoreService.upsertEmbedding(partId, embedding, info);
        return new EmbeddingResponse(partId, embedding.length);
    }

    public record EmbeddingResponse(String partId, int dimensions) {}
}
