# Embedding Engine Module (Drop-in)

This archive contains a drop-in `embedding-engine` Gradle module that provides:

- Java ONNX Runtime-based `EmbeddingService`
- Point cloud → tensor preprocessing (`PointCloudPreprocessor`)
- Optimized ONNX session initialization with warmup
- Embedding dimension validation
- Basic JUnit test harness

## Integration

1. Copy `embedding-engine/` into your project root.

2. Add to `settings.gradle.kts`:

   ```kotlin
   include("embedding-engine")
   ```

3. In the module that uses embeddings:

   ```kotlin
   implementation(project(":embedding-engine"))
   ```

4. Add properties to your `application.yml`:

   ```yaml
   embedding:
     model-path: "./models/shape-embed.onnx"
     model-version: "v1"
     expected-dimensions: 256
     target-point-count: 2048
     warmup-enabled: true
   ```

5. Place your ONNX model at `./models/shape-embed.onnx` or adjust `model-path`.

6. Inject and use `EmbeddingService` in Spring:

   ```java
   @RestController
   class ExampleController {
       private final EmbeddingService embeddingService;

       ExampleController(EmbeddingService embeddingService) {
           this.embeddingService = embeddingService;
       }
   }
   ```
