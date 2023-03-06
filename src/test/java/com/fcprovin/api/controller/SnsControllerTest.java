package com.fcprovin.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcprovin.api.dto.request.create.SnsCreateRequest;
import com.fcprovin.api.dto.response.BaseResponse;
import com.fcprovin.api.dto.response.SnsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.fcprovin.api.docs.ApiDocumentUtils.getDocumentRequest;
import static com.fcprovin.api.docs.ApiDocumentUtils.getDocumentResponse;
import static com.fcprovin.api.entity.SnsType.GOOGLE;
import static java.time.LocalDateTime.now;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SnsController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.fcprovin.com")
class SnsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    SnsController snsController;

    @Test
    @WithMockUser
    void create() throws Exception {
        //given
        given(snsController
                .create(any(SnsCreateRequest.class)))
                .willReturn(new BaseResponse<>(SnsResponse.builder()
                        .id(1L)
                        .uuid("4d2d0eff-b7a7-4be5-adb3-b02427598362")
                        .type(GOOGLE)
                        .createdDate(now())
                        .updatedDate(now())
                        .build()));

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/sns").with(csrf())
                .content(mapper.writeValueAsString(SnsCreateRequest.builder()
                        .uuid("4d2d0eff-b7a7-4be5-adb3-b02427598362")
                        .type(GOOGLE)
                        .build())
                ).contentType(APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andDo(document("sns-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("uuid").type(STRING).description("SNS 고유 아이디").optional(),
                                fieldWithPath("type").type(STRING).description("SNS 플랫폼 - 'snsType' 공통 Code 참조").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.id").type(NUMBER).description("ID"),
                                fieldWithPath("result.uuid").type(STRING).description("SNS 고유 아이디"),
                                fieldWithPath("result.type").type(STRING).description("SNS 플랫폼 - 'snsType' 공통 Code 참조"),
                                fieldWithPath("result.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }

    @Test
    @WithMockUser
    void readAll() throws Exception {
        //given
        given(snsController
                .readAll())
                .willReturn(new BaseResponse<>(List.of(SnsResponse.builder()
                        .id(1L)
                        .uuid("4d2d0eff-b7a7-4be5-adb3-b02427598362")
                        .type(GOOGLE)
                        .createdDate(now())
                        .updatedDate(now())
                        .build())));

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/sns"));

        //then
        result.andExpect(status().isOk())
                .andDo(document("sns-readAll",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.[].id").type(NUMBER).description("ID"),
                                fieldWithPath("result.[].uuid").type(STRING).description("SNS 고유 아이디"),
                                fieldWithPath("result.[].type").type(STRING).description("SNS 플랫폼 - 'snsType' 공통 Code 참조"),
                                fieldWithPath("result.[].createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.[].updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }

    @Test
    @WithMockUser
    void read() throws Exception {
        //given
        given(snsController
                .read(any(Long.class)))
                .willReturn(new BaseResponse<>(SnsResponse.builder()
                        .id(1L)
                        .uuid("4d2d0eff-b7a7-4be5-adb3-b02427598362")
                        .type(GOOGLE)
                        .createdDate(now())
                        .updatedDate(now())
                        .build()));

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/sns/{id}", 1L));

        //then
        result.andExpect(status().isOk())
                .andDo(document("sns-read",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("SNS ID")
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.id").type(NUMBER).description("ID"),
                                fieldWithPath("result.uuid").type(STRING).description("SNS 고유 아이디"),
                                fieldWithPath("result.type").type(STRING).description("SNS 플랫폼 - 'snsType' 공통 Code 참조"),
                                fieldWithPath("result.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }
}
