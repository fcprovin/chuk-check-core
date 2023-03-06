package com.fcprovin.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcprovin.api.dto.request.create.TeamCreateRequest;
import com.fcprovin.api.dto.request.update.TeamUpdateRequest;
import com.fcprovin.api.dto.response.BaseResponse;
import com.fcprovin.api.dto.response.RegionResponse;
import com.fcprovin.api.dto.response.TeamResponse;
import com.fcprovin.api.dto.search.TeamSearch;
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
import static com.fcprovin.api.entity.BaseStatus.APPROVE;
import static com.fcprovin.api.entity.BaseStatus.WAIT;
import static java.time.LocalDateTime.now;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeamController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.fcprovin.com")
class TeamControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    TeamController teamController;

    @Test
    @WithMockUser
    void create() throws Exception {
        //given
        given(teamController
                .create(any(TeamCreateRequest.class)))
                .willReturn(new BaseResponse<>(TeamResponse.builder()
                        .id(1L)
                        .name("프로빈")
                        .status(WAIT)
                        .createdDate(now())
                        .updatedDate(now())
                        .build()));

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/team").with(csrf())
                .content(mapper.writeValueAsString(TeamCreateRequest.builder()
                        .regionId(1L)
                        .name("프로빈")
                        .build())
                ).contentType(APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andDo(document("team-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("regionId").type(NUMBER).description("지역 ID").optional(),
                                fieldWithPath("name").type(STRING).description("팀 이름").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.id").type(NUMBER).description("팀 ID"),
                                fieldWithPath("result.name").type(STRING).description("팀 이름"),
                                fieldWithPath("result.status").type(STRING).description("팀 상태 - 'baseStatus' 공통 Code 참조"),
                                fieldWithPath("result.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }

    @Test
    @WithMockUser
    void update() throws Exception {
        //given
        given(teamController
                .update(eq(1L), any(TeamUpdateRequest.class)))
                .willReturn(new BaseResponse<>(TeamResponse.builder()
                        .id(1L)
                        .name("프로빈")
                        .status(APPROVE)
                        .createdDate(now())
                        .updatedDate(now())
                        .build()));

        //when
        ResultActions result = mockMvc.perform(put("/api/v1/team/{id}", 1L).with(csrf())
                .content(mapper.writeValueAsString(TeamUpdateRequest.builder()
                        .status(APPROVE)
                        .build())
                ).contentType(APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andDo(document("team-update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("status").type(STRING).description("팀 상태 - 'baseStatus' 공통 Code 참조").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.id").type(NUMBER).description("팀 ID"),
                                fieldWithPath("result.name").type(STRING).description("팀 이름"),
                                fieldWithPath("result.status").type(STRING).description("팀 상태 - 'baseStatus' 공통 Code 참조"),
                                fieldWithPath("result.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }

    @Test
    @WithMockUser
    void readAll() throws Exception {
        //given
        given(teamController
                .readAll(any(TeamSearch.class)))
                .willReturn(new BaseResponse<>(List.of(TeamResponse.builder()
                        .id(1L)
                        .name("프로빈")
                        .status(WAIT)
                        .createdDate(now())
                        .updatedDate(now())
                        .region(RegionResponse.builder()
                                .id(1L)
                                .country("경기도")
                                .city("성남시")
                                .createdDate(now())
                                .updatedDate(now())
                                .build())
                        .build())));

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/team")
                .queryParam("regionId", "1")
                .queryParam("name", "프로빈")
                .queryParam("status", "WAIT"));

        //then
        result.andExpect(status().isOk())
                .andDo(document("team-readAll",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("regionId").description("지역 ID"),
                                parameterWithName("name").description("팀 이름"),
                                parameterWithName("status").description("팀 상태 - 'baseStatus' 공통 Code 참조")
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.[].id").type(NUMBER).description("팀 ID"),
                                fieldWithPath("result.[].name").type(STRING).description("팀 이름"),
                                fieldWithPath("result.[].status").type(STRING).description("팀 상태 - 'baseStatus' 공통 Code 참조"),
                                fieldWithPath("result.[].createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.[].updatedDate").type(STRING).description("수정일자"),
                                fieldWithPath("result.[].region").type(OBJECT).description("지역"),
                                fieldWithPath("result.[].region.id").type(NUMBER).description("지역 ID"),
                                fieldWithPath("result.[].region.country").type(STRING).description("지역 시/도"),
                                fieldWithPath("result.[].region.city").type(STRING).description("지역 도시"),
                                fieldWithPath("result.[].region.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.[].region.updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }

    @Test
    @WithMockUser
    void read() throws Exception {
        //given
        given(teamController
                .read(eq(1L)))
                .willReturn(new BaseResponse<>(TeamResponse.builder()
                        .id(1L)
                        .name("프로빈")
                        .status(WAIT)
                        .createdDate(now())
                        .updatedDate(now())
                        .region(RegionResponse.builder()
                                .id(1L)
                                .country("경기도")
                                .city("성남시")
                                .createdDate(now())
                                .updatedDate(now())
                                .build())
                        .build()));

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/team/{id}", 1L));

        //then
        result.andExpect(status().isOk())
                .andDo(document("team-read",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("팀 ID")
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.id").type(NUMBER).description("팀 ID"),
                                fieldWithPath("result.name").type(STRING).description("팀 이름"),
                                fieldWithPath("result.status").type(STRING).description("팀 상태 - 'baseStatus' 공통 Code 참조"),
                                fieldWithPath("result.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.updatedDate").type(STRING).description("수정일자"),
                                fieldWithPath("result.region").type(OBJECT).description("지역"),
                                fieldWithPath("result.region.id").type(NUMBER).description("지역 ID"),
                                fieldWithPath("result.region.country").type(STRING).description("지역 시/도"),
                                fieldWithPath("result.region.city").type(STRING).description("지역 도시"),
                                fieldWithPath("result.region.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.region.updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }
}
