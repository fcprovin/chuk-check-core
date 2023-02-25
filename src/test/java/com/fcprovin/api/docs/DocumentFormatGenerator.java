package com.fcprovin.api.docs;

import org.springframework.restdocs.snippet.Attributes.Attribute;

public interface DocumentFormatGenerator {

    static Attribute getDateFormat() {
        return new Attribute("format", "yyyyMMdd");
    }
}
