package com.fcprovin.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcprovin.api.dto.request.create.VoteCreateRequest;
import com.fcprovin.api.dto.request.update.VoteUpdateRequest;
import com.fcprovin.api.dto.response.*;
import com.fcprovin.api.dto.search.VoteSearch;
import com.fcprovin.api.entity.AttendDate;
import com.fcprovin.api.entity.MatchDate;
import com.fcprovin.api.entity.VoteDate;
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
import static com.fcprovin.api.entity.MatchStatus.VOTE;
import static com.fcprovin.api.entity.PlayerAuthority.MANAGER;
import static com.fcprovin.api.entity.Position.MF;
import static com.fcprovin.api.entity.VoteStatus.FALSE;
import static com.fcprovin.api.entity.VoteStatus.TRUE;
import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.of;
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

@WebMvcTest(VoteController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.fcprovin.com")
class VoteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    VoteController voteController;

    @Test
    @WithMockUser
    void create() throws Exception {
        //given
        given(voteController
                .create(any(VoteCreateRequest.class)))
                .willReturn(new BaseResponse<>(VoteResponse.builder()
                        .id(1L)
                        .status(TRUE)
                        .createdDate(now())
                        .updatedDate(now())
                        .build()));

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/vote").with(csrf())
                .content(mapper.writeValueAsString(VoteCreateRequest.builder()
                        .playerId(1L)
                        .matchId(1L)
                        .status(TRUE)
                        .build())
                ).contentType(APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andDo(document("vote-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("playerId").type(NUMBER).description("플레이어 ID").optional(),
                                fieldWithPath("matchId").type(NUMBER).description("매치 ID").optional(),
                                fieldWithPath("status").type(STRING).description("투표 상태 - 'voteStatus' 공통 Code 참조").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.id").type(NUMBER).description("투표 ID"),
                                fieldWithPath("result.status").type(STRING).description("투표 상태 - 'voteStatus' 공통 Code 참조"),
                                fieldWithPath("result.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }

    @Test
    @WithMockUser
    void update() throws Exception {
        //given
        given(voteController
                .update(eq(1L), any(VoteUpdateRequest.class)))
                .willReturn(new BaseResponse<>(VoteResponse.builder()
                        .id(1L)
                        .status(FALSE)
                        .createdDate(now())
                        .updatedDate(now())
                        .build()));

        //when
        ResultActions result = mockMvc.perform(put("/api/v1/vote/{id}", 1L).with(csrf())
                .content(mapper.writeValueAsString(new VoteUpdateRequest(FALSE)))
                .contentType(APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andDo(document("vote-update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("status").type(STRING).description("투표 상태 - 'voteStatus' 공통 Code 참조").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.id").type(NUMBER).description("투표 ID"),
                                fieldWithPath("result.status").type(STRING).description("투표 상태 - 'voteStatus' 공통 Code 참조"),
                                fieldWithPath("result.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }

    @Test
    @WithMockUser
    void readAll() throws Exception {
        //given
        given(voteController
                .readAll(any(VoteSearch.class)))
                .willReturn(new BaseResponse<>(List.of(VoteResponse.builder()
                        .id(1L)
                        .status(FALSE)
                        .createdDate(now())
                        .updatedDate(now())
                        .player(PlayerResponse.builder()
                            .id(1L)
                            .uniformNumber(6)
                            .position(MF)
                            .status(APPROVE)
                            .authority(MANAGER)
                            .createdDate(now())
                            .updatedDate(now())
                            .member(MemberResponse.builder()
                                    .id(1L)
                                    .name("홍길동")
                                    .email("hong@gmail.com")
                                    .birthDate(LocalDate.of(1997, 3, 7))
                                    .createdDate(now())
                                    .updatedDate(now())
                                    .build())
                            .build())
                        .match(MatchResponse.builder()
                            .id(1L)
                            .otherTeamName("징기스칸")
                            .home(Boolean.FALSE)
                            .notice("02월 25일 10시 12시 황송")
                            .status(VOTE)
                            .matchDate(MatchDate.of(of(2023, 2, 25, 10, 0), of(2023, 2, 25, 12, 0)))
                            .voteDate(VoteDate.of(of(2023, 2, 20, 9, 0), of(2023, 2, 23, 12, 0)))
                            .attendDate(AttendDate.of(of(2023, 2, 25, 9, 30)))
                            .createdDate(now())
                            .updatedDate(now())
                            .build())
                        .build())));

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/vote").queryParam("status", "FALSE"));

        //then
        result.andExpect(status().isOk())
                .andDo(document("vote-readAll",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("status").description("투표 상태 - 'voteStatus' 공통 Code 참조")
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.[].id").type(NUMBER).description("투표 ID"),
                                fieldWithPath("result.[].status").type(STRING).description("투표 상태 - 'voteStatus' 공통 Code 참조"),
                                fieldWithPath("result.[].createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.[].updatedDate").type(STRING).description("수정일자"),
                                fieldWithPath("result.[].player").type(OBJECT).description("플레이어"),
                                fieldWithPath("result.[].player.id").type(NUMBER).description("플레이어 ID"),
                                fieldWithPath("result.[].player.uniformNumber").type(NUMBER).description("플레이어 등번호"),
                                fieldWithPath("result.[].player.position").type(STRING).description("플레이어 포지션 - 'position' 공통 Code 참조"),
                                fieldWithPath("result.[].player.status").type(STRING).description("플레이어 상태 - 'baseStatus' 공통 Code 참조"),
                                fieldWithPath("result.[].player.authority").type(STRING).description("플레이어 직책 - 'playerAuthority' 공통 Code 참조"),
                                fieldWithPath("result.[].player.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.[].player.updatedDate").type(STRING).description("수정일자"),
                                fieldWithPath("result.[].player.member").type(OBJECT).description("회원"),
                                fieldWithPath("result.[].player.member.id").type(NUMBER).description("회원 ID"),
                                fieldWithPath("result.[].player.member.name").type(STRING).description("회원 이름"),
                                fieldWithPath("result.[].player.member.email").type(STRING).description("회원 이메일"),
                                fieldWithPath("result.[].player.member.birthDate").type(STRING).description("회원 생년월일"),
                                fieldWithPath("result.[].player.member.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.[].player.member.updatedDate").type(STRING).description("수정일자"),
                                fieldWithPath("result.[].match").type(OBJECT).description("매치"),
                                fieldWithPath("result.[].match.id").type(NUMBER).description("매치 ID"),
                                fieldWithPath("result.[].match.otherTeamName").type(STRING).description("매치 상대팀 명"),
                                fieldWithPath("result.[].match.home").type(BOOLEAN).description("매치 홈구장 여부"),
                                fieldWithPath("result.[].match.notice").type(STRING).description("매치 공지"),
                                fieldWithPath("result.[].match.status").type(STRING).description("매치 상태 - 'matchStatus' 공통 Code 참조"),
                                fieldWithPath("result.[].match.matchDate").type(OBJECT).description("매치 일자"),
                                fieldWithPath("result.[].match.matchDate.startDate").type(STRING).description("매치 시작 일자"),
                                fieldWithPath("result.[].match.matchDate.endDate").type(STRING).description("매치 종료 일자"),
                                fieldWithPath("result.[].match.voteDate").type(OBJECT).description("매치 투표 일자"),
                                fieldWithPath("result.[].match.voteDate.startDate").type(STRING).description("매치 투표 시작 일자"),
                                fieldWithPath("result.[].match.voteDate.endDate").type(STRING).description("매치 종료 종료 일자"),
                                fieldWithPath("result.[].match.attendDate").type(OBJECT).description("메치 출석 일자"),
                                fieldWithPath("result.[].match.attendDate.deadlineDate").type(STRING).description("메치 출석 마감 일자"),
                                fieldWithPath("result.[].match.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.[].match.updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }

    @Test
    @WithMockUser
    void read() throws Exception {
        //given
        given(voteController
                .read(eq(1L)))
                .willReturn(new BaseResponse<>(VoteResponse.builder()
                        .id(1L)
                        .status(FALSE)
                        .createdDate(now())
                        .updatedDate(now())
                        .player(PlayerResponse.builder()
                            .id(1L)
                            .uniformNumber(6)
                            .position(MF)
                            .status(APPROVE)
                            .authority(MANAGER)
                            .createdDate(now())
                            .updatedDate(now())
                            .member(MemberResponse.builder()
                                    .id(1L)
                                    .name("홍길동")
                                    .email("hong@gmail.com")
                                    .birthDate(LocalDate.of(1997, 3, 7))
                                    .createdDate(now())
                                    .updatedDate(now())
                                    .build())
                            .build())
                        .match(MatchResponse.builder()
                            .id(1L)
                            .otherTeamName("징기스칸")
                            .home(Boolean.FALSE)
                            .notice("02월 25일 10시 12시 황송")
                            .status(VOTE)
                            .matchDate(MatchDate.of(of(2023, 2, 25, 10, 0), of(2023, 2, 25, 12, 0)))
                            .voteDate(VoteDate.of(of(2023, 2, 20, 9, 0), of(2023, 2, 23, 12, 0)))
                            .attendDate(AttendDate.of(of(2023, 2, 25, 9, 30)))
                            .createdDate(now())
                            .updatedDate(now())
                            .build())
                        .build()));

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/vote/{id}", 1L));

        //then
        result.andExpect(status().isOk())
                .andDo(document("vote-read",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("투표 ID")
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.id").type(NUMBER).description("투표 ID"),
                                fieldWithPath("result.status").type(STRING).description("투표 상태 - 'voteStatus' 공통 Code 참조"),
                                fieldWithPath("result.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.updatedDate").type(STRING).description("수정일자"),
                                fieldWithPath("result.player").type(OBJECT).description("플레이어"),
                                fieldWithPath("result.player.id").type(NUMBER).description("플레이어 ID"),
                                fieldWithPath("result.player.uniformNumber").type(NUMBER).description("플레이어 등번호"),
                                fieldWithPath("result.player.position").type(STRING).description("플레이어 포지션 - 'position' 공통 Code 참조"),
                                fieldWithPath("result.player.status").type(STRING).description("플레이어 상태 - 'baseStatus' 공통 Code 참조"),
                                fieldWithPath("result.player.authority").type(STRING).description("플레이어 직책 - 'playerAuthority' 공통 Code 참조"),
                                fieldWithPath("result.player.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.player.updatedDate").type(STRING).description("수정일자"),
                                fieldWithPath("result.player.member").type(OBJECT).description("회원"),
                                fieldWithPath("result.player.member.id").type(NUMBER).description("회원 ID"),
                                fieldWithPath("result.player.member.name").type(STRING).description("회원 이름"),
                                fieldWithPath("result.player.member.email").type(STRING).description("회원 이메일"),
                                fieldWithPath("result.player.member.birthDate").type(STRING).description("회원 생년월일"),
                                fieldWithPath("result.player.member.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.player.member.updatedDate").type(STRING).description("수정일자"),
                                fieldWithPath("result.match").type(OBJECT).description("매치"),
                                fieldWithPath("result.match.id").type(NUMBER).description("매치 ID"),
                                fieldWithPath("result.match.otherTeamName").type(STRING).description("매치 상대팀 명"),
                                fieldWithPath("result.match.home").type(BOOLEAN).description("매치 홈구장 여부"),
                                fieldWithPath("result.match.notice").type(STRING).description("매치 공지"),
                                fieldWithPath("result.match.status").type(STRING).description("매치 상태 - 'matchStatus' 공통 Code 참조"),
                                fieldWithPath("result.match.matchDate").type(OBJECT).description("매치 일자"),
                                fieldWithPath("result.match.matchDate.startDate").type(STRING).description("매치 시작 일자"),
                                fieldWithPath("result.match.matchDate.endDate").type(STRING).description("매치 종료 일자"),
                                fieldWithPath("result.match.voteDate").type(OBJECT).description("매치 투표 일자"),
                                fieldWithPath("result.match.voteDate.startDate").type(STRING).description("매치 투표 시작 일자"),
                                fieldWithPath("result.match.voteDate.endDate").type(STRING).description("매치 종료 종료 일자"),
                                fieldWithPath("result.match.attendDate").type(OBJECT).description("메치 출석 일자"),
                                fieldWithPath("result.match.attendDate.deadlineDate").type(STRING).description("메치 출석 마감 일자"),
                                fieldWithPath("result.match.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.match.updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }
}
