package umc.dofarming.domain.challenge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.dofarming.api_response.ApiResponse;
import umc.dofarming.domain.challenge.controller.response.ChallengeResultResponse;
import umc.dofarming.domain.challenge.controller.response.CheckChallengeLogResponse;
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
  public ApiResponse<List<ChallengeResponseDTO.GetMyChallengeInfoResult>> getOngoingChallengeInfoByMemberId() {
    List<ChallengeResponseDTO.GetMyChallengeInfoResult> getMyChallengeInfoResultList = challengeService.findMyChallengeInfo(true);
    return ApiResponse.onSuccess(getMyChallengeInfoResultList);
  }

  @PostMapping("/complete")
  @Operation(summary = "참여 완료한 챌린지")
  public ApiResponse<List<ChallengeResponseDTO.GetMyChallengeInfoResult>> getCompleteChallengeInfoByMemberId() {
    List<ChallengeResponseDTO.GetMyChallengeInfoResult> getMyChallengeInfoResultList = challengeService.findMyChallengeInfo(false);
    return ApiResponse.onSuccess(getMyChallengeInfoResultList);
  }

  @GetMapping("")
  @Operation(summary = "챌린지 리스트 조회 API", description = "Defult 챌린지 리스트 4개, 한 챌린지가 끝나면 자동으로 다른 랜덤한 챌린지가 만들어짐")
  @ApiResponses(value = {
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "KEY_NOT_EXIST", description = "SortBy 값 오류"
      , content = @Content(schema = @Schema(implementation = ApiResponse.class)))
  })
  @Parameters({
    @Parameter(name = "sortBy", description = "POPULAR, LATEST의 String을 가진 Enum 값, 각각 인기순과 최신순을 의미")
  })
  public ApiResponse<List<ChallengeResponse.JoinChallenge>> joinChallengeList
    (
      @RequestParam("sortBy") @Valid @NotNull SortBy sortBy
    ) {
    return ApiResponse.onSuccess(challengeService.joinChallengeList(sortBy));
  }

  @GetMapping("/{challengeId}")
  @Operation(summary = "챌린지 상세 조회 API")
  @ApiResponses(value = {
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "KEY_NOT_EXIST", description = "id 값에 해당하는 챌린지 존재하지 않음"
      , content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "VALIDATION_ERROR", description = "시작 기한이 지난 챌린지"
      , content = @Content(schema = @Schema(implementation = ApiResponse.class)))
  })
  public ApiResponse<ChallengeResponse.JoinChallengeDetail> joinChallengeDetail
    (
      @PathVariable("challengeId") Long challengeId
    ) {
    return ApiResponse.onSuccess(challengeService.joinChallengeDetail(challengeId));
  }

  @PostMapping("/{challengeId}")
  @Operation(summary = "챌린지 참여하기 API")
  @ApiResponses(value = {
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "KEY_NOT_EXIST", description = "id 값에 해당하는 챌린지 존재하지 않음"
      , content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "VALIDATION_ERROR", description = "시작 기한이 지난 챌린지"
      , content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "VALIDATION_ERROR", description = "이미 참여한 챌린지입니다"
      , content = @Content(schema = @Schema(implementation = ApiResponse.class)))
  })
  public ApiResponse<Long> postChallengeMission
    (
      @PathVariable("challengeId") Long challengeId
    ) {
    return ApiResponse.onSuccess(challengeService.postChallengeMission(challengeId));
  }

  @GetMapping("/{challengeId}/result")
  @Operation(summary = "챌린지 결과 조회")
  public ApiResponse<ChallengeResultResponse> getChallengeResult(@PathVariable Long challengeId) {
    return ApiResponse.onSuccess(challengeService.getChallengeResult(challengeId));
  }

  @PostMapping("/{challengeId}/log")
  @Operation(summary = "챌린지 최종 결과 확인 이력 저장")
  public ApiResponse<Void> saveChallengeLog(@PathVariable Long challengeId) {
    challengeService.saveChallengeLog(challengeId);
    return ApiResponse.onSuccess();
  }

  @GetMapping("/{challengeId}/log")
  @Operation(summary = "챌린지 최종 결과 확인 여부 확인")
  public ApiResponse<CheckChallengeLogResponse> checkChallengeLog(@PathVariable Long challengeId) {
    return ApiResponse.onSuccess(challengeService.checkChallengeLog(challengeId));
  }

}
