package umc.dofarming.domain.challengeMission.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.dofarming.api_response.ApiResponse;
import umc.dofarming.domain.challengeMission.controller.request.ProofMissionRequest;
import umc.dofarming.domain.challengeMission.controller.response.ListChallengeMissionResponse;
import umc.dofarming.domain.challengeMission.controller.response.ListChallengeMissionWeekResponse;
import umc.dofarming.domain.challengeMission.service.ChallengeMissionService;

@RestController
@RequestMapping("/missions")
@RequiredArgsConstructor
@Tag(name = "MissionController", description = "미션 관련 API")
public class ChallengeMissionController {

  private final ChallengeMissionService challengeMissionService;

  @GetMapping("/{challengeId}")
  @Operation(summary = "오늘의 미션 & 총 미션 달성 수")
  public ApiResponse<ListChallengeMissionResponse> getChallengeMission(@PathVariable Long challengeId) {
    return ApiResponse.onSuccess(challengeMissionService.getChallengeMission(challengeId));
  }

  @GetMapping("/{challengeId}/week")
  @Operation(summary = "이번주 미션 달성 현황")
  public ApiResponse<ListChallengeMissionWeekResponse> getChallengeMissionWeek(@PathVariable Long challengeId) {
    return ApiResponse.onSuccess(challengeMissionService.getChallengeMissionWeek(challengeId));
  }

  @PostMapping("/{missionId}")
  @Operation(summary = "미션 수행 인증")
  public ApiResponse<Void> proofMission(
    @PathVariable Long missionId,
    @RequestBody @Valid ProofMissionRequest request) {
    challengeMissionService.proofMission(missionId, request);
    return ApiResponse.onSuccess();
  }

}
