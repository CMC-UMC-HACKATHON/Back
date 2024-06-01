package umc.dofarming.domain.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "로그인 요청 객체")
public class LoginRequest {

  @NotBlank
  @Schema(description = "로그인 아이디", example = "engus525")
  String loginId;

  @NotBlank
  @Schema(description = "비밀번호")
  String password;

}
