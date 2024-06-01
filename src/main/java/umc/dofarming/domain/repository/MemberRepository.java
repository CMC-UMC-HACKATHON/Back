package umc.dofarming.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.dofarming.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByUsername(String username);
  Optional<Member> findByLoginId(String loginId);
}
