package umc.dofarming.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.dofarming.domain.challenge.Challenge;
import umc.dofarming.domain.challenge.DTO.ChallengeResponseDTO;
import umc.dofarming.domain.challenge.converter.ChallengeConverter;
import umc.dofarming.domain.challenge.repository.ChallengeRepository;
import umc.dofarming.domain.member.repository.MemberRepository;
import umc.dofarming.domain.memberChallenge.repository.MemberChallengeRepository;
import umc.dofarming.util.SecurityUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeService {
    private final MemberRepository memberRepository;
    private final MemberChallengeRepository memberChallengeRepository;
    public ChallengeResponseDTO.GetMyChallengeInfoResultList findMyChallengeInfo(boolean ongoing){
        String loginId = SecurityUtils.getCurrentMemberLoginId();
        Long memberId = memberRepository.findByLoginId(loginId).get().getId();
        List<Challenge> challengeList = memberChallengeRepository.findChallengesByMemberId(memberId, ongoing);

        return ChallengeConverter.toChallengeResponseDTOList(challengeList);
    }
}
