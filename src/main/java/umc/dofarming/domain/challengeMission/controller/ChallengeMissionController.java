package umc.dofarming.domain.challengeMission.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.dofarming.api_response.ApiResponse;
import umc.dofarming.domain.challengeMission.controller.response.ListChallengeMissionResponse;
import umc.dofarming.domain.challengeMission.service.ChallengeMissionService;

@RestController
@RequestMapping("/missions")
@RequiredArgsConstructor
@Tag(name = "MissionController", description = "미션 관련 API")
public class ChallengeMissionController {

  private final ChallengeMissionService challengeMissionService;

  @GetMapping("/{challengeId}")
  @Operation(summary = "오늘의 미션")
  public ApiResponse<ListChallengeMissionResponse> getChallengeMission(@PathVariable Long challengeId) {
    return ApiResponse.onCreate(challengeMissionService.getChallengeMission(challengeId));
  }

}
