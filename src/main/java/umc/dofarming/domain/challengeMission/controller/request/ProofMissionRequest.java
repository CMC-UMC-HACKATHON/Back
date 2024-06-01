package umc.dofarming.domain.challengeMission.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "미션 수행 인증 요청 객체")
public class ProofMissionRequest {

  @NotBlank
  @Schema(description = "미션 수행 인증 요청 객체", requiredMode = RequiredMode.REQUIRED)
  private String proofUrl;

  @Schema(description = "미션 수행 인증 요청 객체", requiredMode = RequiredMode.NOT_REQUIRED)
  private String proofText;
}
