package umc.dofarming.domain.challengeMission.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.dofarming.domain.challengeMission.ChallengeMission;

public interface ChallengeMissionRepository extends JpaRepository<ChallengeMission, Long> {

  List<ChallengeMission> findAllByChallengeId(Long challengeId);
}
