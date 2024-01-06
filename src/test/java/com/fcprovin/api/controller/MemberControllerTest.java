package com.fcprovin.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcprovin.api.dto.request.create.MemberCreateRequest;
import com.fcprovin.api.dto.response.BaseResponse;
import com.fcprovin.api.dto.response.MemberResponse;
import com.fcprovin.api.dto.response.SnsResponse;
import com.fcprovin.api.dto.search.MemberSearch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
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
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.fcprovin.com")
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    MemberController memberController;

    @Test
    @WithMockUser
    void create() throws Exception {
        //given
        given(memberController
                .create(any(MemberCreateRequest.class)))
                .willReturn(new BaseResponse<>(MemberResponse.builder()
                        .id(1L)
                        .name("홍길동")
                        .email("hong@gmail.com")
                        .birthDate(LocalDate.of(1997, 3, 7))
                        .createdDate(now())
                        .updatedDate(now())
                        .build()));

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/member").with(csrf().asHeader())
                .content(mapper.writeValueAsString(MemberCreateRequest.builder()
                        .snsId(1L)
                        .name("홍길동")
                        .email("hong@gmail.com")
                        .birthDate(LocalDate.of(1997, 3, 7))
                        .build())
                ).contentType(APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andDo(document("member-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("snsId").type(NUMBER).description("SNS ID").optional(),
                                fieldWithPath("name").type(STRING).description("회원 이름").optional(),
                                fieldWithPath("email").type(STRING).description("회원 이메일").optional(),
                                fieldWithPath("birthDate").type(STRING).description("회원 생년월일").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.id").type(NUMBER).description("회원 ID"),
                                fieldWithPath("result.name").type(STRING).description("회원 이름"),
                                fieldWithPath("result.email").type(STRING).description("회원 이메일"),
                                fieldWithPath("result.birthDate").type(STRING).description("회원 생년월일"),
                                fieldWithPath("result.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }

    @Test
    @WithMockUser
    void readAll() throws Exception {
        //given
        given(memberController
                .readAll(any(MemberSearch.class)))
                .willReturn(new BaseResponse<>(List.of(MemberResponse.builder()
                        .id(1L)
                        .name("홍길동")
                        .email("hong@gmail.com")
                        .birthDate(LocalDate.of(1997, 3, 7))
                        .createdDate(now())
                        .updatedDate(now())
                        .sns(SnsResponse.builder()
                                .id(1L)
                                .uuid("4d2d0eff-b7a7-4be5-adb3-b02427598362")
                                .type(GOOGLE)
                                .createdDate(now())
                                .updatedDate(now())
                                .build())
                        .build())));

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/member")
                .queryParam("snsId", "1")
                .queryParam("name", "홍길동")
                .queryParam("email", "hong@gmail.com"));

        //then
        result.andExpect(status().isOk())
                .andDo(document("member-readAll",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("snsId").description("SNS ID"),
                                parameterWithName("name").description("회원 이름"),
                                parameterWithName("email").description("회원 이메일")
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.[].id").type(NUMBER).description("ID"),
                                fieldWithPath("result.[].name").type(STRING).description("회원 이름"),
                                fieldWithPath("result.[].email").type(STRING).description("회원 이메일"),
                                fieldWithPath("result.[].birthDate").type(STRING).description("회원 생년월일"),
                                fieldWithPath("result.[].createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.[].updatedDate").type(STRING).description("수정일자"),
                                fieldWithPath("result.[].sns").type(OBJECT).description("SNS"),
                                fieldWithPath("result.[].sns.id").type(NUMBER).description("SNS ID"),
                                fieldWithPath("result.[].sns.uuid").type(STRING).description("SNS 고유 아이디"),
                                fieldWithPath("result.[].sns.type").type(STRING).description("SNS 플랫폼 - 'snsType' 공통 Code 참조"),
                                fieldWithPath("result.[].sns.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.[].sns.updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }

    @Test
    @WithMockUser
    void read() throws Exception {
        //given
        given(memberController
                .read(any(Long.class)))
                .willReturn(new BaseResponse<>(MemberResponse.builder()
                        .id(1L)
                        .name("홍길동")
                        .email("hong@gmail.com")
                        .birthDate(LocalDate.of(1997, 3, 7))
                        .createdDate(now())
                        .updatedDate(now())
                        .sns(SnsResponse.builder()
                                .id(1L)
                                .uuid("4d2d0eff-b7a7-4be5-adb3-b02427598362")
                                .type(GOOGLE)
                                .createdDate(now())
                                .updatedDate(now())
                                .build())
                        .build()));

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/member/{id}", 1L));

        //then
        result.andExpect(status().isOk())
                .andDo(document("member-read",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("회원 ID")
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.id").type(NUMBER).description("ID"),
                                fieldWithPath("result.name").type(STRING).description("회원 이름"),
                                fieldWithPath("result.email").type(STRING).description("회원 이메일"),
                                fieldWithPath("result.birthDate").type(STRING).description("회원 생년월일"),
                                fieldWithPath("result.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.updatedDate").type(STRING).description("수정일자"),
                                fieldWithPath("result.sns").type(OBJECT).description("SNS"),
                                fieldWithPath("result.sns.id").type(NUMBER).description("SNS ID"),
                                fieldWithPath("result.sns.uuid").type(STRING).description("SNS 고유 아이디"),
                                fieldWithPath("result.sns.type").type(STRING).description("SNS 플랫폼 - 'snsType' 공통 Code 참조"),
                                fieldWithPath("result.sns.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.sns.updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }
}
