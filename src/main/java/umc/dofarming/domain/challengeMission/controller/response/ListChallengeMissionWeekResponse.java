package umc.dofarming.domain.challengeMission.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "챌린지 미션 목록 응답 객체")
public class ListChallengeMissionWeekResponse {

  @Schema(description = "이번주 요일별 미션 달성 현황 객체 list")
  private List<ListChallengeMissionWeekDto> missionWeekList;
}
