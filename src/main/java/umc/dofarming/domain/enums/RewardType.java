package umc.dofarming.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RewardType {
    MONEY("MONEY"), RANDOM_BOX("랜덤박스");
    private final String title;
}
