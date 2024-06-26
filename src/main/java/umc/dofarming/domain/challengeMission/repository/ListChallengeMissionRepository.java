package umc.dofarming.domain.challengeMission.repository;

import static umc.dofarming.domain.challengeMission.QChallengeMission.challengeMission;
import static umc.dofarming.domain.memberMission.QMemberMission.memberMission;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import umc.dofarming.domain.challengeMission.controller.response.ListChallengeMissionDto;
import umc.dofarming.domain.enums.MissionStatus;
import umc.dofarming.util.SecurityUtils;

@Repository
@RequiredArgsConstructor
public class ListChallengeMissionRepository {

  private final JPAQueryFactory queryFactory;


  public List<ListChallengeMissionDto> getChallengeMission(Long challengeId) {

    return queryFactory
      .select(Projections.fields(ListChallengeMissionDto.class,
          challengeMission.id.as("missionId"),
          memberMission.proofUrl.as("proofUrl"),
          memberMission.missionStatus,
          challengeMission.type.as("missionType"),
          challengeMission.mission.stringValue().as("missionName")
        )
      )
      .from(challengeMission)
      .innerJoin(memberMission).on(challengeMission.eq(memberMission.challengeMission))
      .where(
        challengeMission.challenge.id.eq(challengeId),
        challengeMission.missionDate.year().eq(LocalDate.now().getYear()),
        challengeMission.missionDate.month().eq(LocalDate.now().getMonthValue()),
        challengeMission.missionDate.dayOfMonth().eq(LocalDate.now().getDayOfMonth()),
        memberMission.member.loginId.eq(SecurityUtils.getCurrentMemberLoginId())
      )
      .fetch();
  }

  public long getCompletedMissionCount(Long challengeId) {

    return queryFactory
      .selectFrom(challengeMission)
      .innerJoin(memberMission).on(challengeMission.eq(memberMission.challengeMission))
      .where(
        challengeMission.challenge.id.eq(challengeId),
        memberMission.missionStatus.eq(MissionStatus.COMPLETE),
        memberMission.member.loginId.eq(SecurityUtils.getCurrentMemberLoginId())
      )
      .fetch().size();
  }

  public long getCompleteYN(Long challengeId, LocalDate now) {
    return queryFactory
      .selectFrom(memberMission)
      .innerJoin(challengeMission).on(memberMission.challengeMission.eq(challengeMission))
      .where(
        challengeMission.id.eq(challengeId),
        memberMission.missionStatus.eq(MissionStatus.COMPLETE),
        memberMission.member.loginId.eq(SecurityUtils.getCurrentMemberLoginId()),
        challengeMission.missionDate.year().eq(now.getYear()),
        challengeMission.missionDate.month().eq(now.getMonthValue()),
        challengeMission.missionDate.dayOfMonth().eq(now.getDayOfMonth())
        )
      .fetch().size();
  }
}
