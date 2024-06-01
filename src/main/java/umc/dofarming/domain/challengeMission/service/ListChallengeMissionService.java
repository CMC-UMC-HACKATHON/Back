package umc.dofarming.domain.challengeMission.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.dofarming.domain.challengeMission.controller.response.ListChallengeMissionDto;
import umc.dofarming.domain.challengeMission.controller.response.ListChallengeMissionResponse;
import umc.dofarming.domain.challengeMission.controller.response.ListChallengeMissionWeekDto;
import umc.dofarming.domain.challengeMission.controller.response.ListChallengeMissionWeekResponse;
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
    long completedMissionCount = listChallengeMissionRepository.getCompletedMissionCount(challengeId);

    return ListChallengeMissionResponse.builder()
      .completedMissionCount(completedMissionCount)
      .missionList(challengeMissionList)
      .build();
  }

  public ListChallengeMissionWeekResponse getChallengeMissionWeek(Long challengeId) {
    LocalDate today = LocalDate.now();
    LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

    List<ListChallengeMissionWeekDto> missionWeekList = new ArrayList<>();
    for (int i = 0; i < 7; i++) {
      LocalDate now = monday.plusDays(i);
      missionWeekList.add(
        ListChallengeMissionWeekDto.builder()
          .date(now.getDayOfMonth())
          .completeYN(listChallengeMissionRepository.getCompleteYN(challengeId, now) > 0)
          .build()
      );
    }

    return ListChallengeMissionWeekResponse.builder()
      .missionWeekList(missionWeekList)
      .build();
  }
}
