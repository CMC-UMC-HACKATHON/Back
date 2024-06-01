package umc.dofarming.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.dofarming.domain.controller.request.CreateMemberRequest;
import umc.dofarming.domain.controller.request.LoginRequest;
import umc.dofarming.domain.controller.response.LoginResponse;

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
