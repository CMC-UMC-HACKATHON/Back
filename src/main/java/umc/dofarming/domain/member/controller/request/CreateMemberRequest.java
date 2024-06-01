package umc.dofarming.domain.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "회원가입 요청 객체")
public class CreateMemberRequest {

  @NotBlank
  @Schema(description = "로그인 아이디", example = "engus525")
  private String loginId;

  @NotBlank
  @Schema(description = "닉네임(이름)", example = "고기")
  private String username;

  @NotBlank
  @Schema(description = "성별", allowableValues = {"남성","여성"})
  private String gender;

  @NotBlank
  @Schema(description = "비밀번호")
  private String password;

  @NotBlank
  @Schema(description = "비밀번호 확인")
  private String checkPassword;


  @NotBlank
  @Schema(description = "핸드폰 번호", example = "01012341234")
  private String phoneNumber;

  @AssertTrue(message = "비밀번호가 일치하지 않습니다.")
  @Schema(hidden = true)
  public boolean getPasswordValidate() {
    return password.equals(checkPassword);
  }
}
