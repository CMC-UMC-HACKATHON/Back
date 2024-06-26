package umc.dofarming.security.jwt;

import lombok.Builder;

@Builder
public record JwtToken(
  String grantType,
  String accessToken,
  String refreshToken
) {

}
