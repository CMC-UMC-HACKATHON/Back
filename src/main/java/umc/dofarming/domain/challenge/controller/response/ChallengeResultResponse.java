package umc.dofarming.domain.challenge.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "챌린지 결과 응답 객체")
public class ChallengeResultResponse {

  @Schema(description = "최종 순위 목록")
  private List<ChallengeResultDto> rankingList;

  @Schema(description = "최종 순위")
  private int ranking;

  @Schema(description = "최종 상금 or 상품")
  private String reward;

  public void update(int ranking, String reward) {
    this.ranking = ranking;
    this.reward = reward;
  }
}
