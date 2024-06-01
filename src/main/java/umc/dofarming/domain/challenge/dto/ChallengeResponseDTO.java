package umc.dofarming.domain.challenge.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class ChallengeResponseDTO {
    public static ChallengeResponseDTO.GetMyChallengeInfoResultList GetMyChallengeInfoResultList;

    @Builder
    public record GetMyChallengeInfoResult(
            Long id,
            String title,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String profileUrl,
            String rewardType,
            String category,
            Long memberCount, // 해당 챌린지에 참여 중인 멤버의 수
            Integer complete_rate
    ){
    }

    @Builder
    public record GetMyChallengeInfoResultList(
            List<GetMyChallengeInfoResult> getMyChallengeInfoResultList
    ){
    }
}
