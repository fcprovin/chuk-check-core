package com.fcprovin.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcprovin.api.dto.jwt.JwtCreate;
import com.fcprovin.api.dto.request.create.TokenCreateRequest;
import com.fcprovin.api.dto.response.BaseResponse;
import com.fcprovin.api.dto.response.TokenResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static com.fcprovin.api.docs.ApiDocumentUtils.getDocumentRequest;
import static com.fcprovin.api.docs.ApiDocumentUtils.getDocumentResponse;
import static com.fcprovin.api.dto.jwt.JwtRole.ROLE_USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.fcprovin.com")
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    AuthController authController;

    @Test
    @WithMockUser
    void accessToken() throws Exception {
        JwtCreate access = JwtCreate.builder()
                .token("ACCESS TOKEN")
                .expire(LocalDateTime.now().plusHours(1))
                .build();

        JwtCreate refresh = JwtCreate.builder()
                .token("REFRESH TOKEN")
                .expire(LocalDateTime.now().plusHours(12))
                .build();

        //given
        given(authController
                .accessToken(any(TokenCreateRequest.class)))
                .willReturn(new BaseResponse<>(TokenResponse.builder()
                        .accessToken(access.getToken())
                        .refreshToken(refresh.getToken())
                        .accessTokenExpireDate(access.getExpire())
                        .refreshTokenExpireDate(refresh.getExpire())
                        .build()));

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/auth/access-token").with(csrf().asHeader())
                .content(mapper.writeValueAsString(TokenCreateRequest.builder()
                        .subject(1L)
                        .scope(ROLE_USER)
                        .build())
                ).contentType(APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andDo(document("auth-access-token",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("subject").type(NUMBER).description("ID").optional(),
                                fieldWithPath("scope").type(STRING).description("인증 스코프 - 'jwtRole' 공통 Code 참조").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.accessToken").type(STRING).description("Access 토큰"),
                                fieldWithPath("result.refreshToken").type(STRING).description("Refresh 토큰"),
                                fieldWithPath("result.accessTokenExpireDate").type(STRING).description("Access 토큰 만료일자"),
                                fieldWithPath("result.refreshTokenExpireDate").type(STRING).description("Refresh 토큰 만료일자")
                        )
                ));
    }

    @Test
    @WithMockUser
    void refreshToken() throws Exception {
        JwtCreate access = JwtCreate.builder()
                .token("ACCESS TOKEN")
                .expire(LocalDateTime.now().plusHours(1))
                .build();

        JwtCreate refresh = JwtCreate.builder()
                .token("REFRESH TOKEN")
                .expire(LocalDateTime.now().plusHours(12))
                .build();

        //given
        given(authController
                .refreshToken(any()))
                .willReturn(new BaseResponse<>(TokenResponse.builder()
                        .accessToken(access.getToken())
                        .refreshToken(refresh.getToken())
                        .accessTokenExpireDate(access.getExpire())
                        .refreshTokenExpireDate(refresh.getExpire())
                        .build()));

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/auth/refresh-token").with(csrf().asHeader())
                .header("Authorization", "REFRESH TOKEN"));

        //then
        result.andExpect(status().isOk())
                .andDo(document("auth-refresh-token",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.accessToken").type(STRING).description("Access 토큰"),
                                fieldWithPath("result.refreshToken").type(STRING).description("Refresh 토큰"),
                                fieldWithPath("result.accessTokenExpireDate").type(STRING).description("Access 토큰 만료일자"),
                                fieldWithPath("result.refreshTokenExpireDate").type(STRING).description("Refresh 토큰 만료일자")
                        )
                ));
    }
}