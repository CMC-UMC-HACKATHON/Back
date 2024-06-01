package umc.dofarming.domain.memberChallenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umc.dofarming.domain.memberChallenge.repository.MemberChallengeRepository;

@Service
public class MemberChallengeService {
    @Autowired
    private MemberChallengeRepository memberChallengeRepository;

    public long countMemberChallengesByChallengeId(Long challengeId) {
        return memberChallengeRepository.countByChallengeId(challengeId);
    }
}
