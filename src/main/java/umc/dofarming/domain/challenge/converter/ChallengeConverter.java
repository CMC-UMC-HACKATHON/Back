package umc.dofarming.domain.challenge.converter;

import umc.dofarming.domain.challenge.Challenge;
import umc.dofarming.domain.challenge.dto.ChallengeResponseDTO;
import umc.dofarming.domain.memberChallenge.service.MemberChallengeService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ChallengeConverter {
    static MemberChallengeService memberChallengeService;
    public static ChallengeResponseDTO.GetMyChallengeInfoResult toChallengeResponseDTO(Challenge challenge) {
        return ChallengeResponseDTO.GetMyChallengeInfoResult.builder()
                .title(challenge.getTitle())
                .startDate(challenge.getStartDate())
                .endDate(challenge.getEndDate())
                .category(challenge.getCategory().getTitle())
                .rewardType(challenge.getRewardType().toString())
                .memberCount(memberChallengeService.countMemberChallengesByChallengeId(challenge.getId()))
                .build();
    }

    public static ChallengeResponseDTO.GetMyChallengeInfoResultList toChallengeResponseDTOList(List<Challenge> challengeList) {
        if (challengeList.isEmpty()) {
            return ChallengeResponseDTO.GetMyChallengeInfoResultList.builder()
                    .getMyChallengeInfoResultList(Collections.emptyList())
                    .build();
        }
        return ChallengeResponseDTO.GetMyChallengeInfoResultList.builder()
                .getMyChallengeInfoResultList(challengeList.stream()
                        .map(ChallengeConverter::toChallengeResponseDTO)
                        .collect(Collectors.toList()))
                .build();
    }
}