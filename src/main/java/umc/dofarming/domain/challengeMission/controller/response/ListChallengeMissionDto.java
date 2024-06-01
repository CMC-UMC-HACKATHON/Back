package umc.dofarming.domain.challengeMission.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.dofarming.domain.enums.MissionStatus;
import umc.dofarming.domain.enums.MissionType;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "챌린지 미션 목록 응답 객체")
public class ListChallengeMissionDto {

  @Schema(description = "챌린지 미션 ID")
  private long missionId;

  @Schema(description = "인증 이미지 URL")
  private String proofUrl;

  @Schema(description = "챌린지 미션 상태(진행중, 완료)")
  private MissionStatus missionStatus;

  @Schema(description = "챌린지 미션 유형(필수, 옵션)")
  private MissionType missionType;

  @Schema(description = "챌린지 미션 이름")
  private String missionName;

  @Schema(hidden = true)
  public void updateMissionName(String name) {
    this.missionName = name;
  }
}
