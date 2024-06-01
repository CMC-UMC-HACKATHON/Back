package umc.dofarming.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.dofarming.domain.member.controller.request.CreateMemberRequest;
import umc.dofarming.domain.member.controller.request.LoginRequest;
import umc.dofarming.domain.member.controller.response.LoginResponse;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final CreateMemberService createMemberService;

  public void createMember(CreateMemberRequest request) {
    createMemberService.createMember(request);
  }

  public LoginResponse login(LoginRequest request) {
    return createMemberService.login(request);
  }
}
