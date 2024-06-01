package umc.dofarming.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import umc.dofarming.domain.member.Member;
import umc.dofarming.domain.service.DetailMemberService;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final DetailMemberService detailMemberService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = detailMemberService.findByLoginId(username);
        return createUserDetails(member);
    }

    private CustomUserDetail createUserDetails(Member member) {
        return CustomUserDetail.builder()
                .loginId(member.getLoginId())
                .password(member.getPassword())
                .build();
    }
}
