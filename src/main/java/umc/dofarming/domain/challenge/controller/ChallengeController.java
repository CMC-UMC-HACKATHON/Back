package umc.dofarming.domain.challenge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.dofarming.api_response.ApiResponse;
import umc.dofarming.domain.challenge.dto.ChallengeResponse;
import umc.dofarming.domain.challenge.service.ChallengeService;
import umc.dofarming.domain.enums.SortBy;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.dofarming.domain.challenge.dto.ChallengeResponseDTO;

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

    @GetMapping("")
    @Operation(summary = "챌린지 리스트 조회 API")
    @ApiResponses( value ={
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "KEY_NOT_EXIST", description = "SortBy 값 오류")
    })
    public ApiResponse<List<ChallengeResponse.JoinChallenge>> joinChallengeList
            (
                    @RequestParam("sortBy") SortBy sortBy
            )
    {
        return ApiResponse.onSuccess(challengeService.joinChallengeList(sortBy));
    }

    @GetMapping("/{challengeId}")
    @Operation(summary = "챌린지 상세 조회 API")
    @ApiResponses( value ={
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "KEY_NOT_EXIST", description = "id 값에 해당하는 챌린지 존재하지 않음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "VALIDATION_ERROR", description = "시작 기한이 지난 챌린지")
    })
    public ApiResponse<ChallengeResponse.JoinChallengeDetail> joinChallengeDetail
            (
                    @PathVariable("challengeId") Long challengeId
            )
    {
        return ApiResponse.onSuccess(challengeService.joinChallengeDetail(challengeId));
    }

    @PostMapping("/{challengeId}")
    @Operation(summary = "챌린지 참여하기 API")
    @ApiResponses( value ={
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "KEY_NOT_EXIST", description = "id 값에 해당하는 챌린지 존재하지 않음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "VALIDATION_ERROR", description = "시작 기한이 지난 챌린지"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "VALIDATION_ERROR", description = "이미 참여한 챌린지입니다")
    })
    public ApiResponse<Long> postChallengeMission
            (
                    @PathVariable("challengeId") Long challengeId
            )
    {
        return ApiResponse.onSuccess(challengeService.postChallengeMission(challengeId));
    }



}
