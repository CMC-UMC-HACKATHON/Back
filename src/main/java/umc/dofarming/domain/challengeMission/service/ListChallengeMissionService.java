package umc.dofarming.domain.challengeMission.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.dofarming.domain.challengeMission.controller.response.ListChallengeMissionDto;
import umc.dofarming.domain.challengeMission.controller.response.ListChallengeMissionResponse;
import umc.dofarming.domain.challengeMission.repository.ListChallengeMissionRepository;
import umc.dofarming.domain.enums.Mission;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListChallengeMissionService {

  private final ListChallengeMissionRepository listChallengeMissionRepository;

  /**
   * 오늘의 미션
   */
  public ListChallengeMissionResponse getChallengeMission(Long challengeId) {
    List<ListChallengeMissionDto> challengeMissionList = listChallengeMissionRepository.getChallengeMission(challengeId);
    for (ListChallengeMissionDto dto : challengeMissionList) {
      dto.updateMissionName(Mission.valueOf(dto.getMissionName()).getTitle());
    }

    return ListChallengeMissionResponse.builder()
      .missionList(challengeMissionList)
      .build();
  }
}
