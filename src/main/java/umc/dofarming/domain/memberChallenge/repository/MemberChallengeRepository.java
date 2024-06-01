package umc.dofarming.domain.memberChallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.dofarming.domain.memberChallenge.MemberChallenge;

@Repository
public interface MemberChallengeRepository extends JpaRepository<MemberChallenge, Long>, MemberChallengeQueryDSLRepository {
    long countByChallengeId(Long challengeId);
}
