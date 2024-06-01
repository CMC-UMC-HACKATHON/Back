package umc.dofarming.domain.challengeMission.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.dofarming.domain.challengeMission.controller.request.ProofMissionRequest;
import umc.dofarming.domain.challengeMission.controller.response.ListChallengeMissionResponse;
import umc.dofarming.domain.challengeMission.controller.response.ListChallengeMissionWeekResponse;
import umc.dofarming.domain.memberMission.service.CreateMemberMissionService;

@Service
@RequiredArgsConstructor
public class ChallengeMissionService {

  private final ListChallengeMissionService listChallengeMissionService;
  private final CreateMemberMissionService createMemberMissionService;

  /**
   * 오늘의 미션
   */
  public ListChallengeMissionResponse getChallengeMission(Long challengeId) {
    return listChallengeMissionService.getChallengeMission(challengeId);
  }

  public ListChallengeMissionWeekResponse getChallengeMissionWeek(Long challengeId) {
    return listChallengeMissionService.getChallengeMissionWeek(challengeId);
  }

  public void proofMission(Long missionId, @Valid ProofMissionRequest request) {
    createMemberMissionService.proofMission(missionId, request);
  }
}
