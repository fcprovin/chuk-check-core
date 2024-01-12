package com.fcprovin.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcprovin.api.dto.request.create.StadiumCreateRequest;
import com.fcprovin.api.dto.response.BaseResponse;
import com.fcprovin.api.dto.response.StadiumResponse;
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
import static java.time.LocalDateTime.now;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

@WebMvcTest(StadiumController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.fcprovin.com")
class StadiumControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    StadiumController stadiumController;

    @Test
    @WithMockUser
    void create() throws Exception {
        //given
        given(stadiumController
                .create(any(StadiumCreateRequest.class)))
                .willReturn(new BaseResponse<>(StadiumResponse.builder()
                        .stadiumId(1L)
                        .name("탄천B")
                        .address("경기 성남시 중원구 여수동 7-17")
                        .longitude(127.119444)
                        .latitude(37.4257533)
                        .createdDate(now())
                        .updatedDate(now())
                        .build()));

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/stadium").with(csrf().asHeader())
                .content(mapper.writeValueAsString(StadiumCreateRequest.builder()
                        .name("탄천B")
                        .address("경기 성남시 중원구 여수동 7-17")
                        .longitude(127.119444)
                        .latitude(37.4257533)
                        .build())
                ).contentType(APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andDo(document("stadium-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").type(STRING).description("구장 이름").optional(),
                                fieldWithPath("address").type(STRING).description("구장 주소").optional(),
                                fieldWithPath("latitude").type(NUMBER).description("구장 위도").optional(),
                                fieldWithPath("longitude").type(NUMBER).description("구장 경도").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.stadiumId").type(NUMBER).description("구장 ID"),
                                fieldWithPath("result.name").type(STRING).description("구장 이름"),
                                fieldWithPath("result.address").type(STRING).description("구장 주소"),
                                fieldWithPath("result.latitude").type(NUMBER).description("구장 위도"),
                                fieldWithPath("result.longitude").type(NUMBER).description("구장 경도"),
                                fieldWithPath("result.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }

    @Test
    @WithMockUser
    void readAll() throws Exception {
        //given
        given(stadiumController
                .readAll())
                .willReturn(new BaseResponse<>(List.of(StadiumResponse.builder()
                        .stadiumId(1L)
                        .name("탄천B")
                        .address("경기 성남시 중원구 여수동 7-17")
                        .longitude(127.119444)
                        .latitude(37.4257533)
                        .createdDate(now())
                        .updatedDate(now())
                        .build())));

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/stadium"));

        //then
        result.andExpect(status().isOk())
                .andDo(document("stadium-readAll",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.[].stadiumId").type(NUMBER).description("구장 ID"),
                                fieldWithPath("result.[].name").type(STRING).description("구장 이름"),
                                fieldWithPath("result.[].address").type(STRING).description("구장 주소"),
                                fieldWithPath("result.[].latitude").type(NUMBER).description("구장 위도"),
                                fieldWithPath("result.[].longitude").type(NUMBER).description("구장 경도"),
                                fieldWithPath("result.[].createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.[].updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }

    @Test
    @WithMockUser
    void read() throws Exception {
        //given
        given(stadiumController
                .read(eq(1L)))
                .willReturn(new BaseResponse<>(StadiumResponse.builder()
                        .stadiumId(1L)
                        .name("탄천B")
                        .address("경기 성남시 중원구 여수동 7-17")
                        .longitude(127.119444)
                        .latitude(37.4257533)
                        .createdDate(now())
                        .updatedDate(now())
                        .build()));

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/stadium/{id}", 1L));

        //then
        result.andExpect(status().isOk())
                .andDo(document("stadium-read",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("구장 ID")
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.stadiumId").type(NUMBER).description("구장 ID"),
                                fieldWithPath("result.name").type(STRING).description("구장 이름"),
                                fieldWithPath("result.address").type(STRING).description("구장 주소"),
                                fieldWithPath("result.latitude").type(NUMBER).description("구장 위도"),
                                fieldWithPath("result.longitude").type(NUMBER).description("구장 경도"),
                                fieldWithPath("result.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }
}
