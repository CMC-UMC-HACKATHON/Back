package umc.dofarming.domain.challenge_log.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.dofarming.domain.challenge_log.ChallengeLog;
import umc.dofarming.domain.memberChallenge.MemberChallenge;

public interface ChallengeLogRepository extends JpaRepository<ChallengeLog, Long> {

  Optional<ChallengeLog> findByMemberChallenge(MemberChallenge memberChallenge);
}
