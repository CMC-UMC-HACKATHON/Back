package umc.dofarming.domain.challenge.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.dofarming.domain.challenge.Challenge;
import umc.dofarming.domain.challenge.dto.ChallengeResponse;
import umc.dofarming.global.converter.DataConverter;

@Component
@RequiredArgsConstructor
public class ChallengeMapper {

    // 챌린지 별 정보 조회
    public static ChallengeResponse.JoinChallenge toJoinChallenge(
            Challenge challenge, Integer participantNum
    ) {
        return ChallengeResponse.JoinChallenge.builder()
                .challengeId(challenge.getId())
                .title(challenge.getTitle())
                .category(challenge.getCategory())
                .rewardType(challenge.getRewardType())
                .startDate(DataConverter.convertToTimeFormat(challenge.getStartDate()))
                .endDate(DataConverter.convertToTimeFormat(challenge.getEndDate()))
                .participantNum(participantNum)
                .build();
    }

    //챌린지 세부 정보 조회
    public static ChallengeResponse.JoinChallengeDetail toJoinChallengeDatail(
            Challenge challenge, Integer participantNum
    ) {
        return ChallengeResponse.JoinChallengeDetail.builder()
                .challengeId(challenge.getId())
                .title(challenge.getTitle())
                .category(challenge.getCategory())
                .rewardType(challenge.getRewardType())
                .startDate(DataConverter.convertToTimeFormat(challenge.getStartDate()))
                .endDate(DataConverter.convertToTimeFormat(challenge.getEndDate()))
                .participantNum(participantNum)
                .challengeAmount(challenge.getMoney())
                .challengeTotalAmount(challenge.getMoney()*participantNum) //총금액
                .build();
    }

}
