package com.fcprovin.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcprovin.api.dto.request.create.PlayerCreateRequest;
import com.fcprovin.api.dto.request.update.PlayerUpdateRequest;
import com.fcprovin.api.dto.response.BaseResponse;
import com.fcprovin.api.dto.response.MemberResponse;
import com.fcprovin.api.dto.response.PlayerResponse;
import com.fcprovin.api.dto.response.TeamResponse;
import com.fcprovin.api.dto.search.PlayerSearch;
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
import static com.fcprovin.api.entity.BaseStatus.APPROVE;
import static com.fcprovin.api.entity.BaseStatus.WAIT;
import static com.fcprovin.api.entity.PlayerAuthority.GENERAL;
import static com.fcprovin.api.entity.PlayerAuthority.MANAGER;
import static com.fcprovin.api.entity.Position.MF;
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

@WebMvcTest(PlayerController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.fcprovin.com")
class PlayerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    PlayerController playerController;

    @Test
    @WithMockUser
    void create() throws Exception {
        //given
        given(playerController
                .create(any(PlayerCreateRequest.class)))
                .willReturn(new BaseResponse<>(PlayerResponse.builder()
                        .playerId(1L)
                        .status(WAIT)
                        .authority(GENERAL)
                        .createdDate(now())
                        .updatedDate(now())
                        .build()));

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/player").with(csrf().asHeader())
                .content(mapper.writeValueAsString(PlayerCreateRequest.builder()
                        .teamId(1L)
                        .memberId(1L)
                        .authority(GENERAL)
                        .build())
                ).contentType(APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andDo(document("player-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("teamId").type(NUMBER).description("팀 ID").optional(),
                                fieldWithPath("memberId").type(NUMBER).description("회원 ID").optional(),
                                fieldWithPath("authority").type(STRING).description("플레이어 직책 - 'playerAuthority' 공통 Code 참조").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.playerId").type(NUMBER).description("플레이어 ID"),
                                fieldWithPath("result.status").type(STRING).description("플레이어 상태 - 'baseStatus' 공통 Code 참조"),
                                fieldWithPath("result.authority").type(STRING).description("플레이어 직책 - 'playerAuthority' 공통 Code 참조"),
                                fieldWithPath("result.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }

    @Test
    @WithMockUser
    void update() throws Exception {
        //given
        given(playerController
                .update(eq(1L), any(PlayerUpdateRequest.class)))
                .willReturn(new BaseResponse<>(PlayerResponse.builder()
                        .playerId(1L)
                        .uniformNumber(6)
                        .position(MF)
                        .status(APPROVE)
                        .authority(MANAGER)
                        .createdDate(now())
                        .updatedDate(now())
                        .build()));

        //when
        ResultActions result = mockMvc.perform(put("/api/v1/player/{id}", 1L).with(csrf().asHeader())
                .content(mapper.writeValueAsString(PlayerUpdateRequest.builder()
                        .uniformNumber(6)
                        .position(MF)
                        .status(APPROVE)
                        .authority(MANAGER)
                        .build())
                ).contentType(APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andDo(document("player-update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("uniformNumber").type(NUMBER).description("플레이어 등번호").optional(),
                                fieldWithPath("position").type(STRING).description("플레이어 포지션 - 'position' 공통 Code 참조").optional(),
                                fieldWithPath("status").type(STRING).description("플레이어 상태 - 'baseStatus' 공통 Code 참조").optional(),
                                fieldWithPath("authority").type(STRING).description("플레이어 직책 - 'playerAuthority' 공통 Code 참조").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.playerId").type(NUMBER).description("플레이어 ID"),
                                fieldWithPath("result.uniformNumber").type(NUMBER).description("플레이어 등번호"),
                                fieldWithPath("result.position").type(STRING).description("플레이어 포지션 - 'position' 공통 Code 참조"),
                                fieldWithPath("result.status").type(STRING).description("플레이어 상태 - 'baseStatus' 공통 Code 참조"),
                                fieldWithPath("result.authority").type(STRING).description("플레이어 직책 - 'playerAuthority' 공통 Code 참조"),
                                fieldWithPath("result.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }

    @Test
    @WithMockUser
    void readAll() throws Exception {
        //given
        given(playerController
                .readAll(any(PlayerSearch.class)))
                .willReturn(new BaseResponse<>(List.of(PlayerResponse.builder()
                        .playerId(1L)
                        .uniformNumber(6)
                        .position(MF)
                        .status(APPROVE)
                        .authority(MANAGER)
                        .createdDate(now())
                        .updatedDate(now())
                        .team(TeamResponse.builder()
                                .teamId(1L)
                                .name("프로빈")
                                .status(WAIT)
                                .createdDate(now())
                                .updatedDate(now())
                                .build())
                        .member(MemberResponse.builder()
                                .memberId(1L)
                                .name("홍길동")
                                .email("hong@gmail.com")
                                .birthDate(LocalDate.of(1997, 3, 7))
                                .createdDate(now())
                                .updatedDate(now())
                                .build())
                        .build())));

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/player")
                .queryParam("memberId", "1")
                .queryParam("teamId", "1")
                .queryParam("position", "MF")
                .queryParam("status", "APPROVE")
                .queryParam("authority", "MANAGER"));

        //then
        result.andExpect(status().isOk())
                .andDo(document("player-readAll",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        queryParameters(
                                parameterWithName("memberId").description("회원 ID"),
                                parameterWithName("teamId").description("팀 ID"),
                                parameterWithName("position").description("플레이어 포지션 - 'position' 공통 Code 참조"),
                                parameterWithName("status").description("플레이어 상태 - 'baseStatus' 공통 Code 참조"),
                                parameterWithName("authority").description("플레이어 직책 - 'playerAuthority' 공통 Code 참조")
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.[].playerId").type(NUMBER).description("플레이어 ID"),
                                fieldWithPath("result.[].uniformNumber").type(NUMBER).description("플레이어 등번호"),
                                fieldWithPath("result.[].position").type(STRING).description("플레이어 포지션 - 'position' 공통 Code 참조"),
                                fieldWithPath("result.[].status").type(STRING).description("플레이어 상태 - 'baseStatus' 공통 Code 참조"),
                                fieldWithPath("result.[].authority").type(STRING).description("플레이어 직책 - 'playerAuthority' 공통 Code 참조"),
                                fieldWithPath("result.[].createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.[].updatedDate").type(STRING).description("수정일자"),
                                fieldWithPath("result.[].team").type(OBJECT).description("팀"),
                                fieldWithPath("result.[].team.teamId").type(NUMBER).description("팀 ID"),
                                fieldWithPath("result.[].team.name").type(STRING).description("팀 이름"),
                                fieldWithPath("result.[].team.status").type(STRING).description("팀 상태 - 'baseStatus' 공통 Code 참조"),
                                fieldWithPath("result.[].team.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.[].team.updatedDate").type(STRING).description("수정일자"),
                                fieldWithPath("result.[].member").type(OBJECT).description("회원"),
                                fieldWithPath("result.[].member.memberId").type(NUMBER).description("회원 ID"),
                                fieldWithPath("result.[].member.name").type(STRING).description("회원 이름"),
                                fieldWithPath("result.[].member.email").type(STRING).description("회원 이메일"),
                                fieldWithPath("result.[].member.birthDate").type(STRING).description("회원 생년월일"),
                                fieldWithPath("result.[].member.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.[].member.updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }

    @Test
    @WithMockUser
    void read() throws Exception {
        //given
        given(playerController
                .read(eq(1L)))
                .willReturn(new BaseResponse<>(PlayerResponse.builder()
                        .playerId(1L)
                        .uniformNumber(6)
                        .position(MF)
                        .status(APPROVE)
                        .authority(MANAGER)
                        .createdDate(now())
                        .updatedDate(now())
                        .team(TeamResponse.builder()
                                .teamId(1L)
                                .name("프로빈")
                                .status(WAIT)
                                .createdDate(now())
                                .updatedDate(now())
                                .build())
                        .member(MemberResponse.builder()
                                .memberId(1L)
                                .name("홍길동")
                                .email("hong@gmail.com")
                                .birthDate(LocalDate.of(1997, 3, 7))
                                .createdDate(now())
                                .updatedDate(now())
                                .build())
                        .build()));

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/player/{id}", 1L));

        //then
        result.andExpect(status().isOk())
                .andDo(document("player-read",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("플레이어 ID")
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.playerId").type(NUMBER).description("플레이어 ID"),
                                fieldWithPath("result.uniformNumber").type(NUMBER).description("플레이어 등번호"),
                                fieldWithPath("result.position").type(STRING).description("플레이어 포지션 - 'position' 공통 Code 참조"),
                                fieldWithPath("result.status").type(STRING).description("플레이어 상태 - 'baseStatus' 공통 Code 참조"),
                                fieldWithPath("result.authority").type(STRING).description("플레이어 직책 - 'playerAuthority' 공통 Code 참조"),
                                fieldWithPath("result.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.updatedDate").type(STRING).description("수정일자"),
                                fieldWithPath("result.team").type(OBJECT).description("팀"),
                                fieldWithPath("result.team.teamId").type(NUMBER).description("팀 ID"),
                                fieldWithPath("result.team.name").type(STRING).description("팀 이름"),
                                fieldWithPath("result.team.status").type(STRING).description("팀 상태 - 'baseStatus' 공통 Code 참조"),
                                fieldWithPath("result.team.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.team.updatedDate").type(STRING).description("수정일자"),
                                fieldWithPath("result.member").type(OBJECT).description("회원"),
                                fieldWithPath("result.member.memberId").type(NUMBER).description("회원 ID"),
                                fieldWithPath("result.member.name").type(STRING).description("회원 이름"),
                                fieldWithPath("result.member.email").type(STRING).description("회원 이메일"),
                                fieldWithPath("result.member.birthDate").type(STRING).description("회원 생년월일"),
                                fieldWithPath("result.member.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.member.updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }
}
