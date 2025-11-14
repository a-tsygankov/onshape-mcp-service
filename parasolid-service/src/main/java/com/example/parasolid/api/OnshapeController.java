package com.example.parasolid.api;

import com.example.onshape.api.DocumentsApi;
import com.example.onshape.api.WorkspacesApi;
import com.example.onshape.api.ElementsApi;
import com.example.onshape.model.DocumentSummary;
import com.example.onshape.model.WorkspaceSummary;
import com.example.onshape.model.ElementSummary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Very thin controller over the generated Onshape client.
 * Currently uses placeholder models generated from the stub OpenAPI spec.
 */
@RestController
@RequestMapping("/api/onshape")
public class OnshapeController {

    private final DocumentsApi documentsApi;
    private final WorkspacesApi workspacesApi;
    private final ElementsApi elementsApi;

    public OnshapeController(DocumentsApi documentsApi,
                             WorkspacesApi workspacesApi,
                             ElementsApi elementsApi) {
        this.documentsApi = documentsApi;
        this.workspacesApi = workspacesApi;
        this.elementsApi = elementsApi;
    }

    @GetMapping("/documents")
    public List<DocumentSummary> listDocuments() {
        return documentsApi.listDocuments();
    }

    @GetMapping("/workspaces")
    public List<WorkspaceSummary> listWorkspaces(@RequestParam("documentId") String documentId) {
        return workspacesApi.listWorkspaces(documentId);
    }

    @GetMapping("/elements")
    public List<ElementSummary> listElements(@RequestParam("documentId") String documentId,
                                             @RequestParam("workspaceId") String workspaceId) {
        return elementsApi.listElements(documentId, workspaceId);
    }
}
