package umc.dofarming.domain.challenge.converter;

import umc.dofarming.domain.challenge.Challenge;
import umc.dofarming.domain.challenge.dto.ChallengeResponseDTO;


public class ChallengeConverter {
    public static ChallengeResponseDTO.GetMyChallengeInfoResult toChallengeResponseDTO(Challenge challenge, int count) {
        return ChallengeResponseDTO.GetMyChallengeInfoResult.builder()
                .challengeId(challenge.getId())
                .title(challenge.getTitle())
                .startDate(challenge.getStartDate())
                .endDate(challenge.getEndDate())
                .category(challenge.getCategory().getTitle())
                .rewardType(challenge.getRewardType().toString())
                .memberCount(count)
                .build();
    }

}