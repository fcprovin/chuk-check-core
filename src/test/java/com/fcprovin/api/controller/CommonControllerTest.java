package com.fcprovin.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcprovin.api.docs.CustomResponseFieldsSnippet;
import com.fcprovin.api.docs.EnumDocs;
import com.fcprovin.api.dto.response.BaseResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommonController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.fcprovin.com")
class CommonControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    @WithMockUser
    public void enums() throws Exception {
        ResultActions result = this.mockMvc.perform(get("/test/enums"));

        MvcResult mvcResult = result.andReturn();
        EnumDocs enumDocs = getData(mvcResult);

        result.andExpect(status().isOk())
                .andDo(document("common-code",
                        customResponseFields("custom-response",
                                beneathPath("result.jwtRole").withSubsectionId("jwtRole"),
                                attributes(key("title").value("jwtRole")),
                                enumConvertFieldDescriptor((enumDocs.getJwtRole()))
                        ),
                        customResponseFields("custom-response",
                                beneathPath("result.attendStatus").withSubsectionId("attendStatus"),
                                attributes(key("title").value("attendStatus")),
                                enumConvertFieldDescriptor((enumDocs.getAttendStatus()))
                        ),
                        customResponseFields("custom-response",
                                beneathPath("result.baseStatus").withSubsectionId("baseStatus"),
                                attributes(key("title").value("baseStatus")),
                                enumConvertFieldDescriptor((enumDocs.getBaseStatus()))
                        ),
                        customResponseFields("custom-response",
                                beneathPath("result.matchStatus").withSubsectionId("matchStatus"),
                                attributes(key("title").value("matchStatus")),
                                enumConvertFieldDescriptor((enumDocs.getMatchStatus()))
                        ),
                        customResponseFields("custom-response",
                                beneathPath("result.playerAuthority").withSubsectionId("playerAuthority"),
                                attributes(key("title").value("playerAuthority")),
                                enumConvertFieldDescriptor((enumDocs.getPlayerAuthority()))
                        ),
                        customResponseFields("custom-response",
                                beneathPath("result.position").withSubsectionId("position"),
                                attributes(key("title").value("position")),
                                enumConvertFieldDescriptor((enumDocs.getPosition()))
                        ),
                        customResponseFields("custom-response",
                                beneathPath("result.snsType").withSubsectionId("snsType"),
                                attributes(key("title").value("snsType")),
                                enumConvertFieldDescriptor((enumDocs.getSnsType()))
                        ),
                        customResponseFields("custom-response",
                                beneathPath("result.voteStatus").withSubsectionId("voteStatus"),
                                attributes(key("title").value("voteStatus")),
                                enumConvertFieldDescriptor((enumDocs.getVoteStatus()))
                        )
                ));
    }

    public CustomResponseFieldsSnippet customResponseFields(String type,
                                 PayloadSubsectionExtractor<?> subsectionExtractor,
                                 Map<String, Object> attributes,
                                 FieldDescriptor... descriptors) {
        return new CustomResponseFieldsSnippet(type, asList(descriptors), attributes, true, subsectionExtractor);
    }

    private FieldDescriptor[] enumConvertFieldDescriptor(Map<String, String> enumValues) {
        return enumValues.entrySet().stream()
                .map(x -> fieldWithPath(x.getKey()).description(x.getValue()))
                .toArray(FieldDescriptor[]::new);
    }

    private EnumDocs getData(MvcResult result) throws IOException {
        BaseResponse<EnumDocs> response = mapper
                .readValue(result.getResponse().getContentAsByteArray(), new TypeReference<>() {});
        return response.getResult();
    }
}