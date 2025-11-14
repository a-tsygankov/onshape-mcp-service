# Parasolid Embedding Service (Local-First)

This project is a **local-first** Spring Boot + Jetty + Qdrant + ONNX-based service that:

- Connects to a local Onshape dev instance
- Exports Parasolid geometry for parts
- Runs a local ONNX model to create embeddings
- Stores embeddings in Qdrant
- Exposes REST APIs (documented with OpenAPI) for:
  - Onshape document/workspace/part discovery
  - Embedding generation
  - Similarity search

## Modules

- `parasolid-service` — Spring Boot 3.3 + Jetty REST API
- `geometry-core` — Parasolid / geometry analysis abstractions
- `embedding-engine` — ONNX model loader and inference
- `vector-db` — Qdrant client and similarity search
- `onshape-client` — Onshape REST client generated from OpenAPI

## Quick Start

1. Start Qdrant locally:

   ```bash
   ./scripts/run-qdrant.sh
   ```

2. (Optional) Start ONNX model container:

   ```bash
   ./scripts/run-onnx-model.sh
   ```

3. Build and run the Spring Boot service:

   ```bash
   ./gradlew :parasolid-service:bootRun
   ```

4. Open API docs:

   - Swagger UI: http://localhost:8080/swagger-ui.html
   - OpenAPI JSON: http://localhost:8080/v3/api-docs

## IntelliJ IDEA Setup

Run:

```bash
./scripts/setup-idea.sh
```

to install recommended plugins (Docker, Kubernetes, Spring, OpenAPI, etc.).

## Rancher Desktop

Rancher Desktop provides a Docker-compatible CLI. All scripts here use `docker compose` and work with Rancher Desktop as long as the Docker context is set correctly.
