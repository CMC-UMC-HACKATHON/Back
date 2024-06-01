package umc.dofarming.domain.memberChallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.dofarming.domain.challenge.Challenge;
import umc.dofarming.domain.member.Member;
import umc.dofarming.domain.memberChallenge.MemberChallenge;

public interface MemberChallengeRepository extends JpaRepository<MemberChallenge, Long>{
    Integer countByChallenge(Challenge challenge);
    Boolean existsByMemberAndChallenge(Member member, Challenge challenge);
}
