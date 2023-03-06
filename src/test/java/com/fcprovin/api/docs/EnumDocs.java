package com.fcprovin.api.docs;

import com.fcprovin.api.entity.*;

import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

public class EnumDocs {

    private final Map<String, String> attendStatus = getDocs(AttendStatus.values());
    private final Map<String, String> baseStatus = getDocs(BaseStatus.values());
    private final Map<String, String> matchStatus = getDocs(MatchStatus.values());
    private final Map<String, String> playerAuthority = getDocs(PlayerAuthority.values());
    private final Map<String, String> position = getDocs(Position.values());
    private final Map<String, String> snsType = getDocs(SnsType.values());
    private final Map<String, String> voteStatus = getDocs(VoteStatus.values());

    public Map<String, String> getAttendStatus() {
        return attendStatus;
    }

    public Map<String, String> getBaseStatus() {
        return baseStatus;
    }

    public Map<String, String> getMatchStatus() {
        return matchStatus;
    }

    public Map<String, String> getPlayerAuthority() {
        return playerAuthority;
    }

    public Map<String, String> getPosition() {
        return position;
    }

    public Map<String, String> getSnsType() {
        return snsType;
    }

    public Map<String, String> getVoteStatus() {
        return voteStatus;
    }

    private Map<String, String> getDocs(EnumType[] enumTypes) {
        return stream(enumTypes).collect(toMap(EnumType::getName, EnumType::getDescription));
    }
}
