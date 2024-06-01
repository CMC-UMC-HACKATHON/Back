package umc.dofarming.domain.challengeMission.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "챌린지 미션 목록 응답 객체")
public class ListChallengeMissionResponse {

  @Schema(description = "챌린지 미션 목록 응답 객체 list")
  private List<ListChallengeMissionDto> missionList;
}
