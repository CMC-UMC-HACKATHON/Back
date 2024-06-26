package umc.dofarming.domain.challenge.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class ChallengeResponseDTO {

    @Builder
    public record GetMyChallengeInfoResult(
            Long challengeId,
            String title,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String profileUrl,
            String rewardType,
            String category,
            int memberCount // 해당 챌린지에 참여 중인 멤버의 수
    ){
    }

}
