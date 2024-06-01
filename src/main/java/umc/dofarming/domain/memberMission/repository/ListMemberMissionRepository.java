package umc.dofarming.domain.memberMission.repository;

import static umc.dofarming.domain.challenge.QChallenge.challenge;
import static umc.dofarming.domain.challengeMission.QChallengeMission.challengeMission;
import static umc.dofarming.domain.member.QMember.member;
import static umc.dofarming.domain.memberMission.QMemberMission.memberMission;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import umc.dofarming.domain.challenge.controller.response.ChallengeResultDto;

@Repository
@RequiredArgsConstructor
public class ListMemberMissionRepository {

  private final JPAQueryFactory queryFactory;

  public List<ChallengeResultDto> getChallengeResult(Long challengeId) {
    return queryFactory
      .select(Projections.fields(ChallengeResultDto.class,
        member.username,
        memberMission.count().as("completeCount"),
        challenge.money,
        member.loginId
        )
      )
      .from(memberMission)
      .innerJoin(challengeMission).on(memberMission.challengeMission.eq(challengeMission))
      .innerJoin(challenge).on(challengeMission.challenge.eq(challenge))
      .innerJoin(member).on(memberMission.member.eq(member))
      .where(challenge.id.eq(challengeId))
      .groupBy(memberMission.member)
      .orderBy(memberMission.count().desc())
      .fetch();
  }
}
