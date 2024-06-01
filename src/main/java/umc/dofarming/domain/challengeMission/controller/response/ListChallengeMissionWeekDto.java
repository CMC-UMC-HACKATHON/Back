package umc.dofarming.domain.challengeMission.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "챌린지 미션 목록 응답 객체")
public class ListChallengeMissionWeekDto {

  @Schema(description = "날짜")
  private int date;

  @Schema(description = "미션 달성 여부")
  private boolean completeYN;
}
