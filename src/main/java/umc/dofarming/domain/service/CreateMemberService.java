package umc.dofarming.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.dofarming.api_response.exception.GeneralException;
import umc.dofarming.api_response.status.ErrorStatus;
import umc.dofarming.domain.controller.request.CreateMemberRequest;
import umc.dofarming.domain.controller.request.LoginRequest;
import umc.dofarming.domain.controller.response.LoginResponse;
import umc.dofarming.domain.member.Member;
import umc.dofarming.domain.repository.MemberRepository;
import umc.dofarming.security.jwt.JwtToken;
import umc.dofarming.security.jwt.JwtTokenProvider;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateMemberService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder encoder;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final JwtTokenProvider jwtTokenProvider;

  /**
   * 회원가입
   */
  public void createMember(CreateMemberRequest request) {
    if (memberRepository.findByLoginId(request.getLoginId()).isPresent()) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "이미 사용중인 아이디입니다.");
    }

    memberRepository.save(
      Member.builder()
        .loginId(request.getLoginId())
        .password(encoder.encode(request.getPassword()))
        .username(request.getUsername())
        .gender(request.getGender())
        .phoneNumber(request.getPhoneNumber())
        .build()
    );
  }

  /**
   * 로그인
   */
  public LoginResponse login(LoginRequest request) {
    UsernamePasswordAuthenticationToken authenticationToken =
      new UsernamePasswordAuthenticationToken(request.getLoginId(), request.getPassword());
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    JwtToken jwtToken = jwtTokenProvider.generateToken(authentication, request.getLoginId());

    return LoginResponse
      .builder()
      .accessToken(jwtToken.accessToken())
      .build();
  }
}
