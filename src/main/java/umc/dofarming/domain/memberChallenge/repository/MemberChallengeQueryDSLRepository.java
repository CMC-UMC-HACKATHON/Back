package umc.dofarming.domain.memberChallenge.repository;

import umc.dofarming.domain.challenge.Challenge;

import java.util.List;

public interface MemberChallengeQueryDSLRepository {
    List<Challenge> findChallengesByMemberId(Long memberId, boolean ongoing);
}
