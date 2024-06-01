package umc.dofarming.domain.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.dofarming.domain.enums.Category;
import umc.dofarming.domain.enums.RewardType;

public class ChallengeResponse {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinChallenge{
        Long challengeId;
        String title;
        String endDate;
        String startDate;
        String category;
        String categoryImage;
        String rewardType;
        Integer participantNum;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinChallengeDetail{
        Long challengeId;
        String title;
        String endDate;
        String startDate;
        String category;
        String categoryImage;
        String rewardType;
        Integer participantNum;
        Integer challengeAmount;
        Integer challengeTotalAmount;
    }

}
