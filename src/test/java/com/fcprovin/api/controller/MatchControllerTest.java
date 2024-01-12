package com.fcprovin.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcprovin.api.dto.request.create.MatchCreateRequest;
import com.fcprovin.api.dto.request.update.MatchUpdateRequest;
import com.fcprovin.api.dto.response.BaseResponse;
import com.fcprovin.api.dto.response.MatchResponse;
import com.fcprovin.api.dto.response.StadiumResponse;
import com.fcprovin.api.dto.response.TeamResponse;
import com.fcprovin.api.dto.search.MatchSearch;
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

import java.util.List;

import static com.fcprovin.api.docs.ApiDocumentUtils.getDocumentRequest;
import static com.fcprovin.api.docs.ApiDocumentUtils.getDocumentResponse;
import static com.fcprovin.api.entity.BaseStatus.WAIT;
import static com.fcprovin.api.entity.MatchStatus.CREATE;
import static com.fcprovin.api.entity.MatchStatus.VOTE;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
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

@WebMvcTest(MatchController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.fcprovin.com")
class MatchControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    MatchController matchController;

    @Test
    @WithMockUser
    void create() throws Exception {
        //given
        given(matchController
                .create(any(MatchCreateRequest.class)))
                .willReturn(new BaseResponse<>(MatchResponse.builder()
                        .matchId(1L)
                        .otherTeamName("블랑")
                        .home(TRUE)
                        .notice("02월 25일 12시 14시 황송")
                        .status(CREATE)
                        .matchDate(MatchDate.of(of(2023, 2, 25, 12, 0), of(2023, 2, 25, 14, 0)))
                        .voteDate(VoteDate.of(of(2023, 2, 20, 9, 0), of(2023, 2, 23, 12, 0)))
                        .attendDate(AttendDate.of(of(2023, 2, 25, 11, 30)))
                        .createdDate(now())
                        .updatedDate(now())
                        .build()));

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/match").with(csrf().asHeader())
                .content(mapper.writeValueAsString(MatchCreateRequest.builder()
                        .teamId(1L)
                        .stadiumId(1L)
                        .otherTeamName("블랑")
                        .home(TRUE)
                        .notice("02월 25일 12시 14시 황송")
                        .startDate(of(2023, 2, 25, 12, 0))
                        .endDate(of(2023, 2, 25, 14, 0))
                        .voteStartDate(of(2023, 2, 20, 9, 0))
                        .voteEndDate(of(2023, 2, 23, 12, 0))
                        .attendDeadlineDate(of(2023, 2, 25, 11, 30))
                        .build())
                ).contentType(APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andDo(document("match-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("teamId").type(NUMBER).description("팀 ID").optional(),
                                fieldWithPath("stadiumId").type(NUMBER).description("구장 ID").optional(),
                                fieldWithPath("otherTeamName").type(STRING).description("매치 상대팀 명").optional(),
                                fieldWithPath("home").type(BOOLEAN).description("매치 홈구장 여부").optional(),
                                fieldWithPath("notice").type(STRING).description("매치 공지").optional(),
                                fieldWithPath("startDate").type(STRING).description("매치 시작 일자").optional(),
                                fieldWithPath("endDate").type(STRING).description("매치 종료 일자").optional(),
                                fieldWithPath("voteStartDate").type(STRING).description("매치 투표 시작 일자").optional(),
                                fieldWithPath("voteEndDate").type(STRING).description("매치 투표 종료 일자").optional(),
                                fieldWithPath("attendDeadlineDate").type(STRING).description("매치 출석 마감 일자").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.matchId").type(NUMBER).description("매치 ID"),
                                fieldWithPath("result.otherTeamName").type(STRING).description("매치 상대팀 명"),
                                fieldWithPath("result.home").type(BOOLEAN).description("매치 홈구장 여부"),
                                fieldWithPath("result.notice").type(STRING).description("매치 공지"),
                                fieldWithPath("result.status").type(STRING).description("매치 상태 - 'matchStatus' 공통 Code 참조"),
                                fieldWithPath("result.matchDate").type(OBJECT).description("매치 일자"),
                                fieldWithPath("result.matchDate.startDate").type(STRING).description("매치 시작 일자"),
                                fieldWithPath("result.matchDate.endDate").type(STRING).description("매치 종료 일자"),
                                fieldWithPath("result.voteDate").type(OBJECT).description("매치 투표 일자"),
                                fieldWithPath("result.voteDate.startDate").type(STRING).description("매치 투표 시작 일자"),
                                fieldWithPath("result.voteDate.endDate").type(STRING).description("매치 종료 종료 일자"),
                                fieldWithPath("result.attendDate").type(OBJECT).description("메치 출석 일자"),
                                fieldWithPath("result.attendDate.deadlineDate").type(STRING).description("메치 출석 마감 일자"),
                                fieldWithPath("result.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }

    @Test
    @WithMockUser
    void update() throws Exception {
        //given
        given(matchController
                .update(eq(1L), any(MatchUpdateRequest.class)))
                .willReturn(new BaseResponse<>(MatchResponse.builder()
                        .matchId(1L)
                        .otherTeamName("징기스칸")
                        .home(FALSE)
                        .notice("02월 25일 10시 12시 황송")
                        .status(VOTE)
                        .matchDate(MatchDate.of(of(2023, 2, 25, 10, 0), of(2023, 2, 25, 12, 0)))
                        .voteDate(VoteDate.of(of(2023, 2, 20, 9, 0), of(2023, 2, 23, 12, 0)))
                        .attendDate(AttendDate.of(of(2023, 2, 25, 9, 30)))
                        .createdDate(now())
                        .updatedDate(now())
                        .build()));

        //when
        ResultActions result = mockMvc.perform(put("/api/v1/match/{id}", 1L).with(csrf().asHeader())
                .content(mapper.writeValueAsString(MatchUpdateRequest.builder()
                        .stadiumId(1L)
                        .otherTeamName("징기스칸")
                        .home(FALSE)
                        .notice("02월 25일 10시 12시 황송")
                        .status(VOTE)
                        .startDate(of(2023, 2, 25, 10, 0))
                        .endDate(of(2023, 2, 25, 12, 0))
                        .voteStartDate(of(2023, 2, 20, 9, 0))
                        .voteEndDate(of(2023, 2, 23, 12, 0))
                        .attendDeadlineDate(of(2023, 2, 25, 9, 30))
                        .build())
                ).contentType(APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andDo(document("match-update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("stadiumId").type(NUMBER).description("구장 ID").optional(),
                                fieldWithPath("otherTeamName").type(STRING).description("매치 상대팀 명").optional(),
                                fieldWithPath("home").type(BOOLEAN).description("매치 홈구장 여부").optional(),
                                fieldWithPath("notice").type(STRING).description("매치 공지").optional(),
                                fieldWithPath("status").type(STRING).description("매치 상태 - 'matchStatus' 공통 Code 참조").optional(),
                                fieldWithPath("startDate").type(STRING).description("매치 시작 일자").optional(),
                                fieldWithPath("endDate").type(STRING).description("매치 종료 일자").optional(),
                                fieldWithPath("voteStartDate").type(STRING).description("매치 투표 시작 일자").optional(),
                                fieldWithPath("voteEndDate").type(STRING).description("매치 투표 종료 일자").optional(),
                                fieldWithPath("attendDeadlineDate").type(STRING).description("매치 출석 마감 일자").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.matchId").type(NUMBER).description("매치 ID"),
                                fieldWithPath("result.otherTeamName").type(STRING).description("매치 상대팀 명"),
                                fieldWithPath("result.home").type(BOOLEAN).description("매치 홈구장 여부"),
                                fieldWithPath("result.notice").type(STRING).description("매치 공지"),
                                fieldWithPath("result.status").type(STRING).description("매치 상태 - 'matchStatus' 공통 Code 참조"),
                                fieldWithPath("result.matchDate").type(OBJECT).description("매치 일자"),
                                fieldWithPath("result.matchDate.startDate").type(STRING).description("매치 시작 일자"),
                                fieldWithPath("result.matchDate.endDate").type(STRING).description("매치 종료 일자"),
                                fieldWithPath("result.voteDate").type(OBJECT).description("매치 투표 일자"),
                                fieldWithPath("result.voteDate.startDate").type(STRING).description("매치 투표 시작 일자"),
                                fieldWithPath("result.voteDate.endDate").type(STRING).description("매치 종료 종료 일자"),
                                fieldWithPath("result.attendDate").type(OBJECT).description("메치 출석 일자"),
                                fieldWithPath("result.attendDate.deadlineDate").type(STRING).description("메치 출석 마감 일자"),
                                fieldWithPath("result.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }

    @Test
    @WithMockUser
    void readAll() throws Exception {
        //given
        given(matchController
                .readAll(any(MatchSearch.class)))
                .willReturn(new BaseResponse<>(List.of(MatchResponse.builder()
                        .matchId(1L)
                        .otherTeamName("징기스칸")
                        .home(FALSE)
                        .notice("02월 25일 10시 12시 황송")
                        .status(VOTE)
                        .matchDate(MatchDate.of(of(2023, 2, 25, 10, 0), of(2023, 2, 25, 12, 0)))
                        .voteDate(VoteDate.of(of(2023, 2, 20, 9, 0), of(2023, 2, 23, 12, 0)))
                        .attendDate(AttendDate.of(of(2023, 2, 25, 9, 30)))
                        .createdDate(now())
                        .updatedDate(now())
                        .team(TeamResponse.builder()
                                .teamId(1L)
                                .name("프로빈")
                                .status(WAIT)
                                .createdDate(now())
                                .updatedDate(now())
                                .build())
                        .stadium(StadiumResponse.builder()
                                .stadiumId(1L)
                                .name("탄천B")
                                .address("경기 성남시 중원구 여수동 7-17")
                                .longitude(127.119444)
                                .latitude(37.4257533)
                                .createdDate(now())
                                .updatedDate(now())
                                .build())
                        .build())));

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/match")
                .queryParam("teamId", "1")
                .queryParam("stadiumId", "1")
                .queryParam("status", "VOTE")
                .queryParam("startDate", "2023-02-01")
                .queryParam("endDate", "2023-02-28"));

        //then
        result.andExpect(status().isOk())
                .andDo(document("match-readAll",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("teamId").description("팀 ID"),
                                parameterWithName("stadiumId").description("구장 ID"),
                                parameterWithName("status").description("매치 상태 - 'matchStatus' 공통 Code 참조"),
                                parameterWithName("startDate").description("검색 시작 날짜 (매치 일자)"),
                                parameterWithName("endDate").description("검색 종료 날짜 (매치 일자)")
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.[].matchId").type(NUMBER).description("매치 ID"),
                                fieldWithPath("result.[].otherTeamName").type(STRING).description("매치 상대팀 명"),
                                fieldWithPath("result.[].home").type(BOOLEAN).description("매치 홈구장 여부"),
                                fieldWithPath("result.[].notice").type(STRING).description("매치 공지"),
                                fieldWithPath("result.[].status").type(STRING).description("매치 상태 - 'matchStatus' 공통 Code 참조"),
                                fieldWithPath("result.[].matchDate").type(OBJECT).description("매치 일자"),
                                fieldWithPath("result.[].matchDate.startDate").type(STRING).description("매치 시작 일자"),
                                fieldWithPath("result.[].matchDate.endDate").type(STRING).description("매치 종료 일자"),
                                fieldWithPath("result.[].voteDate").type(OBJECT).description("매치 투표 일자"),
                                fieldWithPath("result.[].voteDate.startDate").type(STRING).description("매치 투표 시작 일자"),
                                fieldWithPath("result.[].voteDate.endDate").type(STRING).description("매치 종료 종료 일자"),
                                fieldWithPath("result.[].attendDate").type(OBJECT).description("메치 출석 일자"),
                                fieldWithPath("result.[].attendDate.deadlineDate").type(STRING).description("메치 출석 마감 일자"),
                                fieldWithPath("result.[].createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.[].updatedDate").type(STRING).description("수정일자"),
                                fieldWithPath("result.[].team").type(OBJECT).description("팀"),
                                fieldWithPath("result.[].team.teamId").type(NUMBER).description("팀 ID"),
                                fieldWithPath("result.[].team.name").type(STRING).description("팀 이름"),
                                fieldWithPath("result.[].team.status").type(STRING).description("팀 상태 - 'baseStatus' 공통 Code 참조"),
                                fieldWithPath("result.[].team.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.[].team.updatedDate").type(STRING).description("수정일자"),
                                fieldWithPath("result.[].stadium").type(OBJECT).description("구장"),
                                fieldWithPath("result.[].stadium.stadiumId").type(NUMBER).description("구장 ID"),
                                fieldWithPath("result.[].stadium.name").type(STRING).description("구장 이름"),
                                fieldWithPath("result.[].stadium.address").type(STRING).description("구장 주소"),
                                fieldWithPath("result.[].stadium.latitude").type(NUMBER).description("구장 위도"),
                                fieldWithPath("result.[].stadium.longitude").type(NUMBER).description("구장 경도"),
                                fieldWithPath("result.[].stadium.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.[].stadium.updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }

    @Test
    @WithMockUser
    void read() throws Exception {
        //given
        given(matchController
                .read(eq(1L)))
                .willReturn(new BaseResponse<>(MatchResponse.builder()
                        .matchId(1L)
                        .otherTeamName("징기스칸")
                        .home(FALSE)
                        .notice("02월 25일 10시 12시 황송")
                        .status(VOTE)
                        .matchDate(MatchDate.of(of(2023, 2, 25, 10, 0), of(2023, 2, 25, 12, 0)))
                        .voteDate(VoteDate.of(of(2023, 2, 20, 9, 0), of(2023, 2, 23, 12, 0)))
                        .attendDate(AttendDate.of(of(2023, 2, 25, 9, 30)))
                        .createdDate(now())
                        .updatedDate(now())
                        .team(TeamResponse.builder()
                                .teamId(1L)
                                .name("프로빈")
                                .status(WAIT)
                                .createdDate(now())
                                .updatedDate(now())
                                .build())
                        .stadium(StadiumResponse.builder()
                                .stadiumId(1L)
                                .name("탄천B")
                                .address("경기 성남시 중원구 여수동 7-17")
                                .longitude(127.119444)
                                .latitude(37.4257533)
                                .createdDate(now())
                                .updatedDate(now())
                                .build())
                        .build()));

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/match/{id}", 1L));

        //then
        result.andExpect(status().isOk())
                .andDo(document("match-read",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("매치 ID")
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("결과코드"),
                                fieldWithPath("message").type(STRING).description("결과메세지"),
                                fieldWithPath("result.matchId").type(NUMBER).description("매치 ID"),
                                fieldWithPath("result.otherTeamName").type(STRING).description("매치 상대팀 명"),
                                fieldWithPath("result.home").type(BOOLEAN).description("매치 홈구장 여부"),
                                fieldWithPath("result.notice").type(STRING).description("매치 공지"),
                                fieldWithPath("result.status").type(STRING).description("매치 상태 - 'matchStatus' 공통 Code 참조"),
                                fieldWithPath("result.matchDate").type(OBJECT).description("매치 일자"),
                                fieldWithPath("result.matchDate.startDate").type(STRING).description("매치 시작 일자"),
                                fieldWithPath("result.matchDate.endDate").type(STRING).description("매치 종료 일자"),
                                fieldWithPath("result.voteDate").type(OBJECT).description("매치 투표 일자"),
                                fieldWithPath("result.voteDate.startDate").type(STRING).description("매치 투표 시작 일자"),
                                fieldWithPath("result.voteDate.endDate").type(STRING).description("매치 종료 종료 일자"),
                                fieldWithPath("result.attendDate").type(OBJECT).description("메치 출석 일자"),
                                fieldWithPath("result.attendDate.deadlineDate").type(STRING).description("메치 출석 마감 일자"),
                                fieldWithPath("result.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.updatedDate").type(STRING).description("수정일자"),
                                fieldWithPath("result.team").type(OBJECT).description("팀"),
                                fieldWithPath("result.team.teamId").type(NUMBER).description("팀 ID"),
                                fieldWithPath("result.team.name").type(STRING).description("팀 이름"),
                                fieldWithPath("result.team.status").type(STRING).description("팀 상태 - 'baseStatus' 공통 Code 참조"),
                                fieldWithPath("result.team.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.team.updatedDate").type(STRING).description("수정일자"),
                                fieldWithPath("result.stadium").type(OBJECT).description("구장"),
                                fieldWithPath("result.stadium.stadiumId").type(NUMBER).description("구장 ID"),
                                fieldWithPath("result.stadium.name").type(STRING).description("구장 이름"),
                                fieldWithPath("result.stadium.address").type(STRING).description("구장 주소"),
                                fieldWithPath("result.stadium.latitude").type(NUMBER).description("구장 위도"),
                                fieldWithPath("result.stadium.longitude").type(NUMBER).description("구장 경도"),
                                fieldWithPath("result.stadium.createdDate").type(STRING).description("생성일자"),
                                fieldWithPath("result.stadium.updatedDate").type(STRING).description("수정일자")
                        )
                ));
    }
}
