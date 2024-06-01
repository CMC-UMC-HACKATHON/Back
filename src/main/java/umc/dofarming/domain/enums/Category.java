package umc.dofarming.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    COMMON("도서"), EXERCISE("운동"), DAILY("일상"), CULTURE("교양");

    private final String title;
}
