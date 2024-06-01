package umc.dofarming.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.dofarming.api_response.ApiResponse;
import umc.dofarming.domain.member.controller.request.CreateMemberRequest;
import umc.dofarming.domain.member.controller.request.LoginRequest;
import umc.dofarming.domain.member.controller.response.LoginResponse;
import umc.dofarming.domain.member.service.MemberService;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Tag(name = "MemberController", description = "멤버 관련 API")
public class MemberController {

  private final MemberService memberService;

  @PostMapping("/sign-up")
  @Operation(summary = "회원가입")
  public ApiResponse<Void> createMember(@RequestBody @Valid CreateMemberRequest request) {
    memberService.createMember(request);
    return ApiResponse.onCreate();
  }

  @PostMapping("/login")
  @Operation(summary = "로그인")
  public ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
    return ApiResponse.onSuccess(memberService.login(request));
  }
}
