package com.fcprovin.api.docs;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

public interface ApiDocumentUtils {

    static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(modifyUris()
                        .scheme("https")
                        .host("api.fcprovin.com")
                        .removePort(),
                removeHeaders(
                        "Content-Length",
                        "X-CSRF-TOKEN"
                ),
                prettyPrint());
    }

    static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(
                removeHeaders(
                        "X-Content-Type-Options",
                        "X-XSS-Protection",
                        "Cache-Control",
                        "Pragma",
                        "Expires",
                        "Strict-Transport-Security",
                        "X-Frame-Options",
                        "Content-Length"
                ),
                prettyPrint());
    }
}
