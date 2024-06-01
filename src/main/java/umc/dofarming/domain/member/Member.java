package umc.dofarming.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  @Comment("로그인 아이디")
  private String loginId;

  @Column(nullable = false, unique = true)
  @Comment("닉네임(이름)")
  private String username;

  @Column(nullable = false)
  @Comment("비밀번호")
  private String password;

  @Comment("성별")
  private String gender;

  @Comment("핸드폰 번호")
  private String phoneNumber;

}
