package umc.dofarming.domain.challenge.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "최종 순위 목록 응답 객체")
public class ChallengeResultDto {

  @Schema(description = "최종 순위")
  private int rank;

  @Schema(description = "닉네임")
  private String username;

  @Schema(description = "최종 상금 or 상품")
  private String reward;

  @Schema(description = "완료한 미션 개수")
  private long completeCount;

  @JsonIgnore
  private int money;

  @JsonIgnore
  private String loginId;

  public void update(int rank, String reward) {
    this.rank = rank;
    this.reward = reward;
  }
}
