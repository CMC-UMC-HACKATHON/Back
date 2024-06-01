package umc.dofarming.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.dofarming.api_response.exception.GeneralException;
import umc.dofarming.api_response.status.ErrorStatus;
import umc.dofarming.domain.member.Member;
import umc.dofarming.domain.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailMemberService {

  private final MemberRepository memberRepository;

  public Member findByLoginId(String loginId) {
    return memberRepository.findByLoginId(loginId)
      .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND, "존재하지 않는 로그인 아이디입니다."));
  }

}
