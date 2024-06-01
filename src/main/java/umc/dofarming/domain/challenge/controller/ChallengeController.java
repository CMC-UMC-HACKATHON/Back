package umc.dofarming.domain.challenge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.dofarming.api_response.ApiResponse;
import umc.dofarming.domain.challenge.DTO.ChallengeResponseDTO;
import umc.dofarming.domain.challenge.service.ChallengeService;

@RestController
@RequestMapping("/challenges")
@RequiredArgsConstructor
@Tag(name = "챌린지 API", description = "공지사항 댓글 관련 API")
public class ChallengeController {
    private final ChallengeService challengeService;

    @PostMapping("/onging")
    @Operation(summary = "현재 참여 중인 챌린지")
    public ApiResponse<ChallengeResponseDTO.GetMyChallengeInfoResultList> getOngoingChallengeInfoByMemberId() {
        ChallengeResponseDTO.GetMyChallengeInfoResultList getMyChallengeInfoResultList = challengeService.findMyChallengeInfo(true);
        return ApiResponse.onSuccess(getMyChallengeInfoResultList);
    }

    @PostMapping("/complete")
    @Operation(summary = "참여 완료한 챌린지")
    public ApiResponse<ChallengeResponseDTO.GetMyChallengeInfoResultList> getCompleteChallengeInfoByMemberId() {
        ChallengeResponseDTO.GetMyChallengeInfoResultList getMyChallengeInfoResultList = challengeService.findMyChallengeInfo(false);
        return ApiResponse.onSuccess(getMyChallengeInfoResultList);
    }

}
