package com.example.parasolid.api;

import com.example.parasolid.vector.SimilaritySearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/similarity")
public class SimilarityController {

    private final SimilaritySearchService similaritySearchService;

    public SimilarityController(SimilaritySearchService similaritySearchService) {
        this.similaritySearchService = similaritySearchService;
    }

    @GetMapping("/by-part")
    public SimilarityResult searchByPart(@RequestParam("partId") String partId,
                                         @RequestParam(name = "topK", defaultValue = "10") int topK) {
        return new SimilarityResult(partId, similaritySearchService.findSimilar(partId, topK));
    }

    public record Match(String partId, double score) {}

    public record SimilarityResult(String queryPartId, List<Match> matches) {}
}
