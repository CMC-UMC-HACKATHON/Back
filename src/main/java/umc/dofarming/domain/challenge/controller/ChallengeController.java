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


@RestController
@RequestMapping("/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

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