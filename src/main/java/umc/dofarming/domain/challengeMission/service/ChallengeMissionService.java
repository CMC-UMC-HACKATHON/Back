package umc.dofarming.domain.challengeMission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.dofarming.domain.challengeMission.controller.response.ListChallengeMissionResponse;
import umc.dofarming.domain.challengeMission.controller.response.ListChallengeMissionWeekResponse;

@Service
@RequiredArgsConstructor
public class ChallengeMissionService {

  private final ListChallengeMissionService listChallengeMissionService;

  /**
   * 오늘의 미션
   */
  public ListChallengeMissionResponse getChallengeMission(Long challengeId) {
    return listChallengeMissionService.getChallengeMission(challengeId);
  }

  public ListChallengeMissionWeekResponse getChallengeMissionWeek(Long challengeId) {
    return listChallengeMissionService.getChallengeMissionWeek(challengeId);
  }
}
