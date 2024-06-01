package umc.dofarming.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    COMMON("공통", "https://donfarming.s3.ap-northeast-2.amazonaws.com//%EA%B3%B5%ED%86%B5.png"),
    EXERCISE("운동", "https://donfarming.s3.ap-northeast-2.amazonaws.com//%EC%9A%B4%EB%8F%99.png"),
    DAILY("일상", "https://donfarming.s3.ap-northeast-2.amazonaws.com//%EC%9D%BC%EC%83%81.png"),
    CULTURE("교양", "https://donfarming.s3.ap-northeast-2.amazonaws.com//%EA%B5%90%EC%96%91.png");

    private final String title;
    private final String S3Url;
}
