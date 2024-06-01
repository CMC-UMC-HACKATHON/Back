package umc.dofarming.domain.memberMission.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.dofarming.domain.challengeMission.ChallengeMission;
import umc.dofarming.domain.member.Member;
import umc.dofarming.domain.memberMission.MemberMission;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

  Optional<MemberMission> findByChallengeMissionAndMember(ChallengeMission challengeMission, Member member);
}
