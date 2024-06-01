package umc.dofarming.domain.challenge.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "챌린지 최종 결과 확인 여부 응답 객체")
public class CheckChallengeLogResponse {

  @Schema(description = "챌린지 최종 결과 확인 여부")
  private boolean checkYN;
}
