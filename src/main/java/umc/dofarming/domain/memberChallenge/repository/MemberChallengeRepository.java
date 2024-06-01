package umc.dofarming.domain.memberChallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.dofarming.domain.memberChallenge.MemberChallenge;
import umc.dofarming.domain.challenge.Challenge;
import umc.dofarming.domain.member.Member;


@Repository
public interface MemberChallengeRepository extends JpaRepository<MemberChallenge, Long>, MemberChallengeQueryDSLRepository {
    long countByChallengeId(Long challengeId);
    Integer countByChallenge(Challenge challenge);
    Boolean existsByMemberAndChallenge(Member member, Challenge challenge);
}
