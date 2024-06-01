package umc.dofarming.domain.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.dofarming.domain.challenge.Challenge;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
}
